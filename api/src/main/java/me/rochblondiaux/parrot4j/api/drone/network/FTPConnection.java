package me.rochblondiaux.parrot4j.api.drone.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public class FTPConnection implements NetworkConnection {

    private final InetAddress address;
    private final int port;
    @Getter
    private boolean connected;
    private URLConnection connection;

    private InputStream inputStream;

    private BufferedReader reader;

    @Override
    public CompletableFuture<Void> connect() {
        throw new UnsupportedOperationException("Use #connect(String) instead");
    }

    public CompletableFuture<Void> connect(String urlPath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(urlPath);
                connection = url.openConnection();
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                return null;
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Error while connecting to url '%s'", urlPath), e);
            }
        });
    }

    @Override
    public void disconnect() {
        try {
            reader.close();
            inputStream.close();
            this.connected = false;
        } catch (IOException e) {
            throw new IllegalStateException("Error while disconnecting", e);
        }
    }

    public Collection<String> readLines() {
        try {
            return doReadLines();
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading lines", e);
        }
    }

    private Collection<String> doReadLines() throws IOException {
        Collection<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        return lines;
    }

    @Override
    public @NotNull InetAddress address() {
        return address;
    }

    @Override
    public int port() {
        return port;
    }
}
