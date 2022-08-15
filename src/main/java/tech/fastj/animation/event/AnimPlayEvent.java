package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;

public class AnimPlayEvent<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> extends AnimEvent<T, TD> {

    private final T eventSource;
    private final TD animationData;
    private final int playFrame;

    public AnimPlayEvent(T eventSource, TD animationData, int playFrame) {
        this.eventSource = eventSource;
        this.animationData = animationData;
        this.playFrame = playFrame;
    }

    @Override
    public T getEventSource() {
        return eventSource;
    }

    public TD getAnimationData() {
        return animationData;
    }

    public int getPlayFrame() {
        return playFrame;
    }
}
