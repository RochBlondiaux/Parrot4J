package me.rochblondiaux.parrot4j.api.network;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FTPConnection {

    private final InetSocketAddress address;
    private URLConnection connection;
    private InputStream inputStream;
    private BufferedReader reader;
    private static final String FTP_PATH = "ftp://%s:%d/%s";


    public FTPConnection(InetSocketAddress address) {
        this.address = address;
    }

    public FTPConnection(String host, int port) {
        this(new InetSocketAddress(host, port));
    }


    public CompletableFuture<Void> connect(@NotNull String path) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(String.format(FTP_PATH, address.getHostString(), address.getPort(), path));
                connection = url.openConnection();
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Error while connecting to url '%s'", path), e);
            }
            return null;
        });
    }

    public CompletableFuture<Void> reconnect(@NotNull String path) {
        return CompletableFuture.supplyAsync(() -> {
            disconnect().join();
            connect(path).join();
            return null;
        });
    }

    public CompletableFuture<Void> disconnect() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                throw new IllegalStateException("Error while disconnecting", e);
            }
            return null;
        });
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

    public InetSocketAddress address() {
        return address;
    }
}
