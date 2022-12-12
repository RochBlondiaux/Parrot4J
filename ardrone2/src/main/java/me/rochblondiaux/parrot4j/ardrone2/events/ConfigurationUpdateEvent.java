package me.rochblondiaux.parrot4j.ardrone2.events;

import me.rochblondiaux.parrot4j.api.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public record ConfigurationUpdateEvent(@NotNull DroneConfiguration configuration) implements Event {
}
