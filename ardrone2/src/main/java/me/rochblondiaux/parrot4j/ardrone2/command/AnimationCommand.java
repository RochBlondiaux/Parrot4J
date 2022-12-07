package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.animation.DroneAnimation;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class AnimationCommand extends ATCommand {

    private final DroneAnimation animation;
    private final int duration;

    /**
     * @param seq       The sequence number of the command
     * @param animation The ID of the animation
     * @param duration  The duration of the animation [s]
     */
    public AnimationCommand(int seq, DroneAnimation animation, int duration) {
        super(seq, "ANIM");

        this.animation = animation;
        this.duration = duration;
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d,%d", seq, animation.id(), duration);
    }

}
