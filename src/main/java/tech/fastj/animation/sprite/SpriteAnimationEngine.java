package tech.fastj.animation.sprite;

import tech.fastj.graphics.game.Sprite2D;

import tech.fastj.animation.AnimationEngine;

public class SpriteAnimationEngine extends AnimationEngine<SpriteAnimationData, Sprite2D> {
    @Override
    public void stepAnimations(float deltaTime) {
        for (Sprite2D sprite : animatedObjects) {
            sprite.stepAnimation(deltaTime);
        }
    }
}
