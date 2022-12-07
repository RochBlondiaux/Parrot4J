package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class MiscCommand extends ATCommand {
    private final int param1;
    private final int param2;
    private final int param3;
    private final int param4;

    /**
     * @param seq    The sequence number of the command
     * @param param1 Undocumented parameter
     * @param param2 Undocumented parameter
     * @param param3 Undocumented parameter
     * @param param4 Undocumented parameter
     */
    public MiscCommand(int seq, int param1, int param2, int param3, int param4) {
        super(seq, "MISC");
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
    }

    /**
     * @param seq The sequence number of the command
     */
    public MiscCommand(int seq) {
        this(seq, 2, 20, 2000, 3000);
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d,%d,%d,%d", seq, param1, param2, param3, param4);
    }
}