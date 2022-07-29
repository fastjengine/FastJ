package tech.fastj.examples.bullethell.scripts;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.control.SceneManager;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tech.fastj.examples.bullethell.util.SceneNames;
import tech.fastj.gameloop.CoreLoopState;

public class PlayerHealthBar implements Behavior {

    private static final int initialHealth = 100;

    private int health;
    private boolean takenDamage;
    private boolean canTakeDamage;

    private final ScheduledExecutorService damageCooldown = Executors.newSingleThreadScheduledExecutor();
    private final Text2D playerMetadata;

    public PlayerHealthBar(Text2D playerMetadata) {
        this.playerMetadata = Objects.requireNonNull(playerMetadata);
    }

    @Override
    public void init(GameObject obj) {
        health = initialHealth;
        takenDamage = false;
        canTakeDamage = true;
    }

    @Override
    public void fixedUpdate(GameObject obj) {
        if (obj instanceof Polygon2D && takenDamage) {
            Polygon2D polygon2D = (Polygon2D) obj;
            Pointf[] updatedHealthBarMesh = DrawUtil.createBox(new Pointf(25f, 40f), new Pointf(health, 20f));
            polygon2D.modifyPoints(updatedHealthBarMesh, false, false, false);

            playerMetadata.setText("Health: " + health);

            takenDamage = false;
        }
    }

    @Override
    public void update(GameObject gameObject) {
        // Empty -- this example does not make use of this method.
    }

    @Override
    public void destroy() {
        damageCooldown.shutdown();
    }

    void takeDamage() {
        if (canTakeDamage) {
            health -= 10;
            takenDamage = true;
            canTakeDamage = false;
            damageCooldown.schedule(() -> canTakeDamage = true, 1, TimeUnit.SECONDS);

            if (health == 0) {
                FastJEngine.runLater(() -> {
                    SceneManager sceneManager = FastJEngine.getLogicManager();
                    sceneManager.switchScenes(SceneNames.LoseSceneName);
                }, CoreLoopState.LateUpdate);
            }
        }
    }
}
