package tech.fastj.gameloop.event;

public record EventObserverCombo<T extends Event>(EventObserver<T> eventObserver, EventBinding<T> eventBinding) {}
