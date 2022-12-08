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
@Getter
public enum DroneAnimation {
    PHI_M30_DEG(0, 1000),
    PHI_30_DEG(1, 1000),
    THETA_M30_DEG(2, 1000),
    THETA_30_DEG(3, 1000),
    THETA_20DEG_YAW_200DEG(4, 1000),
    THETA_20DEG_YAW_M200DEG(5, 1000),
    TURNAROUND(6, 5000),
    TURNAROUND_GOD_OWN(7, 5000),
    YAW_SHAKE(8, 2000),
    YAW_DANCE(9, 5000),
    PHI_DANCE(10, 5000),
    THETA_DANCE(11, 5000),
    VZ_DANCE(12, 5000),
    WAVE(13, 5000),
    PHI_THETA_MIXED(14, 5000),
    DOUBLE_PHI_THETA_MIXED(15, 5000),
    FLIP_AHEAD(16, 15),  // AR.Drone 2.0
    FLIP_BEHIND(17, 15), // AR.Drone 2.0
    FLIP_LEFT(18, 15),   // AR.Drone 2.0
    FLIP_RIGHT(19, 15),  // AR.Drone 2.0
    ANIM_MAYDAY(20, 15);

    private final int id;
    private final int duration;
}
