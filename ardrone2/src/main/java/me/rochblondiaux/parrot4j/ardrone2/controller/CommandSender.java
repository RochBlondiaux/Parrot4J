package me.rochblondiaux.parrot4j.ardrone2.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.network.UDPConnection;
import me.rochblondiaux.parrot4j.api.util.StatefulService;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.Command;
import me.rochblondiaux.parrot4j.ardrone2.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class CommandSender extends StatefulService {

    private static final int MAX_RETRIES = 5;

    private final UDPConnection connection;
    private final ConcurrentLinkedQueue<Command> queue;
    private final Thread queueThread;
    private final AuthenticationData authenticationData;
    private int sequence;
    @Setter
    private Ar2Controller controller;

    public CommandSender(ClientOptions options) {
        this.connection = new UDPConnection(new InetSocketAddress(options.address(), options.ports().commands()));
        this.queue = new ConcurrentLinkedQueue<>();
        this.sequence = 1;
        this.queueThread = new Thread(this::consumeQueue, "CommandSender");
        this.authenticationData = new AuthenticationData("Parrot4J", "Parrot4J");
    }

    public @Blocking void start() {
        if (queueThread.isAlive())
            throw new IllegalStateException("CommandSender thread is already running");
        this.queueThread.start();
        this.connection.connect().thenAccept(unused -> setReady());
        this.waitUntilReady();
    }

    public void stop() {
        if (!queueThread.isAlive())
            throw new IllegalStateException("CommandSender thread is not running");
        this.queueThread.interrupt();
        this.connection.disconnect();
    }

    public void queue(Command command) {
        queue.add(command);
    }

    public int queuedCommands() {
        return queue.size();
    }

    private void consumeQueue() {
        while (!queueThread.isInterrupted()) {
            try {
                final Command command = queue.poll();
                if (command == null || !this.connection.isConnected()) {
                    Thread.sleep(100);
                    continue;
                }
                sendCommand(command).join();
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public CompletableFuture<Void> sendCommand(@NotNull Function<AuthenticationData, Command> supplier) {
        return sendCommand(supplier.apply(authenticationData));
    }

    public CompletableFuture<Void> sendCommand(@NotNull Command command) {
        return CompletableFuture.supplyAsync(() -> {
            if (!connection.isConnected()) {
                this.queue(command);
                log.warn("Connection is not ready, command queued");
                return null;
            }
            final Ar2Drone drone = controller.getDrone();
            int tries = 0;
            while (tries++ < MAX_RETRIES) {
                if (command instanceof ATCommand atCommand)
                    sendTextCommand(atCommand.buildText(sequence));
                else if (command instanceof ComposedCommand composedCommand) {
                    for (Command c : composedCommand.commands())
                        sendCommand(c).join();
                }

                try {
                    if (command.timeout() > 0)
                        Thread.sleep(command.timeout());
                    Thread.sleep(10);
                    if (command.isSuccessful(drone.data(), drone.configuration()))
                        return null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException("Command failed after " + MAX_RETRIES + " tries");
        });
    }

    public void sendTextCommand(@NotNull String commandText) {
        if (!connection.isConnected())
            throw new UnsupportedOperationException("Connection is not established");
        byte[] sendData = commandText.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, connection.address());
        if (!commandText.startsWith("AT*COMWDG"))
            log.info("Sending command: " + commandText);
        connection.send(sendPacket);
    }

    public CompletableFuture<Void> connect() {
        return connection.connect();
    }

    public CompletableFuture<Void> disconnect() {
        return connection.disconnect();
    }

    public CompletableFuture<Void> reconnect() {
        return connection.reconnect();
    }

    public int sequence() {
        return sequence;
    }

    public int nextSequence() {
        return sequence++;
    }
}
