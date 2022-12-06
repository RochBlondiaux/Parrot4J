package me.rochblondiaux.parrot4j.components;

import com.google.common.collect.Sets;
import lombok.NoArgsConstructor;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;

import java.util.Set;

@NoArgsConstructor

public class ReadyStateListenerComponent {
    private final Set<ReadyStateChangeListener> readyStateChangeListeners = Sets.newHashSet();

    public void addReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateChangeListeners.add(readyStateChangeListener);
    }

    public void removeReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateChangeListeners.remove(readyStateChangeListener);
    }

    public void emitReadyStateChange(ReadyStateChangeListener.ReadyState readyState) {
        for (ReadyStateChangeListener listener : readyStateChangeListeners) {
            listener.onReadyStateChange(readyState);
        }
    }
}