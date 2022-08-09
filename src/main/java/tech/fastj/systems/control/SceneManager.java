package tech.fastj.systems.control;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.systems.behaviors.Behavior;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Top-level game structure which manages {@link Scene scenes}.
 * <p>
 * Here is a simple example of the scene manager in action. It:
 * <ol>
 *     <li>Creates and loads an instance of a {@link Scene scene} called {@code MyScene}</li>
 *     <li>{@link FastJEngine#run() Runs FastJ}, where the content shown is {@link Text2D text} saying {@code "Hello FastJ!"}</li>
 * </ol>
 * {@snippet lang = "java":
 * // GameManager.java
 * import tech.fastj.engine.FastJEngine;
 * import tech.fastj.graphics.display.FastJCanvas;
 * import tech.fastj.graphics.display.RenderSettings;
 *
 * public class GameManager extends SceneManager {
 *     @Override
 *     public void init(FastJCanvas canvas) {
 *         // enable anti-aliasing
 *         canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable);
 *         // add scene to scene manager
 *         MyScene scene = new MyScene();
 *         addScene(scene);
 *
 *         // setup and load the first scene
 *         setCurrentScene(scene);
 *         loadCurrentScene();
 *     }
 *
 *     public static void main(String[] args) {
 *          // initialize and run the game, using the scene manager
 *          FastJEngine.init("Scene-Based Game", new GameManager());
 *          FastJEngine.run();
 *     }
 * }
 *
 * // MyScene.java
 * import tech.fastj.graphics.display.FastJCanvas;
 * import tech.fastj.graphics.game.Text2D;
 * import tech.fastj.systems.control.Scene;
 *
 * public class MyScene extends Scene {
 *     public static final String SceneName = "My Scene";
 *
 *     public MyScene() {
 *         // set the scene name
 *         super(SceneName);
 *     }
 *
 *     @Override
 *     public void load(FastJCanvas canvas) {
 *         // say hello to FastJ from a scene!
 *         Text2D helloWorld = Text2D.fromText("Hello FastJ!");
 *         drawableManager().addGameObject(helloWorld);
 *     }
 * }}
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public abstract class SceneManager implements LogicManager {

    private final Map<String, Scene> scenes = new ConcurrentHashMap<>();
    private Scene currentScene;
    private boolean switchingScenes;

    /**
     * Initializes the scene manager, primarily for {@link #addScene(Scene) adding scenes} and
     * {@link #setCurrentScene(Scene) setting}/{@link #loadCurrentScene() loading} an initial scene.
     *
     * @param canvas The {@link FastJCanvas} that the game renders to. Useful for applying canvas-related settings before the game starts.
     */
    @Override
    public abstract void init(FastJCanvas canvas);

    /**
     * Internal method to initialize the scene manager (and {@link #getCurrentScene() current scene})'s {@link Behavior behaviors}.
     * <p>
     * For general purposes, <b>do not override this method</b>.
     * <ul>
     *     <li>To perform initial scene manager setup, you must override {@link #init(FastJCanvas) the scene manager's init method}.</li>
     *     <li>To perform initial scene setup, you must override {@link Scene#load(FastJCanvas) the scene's load method}.</li>
     *     <li>To perform initial behavior setup, you must override {@link Behavior#init(GameObject) the behavior's init method}.</li>
     * </ul>
     */
    @Override
    public abstract void init(FastJCanvas canvas);

    @Override
    public void initBehaviors() {
        safeInit();
    }

    /**
     * Internal method to run a fixed update on the {@link #getCurrentScene() current scene}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform actions during fixed update, you must override your current
     * scene's {@link Scene#fixedUpdate(FastJCanvas) fixed update method}.
     */
    @Override
    public void fixedUpdate(FastJCanvas canvas) {
        safeUpdate(() -> currentScene.fixedUpdate(canvas));
    }

    /**
     * Internal method to run an update on the {@link #getCurrentScene() current scene}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform actions during update, you must override your current scene's
     * {@link Scene#update(FastJCanvas) update method}.
     */
    @Override
    public void update(FastJCanvas canvas) {
        safeUpdate(() -> currentScene.update(canvas));
    }

    /**
     * Internal method to run a fixed update on the {@link #getCurrentScene() current scene}'s {@link Behavior behaviors}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform actions in a {@link Behavior game object behavior} during fixed
     * update, you must override {@link Behavior#fixedUpdate(GameObject)} your behavior's fixed update method}.
     */
    @Override
    public void fixedUpdateBehaviors() {
        safeUpdate(currentScene::fixedUpdateBehaviorListeners);
    }

    /**
     * Internal method to run an update on the {@link #getCurrentScene() current scene}'s {@link Behavior behaviors}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform actions in a {@link Behavior game object behavior} during
     * update, you must override {@link Behavior#update(GameObject)} your behavior's update method}.
     */
    @Override
    public void updateBehaviors() {
        safeUpdate(currentScene::updateBehaviorListeners);
    }

    /**
     * Internal method to render the {@link #getCurrentScene() current scene}'s {@link DrawableManager#getGameObjects() game objects} and
     * {@link DrawableManager#getUIElements() ui}.
     * <p>
     * For general purposes, <b>do not override this method</b>.
     * <ul>
     *     <li>
     *         To render a {@link GameObject game object}, you must create one and {@link DrawableManager#addGameObject(GameObject) add it}
     *         to the scene's {@link Scene#drawableManager() drawable manager}.
     *     </li>
     *     <li>
     *         To render a {@link UIElement ui element}, you must create one and {@link DrawableManager#addUIElement(UIElement)} add it}
     *         to the scene's {@link Scene#drawableManager() drawable manager}.
     *     </li>
     * </ul>
     */
    @Override
    public void render(FastJCanvas display) {
        safeRender(display);
    }

    /**
     * Internal method to send keyboard events to all {@link KeyboardActionListener keyboard listeners}.
     * <p>
     * For general purposes, <b>do not override this method</b>. Please see {@link KeyboardActionListener} to learn how to listen to the
     * keyboard actions.
     */
    @Override
    public void processKeysDown() {
        safeUpdate(currentScene.inputManager()::fireKeysDown);
    }

    /** Resets the logic manager. */
    @Override
    public void reset() {
        for (Scene scene : scenes.values()) {
            if (scene.isInitialized()) {
                scene.reset();
            }
        }

        scenes.clear();
    }

    /**
     * Gets the currently active scene.
     *
     * @param <T> The type of the scene being retrieved. This type must match the actual type of the retrieved scene, and must always extend
     *            {@link Scene}.
     * @return Returns the currently active scene.
     */
    @SuppressWarnings("unchecked")
    public <T extends Scene> T getCurrentScene() {
        return (T) currentScene;
    }

    /**
     * Sets the current scene to the scene specified.
     * <p>
     * Instead of using this method to switch scenes, it is preferred that you use the {@code switchScene(String nextScene)} method.
     *
     * @param scene The scene which the current scene will be set to.
     */
    public void setCurrentScene(Scene scene) {
        setCurrentScene(scene.getSceneName());
    }

    /**
     * Sets the current scene to the scene with the name specified.
     * <p>
     * Instead of using this method to switch scenes, it is preferred that you use the {@code switchScene(String nextScene)} method.
     *
     * @param sceneName The name of the scene which the current scene will be set to.
     */
    public void setCurrentScene(String sceneName) {
        sceneExistenceCheck(sceneName);

        currentScene = scenes.get(sceneName);
        switchingScenes = !currentScene.isInitialized();
    }

    /**
     * Gets the list of all scenes in the logic manager.
     *
     * @return Returns the list of scenes in the logic manager.
     */
    public List<Scene> getScenes() {
        return Collections.list(Collections.enumeration(scenes.values()));
    }

    /**
     * Gets the scene with the specified scene name, if it exists.
     *
     * @param <T>       The type of the scene being retrieved. This type must match the actual type of the retrieved scene, and must always
     *                  extend {@link Scene}.
     * @param sceneName The name of the scene to retrieve.
     * @return The scene, if it exists.
     */
    @SuppressWarnings("unchecked")
    public <T extends Scene> T getScene(String sceneName) {
        sceneExistenceCheck(sceneName);
        return (T) scenes.get(sceneName);
    }

    /**
     * Gets the boolean that specifies whether the logic manager is currently switching scenes.
     *
     * @return Returns a boolean that specifies whether the logic manager is currently switching scenes.
     */
    public boolean isSwitchingScenes() {
        return switchingScenes;
    }

    /**
     * Adds the specified scene into the logic manager.
     *
     * @param scene The Scene object to be added.
     */
    public void addScene(Scene scene) {
        sceneNameAlreadyExistsCheck(scene.getSceneName());
        scenes.put(scene.getSceneName(), scene);
    }

    /**
     * Adds all the specified scenes into the logic manager.
     *
     * @param scenes All the Scene objects to be added.
     */
    public void addScenes(Scene... scenes) {
        for (Scene scene : scenes) {
            addScene(scene);
        }
    }

    /**
     * Adds all the specified scenes into the logic manager.
     *
     * @param scenes The list of Scene objects to be added.
     */
    public void addScenes(List<Scene> scenes) {
        for (Scene scene : scenes) {
            addScene(scene);
        }
    }

    /**
     * Removes the specified scene from the logic manager.
     *
     * @param scene The Scene object to be removed.
     */
    public void removeScene(Scene scene) {
        removeScene(scene.getSceneName());
    }

    /**
     * Removes a scene from the logic manager, based on the specified scene name.
     *
     * @param sceneName The name of the Scene to be removed.
     */
    public void removeScene(String sceneName) {
        sceneExistenceCheck(sceneName);
        scenes.remove(sceneName);
    }

    /**
     * Switches to the scene specified, loading that scene if necessary.
     * <p>
     * This is the preferred method of switching from one scene to another. It will also unload the current scene.
     *
     * @param nextSceneName The name of the next Scene to be loaded.
     */
    public void switchScenes(String nextSceneName) {
        switchScenes(nextSceneName, true);
    }

    /**
     * Switches to the scene specified, loading that scene if necessary.
     * <p>
     * This is the preferred method of switching from one scene to another.
     *
     * @param nextSceneName      The name of the next Scene to be loaded.
     * @param unloadCurrentScene Whether to unload the current scene.
     */
    public void switchScenes(String nextSceneName, boolean unloadCurrentScene) {
        if (!scenes.containsKey(nextSceneName)) {
            FastJEngine.error(
                CrashMessages.SceneError.errorMessage,
                new IllegalArgumentException("A scene with the name: \"" + nextSceneName + "\" hasn't been added!")
            );
        }

        switchingScenes = true;
        FastJCanvas canvas = FastJEngine.getCanvas();

        if (unloadCurrentScene) {
            currentScene.generalUnload(canvas);
        } else {
            currentScene.inputManager().unload();
        }

        Scene nextScene = scenes.get(nextSceneName);
        nextScene.inputManager().load();

        if (!nextScene.isInitialized()) {
            nextScene.generalLoad(canvas);
            nextScene.initBehaviorListeners();
            nextScene.setInitialized(true);
        }

        canvas.setBackgroundToCameraPos(nextScene.getCamera());
        setCurrentScene(nextSceneName);

        switchingScenes = false;
    }

    /** Loads the current scene, if it's not already initialized. */
    public void loadCurrentScene() {
        nullSceneCheck();

        if (!currentScene.isInitialized()) {
            FastJCanvas canvas = FastJEngine.getCanvas();
            currentScene.generalLoad(canvas);
            canvas.setBackgroundToCameraPos(currentScene.getCamera());
        }

        currentScene.setInitialized(true);
        switchingScenes = false;
    }

    /**
     * Creates a snapshot of the {@code switchingScenes} boolean, to make sure the game doesn't crash out due to an attempt to call methods
     * and other fields illegally.
     *
     * @return An array of booleans to check through.
     */
    private boolean[] createSnapshot() {
        return new boolean[] {switchingScenes};
    }

    /**
     * Checks if the logic manager was switching scenes.
     * <p>
     * This method takes a boolean parameter, which is a record of whether the logic manager was switching scenes at the beginning of the
     * parent method call.
     * <p>
     * If the logic manager was not switching scenes, this method will error out the game engine.
     *
     * @param snapshot Record of whether the logic manager was switching scenes at the beginning of the parent method call.
     * @param e        The NullPointerException that would be used in the error call.
     */
    private void snapshotCheck(boolean[] snapshot, NullPointerException e) {
        for (boolean b : snapshot) {
            if (b) {
                return;
            }
        }

        FastJEngine.error(CrashMessages.SceneError.errorMessage, e);
    }

    /** Safely initializes the current scene by first checking for null pointers. */
    private void safeInit() {
        boolean[] snapshot = createSnapshot();

        try {
            nullSceneCheck();
            currentScene.initBehaviorListeners();

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Safely updates the current scene with the specified action, by first checking for null pointers and scene initialization.
     *
     * @param action The action to safely run.
     */
    private void safeUpdate(Runnable action) {
        boolean[] snapshot = createSnapshot();

        try {
            nullSceneCheck();
            initSceneCheck();
            action.run();

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Safely renders the current scene to the {@code Display}.
     *
     * @param canvas The {@code FastJCanvas} that the game renders to.
     */
    private void safeRender(FastJCanvas canvas) {
        boolean[] snapshot = createSnapshot();

        try {
            nullSceneCheck();
            initSceneCheck();

            canvas.render(
                currentScene.drawableManager().getGameObjects(),
                currentScene.drawableManager().getUIElements(),
                currentScene.getCamera()
            );

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Checks if the current scene is null.
     * <p>
     * If the current scene is null, this throws a NullPointerException that has a customized message, based on the context of the error.
     */
    private void nullSceneCheck() {
        if (currentScene == null) {
            throw new NullPointerException(
                (scenes.size() < 1)
                ? "You haven't created a Scene yet, or you haven't added it to the list of scenes for the logic manager."
                    + System.lineSeparator()
                    + "To add a scene, use the addScene(Scene) method in your logic manager."
                    + System.lineSeparator()
                    + "Then, set the current scene to the scene you just added, using the setCurrentScene(Scene) method."

                : "A current scene hasn't been set."
                    + System.lineSeparator()
                    + "You should set the current scene, using the setCurrentScene(Scene) method from your logic manager."
                    + System.lineSeparator()
                    + "Scenes added: " + scenes.keySet()
            );
        }
    }

    /**
     * Checks if the current scene is initialized.
     * <p>
     * If the current scene isn't initialized, this throws a NullPointerException with a message that says so.
     */
    private void initSceneCheck() {
        if (!currentScene.isInitialized()) {
            throw new NullPointerException(
                "Current scene \"" + currentScene.getSceneName() + "\" isn't initialized."
                    + System.lineSeparator()
                    + "You should initialize the current scene, using the initCurrentScene(Display) method from your logic manager.");
        }
    }

    /**
     * Checks if the specified scene's name is already in use in the logic manager.
     * <p>
     * If the scene name is already in use, this method will error out the game engine.
     *
     * @param sceneName The scene name to check for.
     */
    private void sceneNameAlreadyExistsCheck(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            throw new IllegalArgumentException(
                "The scene name \"" + sceneName + "\" is already in use."
                    + System.lineSeparator()
                    + "Scenes added: " + scenes.keySet()
            );
        }
    }

    /**
     * Checks if the specified scene name corresponds with a scene in the logic manager.
     * <p>
     * If the scene name doesn't correspond with any scenes in the logic manager, this method will error out the game engine.
     *
     * @param sceneName Name of the scene to be checked for.
     */
    private void sceneExistenceCheck(String sceneName) {
        if (!scenes.containsKey(sceneName)) {
            throw new IllegalArgumentException("A scene with the name: \"" + sceneName + "\" hasn't been added!");
        }
    }
}
