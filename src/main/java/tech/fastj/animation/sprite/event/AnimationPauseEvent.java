package tech.fastj.animation.sprite.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.event.AnimationEvent;
import tech.fastj.animation.sprite.SpriteAnimationData;

public class AnimationPauseEvent<TD extends SpriteAnimationData, T extends Animated<TD>> implements AnimationEvent<TD, T> {

    private final T eventSource;
    private final TD animationData;
    private final int pauseFrame;

    public AnimationPauseEvent(T eventSource, TD animationData, int pauseFrame) {
        this.eventSource = eventSource;
        this.animationData = animationData;
        this.pauseFrame = pauseFrame;
    }

    @Override
    public T getEventSource() {
        return eventSource;
    }

    public TD getAnimationData() {
        return animationData;
    }

    public int getPauseFrame() {
        return pauseFrame;
    }
}
