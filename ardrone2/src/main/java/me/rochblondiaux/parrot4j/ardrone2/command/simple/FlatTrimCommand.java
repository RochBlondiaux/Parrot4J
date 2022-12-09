package me.rochblondiaux.parrot4j.ardrone2.command.simple;

import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;

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
