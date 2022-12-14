package me.rochblondiaux.parrot4j.ar.command.composed;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.ar.command.Command;
import me.rochblondiaux.parrot4j.ar.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;

import java.util.List;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public class LoginCommand extends ComposedCommand {

    private final AuthenticationData data;

    @Override
    public List<Command> commands() {
        return List.of(
                new SetConfigValueCommand(data, ConfigurationKeys.CUSTOM_SESSION_ID, data.getSessionChecksum()),
                new SetConfigValueCommand(data, ConfigurationKeys.CUSTOM_PROFILE_ID, data.getProfileChecksum()),
                new SetConfigValueCommand(data, ConfigurationKeys.CUSTOM_APPLICATION_ID, data.getApplicationChecksum())
        );
    }
}
