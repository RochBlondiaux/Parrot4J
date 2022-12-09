package me.rochblondiaux.parrot4j.api.driver;

import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.drone.factory.DroneFactory;
import me.rochblondiaux.parrot4j.api.drone.model.Drone;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DroneDriver<D extends Drone> {

    DroneFactory<D> factory(@NotNull ClientOptions options);

    DroneController controller(@NotNull ClientOptions options, @NotNull D drone);
}
