package me.rochblondiaux.parrot4j.ardrone2.model;

import lombok.RequiredArgsConstructor;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum DroneAnimation {
    PHI_M30_DEG(0),
    PHI_30_DEG(1),
    THETA_M30_DEG(2),
    THETA_30_DEG(3),
    THETA_20DEG_YAW_200DEG(4),
    THETA_20DEG_YAW_M200DEG(5),
    TURNAROUND(6),
    TURNAROUND_GODOWN(7),
    YAW_SHAKE(8),
    YAW_DANCE(9),
    PHI_DANCE(10),
    THETA_DANCE(11),
    VZ_DANCE(12),
    WAVE(13),
    PHI_THETA_MIXED(14),
    DOUBLE_PHI_THETA_MIXED(15),
    FLIP_AHEAD(16),  // AR.Drone 2.0
    FLIP_BEHIND(17), // AR.Drone 2.0
    FLIP_LEFT(18),   // AR.Drone 2.0
    FLIP_RIGHT(19),  // AR.Drone 2.0
    ANIM_MAYDAY(20);

    private final int id;

    public int id() {
        return id;
    }
}
