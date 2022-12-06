package me.rochblondiaux.parrot4j.drone;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.Drone;
import me.rochblondiaux.parrot4j.drone.movements.Location;
import me.rochblondiaux.parrot4j.drone.movements.Rotation;
import me.rochblondiaux.parrot4j.drone.movements.Speed;
import me.rochblondiaux.parrot4j.drone.state.AlertState;
import me.rochblondiaux.parrot4j.drone.state.FlyingState;
import me.rochblondiaux.parrot4j.drone.state.NavigationState;
import me.rochblondiaux.parrot4j.drone.state.NavigationStateReason;
import org.jetbrains.annotations.ApiStatus;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@ApiStatus.Internal
@RequiredArgsConstructor
public abstract class AbstractDrone implements Drone {

    protected final String name;
    protected final DroneModel model;
    protected final String firmwareVersion;
    protected final String hardwareVersion;

    protected byte batteryLevel;
    protected boolean online;
    protected FlyingState flyingState;
    protected AlertState alertState;
    protected NavigationState navigationState;
    protected NavigationStateReason navigationStateReason;
    protected double altitude;
    protected Rotation rotation;
    protected Location location;
    protected Speed speed;

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean online() {
        return online;
    }

    @Override
    public DroneModel model() {
        return model;
    }

    @Override
    public String firmwareVersion() {
        return firmwareVersion;
    }

    @Override
    public String hardwareVersion() {
        return hardwareVersion;
    }

    @Override
    public byte batteryLevel() {
        return batteryLevel;
    }

    @Override
    public void batteryLevel(byte batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public FlyingState flyingState() {
        return flyingState;
    }

    @Override
    public void flyingState(FlyingState flyingState) {
        this.flyingState = flyingState;
    }

    @Override
    public AlertState alertState() {
        return alertState;
    }

    @Override
    public void alertState(AlertState alertState) {
        this.alertState = alertState;
    }

    @Override
    public NavigationState navigationState() {
        return navigationState;
    }

    @Override
    public void navigationState(NavigationState navigationState) {
        this.navigationState = navigationState;
    }

    @Override
    public NavigationStateReason navigationStateReason() {
        return navigationStateReason;
    }

    @Override
    public void navigationStateReason(NavigationStateReason navigationStateReason) {
        this.navigationStateReason = navigationStateReason;
    }

    @Override
    public double altitude() {
        return altitude;
    }

    @Override
    public void altitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public Rotation rotation() {
        return rotation;
    }

    @Override
    public void rotation(Rotation rotation) {
        this.rotation = rotation;
    }

    @Override
    public Speed speed() {
        return speed;
    }

    @Override
    public void speed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public Location location() {
        return location;
    }

    @Override
    public void location(Location location) {
        this.location = location;
    }
}
