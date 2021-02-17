package io.github.lucasstarsz.fastj.engine.systems.game;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.engine.graphics.Drawable;
import io.github.lucasstarsz.fastj.engine.io.Display;
import io.github.lucasstarsz.fastj.engine.io.Mouse;
import io.github.lucasstarsz.fastj.engine.util.CrashMessages;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The manager which allows for control over the scenes in a game.
 * <p>
 * A {@code LogicManager} acts as a bridge between the internals of the engine, and the developer's
 * code.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 */
public abstract class LogicManager {

    private final Map<String, Scene> scenes = new LinkedHashMap<>();
    private Scene currentScene;
    private boolean switchingScenes;

    /**
     * Set up the game scenes, the display, and everything in between.
     * <p>
     * This method is called after the engine has been set up, and the display has been created. As
     * it is only called once, it is the best place to set some initial settings that apply to the
     * entire game.
     *
     * @param display The {@code Display} that the game renders to.
     * @see io.github.lucasstarsz.fastj.engine.io.Display
     */
    public abstract void setup(Display display);

    /**
     * Updates the current scene, its behaviors, and listeners.
     *
     * @param display The {@code Display} that the game renders to.
     * @see io.github.lucasstarsz.fastj.engine.io.Display
     */
    public void update(Display display) {
        updateCurrentScene(display);
    }

    /**
     * Renders the current scene to the {@code Display}.
     *
     * @param display The {@code Display} that the game renders to.
     * @see io.github.lucasstarsz.fastj.engine.io.Display
     */
    public void render(Display display) {
        renderCurrentScene(display);
    }

    /**
     * Gets the currently active scene.
     *
     * @return Returns the currently active scene.
     *
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Gets the list of all scenes in the logic manager.
     *
     * @return Returns the list of scenes in the logic manager.
     *
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public List<Scene> getScenes() {
        return Collections.list(Collections.enumeration(scenes.values()));
    }

    /**
     * Gets the scene with the specified scene name, if it exists.
     *
     * @param sceneName The name of the scene to retrieve.
     * @return The scene, if it exists.
     *
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public Scene getScene(String sceneName) {
        sceneExistenceCheck(sceneName);
        return scenes.get(sceneName);
    }

    /**
     * Gets the boolean that specifies whether the logic manager is currently switching scenes.
     *
     * @return Returns a boolean that specifies whether the logic manager is currently switching
     * scenes.
     */
    public boolean isSwitchingScenes() {
        return switchingScenes;
    }

    /**
     * Sets the current scene to the scene specified.
     * <p>
     * Instead of using this method to switch scenes, it is preferred that you use the {@code
     * switchScene(String nextScene)} method.
     *
     * @param scene The scene which the current scene will be set to.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public void setCurrentScene(Scene scene) {
        setCurrentScene(scene.getSceneName());
    }

    /**
     * Sets the current scene to the scene with the name specified.
     * <p>
     * Instead of using this method to switch scenes, it is preferred that you use the {@code
     * switchScene(String nextScene)} method.
     *
     * @param sceneName The name of the scene which the current scene will be set to.
     */
    public void setCurrentScene(String sceneName) {
        sceneExistenceCheck(sceneName);

        currentScene = scenes.get(sceneName);
        switchingScenes = !currentScene.isInitialized();
    }

    /**
     * Fires an event to the current scene, based on which event type it is.
     *
     * @param action The type of mouse action.
     * @param event  The mouse event information to be passed through.
     * @see io.github.lucasstarsz.fastj.engine.io.Mouse.MouseAction
     * @see MouseEvent
     */
    public void fireMouseAction(Mouse.MouseAction action, MouseEvent event) {
        switch (action) {
            case PRESS:
                currentScene.fireMousePressed(event);
                break;
            case RELEASE:
                currentScene.fireMouseReleased(event);
                break;
            case CLICK:
                currentScene.fireMouseClicked(event);
                break;
            case MOVE:
                currentScene.fireMouseMoved(event);
                break;
            case DRAG:
                currentScene.fireMouseDragged(event);
                break;
            case ENTER:
                currentScene.fireMouseEntered(event);
                break;
            case EXIT:
                currentScene.fireMouseExited(event);
                break;
            default: {
                FastJEngine.error(
                        CrashMessages.theGameCrashed("an unexpected MouseAction value"),
                        new IllegalArgumentException("Unexpected value: " + action.name())
                );
            }
        }
    }

    /**
     * Fires a mouse wheel event to the current scene.
     * <p>
     * This event would be with the other mouse event types, but its event type is dissimilar to the
     * others.
     *
     * @param event The mouse wheel scroll event information to be passed through.
     * @see MouseWheelEvent
     */
    public void fireMouseWheelAction(MouseWheelEvent event) {
        currentScene.fireMouseWheelScrolled(event);
    }

    /**
     * Fires a "key recently pressed" event to the current scene.
     *
     * @param event The key event information to pass through.
     * @see KeyEvent
     */
    public void fireKeyRecentlyPressed(KeyEvent event) {
        currentScene.fireKeyRecentlyPressed(event);
    }

    /**
     * Fires a "key recently released" event to the current scene.
     *
     * @param event The key event information to pass through.
     * @see KeyEvent
     */
    public void fireKeyReleased(KeyEvent event) {
        currentScene.fireKeyReleased(event);
    }

    /**
     * Fires a "key recently typed" event to the current scene.
     *
     * @param event The key event information to pass through.
     * @see KeyEvent
     */
    public void fireKeyTyped(KeyEvent event) {
        currentScene.fireKeyTyped(event);
    }

    /**
     * Adds the specified scene into the logic manager.
     *
     * @param scene The Scene object to be added.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
     */
    public void addScene(Scene scene) {
        sceneNameAlreadyExistsCheck(scene.getSceneName());
        scenes.put(scene.getSceneName(), scene);
    }

    /**
     * Removes the specified scene from the logic manager.
     *
     * @param scene The Scene object to be removed.
     * @see io.github.lucasstarsz.fastj.engine.systems.game.Scene
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
     * This is the preferred method of switching from one scene to another. However, it does not
     * unload the last scene. That has to be done by the user.
     *
     * @param nextSceneName The name of the next Scene to be loaded.
     */
    public void switchScenes(String nextSceneName) {
        if (!scenes.containsKey(nextSceneName)) {
            FastJEngine.error(CrashMessages.SCENE_ERROR.errorMessage,
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
     * @see io.github.lucasstarsz.fastj.engine.io.Display
     */
    private void updateCurrentScene(Display display) {
        boolean[] snapshot = createSnapshot(display);

        try {
            nullSceneCheck();
            initSceneCheck();

            currentScene.update(display);
            currentScene.updateBehaviorListeners();
            currentScene.fireKeysDown();

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Safely renders the current scene to the Display.
     *
     * @param display The {@code Display} that the game renders to.
     * @see io.github.lucasstarsz.fastj.engine.io.Display
     */
    private void renderCurrentScene(Display display) {
        boolean[] snapshot = createSnapshot(display);

        try {
            nullSceneCheck();
            initSceneCheck();

            // create reference copies to avoid concurrency modification through events
            Map<String, Drawable> gameObjectsCopy = new LinkedHashMap<>(currentScene.getGameObjects());
            Map<String, Drawable> guiCopy = new LinkedHashMap<>(currentScene.getGUIObjects());

            display.render(gameObjectsCopy, guiCopy, currentScene.getCamera());

        } catch (NullPointerException e) {
            snapshotCheck(snapshot, e);
        }
    }

    /**
     * Creates a snapshot of the {@code switchingScenes} and {@code isSwitchingFullscreenState}
     * booleans, to make sure the game doesn't crash out due to an attempt to call methods and other
     * fields illegally.
     *
     * @param display The {@code Display} to get the fullscreen state from.
     * @return An array of booleans to check through.
     *
     * @see io.github.lucasstarsz.fastj.engine.io.Display
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
     * This method takes a boolean parameter, which is a record of whether the logic manager was
     * switching scenes at the beginning of the parent method call.
     * <p>
     * If the logic manager was not switching scenes, this method will error out the game engine.
     *
     * @param snapshot Record of whether the logic manager was switching scenes at the beginning of
     *                 the parent method call.
     * @param e        The NullPointerException that would be used in the error call.
     */
    private void snapshotCheck(boolean[] snapshot, NullPointerException e) {
        for (boolean b : snapshot) {
            if (b) {
                return;
            }
        }

        FastJEngine.error(CrashMessages.SCENE_ERROR.errorMessage, e);
    }

    /**
     * Checks if the current scene is null.
     * <p>
     * If the current scene is null, this throws a NullPointerException that has a customized
     * message, based on the context of the error.
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
     * If the current scene isn't initialized, this throws a NullPointerException with a message
     * that says so.
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
                            + "Scenes added: " + scenes.keySet().toString()
            );

            FastJEngine.error(CrashMessages.SCENE_ERROR.errorMessage, e);
        }
    }

    /**
     * Checks if the specified scene name corresponds with a scene in the logic manager.
     * <p>
     * If the scene name doesn't correspond with any scenes in the logic manager, this method will
     * error out the game engine.
     *
     * @param sceneName Name of the scene to be checked for.
     */
    private void sceneExistenceCheck(String sceneName) {
        if (!scenes.containsKey(sceneName)) {
            FastJEngine.error(CrashMessages.SCENE_ERROR.errorMessage,
                    new IllegalArgumentException("A scene with the name: \"" + sceneName + "\" hasn't been added!"));
        }
    }

    /** Resets the logic manager. */
    public void reset() {
        for (Scene s : scenes.values()) {
            if (s.isInitialized()) {
                s.unload(FastJEngine.getDisplay());
            }
        }
        scenes.clear();
    }
}
