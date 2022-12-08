package me.rochblondiaux.parrot4j.ardrone2.command;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public abstract class ATCommand {

    @Getter
    private final String name;
    @Getter
    private final boolean authenticationNeeded;
    @Getter
    private final int timeOfExecution;
    private final FloatBuffer floatBuffer;
    private final IntBuffer intBuffer;

    public ATCommand(String name, boolean authenticationNeeded, int timeOfExecution) {
        this.name = name;
        this.authenticationNeeded = authenticationNeeded;
        this.timeOfExecution = timeOfExecution;

        final ByteBuffer bb = ByteBuffer.allocate(4);
        this.floatBuffer = bb.asFloatBuffer();
        this.intBuffer = bb.asIntBuffer();
    }

    public ATCommand(String name, boolean authenticationNeeded) {
        this(name, authenticationNeeded, 0);
    }

    public ATCommand(String name, int timeOfExecution) {
        this(name, false, timeOfExecution);
    }

    public ATCommand(String name) {
        this(name, false, -1);
    }

    /**
     * @param sequence The sequence number of the command.
     * @return The command text to send to the drone.
     */
    protected abstract String build(int sequence);

    /**
     * @return The command as a string (For further explanation see the A.R.Drone Developer Guide, p. 30)
     */
    public String buildText(int sequence) {
        return String.format("AT*%s=%s\r", name, build(sequence));
    }

    /**
     * This method converts a float to an int (For further explanation see the A.R.Drone Developer Guide, p. 31)
     *
     * @param f The float that needs to be converted
     * @return The converted float
     */
    protected int intOfFloat(float f) {
        floatBuffer.put(0, f);
        return intBuffer.get(0);
    }
}