package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;

import io.github.lucasstarsz.fastj.example.bullethell.scenes.GameScene;

public class BulletMovement implements Behavior {

    private static final float travelSpeed = 15f;

    private final GameScene gameScene;
    private final float travelAngle;
    private final PlayerCannon playerCannonScript;

    private Pointf travelVector;

    public BulletMovement(GameScene gameScene, float travelAngle, PlayerCannon playerCannonScript) {
        this.travelAngle = travelAngle;
        this.gameScene = gameScene;
        this.playerCannonScript = playerCannonScript;
    }

    @Override
    public void init(GameObject obj) {
        travelVector = new Pointf(0f, travelSpeed).rotate(180 - travelAngle);
    }

    @Override
    public void update(GameObject obj) {
        obj.translate(travelVector);
        if (!FastJEngine.getDisplay().isOnScreen(obj, gameScene.getCamera())) {
            bulletDied(obj);
        }
    }

    @Override
    public void destroy() {
        travelVector.reset();
    }

    public void bulletDied(GameObject obj) {
        FastJEngine.runAfterUpdate(() -> {
            obj.destroy(gameScene);
            playerCannonScript.bulletDied();
        });
    }
}
