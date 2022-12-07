package me.rochblondiaux.parrot4j.api.driver;


import me.rochblondiaux.parrot4j.api.drone.Drone;
import me.rochblondiaux.parrot4j.api.drone.DroneModel;

import java.util.Set;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneDriver {

    /**
     * Get the drone model.
     *
     * @return The drone model.
     */
    Set<DroneModel> supportedModels();

    /**
     * Get the drone factory.
     *
     * @param <D> The drone type.
     * @return The drone factory.
     */
    <D extends Drone> DroneFactory<D> factory();
}
