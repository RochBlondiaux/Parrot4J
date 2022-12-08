package me.rochblondiaux.parrot4j.api.network;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface NetworkConnection {

    CompletableFuture<Void> connect();

    CompletableFuture<Void> reconnect();

    CompletableFuture<Void> disconnect();

    void sendKeepAlivePacket();

    boolean isConnected();

    InetSocketAddress address();
}
