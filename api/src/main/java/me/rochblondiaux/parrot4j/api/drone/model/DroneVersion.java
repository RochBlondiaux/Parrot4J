package me.rochblondiaux.parrot4j.api.drone.model;

import me.rochblondiaux.parrot4j.api.drone.model.impl.DroneVersionImpl;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneVersion {

    String firmware();

    String hardware();

    static DroneVersion of(String firmware, String hardware) {
        return new DroneVersionImpl(firmware, hardware);
    }
}
