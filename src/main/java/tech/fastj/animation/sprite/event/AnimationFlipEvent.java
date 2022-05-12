package tech.fastj.animation.sprite.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.event.AnimationEvent;
import tech.fastj.animation.sprite.SpriteAnimationData;

public class AnimationFlipEvent<TD extends SpriteAnimationData, T extends Animated<TD>> implements AnimationEvent<TD, T> {

    private final T eventSource;
    private final TD animationData;
    private final int oldFrame;
    private final int newFrame;

    public AnimationFlipEvent(T eventSource, TD animationData, int oldFrame, int newFrame) {
        this.eventSource = eventSource;
        this.animationData = animationData;
        this.oldFrame = oldFrame;
        this.newFrame = newFrame;
    }

    @Override
    public T getEventSource() {
        return eventSource;
    }

    public TD getAnimationData() {
        return animationData;
    }

    public int getOldFrame() {
        return oldFrame;
    }

    public int getNewFrame() {
        return newFrame;
    }
}
