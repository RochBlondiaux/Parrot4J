package me.rochblondiaux.parrot4j.api.drone.factory;

import me.rochblondiaux.parrot4j.api.drone.model.Drone;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneFactory<D extends Drone> {

    @NotNull D make(InetAddress address);

}
