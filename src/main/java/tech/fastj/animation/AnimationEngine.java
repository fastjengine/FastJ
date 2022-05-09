package tech.fastj.animation;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimationEngine<TD extends AnimationData, T extends Animated<TD>> {

    protected final List<T> animatedObjects;

    protected AnimationEngine() {
        animatedObjects = new ArrayList<>();
    }

    public void addAnimated(T animated) {
        animatedObjects.add(animated);
    }

    public void removeAnimated(T animated) {
        animatedObjects.remove(animated);
    }

    public abstract void stepAnimations(float deltaTime);
}
