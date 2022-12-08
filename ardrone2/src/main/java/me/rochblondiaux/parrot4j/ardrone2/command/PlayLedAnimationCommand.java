package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.LedAnimation;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class PlayLedAnimationCommand extends ATCommand {

    private final LedAnimation animation;
    private final float frequency;
    private final int durationInSecs;

    public PlayLedAnimationCommand(LedAnimation animation, float frequency, int durationInSecs) {
        super("LED", durationInSecs * 1000);
        this.animation = animation;
        this.frequency = frequency;
        this.durationInSecs = durationInSecs;
    }

    @Override
    protected String build(int sequence) {
        return "%d,%d,%d,%d".formatted(sequence, animation.getId(), intOfFloat(frequency), durationInSecs);
    }
}
