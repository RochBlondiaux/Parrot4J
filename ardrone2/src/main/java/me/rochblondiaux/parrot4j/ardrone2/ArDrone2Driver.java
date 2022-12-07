package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.driver.DroneDriver;
import me.rochblondiaux.parrot4j.api.drone.DroneModel;
import me.rochblondiaux.parrot4j.api.drone.DroneModels;

import java.util.Set;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ArDrone2Driver implements DroneDriver {

    @Override
    public Set<DroneModel> supportedModels() {
        return Set.of(DroneModels.AR_DRONE_2);
    }

    @Override
    public ArDrone2Factory factory() {
        return new ArDrone2Factory();
    }
}
