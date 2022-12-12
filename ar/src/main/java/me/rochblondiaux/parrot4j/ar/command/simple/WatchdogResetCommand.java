package me.rochblondiaux.parrot4j.ar.command.simple;

import me.rochblondiaux.parrot4j.ar.command.ATCommand;

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
