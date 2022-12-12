package me.rochblondiaux.parrot4j.ar;

import me.rochblondiaux.parrot4j.api.drone.implementation.AbstractDrone;
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
public class ArDrone extends AbstractDrone {

    private NavigationData navigationData;
    private final DroneConfiguration configuration;

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
}
