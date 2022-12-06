package me.rochblondiaux.parrot4j.components;


import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

@Log4j2
public class TcpComponent {
    private static final byte[] KEEP_ALIVE_BYTES = new byte[]{0x01, 0x00, 0x00, 0x00};
    private static final int DEFAULT_TIMEOUT = 3000;


    private Socket socket = null;
    private InetAddress address;
    private int port;
    private int timeout;
    private BufferedReader reader = null;

    public void connect(InetAddress address, int port) {
        connect(address, port, DEFAULT_TIMEOUT);
    }

    public void connect(InetAddress address, int port, int timeout) {
        this.address = address;
        this.port = port;
        this.timeout = timeout;

        try {
            socket = new Socket(address, port);
            socket.setSoTimeout(timeout);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException("Error while connecting to TCP socket %s:%d".formatted(address.getHostName(), port), e);
        }
    }

    public void disconnect() {
        try {
            socket.close();

            socket = null;
            reader = null;
        } catch (IOException e) {
            throw new IllegalStateException("Error while disconnecting socket", e);
        }
    }

    public void reconnect() {
        try {
            disconnect();
        } catch (Exception e) {
            log.error("Error while disconnecting from port %d".formatted(port), e);
        }

        connect(address, port, timeout);
    }

    public void sendKeepAlivePacket() {
        try {
            socket.getOutputStream().write(KEEP_ALIVE_BYTES);
        } catch (IOException e) {
            throw new IllegalStateException("Error sending keep alive packet bytes", e);
        }
    }

    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            throw new IllegalStateException("Error getting input stream", e);
        }
    }

    public BufferedReader getReader() {
        return reader;
    }
}