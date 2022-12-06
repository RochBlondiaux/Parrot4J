package me.rochblondiaux.parrot4j.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FlightMode {
    TAKE_OFF(290718208),
    LAND(290717696),
    EMERGENCY(290717952);

    private final int commandCode;
}
