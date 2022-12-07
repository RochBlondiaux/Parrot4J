package me.rochblondiaux.parrot4j.api.drone.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class UDPConnection extends AbstractConnection {

    private DatagramPacket keepAlivePacket;
    private DatagramSocket socket;

    public UDPConnection(InetAddress ip, int port) {
        super(ip, port);
    }

    @Override
    public CompletableFuture<Void> connect() {
        return CompletableFuture.supplyAsync(() -> {
            this.makeKeepAlivePacket();

            try {
                socket = new DatagramSocket(port);
                socket.setSoTimeout(3000);
                this.connected = true;
            } catch (SocketException e) {
                throw new IllegalStateException(e);
            }
            return null;
        });
    }


    @Override
    public void disconnect() {
        this.connected = false;
        socket.disconnect();
        socket = null;
    }

    @Override
    public void sendKeepAlivePacket() {
        this.send(keepAlivePacket);
    }

    public void send(@NotNull DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void receive(@NotNull DatagramPacket packet) {
        try {
            socket.receive(packet);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void makeKeepAlivePacket() {
        keepAlivePacket = new DatagramPacket(KEEP_ALIVE_BYTES, KEEP_ALIVE_BYTES.length, address, port);
    }
}
