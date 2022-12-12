package me.rochblondiaux.parrot4j.ar.command.simple;


import me.rochblondiaux.parrot4j.ar.command.ATCommand;
import me.rochblondiaux.parrot4j.ar.model.FlightMode;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class SetFlightModeCommand extends ATCommand {

    private final FlightMode flightMode;

    public SetFlightModeCommand(FlightMode flightMode) {
        super("REF");
        this.flightMode = flightMode;
    }

    @Override
    protected String build(int sequence) {
        return String.format("%d,%d", sequence, flightMode.getCommandCode());
    }
}
