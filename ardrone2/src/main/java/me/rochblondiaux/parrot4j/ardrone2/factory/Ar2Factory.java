package me.rochblondiaux.parrot4j.ardrone2.factory;

import me.rochblondiaux.parrot4j.api.drone.factory.DroneFactory;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Ar2Factory implements DroneFactory<Ar2Drone> {

    @Override
    public @NotNull Ar2Drone make(InetAddress address) {
        return new Ar2Drone(address);
    }
}
