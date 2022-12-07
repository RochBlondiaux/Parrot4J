package me.rochblondiaux.parrot4j.api.drone.enums;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.api.drone.model.DroneModel;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum DroneModels implements DroneModel {
    AR_DRONE_2("AR", 2),
    ;

    private final String name;
    private final int version;

    @Override
    public String model() {
        return name;
    }

    @Override
    public int version() {
        return version;
    }
}
