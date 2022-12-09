package me.rochblondiaux.parrot4j.ardrone2.command.composed;

import me.rochblondiaux.parrot4j.ardrone2.command.Command;
import me.rochblondiaux.parrot4j.ardrone2.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.simple.SetControlDataCommand;
import me.rochblondiaux.parrot4j.ardrone2.model.ControlDataMode;

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
