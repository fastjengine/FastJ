package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;

public class AnimLoopEvent<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> extends AnimEvent<T, TD> {

    private final T eventSource;
    private final TD animationData;

    public AnimLoopEvent(T eventSource, TD animationData) {
        this.eventSource = eventSource;
        this.animationData = animationData;
    }

    @Override
    public T getEventSource() {
        return eventSource;
    }

    public TD getAnimationData() {
        return animationData;
    }
}
