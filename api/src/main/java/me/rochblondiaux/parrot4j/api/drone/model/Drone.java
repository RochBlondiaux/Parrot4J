package me.rochblondiaux.parrot4j.api.drone.model;

import me.rochblondiaux.parrot4j.api.drone.enums.AlertState;
import me.rochblondiaux.parrot4j.api.drone.enums.FlyingState;
import me.rochblondiaux.parrot4j.api.drone.enums.NavigationState;
import me.rochblondiaux.parrot4j.api.drone.enums.NavigationStateReason;
import me.rochblondiaux.parrot4j.api.model.Location;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface Drone {

    InetAddress address();

    String name();

    String serialNumber();

    DroneVersion version();

    DroneModel model();

    boolean online();

    byte batteryLevel();

    FlyingState flyingState();

    AlertState alertState();

    NavigationState navigationState();

    NavigationStateReason navigationStateReason();

    double altitude();

    Rotation rotation();

    Speed speed();

    @Nullable Location location();
}
