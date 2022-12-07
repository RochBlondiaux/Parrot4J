package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ModeCommand extends ATCommand {

    private final int param1;

    public ModeCommand(int seq, int param1) {
        super(seq, "PMODE");
        this.param1 = param1;
    }

    public ModeCommand(int seq) {
        this(seq, 2);
    }

    @Override
    protected String parametersToString() {
        return String.format("%d,%d", seq, param1);
    }
}
