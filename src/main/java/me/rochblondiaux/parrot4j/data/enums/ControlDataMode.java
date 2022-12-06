package me.rochblondiaux.parrot4j.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ControlDataMode {
    IDLE(0),
    GET_CONFIGURATION_DATA(4),
    RESET_ACK_FLAG(5);

    private final int controlModeCode;
}
