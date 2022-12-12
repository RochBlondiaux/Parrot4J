package me.rochblondiaux.parrot4j.ar.data;

import lombok.NoArgsConstructor;
import lombok.Setter;
import me.rochblondiaux.parrot4j.ar.model.ControlAlgorithm;

import java.util.Objects;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Setter
@NoArgsConstructor
public class DroneState {

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

    public boolean communicationProblemOccurred() {
        return communicationProblemOccurred;
    }


    public boolean flying() {
        return flying;
    }

    public boolean videoEnabled() {
        return videoEnabled;
    }

    public boolean visionEnabled() {
        return visionEnabled;
    }

    public ControlAlgorithm controlAlgorithm() {
        return controlAlgorithm;
    }

    public boolean altitudeControlActive() {
        return altitudeControlActive;
    }

    public boolean userFeedbackOn() {
        return userFeedbackOn;
    }

    public boolean controlReceived() {
        return controlReceived;
    }

    public boolean trimReceived() {
        return trimReceived;
    }

    public boolean trimRunning() {
        return trimRunning;
    }

    public boolean trimSucceeded() {
        return trimSucceeded;
    }

    public boolean navDataDemoOnly() {
        return navDataDemoOnly;
    }

    public boolean navDataBootstrap() {
        return navDataBootstrap;
    }

    public boolean motorsDown() {
        return motorsDown;
    }

    public boolean gyrometersDown() {
        return gyrometersDown;
    }

    public boolean batteryTooLow() {
        return batteryTooLow;
    }

    public boolean batteryTooHigh() {
        return batteryTooHigh;
    }

    public boolean timerElapsed() {
        return timerElapsed;
    }

    public boolean notEnoughPower() {
        return notEnoughPower;
    }

    public boolean anglesOutOfRange() {
        return anglesOutOfRange;
    }

    public boolean tooMuchWind() {
        return tooMuchWind;
    }

    public boolean ultrasonicSensorDeaf() {
        return ultrasonicSensorDeaf;
    }

    public boolean cutoutSystemDetected() {
        return cutoutSystemDetected;
    }

    public boolean picVersionNumberOK() {
        return picVersionNumberOK;
    }

    public boolean atCodedThreadOn() {
        return atCodedThreadOn;
    }

    public boolean navDataThreadOn() {
        return navDataThreadOn;
    }

    public boolean videoThreadOn() {
        return videoThreadOn;
    }

    public boolean acquisitionThreadOn() {
        return acquisitionThreadOn;
    }

    public boolean controlWatchdogDelayed() {
        return controlWatchdogDelayed;
    }

    public boolean adcWatchdogDelayed() {
        return adcWatchdogDelayed;
    }

    public boolean emergency() {
        return emergency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DroneState) obj;
        return this.flying == that.flying &&
                this.videoEnabled == that.videoEnabled &&
                this.visionEnabled == that.visionEnabled &&
                Objects.equals(this.controlAlgorithm, that.controlAlgorithm) &&
                this.altitudeControlActive == that.altitudeControlActive &&
                this.userFeedbackOn == that.userFeedbackOn &&
                this.controlReceived == that.controlReceived &&
                this.trimReceived == that.trimReceived &&
                this.trimRunning == that.trimRunning &&
                this.trimSucceeded == that.trimSucceeded &&
                this.navDataDemoOnly == that.navDataDemoOnly &&
                this.navDataBootstrap == that.navDataBootstrap &&
                this.motorsDown == that.motorsDown &&
                this.gyrometersDown == that.gyrometersDown &&
                this.batteryTooLow == that.batteryTooLow &&
                this.batteryTooHigh == that.batteryTooHigh &&
                this.timerElapsed == that.timerElapsed &&
                this.notEnoughPower == that.notEnoughPower &&
                this.anglesOutOfRange == that.anglesOutOfRange &&
                this.tooMuchWind == that.tooMuchWind &&
                this.ultrasonicSensorDeaf == that.ultrasonicSensorDeaf &&
                this.cutoutSystemDetected == that.cutoutSystemDetected &&
                this.picVersionNumberOK == that.picVersionNumberOK &&
                this.atCodedThreadOn == that.atCodedThreadOn &&
                this.navDataThreadOn == that.navDataThreadOn &&
                this.videoThreadOn == that.videoThreadOn &&
                this.acquisitionThreadOn == that.acquisitionThreadOn &&
                this.controlWatchdogDelayed == that.controlWatchdogDelayed &&
                this.adcWatchdogDelayed == that.adcWatchdogDelayed &&
                this.emergency == that.emergency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flying, videoEnabled, visionEnabled, controlAlgorithm, altitudeControlActive, userFeedbackOn, controlReceived, trimReceived, trimRunning, trimSucceeded, navDataDemoOnly, navDataBootstrap, motorsDown, gyrometersDown, batteryTooLow, batteryTooHigh, timerElapsed, notEnoughPower, anglesOutOfRange, tooMuchWind, ultrasonicSensorDeaf, cutoutSystemDetected, picVersionNumberOK, atCodedThreadOn, navDataThreadOn, videoThreadOn, acquisitionThreadOn, controlWatchdogDelayed, adcWatchdogDelayed, emergency);
    }

    @Override
    public String toString() {
        return "DroneState[" +
                "flying=" + flying + ", " +
                "videoEnabled=" + videoEnabled + ", " +
                "visionEnabled=" + visionEnabled + ", " +
                "controlAlgorithm=" + controlAlgorithm + ", " +
                "altitudeControlActive=" + altitudeControlActive + ", " +
                "userFeedbackOn=" + userFeedbackOn + ", " +
                "controlReceived=" + controlReceived + ", " +
                "trimReceived=" + trimReceived + ", " +
                "trimRunning=" + trimRunning + ", " +
                "trimSucceeded=" + trimSucceeded + ", " +
                "navDataDemoOnly=" + navDataDemoOnly + ", " +
                "navDataBootstrap=" + navDataBootstrap + ", " +
                "motorsDown=" + motorsDown + ", " +
                "gyrometersDown=" + gyrometersDown + ", " +
                "batteryTooLow=" + batteryTooLow + ", " +
                "batteryTooHigh=" + batteryTooHigh + ", " +
                "timerElapsed=" + timerElapsed + ", " +
                "notEnoughPower=" + notEnoughPower + ", " +
                "anglesOutOfRange=" + anglesOutOfRange + ", " +
                "tooMuchWind=" + tooMuchWind + ", " +
                "ultrasonicSensorDeaf=" + ultrasonicSensorDeaf + ", " +
                "cutoutSystemDetected=" + cutoutSystemDetected + ", " +
                "picVersionNumberOK=" + picVersionNumberOK + ", " +
                "atCodedThreadOn=" + atCodedThreadOn + ", " +
                "navDataThreadOn=" + navDataThreadOn + ", " +
                "videoThreadOn=" + videoThreadOn + ", " +
                "acquisitionThreadOn=" + acquisitionThreadOn + ", " +
                "controlWatchdogDelayed=" + controlWatchdogDelayed + ", " +
                "adcWatchdogDelayed=" + adcWatchdogDelayed + ", " +
                "emergency=" + emergency + ']';
    }


}
