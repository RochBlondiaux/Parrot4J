package me.rochblondiaux.parrot4j.api.drone.enums;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.api.drone.model.DronePort;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum DronePorts implements DronePort {
    AR_DRONE_2(5551, 5552, 5556, 5555, 5553, 5558, 5559, 5554, 5557),
    ;

    private final int ftp;
    private final int authentication;
    private final int commands;
    private final int video;
    private final int videoRecorder;
    private final int logs;
    private final int control;
    private final int navigation;
    private final int media;

    @Override
    public int ftp() {
        return ftp;
    }

    @Override
    public int authentication() {
        return authentication;
    }

    @Override
    public int commands() {
        return commands;
    }

    @Override
    public int video() {
        return video;
    }

    @Override
    public int videoRecorder() {
        return videoRecorder;
    }

    @Override
    public int logs() {
        return logs;
    }

    @Override
    public int control() {
        return control;
    }

    @Override
    public int navigation() {
        return navigation;
    }

    @Override
    public int media() {
        return media;
    }
}
