package me.rochblondiaux.parrot4j.api.client;

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
public class Client<D extends Drone> {

    private final DroneDriver<D> driver;
    private final ClientOptions options;
    private final DroneController controller;
    private final D drone;

    public Client(DroneDriver<D> driver, ClientOptions options) {
        this.driver = driver;
        this.options = options;
        this.drone = driver.factory(options).make(options.address());
        this.controller = driver.controller(options, drone);
    }

    public CompletableFuture<D> connect() {
        // TODO: do something
        return null;
    }

    public void disconnect() {
        controller.disconnect();
    }

    public boolean isConnected() {
        return drone.online();
    }

    public D drone() {
        return drone;
    }

    public DroneController controller() {
        return controller;
    }
}
