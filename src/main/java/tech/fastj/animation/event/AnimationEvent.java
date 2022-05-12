package tech.fastj.animation.event;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;
import tech.fastj.gameloop.event.GameEvent;

public interface AnimationEvent<TD extends AnimationData, T extends Animated<TD>> extends GameEvent {

    T getEventSource();
}
