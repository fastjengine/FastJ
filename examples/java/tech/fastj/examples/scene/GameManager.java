package tech.fastj.examples.scene;

import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.RenderSettings;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SceneManager;

/**
 * Simple implementation of a {@link SceneManager Scene Manager}.
 * <p>
 * This scene manager acts as demonstration to how scenes can be set up in FastJ.
 */
public class GameManager extends SceneManager {

    /** The defined name of the {@link FirstScene first scene}. */
    public static final String FirstSceneName = "First Scene";
    /** The defined name of the {@link SecondScene second scene}. */
    public static final String SecondSceneName = "Second Scene";

    /**
     * Initializes this simple GameManager.
     * <p>
     * This method implementation sets up {@link RenderSettings.Antialiasing anti-aliasing} for all scenes. It also adds our first two
     * scenes, and initializes the starting scene.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to. Useful for applying canvas-related settings before the game starts.
     */
    @Override
    public void init(FastJCanvas canvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable);

        /* Our first scene. This will act as our initial scene for this example. */
        Scene firstScene = new FirstScene();

        /* The second scene. We will be able to switch to this scene from the first scene. */
        Scene secondScene = new SecondScene();

        addScenes(firstScene, secondScene);
        /* adding scenes individually via addScene will also work!
         * addScene(firstScene);
         * addScene(secondScene);
         */

        setCurrentScene(firstScene);
        /* You can also use a scene's name to set the current scene.
         * setCurrentScene(firstScene.getSceneName()); // getting the scene name from the scene instance
         * setCurrentScene(FirstSceneName); // our defined scene name, which our first scene uses
         */

        loadCurrentScene();
    }
}
