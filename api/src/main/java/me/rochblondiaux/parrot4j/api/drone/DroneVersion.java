package me.rochblondiaux.parrot4j.api.drone;

import me.rochblondiaux.parrot4j.api.drone.implementation.DroneVersionImpl;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneVersion {

    String firmware();

    String hardware();

    static DroneVersion of(String firmware, String hardware) {
        return DroneVersionImpl.of(firmware, hardware);
    }
}
