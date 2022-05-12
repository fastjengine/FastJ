package tech.fastj.animation.sprite.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.event.AnimationEvent;
import tech.fastj.animation.sprite.SpriteAnimationData;

public class AnimationChangeEvent<TD extends SpriteAnimationData, T extends Animated<TD>> implements AnimationEvent<TD, T> {

    private final T eventSource;
    private final TD oldAnimationData;
    private final int oldFrame;
    private final TD newAnimationData;
    private final int newFrame;

    public AnimationChangeEvent(T eventSource, TD oldAnimationData, int oldFrame, TD newAnimationData, int newFrame) {
        this.eventSource = eventSource;
        this.oldAnimationData = oldAnimationData;
        this.oldFrame = oldFrame;
        this.newAnimationData = newAnimationData;
        this.newFrame = newFrame;
    }

    @Override
    public T getEventSource() {
        return eventSource;
    }

    public TD getOldAnimationData() {
        return oldAnimationData;
    }

    public int getOldFrame() {
        return oldFrame;
    }

    public TD getNewAnimationData() {
        return newAnimationData;
    }

    public int getNewFrame() {
        return newFrame;
    }
}
