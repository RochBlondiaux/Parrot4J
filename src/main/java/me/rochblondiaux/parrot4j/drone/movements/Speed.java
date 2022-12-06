package me.rochblondiaux.parrot4j.drone.movements;

import lombok.With;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@With
public record Speed(double vx, double vy, double vz) {

}