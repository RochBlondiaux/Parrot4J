package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.drone.AbstractDrone;
import me.rochblondiaux.parrot4j.api.drone.DroneModels;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ArDrone2 extends AbstractDrone {

    public ArDrone2(String name) {
        super(name, DroneModels.AR_DRONE_2);
    }
}
