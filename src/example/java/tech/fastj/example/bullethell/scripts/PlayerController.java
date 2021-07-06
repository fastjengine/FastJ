package tech.fastj.example.bullethell.scripts;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.input.keyboard.Keyboard;
import tech.fastj.systems.input.keyboard.Keys;

public class PlayerController implements Behavior {

    private final float speed;
    private final float rotation;

    private float currentRotation;
    private float inputRotation;

    private Pointf inputTranslation;

    public PlayerController(float speedInterval, float rotationInterval) {
        speed = speedInterval;
        rotation = rotationInterval;
    }

    @Override
    public void init(GameObject obj) {
        inputTranslation = new Pointf();
        inputRotation = 0f;
        currentRotation = 0f;
    }

    @Override
    public void update(GameObject obj) {
        resetTransformations();
        pollMovement();
        movePlayer(obj);
    }

    private void resetTransformations() {
        inputTranslation.reset();
        inputRotation = 0f;
    }

    private void pollMovement() {
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
    }

    private void movePlayer(GameObject obj) {
        obj.rotate(inputRotation);
        obj.translate(inputTranslation);
    }

    @Override
    public void destroy() {
        inputTranslation = null;
        inputRotation = 0f;
        currentRotation = 0f;
    }
}
