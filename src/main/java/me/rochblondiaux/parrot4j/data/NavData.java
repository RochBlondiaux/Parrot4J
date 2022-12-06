package me.rochblondiaux.parrot4j.data;

public class NavData {
    private NavDataState state;

    private boolean onlyHeaderPresent;

    private int batteryLevel;

    private int sequenceNumber;

    private float pitch;

    private float roll;

    private float yaw;

    private float altitude;

    private float speedX;

    private float speedY;

    private float speedZ;

    public NavData() {
        onlyHeaderPresent = true;
    }

    public boolean isOnlyHeaderPresent() {
        return onlyHeaderPresent;
    }

    public void setOnlyHeaderPresent(boolean onlyHeaderPresent) {
        this.onlyHeaderPresent = onlyHeaderPresent;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public NavDataState getState() {
        return state;
    }

    public void setState(NavDataState state) {
        this.state = state;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedZ() {
        return speedZ;
    }

    public void setSpeedZ(float speedZ) {
        this.speedZ = speedZ;
    }
}