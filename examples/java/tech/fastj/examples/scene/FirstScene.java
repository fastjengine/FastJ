package tech.fastj.examples.scene;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.SimpleDisplay;
import tech.fastj.graphics.game.Text2D;

import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.Keys;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.systems.control.Scene;

/** This is a scene which our {@link GameManager game manager} will load first when we {@link Main#main(String[])} run our game}. */
public class FirstScene extends Scene {

    /** Constructor for the {@link FirstScene first scene}. */
    public FirstScene() {
        super(GameManager.FirstSceneName);
    }

    /**
     * This method is called when the game loads this scene. Since {@link FirstScene this} is the scene originally set as the current scene
     * in out {@link GameManager game manager}, it will be loaded when the game begins running -- or when the other scene switches back to
     * this scene.
     * <p>
     * Here, we add text to tell the user they can press the Right Arrow Key to switch to {@link SecondScene the next scene}; we also add
     * the key listener which switches scenes when the Right Arrow Key is pressed.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    @Override
    public void load(FastJCanvas canvas) {
        // Firstly, let's identify the scene in the window title.
        FastJEngine.<SimpleDisplay>getDisplay().setTitle("Current Scene: " + getSceneName());

        // Now, let's tell the user how they can switch scenes.
        Text2D nextSceneHint = Text2D.fromText("Press the Right Arrow Key to go to the next scene");
        nextSceneHint.translate(canvas.getCanvasCenter().subtract(nextSceneHint.width() / 2f, nextSceneHint.height() / 2f));
        drawableManager().addGameObject(nextSceneHint);

        // Now we'll set up a keyboard listener that will switch the scene when the right arrow key
        // is pressed!
        inputManager().addKeyboardActionListener(new KeyboardActionListener() {
            @Override
            public void onKeyRecentlyPressed(KeyboardStateEvent event) {
                // We'll only use this key event if it's not consumed, and if it refers to the
                // right arrow key.
                if (event.isConsumed() || event.getKey() != Keys.Right) {
                    return;
                }

                // "consume" or "eat" the keyboard event, so that nothing else can use this event.
                // When it comes to input events like mouse and keyboard, it's usually best we
                // prevent a single input from performing multiple actions.
                event.consume();

                // Now, we just tell FastJ to switch scenes when it is next most convenient.
                FastJEngine.runLater(() -> FastJEngine.<GameManager>getLogicManager().switchScenes(GameManager.SecondSceneName));
            }
        });
    }
}
