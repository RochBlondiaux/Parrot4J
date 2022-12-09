package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.driver.DroneDriver;
import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.drone.factory.DroneFactory;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import me.rochblondiaux.parrot4j.ardrone2.controller.CommandSender;
import me.rochblondiaux.parrot4j.ardrone2.factory.Ar2Factory;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Ar2Driver implements DroneDriver<Ar2Drone> {

    @Override
    public DroneFactory<Ar2Drone> factory(@NotNull ClientOptions options) {
        return new Ar2Factory();
    }

    @Override
    public DroneController controller(@NotNull ClientOptions options, @NotNull Ar2Drone drone) {
        final CommandSender sender = new CommandSender(options);
        Ar2Controller controller = new Ar2Controller(sender, drone, options);
        sender.setController(controller);
        return controller;
    }
}
