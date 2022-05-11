package tech.fastj.gameloop.event;

@FunctionalInterface
public interface GameEventObserver<T extends GameEvent> {
    void eventReceived(T event);
}
