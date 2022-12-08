package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.api.model.Rotation;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FlightMoveCommand extends ATCommand {

    private final Rotation rotation;
    private final float gaz;
    private final int flag;


    public FlightMoveCommand(Rotation rotation, float gaz, int flag) {
        super("PCMD");
        this.rotation = rotation;
        this.gaz = gaz;
        this.flag = flag;
    }

    public FlightMoveCommand(Rotation rotation, float gaz) {
        this(rotation, gaz, 1);
    }


    @Override
    protected String build(int sequence) {
        return "%d,%d,%d,%d,%d,%d".formatted(sequence, flag, intOfFloat(rotation.roll()), intOfFloat(rotation.pitch()), intOfFloat(gaz), intOfFloat(rotation.yaw()));
    }
}
