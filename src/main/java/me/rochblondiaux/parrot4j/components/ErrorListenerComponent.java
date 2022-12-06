package me.rochblondiaux.parrot4j.components;

import com.google.common.collect.Sets;
import me.rochblondiaux.parrot4j.listeners.ErrorListener;

import java.util.Set;

public class ErrorListenerComponent {
    private final Set<ErrorListener> errorListeners;

    public ErrorListenerComponent() {
        errorListeners = Sets.newHashSet();
    }

    public void addErrorListener(ErrorListener errorListener) {
        if (!errorListeners.contains(errorListener)) {
            errorListeners.add(errorListener);
        }
    }

    public void removeErrorListener(ErrorListener errorListener) {
        if (errorListeners.contains(errorListener)) {
            errorListeners.remove(errorListener);
        }
    }

    public void emitError(Throwable e) {
        for (ErrorListener listener : errorListeners) {
            listener.onError(e);
        }
    }
}