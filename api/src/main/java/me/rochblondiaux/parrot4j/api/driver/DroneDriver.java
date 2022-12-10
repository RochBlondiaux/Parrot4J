package me.rochblondiaux.parrot4j.api.driver;

import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.drone.Drone;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneDriver<D extends Drone> {

    DroneFactory<D> factory();

    DroneController controller(@NotNull ClientOptions options, @NotNull D drone);
}
