package me.rochblondiaux.parrot4j.drone.state;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public enum NavigationStateReason {
    REQUESTED,
    CONNECTION_LOST,
    BATTERY_LOW,
    FINISHED,
    STOPPED,
    DISABLED,
    ENABLED
}
