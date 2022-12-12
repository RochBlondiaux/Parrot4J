package me.rochblondiaux.parrot4j.ar.command.composed;

import me.rochblondiaux.parrot4j.ar.command.Command;
import me.rochblondiaux.parrot4j.ar.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.model.VideoCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class InitConfigurationCommand implements ComposedCommand {

    private final AuthenticationData data;
    private final VideoCodec codec;

    public InitConfigurationCommand(AuthenticationData data, VideoCodec codec) {
        this.data = data;
        this.codec = codec;
    }

    @Override
    public List<Command> commands() {
        return List.of(
                new LoginCommand(data),
                new EnableNavDataCommand(data),
                new SetConfigValueCommand(data, ConfigurationKeys.VIDEO_CODEC, codec.getCodecValue()),
                new GetConfigurationDataCommand()
        );
    }

    @Override
    public boolean isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
        return true;
    }
}
