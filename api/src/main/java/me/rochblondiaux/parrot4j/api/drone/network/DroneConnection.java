package me.rochblondiaux.parrot4j.api.drone.network;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneConnection {


    CompletableFuture<Void> connect();

    CompletableFuture<Void> reconnect(int delay);

    void sendKeepAlivePacket();

    void disconnect();

    @NotNull InetAddress address();

    int port();

    boolean isConnected();
}
