package tech.fastj.graphics.display;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.game.GameObject;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.input.InputActionEvent;
import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.mouse.Mouse;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FastJ's main rendering screen. This can be added in whatever way needed to any given {@link java.awt.Window window}.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public class FastJCanvas {

    /** Integer representing the default back buffer amount of {@code 3}. */
    public static final int DefaultBackBufferAmount = 3;

    // input
    private final Mouse mouse;
    private final Keyboard keyboard;

    // display and background
    private final Display display;
    private Rectangle2D.Float background;

    // resolution
    private Point resolution;

    // graphics drawing
    private final Map<RenderingHints.Key, Object> renderHints;
    private Canvas canvas;

    /**
     * Creates a display with the specified title, window resolution, and internal resolution.
     *
     * @param canvasResolution The internal game resolution.
     */
    public FastJCanvas(Display display, Point canvasResolution) {
        this.display = display;
        resolution = canvasResolution.copy();

        renderHints = new LinkedHashMap<>();
        mouse = new Mouse();
        keyboard = new Keyboard();
    }

    /**
     * Gets the internal resolution of the {@code Display}.
     *
     * @return The internal resolution, as a {@code Point}.
     */
    public Point getResolution() {
        return resolution;
    }

    /**
     * Sets the {@code Display}'s game resolution.
     * <p>
     * This is the resolution that the game itself is intended for, and looks the best on.
     *
     * @param res The new resolution to be set to.
     */
    public void setResolution(Point res) {
        FastJEngine.runningCheck();
        resolution = res.copy();
    }

    /**
     * Gets the scaling of the {@code Display} resolution.
     * <p>
     * The scale is represented as an expression of {@code windowResolution / internalResolution}.
     * <p>
     * The values for the x and y of the returned {@code Pointf} are {@code 0 < x <= 1}.
     *
     * @return The scale of the {@code Display} resolution.
     */
    public Pointf getResolutionScale() {
        return Pointf.divide(new Point(display.getWindow().getSize()).asPointf(), resolution.asPointf());
    }

    /**
     * Gets the centerpoint of the {@code Display}'s internal resolution.
     *
     * @return The centerpoint of the internal resolution as a {@code Pointf}.
     */
    public Pointf getCanvasCenter() {
        return Pointf.divide(resolution.asPointf(), 2f);
    }

    /**
     * Gets the background of the {@code Display}.
     *
     * @return The background, as a {@code Rectangle2D.Float}.
     */
    public Rectangle2D.Float getBackground() {
        return background;
    }

    /**
     * Gets the color of the background of the {@code Display}.
     *
     * @return The color of the background, as a {@code Color}.
     */
    public Color getBackgroundColor() {
        return canvas.getBackground();
    }

    /**
     * Sets the display's background color.
     *
     * @param newColor The {@code Color} to be set to.
     */
    public void setBackgroundColor(Color newColor) {
        canvas.setBackground(newColor);
    }

    /**
     * Gets the {@code Canvas} of the {@code Display}.
     *
     * @return The {@code Canvas} of the {@code Display}.
     */
    public Canvas getRawCanvas() {
        return canvas;
    }

    /**
     * Gets the {@code Graphics2D} object associated with this {@code Display}, set to the transformation of the current
     * scene's camera.
     *
     * @return The {@code Graphics2D} object which is associated with the {@code Display}.
     */
    public Graphics2D getGraphics() {
        return prepareGraphics((Graphics2D) canvas.getBufferStrategy().getDrawGraphics(), Camera.Default);
    }

    /**
     * Renders the specified game objects and GUI objects, within the viewing area of the Camera.
     *
     * @param gameObjects The game objects to be rendered.
     * @param gui         The GUI objects to be rendered.
     * @param camera      The camera that the user will view the game from.
     */
    public void render(Map<String, GameObject> gameObjects, Map<String, UIElement<? extends InputActionEvent>> gui, Camera camera) {
        if (!display.getWindow().isVisible()) {
            return;
        }

        try {
            BufferStrategy drawBuffer;
            do {
                drawBuffer = canvas.getBufferStrategy();
            } while (drawBuffer == null);

            Graphics2D drawGraphics = prepareGraphics((Graphics2D) drawBuffer.getDrawGraphics(), camera);
            drawGraphics.clearRect(
                    (int) (background.x - camera.getTranslation().x),
                    (int) (background.y - camera.getTranslation().y),
                    // add 1 to these values to account for floating point cutoff, since this has to be all integers
                    (int) background.width + 1,
                    (int) background.height + 1
            );

            for (GameObject gameObject : gameObjects.values()) {
                try {
                    if (!isOnScreen(gameObject, camera) || !gameObject.shouldRender()) {
                        continue;
                    }
                    gameObject.render(drawGraphics);
                } catch (Exception exception) {
                    FastJEngine.error(CrashMessages.RenderError.errorMessage + " | Origin: " + gameObject.getID(), exception);
                    return;
                }
            }

            for (UIElement<? extends InputActionEvent> guiObj : gui.values()) {
                try {
                    if (!isOnScreen(guiObj, camera) || !guiObj.shouldRender()) {
                        continue;
                    }
                    guiObj.renderAsGUIObject(drawGraphics, camera);
                } catch (Exception exception) {
                    FastJEngine.error(CrashMessages.RenderError.errorMessage + " | Origin: " + guiObj.getID(), exception);
                    return;
                }
            }

            drawBuffer.show();
            drawGraphics.dispose();
        } catch (IllegalStateException exception) {
            if (!FastJEngine.isRunning()) {
                FastJEngine.error(CrashMessages.illegalAction(getClass()), exception);
            }
        }
    }

    /**
     * Changes the rendering settings for the specified key.
     * <p>
     * This takes advantage of the {@code RenderingHints} class, allowing the programmer to change values of how the
     * game engine renders objects.
     *
     * @param renderHintKey   Rendering hint key used to determine which setting you are modifying.
     * @param renderHintValue The value to go along with the key.
     */
    public void modifyRenderSettings(RenderingHints.Key renderHintKey, Object renderHintValue) {
        renderHints.remove(renderHintKey);
        renderHints.put(renderHintKey, renderHintValue);
    }

    /**
     * Changes the global rendering settings based on the provided option.
     *
     * @param renderSetting The {@link RenderSettings} value used to change the global rendering settings.
     */
    public void modifyRenderSettings(RenderSettings renderSetting) {
        renderHints.remove(renderSetting.key);
        renderHints.put(renderSetting.key, renderSetting.value);
    }

    /**
     * Gets the value that determines whether the {@code Drawable} is visible on screen.
     *
     * @param drawable The {@code Drawable} to check.
     * @param camera   The {@code Camera} to check the drawable with.
     * @return A boolean that represents whether the polygon is visible on screen.
     */
    public boolean isOnScreen(Drawable drawable, Camera camera) {
        Rectangle2D.Float drawableBounds = DrawUtil.createRect(drawable.getBounds());
        drawableBounds.x += camera.getTranslation().x;
        drawableBounds.y += camera.getTranslation().y;

        return drawableBounds.intersects(background);
    }

    /**
     * Initializes the {@code Display}.
     * <p>
     * This method should only be called once per instance of the {@code Display}. Furthermore, this method should not
     * be used on the default window for the game engine - it is initialized internally.
     */
    public void init() {
        initCanvas();
        setRenderHints();
    }

    /**
     * Resizes the canvas to the given {@code Point} value.
     *
     * @param newResolution The new canvas size.
     */
    public void resize(Point newResolution) {
        canvas.setPreferredSize(newResolution.asDimension());
        canvas.revalidate();
        FastJEngine.debug("resized canvas to {}", newResolution);
    }

    /**
     * Changes the background to the location of the camera.
     *
     * @param camera The camera for the background to be translated to.
     */
    public void setBackgroundToCameraPos(Camera camera) {
        final Pointf translation = camera.getTranslation();
        background.setRect(-translation.x, -translation.y, resolution.x, resolution.y);
    }

    /** Initializes the display, and all of its components. */
    private void initCanvas() {
        Keyboard.init();
        Mouse.init();

        // Canvas
        canvas = new Canvas();
        canvas.setPreferredSize(display.getWindow().getSize());
        canvas.setFocusable(true);

        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        canvas.addMouseWheelListener(mouse);
        canvas.addKeyListener(keyboard);

        display.getWindow().add(canvas);
        display.getWindow().pack();
        display.getWindow().setLocationRelativeTo(null);

        canvas.createBufferStrategy(FastJCanvas.DefaultBackBufferAmount);

        // set background rectangle
        background = new Rectangle2D.Float(0f, 0f, resolution.x, resolution.y);
    }

    /** Sets the default state of the rendering hints for the {@code Display}. */
    private void setRenderHints() {
        renderHints.clear();

        // All
        renderHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Shapes
        renderHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        // Colors
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        renderHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        renderHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

        // Images
        renderHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

        // Text
        renderHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        renderHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
    }

    /**
     * Prepares the provided {@code Graphics2D} object.
     * <p>
     * This scales the object by the current display's resolution scale, sets its rendering hints to the current {@code
     * Display}'s rendering hints, and transforms it based on the specified camera's transformation.
     *
     * @param g      The {@code Graphics2D} object to be prepared.
     * @param camera The camera used to prepare the graphics object.
     * @return A prepared version of the original {@code Graphics2D} object.
     */
    private Graphics2D prepareGraphics(Graphics2D g, Camera camera) {
        g.setRenderingHints(renderHints);
        g.scale(getResolutionScale().x, getResolutionScale().y);
        g.transform(camera.getTransformation());

        return g;
    }
}
