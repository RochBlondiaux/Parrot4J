package me.rochblondiaux.parrot4j.ar.command.simple;

import me.rochblondiaux.parrot4j.api.util.Preconditions;
import me.rochblondiaux.parrot4j.ar.command.ATCommand;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class UpdateConfigATCommand extends ATCommand {

    private final AuthenticationData authData;
    private final ConfigurationKeys key;
    private final Object value;

    public UpdateConfigATCommand(AuthenticationData authData, ConfigurationKeys key, Object value) {
        super("CONFIG");
        this.authData = authData;
        this.key = key;
        this.value = value;
    }

    @Override
    protected String build(int sequence) {
        return String.format("%d,\"%s\",\"%s\"", sequence, key.key(), value);
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
    public void isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
        Preconditions.checkState(data.state().controlReceived(), "The command config ACK flag was not set");
    }
}
