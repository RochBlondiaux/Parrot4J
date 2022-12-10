package me.rochblondiaux.parrot4j.api.drone;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneModel {

    String model();

    int majorVersion();

    DronePort ports();
}
