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
public class Speed {

    private final float x;
    private final float y;
    private final float z;

    public Speed(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Speed speed = (Speed) obj;
        return x == speed.x && y == speed.y && z == speed.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Speed{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
