package me.rochblondiaux.parrot4j.ardrone2.data;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.network.UDPConnection;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class DataUpdater extends Thread {

    public static final int RECEIVING_BUFFER_SIZE = 10240;
    private final UDPConnection connection;
    private final byte[] receivingBuffer;
    private final DatagramPacket incomingDataPacket;
    private final DataDecoder decoder;
    private final Ar2Drone drone;
    @Getter
    private DroneData data;
    private final List<DataUpdateListener> listeners = new ArrayList<>();


    public DataUpdater(@NotNull Ar2Controller controller) {
        super("DataUpdater");
        this.drone = controller.getDrone();
        this.connection = new UDPConnection(new InetSocketAddress(controller.getOptions().address(), controller.getOptions().ports().navigation()));
        this.decoder = new DataDecoder();
        this.receivingBuffer = new byte[RECEIVING_BUFFER_SIZE];
        this.incomingDataPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
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
        while (!isInterrupted()) {
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
            this.data = decoder.from(receivingBuffer, incomingDataPacket.getLength());
            if (this.data == null)
                return;
            log.trace("Received nav data - battery level: {} percent, altitude: {}", data.battery(), data.altitude());
            listeners.forEach(droneDataConsumer -> droneDataConsumer.onDataUpdate(data));
            drone.data(this.data);
        } catch (RuntimeException ignored) {
        }
    }

    private void initializeCommunication() {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        connection.sendKeepAlivePacket();
    }

    public void addListener(DataUpdateListener listener) {
        listeners.add(listener);
    }
}
