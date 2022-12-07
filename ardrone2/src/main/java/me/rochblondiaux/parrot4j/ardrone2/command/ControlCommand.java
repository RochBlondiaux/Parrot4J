package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ControlCommand extends ATCommand {
    private final int param1;
    private final int param2;

    /**
     * @param seq The sequence number of the command
     */
    public ControlCommand(int seq) {
        this(seq, 5, 0);
    }

    /**
     * @param seq    The sequence number of the command
     * @param param1 Undocumented parameter
     * @param param2 Undocumented parameter
     */
    public ControlCommand(int seq, int param1, int param2) {
        super(seq, "CTRL");

        this.param1 = param1;
        this.param2 = param2;
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d,%d", seq, param1, param2);
    }

}