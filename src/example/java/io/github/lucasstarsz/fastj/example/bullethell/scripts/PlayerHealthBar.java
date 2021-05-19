package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;
import io.github.lucasstarsz.fastj.graphics.game.Text2D;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerHealthBar implements Behavior {

    private static final int initialHealth = 100;

    private int health;
    private boolean takenDamage;
    private boolean canTakeDamage;

    private final ScheduledExecutorService damageCooldown = Executors.newSingleThreadScheduledExecutor();
    private final Text2D playerMetadata;

    public PlayerHealthBar(Text2D playerMetadata) {
        this.playerMetadata = playerMetadata;
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

    void takeDamage() {
        if (canTakeDamage) {
            health -= 10;
            takenDamage = true;
            canTakeDamage = false;
            damageCooldown.schedule(() -> canTakeDamage = true, 1, TimeUnit.SECONDS);
        }
    }
}
