package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class CalibrateCommand extends ATCommand {

    // The device ID
    private final int deviceNumber;

    /**
     * @param seq          The sequence number of the command
     * @param deviceNumber The device ID that must be calibrated
     */
    public CalibrateCommand(int seq, int deviceNumber) {
        super(seq, "CALIB");
        this.deviceNumber = deviceNumber;
    }

    /**
     * The magneto meter will be calibrated
     *
     * @param seq The sequence number of the command
     */
    public CalibrateCommand(int seq) {
        this(seq, 0);
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d", seq, deviceNumber);
    }
}