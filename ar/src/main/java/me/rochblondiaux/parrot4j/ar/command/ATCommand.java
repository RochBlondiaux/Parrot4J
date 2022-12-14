package me.rochblondiaux.parrot4j.ar.command;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public abstract class ATCommand implements Command {

    @Getter
    private final String name;
    private final FloatBuffer floatBuffer;
    private final IntBuffer intBuffer;
    private final CompletableFuture<Void> callback;

    public ATCommand(String name) {
        this.name = name;
        this.callback = new CompletableFuture<>();
        final ByteBuffer bb = ByteBuffer.allocate(4);
        this.floatBuffer = bb.asFloatBuffer();
        this.intBuffer = bb.asIntBuffer();
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

    public @Nullable String preparationCommandText(int sequenceNumber) {
        return null;
    }

    public boolean hasPreparationCommand() {
        return preparationCommandText(-1) != null;
    }

    @Override
    public CompletableFuture<Void> callback() {
        return callback;
    }
}
