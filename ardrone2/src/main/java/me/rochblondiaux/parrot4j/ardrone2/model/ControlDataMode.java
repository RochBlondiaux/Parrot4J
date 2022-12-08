package me.rochblondiaux.parrot4j.ardrone2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
@Getter
public enum ControlDataMode {
    IDLE(0, -1),
    GET_CONFIGURATION_DATA(4, 100),
    RESET_ACK_FLAG(5, 1250);

    private final int controlModeCode;
    private final int duration;

}
