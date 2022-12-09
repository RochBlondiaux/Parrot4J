package me.rochblondiaux.parrot4j.ardrone2.configuration;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.network.TCPConnection;
import me.rochblondiaux.parrot4j.api.util.StatefulService;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import org.jetbrains.annotations.Blocking;
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
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class ConfigurationUpdater extends StatefulService implements Runnable {

    private final DroneConfiguration configuration;
    private final TCPConnection connection;
    private final Thread thread;

    public ConfigurationUpdater(@NotNull Ar2Controller controller) {
        this.configuration = controller.getDrone().configuration();
        this.connection = new TCPConnection(new InetSocketAddress(controller.getOptions().address(), controller.getOptions().ports().control()));
        this.thread = new Thread(this, "ConfigurationUpdater");
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
        log.info("Connecting to the drone control channel...");
        connection.connect()
                .exceptionally(throwable -> {
                    log.error("Unable to connect to the drone control channel", throwable);
                    System.exit(0);
                    return null;
                })
                .join();
        log.info("Connected to the drone control channel.");
        setReady();
        while (!thread.isInterrupted()) {
            final List<String> data = read();
            if (data == null || data.isEmpty())
                continue;
            process(data);
        }
        connection.disconnect();
        log.info("ConfigurationUpdater disconnected.");
    }

    private void process(List<String> data) {
        log.info("Processing drone configuration data...");
        long start = System.currentTimeMillis();

        Map<ConfigurationKeys, String> parsedData = new HashMap<>();
        for (String entry : data) {
            log.info(entry);
            String[] split = entry.split(" = ");
            if (split.length != 2)
                continue;
            ConfigurationKeys.from(split[0])
                    .ifPresentOrElse(configurationKeys -> parsedData.put(configurationKeys, split[1]), () -> log.warn("Unknown configuration key: {}", split[0]));
        }
        configuration.update(parsedData);
        log.info("Drone configuration data processed in {} ms.", System.currentTimeMillis() - start);
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
