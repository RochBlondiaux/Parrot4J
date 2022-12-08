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
public enum Camera {
    FRONT(0),
    BACK(1),
    PIP_FRONT(2),
    PIP_BACK(3),
    NEXT(4);

    @Getter
    private final int cameraCode;
}
