package me.rochblondiaux.parrot4j;

import me.rochblondiaux.parrot4j.drone.DroneModel;
import me.rochblondiaux.parrot4j.drone.movements.Location;
import me.rochblondiaux.parrot4j.drone.movements.Rotation;
import me.rochblondiaux.parrot4j.drone.movements.Speed;
import me.rochblondiaux.parrot4j.drone.state.AlertState;
import me.rochblondiaux.parrot4j.drone.state.FlyingState;
import me.rochblondiaux.parrot4j.drone.state.NavigationState;
import me.rochblondiaux.parrot4j.drone.state.NavigationStateReason;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface Drone {

    String name();

    boolean online();

    /* Version */
    DroneModel model();

    String firmwareVersion();

    String hardwareVersion();

    /* Status */
    byte batteryLevel();

    void batteryLevel(byte batteryLevel);

    FlyingState flyingState();

    void flyingState(FlyingState flyingState);

    AlertState alertState();

    void alertState(AlertState alertState);

    NavigationState navigationState();

    void navigationState(NavigationState navigationState);

    NavigationStateReason navigationStateReason();

    void navigationStateReason(NavigationStateReason navigationStateReason);


    /* Movements */
    double altitude();

    void altitude(double altitude);

    Rotation rotation();

    void rotation(Rotation rotation);

    Speed speed();

    void speed(Speed speed);

    Location location();

    void location(Location location);
}
