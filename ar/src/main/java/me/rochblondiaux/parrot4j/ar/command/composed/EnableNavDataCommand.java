package me.rochblondiaux.parrot4j.ar.command.composed;

import me.rochblondiaux.parrot4j.api.util.Preconditions;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class EnableNavDataCommand extends SetConfigValueCommand {

    public EnableNavDataCommand(AuthenticationData data) {
        super(data, ConfigurationKeys.GENERAL_NAV_DATA_DEMO, "TRUE");
    }

    @Override
    public int timeout() {
        return DEFAULT_NAVDATA_TIMEOUT;
    }

    @Override
    public void isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
        Preconditions.checkState(!data.onlyHeaderPresent(), "The nav data state is not sent yet");
    }
}
