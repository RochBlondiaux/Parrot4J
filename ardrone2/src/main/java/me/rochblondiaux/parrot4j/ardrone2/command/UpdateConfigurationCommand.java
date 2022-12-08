package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class UpdateConfigurationCommand extends ATCommand {

    private final String key;
    private final String value;

    public UpdateConfigurationCommand(@NotNull ConfigurationKeys key, @NotNull Object value) {
        super("CONFIG", true, 100);
        this.key = key.key();
        this.value = value.toString();
    }

    @Override
    protected String build(int sequence) {
        return "%d,\"%s\",\"%s\"".formatted(sequence, key, value);
    }
}
