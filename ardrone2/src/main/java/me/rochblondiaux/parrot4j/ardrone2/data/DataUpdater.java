package me.rochblondiaux.parrot4j.ardrone2.data;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.event.EventService;
import me.rochblondiaux.parrot4j.api.network.UDPConnection;
import me.rochblondiaux.parrot4j.api.util.StatefulService;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import me.rochblondiaux.parrot4j.ardrone2.events.DroneDataUpdateEvent;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class DataUpdater extends StatefulService implements Runnable {

    public static final int RECEIVING_BUFFER_SIZE = 10240;
    private final UDPConnection connection;
    private final byte[] receivingBuffer;
    private final DatagramPacket incomingDataPacket;
    private final DataDecoder decoder;
    private final Ar2Drone drone;
    private final Thread thread;
    @Getter
    private DroneData data;


    public DataUpdater(@NotNull Ar2Controller controller) {
        this.thread = new Thread(this, "DataUpdater");
        this.drone = controller.getDrone();
        this.connection = new UDPConnection(new InetSocketAddress(controller.getOptions().address(), controller.getOptions().ports().navigation()));
        this.decoder = new DataDecoder();
        this.receivingBuffer = new byte[RECEIVING_BUFFER_SIZE];
        this.incomingDataPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
    }

    public @Blocking void start() {
        this.thread.start();
        this.waitUntilReady();
    }

    public void stop() {
        this.thread.interrupt();
        connection.disconnect();
    }

    @Override
    public void run() {
        log.info("Starting DataUpdater...");
        connection.connect()
                .exceptionally(throwable -> {
                    log.error("Error while connecting to the drone navigation channel.", throwable);
                    System.exit(0);
                    return null;
                })
                .join();
        initializeCommunication();
        log.info("Data updater started!");
        setReady();
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
        log.info("DataUpdater disconnected.");
    }

    private void process() {
        try {
            log.debug("Processing drone data...");
            this.data = decoder.from(receivingBuffer, incomingDataPacket.getLength());
            if (this.data == null)
                return;
            log.debug("Received nav data - battery level: {} percent, altitude: {}", data.battery(), data.altitude());
            EventService.call(new DroneDataUpdateEvent(drone, data));
            drone.data(this.data);
        } catch (RuntimeException ignored) {
        }
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
