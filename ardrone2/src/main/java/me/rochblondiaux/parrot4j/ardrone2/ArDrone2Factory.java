package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.controller.DroneController;
import me.rochblondiaux.parrot4j.api.driver.DroneFactory;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ArDrone2Factory implements DroneFactory<ArDrone2> {

    private static final AtomicInteger ID = new AtomicInteger(0);

    @Override
    public ArDrone2 make(@NotNull InetAddress address) {
        final String name = "ar_drone_v2_%d".formatted(ID.getAndIncrement());
        final ArDrone2 drone = new ArDrone2(name);
        final DroneController controller = new ArDrone2Controller(address, drone);

        drone.controller(controller);
        return drone;
    }
}
