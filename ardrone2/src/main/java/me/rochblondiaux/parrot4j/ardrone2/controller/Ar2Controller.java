package me.rochblondiaux.parrot4j.ardrone2.controller;

import lombok.Getter;
import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.model.Location;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.network.FTPConnection;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.command.*;
import me.rochblondiaux.parrot4j.ardrone2.configuration.ConfigurationUpdater;
import me.rochblondiaux.parrot4j.ardrone2.data.DataUpdater;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;
import me.rochblondiaux.parrot4j.ardrone2.model.ControlDataMode;
import me.rochblondiaux.parrot4j.ardrone2.model.FlightMode;
import me.rochblondiaux.parrot4j.ardrone2.model.VideoCodec;
import me.rochblondiaux.parrot4j.ardrone2.utils.VersionUtil;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Getter
public class Ar2Controller implements DroneController {

    private static final String MIN_FIRMWARE_VERSION = "1.6.4";

    private final CommandSender sender;
    private final Ar2Drone drone;
    private final ClientOptions options;
    private final ConfigurationUpdater configurationUpdater;
    private final DataUpdater dataUpdater;

    public Ar2Controller(CommandSender sender, Ar2Drone drone, ClientOptions options) {
        this.sender = sender;
        this.drone = drone;
        this.options = options;
        this.configurationUpdater = new ConfigurationUpdater(this);
        this.dataUpdater = new DataUpdater(this);
    }

    @Override
    public CompletableFuture<Void> initialize() {
        this.configurationUpdater.start();
        this.dataUpdater.start();

        final ATCommand navDataConfiguration = new UpdateConfigurationCommand(ConfigurationKeys.GENERAL_NAV_DATA_DEMO, "TRUE");
        final ATCommand videoConfiguration = new UpdateConfigurationCommand(ConfigurationKeys.VIDEO_CODEC, VideoCodec.H264_720P.getCodecValue()); // TODO: Make this configurable
        final ATCommand resetAckFlagCommand = new SetControlDataCommand(ControlDataMode.RESET_ACK_FLAG);
        final ATCommand getConfigurationDataCommand = new SetControlDataCommand(ControlDataMode.GET_CONFIGURATION_DATA);

        return sender.sendCommands(resetAckFlagCommand, navDataConfiguration, resetAckFlagCommand, videoConfiguration, getConfigurationDataCommand)
                .thenAccept(unused -> {
                    String firmwareVersion = drone.configuration().get(ConfigurationKeys.GENERAL_NUM_VERSION_SOFT);
                    if (firmwareVersion == null)
                        throw new IllegalStateException("Firmware version is null");
                    if (me.rochblondiaux.parrot4j.api.util.VersionUtil.compareVersions(firmwareVersion, MIN_FIRMWARE_VERSION) == -1)
                        throw new IllegalStateException("The firmware version of the drone is too old. Please update it to at least " + MIN_FIRMWARE_VERSION);
                    isCompatible()
                            .thenAccept(aBoolean -> {
                                if (!aBoolean)
                                    throw new IllegalStateException("The firmware version of the drone is not compatible with this version of Parrot4J. Please update it to at least " + MIN_FIRMWARE_VERSION);
                            });
                });
    }

    @Override
    public CompletableFuture<Void> takeOff() {
        return sender.sendCommand(new FlightModeCommand(FlightMode.TAKE_OFF));
    }

    @Override
    public CompletableFuture<Void> land() {
        return sender.sendCommand(new FlightModeCommand(FlightMode.LAND));
    }

    @Override
    public CompletableFuture<Void> emergency() {
        return sender.sendCommand(new FlightModeCommand(FlightMode.EMERGENCY));
    }

    @Override
    public CompletableFuture<Void> move3d(double vx, double vy, double vz, double vr) {
        return null;
    }

    @Override
    public CompletableFuture<Void> move(double vx, double vy, double vr) {
        return null;
    }

    @Override
    public CompletableFuture<Void> move(@NotNull Location location) {
        return null;
    }

    @Override
    public CompletableFuture<Void> rotate(@NotNull Rotation rotation, float gas) {
        return sender.sendCommand(new FlightMoveCommand(rotation, gas));
    }

    @Override
    public CompletableFuture<Void> cancelMovement() {
        return null;
    }

    @Override
    public CompletableFuture<Void> calibrate() {
        return sender.sendCommand(new CalibrateCommand());
    }

    @Override
    public CompletableFuture<Void> flatTrim() {
        return sender.sendCommand(new FlatTrimCommand());
    }

    public CompletableFuture<Boolean> isCompatible() {
        final FTPConnection connection = new FTPConnection(new InetSocketAddress(options.address(), options.ports().ftp()));
        return connection.connect("version.txt")
                .thenCompose(unused -> {
                    List<String> lines = new ArrayList<>(connection.readLines());
                    return connection.disconnect()
                            .thenApply(unused1 -> {
                                if (lines.size() > 1)
                                    throw new IllegalStateException("The version file must contain exactly one line.");
                                return VersionUtil.fromVersionNumber(lines.get(0)) == drone.model();
                            });
                });
    }

    @Override
    public void disconnect() {
        this.configurationUpdater.interrupt();
        this.dataUpdater.interrupt();
        this.sender.disconnect();
    }
}
