package me.rochblondiaux.parrot4j.data;

import lombok.Data;

@Data
public class InternalState {
    private boolean takeOffRequested;

    private boolean landRequested;

    private boolean emergencyRequested;

    private boolean flatTrimRequested;

    private boolean moveRequested;

    private float requestedRoll;

    private float requestedPitch;

    private float requestedYaw;

    private float requestedGaz;
}