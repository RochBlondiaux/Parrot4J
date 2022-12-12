package me.rochblondiaux.parrot4j.ar.command.composed;

import me.rochblondiaux.parrot4j.api.drone.DroneModel;
import me.rochblondiaux.parrot4j.api.drone.DroneVersion;
import me.rochblondiaux.parrot4j.api.util.Preconditions;
import me.rochblondiaux.parrot4j.api.util.VersionUtil;
import me.rochblondiaux.parrot4j.ar.ArDrone;
import me.rochblondiaux.parrot4j.ar.command.Command;
import me.rochblondiaux.parrot4j.ar.command.ComposedCommand;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import me.rochblondiaux.parrot4j.ar.model.AuthenticationData;
import me.rochblondiaux.parrot4j.ar.model.VideoCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tinylog.Logger;

import java.util.List;
import java.util.Objects;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class InitConfigurationCommand extends ComposedCommand {

    public static final String MIN_FIRMWARE_VERSION = "1.6.4";

    private final AuthenticationData data;
    private final VideoCodec codec;
    private final ArDrone drone;

    public InitConfigurationCommand(@NotNull ArDrone drone, @NotNull AuthenticationData data, @NotNull VideoCodec codec) {
        super();
        this.data = data;
        this.codec = codec;
        this.drone = drone;
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
    public void isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
        Preconditions.checkNotNull(configuration, "Configuration is null");
        Preconditions.checkNotNull(data, "Navigation data is null");

        String firmwareVersion = configuration.get(ConfigurationKeys.GENERAL_NUM_VERSION_SOFT);
        String hardwareVersion = configuration.get(ConfigurationKeys.GENERAL_NUM_VERSION_MB);
        String configurationVersion = configuration.get(ConfigurationKeys.GENERAL_NUM_VERSION_CONFIG);
        Preconditions.checkState(VersionUtil.compareVersions(firmwareVersion, MIN_FIRMWARE_VERSION) >= 0, "The firmware version used is too old");

        String sessionId = configuration.get(ConfigurationKeys.CUSTOM_SESSION_ID);
        String profileId = configuration.get(ConfigurationKeys.CUSTOM_PROFILE_ID);
        String applicationId = configuration.get(ConfigurationKeys.CUSTOM_APPLICATION_ID);

        Preconditions.checkState(Objects.equals(this.data.getSessionChecksum(), sessionId),
                String.format("The session ID was not set to '%s', but was '%s'", this.data.getSessionChecksum(), sessionId));
        Preconditions.checkState(Objects.equals(this.data.getProfileChecksum(), profileId), "The profile ID was not set");
        Preconditions.checkState(Objects.equals(this.data.getApplicationChecksum(), applicationId), "The application ID was not set");

        String videoCodecCode = configuration.get(ConfigurationKeys.VIDEO_CODEC);
        Preconditions.checkState(Objects.equals(String.valueOf(this.codec.getCodecValue()), videoCodecCode), "The video codec was not set");

        // Update drone
        final DroneVersion version = DroneVersion.of(firmwareVersion, hardwareVersion);
        final DroneModel model = VersionUtil.fromVersionNumber("AR", configurationVersion);
        this.drone.initInformation(version, model);
        Logger.info("Drone version: {}, model: {}", version, model);
    }
}
