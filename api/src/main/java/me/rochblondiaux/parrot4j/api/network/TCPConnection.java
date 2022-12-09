package me.rochblondiaux.parrot4j.api.network;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class TCPConnection extends AbstractConnection {

    private static final int DEFAULT_TIMEOUT = 3000;
    private Socket socket = null;
    private final int timeout;
    @Getter
    private BufferedReader reader = null;

    public TCPConnection(InetSocketAddress address) {
        this(address, DEFAULT_TIMEOUT);
    }

    public TCPConnection(InetSocketAddress address, int timeout) {
        super(address);
        this.timeout = timeout;
    }

    @Override
    public CompletableFuture<Void> connect() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                socket = new Socket(address().getAddress(), address().getPort());
                socket.setSoTimeout(timeout);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Error while connecting to TCP socket %s:%d", address().getHostName(), address().getPort()), e);
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> disconnect() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                socket.close();

                socket = null;
                reader = null;
            } catch (IOException e) {
                throw new IllegalStateException("Error while disconnecting socket", e);
            }
            return null;
        });
    }

    @Override
    public void sendKeepAlivePacket() {
        try {
            socket.getOutputStream().write(KEEP_ALIVE_BYTES);
        } catch (IOException e) {
            throw new IllegalStateException("Error sending keep alive packet bytes", e);
        }
    }

    public InputStream inputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            throw new IllegalStateException("Error getting input stream", e);
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }
}
