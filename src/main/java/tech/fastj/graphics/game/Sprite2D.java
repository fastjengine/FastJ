package tech.fastj.graphics.game;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.resources.images.ImageResource;
import tech.fastj.resources.images.ImageUtil;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Map;

import tech.fastj.animation.Animated;
import tech.fastj.animation.AnimationStyle;
import tech.fastj.animation.event.AnimationEvent;
import tech.fastj.animation.sprite.SpriteAnimationData;
import tech.fastj.animation.sprite.event.AnimationChangeEvent;
import tech.fastj.animation.sprite.event.AnimationFlipEvent;
import tech.fastj.animation.sprite.event.AnimationLoopEvent;
import tech.fastj.animation.sprite.event.AnimationPauseEvent;
import tech.fastj.animation.sprite.event.AnimationPlayEvent;

public class Sprite2D extends GameObject implements Animated<SpriteAnimationData> {

    public static final int DefaultStartingFrame = 0;
    public static final int DefaultAnimationFPS = 12;
    public static final int DefaultHorizontalImageCount = 1;
    public static final int DefaultVerticalImageCount = 1;
    public static final String NoAnimation = "No Current Animation";

    private static final Map<String, SpriteAnimationData> NoAnimationsLoaded = Map.of(
            NoAnimation, new SpriteAnimationData(NoAnimation, AnimationStyle.Static, 0, 0)
    );

    private static final BufferedImage[] NoSpritesLoaded = {
            ImageUtil.createBufferedImage(16, 16)
    };

    private final Map<String, SpriteAnimationData> animationDataMap;

    private final ImageResource spritesResource;
    private BufferedImage[] sprites;
    private String currentAnimation;
    private volatile float currentFrame;
    private volatile int animationFPS;
    private volatile boolean paused;

    Sprite2D(ImageResource spritesResource, int horizontalImageCount, int verticalImageCount, Map<String, SpriteAnimationData> animationDataMap) {
        this.spritesResource = spritesResource;
        this.animationDataMap = animationDataMap;
        this.paused = true;

        if (this.animationDataMap.isEmpty()) {
            animationDataMap.putAll(NoAnimationsLoaded);
            Log.warn(
                    Sprite2D.class,
                    "No animations were loaded from Sprite2D created from resource at path \"{}\".",
                    spritesResource.getPath().toAbsolutePath()
            );
        }

        resetSpriteSheet(horizontalImageCount, verticalImageCount);
        setCollisionPath(DrawUtil.createPath(DrawUtil.createBoxFromImage(sprites[0])));
    }

    public static Sprite2DBuilder create(Path spriteResourcePath) {
        ImageResource spriteResource = FastJEngine.getResourceManager(ImageResource.class).loadResource(spriteResourcePath);
        return create(spriteResource);
    }

    public static Sprite2DBuilder create(Path spriteResourcePath, boolean shouldRender) {
        ImageResource spriteResource = FastJEngine.getResourceManager(ImageResource.class).loadResource(spriteResourcePath);
        return create(spriteResource, shouldRender);
    }

    public static Sprite2DBuilder create(ImageResource spritesResource) {
        return new Sprite2DBuilder(spritesResource, Drawable.DefaultShouldRender);
    }

    public static Sprite2DBuilder create(ImageResource spritesResource, boolean shouldRender) {
        return new Sprite2DBuilder(spritesResource, shouldRender);
    }

    public static Sprite2D fromImageResource(ImageResource spritesResource) {
        return new Sprite2DBuilder(spritesResource, Drawable.DefaultShouldRender).build();
    }

    public static Sprite2D fromPath(Path spriteResourcePath) {
        return create(spriteResourcePath).build();
    }

    public void reloadSpriteResource(int horizontalImageCount, int verticalImageCount, Map<String, SpriteAnimationData> animationDataMap) {
        resetSpriteSheet(horizontalImageCount, verticalImageCount);
        if (animationDataMap != null) {
            this.animationDataMap.clear();
            this.animationDataMap.putAll(animationDataMap);
        }
    }

    public float getCurrentFrame() {
        return currentFrame;
    }

    public Sprite2D setAnimationFPS(int animationFPS) {
        this.animationFPS = animationFPS;
        return this;
    }

    public Sprite2D setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
        return this;
    }

    public Sprite2D setCurrentAnimation(String currentAnimation) {
        if (currentAnimation == null || animationDataMap.get(currentAnimation) == null) {
            throw new IllegalArgumentException(
                    "Could not find an animation named " + currentAnimation
                            + " in animation from \"" + spritesResource.getPath().toAbsolutePath() + "\"."
            );
        } else {
            this.currentAnimation = currentAnimation;
            this.currentFrame = animationDataMap.get(currentAnimation).getFirstFrame();
        }
        return this;
    }

    private void resetSpriteSheet(int horizontalImageCount, int verticalImageCount) {
        spritesResource.unload();
        spritesResource.load();
        sprites = ImageUtil.createSpriteSheet(spritesResource.get(), horizontalImageCount, verticalImageCount);
    }

    @Override
    public Map<String, SpriteAnimationData> getAnimationDataMap() {
        return animationDataMap;
    }

    @Override
    public int getAnimationFPS() {
        return animationFPS;
    }

    @Override
    public String getCurrentAnimation() {
        return currentAnimation;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void setPaused(boolean paused) {
        AnimationEvent<SpriteAnimationData, Sprite2D> animationEvent = null;

        if (this.paused && !paused) {
            animationEvent = new AnimationPlayEvent<>(
                    this,
                    animationDataMap.get(currentAnimation),
                    (int) currentFrame
            );
        } else if (!this.paused && paused) {
            animationEvent = new AnimationPauseEvent<>(
                    this,
                    animationDataMap.get(currentAnimation),
                    (int) currentFrame
            );
        }

        this.paused = paused;

        if (animationEvent != null) {
            FastJEngine.getGameLoop().fireEvent(animationEvent);
        }
    }

    @Override
    public void stepAnimation(float deltaTime) {
        if (paused) {
            return;
        }

        synchronized (this) {
            float nextFrame = currentFrame + (deltaTime * animationFPS);

            // check if animation needs to be changed
            SpriteAnimationData currentAnimationData = animationDataMap.get(currentAnimation);
            for (var needsAnimationSwitch : currentAnimationData.getNextPossibleAnimations().entrySet()) {
                if (needsAnimationSwitch.getKey().test(this)) {
                    SpriteAnimationData nextAnimationData = needsAnimationSwitch.getValue();
                    AnimationChangeEvent<SpriteAnimationData, Sprite2D> animationChangeEvent = new AnimationChangeEvent<>(
                            this,
                            currentAnimationData,
                            (int) currentFrame,
                            nextAnimationData,
                            nextAnimationData.getFirstFrame()
                    );
                    currentAnimation = nextAnimationData.getAnimationName();
                    currentFrame = nextAnimationData.getFirstFrame();
                    FastJEngine.getGameLoop().fireEvent(animationChangeEvent);
                    return;
                }
            }

            // otherwise, account for animation style
            switch (currentAnimationData.getAnimationStyle()) {
                case ContinuousLoop: {
                    if ((int) nextFrame > currentAnimationData.getLastFrame()) {
                        nextFrame = currentAnimationData.getFirstFrame();
                        AnimationLoopEvent<SpriteAnimationData, Sprite2D> animationLoopEvent = new AnimationLoopEvent<>(
                                this,
                                currentAnimationData
                        );
                        FastJEngine.getGameLoop().fireEvent(animationLoopEvent);
                    }
                    break;
                }
                case PlayUntilEnd: {
                    if ((int) nextFrame >= currentAnimationData.getLastFrame()) {
                        nextFrame = currentAnimationData.getLastFrame();
                    }
                    break;
                }
                case Static: {
                    return;
                }
            }

            AnimationFlipEvent<SpriteAnimationData, Sprite2D> animationFlipEvent = null;

            if ((int) nextFrame > (int) currentFrame) {
                animationFlipEvent = new AnimationFlipEvent<>(
                        this,
                        currentAnimationData,
                        (int) currentFrame,
                        (int) nextFrame
                );
            }

            currentFrame = nextFrame;

            if (animationFlipEvent != null) {
                FastJEngine.getGameLoop().fireEvent(animationFlipEvent);
            }
        }
    }

    @Override
    public void destroy(Scene origin) {
        setPaused(true);

        animationDataMap.clear();
        animationDataMap.putAll(NoAnimationsLoaded);
        currentAnimation = NoAnimation;
        sprites = NoSpritesLoaded;
        currentFrame = DefaultStartingFrame;
        animationFPS = DefaultAnimationFPS;

        super.destroyTheRest(origin);
    }

    @Override
    public void destroy(SimpleManager origin) {
        setPaused(true);

        animationDataMap.clear();
        animationDataMap.putAll(NoAnimationsLoaded);
        currentAnimation = NoAnimation;
        sprites = NoSpritesLoaded;
        currentFrame = DefaultStartingFrame;
        animationFPS = DefaultAnimationFPS;

        super.destroyTheRest(origin);
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        g.transform(getTransformation());

        g.drawImage(sprites[(int) currentFrame], null, null);

        g.setTransform(oldTransform);
    }
}
