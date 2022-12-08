package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class WatchdogResetCommand extends ATCommand {

    public WatchdogResetCommand() {
        super("COMWDG");
    }

    @Override
    protected String build(int sequence) {
        return sequence + "";
    }
}
