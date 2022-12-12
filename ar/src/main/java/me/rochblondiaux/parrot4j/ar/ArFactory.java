package me.rochblondiaux.parrot4j.ar;

import me.rochblondiaux.parrot4j.api.driver.DroneFactory;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ArFactory implements DroneFactory<ArDrone> {

    @Override
    public @Nullable ArDrone make(InetAddress address) {
        return new ArDrone();
    }

}
