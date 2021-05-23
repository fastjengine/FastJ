package io.github.lucasstarsz.fastj.example.oldhelloworld.customscripts;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.systems.input.keyboard.Keys;

import io.github.lucasstarsz.fastj.example.oldhelloworld.scenes.GameScene;

/** A custom script that moves the received game object using the WASD keys. */
public class PlayerScript implements Behavior {

    private Pointf travel;
    private final float speed;

    /**
     * Creates a PlayerScript behavior.
     *
     * @param speed The speed at which the game object(s) assigned to this behavior will travel via WASD.
     */
    public PlayerScript(float speed) {
        this.speed = speed;
    }

    /**
     * This is used to initialize the game object, or any variables in the Behavior itself.
     *
     * @param obj A GameObject that has been assigned this behavior.
     */
    @Override
    public void init(GameObject obj) {
        /* Because of that second part, we can initialize the Pointf in here. */
        travel = new Pointf();
    }

    /**
     * This is where the game object's state gets updated.
     * <p>
     * In this case, we're going to move the game object (our player in {@link GameScene}) based on input from the WASD
     * keys.
     *
     * @param obj A GameObject that has been assigned this behavior.
     */
    @Override
    public void update(GameObject obj) {
        /* Translates the Pointf based on whether the W or S key is pressed. */
        if (Keyboard.isKeyDown(Keys.W)) {
            travel.y -= speed;
        } else if (Keyboard.isKeyDown(Keys.S)) {
            travel.y += speed;
        }

        /* Translates the Pointf based on whether the A or D key is pressed. */
        if (Keyboard.isKeyDown(Keys.A)) {
            travel.x -= speed;
        } else if (Keyboard.isKeyDown(Keys.D)) {
            travel.x += speed;
        }

        /* Then, we translate the game object by the Pointf we translated just before.
         * The result? A player that responds to WASD key presses. */
        obj.translate(travel);

        /* In order for this to work properly, we need to reset the Pointf when we reach the end of the method. */
        travel.reset();
    }

    @Override
    public void destroy() {
        travel = null;
    }
}
