package me.rochblondiaux.parrot4j.ar.command;

import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface Command {

    int NO_TIMEOUT = 0;

    int DEFAULT_NAVDATA_TIMEOUT = 100;

    int DEFAULT_CONFIGURATION_TIMEOUT = 1250;
    AtomicInteger ID = new AtomicInteger(0);

    default int getId() {
        return ID.getAndIncrement();
    }

    default int timeout() {
        return -1;
    }

    default void isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
    }

    CompletableFuture<Void> callback();

}
