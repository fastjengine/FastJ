package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;
import tech.fastj.gameloop.event.Event;

public abstract class AnimEvent<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> extends Event {

    public abstract T getEventSource();
}
