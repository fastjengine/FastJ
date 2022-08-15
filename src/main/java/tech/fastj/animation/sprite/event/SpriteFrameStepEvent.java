package tech.fastj.animation.sprite.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;
import tech.fastj.animation.event.AnimEvent;

public class SpriteFrameStepEvent<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> extends AnimEvent<T, TD> {

    private final T eventSource;
    private final TD animationData;
    private final int oldFrame;
    private final int newFrame;

    public SpriteFrameStepEvent(T eventSource, TD animationData, int oldFrame, int newFrame) {
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
