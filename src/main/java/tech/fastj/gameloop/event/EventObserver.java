package tech.fastj.gameloop.event;

@FunctionalInterface
public interface EventObserver<T extends Event> {
    void eventReceived(T event);
}
