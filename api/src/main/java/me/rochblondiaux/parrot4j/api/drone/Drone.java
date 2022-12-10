package me.rochblondiaux.parrot4j.api.drone;

import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface Drone {

    String serialNumber();

    DroneVersion version();

    DroneModel model();

    int batteryLevel();

    Rotation rotation();

    Speed speed();

    float altitude();

    boolean flying();


}
