package tech.fastj.systems.control;

import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.Display;

import tech.fastj.systems.behaviors.BehaviorHandler;
import tech.fastj.systems.behaviors.BehaviorManager;
import tech.fastj.systems.tags.TagHandler;
import tech.fastj.systems.tags.TagManager;

import java.awt.event.InputEvent;

import tech.fastj.input.InputManager;

public abstract class SimpleManager implements LogicManager, BehaviorHandler, TagHandler {

    private final Camera camera;
    public final InputManager inputManager;
    public final DrawableManager drawableManager;

    /**
     * Initializes the contents of the {@code SimpleManager}.
     */
    protected SimpleManager() {
        camera = new Camera();

        inputManager = new InputManager();
        drawableManager = new DrawableManager();

        TagManager.addTaggableEntityList(this);
        BehaviorManager.addListenerList(this);
    }

    /**
     * Renders the contents of the manager's {@code DrawableManager} to the {@code Display}.
     *
     * @param display The {@code Display} that the game renders to.
     */
    @Override
    public void render(Display display) {
        display.render(
                drawableManager.getGameObjects(),
                drawableManager.getUIElements(),
                camera
        );
    }

    /** Processes all stored input events. */
    @Override
    public void processInputEvents() {
        inputManager.processEvents();
    }

    /** Stores the specified input event to be processed later ({@link #processInputEvents()}). */
    @Override
    public void receivedInputEvent(InputEvent inputEvent) {
        inputManager.receivedInputEvent(inputEvent);
    }

    /**
     * Gets the {@code Camera} of the manager.
     *
     * @return The manager's camera.
     */
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void reset() {
        camera.reset();
        inputManager.clearAllLists();
        drawableManager.clearAllLists();
        this.clearTaggableEntities();
        this.clearBehaviorListeners();
    }
}
