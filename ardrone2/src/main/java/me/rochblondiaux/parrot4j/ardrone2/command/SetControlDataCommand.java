package me.rochblondiaux.parrot4j.ardrone2.command;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import me.rochblondiaux.parrot4j.ardrone2.model.ControlDataMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class SetControlDataCommand extends ATCommand {

    private final ControlDataMode mode;
    private final int parameter2;

    public SetControlDataCommand(ControlDataMode mode, int parameter2) {
        super("CTRL");
        this.mode = mode;
        this.parameter2 = parameter2;
    }

    public SetControlDataCommand(ControlDataMode mode) {
        this(mode, 0);
    }

    @Override
    protected String build(int sequence) {
        return "AT*CTRL=%d,%d,%d".formatted(sequence, mode.getControlModeCode(), parameter2);
    }

    @Override
    public int timeout() {
        return mode.getDuration();
    }

    @Override
    public boolean isSuccessful(@NotNull DroneData data, @Nullable DroneConfiguration configuration) {
        switch (mode) {
            case RESET_ACK_FLAG -> {
                boolean success = data.state().controlReceived();
                if (!success)
                    log.warn("The command config ACK flag was not reset");
                return success;
            }
            case GET_CONFIGURATION_DATA -> {
                boolean success = configuration == null;
                if (!success)
                    log.warn("The drone configuration was not sent");
                return success;
            }
            default -> {
                return true;
            }
        }
    }

    @Override
    public void onExecution(@NotNull Ar2Controller controller) {
        if (mode == ControlDataMode.RESET_ACK_FLAG)
            controller.getDrone().configuration().reset();
    }
}
