package me.rochblondiaux.parrot4j.api.client;

import me.rochblondiaux.parrot4j.api.driver.DroneController;
import me.rochblondiaux.parrot4j.api.driver.DroneDriver;
import me.rochblondiaux.parrot4j.api.drone.Drone;
import me.rochblondiaux.parrot4j.api.event.EventManager;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ParrotClient<D extends Drone> {

    private final ClientOptions options;
    private final DroneDriver<D> driver;
    private final D drone;
    private final DroneController controller;
    private final EventManager eventManager;

    public ParrotClient(ClientOptions options, DroneDriver<D> driver) {
        this.eventManager = new EventManager();
        this.options = options;
        this.driver = driver;
        this.drone = driver.factory().make(options.getAddress());
        if (this.drone == null)
            throw new IllegalArgumentException("Unable to make a drone instance!");
        this.controller = driver.controller(options, drone);
    }

    public DroneController controller() {
        return controller;
    }

    public D drone() {
        return drone;
    }

    public DroneDriver<D> driver() {
        return driver;
    }

    public ClientOptions options() {
        return options;
    }

    public EventManager events() {
        return eventManager;
    }

}
