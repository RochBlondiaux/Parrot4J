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
public enum VideoCodec {
    H264_360P(129),
    H264_720P(131);

    @Getter
    private final int codecValue;

    @Override
    public String toString() {
        return String.valueOf(codecValue);
    }
}
