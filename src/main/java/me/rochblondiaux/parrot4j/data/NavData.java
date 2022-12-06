package me.rochblondiaux.parrot4j.data;

import lombok.Data;

@Data
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
}