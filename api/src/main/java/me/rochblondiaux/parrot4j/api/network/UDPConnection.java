package me.rochblondiaux.parrot4j.api.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class UDPConnection extends AbstractConnection {

    private DatagramPacket keepAlivePacket;
    private DatagramSocket socket;

    public UDPConnection(InetSocketAddress address) {
        super(address);
    }

    @Override
    public CompletableFuture<Void> connect() {
        return CompletableFuture.supplyAsync(() -> {
            this.makeKeepAlivePacket();

            try {
                socket = new DatagramSocket(address().getPort());
                socket.setSoTimeout(3000);
            } catch (SocketException e) {
                throw new IllegalStateException(e);
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> disconnect() {
        return CompletableFuture.supplyAsync(() -> {
            socket.disconnect();
            socket = null;
            return null;
        });
    }

    public void send(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void receive(DatagramPacket packet) {
        try {
            socket.receive(packet);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void sendKeepAlivePacket() {
        this.send(keepAlivePacket);
    }

    private void makeKeepAlivePacket() {
        keepAlivePacket = new DatagramPacket(KEEP_ALIVE_BYTES, KEEP_ALIVE_BYTES.length, address());
    }

    @Override
    public boolean isConnected() {
        return socket != null;
    }
}
