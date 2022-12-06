package me.rochblondiaux.parrot4j.commands.composed;

import me.rochblondiaux.parrot4j.commands.ComposedCommand;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.NavData;

public abstract class UnconditionalComposedCommandAbstract implements ComposedCommand {
    @Override
    public int getTimeoutMillis() {
        return 0;
    }

    @Override
    public void checkSuccess(NavData navData, DroneConfiguration droneConfiguration) {
        // Nothing to do here
    }
}
