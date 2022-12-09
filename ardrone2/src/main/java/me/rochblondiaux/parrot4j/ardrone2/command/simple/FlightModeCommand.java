package me.rochblondiaux.parrot4j.ardrone2.command.simple;

import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;
import me.rochblondiaux.parrot4j.ardrone2.model.FlightMode;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FlightModeCommand extends ATCommand {

    private final FlightMode flightMode;

    public FlightModeCommand(FlightMode flightMode) {
        super("REF");
        this.flightMode = flightMode;
    }

    @Override
    protected String build(int sequence) {
        return "%d,%d".formatted(sequence, flightMode.getCommandCode());
    }
}
