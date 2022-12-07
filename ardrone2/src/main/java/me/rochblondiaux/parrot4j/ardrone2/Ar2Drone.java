package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.drone.enums.DroneModels;
import me.rochblondiaux.parrot4j.api.drone.model.impl.AbstractDrone;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Ar2Drone extends AbstractDrone {

    public Ar2Drone(@NotNull InetAddress address) {
        super(address, DroneModels.AR_DRONE_2);
    }
}
