package io.github.lucasstarsz.fastj.game.scenes;

import io.github.lucasstarsz.fastj.engine.graphics.Drawable;
import io.github.lucasstarsz.fastj.engine.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.engine.io.Display;
import io.github.lucasstarsz.fastj.engine.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.engine.systems.game.Scene;
import io.github.lucasstarsz.fastj.engine.util.DrawUtil;
import io.github.lucasstarsz.fastj.game.scripts.PlayerScript;

import java.awt.Color;

public class GameScene extends Scene {

    private Drawable box;

    /**
     * Constructs a scene with the specified name.
     *
     * @param setName The name to set the scene's name to.
     */
    public GameScene(String setName) {
        super(setName);
    }

    @Override
    public void load(Display display) {
        box = new Polygon2D(DrawUtil.createBox(0f, 0f, 50f))
                .setColor(Color.RED)
                .addBehavior(new PlayerScript(), this)
                .addBehavior(Behavior.simpleRotation(1f), this)
                .addAsGameObject(this);
    }

    @Override
    public void unload(Display display) {
        box.destroy(this);
    }

    @Override
    public void update(Display display) {
    }
}
