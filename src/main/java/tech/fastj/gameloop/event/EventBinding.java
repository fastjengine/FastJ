package tech.fastj.gameloop.event;

@FunctionalInterface
public interface EventBinding<T extends Event> {
    boolean isRelevant(T event);
}
