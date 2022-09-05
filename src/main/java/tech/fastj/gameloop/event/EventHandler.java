package tech.fastj.gameloop.event;

import tech.fastj.gameloop.GameLoop;

import java.util.List;
import java.util.Queue;

/**
 * Handler of {@link Event events} and their {@link EventObserver observers} for the {@link GameLoop game loop}.
 * <p>
 * Event handling is a callback feature -- once {@link GameLoop#addEventHandler(Class, EventHandler) added}, it allows you to
 * {@link #handleEvents(List, Queue) handle} events and observers of the registered types {@code T} and {@code V}.
 *
 * @param <T> The type of {@link Event event} to handle.
 * @param <V> The type of {@link EventObserver event observer} to handle.
 * @author Andrew Dey
 * @since 1.7.0
 */
@FunctionalInterface
public interface EventHandler<T extends Event, V extends EventObserver<T>> {

    /**
     * Acts on accumulated events and their observers, typically received from the {@link GameLoop game loop}.
     *
     * @param gameEventObservers The observers of the given events.
     * @param gameEvents         The events accumulated.
     */
    default void handleEvents(List<V> gameEventObservers, Queue<T> gameEvents) {
        while (!gameEvents.isEmpty()) {
            T gameEvent = gameEvents.poll();
            handleEvent(gameEventObservers, gameEvent);
        }
    }

    /**
     * Acts on the event and its observers.
     *
     * @param gameEventObservers The observers of the given event, typically received from the {@link GameLoop game loop}.
     * @param gameEvent          The event fired.
     */
    void handleEvent(List<V> gameEventObservers, T gameEvent);
}
