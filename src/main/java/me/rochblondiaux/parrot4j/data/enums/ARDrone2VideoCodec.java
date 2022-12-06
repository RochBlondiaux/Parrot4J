package me.rochblondiaux.parrot4j.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ARDrone2VideoCodec {
    H264_360P(129),
    H264_720P(131);

    private final int codecValue;

    @Override
    public String toString() {
        return String.valueOf(codecValue);
    }
}
