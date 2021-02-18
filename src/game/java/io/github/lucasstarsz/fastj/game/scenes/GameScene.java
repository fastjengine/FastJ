package io.github.lucasstarsz.fastj.game.scenes;

import io.github.lucasstarsz.fastj.framework.graphics.GameObject;
import io.github.lucasstarsz.fastj.framework.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.framework.math.Pointf;
import io.github.lucasstarsz.fastj.framework.render.Display;
import io.github.lucasstarsz.fastj.framework.render.util.DrawUtil;
import io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.framework.systems.game.Scene;
import io.github.lucasstarsz.fastj.framework.ui.UIElement;
import io.github.lucasstarsz.fastj.framework.ui.elements.Button;

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
        final Pointf[] boxPoints = DrawUtil.createBox(0f, 0f, 50f);
        final PlayerScript playerScript = new PlayerScript(5f);
        final Behavior rotationScript = Behavior.simpleRotation(1f);
        box = new Polygon2D(boxPoints)
                .setColor(Color.RED)
                .addBehavior(playerScript, this)
                .addBehavior(rotationScript, this)
                .addAsGameObject(this);

        final Pointf buttonLocation = new Pointf(100f, 100f);
        final Pointf buttonSize = new Pointf(100f, 20f);
        final Font buttonFont = new Font("Consolas", Font.PLAIN, 36);

        final float[] gradientMixLocations = {0.0f, 0.5f, 1.0f};
        final Color[] gradientColors = {Color.white, Color.cyan, Color.yellow};
        final LinearGradientPaint buttonPaint = new LinearGradientPaint(0, 0, 100, 20,
                gradientMixLocations,
                gradientColors,
                MultipleGradientPaint.CycleMethod.REFLECT
        );

        button = new Button(this, buttonLocation, buttonSize)
                .setText("Hello there")
                .setFont(buttonFont)
                .setPaint(buttonPaint)
                .setOnAction(action -> FastJEngine.log("you clicked me!"));
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
