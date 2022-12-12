package me.rochblondiaux.parrot4j.ar.configuration;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.network.TCPConnection;
import me.rochblondiaux.parrot4j.api.util.StatefulManager;
import me.rochblondiaux.parrot4j.ar.ArController;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class ConfigurationManager extends StatefulManager implements Runnable {

    private final DroneConfiguration configuration;
    private final TCPConnection connection;
    private final Thread thread;

    public ConfigurationManager(@NotNull ArController controller) {
        this.configuration = controller.getDrone().configuration();
        this.connection = new TCPConnection(new InetSocketAddress(controller.getOptions().getAddress(), controller.getOptions().getModel().ports().control()));
        this.thread = new Thread(this, "ConfigurationManager");
    }

    public @Blocking void start() {
        log.info("Connecting to the drone control channel...");
        connection.connect()
                .exceptionally(throwable -> {
                    log.error("Unable to connect to the drone control channel", throwable);
                    System.exit(0);
                    return null;
                })
                .thenAccept(unused -> {
                    log.info("Connected to the drone control channel.");
                    this.thread.setDaemon(true);
                    this.thread.start();
                    setReady(true);
                });
        this.waitUntilReady();
    }

    public @NonBlocking void stop() {
        this.thread.interrupt();
        this.connection.disconnect();
        this.setReady(false);
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            if (!connection.isConnected()) {
                log.warn("Drone control channel disconnect, reconnecting...");
                setReady(false);
                connection.reconnect()
                        .exceptionally(throwable -> {
                            log.error("Unable to reconnect to the drone control channel!");
                            return null;
                        })
                        .join();
                log.info("Reconnected to the drone control channel.");
            }
            final List<String> data = read();
            if (data == null || data.isEmpty())
                continue;
            process(data);
        }
        connection.disconnect();
        log.info("Disconnected from the drone control channel.");
    }

    private void process(List<String> data) {
        log.debug("Processing drone configuration data...");
        long start = System.currentTimeMillis();

        Map<ConfigurationKeys, String> parsedData = new HashMap<>();
        for (String entry : data) {
            String[] split = entry.split(" = ");
            if (split.length != 2)
                continue;
            ConfigurationKeys.from(split[0])
                    .ifPresentOrElse(configurationKeys -> parsedData.put(configurationKeys, split[1]), () -> log.warn("Unknown configuration key: {}", split[0]));
        }
        configuration.update(parsedData);
        // TODO: Call an event
        log.debug("Drone configuration data processed in {} ms.", System.currentTimeMillis() - start);
    }


    private List<String> read() {
        List<String> receivedLines = new ArrayList<>();
        try {
            String line = connection.getReader().readLine();
            while (line != null) {
                receivedLines.add(line);
                line = connection.getReader().readLine();
            }
        } catch (SocketTimeoutException e) {
            // EOF is reached (this is a dirty workaround, but there is no indicator telling us when to stop)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return receivedLines;
    }
}
