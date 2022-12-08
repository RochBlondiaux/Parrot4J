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
public enum ControlDataMode {
    IDLE(0),
    GET_CONFIGURATION_DATA(4),
    RESET_ACK_FLAG(5);

    @Getter
    private final int controlModeCode;
}
