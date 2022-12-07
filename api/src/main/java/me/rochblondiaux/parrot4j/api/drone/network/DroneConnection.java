package me.rochblondiaux.parrot4j.api.drone.network;

import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneConnection extends NetworkConnection {

    CompletableFuture<Void> reconnect(int delay);

    void sendKeepAlivePacket();
}
