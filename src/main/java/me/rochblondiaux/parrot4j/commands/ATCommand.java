package me.rochblondiaux.parrot4j.commands;

public interface ATCommand extends SimpleCommand {
    String getCommandText(int sequenceNumber);

    String getPreparationCommandText(int sequenceNumber);

    boolean isPreparationCommandNeeded();
}