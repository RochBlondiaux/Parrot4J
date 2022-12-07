package me.rochblondiaux.parrot4j.api.driver;

import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.drone.factory.DroneFactory;
import me.rochblondiaux.parrot4j.api.drone.model.Drone;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneDriver<D extends Drone> {

    DroneFactory<D> factory();

    DroneController controller();
}
