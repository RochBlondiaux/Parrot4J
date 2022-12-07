package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.api.drone.movements.Rotation;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class MoveCommand extends ATCommand {

    protected final int flag;
    protected final Rotation rotation;
    protected final float gaz;

    public MoveCommand(int seq, int flag, Rotation rotation, float gaz) {
        super(seq, "PCMD");
        this.flag = flag;
        this.rotation = rotation;
        this.gaz = gaz;
    }

    @Override
    protected String parametersToString() {
        return String.format("%d,%d,%d,%d,%d,%d", seq, flag, intOfFloat(rotation.roll()), intOfFloat(rotation.pitch()), intOfFloat(gaz), intOfFloat(rotation.yaw()));
    }
}
