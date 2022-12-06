package me.rochblondiaux.parrot4j.data;

import lombok.Data;
import me.rochblondiaux.parrot4j.data.enums.ControlAlgorithm;

@Data
public class NavDataState {
    private boolean flying;
    private boolean videoEnabled;
    private boolean visionEnabled;
    private ControlAlgorithm controlAlgorithm;
    private boolean altitudeControlActive;
    private boolean userFeedbackOn;
    private boolean controlReceived;
    private boolean trimReceived;
    private boolean trimRunning;
    private boolean trimSucceeded;
    private boolean navDataDemoOnly;
    private boolean navDataBootstrap;
    private boolean motorsDown;
    private boolean gyrometersDown;
    private boolean batteryTooLow;
    private boolean batteryTooHigh;
    private boolean timerElapsed;
    private boolean notEnoughPower;
    private boolean anglesOutOfRange;
    private boolean tooMuchWind;
    private boolean ultrasonicSensorDeaf;
    private boolean cutoutSystemDetected;
    private boolean picVersionNumberOK;
    private boolean atCodedThreadOn;
    private boolean navDataThreadOn;
    private boolean videoThreadOn;
    private boolean acquisitionThreadOn;
    private boolean controlWatchdogDelayed;
    private boolean adcWatchdogDelayed;
    private boolean communicationProblemOccurred;
    private boolean emergency;
}