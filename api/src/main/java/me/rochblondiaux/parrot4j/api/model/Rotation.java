package me.rochblondiaux.parrot4j.api.model;

import lombok.With;

import java.util.Objects;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@With
public class Rotation {

    private final float roll;
    private final float pitch;
    private final float yaw;

    public Rotation(float roll, float pitch, float yaw) {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public float roll() {
        return roll;
    }

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Rotation rotation = (Rotation) obj;
        return Float.compare(rotation.roll, roll) == 0 &&
                Float.compare(rotation.pitch, pitch) == 0 &&
                Float.compare(rotation.yaw, yaw) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roll, pitch, yaw);
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "roll=" + roll +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                '}';
    }
}
