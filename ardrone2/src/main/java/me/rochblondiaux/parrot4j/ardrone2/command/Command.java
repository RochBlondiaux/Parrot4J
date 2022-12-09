package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface Command {

    int NO_TIMEOUT = 0;

    int DEFAULT_NAVDATA_TIMEOUT = 100;

    int DEFAULT_CONFIGURATION_TIMEOUT = 1250;

    default int timeout() {
        return -1;
    }

    default boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        return true;
    }
}
