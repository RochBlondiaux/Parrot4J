package me.rochblondiaux.parrot4j.ardrone2.command.simple;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.ardrone2.command.ATCommand;
import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import me.rochblondiaux.parrot4j.ardrone2.model.ControlDataMode;
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
        return "%d,%d,%d".formatted(sequence, controlDataMode.getControlModeCode(), flag);
    }

    @Override
    public int timeout() {
        return switch (controlDataMode) {
            case RESET_ACK_FLAG -> DEFAULT_NAVDATA_TIMEOUT;
            case GET_CONFIGURATION_DATA -> DEFAULT_CONFIGURATION_TIMEOUT;
            default -> 0;
        };
    }

    @Override
    public boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        return switch (controlDataMode) {
            case RESET_ACK_FLAG -> data != null && data.state().controlReceived();
            case GET_CONFIGURATION_DATA -> configuration != null;

            default -> true;
        };
    }
}
