package me.rochblondiaux.parrot4j.commands.simple;

import me.rochblondiaux.parrot4j.data.enums.FlightMode;

public class FlightModeCommand extends ATCommandAbstract {
    private final FlightMode flightMode;

    public FlightModeCommand(FlightMode flightMode) {
        super(false);
        this.flightMode = flightMode;
    }

    @Override
    protected String getCommand(int sequenceNumber) {
        return String.format("AT*REF=%d,%d", sequenceNumber, flightMode.getCommandCode());
    }
}
