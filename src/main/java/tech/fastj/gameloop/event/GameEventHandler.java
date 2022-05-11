package tech.fastj.gameloop.event;

import java.util.List;
import java.util.Queue;

@FunctionalInterface
public interface GameEventHandler<T extends GameEvent, V extends GameEventObserver<T>> {

    default void handleEvents(List<V> gameEventObservers, Queue<T> gameEvents) {
        while (!gameEvents.isEmpty()) {
            T gameEvent = gameEvents.poll();
            handleEvent(gameEventObservers, gameEvent);
        }
    }

    void handleEvent(List<V> gameEventObservers, T gameEvent);
}
