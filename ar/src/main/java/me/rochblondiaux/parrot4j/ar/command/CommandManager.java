package me.rochblondiaux.parrot4j.ar.command;

import me.rochblondiaux.parrot4j.api.network.UDPConnection;
import me.rochblondiaux.parrot4j.api.util.StatefulManager;
import me.rochblondiaux.parrot4j.ar.ArController;
import me.rochblondiaux.parrot4j.ar.ArDrone;
import me.rochblondiaux.parrot4j.ar.command.simple.WatchdogResetCommand;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;
import org.tinylog.Logger;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class CommandManager extends StatefulManager {

    private static final int MAX_RETRIES = 5;

    private final ArController controller;
    private final UDPConnection connection;
    private final BlockingQueue<Command> queue;
    private final Thread queueThread;
    private final AuthenticationData authenticationData;
    private final AtomicInteger commandsCount = new AtomicInteger(0);

    private int sequence;

    public CommandManager(@NotNull ArController controller) {
        this.controller = controller;
        this.authenticationData = new AuthenticationData("Parrot4J", "Parrot4J");
        this.connection = new UDPConnection(new InetSocketAddress(controller.getOptions().getAddress(), controller.getOptions().getModel().ports().commands()));
        this.queue = new LinkedBlockingQueue<>();
        this.queueThread = new Thread(this::consumeQueue, "CommandSender");
    }

    public @Blocking void start() {
        if (queueThread.isAlive())
            throw new IllegalStateException("Command queue thread is already running");
        long start = System.currentTimeMillis();
        Logger.info("Starting command manager...");
        this.connection.connect().thenAccept(unused -> setReady(true));
        this.waitUntilReady();
        this.queueThread.setDaemon(true);
        this.queueThread.start();
        Logger.info("Command manager started in {}ms", System.currentTimeMillis() - start);
    }

    public @NonBlocking void stop() {
        this.setReady(false);
        this.queueThread.interrupt();
        this.connection.disconnect();
        Logger.info("Command manager disconnected.");
    }

    public void consumeQueue() {
        while (!queueThread.isInterrupted()) {
            try {
                final Command cmd = queue.take();
                if (!this.connection.isConnected()) {
                    Logger.warn("Connection is not ready, waiting...");
                    Thread.sleep(50);
                    continue;
                }
                sendCommand(cmd);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public @NotNull CompletableFuture<Void> send(@NotNull Function<AuthenticationData, Command> supplier) {
        return send(supplier.apply(authenticationData));
    }

    public @NotNull CompletableFuture<Void> send(@NotNull Command command) {
        try {
            queue.put(command);
            if (command instanceof ATCommand && commandsCount.getAndIncrement() % 10 == 0)
                queue.put(new WatchdogResetCommand());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return command.callback();
    }

    private void sendCommand(@NotNull Command command) {
        if (!connection.isConnected()) {
            queue.add(command);
            Logger.warn("The drone command channel is not ready, command queued.");
            return;
        }
        if (command instanceof ATCommand) {
            ATCommand atCommand = (ATCommand) command;
            if (atCommand.hasPreparationCommand())
                sendTextCommand(atCommand.preparationCommandText(sequence()));
            sendTextCommand(atCommand.buildText(sequence()));
            execute(atCommand);
        } else if (command instanceof ComposedCommand) {
            ComposedCommand composedCommand = (ComposedCommand) command;
            try {
                CompletableFuture.allOf(composedCommand.commands()
                                .stream()
                                .map(this::send)
                                .toArray(CompletableFuture[]::new))
                        .thenAccept(unused -> {
                            command.callback().complete(null);
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendTextCommand(@NotNull String commandText) {
        if (!connection.isConnected())
            throw new UnsupportedOperationException("Connection is not established");
        byte[] sendData = commandText.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, connection.address());
        if (!commandText.startsWith("AT*COMWDG"))
            Logger.debug("Sending command: " + commandText);
        connection.send(sendPacket);
    }

    private void execute(@NotNull ATCommand command) {
        final ArDrone drone = controller.getDrone();
        final AtomicInteger tries = new AtomicInteger(0);
        long start = System.currentTimeMillis();
        CompletableFuture.runAsync(() -> {
            try {
                while (tries.getAndIncrement() < MAX_RETRIES) {
                    try {
                        if (command.timeout() > 0)
                            Thread.sleep(command.timeout());
                        try {
                            command.isSuccessful(drone.navigationData(), drone.configuration());
                            Logger.info("Command {} executed successfully in {}ms!", command.getClass().getSimpleName(), System.currentTimeMillis() - start);
                            command.callback().complete(null);
                            return;
                        } catch (Exception e) {
                            Logger.warn("Command {} failed, retrying.. ({}).", command.getClass().getSimpleName(), e.getMessage());
                        }
                        sendCommand(command);
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        command.callback().completeExceptionally(e);
                    }
                }
                command.callback().completeExceptionally(new RuntimeException("Command " + command.getClass().getSimpleName() + " failed after " + tries.get() + " tries"));
                Logger.error("Command {} failed after {} tries!", command.getClass().getSimpleName(), MAX_RETRIES);
            } catch (Exception e) {
                Logger.error(e);
                e.printStackTrace();
            }
        }, ArController.EXECUTOR);
    }


    private int sequence() {
        return sequence++;
    }
}
