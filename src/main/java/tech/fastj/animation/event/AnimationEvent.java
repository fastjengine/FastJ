package tech.fastj.animation.event;

import tech.fastj.gameloop.event.GameEvent;
import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationData;

public interface AnimationEvent<TD extends AnimationData, T extends Animated<TD>> extends GameEvent {

    T getEventSource();
}
