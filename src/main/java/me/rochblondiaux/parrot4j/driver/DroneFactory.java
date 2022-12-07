package me.rochblondiaux.parrot4j.driver;

import me.rochblondiaux.parrot4j.drone.Drone;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneFactory<D extends Drone> {

    /**
     * Create a new drone instance.
     *
     * @param address The address of the drone.
     * @return The drone instance.
     */
    D make(@NotNull InetAddress address);
}
