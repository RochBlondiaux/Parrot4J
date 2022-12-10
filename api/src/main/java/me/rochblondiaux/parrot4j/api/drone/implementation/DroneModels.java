package me.rochblondiaux.parrot4j.api.drone.implementation;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.api.drone.DroneModel;
import me.rochblondiaux.parrot4j.api.drone.DronePort;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum DroneModels implements DroneModel {
    AR_DRONE_1("AR", 1, DronePorts.AR_DRONE_1),
    AR_DRONE_2("AR", 2, DronePorts.AR_DRONE_2),
    BEBOP_2("Bebop", 2, DronePorts.BEBOP_2),
    ;

    private final String model;
    private final int version;
    private final DronePort ports;

    @Override
    public String model() {
        return model;
    }

    @Override
    public int majorVersion() {
        return version;
    }

    @Override
    public DronePort ports() {
        return ports;
    }
}
