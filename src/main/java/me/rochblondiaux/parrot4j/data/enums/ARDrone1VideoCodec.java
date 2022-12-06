package me.rochblondiaux.parrot4j.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ARDrone1VideoCodec {
    P264(64);

    private final int codecValue;

    @Override
    public String toString() {
        return String.valueOf(codecValue);
    }
}