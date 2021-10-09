package tech.fastj.example.bullethell.scripts;

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

import tech.fastj.example.bullethell.scenes.GameScene;
import tech.fastj.example.bullethell.util.SceneNames;

public class PlayerHealthBar implements Behavior {

    private static final int initialHealth = 100;

    private int health;
    private boolean takenDamage;
    private boolean canTakeDamage;

    private final GameScene gameScene;
    private final ScheduledExecutorService damageCooldown = Executors.newSingleThreadScheduledExecutor();
    private final Text2D playerMetadata;

    public PlayerHealthBar(Text2D playerMetadata, GameScene gameScene) {
        this.playerMetadata = Objects.requireNonNull(playerMetadata);
        this.gameScene = gameScene;
    }

    @Override
    public void init(GameObject obj) {
        health = initialHealth;
        takenDamage = false;
        canTakeDamage = true;
    }

    @Override
    public void update(GameObject obj) {
        if (obj instanceof Polygon2D && takenDamage) {
            Polygon2D polygon2D = (Polygon2D) obj;
            Pointf[] updatedHealthBarMesh = DrawUtil.createBox(new Pointf(25f, 40f), new Pointf(health, 20f));
            polygon2D.modifyPoints(updatedHealthBarMesh, false, false, false);

            playerMetadata.setText("Health: " + health);

            takenDamage = false;
        }
    }

    @Override
    public void destroy() {
        damageCooldown.shutdownNow();
    }

    void takeDamage() {
        if (canTakeDamage) {
            health -= 10;
            takenDamage = true;
            canTakeDamage = false;
            damageCooldown.schedule(() -> canTakeDamage = true, 1, TimeUnit.SECONDS);

            if (health == 0) {
                FastJEngine.runAfterUpdate(() -> {
                    SceneManager sceneManager = FastJEngine.getLogicManager();
                    sceneManager.switchScenes(SceneNames.LoseSceneName);
                    sceneManager.getScene(SceneNames.GameSceneName).unload(FastJEngine.getCanvas());
                });
            }
        }
    }
}
