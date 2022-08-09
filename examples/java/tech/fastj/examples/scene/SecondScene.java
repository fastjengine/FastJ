package tech.fastj.examples.scene;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.SimpleDisplay;
import tech.fastj.graphics.game.Text2D;

import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.Keys;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.systems.control.Scene;

/** This is a scene which our {@link GameManager game manager} will load when we switch to it from {@link FirstScene the first scene}. */
public class SecondScene extends Scene {

    /** Constructor for the {@link SecondScene second scene}. */
    public SecondScene() {
        super(GameManager.SecondSceneName);
    }

    /**
     * This method is called when the game loads this scene.
     * <p>
     * Since {@link SecondScene this scene} is not the starting current scene in the {@link GameManager game manager}, it will only be
     * loaded whenever the user switches to this scene from the previous scene.
     * <p>
     * Here, we add text to tell the user they can press the Left Arrow Key to switch to {@link FirstScene the previous scene}; we also add
     * the key listener which switches scenes when the Left Arrow Key is pressed.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    @Override
    public void load(FastJCanvas canvas) {
        // Firstly, let's identify the scene in the window title.
        FastJEngine.<SimpleDisplay>getDisplay().setTitle("Current Scene: " + getSceneName());

        // Now, let's tell the user how they can switch scenes.
        Text2D previousSceneHint = Text2D.fromText("Press the Left Arrow Key to go to the previous scene");
        previousSceneHint.translate(canvas.getCanvasCenter().subtract(previousSceneHint.width() / 2f, previousSceneHint.height() / 2f));
        drawableManager().addGameObject(previousSceneHint);

        // Now we'll set up a keyboard listener that will switch the scene when the left arrow key
        // is pressed!
        inputManager().addKeyboardActionListener(new KeyboardActionListener() {
            @Override
            public void onKeyRecentlyPressed(KeyboardStateEvent event) {
                // We'll only use this key event if it's not consumed, and if it refers to the
                // left arrow key.
                if (event.isConsumed() || event.getKey() != Keys.Left) {
                    return;
                }

                // "consume" or "eat" the keyboard event, so that nothing else can use this event.
                // When it comes to input events like mouse and keyboard, it's usually best we
                // prevent a single input from performing multiple actions.
                event.consume();

                // Now, we just tell FastJ to switch scenes when it is next most convenient.
                FastJEngine.runLater(() -> FastJEngine.<GameManager>getLogicManager().switchScenes(GameManager.FirstSceneName));
            }
        });
    }
}
