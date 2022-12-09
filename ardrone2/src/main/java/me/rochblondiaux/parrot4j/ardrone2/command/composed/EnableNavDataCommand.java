package me.rochblondiaux.parrot4j.ardrone2.command.composed;

import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
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
    public boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        return !data.onlyHeaderPresent();
    }
}
