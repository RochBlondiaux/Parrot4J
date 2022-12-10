package me.rochblondiaux.parrot4j.api.driver;

import me.rochblondiaux.parrot4j.api.drone.Drone;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneFactory<D extends Drone> {

    @Nullable D make(InetAddress address);
}
