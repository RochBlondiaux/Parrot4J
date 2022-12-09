package me.rochblondiaux.parrot4j.ardrone2.command.composed;

import me.rochblondiaux.parrot4j.ardrone2.command.Command;
import me.rochblondiaux.parrot4j.ardrone2.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.simple.SetConfigValueATCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.simple.SetControlDataCommand;
import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ardrone2.model.ControlDataMode;

import java.util.List;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class SetConfigValueCommand implements ComposedCommand {

    private final AuthenticationData data;
    private final ConfigurationKeys key;
    private final Object value;

    public SetConfigValueCommand(AuthenticationData data, ConfigurationKeys key, Object value) {
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
                new SetConfigValueATCommand(data, key, value)
        );
    }
}
