package org.infernalstudios.questlog.event;

import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.event.events.QuestEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Simple event bus for Questlog events.
 * <p>
 * This class is basically a stripped down reimplementation of Forge's event bus.
 */
public class QuestlogEventBus {
    private final Map<Class<?>, List<Consumer<? extends QuestEvent>>> listeners = new HashMap<>();

    /**
     * Registers a listener against an explicit event class.
     *
     * The upstream convenience overload inferred the generic Consumer argument at
     * runtime through TypeTools. OVERLORD QUESTS owns all registrations on this
     * private bus and uses explicit event classes instead, avoiding an otherwise
     * unnecessary runtime library dependency and making listener ownership clear.
     */
    public <T extends QuestEvent> void addListener(Class<T> eventClass, Consumer<T> listener) {
        if (!QuestEvent.class.isAssignableFrom(eventClass)) {
            Questlog.LOGGER.warn("Registering an event of class {} which is not a subclass of QuestEvent", eventClass);
        }

        List<Consumer<? extends QuestEvent>> listeners = this.listeners.computeIfAbsent(eventClass, k -> new ArrayList<>(1));
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Removes one exact listener registration without disturbing listeners owned
     * by other active quest instances.
     */
    public <T extends QuestEvent> void removeListener(Class<T> eventClass, Consumer<T> listener) {
        List<Consumer<? extends QuestEvent>> listeners = this.listeners.get(eventClass);
        if (listeners == null) return;

        listeners.remove(listener);
        if (listeners.isEmpty()) {
            this.listeners.remove(eventClass);
        }
    }

    public <T extends QuestEvent> void post(T event) {
        List<Consumer<? extends QuestEvent>> listeners = this.listeners.get(event.getClass());
        if (listeners != null) {
            // Snapshot the list so a callback can cause quest disposal/reload
            // without invalidating iteration over the current event delivery.
            for (Consumer<? extends QuestEvent> listener : List.copyOf(listeners)) {
                //noinspection unchecked
                ((Consumer<T>) listener).accept(event);
            }
        }
    }

    public void removeAllListeners() {
        this.listeners.clear();
    }
}
