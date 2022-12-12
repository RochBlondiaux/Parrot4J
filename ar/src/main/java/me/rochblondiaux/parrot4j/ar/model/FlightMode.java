package me.rochblondiaux.parrot4j.ar.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum FlightMode {
    TAKE_OFF(290718208),
    LAND(290717696),
    EMERGENCY(290717952);

    @Getter
    private final int commandCode;
}