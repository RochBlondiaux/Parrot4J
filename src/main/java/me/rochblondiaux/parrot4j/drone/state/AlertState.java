package me.rochblondiaux.parrot4j.drone.state;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public enum AlertState {
    NONE,
    USER_EMERGENCY,
    CUT_OUT,
    BATTERY_CRITICAL,
    BATTERY_LOW,
    ANGLE_CRITICAL
}
