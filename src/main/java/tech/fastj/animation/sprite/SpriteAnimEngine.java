package tech.fastj.animation.sprite;

import tech.fastj.animation.AnimationEngine;
import tech.fastj.graphics.game.Sprite2D;

public class SpriteAnimEngine extends AnimationEngine<Sprite2D, SpriteAnimData> {
    @Override
    public void stepAnimations(float deltaTime) {
        for (Sprite2D sprite : animatedObjects) {
            sprite.stepAnimation(deltaTime);
        }
    }
}
