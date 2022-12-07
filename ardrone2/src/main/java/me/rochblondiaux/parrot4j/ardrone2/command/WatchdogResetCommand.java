package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class WatchdogResetCommand extends ATCommand {

    /**
     * @param seq The sequence number of the command
     */
    public WatchdogResetCommand(int seq) {
        super(seq, "COMWDG");
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d", seq);
    }

}