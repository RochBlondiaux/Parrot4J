package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class CalibrateCommand extends ATCommand {

    private final int deviceNumber;

    public CalibrateCommand(int deviceNumber) {
        super("CALIB");
        this.deviceNumber = deviceNumber;
    }

    public CalibrateCommand() {
        this(0);
    }


    @Override
    protected String build(int sequence) {
        return "%d,%d".formatted(sequence, deviceNumber);
    }
}
