package me.rochblondiaux.parrot4j.ardrone2.events;

import me.rochblondiaux.parrot4j.api.event.Event;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public record DroneDataUpdateEvent(Ar2Drone drone, DroneData data) implements Event {
}
