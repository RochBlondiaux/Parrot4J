package me.rochblondiaux.parrot4j.ardrone2.data;

import me.rochblondiaux.parrot4j.api.event.Rotation;
import me.rochblondiaux.parrot4j.api.event.Speed;
import me.rochblondiaux.parrot4j.ardrone2.model.ControlAlgorithm;

import static me.rochblondiaux.parrot4j.ardrone2.utils.BinaryDataHelper.*;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class DataDecoder {

    private final static int CORRECT_HEADER = 0x55667788;

    private static final int NAV_DATA_TAG = 0x0000;

    private static final int NAV_DATA_TAG_CHECKSUM = 0xFFFF;

    private int currentOffset;

    private byte[] buffer;

    private int bufferLength;

    private DroneData data;

    public DroneData from(byte[] buffer, int bufferLength) {
        initializeFields(buffer, bufferLength);

        processNavDataHeader(buffer);
        currentOffset += 16;

        while (currentOffset < bufferLength) {
            int tag = getIntValue(buffer, currentOffset, 2);
            int length = getIntValue(buffer, currentOffset + 2, 2);
            currentOffset += 4;

            if (length == 0)
                throw new IllegalStateException("Got a zero length tag");
            processTag(tag, length);

            currentOffset += length - 4;
        }

        return data;
    }

    private void initializeFields(byte[] buffer, int bufferLength) {
        this.buffer = buffer;
        this.bufferLength = bufferLength;
        this.currentOffset = 0;
        this.data = new DroneData();
    }

    private void processNavDataHeader(byte[] buffer) {
        if (!isHeaderCorrect())
            throw new IllegalStateException("The header is incorrect");
        int stateFlags = getIntValue(buffer, currentOffset + 4, 4);
        int sequenceNumber = getIntValue(buffer, currentOffset + 8, 4);

        data.setSequenceNumber(sequenceNumber);
        data.setState(getDroneState(stateFlags));
    }

    private boolean isHeaderCorrect() {
        int header = getIntValue(buffer, 0, 4);
        return header == CORRECT_HEADER;
    }

    public DroneState getDroneState(int stateFlags) {
        final DroneState state = new DroneState();

        state.setFlying(flagSet(stateFlags, 0));
        state.setVideoEnabled(flagSet(stateFlags, 1));
        state.setVisionEnabled(flagSet(stateFlags, 2));
        state.setControlAlgorithm(flagSet(stateFlags, 3) ? ControlAlgorithm.ANGULAR_SPEED_CONTROL : ControlAlgorithm.EULER_ANGLES_CONTROL);
        state.setAltitudeControlActive(flagSet(stateFlags, 4));
        state.setUserFeedbackOn(flagSet(stateFlags, 5));
        state.setControlReceived(flagSet(stateFlags, 6));
        state.setTrimReceived(flagSet(stateFlags, 7));
        state.setTrimRunning(flagSet(stateFlags, 8));
        state.setTrimSucceeded(flagSet(stateFlags, 9));
        state.setNavDataDemoOnly(flagSet(stateFlags, 10));
        state.setNavDataBootstrap(flagSet(stateFlags, 11));
        state.setMotorsDown(flagSet(stateFlags, 12));
        state.setGyrometersDown(flagSet(stateFlags, 14));
        state.setBatteryTooLow(flagSet(stateFlags, 15));
        state.setBatteryTooHigh(flagSet(stateFlags, 16));
        state.setTimerElapsed(flagSet(stateFlags, 17));
        state.setNotEnoughPower(flagSet(stateFlags, 18));
        state.setAnglesOutOfRange(flagSet(stateFlags, 19));
        state.setTooMuchWind(flagSet(stateFlags, 20));
        state.setUltrasonicSensorDeaf(flagSet(stateFlags, 21));
        state.setCutoutSystemDetected(flagSet(stateFlags, 22));
        state.setPicVersionNumberOK(flagSet(stateFlags, 23));
        state.setAtCodedThreadOn(flagSet(stateFlags, 24));
        state.setNavDataThreadOn(flagSet(stateFlags, 25));
        state.setVideoThreadOn(flagSet(stateFlags, 26));
        state.setAcquisitionThreadOn(flagSet(stateFlags, 27));
        state.setControlWatchdogDelayed(flagSet(stateFlags, 28));
        state.setAdcWatchdogDelayed(flagSet(stateFlags, 29));
        state.setCommunicationProblemOccurred(flagSet(stateFlags, 30));
        state.setEmergency(flagSet(stateFlags, 31));

        return state;
    }

    private void processTag(int tag, int length) {
        if (tag == NAV_DATA_TAG) {
            data.setOnlyHeaderPresent(false);
            processNavData();
        } else if (tag == NAV_DATA_TAG_CHECKSUM)
            processCheckSum(length);
    }

    private void processNavData() {
        data.setBattery(getIntValue(buffer, currentOffset + 4, 4));
        data.setRotation(new Rotation(getFloatValue(buffer, currentOffset + 12, 4) / 1000, getFloatValue(buffer, currentOffset + 8, 4) / 1000, getFloatValue(buffer, currentOffset + 16, 4) / 1000));
        data.setAltitude((float) getIntValue(buffer, currentOffset + 20, 4) / 1000.0f);
        data.setSpeed(new Speed(getFloatValue(buffer, currentOffset + 24, 4), getFloatValue(buffer, currentOffset + 28, 4), getFloatValue(buffer, currentOffset + 32, 4)));
    }

    private void processCheckSum(int checkSumLength) {
        int checksum = calculateCheckSumForReceivedBytes(checkSumLength);
        int checkSumReceived = getIntValue(buffer, currentOffset, 4);

        if (checksum != checkSumReceived)
            throw new IllegalStateException("The checksum is incorrect");
    }

    private int calculateCheckSumForReceivedBytes(int checkSumLength) {
        int checksum = 0;
        for (int index = 0; index < bufferLength - checkSumLength; index++) {
            checksum += getUnsignedByteValue(buffer[index]);
        }
        return checksum;
    }
}
