package io.github.lucasstarsz.fastj.game.scenes;

import io.github.lucasstarsz.fastj.graphics.GameObject;
import io.github.lucasstarsz.fastj.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.render.Display;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.game.Scene;
import io.github.lucasstarsz.fastj.ui.UIElement;
import io.github.lucasstarsz.fastj.ui.elements.Button;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import io.github.lucasstarsz.fastj.game.scripts.PlayerScript;

import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;

public class GameScene extends Scene {

    private GameObject box;
    private UIElement button;

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
        // create player
        final Pointf[] boxPoints = DrawUtil.createBox(0f, 0f, 50f);
        final PlayerScript playerScript = new PlayerScript(5f);
        final Behavior rotationScript = Behavior.simpleRotation(1f);

        box = new Polygon2D(boxPoints)
                .setColor(Color.RED)
                .addBehavior(playerScript, this)
                .addBehavior(rotationScript, this)
                .addAsGameObject(this);

        // Create button
        final Pointf buttonLocation = new Pointf(100f, 100f);
        final Pointf buttonSize = new Pointf(100f, 30f);
        final Font buttonFont = new Font("Segoe UI", Font.PLAIN, 20);

        final float[] gradientMixLocations = {0.0f, 1.0f};
        final Color[] gradientColors = {new Color(200, 200, 200), new Color(100, 100, 100)};
        final LinearGradientPaint buttonPaint = new LinearGradientPaint(100f, 100f, 100f, 130f,
                gradientMixLocations,
                gradientColors,
                MultipleGradientPaint.CycleMethod.REPEAT
        );

        button = new Button(this, buttonLocation, buttonSize)
                .setText("Click me")
                .setFont(buttonFont)
                .setPaint(buttonPaint)
                .setOnAction(action -> FastJEngine.log("Playing with your heart!"));
    }

    @Override
    public void unload(Display display) {
        box.destroy(this);
        button.destroy(this);
    }

    @Override
    public void update(Display display) {
    }
}
