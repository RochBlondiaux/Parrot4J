package me.rochblondiaux.parrot4j.api.driver;

import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneController {

    CompletableFuture<Void> initialize();

    CompletableFuture<Void> takeOff();

    CompletableFuture<Void> land();

    CompletableFuture<Void> emergency();

    void disconnect();
}
