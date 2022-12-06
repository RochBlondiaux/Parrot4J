package me.rochblondiaux.parrot4j;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.commands.Command;
import me.rochblondiaux.parrot4j.commands.composed.*;
import me.rochblondiaux.parrot4j.components.ErrorListenerComponent;
import me.rochblondiaux.parrot4j.components.ReadyStateListenerComponent;
import me.rochblondiaux.parrot4j.data.Config;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.enums.*;
import me.rochblondiaux.parrot4j.injection.Context;
import me.rochblondiaux.parrot4j.listeners.ErrorListener;
import me.rochblondiaux.parrot4j.listeners.NavDataListener;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;
import me.rochblondiaux.parrot4j.listeners.VideoDataListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkState;

@Log4j2
public class DroneController {
    private static final int NUMBER_OF_THREADS = 1;


    private final ReadyStateListenerComponent readyStateListenerComponent;

    private final ErrorListenerComponent errorListenerComponent;

    private final DroneStartupCoordinator droneStartupCoordinator;

    private final CommandSenderCoordinator commandSender;

    private final NavigationDataRetriever navigationDataRetriever;

    private final VideoRetrieverP264 videoRetrieverP264;

    private final VideoRetrieverH264 videoRetrieverH264;

    private final InternalStateWatcher internalStateWatcher;

    private ExecutorService executor;

    private Config config;

    @Inject
    public DroneController(ReadyStateListenerComponent readyStateListenerComponent, ErrorListenerComponent errorListenerComponent,
                           DroneStartupCoordinator droneStartupCoordinator, CommandSenderCoordinator commandSenderCoordinator,
                           NavigationDataRetriever navigationDataRetriever, VideoRetrieverP264 videoRetrieverP264,
                           VideoRetrieverH264 videoRetrieverH264, InternalStateWatcher internalStateWatcher) {
        this.readyStateListenerComponent = readyStateListenerComponent;
        this.errorListenerComponent = errorListenerComponent;
        this.droneStartupCoordinator = droneStartupCoordinator;
        this.commandSender = commandSenderCoordinator;
        this.navigationDataRetriever = navigationDataRetriever;
        this.videoRetrieverP264 = videoRetrieverP264;
        this.videoRetrieverH264 = videoRetrieverH264;
        this.internalStateWatcher = internalStateWatcher;
    }

    public static DroneController build() {
        return Context.getBean(DroneController.class);
    }

    public CompletableFuture<Void> startAsync(final Config config) {
        checkInitializationStateStarted();
        initializeExecutor();

        return CompletableFuture.supplyAsync(() -> {
            try {
                start(config);
            } catch (Throwable e) {
                errorListenerComponent.emitError(e);
            }
            return null;
        }, executor);
    }

    public void start(Config config) {
        checkInitializationStateStarted();

        log.info("Starting drone controller");

        this.config = config;

        initializeExecutor();
        droneStartupCoordinator.start(config);
        readyStateListenerComponent.emitReadyStateChange(ReadyStateChangeListener.ReadyState.READY);
    }

    private void initializeExecutor() {
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public void stop() {
        readyStateListenerComponent.emitReadyStateChange(ReadyStateChangeListener.ReadyState.NOT_READY);
        log.info("Stopping drone controller");
        droneStartupCoordinator.stop();
        executor.shutdownNow();
    }

    public boolean isInitialized() {
        return droneStartupCoordinator.getState() == ControllerState.READY;
    }

    public void addReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void removeReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void addErrorListener(ErrorListener errorListener) {
        errorListenerComponent.addErrorListener(errorListener);
    }

    public void removeErrorListener(ErrorListener errorListener) {
        errorListenerComponent.removeErrorListener(errorListener);
    }

    public void addNavDataListener(NavDataListener navDataListener) {
        navigationDataRetriever.addNavDataListener(navDataListener);
    }

    public void removeNavDataListener(NavDataListener navDataListener) {
        navigationDataRetriever.removeNavDataListener(navDataListener);
    }

    public void addVideoDataListener(VideoDataListener videoDataListener) {
        videoRetrieverH264.addVideoDataListener(videoDataListener);
        videoRetrieverP264.addVideoDataListener(videoDataListener);
    }

    public void removeVideoDataListener(VideoDataListener videoDataListener) {
        videoRetrieverH264.removeVideoDataListener(videoDataListener);
        videoRetrieverP264.removeVideoDataListener(videoDataListener);
    }

    public DroneVersion getDroneVersion() {
        checkInitializationState();
        return droneStartupCoordinator.getDroneVersion();
    }

    public DroneConfiguration getDroneConfiguration() {
        checkInitializationState();
        return droneStartupCoordinator.getDroneConfiguration();
    }

    public void takeOff() {
        checkInitializationState();

        log.debug("Taking off");
        internalStateWatcher.requestTakeOff();
    }

    public void land() {
        checkInitializationState();

        log.debug("Landing");
        internalStateWatcher.requestLand();
    }

    public void emergency() {
        checkInitializationState();

        log.debug("Setting emergency");
        internalStateWatcher.requestEmergency();
    }

    public void flatTrim() {
        checkInitializationState();

        log.debug("Flat trim");
        internalStateWatcher.requestFlatTrim();
    }

    public void move(float roll, float pitch, float yaw, float gaz) {
        checkInitializationState();

        log.trace(String.format("Moving - roll: %.2f, pitch: %.2f, yaw: %.2f, gaz: %.2f", roll, pitch, yaw, gaz));
        internalStateWatcher.requestMove(roll, pitch, yaw, gaz);
    }

    public CompletableFuture<Void> switchCamera(Camera camera) {
        checkInitializationState();

        log.debug("Changing camera to '{}'", camera.name());
        return executeCommandsAsync(new SwitchCameraCommand(config.getLoginData(), camera), new GetConfigurationDataCommand());
    }

    public CompletableFuture<Void> playLedAnimation(LedAnimation ledAnimation, float frequency, int durationSeconds) {
        checkInitializationState();

        log.debug(String.format("Playing LED animation '%s'", ledAnimation.name()));
        return executeCommandsAsync(new PlayLedAnimationCommand(config.getLoginData(), ledAnimation, frequency, durationSeconds));
    }

    public CompletableFuture<Void> playFlightAnimation(FlightAnimation animation) {
        checkInitializationState();

        log.debug(String.format("Playing flight animation '%s'", animation.name()));
        return executeCommandsAsync(new PlayFlightAnimationCommand(config.getLoginData(), animation));
    }

    public CompletableFuture<Void> setConfigValue(String key, Object value) {
        checkInitializationState();

        log.debug("Setting config value '{}' to '{}'", key, value.toString());
        return executeCommandsAsync(new SetConfigValueCommand(config.getLoginData(), key, value), new GetConfigurationDataCommand());
    }

    public void executeCommands(Command... commands) {
        for (Command command : commands)
            commandSender.executeCommand(command);
    }

    public CompletableFuture<Void> executeCommandsAsync(final Command... commands) {
        return CompletableFuture.supplyAsync(() -> {
            executeCommands(commands);
            return null;
        }, executor);
    }

    private void checkInitializationState() {
        checkState(isInitialized(), "The drone controller is not yet fully initialized");
    }

    private void checkInitializationStateStarted() {
        checkState(droneStartupCoordinator.getState() == ControllerState.STARTED, "The drone controller has already been initialized");
    }
}