package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.FlightMode;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FlightModeCommand extends ATCommand {

    private final FlightMode mode;

    public FlightModeCommand(FlightMode mode) {
        super("REF");
        this.mode = mode;
    }

    @Override
    protected String build(int sequence) {
        return "%d,%d".formatted(sequence, mode.getCommandCode());
    }
}
