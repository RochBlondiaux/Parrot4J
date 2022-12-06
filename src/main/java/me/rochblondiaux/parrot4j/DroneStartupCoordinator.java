package me.rochblondiaux.parrot4j;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.commands.composed.InitializeConfigurationCommand;
import me.rochblondiaux.parrot4j.components.AddressComponent;
import me.rochblondiaux.parrot4j.data.Config;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.NavData;
import me.rochblondiaux.parrot4j.data.enums.ControllerState;
import me.rochblondiaux.parrot4j.data.enums.DroneVersion;
import me.rochblondiaux.parrot4j.listeners.DroneConfigurationListener;
import me.rochblondiaux.parrot4j.listeners.NavDataListener;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;

import static me.rochblondiaux.parrot4j.helpers.ThreadHelper.sleep;

@Log4j2
public class DroneStartupCoordinator implements ReadyStateChangeListener, NavDataListener, DroneConfigurationListener {
    private static final int STOP_TIMEOUT = 3000;


    private final CommandSenderCoordinator commandSenderCoordinator;
    private final AddressComponent addressComponent;
    private final VersionReader versionReader;
    private final CommandSender commandSender;
    private final NavigationDataRetriever navigationDataRetriever;
    private final VideoRetrieverP264 videoRetrieverP264;
    private final VideoRetrieverH264 videoRetrieverH264;
    private final ConfigurationDataRetriever configurationDataRetriever;
    private Config config;
    private ControllerState currentState;
    private DroneVersion droneVersion;
    private DroneConfiguration droneConfiguration;

    @Inject
    public DroneStartupCoordinator(CommandSenderCoordinator commandSenderCoordinator, AddressComponent addressComponent, VersionReader versionReader,
                                   CommandSender commandSender, NavigationDataRetriever navigationDataRetriever,
                                   VideoRetrieverP264 videoRetrieverP264, VideoRetrieverH264 videoRetrieverH264,
                                   ConfigurationDataRetriever configurationDataRetriever) {
        this.commandSenderCoordinator = commandSenderCoordinator;
        this.addressComponent = addressComponent;
        this.versionReader = versionReader;
        this.commandSender = commandSender;
        this.navigationDataRetriever = navigationDataRetriever;
        this.videoRetrieverP264 = videoRetrieverP264;
        this.videoRetrieverH264 = videoRetrieverH264;
        this.configurationDataRetriever = configurationDataRetriever;

        addListeners(commandSender);
        currentState = ControllerState.STARTED;
    }

    private void addListeners(CommandSender commandSender) {
        commandSender.addReadyStateChangeListener(this);
        configurationDataRetriever.addReadyStateChangeListener(this);
        navigationDataRetriever.addReadyStateChangeListener(this);
        videoRetrieverH264.addReadyStateChangeListener(this);
        videoRetrieverP264.addReadyStateChangeListener(this);

        navigationDataRetriever.addNavDataListener(this);
        configurationDataRetriever.addDroneConfigurationListener(this);
    }

    public void start(Config config) {
        for (int currentTry = 0; currentTry <= config.getMaxStartupRetries(); currentTry++) {
            try {
                startConnecting(config);
                break;
            } catch (Exception e) {
                performStartupErrorActions(config, currentTry, e);
            }
        }
    }

    private void startConnecting(Config config) {
        this.config = config;

        checkIfDroneIsReachable();
        determineDroneVersion();

        startWorkers();
        waitForState(ControllerState.WORKERS_READY);
        log.info("Workers are ready to be used");

        login();
        log.info("Logged in successfully");

        startVideoRetriever();
        waitForState(ControllerState.READY);
        log.info("Drone setup complete");
    }

    private void performStartupErrorActions(Config config, int currentTry, Exception e) {
        log.warn("There was an error while connecting: {}", e.getMessage());
        stop();

        if (currentTry == config.getMaxStartupRetries()) {
            throw new IllegalStateException(e);
        } else {
            sleep(STOP_TIMEOUT);
        }
    }

    private void checkIfDroneIsReachable() {
        //checkState(addressComponent.isReachable(config.getDroneIpAddress(), Config.REACHABLE_TIMEOUT), "The drone could not be pinged");
        log.info("The drone could be pinged");
    }

    private void determineDroneVersion() {
        droneVersion = versionReader.getDroneVersion(config.getDroneIpAddress(), config.getFtpPort());
    }

    private void startWorkers() {
        commandSender.start(config.getDroneIpAddress(), config.getCommandPort());
        configurationDataRetriever.start(config.getDroneIpAddress(), config.getConfigDataPort());
        navigationDataRetriever.start(config.getDroneIpAddress(), config.getNavDataPort());
    }

    private void login() {
        if (droneVersion == DroneVersion.AR_DRONE_1) {
            commandSenderCoordinator.executeCommand(new InitializeConfigurationCommand(config.getLoginData(), config.getArDrone1VideoCodec()));
        } else {
            commandSenderCoordinator.executeCommand(new InitializeConfigurationCommand(config.getLoginData(), config.getArDrone2VideoCodec()));
        }
    }

    private void startVideoRetriever() {
        if (droneVersion == DroneVersion.AR_DRONE_1) {
            videoRetrieverP264.start(config.getDroneIpAddress(), config.getVideoDataPort());
        } else {
            videoRetrieverH264.start(config.getDroneIpAddress(), config.getVideoDataPort());
        }
    }

    private void waitForState(ControllerState state) {
        while (currentState != state) {
            sleep(Config.WAIT_TIMEOUT);
        }
    }

    public void stop() {
        currentState = ControllerState.STOPPED;

        configurationDataRetriever.stop();
        navigationDataRetriever.stop();
        commandSender.stop();
        stopVideoRetriever();
    }

    private void stopVideoRetriever() {
        if (droneVersion == DroneVersion.AR_DRONE_1) {
            videoRetrieverP264.stop();
        } else {
            videoRetrieverH264.stop();
        }
    }

    @Override
    public void onDroneConfiguration(DroneConfiguration configuration) {
        droneConfiguration = configuration;
    }

    @Override
    public void onNavData(NavData navData) {
        //noinspection StatementWithEmptyBody
        if (navData.getState().isCommunicationProblemOccurred()) {
            // TODO if there are problems, reset the command sender sequence number
            // There is no clear reference in the manual for that
            // (@see Developer Manual, page 40 ("How do the client and the droneapi synchronize?")
        }
    }

    @Override
    public void onReadyStateChange(ReadyState readyState) {
        if (readyState == ReadyStateChangeListener.ReadyState.READY) {
            currentState = currentState.getNextState();
        }
    }

    public ControllerState getState() {
        return currentState;
    }

    public DroneVersion getDroneVersion() {
        return droneVersion;
    }

    public DroneConfiguration getDroneConfiguration() {
        return droneConfiguration;
    }
}