package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;
import tech.fastj.gameloop.event.Event;

public abstract class AnimationEvent<TD extends AnimationData, T extends Animated<TD>> extends Event {

    public abstract T getEventSource();
}
