package me.rochblondiaux.parrot4j.api.event;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface CancellableEvent extends Event {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

}
