package me.rochblondiaux.parrot4j.ar.command.simple;


import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.ar.command.ATCommand;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class MoveCommand extends ATCommand {

    private final Rotation rotation;
    private final float gaz;

    public MoveCommand(Rotation rotation, float gaz) {
        super("PCMD");
        this.rotation = rotation;
        this.gaz = gaz;
    }

    @Override
    protected String build(int sequence) {
        return String.format("%d,%d,%d,%d,%d,%d", sequence, 1, intOfFloat(rotation.roll()), intOfFloat(rotation.pitch()), intOfFloat(gaz), intOfFloat(rotation.yaw()));
    }
}
