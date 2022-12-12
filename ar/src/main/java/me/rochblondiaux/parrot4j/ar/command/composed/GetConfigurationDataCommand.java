package me.rochblondiaux.parrot4j.ar.command.composed;

import me.rochblondiaux.parrot4j.ar.command.Command;
import me.rochblondiaux.parrot4j.ar.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ar.command.simple.SetControlDataCommand;
import me.rochblondiaux.parrot4j.ar.model.ControlDataMode;

import java.util.List;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class GetConfigurationDataCommand implements ComposedCommand {
    @Override
    public List<Command> commands() {
        return List.of(
                new SetControlDataCommand(ControlDataMode.RESET_ACK_FLAG),
                new SetControlDataCommand(ControlDataMode.GET_CONFIGURATION_DATA)
        );
    }
}
