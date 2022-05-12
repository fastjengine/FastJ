package tech.fastj.animation.sprite.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.event.AnimationEvent;
import tech.fastj.animation.sprite.SpriteAnimationData;

public class AnimationPlayEvent<TD extends SpriteAnimationData, T extends Animated<TD>> extends AnimationEvent<TD, T> {

    private final T eventSource;
    private final TD animationData;
    private final int playFrame;

    public AnimationPlayEvent(T eventSource, TD animationData, int playFrame) {
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
