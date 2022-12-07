package me.rochblondiaux.parrot4j.api.drone.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class TCPConnection extends AbstractConnection {

    private static final int DEFAULT_TIMEOUT = 3000;
    private final int timeout;
    private Socket socket;
    private BufferedReader reader;

    public TCPConnection(InetAddress address, int port, int timeout) {
        super(address, port);
        this.socket = null;
        this.reader = null;
        this.timeout = timeout;
    }

    public TCPConnection(InetAddress address, int port) {
        this(address, port, DEFAULT_TIMEOUT);
    }

    @Override
    public CompletableFuture<Void> connect() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                socket = new Socket(address, port);
                socket.setSoTimeout(timeout);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                return null;
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Error while connecting to TCP socket %s:%d", address.getHostName(), port), e);
            }
        });
    }


    @Override
    public void disconnect() {
        try {
            socket.close();

            socket = null;
            reader = null;
        } catch (IOException e) {
            throw new IllegalStateException("Error while disconnecting socket", e);
        }
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

    public BufferedReader reader() {
        return reader;
    }
}
