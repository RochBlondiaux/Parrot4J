package me.rochblondiaux.parrot4j;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.components.*;
import me.rochblondiaux.parrot4j.data.NavData;
import me.rochblondiaux.parrot4j.listeners.NavDataListener;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;
import me.rochblondiaux.parrot4j.navdata.NavigationDataDecoder;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

import static me.rochblondiaux.parrot4j.helpers.ThreadHelper.sleep;

@Log4j2
public class NavigationDataRetriever implements Runnable {
    public static final int RECEIVING_BUFFER_SIZE = 10240;
    private final ThreadComponent threadComponent;
    private final AddressComponent addressComponent;
    private final UdpComponent udpComponent;
    private final ReadyStateListenerComponent readyStateListenerComponent;
    private final ErrorListenerComponent errorListenerComponent;
    private final NavigationDataDecoder decoder;
    private final Set<NavDataListener> navDataListeners;
    private byte[] receivingBuffer;
    private DatagramPacket incomingDataPacket;
    private String droneIpAddress;
    private int navDataPort;

    @Inject
    public NavigationDataRetriever(ThreadComponent threadComponent, AddressComponent addressComponent, UdpComponent udpComponent,
                                   ReadyStateListenerComponent readyStateListenerComponent, ErrorListenerComponent errorListenerComponent,
                                   NavigationDataDecoder decoder) {
        super();
        this.threadComponent = threadComponent;
        this.addressComponent = addressComponent;
        this.udpComponent = udpComponent;
        this.readyStateListenerComponent = readyStateListenerComponent;
        this.errorListenerComponent = errorListenerComponent;
        this.decoder = decoder;
        navDataListeners = Sets.newLinkedHashSet();

        determineDatagramPackets();
    }

    public void start(String droneIpAddress, int navDataPort) {
        this.droneIpAddress = droneIpAddress;
        this.navDataPort = navDataPort;

        log.info("Starting nav data thread");
        threadComponent.start(this);
    }

    public void stop() {
        log.info("Stopping nav data thread");
        threadComponent.stopAndWait();
    }

    public void addReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void removeReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void addNavDataListener(NavDataListener navDataListener) {
        navDataListeners.add(navDataListener);
    }

    public void removeNavDataListener(NavDataListener navDataListener) {
        navDataListeners.remove(navDataListener);
    }

    private void determineDatagramPackets() {
        receivingBuffer = new byte[RECEIVING_BUFFER_SIZE];
        incomingDataPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Throwable e) {
            errorListenerComponent.emitError(e);
        }
    }

    private void doRun() {
        connectToNavDataPort();
        initializeCommunication();
        readyStateListenerComponent.emitReadyStateChange(ReadyStateChangeListener.ReadyState.READY);

        while (!threadComponent.isStopped()) {
            try {
                udpComponent.receive(incomingDataPacket);
                processData();

                udpComponent.sendKeepAlivePacket();
            } catch (Throwable e) {
                udpComponent.sendKeepAlivePacket();
                log.error(e.getMessage(), e);
            }
        }

        disconnectFromNavDataPort();
    }

    private void connectToNavDataPort() {
        InetAddress address = addressComponent.getInetAddress(droneIpAddress);

        log.info("Connecting to nav data port {}", navDataPort);
        udpComponent.connect(address, navDataPort);
    }

    private void initializeCommunication() {
        sleep(100);
        udpComponent.sendKeepAlivePacket();
    }

    private void processData() {
        NavData navData = getNavData();
        if (navData == null) {
            return;
        }
        log.trace("Received nav data - battery level: {} percent, altitude: {}", navData.getBatteryLevel(), navData.getAltitude());
        for (NavDataListener listener : navDataListeners) {
            listener.onNavData(navData);
        }
    }

    private NavData getNavData() {
        try {
            return decoder.getNavDataFrom(receivingBuffer, incomingDataPacket.getLength());
        } catch (RuntimeException e) {
            // Happens from time to time
            return null;
        }
    }

    private void disconnectFromNavDataPort() {
        log.info("Disconnecting from nav data port {}", navDataPort);
        udpComponent.disconnect();
    }
}