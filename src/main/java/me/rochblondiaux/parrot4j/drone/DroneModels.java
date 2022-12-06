package me.rochblondiaux.parrot4j.drone;

import lombok.RequiredArgsConstructor;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum DroneModels implements DroneModel {
    AR_DRONE_1("AR.Drone", 1),
    AR_DRONE_2("AR.Drone", 2),
    BEBOP_1("Bebop", 1),
    BEBOP_2("Bebop", 2);


    private final String model;
    private final int version;

    @Override
    public String model() {
        return model;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public String toString() {
        return "%s v%s".formatted(model, version);
    }
}
