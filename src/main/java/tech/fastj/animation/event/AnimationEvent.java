package tech.fastj.animation.event;

import tech.fastj.gameloop.event.Event;
import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;

public abstract class AnimationEvent<TD extends AnimationData, T extends Animated<TD>> extends Event {

    public abstract T getEventSource();
}
