package me.rochblondiaux.parrot4j.api.drone.model.impl;

import me.rochblondiaux.parrot4j.api.drone.model.DroneVersion;
import org.jetbrains.annotations.ApiStatus;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@ApiStatus.Internal
public record DroneVersionImpl(String firmware, String hardware) implements DroneVersion {
}
