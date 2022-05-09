package tech.fastj.animation;

import java.util.Map;

public interface Animated<TD extends AnimationData> {
    Map<String, TD> getAnimationDataMap();

    default TD getAnimationData(String animationName) {
        return getAnimationDataMap().get(animationName);
    }

    String getCurrentAnimation();

    int getAnimationFPS();

    boolean isPaused();

    void setPaused(boolean paused);

    void stepAnimation(float deltaTime);
}
