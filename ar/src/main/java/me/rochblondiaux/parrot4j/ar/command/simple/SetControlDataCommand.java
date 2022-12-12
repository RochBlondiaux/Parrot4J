package me.rochblondiaux.parrot4j.ar.command.simple;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.util.Preconditions;
import me.rochblondiaux.parrot4j.ar.command.ATCommand;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import me.rochblondiaux.parrot4j.ar.model.ControlDataMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class SetControlDataCommand extends ATCommand {

    private final ControlDataMode controlDataMode;
    private final int flag;

    public SetControlDataCommand(ControlDataMode controlDataMode, int flag) {
        super("CTRL");
        this.controlDataMode = controlDataMode;
        this.flag = flag;
    }

    public SetControlDataCommand(ControlDataMode controlDataMode) {
        this(controlDataMode, 0);
    }

    @Override
    protected String build(int sequence) {
        return String.format("%d,%d,%d", sequence, controlDataMode.getControlModeCode(), flag);
    }

    @Override
    public int timeout() {
        switch (controlDataMode) {
            case RESET_ACK_FLAG:
                return DEFAULT_NAVDATA_TIMEOUT;
            case GET_CONFIGURATION_DATA:
                return DEFAULT_CONFIGURATION_TIMEOUT;
            default:
                return 0;
        }
    }

    @Override
    public void isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
        switch (controlDataMode) {
            case RESET_ACK_FLAG:
                Preconditions.checkState(!data.state().controlReceived(), "The command config ACK flag was not reset");
                break;
            case GET_CONFIGURATION_DATA:
                Preconditions.checkNotNull(configuration, "The drone configuration was not sent");
                break;
        }
    }
}
