package me.rochblondiaux.parrot4j.ardrone2.command.simple;

import me.rochblondiaux.parrot4j.api.event.Rotation;
import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FlightMoveCommand extends ATCommand {

    private final Rotation rotation;
    private final float gaz;

    public FlightMoveCommand(Rotation rotation, float gaz) {
        super("PCMD");
        this.rotation = rotation;
        this.gaz = gaz;
    }

    @Override
    protected String build(int sequence) {
        return "%d,%d,%d,%d,%d,%d".formatted(sequence, 1, intOfFloat(rotation.roll()), intOfFloat(rotation.pitch()), intOfFloat(gaz), intOfFloat(rotation.yaw()));
    }
}
