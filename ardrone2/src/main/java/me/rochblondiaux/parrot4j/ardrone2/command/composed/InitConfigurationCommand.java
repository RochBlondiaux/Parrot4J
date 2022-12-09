package me.rochblondiaux.parrot4j.ardrone2.command.composed;

import me.rochblondiaux.parrot4j.ardrone2.command.Command;
import me.rochblondiaux.parrot4j.ardrone2.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ardrone2.model.VideoCodec;
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
    public boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        return true;
    }
}
