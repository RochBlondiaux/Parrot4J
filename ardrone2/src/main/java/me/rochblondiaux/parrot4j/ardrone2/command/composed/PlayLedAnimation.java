package me.rochblondiaux.parrot4j.ardrone2.command.composed;

import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ardrone2.model.LedAnimation;
import me.rochblondiaux.parrot4j.ardrone2.utils.BinaryDataHelper;

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
