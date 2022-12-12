package me.rochblondiaux.parrot4j.ar.command;

import java.util.List;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface ComposedCommand extends Command {

    List<Command> commands();

}
