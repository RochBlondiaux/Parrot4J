package me.rochblondiaux.parrot4j.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Camera {
    FRONT(0),
    BACK(1),
    PIP_FRONT(2),
    PIP_BACK(3),
    NEXT(4);

    private final int cameraCode;
}
