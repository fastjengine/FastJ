package tech.fastj.example.bullethell.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.Behavior;

import tech.fastj.example.bullethell.scenes.GameScene;

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
        Pointf originalTranslation = obj.getTranslation();
        obj.translate(travelVector);
        if (!FastJEngine.getDisplay().isOnScreen(obj, gameScene.getCamera())) {
            bulletDied(obj);
        }

        Pointf newTranslation = obj.getTranslation();
        if (newTranslation.equals(originalTranslation)) {
            FastJEngine.warning("not moving! bullet at " + originalTranslation + " with " + travelAngle + "f " + travelVector);
        }
    }

    @Override
    public void destroy() {
        travelVector.reset();
    }

    public void bulletDied(GameObject obj) {
        FastJEngine.runAfterUpdate(() -> {
            FastJEngine.log("death! of bullet " + travelAngle + "f " + travelVector);
            obj.destroy(gameScene);
            playerCannonScript.bulletDied();
        });
    }
}
