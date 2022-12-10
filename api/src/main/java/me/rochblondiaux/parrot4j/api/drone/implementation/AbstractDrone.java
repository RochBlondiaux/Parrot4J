package me.rochblondiaux.parrot4j.api.drone.implementation;

import me.rochblondiaux.parrot4j.api.drone.Drone;
import me.rochblondiaux.parrot4j.api.drone.DroneModel;
import me.rochblondiaux.parrot4j.api.drone.DroneVersion;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;
import org.jetbrains.annotations.ApiStatus;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@ApiStatus.Internal
public abstract class AbstractDrone implements Drone {

    protected String serialNumber;
    protected DroneVersion version;
    protected DroneModel model;
    protected int batteryLevel;
    protected Rotation rotation;
    protected Speed speed;
    protected float altitude;
    protected boolean flying;

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
    public int batteryLevel() {
        return batteryLevel;
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
    public float altitude() {
        return altitude;
    }

    @Override
    public boolean flying() {
        return flying;
    }
}
