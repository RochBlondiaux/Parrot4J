package me.rochblondiaux.parrot4j.api.drone.model.impl;

import me.rochblondiaux.parrot4j.api.drone.enums.AlertState;
import me.rochblondiaux.parrot4j.api.drone.enums.FlyingState;
import me.rochblondiaux.parrot4j.api.drone.enums.NavigationState;
import me.rochblondiaux.parrot4j.api.drone.enums.NavigationStateReason;
import me.rochblondiaux.parrot4j.api.drone.model.Drone;
import me.rochblondiaux.parrot4j.api.drone.model.DroneModel;
import me.rochblondiaux.parrot4j.api.drone.model.DroneVersion;
import me.rochblondiaux.parrot4j.api.model.Location;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@ApiStatus.Internal
public abstract class AbstractDrone implements Drone {

    protected final InetAddress address;
    protected final DroneModel model;
    protected String name;
    protected String serialNumber;
    protected DroneVersion version;
    protected boolean connected;
    protected double altitude;
    protected byte batteryLevel;
    protected FlyingState flyingState;
    protected NavigationState navigationState;
    protected NavigationStateReason navigationStateReason;
    protected AlertState alertState;
    protected Location location;
    protected Rotation rotation;
    protected Speed speed;

    public AbstractDrone(@NotNull InetAddress address, @NotNull DroneModel model) {
        this.address = address;
        this.model = model;
    }

    @Override
    public InetAddress address() {
        return address;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String serialNumber() {
        return serialNumber;
    }

    @Override
    public DroneVersion version() {
        return version;
    }

    @Override
    public DroneModel model() {
        return model;
    }

    @Override
    public boolean online() {
        return connected;
    }

    @Override
    public byte batteryLevel() {
        return batteryLevel;
    }

    @Override
    public FlyingState flyingState() {
        return flyingState;
    }

    @Override
    public AlertState alertState() {
        return alertState;
    }

    @Override
    public NavigationState navigationState() {
        return navigationState;
    }

    @Override
    public NavigationStateReason navigationStateReason() {
        return navigationStateReason;
    }

    @Override
    public double altitude() {
        return altitude;
    }

    @Override
    public Rotation rotation() {
        return rotation;
    }

    @Override
    public Speed speed() {
        return speed;
    }

    @Override
    public @Nullable Location location() {
        return location;
    }
}
