package me.rochblondiaux.parrot4j.ardrone2.command;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class UpdateConfigurationCommand extends ATCommand {

    private final String key;
    private final String value;

    public UpdateConfigurationCommand(@NotNull ConfigurationKeys key, @NotNull Object value) {
        super("CONFIG", true);
        this.key = key.key();
        this.value = value.toString();
    }

    @Override
    protected String build(int sequence) {
        return "%d,\"%s\",\"%s\"".formatted(sequence, key, value);
    }

    @Override
    public int timeout() {
        return 100;
    }

    @Override
    public boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        if (data.state().controlReceived())
            return true;
        log.warn("The command config ACK flag was not set");
        return false;
    }
}
