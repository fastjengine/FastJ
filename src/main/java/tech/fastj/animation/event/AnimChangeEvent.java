package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;

public class AnimChangeEvent<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> extends AnimEvent<T, TD> {

    private final T eventSource;
    private final TD oldAnimationData;
    private final int oldFrame;
    private final TD newAnimationData;
    private final int newFrame;

    public AnimChangeEvent(T eventSource, TD oldAnimationData, int oldFrame, TD newAnimationData, int newFrame) {
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
