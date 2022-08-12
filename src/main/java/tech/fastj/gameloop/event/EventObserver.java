package tech.fastj.gameloop.event;

import tech.fastj.gameloop.GameLoop;

/**
 * Observer of {@link Event events} for the {@link GameLoop game loop}.
 * <p>
 * Event observing is a callback feature -- once {@link GameLoop#addEventObserver(EventObserver, Class) added}, it allows you to
 * {@link #eventReceived(Event) receive} events of the registered type {@code T}.
 *
 * @param <T> The type of {@link Event event} to observe.
 * @author Andrew Dey
 * @since 1.7.0
 */
@FunctionalInterface
public interface EventObserver<T extends Event> {
    /**
     * Acts on an {@link Event event} typically received from the {@link GameLoop game loop}.
     *
     * @param event The event received.
     */
    void eventReceived(T event);
}
