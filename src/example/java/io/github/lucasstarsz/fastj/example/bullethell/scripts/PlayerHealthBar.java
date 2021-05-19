package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.Boundary;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;

public class PlayerHealthBar implements Behavior {

    private static final int initialHealth = 100;

    private int health;
    private boolean takenDamage;

    @Override
    public void init(GameObject obj) {
        health = initialHealth;
    }

    @Override
    public void update(GameObject obj) {
        if (obj instanceof Polygon2D && takenDamage) {
            Polygon2D polygon2D = (Polygon2D) obj;

            Pointf topLeft = polygon2D.getBound(Boundary.TopLeft);
            Pointf bottomLeft = polygon2D.getBound(Boundary.BottomLeft);

            float healthBarHeight = Pointf.subtract(topLeft, bottomLeft).y;
            Pointf[] updatedHealthBarMesh = DrawUtil.createBox(topLeft, new Pointf(health, healthBarHeight));

            polygon2D.modifyPoints(updatedHealthBarMesh, false, false, false);

            takenDamage = false;
        }
    }

    void takeDamage() {
        if (!takenDamage) {
            health -= 10;
            takenDamage = true;
        }
    }
}
