package me.rochblondiaux.parrot4j.ar;

import me.rochblondiaux.parrot4j.api.drone.Drone;
import me.rochblondiaux.parrot4j.api.drone.DroneModel;
import me.rochblondiaux.parrot4j.api.drone.DroneVersion;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ArDrone implements Drone {

    private NavigationData navigationData;
    private final DroneConfiguration configuration;
    private DroneVersion version;
    private DroneModel model;

    public ArDrone() {
        this.navigationData = null;
        this.configuration = new DroneConfiguration();
    }

    public DroneConfiguration configuration() {
        return configuration;
    }

    @ApiStatus.Internal
    public void updateNavigationData(@NotNull NavigationData navigationData) {
        this.navigationData = navigationData;
    }

    public @Nullable NavigationData navigationData() {
        return navigationData;
    }

    @Override
    public String serialNumber() {
        return configuration.get(ConfigurationKeys.GENERAL_DRONE_SERIAL);
    }

    @Override
    public @Nullable DroneVersion version() {
        return version;
    }

    @Override
    public @Nullable DroneModel model() {
        return model;
    }

    @Override
    public int batteryLevel() {
        return navigationData.battery();
    }

    @Override
    public Rotation rotation() {
        return navigationData.rotation();
    }

    @Override
    public Speed speed() {
        return navigationData.speed();
    }

    @Override
    public float altitude() {
        return navigationData.battery();
    }

    @Override
    public boolean flying() {
        return navigationData.state().flying();
    }

    @ApiStatus.Internal
    public void initInformation(@NotNull DroneVersion version, @NotNull DroneModel model) {
        this.version = version;
        this.model = model;
    }
}
