package me.rochblondiaux.parrot4j.api.client;

import lombok.Builder;
import me.rochblondiaux.parrot4j.api.driver.DroneDriver;
import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.drone.model.Drone;

import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Builder
public record Client<D extends Drone>(DroneDriver<D> driver, ClientOptions options, Drone drone) {

    public Client(DroneDriver<D> driver, ClientOptions options) {
        this(driver, options, driver.factory().make(options.address()));
    }

    public CompletableFuture<D> connect() {
        // TODO: do something
        return null;
    }

    public CompletableFuture<Void> disconnect() {
        return null;
    }

    public boolean isConnected() {
        return drone.online();
    }

    public DroneController controller() {
        return driver.controller();
    }
}
