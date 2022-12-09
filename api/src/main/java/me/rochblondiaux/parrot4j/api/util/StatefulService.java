package me.rochblondiaux.parrot4j.api.util;

import org.jetbrains.annotations.Blocking;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public abstract class StatefulService {

    protected boolean ready = false;

    protected void setReady() {
        this.ready = true;
    }

    protected void setUnready() {
        this.ready = false;
    }

    public boolean isReady() {
        return ready;
    }

    public @Blocking void waitUntilReady() {
        while (!isReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
