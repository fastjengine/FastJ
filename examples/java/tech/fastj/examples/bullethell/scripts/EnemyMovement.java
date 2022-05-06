package tech.fastj.examples.bullethell.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;

import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.tags.TagManager;

import java.util.List;

import tech.fastj.examples.bullethell.scenes.GameScene;
import tech.fastj.examples.bullethell.util.Tags;

public class EnemyMovement implements Behavior {

    private final GameScene gameScene;

    private Model2D player;
    private Polygon2D playerHealthBar;

    public EnemyMovement(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void init(GameObject obj) {
        player = (Model2D) TagManager.getAllWithTag(Tags.Player).get(0);
        playerHealthBar = (Polygon2D) TagManager.getAllWithTag(Tags.PlayerHealthBar).get(0);
    }

    @Override
    public void fixedUpdate(GameObject obj) {
        checkCollisions(obj);
        moveToPlayer(obj);
    }

    @Override
    public void update(GameObject gameObject) {
        // Empty -- this example does not make use of this method.
    }

    private void checkCollisions(GameObject obj) {
        if (obj.collidesWith(player)) {
            ((PlayerHealthBar) playerHealthBar.getBehaviors().get(0)).takeDamage();
        }

        List<Drawable> bullets = TagManager.getAllWithTag(Tags.Bullet);
        for (Drawable bullet : bullets) {
            if (obj.collidesWith(bullet)) {
                BulletMovement bulletMovementScript = (BulletMovement) ((GameObject) bullet).getBehaviors().get(0);
                bulletMovementScript.bulletDied((GameObject) bullet);
                enemyDied(obj);
            }
        }
    }

    private void enemyDied(GameObject enemy) {
        FastJEngine.runAfterUpdate(() -> gameScene.enemyDied((Model2D) enemy));
    }

    private void moveToPlayer(GameObject obj) {
        Pointf travelDistance = Pointf.subtract(player.getCenter(), obj.getCenter())
                .normalized()
                .multiply(2f);
        obj.translate(travelDistance);
    }
}
