package me.rochblondiaux.parrot4j.ar;

import lombok.Data;
import me.rochblondiaux.parrot4j.api.client.ClientOptions;
import me.rochblondiaux.parrot4j.api.driver.DroneController;
import me.rochblondiaux.parrot4j.ar.command.Command;
import me.rochblondiaux.parrot4j.ar.command.CommandManager;
import me.rochblondiaux.parrot4j.ar.command.composed.InitConfigurationCommand;
import me.rochblondiaux.parrot4j.ar.command.simple.SetFlightModeCommand;
import me.rochblondiaux.parrot4j.ar.configuration.ConfigurationManager;
import me.rochblondiaux.parrot4j.ar.data.NavigationDataManager;
import me.rochblondiaux.parrot4j.ar.model.FlightMode;
import me.rochblondiaux.parrot4j.ar.model.VideoCodec;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Data
public class ArController implements DroneController {

    public static final Executor EXECUTOR = Executors.newFixedThreadPool(8);

    private final ClientOptions options;
    private final ArDrone drone;
    private final CommandManager commandManager;
    private final ConfigurationManager configurationManager;
    private final NavigationDataManager navigationDataManager;

    public ArController(ClientOptions options, ArDrone drone) {
        this.options = options;
        this.drone = drone;
        this.commandManager = new CommandManager(this);
        this.configurationManager = new ConfigurationManager(this);
        this.navigationDataManager = new NavigationDataManager(this);
    }

    @Override
    public CompletableFuture<Void> initialize() {
        return CompletableFuture.runAsync(() -> {
            // Managers
            this.configurationManager.start();
            this.navigationDataManager.start();
            this.commandManager.start();

            // Init commands
            this.commandManager.send(data -> new InitConfigurationCommand(drone, data, VideoCodec.H264_720P)).join();
        });
    }

    @Override
    public CompletableFuture<Void> takeOff() {
        return commandManager.send(new SetFlightModeCommand(FlightMode.TAKE_OFF));
    }

    @Override
    public CompletableFuture<Void> land() {
        return commandManager.send(new SetFlightModeCommand(FlightMode.LAND));
    }

    @Override
    public CompletableFuture<Void> emergency() {
        return commandManager.send(new SetFlightModeCommand(FlightMode.EMERGENCY));
    }

    public CompletableFuture<Void> sendCommand(@NotNull Command command) {
        return commandManager.send(command);
    }

    @Override
    public void disconnect() {
        // Managers
        this.configurationManager.stop();
        this.navigationDataManager.stop();
        this.commandManager.stop();
    }
}
