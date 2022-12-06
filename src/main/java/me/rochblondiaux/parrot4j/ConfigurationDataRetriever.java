package me.rochblondiaux.parrot4j;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.components.*;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.listeners.DroneConfigurationListener;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Log4j2
public class ConfigurationDataRetriever implements Runnable {
    public static final String SEPARATOR = " = ";


    private final ThreadComponent threadComponent;

    private final AddressComponent addressComponent;

    private final TcpComponent tcpComponent;

    private final ReadyStateListenerComponent readyStateListenerComponent;

    private final ErrorListenerComponent errorListenerComponent;

    private final Set<DroneConfigurationListener> droneConfigurationListeners;

    private String droneIpAddress;

    private int configDataPort;

    @Inject
    public ConfigurationDataRetriever(ThreadComponent threadComponent, AddressComponent addressComponent, TcpComponent tcpComponent,
                                      ReadyStateListenerComponent readyStateListenerComponent, ErrorListenerComponent errorListenerComponent) {
        this.threadComponent = threadComponent;
        this.addressComponent = addressComponent;
        this.tcpComponent = tcpComponent;
        this.readyStateListenerComponent = readyStateListenerComponent;
        this.errorListenerComponent = errorListenerComponent;

        droneConfigurationListeners = Sets.newHashSet();
    }

    public void start(String droneIpAddress, int configDataPort) {
        this.droneIpAddress = droneIpAddress;
        this.configDataPort = configDataPort;

        log.info("Starting config data thread");
        threadComponent.start(this);
    }

    public void stop() {
        log.info("Stopping config data thread");
        threadComponent.stopAndWait();
    }

    public void addReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void removeReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void addDroneConfigurationListener(DroneConfigurationListener droneConfigurationListener) {
        droneConfigurationListeners.add(droneConfigurationListener);
    }

    public void removeDroneConfigurationListener(DroneConfigurationListener droneConfigurationListener) {
        droneConfigurationListeners.remove(droneConfigurationListener);
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
        connectToConfigDataPort();
        readyStateListenerComponent.emitReadyStateChange(ReadyStateChangeListener.ReadyState.READY);

        while (!threadComponent.isStopped()) {
            try {
                processData(readLines());
            } catch (Throwable e) {
                log.error("Error processing the config control data", e);
            }
        }

        disconnectFromConfigDataPort();
    }

    private void connectToConfigDataPort() {
        log.info(String.format("Connecting to config data port %d", configDataPort));
        tcpComponent.connect(addressComponent.getInetAddress(droneIpAddress), configDataPort, 1000);
    }

    public Collection<String> readLines() {
        try {
            return doReadLines();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Error receiving current lines", e);
        }
    }

    private Collection<String> doReadLines() throws IOException, ClassNotFoundException {
        Collection<String> receivedLines = Lists.newArrayList();

        try {
            String line = tcpComponent.getReader().readLine();
            while (line != null) {
                receivedLines.add(line);
                line = tcpComponent.getReader().readLine();
            }
        } catch (SocketTimeoutException e) {
            // EOF is reached (this is a dirty workaround, but there is no indicator telling us when to stop)
        }

        return receivedLines;
    }

    private void processData(Collection<String> lines) {
        if (lines.size() == 0) {
            return;
        }

        log.debug("Drone configuration data received");
        DroneConfiguration droneConfiguration = getDroneConfiguration(lines);

        for (DroneConfigurationListener listener : droneConfigurationListeners) {
            listener.onDroneConfiguration(droneConfiguration);
        }
    }

    private DroneConfiguration getDroneConfiguration(Collection<String> lines) {
        Map<String, String> configMap = Maps.newHashMap();

        for (String line : lines) {
            String[] configOption = line.split(SEPARATOR);
            if (configOption.length != 2) {
                continue;
            }

            configMap.put(configOption[0], configOption[1]);
        }

        return new DroneConfiguration(configMap);
    }

    private void disconnectFromConfigDataPort() {
        log.info("Disconnecting from config data port {}", configDataPort);
        tcpComponent.disconnect();
    }
}