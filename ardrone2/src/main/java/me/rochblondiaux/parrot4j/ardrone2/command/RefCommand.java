package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class RefCommand extends ATCommand {

    private final int input;

    public RefCommand(int seq, String commandName, int input) {
        super(seq, commandName);
        this.input = input;
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,%d", seq, input);
    }
}
