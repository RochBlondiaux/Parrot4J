package me.rochblondiaux.parrot4j.components;

import com.google.common.collect.Sets;
import lombok.NoArgsConstructor;
import me.rochblondiaux.parrot4j.listeners.ErrorListener;

import java.util.Set;

@NoArgsConstructor
public class ErrorListenerComponent {
    private final Set<ErrorListener> errorListeners = Sets.newHashSet();

    public void addErrorListener(ErrorListener errorListener) {
        errorListeners.add(errorListener);
    }

    public void removeErrorListener(ErrorListener errorListener) {
        errorListeners.remove(errorListener);
    }

    public void emitError(Throwable e) {
        for (ErrorListener listener : errorListeners)
            listener.onError(e);
    }
}