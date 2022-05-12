package tech.fastj.animation.sprite.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.event.AnimationEvent;
import tech.fastj.animation.sprite.SpriteAnimationData;

public class AnimationLoopEvent<TD extends SpriteAnimationData, T extends Animated<TD>> implements AnimationEvent<TD, T> {

    private final T eventSource;
    private final TD animationData;

    public AnimationLoopEvent(T eventSource, TD animationData) {
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
