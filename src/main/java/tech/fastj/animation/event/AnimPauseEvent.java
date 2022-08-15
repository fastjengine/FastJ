package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;

public class AnimPauseEvent<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> extends AnimEvent<T, TD> {

    private final T eventSource;
    private final TD animationData;
    private final int pauseFrame;

    public AnimPauseEvent(T eventSource, TD animationData, int pauseFrame) {
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
