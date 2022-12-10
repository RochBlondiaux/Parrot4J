package me.rochblondiaux.parrot4j.api.drone.implementation;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.api.drone.DroneVersion;
import org.jetbrains.annotations.ApiStatus;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@ApiStatus.Internal
@ApiStatus.NonExtendable
@RequiredArgsConstructor(staticName = "of")
public class DroneVersionImpl implements DroneVersion {

    private final String firmware;
    private final String hardware;

    @Override
    public String firmware() {
        return firmware;
    }

    @Override
    public String hardware() {
        return hardware;
    }
}
