package io.github.lucasstarsz.fastj.example.bullethell.scripts;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Text2D;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keys;

import java.util.Objects;

public class PlayerController implements Behavior {

    private final float speed;
    private final float rotation;
    private final Text2D playerInfo;

    private float currentRotation;
    private float inputRotation;

    private Pointf inputTranslation;


    public PlayerController(float speedInterval, float rotationInterval, Text2D playerStats) {
        speed = speedInterval;
        rotation = rotationInterval;
        playerInfo = Objects.requireNonNull(playerStats);
    }

    @Override
    public void init(GameObject obj) {
        inputTranslation = new Pointf();
        inputRotation = 0f;
        currentRotation = 0f;
    }

    @Override
    public void update(GameObject obj) {
        inputTranslation.reset();
        inputRotation = 0f;

        if (Keyboard.isKeyDown(Keys.A)) {
            inputRotation -= rotation;
        } else if (Keyboard.isKeyDown(Keys.D)) {
            inputRotation += rotation;
        }
        currentRotation += inputRotation;

        if (Keyboard.isKeyDown(Keys.W)) {
            inputTranslation.y -= speed;
        } else if (Keyboard.isKeyDown(Keys.S)) {
            inputTranslation.y += speed;
        }

        inputTranslation.rotate(-currentRotation);
        obj.rotate(inputRotation);
        obj.translate(inputTranslation);

        if (currentRotation >= 360f) {
            currentRotation -= 360f;
            obj.setRotation(currentRotation);
        } else if (currentRotation <= -360f) {
            currentRotation += 360f;
            obj.setRotation(currentRotation);
        }

        playerInfo.setText(String.format("Rotation: %s, Location: %s", obj.getRotation(), obj.getTranslation()));
    }

    @Override
    public void destroy() {
        inputTranslation = null;
        inputRotation = 0f;
        currentRotation = 0f;
    }
}
