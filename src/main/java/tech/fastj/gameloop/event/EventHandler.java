package tech.fastj.gameloop.event;

import java.util.List;
import java.util.Queue;

@FunctionalInterface
public interface EventHandler<T extends Event, V extends EventObserver<T>> {

    default void handleEvents(List<V> gameEventObservers, Queue<T> gameEvents) {
        while (!gameEvents.isEmpty()) {
            T gameEvent = gameEvents.poll();
            handleEvent(gameEventObservers, gameEvent);
        }
    }

    void handleEvent(List<V> gameEventObservers, T gameEvent);
}
