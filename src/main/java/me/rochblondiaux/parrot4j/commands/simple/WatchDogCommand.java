package me.rochblondiaux.parrot4j.commands.simple;

public class WatchDogCommand extends ATCommandAbstract {
    public WatchDogCommand() {
        super(false);
    }

    @Override
    protected String getCommand(int sequenceNumber) {
        return "AT*COMWDG=%d".formatted(sequenceNumber);
    }
}
