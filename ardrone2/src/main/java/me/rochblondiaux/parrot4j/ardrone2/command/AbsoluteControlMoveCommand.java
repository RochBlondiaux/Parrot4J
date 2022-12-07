package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.api.drone.movements.Rotation;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class AbsoluteControlMoveCommand extends MoveCommand {

    private final float psi;
    private final float accPsi;

    public AbsoluteControlMoveCommand(int seq, int flag, Rotation rotation, float gaz, float psi, float accPsi) {
        super(seq, flag, rotation, gaz);
        super.setCommandName("PCMD_MAG");
        this.psi = psi;
        this.accPsi = accPsi;
    }

    /**
     *
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d,%d,%d,%d,%d,%d,%d", seq, flag,
                intOfFloat(rotation.roll()), intOfFloat(rotation.pitch()), intOfFloat(gaz), intOfFloat(rotation.yaw()), intOfFloat(psi), intOfFloat(accPsi));
    }
}
