package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.command.UpdateConfigurationCommand;
import me.rochblondiaux.parrot4j.ardrone2.model.Camera;
import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class SwitchCameraCommand extends UpdateConfigurationCommand {

    public SwitchCameraCommand(Camera camera) {
        super(ConfigurationKeys.VIDEO_CHANNEL, camera.getCameraCode());
    }
}
