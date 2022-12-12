package me.rochblondiaux.parrot4j.ar.data;

import me.rochblondiaux.parrot4j.api.model.Rotation;
import me.rochblondiaux.parrot4j.api.model.Speed;
import me.rochblondiaux.parrot4j.api.util.BinaryDataHelper;
import me.rochblondiaux.parrot4j.ar.model.ControlAlgorithm;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class NavigationDataDecoder {

    private final static int CORRECT_HEADER = 0x55667788;

    private static final int NAV_DATA_TAG = 0x0000;

    private static final int NAV_DATA_TAG_CHECKSUM = 0xFFFF;

    private int currentOffset;

    private byte[] buffer;

    private int bufferLength;

    private NavigationData data;

    public NavigationData from(byte[] buffer, int bufferLength) {
        initializeFields(buffer, bufferLength);

        processNavDataHeader(buffer);
        currentOffset += 16;

        while (currentOffset < bufferLength) {
            int tag = BinaryDataHelper.getIntValue(buffer, currentOffset, 2);
            int length = BinaryDataHelper.getIntValue(buffer, currentOffset + 2, 2);
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
        this.data = new NavigationData();
    }

    private void processNavDataHeader(byte[] buffer) {
        if (!isHeaderCorrect())
            throw new IllegalStateException("The header is incorrect");
        int stateFlags = BinaryDataHelper.getIntValue(buffer, currentOffset + 4, 4);
        int sequenceNumber = BinaryDataHelper.getIntValue(buffer, currentOffset + 8, 4);

        data.setSequenceNumber(sequenceNumber);
        data.setState(getDroneState(stateFlags));
    }

    private boolean isHeaderCorrect() {
        int header = BinaryDataHelper.getIntValue(buffer, 0, 4);
        return header == CORRECT_HEADER;
    }

    public DroneState getDroneState(int stateFlags) {
        final DroneState state = new DroneState();

        state.setFlying(BinaryDataHelper.flagSet(stateFlags, 0));
        state.setVideoEnabled(BinaryDataHelper.flagSet(stateFlags, 1));
        state.setVisionEnabled(BinaryDataHelper.flagSet(stateFlags, 2));
        state.setControlAlgorithm(BinaryDataHelper.flagSet(stateFlags, 3) ? ControlAlgorithm.ANGULAR_SPEED_CONTROL : ControlAlgorithm.EULER_ANGLES_CONTROL);
        state.setAltitudeControlActive(BinaryDataHelper.flagSet(stateFlags, 4));
        state.setUserFeedbackOn(BinaryDataHelper.flagSet(stateFlags, 5));
        state.setControlReceived(BinaryDataHelper.flagSet(stateFlags, 6));
        state.setTrimReceived(BinaryDataHelper.flagSet(stateFlags, 7));
        state.setTrimRunning(BinaryDataHelper.flagSet(stateFlags, 8));
        state.setTrimSucceeded(BinaryDataHelper.flagSet(stateFlags, 9));
        state.setNavDataDemoOnly(BinaryDataHelper.flagSet(stateFlags, 10));
        state.setNavDataBootstrap(BinaryDataHelper.flagSet(stateFlags, 11));
        state.setMotorsDown(BinaryDataHelper.flagSet(stateFlags, 12));
        state.setGyrometersDown(BinaryDataHelper.flagSet(stateFlags, 14));
        state.setBatteryTooLow(BinaryDataHelper.flagSet(stateFlags, 15));
        state.setBatteryTooHigh(BinaryDataHelper.flagSet(stateFlags, 16));
        state.setTimerElapsed(BinaryDataHelper.flagSet(stateFlags, 17));
        state.setNotEnoughPower(BinaryDataHelper.flagSet(stateFlags, 18));
        state.setAnglesOutOfRange(BinaryDataHelper.flagSet(stateFlags, 19));
        state.setTooMuchWind(BinaryDataHelper.flagSet(stateFlags, 20));
        state.setUltrasonicSensorDeaf(BinaryDataHelper.flagSet(stateFlags, 21));
        state.setCutoutSystemDetected(BinaryDataHelper.flagSet(stateFlags, 22));
        state.setPicVersionNumberOK(BinaryDataHelper.flagSet(stateFlags, 23));
        state.setAtCodedThreadOn(BinaryDataHelper.flagSet(stateFlags, 24));
        state.setNavDataThreadOn(BinaryDataHelper.flagSet(stateFlags, 25));
        state.setVideoThreadOn(BinaryDataHelper.flagSet(stateFlags, 26));
        state.setAcquisitionThreadOn(BinaryDataHelper.flagSet(stateFlags, 27));
        state.setControlWatchdogDelayed(BinaryDataHelper.flagSet(stateFlags, 28));
        state.setAdcWatchdogDelayed(BinaryDataHelper.flagSet(stateFlags, 29));
        state.setCommunicationProblemOccurred(BinaryDataHelper.flagSet(stateFlags, 30));
        state.setEmergency(BinaryDataHelper.flagSet(stateFlags, 31));

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
        data.setBattery(BinaryDataHelper.getIntValue(buffer, currentOffset + 4, 4));
        data.setRotation(new Rotation(BinaryDataHelper.getFloatValue(buffer, currentOffset + 12, 4) / 1000, BinaryDataHelper.getFloatValue(buffer, currentOffset + 8, 4) / 1000, BinaryDataHelper.getFloatValue(buffer, currentOffset + 16, 4) / 1000));
        data.setAltitude((float) BinaryDataHelper.getIntValue(buffer, currentOffset + 20, 4) / 1000.0f);
        data.setSpeed(new Speed(BinaryDataHelper.getFloatValue(buffer, currentOffset + 24, 4), BinaryDataHelper.getFloatValue(buffer, currentOffset + 28, 4), BinaryDataHelper.getFloatValue(buffer, currentOffset + 32, 4)));
    }

    private void processCheckSum(int checkSumLength) {
        int checksum = calculateCheckSumForReceivedBytes(checkSumLength);
        int checkSumReceived = BinaryDataHelper.getIntValue(buffer, currentOffset, 4);

        if (checksum != checkSumReceived)
            throw new IllegalStateException("The checksum is incorrect");
    }

    private int calculateCheckSumForReceivedBytes(int checkSumLength) {
        int checksum = 0;
        for (int index = 0; index < bufferLength - checkSumLength; index++) {
            checksum += BinaryDataHelper.getUnsignedByteValue(buffer[index]);
        }
        return checksum;
    }
}
