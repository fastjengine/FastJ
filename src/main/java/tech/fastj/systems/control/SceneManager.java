package tech.fastj.systems.control;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.Display;

import java.awt.event.InputEvent;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The manager which allows for control over the scenes in a game.
 * <p>
 * A {@code LogicManager} acts as a bridge between the internals of the engine, and the developer's code.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class SceneManager implements LogicManager {

    private final Map<String, Scene> scenes = new LinkedHashMap<>();
    private Scene currentScene;
    private boolean switchingScenes;

    /**
     * Updates the current scene, its behaviors, and listeners.
     *
     * @param display The {@code Display} that the game renders to.
     */
    @Override
    public void update(Display display) {
        updateCurrentScene(display);
    }

    /**
     * Renders the current scene to the {@code Display}.
     *
     * @param display The {@code Display} that the game renders to.
     */
    @Override
    public void render(Display display) {
        renderCurrentScene(display);
    }

    /** Processes all pending input events. */
    @Override
    public void processInputEvents() {
        currentScene.inputManager.processEvents();
    }

    @Override
    public void receivedInputEvent(InputEvent inputEvent) {
        currentScene.inputManager.receivedInputEvent(inputEvent);
    }

    /** Resets the logic manager. */
    @Override
    public void reset() {
        for (Scene s : scenes.values()) {
            if (s.isInitialized()) {
                s.unload(FastJEngine.getDisplay());
            }
        }
        scenes.clear();
    }

    /**
     * Gets the currently active scene.
     *
     * @param <T> The type of the scene being retrieved. This type must match the actual type of the retrieved scene,
     *            and must always extend {@link Scene}.
     * @return Returns the currently active scene.
     */
    @SuppressWarnings("unchecked")
    public <T extends Scene> T getCurrentScene() {
        return (T) currentScene;
    }

    /**
     * Sets the current scene to the scene specified.
     * <p>
     * Instead of using this method to switch scenes, it is preferred that you use the {@code switchScene(String
     * nextScene)} method.
     *
     * @param scene The scene which the current scene will be set to.
     */
    public void setCurrentScene(Scene scene) {
        setCurrentScene(scene.getSceneName());
    }

    /**
     * Sets the current scene to the scene with the name specified.
     * <p>
     * Instead of using this method to switch scenes, it is preferred that you use the {@code switchScene(String
     * nextScene)} method.
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
     * @param <T>       The type of the scene being retrieved. This type must match the actual type of the retrieved
     *                  scene, and must always extend {@link Scene}.
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
     * This is the preferred method of switching from one scene to another. However, it does not unload the last scene.
     * That has to be done by the user.
     *
     * @param nextSceneName The name of the next Scene to be loaded.
     */
    public void switchScenes(String nextSceneName) {
        if (!scenes.containsKey(nextSceneName)) {
            FastJEngine.error(CrashMessages.SceneError.errorMessage,
                    new IllegalArgumentException("A scene with the name: \"" + nextSceneName + "\" hasn't been added!"));
        }

        switchingScenes = true;
        Display display = FastJEngine.getDisplay();

        Scene nextScene = scenes.get(nextSceneName);
        if (!nextScene.isInitialized()) {
            nextScene.load(display);
            nextScene.initBehaviorListeners();
            nextScene.setInitialized(true);
        }
        display.setBackgroundToCameraPos(nextScene.getCamera());

        setCurrentScene(nextSceneName);
        switchingScenes = false;
    }

    /** Loads the current scene, if it's not already initialized. */
    public void loadCurrentScene() {
        nullSceneCheck();

        if (!currentScene.isInitialized()) {
            currentScene.load(FastJEngine.getDisplay());
            currentScene.initBehaviorListeners();

            FastJEngine.getDisplay().setBackgroundToCameraPos(currentScene.getCamera());
        }

        currentScene.setInitialized(true);
        switchingScenes = false;
    }

    /**
     * Safely updates the current scene.
     *
     * @param display The {@code Display} that the game renders to.
     */
    private void updateCurrentScene(Display display) {
        boolean[] snapshot = createSnapshot(display);

        try {
            nullSceneCheck();
            initSceneCheck();

            currentScene.update(display);
            currentScene.updateBehaviorListeners();
            currentScene.inputManager.fireKeysDown();

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Safely renders the current scene to the {@code Display}.
     *
     * @param display The {@code Display} that the game renders to.
     */
    private void renderCurrentScene(Display display) {
        boolean[] snapshot = createSnapshot(display);

        try {
            nullSceneCheck();
            initSceneCheck();

            display.render(
                    currentScene.drawableManager.getGameObjects(),
                    currentScene.drawableManager.getUIElements(),
                    currentScene.getCamera()
            );

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Creates a snapshot of the {@code switchingScenes} and {@code isSwitchingFullscreenState} booleans, to make sure
     * the game doesn't crash out due to an attempt to call methods and other fields illegally.
     *
     * @param display The {@code Display} to get the fullscreen state from.
     * @return An array of booleans to check through.
     */
    private boolean[] createSnapshot(Display display) {
        return new boolean[]{
                switchingScenes,
                display.isSwitchingScreenState()
        };
    }

    /**
     * Checks if the logic manager was switching scenes.
     * <p>
     * This method takes a boolean parameter, which is a record of whether the logic manager was switching scenes at the
     * beginning of the parent method call.
     * <p>
     * If the logic manager was not switching scenes, this method will error out the game engine.
     *
     * @param snapshot Record of whether the logic manager was switching scenes at the beginning of the parent method
     *                 call.
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

    /**
     * Checks if the current scene is null.
     * <p>
     * If the current scene is null, this throws a NullPointerException that has a customized message, based on the
     * context of the error.
     */
    private void nullSceneCheck() {
        if (currentScene == null) {
            throw new NullPointerException((scenes.size() < 1)
                    ?
                    "You haven't created a Scene yet, or you haven't added it to the list of scenes for the logic manager."
                            + System.lineSeparator()
                            + "To add a scene, use the addScene(Scene) method in your logic manager."
                            + System.lineSeparator()
                            + "Then, set the current scene to the scene you just added, using the setCurrentScene(Scene) method."

                    :
                    "A current scene hasn't been set."
                            + System.lineSeparator()
                            + "You should set the current scene, using the setCurrentScene(Scene) method from your logic manager."
                            + System.lineSeparator()
                            + "Scenes added: " + scenes.keySet().toString()
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
            IllegalArgumentException e = new IllegalArgumentException(
                    "The scene name \"" + sceneName + "\" is already in use."
                            + System.lineSeparator()
                            + "Scenes added: " + scenes.keySet()
            );

            FastJEngine.error(CrashMessages.SceneError.errorMessage, e);
        }
    }

    /**
     * Checks if the specified scene name corresponds with a scene in the logic manager.
     * <p>
     * If the scene name doesn't correspond with any scenes in the logic manager, this method will error out the game
     * engine.
     *
     * @param sceneName Name of the scene to be checked for.
     */
    private void sceneExistenceCheck(String sceneName) {
        if (!scenes.containsKey(sceneName)) {
            FastJEngine.error(CrashMessages.SceneError.errorMessage,
                    new IllegalArgumentException("A scene with the name: \"" + sceneName + "\" hasn't been added!"));
        }
    }
}
