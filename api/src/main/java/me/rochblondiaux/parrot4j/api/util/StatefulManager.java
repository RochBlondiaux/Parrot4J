package me.rochblondiaux.parrot4j.api.util;

import org.jetbrains.annotations.Blocking;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class StatefulManager {

    private volatile boolean ready;

    public StatefulManager() {
        this.ready = false;
    }

    public synchronized boolean isReady() {
        return ready;
    }

    public synchronized void setReady(boolean ready) {
        this.ready = ready;
    }

    public @Blocking void waitUntilReady() {
        while (!isReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
