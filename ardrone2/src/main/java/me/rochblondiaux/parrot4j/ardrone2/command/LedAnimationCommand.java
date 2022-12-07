package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.DroneLedAnimation;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class LedAnimationCommand extends ATCommand {

    private final DroneLedAnimation animation;
    private final float frequency;
    private final int duration;


    /**
     * @param seq       The sequence number of the command
     * @param animation The ID of the animation
     * @param frequency The frequency of the animation [Hz]
     * @param duration  The duration of the animation [s]
     */
    public LedAnimationCommand(int seq, DroneLedAnimation animation, float frequency, int duration) {
        super(seq, "LED");
        this.animation = animation;
        this.frequency = frequency;
        this.duration = duration;
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d,%d,%d", seq, animation.id(), intOfFloat(frequency), duration);
    }
}
