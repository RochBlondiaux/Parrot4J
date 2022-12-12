package me.rochblondiaux.parrot4j.ar.command.composed;

import me.rochblondiaux.parrot4j.ar.command.Command;
import me.rochblondiaux.parrot4j.ar.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ar.command.simple.SetControlDataCommand;
import me.rochblondiaux.parrot4j.ar.command.simple.UpdateConfigATCommand;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.model.ControlDataMode;

import java.util.List;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class SetConfigValueCommand extends ComposedCommand {

    private final AuthenticationData data;
    private final ConfigurationKeys key;
    private final Object value;

    public SetConfigValueCommand(AuthenticationData data, ConfigurationKeys key, Object value) {
        super();
        this.data = data;
        this.key = key;
        this.value = value;
    }

    public SetConfigValueCommand(AuthenticationData data) {
        this(data, null, null);
    }

    @Override
    public List<Command> commands() {
        assert key != null;
        assert value != null;
        return List.of(
                new SetControlDataCommand(ControlDataMode.RESET_ACK_FLAG),
                new UpdateConfigATCommand(data, key, value)
        );
    }
}
