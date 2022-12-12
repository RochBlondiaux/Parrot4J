package me.rochblondiaux.parrot4j.ar.command;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public abstract class ComposedCommand implements Command {

    private final CompletableFuture<Void> callback;

    public ComposedCommand() {
        this.callback = new CompletableFuture<>();
    }
    public abstract List<Command> commands();


    @Override
    public CompletableFuture<Void> callback() {
        return callback;
    }
}
