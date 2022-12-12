package me.rochblondiaux.parrot4j.ar.command.simple;

import me.rochblondiaux.parrot4j.ar.command.ATCommand;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FlatTrimCommand extends ATCommand {

    public FlatTrimCommand() {
        super("FTRIM");
    }

    @Override
    protected String build(int sequence) {
        return sequence + "";
    }
}
