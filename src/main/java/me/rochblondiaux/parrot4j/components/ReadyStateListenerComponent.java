package me.rochblondiaux.parrot4j.components;

import com.google.common.collect.Sets;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;

import java.util.Set;

public class ReadyStateListenerComponent {
    private final Set<ReadyStateChangeListener> readyStateChangeListeners;

    public ReadyStateListenerComponent() {
        readyStateChangeListeners = Sets.newHashSet();
    }

    public void addReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        if (!readyStateChangeListeners.contains(readyStateChangeListener)) {
            readyStateChangeListeners.add(readyStateChangeListener);
        }
    }

    public void removeReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        if (readyStateChangeListeners.contains(readyStateChangeListener)) {
            readyStateChangeListeners.remove(readyStateChangeListener);
        }
    }

    public void emitReadyStateChange(ReadyStateChangeListener.ReadyState readyState) {
        for (ReadyStateChangeListener listener : readyStateChangeListeners) {
            listener.onReadyStateChange(readyState);
        }
    }
}