package me.rochblondiaux.parrot4j.commands.composed;

import me.rochblondiaux.parrot4j.commands.Command;
import me.rochblondiaux.parrot4j.commands.simple.SetConfigValueATCommand;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.LoginData;
import me.rochblondiaux.parrot4j.data.enums.FlightAnimation;

public class PlayFlightAnimationCommand extends SetConfigValueCommand {
    private final FlightAnimation animation;

    public PlayFlightAnimationCommand(LoginData loginData, FlightAnimation animation) {
        super(loginData);
        this.animation = animation;
    }

    @Override
    protected Command getConfigValueCommand() {
        return new SetConfigValueATCommand(getLoginData(), DroneConfiguration.FLIGHT_ANIMATION_KEY, getAnimationValuesText());
    }

    private String getAnimationValuesText() {
        return String.format("%d,%d", animation.getCommandCode(), animation.getTimeout());
    }
}