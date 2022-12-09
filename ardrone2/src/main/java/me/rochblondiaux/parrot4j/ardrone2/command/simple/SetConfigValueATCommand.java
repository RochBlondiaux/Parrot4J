package me.rochblondiaux.parrot4j.ardrone2.command.simple;

import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;
import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class SetConfigValueATCommand extends ATCommand {

    private final AuthenticationData authData;
    private final ConfigurationKeys key;
    private final Object value;

    public SetConfigValueATCommand(AuthenticationData authData, ConfigurationKeys key, Object value) {
        super("CONFIG");
        this.authData = authData;
        this.key = key;
        this.value = value;
    }

    @Override
    protected String build(int sequence) {
        return "%d,\"%s\",\"%s\"".formatted(sequence, key.key(), value);
    }

    @Override
    public @Nullable String preparationCommandText(int sequenceNumber) {
        return String.format("AT*CONFIG_IDS=%d,\"%s\",\"%s\",\"%s\"", sequenceNumber, authData.getSessionChecksum(), authData.getProfileChecksum(), authData.getApplicationChecksum());
    }

    @Override
    public int timeout() {
        return DEFAULT_NAVDATA_TIMEOUT;
    }

    @Override
    public boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        return data.state().controlReceived();
    }
}
