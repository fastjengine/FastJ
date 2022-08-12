package tech.fastj.gameloop.event;

import tech.fastj.gameloop.GameLoop;

import java.util.concurrent.TimeUnit;

/**
 * General abstraction for all events for the {@link GameLoop game loop}.
 * <p>
 * Every event provides the ability to {@link #consume() be used}, as well as a {@link #getTimestamp() timestamp} of when it was created.
 */
public class Event {

    private boolean isConsumed;
    private final long timestamp;

    /** Initializes event's {@link #isConsumed() consumption state} and {@link #getTimestamp() timestamp}. */
    public Event() {
        isConsumed = false;
        timestamp = System.nanoTime();
    }

    /**
     * {@return whether the event was consumed}
     * <p>
     * This information is primarily used in event {@link EventHandler handlers} and {@link EventObserver observers} to determine whether an
     * event has already been used. If this value is {@code true}, then the event has been used. Otherwise, it is free to use where you can
     * {@link #consume() declare it as used}.
     */
    public boolean isConsumed() {
        return isConsumed;
    }

    /** Declares the event as used. */
    public void consume() {
        isConsumed = true;
    }

    /** {@return the timestamp of when the event occurred, in {@link TimeUnit#NANOSECONDS nanoseconds}} */
    public long getTimestamp() {
        return timestamp;
    }
}
