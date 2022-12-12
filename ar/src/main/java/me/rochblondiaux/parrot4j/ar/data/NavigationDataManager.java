package me.rochblondiaux.parrot4j.ar.data;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.network.UDPConnection;
import me.rochblondiaux.parrot4j.api.util.StatefulManager;
import me.rochblondiaux.parrot4j.ar.ArController;
import me.rochblondiaux.parrot4j.ar.ArDrone;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class NavigationDataManager extends StatefulManager implements Runnable {

    public static final int RECEIVING_BUFFER_SIZE = 10240;
    private final UDPConnection connection;
    private final byte[] receivingBuffer;
    private final DatagramPacket incomingDataPacket;
    private final ArDrone drone;
    private final Thread thread;
    private final NavigationDataDecoder decoder;

    public NavigationDataManager(@NotNull ArController controller) {
        this.thread = new Thread(this, "DataUpdater");
        this.drone = controller.getDrone();
        this.connection = new UDPConnection(new InetSocketAddress(controller.getOptions().getAddress(), controller.getOptions().getModel().ports().navigation()));
        this.decoder = new NavigationDataDecoder();
        this.receivingBuffer = new byte[RECEIVING_BUFFER_SIZE];
        this.incomingDataPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
    }

    public @Blocking void start() {
        long start = System.currentTimeMillis();
        log.info("Starting navigation data manager...");
        this.connection.connect()
                .exceptionally(throwable -> {
                    log.error("Error while connecting to the drone navigation channel.", throwable);
                    System.exit(0);
                    return null;
                })
                .thenAccept(unused -> {
                    this.thread.setDaemon(true);
                    this.thread.start();
                    log.info("Navigation data manager started in {}ms", System.currentTimeMillis() - start);
                    initializeCommunication();
                    setReady(true);
                });
        this.waitUntilReady();
    }

    public @NonBlocking void stop() {
        this.thread.interrupt();
        connection.disconnect();
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            try {
                connection.receive(incomingDataPacket);
                process();

                connection.sendKeepAlivePacket();
            } catch (Throwable e) {
                connection.sendKeepAlivePacket();
                log.error("Error while receiving data from the drone.", e);
            }
        }
        connection.disconnect()
                .exceptionally(throwable -> {
                    log.error("Error while disconnecting from the drone navigation channel.", throwable);
                    return null;
                })
                .join();
        log.info("Navigation data manager stopped.");
    }

    private void process() {
        try {
            log.debug("Processing drone data...");
            NavigationData data = decoder.from(receivingBuffer, incomingDataPacket.getLength());
            if (data == null)
                return;
            log.debug("Received nav data - battery level: {} percent, altitude: {}", data.battery(), data.altitude());
            drone.updateNavigationData(data);
            // TODO: call an event
            return;
        } catch (RuntimeException ignored) {
        }
        return;
    }

    private void initializeCommunication() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        connection.sendKeepAlivePacket();
    }
}
