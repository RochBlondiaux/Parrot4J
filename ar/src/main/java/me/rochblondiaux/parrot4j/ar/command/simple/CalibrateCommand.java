package me.rochblondiaux.parrot4j.ar.command.simple;

import me.rochblondiaux.parrot4j.ar.command.ATCommand;

/**
 * Parrot4J
 * 11/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class CalibrateCommand extends ATCommand {

    private final int deviceId;

    public CalibrateCommand(int deviceId) {
        super("CALIB");
        this.deviceId = deviceId;
    }

    public CalibrateCommand() {
        this(0);
    }

    @Override
    protected String build(int sequence) {
        return String.format("%d,%d", sequence, deviceId);
    }
}
