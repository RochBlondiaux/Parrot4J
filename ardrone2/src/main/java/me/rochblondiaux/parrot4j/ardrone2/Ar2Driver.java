package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.driver.DroneDriver;
import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.drone.factory.DroneFactory;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import me.rochblondiaux.parrot4j.ardrone2.factory.Ar2Factory;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Ar2Driver implements DroneDriver<Ar2Drone> {

    @Override
    public DroneFactory<Ar2Drone> factory() {
        return new Ar2Factory();
    }

    @Override
    public DroneController controller() {
        return new Ar2Controller();
    }
}
