package me.rochblondiaux.parrot4j.data;

import me.rochblondiaux.parrot4j.data.enums.ControlAlgorithm;

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

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }

    public void setVideoEnabled(boolean videoEnabled) {
        this.videoEnabled = videoEnabled;
    }

    public boolean isVisionEnabled() {
        return visionEnabled;
    }

    public void setVisionEnabled(boolean visionEnabled) {
        this.visionEnabled = visionEnabled;
    }

    public ControlAlgorithm getControlAlgorithm() {
        return controlAlgorithm;
    }

    public void setControlAlgorithm(ControlAlgorithm controlAlgorithm) {
        this.controlAlgorithm = controlAlgorithm;
    }

    public boolean isAltitudeControlActive() {
        return altitudeControlActive;
    }

    public void setAltitudeControlActive(boolean altitudeControlActive) {
        this.altitudeControlActive = altitudeControlActive;
    }

    public boolean isUserFeedbackOn() {
        return userFeedbackOn;
    }

    public void setUserFeedbackOn(boolean userFeedbackOn) {
        this.userFeedbackOn = userFeedbackOn;
    }

    public boolean isControlReceived() {
        return controlReceived;
    }

    public void setControlReceived(boolean controlReceived) {
        this.controlReceived = controlReceived;
    }

    public boolean isTrimReceived() {
        return trimReceived;
    }

    public void setTrimReceived(boolean trimReceived) {
        this.trimReceived = trimReceived;
    }

    public boolean isTrimRunning() {
        return trimRunning;
    }

    public void setTrimRunning(boolean trimRunning) {
        this.trimRunning = trimRunning;
    }

    public boolean isTrimSucceeded() {
        return trimSucceeded;
    }

    public void setTrimSucceeded(boolean trimSucceeded) {
        this.trimSucceeded = trimSucceeded;
    }

    public boolean isNavDataDemoOnly() {
        return navDataDemoOnly;
    }

    public void setNavDataDemoOnly(boolean navDataDemoOnly) {
        this.navDataDemoOnly = navDataDemoOnly;
    }

    public boolean isNavDataBootstrap() {
        return navDataBootstrap;
    }

    public void setNavDataBootstrap(boolean navDataBootstrap) {
        this.navDataBootstrap = navDataBootstrap;
    }

    public boolean isMotorsDown() {
        return motorsDown;
    }

    public void setMotorsDown(boolean motorsDown) {
        this.motorsDown = motorsDown;
    }

    public boolean isGyrometersDown() {
        return gyrometersDown;
    }

    public void setGyrometersDown(boolean gyrometersDown) {
        this.gyrometersDown = gyrometersDown;
    }

    public boolean isBatteryTooLow() {
        return batteryTooLow;
    }

    public void setBatteryTooLow(boolean batteryTooLow) {
        this.batteryTooLow = batteryTooLow;
    }

    public boolean isBatteryTooHigh() {
        return batteryTooHigh;
    }

    public void setBatteryTooHigh(boolean batteryTooHigh) {
        this.batteryTooHigh = batteryTooHigh;
    }

    public boolean isTimerElapsed() {
        return timerElapsed;
    }

    public void setTimerElapsed(boolean timerElapsed) {
        this.timerElapsed = timerElapsed;
    }

    public boolean isNotEnoughPower() {
        return notEnoughPower;
    }

    public void setNotEnoughPower(boolean notEnoughPower) {
        this.notEnoughPower = notEnoughPower;
    }

    public boolean isAnglesOutOfRange() {
        return anglesOutOfRange;
    }

    public void setAnglesOutOfRange(boolean anglesOutOfRange) {
        this.anglesOutOfRange = anglesOutOfRange;
    }

    public boolean isTooMuchWind() {
        return tooMuchWind;
    }

    public void setTooMuchWind(boolean tooMuchWind) {
        this.tooMuchWind = tooMuchWind;
    }

    public boolean isUltrasonicSensorDeaf() {
        return ultrasonicSensorDeaf;
    }

    public void setUltrasonicSensorDeaf(boolean ultrasonicSensorDeaf) {
        this.ultrasonicSensorDeaf = ultrasonicSensorDeaf;
    }

    public boolean isCutoutSystemDetected() {
        return cutoutSystemDetected;
    }

    public void setCutoutSystemDetected(boolean cutoutSystemDetected) {
        this.cutoutSystemDetected = cutoutSystemDetected;
    }

    public boolean isPicVersionNumberOK() {
        return picVersionNumberOK;
    }

    public void setPicVersionNumberOK(boolean picVersionNumberOK) {
        this.picVersionNumberOK = picVersionNumberOK;
    }

    public boolean isAtCodedThreadOn() {
        return atCodedThreadOn;
    }

    public void setAtCodedThreadOn(boolean atCodedThreadOn) {
        this.atCodedThreadOn = atCodedThreadOn;
    }

    public boolean isNavDataThreadOn() {
        return navDataThreadOn;
    }

    public void setNavDataThreadOn(boolean navDataThreadOn) {
        this.navDataThreadOn = navDataThreadOn;
    }

    public boolean isVideoThreadOn() {
        return videoThreadOn;
    }

    public void setVideoThreadOn(boolean videoThreadOn) {
        this.videoThreadOn = videoThreadOn;
    }

    public boolean isAcquisitionThreadOn() {
        return acquisitionThreadOn;
    }

    public void setAcquisitionThreadOn(boolean acquisitionThreadOn) {
        this.acquisitionThreadOn = acquisitionThreadOn;
    }

    public boolean isControlWatchdogDelayed() {
        return controlWatchdogDelayed;
    }

    public void setControlWatchdogDelayed(boolean controlWatchdogDelayed) {
        this.controlWatchdogDelayed = controlWatchdogDelayed;
    }

    public boolean isAdcWatchdogDelayed() {
        return adcWatchdogDelayed;
    }

    public void setAdcWatchdogDelayed(boolean adcWatchdogDelayed) {
        this.adcWatchdogDelayed = adcWatchdogDelayed;
    }

    public boolean isCommunicationProblemOccurred() {
        return communicationProblemOccurred;
    }

    public void setCommunicationProblemOccurred(boolean communicationProblemOccurred) {
        this.communicationProblemOccurred = communicationProblemOccurred;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }
}