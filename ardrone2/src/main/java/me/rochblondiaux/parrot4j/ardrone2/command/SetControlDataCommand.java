package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.ControlDataMode;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class SetControlDataCommand extends ATCommand {

    private final ControlDataMode mode;
    private final int parameter2;

    public SetControlDataCommand(ControlDataMode mode, int parameter2) {
        super("CTRL", mode.getDuration());
        this.mode = mode;
        this.parameter2 = parameter2;
    }

    public SetControlDataCommand(ControlDataMode mode) {
        this(mode, 0);
    }

    @Override
    protected String build(int sequence) {
        return "AT*CTRL=%d,%d,%d".formatted(sequence, mode.getControlModeCode(), parameter2);
    }
}
