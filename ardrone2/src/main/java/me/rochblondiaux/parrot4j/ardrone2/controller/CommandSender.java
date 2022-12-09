package me.rochblondiaux.parrot4j.ardrone2.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.network.UDPConnection;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.AuthenticationCommand;
import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class CommandSender {

    private static final int MAX_RETRIES = 5;

    private final UDPConnection connection;
    private final ConcurrentLinkedQueue<ATCommand> queue;
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

    public void start() {
        if (queueThread.isAlive())
            throw new IllegalStateException("CommandSender thread is already running");
        this.queueThread.start();
    }

    public void stop() {
        if (!queueThread.isAlive())
            throw new IllegalStateException("CommandSender thread is not running");
        this.queueThread.interrupt();
    }

    public void queue(ATCommand command) {
        queue.add(command);
    }

    public int queuedCommands() {
        return queue.size();
    }

    private void consumeQueue() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                final ATCommand command = queue.poll();
                if (command == null) {
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

    public CompletableFuture<Void> sendCommands(@NotNull ATCommand... commands) {
        return CompletableFuture.allOf(Arrays.stream(commands)
                .map(this::sendCommand)
                .toArray(CompletableFuture[]::new));
    }

    public CompletableFuture<Void> sendCommand(@NotNull ATCommand command) {
        return CompletableFuture.supplyAsync(() -> {
            final Ar2Drone drone = controller.getDrone();
            int tries = 0;
            while (tries++ < MAX_RETRIES) {
                if (command.isAuthenticationNeeded())
                    sendCommand(new AuthenticationCommand(authenticationData));
                sendTextCommand(command.buildText(nextSequence()));
                command.onExecution(controller);
                if (command.timeout() > 0)
                    try {
                        Thread.sleep(command.timeout());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                if (command.isSuccessful(drone.data(), drone.configuration()))
                    return null;
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
            log.debug("Sending command: " + commandText);
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
