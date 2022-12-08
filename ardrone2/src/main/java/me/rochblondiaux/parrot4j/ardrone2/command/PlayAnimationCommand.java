package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.DroneAnimation;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class PlayAnimationCommand extends ATCommand {

    private final DroneAnimation animation;
    private final int duration;

    public PlayAnimationCommand(DroneAnimation animation, int duration) {
        super("ANIM", animation.getDuration());
        this.animation = animation;
        this.duration = duration;
    }

    @Override
    protected String build(int sequence) {
        return "%d,%d,%d".formatted(sequence, animation.getId(), duration);
    }
}
