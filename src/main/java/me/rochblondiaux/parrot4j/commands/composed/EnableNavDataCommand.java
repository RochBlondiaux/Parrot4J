package me.rochblondiaux.parrot4j.commands.composed;

import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.LoginData;
import me.rochblondiaux.parrot4j.data.NavData;

import static com.google.common.base.Preconditions.checkState;

public class EnableNavDataCommand extends SetConfigValueCommand {
    public EnableNavDataCommand(LoginData loginData) {
        super(loginData, DroneConfiguration.ENABLE_NAV_DATA_KEY, "TRUE");
    }

    @Override
    public int getTimeoutMillis() {
        return DEFAULT_NAVDATA_TIMEOUT;
    }

    @Override
    public void checkSuccess(NavData navData, DroneConfiguration droneConfiguration) {
        checkState(!navData.isOnlyHeaderPresent(), "The nav data state is not sent yet");
    }
}