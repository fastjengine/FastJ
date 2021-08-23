package tech.fastj.graphics.game;

import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sprite2D extends GameObject {

    public static final int DefaultStartingFrame = 0;
    public static final int DefaultAnimationFPS = 12;
    public static final AnimationStyle DefaultAnimationStyle = AnimationStyle.ContinuousLoop;

    private BufferedImage[] sprites;
    private int currentFrame;
    private int animationFPS = DefaultAnimationFPS;
    private AnimationStyle animationStyle;

    private ScheduledExecutorService spriteAnimator;

    Sprite2D(BufferedImage[] sprites) {
        this.sprites = sprites;
        setCollisionPath(DrawUtil.createPath(DrawUtil.createBoxFromImage(sprites[0])));
        resetSpriteAnimator();
    }

    public static Sprite2DBuilder create(BufferedImage[] sprites) {
        return new Sprite2DBuilder(sprites);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getAnimationFPS() {
        return animationFPS;
    }

    public AnimationStyle getAnimationStyle() {
        return animationStyle;
    }

    public Sprite2D setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
        return this;
    }

    public Sprite2D setAnimationFPS(int animationFPS) {
        this.animationFPS = animationFPS;
        resetSpriteAnimator();
        return this;
    }

    public Sprite2D setAnimationStyle(AnimationStyle animationStyle) {
        this.animationStyle = animationStyle;
        return this;
    }

    @Override
    public void render(Graphics2D g) {
        if (!shouldRender()) {
            return;
        }

        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        g.transform(getTransformation());

        g.drawImage(sprites[currentFrame], null, null);

        g.setTransform(oldTransform);
    }

    @Override
    public void destroy(Scene origin) {
        spriteAnimator.shutdownNow();
        sprites = null;
        currentFrame = -1;
        animationFPS = -1;
        animationStyle = null;

        super.destroyTheRest(origin);
    }

    @Override
    public void destroy(SimpleManager origin) {
        spriteAnimator.shutdownNow();
        sprites = null;
        currentFrame = -1;
        animationFPS = -1;
        animationStyle = null;

        super.destroyTheRest(origin);
    }

    private void resetSpriteAnimator() {
        if (spriteAnimator != null) {
            spriteAnimator.shutdownNow();
            spriteAnimator = null;
        }

        spriteAnimator = Executors.newSingleThreadScheduledExecutor();
        spriteAnimator.scheduleAtFixedRate(
                () -> {
                    switch (animationStyle) {
                        case Static: {
                            break;
                        }
                        case ContinuousLoop: {
                            currentFrame++;
                            if (currentFrame == sprites.length) {
                                currentFrame = 0;
                            }
                            break;
                        }
                        case PlayUntilEnd: {
                            if (currentFrame < sprites.length - 1) {
                                currentFrame++;
                            }
                            break;
                        }
                    }
                },
                1000 / animationFPS,
                1000 / animationFPS,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        Sprite2D sprite2D = (Sprite2D) other;
        return currentFrame == sprite2D.currentFrame
                && animationFPS == sprite2D.animationFPS
                && animationStyle == sprite2D.animationStyle
                && Arrays.equals(sprites, sprite2D.sprites);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentFrame, animationFPS, animationStyle);
        result = 31 * result + Arrays.hashCode(sprites);
        return result;
    }

    @Override
    public String toString() {
        return "Sprite2D{" +
                "sprites=" + Arrays.toString(sprites) +
                ", currentFrame=" + currentFrame +
                ", animationFPS=" + animationFPS +
                ", animationStyle=" + animationStyle +
                '}';
    }
}
