package me.rochblondiaux.parrot4j.commands;

import java.util.Collection;

public interface ComposedCommand extends Command {
    Collection<Command> getCommands();
}
