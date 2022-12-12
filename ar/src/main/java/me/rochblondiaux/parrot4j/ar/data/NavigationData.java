package me.rochblondiaux.parrot4j.ar.data;

import lombok.Setter;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;

import java.util.Objects;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Setter
public class NavigationData {

    private boolean onlyHeaderPresent;
    private int battery;
    private int sequenceNumber;
    private Rotation rotation;
    private Speed speed;
    private float altitude;
    private DroneState state;

    public boolean onlyHeaderPresent() {
        return onlyHeaderPresent;
    }

    public int battery() {
        return battery;
    }

    public int sequenceNumber() {
        return sequenceNumber;
    }

    public Rotation rotation() {
        return rotation;
    }

    public Speed speed() {
        return speed;
    }

    public float altitude() {
        return altitude;
    }

    public DroneState state() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (NavigationData) obj;
        return this.onlyHeaderPresent == that.onlyHeaderPresent &&
                this.battery == that.battery &&
                this.sequenceNumber == that.sequenceNumber &&
                Objects.equals(this.rotation, that.rotation) &&
                Objects.equals(this.speed, that.speed) &&
                Float.floatToIntBits(this.altitude) == Float.floatToIntBits(that.altitude) &&
                Objects.equals(this.state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(onlyHeaderPresent, battery, sequenceNumber, rotation, speed, altitude, state);
    }

    @Override
    public String toString() {
        return "DroneData[" +
                "onlyHeaderPresent=" + onlyHeaderPresent + ", " +
                "battery=" + battery + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "rotation=" + rotation + ", " +
                "speed=" + speed + ", " +
                "altitude=" + altitude + ", " +
                "state=" + state + ']';
    }
}
