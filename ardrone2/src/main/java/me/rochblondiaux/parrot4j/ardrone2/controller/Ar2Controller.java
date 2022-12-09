package me.rochblondiaux.parrot4j.ardrone2.controller;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.event.EventService;
import me.rochblondiaux.parrot4j.api.model.Location;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.network.FTPConnection;
import me.rochblondiaux.parrot4j.ardrone2.Ar2Drone;
import me.rochblondiaux.parrot4j.ardrone2.command.composed.InitConfigurationCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.simple.FlatTrimCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.simple.FlightModeCommand;
import me.rochblondiaux.parrot4j.ardrone2.command.simple.FlightMoveCommand;
import me.rochblondiaux.parrot4j.ardrone2.configuration.ConfigurationUpdater;
import me.rochblondiaux.parrot4j.ardrone2.data.DataUpdater;
import me.rochblondiaux.parrot4j.ardrone2.events.DroneDataUpdateEvent;
import me.rochblondiaux.parrot4j.ardrone2.model.FlightMode;
import me.rochblondiaux.parrot4j.ardrone2.model.VideoCodec;
import me.rochblondiaux.parrot4j.ardrone2.utils.VersionUtil;
import me.rochblondiaux.parrot4j.ardrone2.video.retriever.VideoRetrieverH264;
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
@Log4j2
public class Ar2Controller implements DroneController {

    private static final String MIN_FIRMWARE_VERSION = "1.6.4";

    private final CommandSender sender;
    private final Ar2Drone drone;
    private final ClientOptions options;
    private final ConfigurationUpdater configurationUpdater;
    private final DataUpdater dataUpdater;
    private final VideoRetrieverH264 videoRetriever;

    public Ar2Controller(CommandSender sender, Ar2Drone drone, ClientOptions options) {
        this.sender = sender;
        this.drone = drone;
        this.options = options;
        this.configurationUpdater = new ConfigurationUpdater(this);
        this.dataUpdater = new DataUpdater(this);
        this.videoRetriever = new VideoRetrieverH264(this);

    }

    @Override
    public CompletableFuture<Void> initialize() {
        // Services
        this.configurationUpdater.start();
        this.dataUpdater.start();
        this.sender.start();

        // Listeners
        EventService.addListener(DroneDataUpdateEvent.class, event -> drone.data(event.data()));

        // Init commands
        return sender.sendCommand(data -> new InitConfigurationCommand(data, VideoCodec.H264_720P));
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
        return null;
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
        this.videoRetriever.interrupt();
        this.configurationUpdater.stop();
        this.dataUpdater.stop();
        this.sender.stop();
    }
}
