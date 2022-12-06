package me.rochblondiaux.parrot4j.commands.composed;

import me.rochblondiaux.parrot4j.commands.Command;
import me.rochblondiaux.parrot4j.commands.simple.SetConfigValueATCommand;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.LoginData;
import me.rochblondiaux.parrot4j.data.enums.LedAnimation;

import static me.rochblondiaux.parrot4j.helpers.BinaryDataHelper.getNormalizedIntValue;

public class PlayLedAnimationCommand extends SetConfigValueCommand {
    private final LedAnimation animation;

    private final float frequency;

    private final int durationSeconds;

    public PlayLedAnimationCommand(LoginData loginData, LedAnimation animation, float frequency, int durationSeconds) {
        super(loginData);

        this.animation = animation;
        this.frequency = frequency;
        this.durationSeconds = durationSeconds;
    }

    @Override
    protected Command getConfigValueCommand() {
        return new SetConfigValueATCommand(getLoginData(), DroneConfiguration.LED_ANIMATION_KEY, getAnimationValuesText());
    }

    private String getAnimationValuesText() {
        return String.format("%d,%d,%d", animation.getAnimationCode(), getNormalizedIntValue(frequency), durationSeconds);
    }
}