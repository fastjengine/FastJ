package tech.fastj.systems.control;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.input.InputManager;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.systems.behaviors.BehaviorManager;

import java.util.List;

/**
 * A distinct section, level, or otherwise part of a game.
 * <p>
 * All of a game's scenes are handled in a {@link SceneManager}. Through this, the user can divide their game into different sections.
 * <p>
 * A scene can be declared simply:
 * {@snippet lang = "java":
 * public class MyScene extends Scene {
 *
 *     public static final String MySceneName = "My Scene";
 *
 *     public MyScene() {
 *         super(MySceneName);
 *     }
 * }}
 * <p>
 * A scene generally contains methods to {@link #load(FastJCanvas) load}, {@link #unload(FastJCanvas) unload}, and perform
 * {@link #fixedUpdate(FastJCanvas) fixed update} and {@link #update(FastJCanvas) update} calls when the {@link FastJEngine engine} is
 * running.
 * <p>
 * Scene example with all major methods implemented:
 * {@snippet lang = "java":
 * public class MyScene extends Scene {
 *
 *     public static final String MySceneName = "My Scene";
 *
 *     public MyScene() {
 *         super(MySceneName);
 *     }
 *
 *     @Override
 *     public void load(FastJCanvas canvas) {
 *         FastJEngine.log("Loaded {}", getSceneName());
 *     }
 *
 *     @Override
 *     public void unload(FastJCanvas canvas) {
 *         FastJEngine.log("Unloaded {}", getSceneName());
 *     }
 *
 *     @Override
 *     public void fixedUpdate(FastJCanvas canvas) {
 *         FastJEngine.log("Fixed update for {}", getSceneName());
 *     }
 *
 *     @Override
 *     public void update(FastJCanvas canvas) {
 *         FastJEngine.log("Update for {}", getSceneName());
 *     }
 * }}
 * <p>
 * See {@link SceneManager the scene manager documentation} for a complete runnable example using {@link Scene}.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class Scene implements GameHandler {

    private final String sceneName;
    private final Camera camera;

    /**
     * Input manager instance for the scene -- it controls the scene's received events.
     *
     * @deprecated Public access to this field will be removed soon -- please use {@link #inputManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final InputManager inputManager;

    /**
     * Drawable manager instance for the scene -- it controls the scene's game objects and ui elements.
     *
     * @deprecated Public access to this field will be removed soon -- please use {@link #drawableManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final DrawableManager drawableManager;

    private boolean isInitialized;

    /**
     * Constructs a scene with the specified scene name.
     * <p>
     * Under standard circumstances, the name specified is some constant you will want to use later, namely for
     * {@link SceneManager#switchScenes(String) scene switching}.
     * {@snippet lang = "java":
     * public class MyScene extends Scene {
     *
     *     public static final String MySceneName = "My Scene";
     *
     *     public MyScene() {
     *         super(MySceneName);
     *     }
     * }}
     * <p>
     * In many cases, putting the constants for scene names in a separate class reads well:
     * {@snippet lang = "java":
     * public class SceneNames {
     *     public static final String MyScene = "My Scene";
     *     public static final String MyOtherScene = "My Other Scene";
     * }
     *
     * public class MyScene extends Scene {
     *     public MyScene() {
     *         super(SceneNames.MyScene);
     *     }
     * }
     *
     * public class MyOtherScene extends Scene {
     *     public MyOtherScene() {
     *         super(SceneNames.MyOtherScene);
     *     }
     * }}
     *
     * <i>The complete scene example can be found {@link Scene here}.</i>
     *
     * @param setName The name to set the scene's name to.
     */
    protected Scene(String setName) {
        sceneName = setName;
        camera = new Camera();

        inputManager = new InputManager();
        drawableManager = new DrawableManager();

        BehaviorManager.addListenerList(this);
    }

    /**
     * Loads the scene into an initialized state.
     * <p>
     * This method is best used for initializing any variables necessary at the beginning of displaying a scene. {@snippet :
     * public class MyScene extends Scene {
     *     @Override
     *     public void load(FastJCanvas canvas) {
     *         FastJEngine.log("Loaded {}", getSceneName());
     *     }
     * }}
     *
     * <i>The complete scene example can be found {@link Scene here}.</i>
     *
     * @param canvas The {@link FastJCanvas} that the game renders to.
     */
    public void load(FastJCanvas canvas) {
    }

    /**
     * Unloads the scene into an uninitialized state.
     * <p>
     * This method is best used for resetting the scene variables' states. {@snippet :
     * public class MyScene extends Scene {
     *     @Override
     *     public void unload(FastJCanvas canvas) {
     *         FastJEngine.log("Unloaded {}", getSceneName());
     *     }
     * }}
     *
     * <i>The complete scene example can be found {@link Scene here}.</i>
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void unload(FastJCanvas canvas) {
    }

    /**
     * Updates the scene's state during game state updates.
     * <p>
     * This method is called on the current scene every time the engine updates its state. {@snippet :
     * public class MyScene extends Scene {
     *     @Override
     *     public void fixedUpdate(FastJCanvas canvas) {
     *         FastJEngine.log("Fixed update for {}", getSceneName());
     *     }
     * }}
     *
     * <i>The complete scene example can be found {@link Scene here}.</i>
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void fixedUpdate(FastJCanvas canvas) {
    }

    /**
     * Updates the scene's state during input receiving, before rendering.
     * <p>
     * This method is called on the current scene every time the engine updates its state. {@snippet :
     * public class MyScene extends Scene {
     *     @Override
     *     public void update(FastJCanvas canvas) {
     *         FastJEngine.log("Update for {}", getSceneName());
     *     }
     * }}
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    public void update(FastJCanvas canvas) {
    }

    /** {@return the name of the scene}. This is often used for {@link SceneManager#switchScenes(String) scene switching}. */
    public String getSceneName() {
        return sceneName;
    }

    /** {@return the scene camera} */
    @Override
    public Camera getCamera() {
        return camera;
    }

    /**
     * {@return the scene's input manager}
     * <p>
     * This is often used for adding {@link InputManager#addKeyboardActionListener(KeyboardActionListener) keyboard} and
     * {@link InputManager#addMouseActionListener(MouseActionListener) mouse} listeners.
     */
    @Override
    public InputManager inputManager() {
        return inputManager;
    }

    /**
     * {@return the scene's drawable manager}
     * <p>
     * This is often used for adding {@link DrawableManager#addGameObject(GameObject) game objects} and
     * {@link DrawableManager#addUIElement(UIElement) ui elements}.
     * <p>
     * The code below is an example segment involving adding a game object to a scene.
     * {@snippet :
     * import tech.fastj.graphics.display.FastJCanvas;
     * import tech.fastj.graphics.game.Polygon2D;
     * import tech.fastj.graphics.util.DrawUtil;
     * import tech.fastj.systems.control.Scene;
     *
     * public class MyScene extends Scene {
     *
     *     public static final String MySceneName = "My Scene";
     *
     *     public MyScene() {
     *         super(MySceneName);
     *     }
     *
     *     @Override
     *     public void load(FastJCanvas canvas) {
     *         Polygon2D box = Polygon2D.fromPath(DrawUtil.createBox(0f, 0f, 10f));
     *         drawableManager().addGameObject(box); // @highlight
     *     }
     * }}
     */
    @Override
    public DrawableManager drawableManager() {
        return drawableManager;
    }

    /** {@return whether the scene is initialized} */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Sets whether the scene is initialized.
     *
     * @param initialized The value that determines whether the scene is initialized.
     */
    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    /** {@return all the scene's taggable entities: {@link GameObject game objects} and {@link UIElement ui elements}} */
    @Override
    public List<Drawable> getTaggableEntities() {
        return drawableManager.getDrawablesList();
    }

    void generalLoad(FastJCanvas canvas) {
        inputManager.load();
        load(canvas);

        setInitialized(true);
    }

    void generalUnload(FastJCanvas canvas) {
        inputManager.unload();
        unload(canvas);
        drawableManager.reset(this);

        setInitialized(false);
    }

    /* Reset */

    /** Removes all elements from the scene. */
    public void clearAllLists() {
        drawableManager.clearAllLists();
        clearBehaviorListeners();
    }

    /** Resets the scene's state entirely. */
    @Override
    public void reset() {
        generalUnload(FastJEngine.getCanvas());

        clearAllLists();
        inputManager.reset();
        camera.reset();
    }
}
