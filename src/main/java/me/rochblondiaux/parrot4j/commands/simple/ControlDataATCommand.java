package me.rochblondiaux.parrot4j.commands.simple;

import me.rochblondiaux.parrot4j.CommandSender;
import me.rochblondiaux.parrot4j.CommandSenderCoordinator;
import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.NavData;
import me.rochblondiaux.parrot4j.data.enums.ControlDataMode;

import static com.google.common.base.Preconditions.checkState;

public class ControlDataATCommand extends ATCommandAbstract {
    private final ControlDataMode controlDataMode;

    public ControlDataATCommand(ControlDataMode controlDataMode) {
        super(false);
        this.controlDataMode = controlDataMode;
    }

    @Override
    public void execute(CommandSender commandSender, CommandSenderCoordinator commandSenderCoordinator) {
        if (controlDataMode == ControlDataMode.GET_CONFIGURATION_DATA)
            commandSenderCoordinator.resetConfiguration();
        super.execute(commandSender, commandSenderCoordinator);
    }

    @Override
    protected String getCommand(int sequenceNumber) {
        return String.format("AT*CTRL=%d,%d,0", sequenceNumber, controlDataMode.getControlModeCode());
    }

    @Override
    public int getTimeoutMillis() {
        return switch (controlDataMode) {
            case RESET_ACK_FLAG -> DEFAULT_NAVDATA_TIMEOUT;
            case GET_CONFIGURATION_DATA -> DEFAULT_CONFIGURATION_TIMEOUT;
            default -> 0;
        };
    }

    @Override
    public void checkSuccess(NavData navData, DroneConfiguration droneConfiguration) {
        switch (controlDataMode) {
            case RESET_ACK_FLAG ->
                    checkState(!navData.getState().isControlReceived(), "The command config ACK flag was not reset");
            case GET_CONFIGURATION_DATA ->
                    checkState(droneConfiguration != null, "The drone configuration was not sent");
        }
    }
}