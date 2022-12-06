package me.rochblondiaux.parrot4j.commands.composed;

import me.rochblondiaux.parrot4j.data.DroneConfiguration;
import me.rochblondiaux.parrot4j.data.LoginData;
import me.rochblondiaux.parrot4j.data.enums.Camera;

public class SwitchCameraCommand extends SetConfigValueCommand {
    public SwitchCameraCommand(LoginData loginData, Camera camera) {
        super(loginData, DroneConfiguration.VIDEO_CHANNEL_KEY, camera.getCameraCode());
    }
}