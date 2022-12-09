package me.rochblondiaux.parrot4j.api.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class EventService {

    private static final Map<Class<Event>, List<Consumer<Event>>> listeners = new HashMap<>();

    public static <E extends Event> void addListener(Class<E> eventClass, Consumer<E> listener) {
        listeners.computeIfAbsent((Class<Event>) eventClass, k -> new ArrayList<>()).add((Consumer<Event>) listener);
    }

    public static void call(Event event) {
        if (event instanceof CancellableEvent cancellableEvent)
            callCancellable(cancellableEvent, event1 -> {
            });
        else
            listeners.get(event.getClass()).forEach(listener -> listener.accept(event));
    }

    public static void callCancellable(CancellableEvent event, Consumer<Event> callback) {
        for (Consumer<Event> eventConsumer : listeners.get(event.getClass())) {
            if (event.isCancelled())
                break;
            eventConsumer.accept(event);
        }
        if (!event.isCancelled())
            callback.accept(event);
    }
}
