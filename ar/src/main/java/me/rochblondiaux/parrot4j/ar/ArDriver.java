package me.rochblondiaux.parrot4j.ar;

import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.driver.DroneController;
import me.rochblondiaux.parrot4j.api.driver.DroneDriver;
import me.rochblondiaux.parrot4j.api.driver.DroneFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ArDriver implements DroneDriver<ArDrone> {

    @Override
    public DroneFactory<ArDrone> factory() {
        return new ArFactory();
    }

    @Override
    public DroneController controller(@NotNull ClientOptions options, @NotNull ArDrone drone) {
        return new ArController(options, drone);
    }
}
