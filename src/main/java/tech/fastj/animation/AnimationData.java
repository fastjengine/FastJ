package tech.fastj.animation;

import java.util.Map;
import java.util.function.Predicate;

public interface AnimationData<T extends Animated<T, TD>, TD extends AnimationData<T, TD>> {
    String getAnimationName();

    Map<Predicate<T>, TD> getNextPossibleAnimations();
}
