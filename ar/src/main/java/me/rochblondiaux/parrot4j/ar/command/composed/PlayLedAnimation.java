package me.rochblondiaux.parrot4j.ar.command.composed;

import me.rochblondiaux.parrot4j.api.util.BinaryDataHelper;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.model.LedAnimation;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class PlayLedAnimation extends SetConfigValueCommand {

    public PlayLedAnimation(AuthenticationData data, LedAnimation animation, float frequency, int durationInSeconds) {
        super(data, ConfigurationKeys.LED_ANIM, String.format("%d,%d,%d", animation.getId(), BinaryDataHelper.getNormalizedIntValue(frequency), durationInSeconds));
    }

}
