package me.rochblondiaux.parrot4j.drone.movements;

import lombok.With;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@With
public record Rotation(double roll, double pitch, double yaw) {

    /***
     * Orientation of drone in a 3D plane
     * @param roll Angle left/right in radians
     * @param pitch Angle forward/backward in radians
     * @param yaw Rotation relative to takeoff orientation in radians
     */
    public Rotation {
    }
}