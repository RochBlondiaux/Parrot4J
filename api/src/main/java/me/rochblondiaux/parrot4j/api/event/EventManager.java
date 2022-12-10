package me.rochblondiaux.parrot4j.api.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class EventManager {

    private final Map<Class<? extends Event>, List<Consumer<Event>>> listeners = new HashMap<>();

    public <E extends Event> EventManager addListener(Class<E> eventClass, Consumer<E> listener) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add((Consumer<Event>) listener);
    }

    public void call(Event event) {
        if (event instanceof CancellableEvent)
            callCancellable((CancellableEvent) event, event1 -> {
            });
        else
            listeners.get(event.getClass()).forEach(listener -> listener.accept(event));
    }

    public void callCancellable(CancellableEvent event, Consumer<Event> callback) {
        for (Consumer<Event> eventConsumer : listeners.get(event.getClass())) {
            if (event.isCancelled())
                break;
            eventConsumer.accept(event);
        }
        if (!event.isCancelled())
            callback.accept(event);
    }
}
