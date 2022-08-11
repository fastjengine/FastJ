package tech.fastj.systems.control;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.input.InputManager;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.behaviors.BehaviorManager;

import java.util.List;

/**
 * Top-level game structure which is the hub for all game logic, without {@link Scene scenes}.
 * <p>
 * Here is a simple example of the simple manager in action. It {@link FastJEngine#run() Runs FastJ}, where the content shown is
 * {@link Text2D text} saying {@code "Hello FastJ!"}.
 * {@snippet lang = "java":
 * //GameManager.java
 * import tech.fastj.engine.FastJEngine;
 * import tech.fastj.graphics.display.FastJCanvas;
 * import tech.fastj.graphics.display.RenderSettings;
 * import tech.fastj.graphics.game.Text2D;
 * import tech.fastj.systems.control.SimpleManager;
 *
 * public class GameManager extends SimpleManager {
 *
 *     @Override
 *     public void init(FastJCanvas canvas) {
 *         // enable anti-aliasing
 *         canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable);
 *
 *         // say hello to FastJ from a scene!
 *         Text2D helloWorld = Text2D.fromText("Hello FastJ!");
 *         drawableManager().addGameObject(helloWorld);
 *     }
 *
 *     public static void main(String[] args) {
 *          // initialize and run the game, using the simple manager
 *          FastJEngine.init("Scene-Based Game", new GameManager());
 *          FastJEngine.run();
 *     }
 * }}
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public abstract class SimpleManager implements LogicManager, GameHandler {

    private final Camera camera;

    /**
     * Input manager instance for the simple manager -- it controls the simple manager's received input events.
     *
     * @deprecated Public access to this field will be removed soon -- please use {@link #inputManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final InputManager inputManager;

    /**
     * Drawable manager instance for the simple manager -- it controls the simple manager's game objects and ui elements.
     *
     * @deprecated Public access to this field will be removed soon -- please use {@link #drawableManager()} instead.
     */
    @Deprecated(forRemoval = true)
    public final DrawableManager drawableManager;

    /** Initializes the internals of the {@link SimpleManager}. */
    protected SimpleManager() {
        camera = new Camera();

        inputManager = new InputManager();
        drawableManager = new DrawableManager();

        BehaviorManager.addListenerList(this);
    }

    @Override
    public List<Drawable> getTaggableEntities() {
        return drawableManager.getDrawablesList();
    }

    /**
     * Internal method to initialize the simple manager's {@link Behavior behaviors}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform initial behavior setup, you must override
     * {@link Behavior#init(GameObject) the behavior's init method}.
     */
    @Override
    public void initBehaviors() {
        this.initBehaviorListeners();
    }

    /**
     * Internal method to send keyboard events to all {@link KeyboardActionListener keyboard listeners}.
     * <p>
     * For general purposes, <b>do not override this method</b>. Please see {@link KeyboardActionListener} to learn how to listen to the
     * keyboard actions.
     */
    @Override
    public void processKeysDown() {
        inputManager.fireKeysDown();
    }

    /**
     * Internal method to run a fixed update on the simple manager's {@link Behavior behaviors}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform actions in a {@link Behavior game object behavior} during fixed
     * update, you must override {@link Behavior#fixedUpdate(GameObject)} your behavior's fixed update method}.
     */
    @Override
    public void fixedUpdateBehaviors() {
        this.fixedUpdateBehaviorListeners();
    }

    /**
     * Internal method to run an update on the simple manager's {@link Behavior behaviors}.
     * <p>
     * For general purposes, <b>do not override this method</b>. To perform actions in a {@link Behavior game object behavior} during
     * update, you must override {@link Behavior#update(GameObject)} your behavior's update method}.
     */
    @Override
    public void updateBehaviors() {
        this.updateBehaviorListeners();
    }

    /**
     * {@return the simple manager's input manager}
     * <p>
     * This is often used for adding {@link InputManager#addKeyboardActionListener(KeyboardActionListener) keyboard} and
     * {@link InputManager#addMouseActionListener(MouseActionListener) mouse} listeners.
     */
    @Override
    public InputManager inputManager() {
        return inputManager;
    }

    /**
     * {@return the simple manager's drawable manager}
     * <p>
     * This is often used for adding {@link DrawableManager#addGameObject(GameObject) game objects} and
     * {@link DrawableManager#addUIElement(UIElement) ui elements}.
     * <p>
     * The code below is an example segment involving adding a game object to a scene.
     * {@snippet :
     * import tech.fastj.graphics.display.FastJCanvas;
     * import tech.fastj.graphics.game.Polygon2D;
     * import tech.fastj.graphics.util.DrawUtil;
     * import tech.fastj.systems.control.SimpleManager;
     *
     * public class GameManager extends SimpleManager {
     *     @Override
     *     public void init(FastJCanvas canvas) {
     *         Polygon2D box = Polygon2D.fromPath(DrawUtil.createBox(0f, 0f, 10f));
     *         drawableManager().addGameObject(box); // @highlight
     *     }
     * }}
     */
    @Override
    public DrawableManager drawableManager() {
        return drawableManager;
    }

    /** {@return the scene camera} */
    @Override
    public Camera getCamera() {
        return camera;
    }

    /**
     * Internal method to render the simple manager's {@link DrawableManager#getGameObjects() game objects} and
     * {@link DrawableManager#getUIElements() ui}.
     * <p>
     * For general purposes, <b>do not override this method</b>.
     * <ul>
     *     <li>
     *         To render a {@link GameObject game object}, you must create one and {@link DrawableManager#addGameObject(GameObject) add it}
     *         to the simple manager's {@link #drawableManager() drawable manager}.
     *     </li>
     *     <li>
     *         To render a {@link UIElement ui element}, you must create one and {@link DrawableManager#addUIElement(UIElement)} add it}
     *         to the simple manager's {@link #drawableManager() drawable manager}.
     *     </li>
     * </ul>
     */
    @Override
    public void render(FastJCanvas canvas) {
        canvas.render(drawableManager.getGameObjects(), drawableManager.getUIElements(), camera);
    }

    /** Resets the simple manager's state entirely. */
    @Override
    public void reset() {
        this.destroyBehaviorListeners();
        drawableManager.reset(this);

        this.clearBehaviorListeners();
        drawableManager.clearAllLists();

        inputManager.reset();
        camera.reset();
    }
}
