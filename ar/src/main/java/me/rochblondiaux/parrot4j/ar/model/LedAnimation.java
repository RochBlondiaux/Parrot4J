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
public enum LedAnimation {
    BLINK_GREEN_RED(0),
    BLINK_GREEN(1),
    BLINK_RED(2),
    BLINK_ORANGE(3),
    SNAKE_GREEN_RED(4),
    FIRE(5),
    STANDARD(6),
    RED(7),
    GREEN(8),
    RED_SNAKE(9),
    BLANK(10),
    RIGHT_MISSILE(11),
    LEFT_MISSILE(12),
    DOUBLE_MISSILE(13),
    FRONT_LEFT_GREEN_OTHERS_RED(14),
    FRONT_RIGHT_GREEN_OTHERS_RED(15),
    REAR_RIGHT_GREEN_OTHERS_RED(16),
    REAR_LEFT_GREEN_OTHERS_RED(17),
    LEFT_GREEN_RIGHT_RED(18),
    LEFT_RED_RIGHT_GREEN(19),
    BLINK_STANDARD(20),
    AR_DRONE_NB_LED_ANIM_MAYDAY(21);

    @Getter
    private final int id;
}
