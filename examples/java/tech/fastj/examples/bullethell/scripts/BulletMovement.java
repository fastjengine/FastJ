package tech.fastj.examples.bullethell.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.Behavior;

import tech.fastj.examples.bullethell.scenes.GameScene;
import tech.fastj.gameloop.CoreLoopState;

public class BulletMovement implements Behavior {

    private static final float travelSpeed = 500f;

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
        obj.translate(Pointf.multiply(travelVector, FastJEngine.getDeltaTime()));
        if (!FastJEngine.getCanvas().isOnScreen(obj, gameScene.getCamera())) {
            bulletDied(obj);
        }

        Pointf newTranslation = obj.getTranslation();
        if (newTranslation.equals(originalTranslation)) {
            FastJEngine.warning("not moving! bullet at {} with {}f{}", originalTranslation, travelAngle, travelVector);
        }
    }

    @Override
    public void fixedUpdate(GameObject gameObject) {
    }

    @Override
    public void destroy() {
        travelVector.reset();
    }

    public void bulletDied(GameObject obj) {
        FastJEngine.runLater(() -> {
            FastJEngine.log("death! of bullet {}f{}", travelAngle, travelVector);
            obj.destroy(gameScene);
            playerCannonScript.bulletDied();
        }, CoreLoopState.FixedUpdate);
    }
}
