package me.rochblondiaux.parrot4j.ardrone2.data;

import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@FunctionalInterface
public interface DataUpdateListener {

    void onDataUpdate(@NotNull DroneData droneData);
}
