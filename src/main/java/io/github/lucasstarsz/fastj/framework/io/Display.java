package io.github.lucasstarsz.fastj.framework.io;

import io.github.lucasstarsz.fastj.framework.CrashMessages;
import io.github.lucasstarsz.fastj.framework.graphics.Camera;
import io.github.lucasstarsz.fastj.framework.graphics.Drawable;
import io.github.lucasstarsz.fastj.framework.graphics.util.DrawUtil;
import io.github.lucasstarsz.fastj.framework.io.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.framework.io.mouse.Mouse;
import io.github.lucasstarsz.fastj.framework.math.Point;
import io.github.lucasstarsz.fastj.framework.math.Pointf;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class that draws to a screen using a combination of Swing's JFrame, and AWT's Canvas.
 *
 * @author Andrew Dey
 * @version 1.0.0
 * @since 1.0.0
 */
public class Display {

    // display and background
    private JFrame outputDisplay;
    private Rectangle2D.Float background;

    // title
    private String displayTitle;
    private String vanityDisplayTitle;
    private boolean shouldDisplayFPSInTitle = false;

    // resolution
    private Point viewerResolution;
    private Point internalResolution;
    private Point lastResolution;

    // full-screen, windowed full-screen
    private boolean isFullscreen;
    private boolean isWindowedFullscreen;
    private boolean switchingScreenState;

    // graphics drawing
    private Canvas drawingCanvas;
    private final Map<RenderingHints.Key, Object> renderHints;

    // input
    private final Mouse mouse;
    private final Keyboard kb;

    // helpers
    private boolean isClosed = false;
    private boolean isReady = false;

    /**
     * Creates a display with the specified title, viewer resolution, and internal resolution.
     *
     * @param title       The title for the {@code Display}.
     * @param viewerRes   The resolution that the user will see.
     * @param internalRes The internal game resolution.
     * @see Point
     */
    public Display(String title, Point viewerRes, Point internalRes) {
        displayTitle = title;
        vanityDisplayTitle = title;
        viewerResolution = viewerRes;
        internalResolution = internalRes.copy();

        lastResolution = new Point();
        renderHints = new LinkedHashMap<>();
        mouse = new Mouse();
        kb = new Keyboard();
    }

    /**
     * Gets the value that determines whether the {@code Display} is ready to be rendered to.
     *
     * @return The status of whether the display is ready to be rendered to.
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Gets the value that determines whether the {@code Display} is closed.
     *
     * @return The status whether the {@code Display} is closed.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Gets the title of the {@code Display}.
     *
     * @return The title.
     */
    public String getTitle() {
        return displayTitle;
    }

    /**
     * Gets the title currently being displayed in the title bar of the {@code Display}.
     * <p>
     * This title takes from the original title, and should only be used for temporary changes of the title.
     *
     * @return The title currently being displayed.
     */
    public String getDisplayedTitle() {
        return vanityDisplayTitle;
    }

    /**
     * Gets the value that determines whether the {@code Display} is showing the FPS in the title bar.
     *
     * @return The status of whether the {@code Display}  is showing the FPS in the title bar.
     */
    public boolean isShowingFPSInTitle() {
        return shouldDisplayFPSInTitle;
    }

    /**
     * Gets the value that determines whether the {@code Display} is showing the title bar.
     *
     * @return The status of whether the {@code Display} is showing the title bar.
     */
    public boolean isShowingTitleBar() {
        return !outputDisplay.isUndecorated();
    }

    /**
     * Gets the viewer resolution of the {@code Display}.
     *
     * @return The viewer resolution, as a {@code Point}.
     */
    public Point getViewerResolution() {
        return viewerResolution;
    }

    /**
     * Gets the internal resolution of the {@code Display}.
     *
     * @return The internal resolution, as a {@code Point}.
     */
    public Point getInternalResolution() {
        return internalResolution;
    }

    /**
     * Gets the scaling of the {@code Display} resolution, as an expression of {@code viewer resolution / internal
     * resolution }.
     * <p>
     * The values for the x and y of the returned {@code Pointf} are {@code 0 < x <= 1}.
     *
     * @return The scale of the {@code Display} resolution.
     */
    public Pointf getResolutionScale() {
        return Pointf.divide(viewerResolution.asPointf(), internalResolution.asPointf());
    }

    /**
     * Gets the value that determines whether the {@code Display} is in full-screen mode.
     *
     * @return The boolean that determines whether the {@code Display} is in full-screen mode.
     */
    public boolean isFullscreen() {
        return isFullscreen;
    }

    /**
     * Gets the value that determines whether the {@code Display} is switching its screen state.
     *
     * @return The full-screen switching state boolean.
     */
    public boolean isSwitchingScreenState() {
        return switchingScreenState;
    }

    /**
     * Gets the value that determines whether the {@code Display} is in windowed full-screen mode.
     *
     * @return Boolean that determines whether the {@code Display} is in windowed full-screen mode.
     */
    public boolean isWindowedFullscreen() {
        return isWindowedFullscreen;
    }

    /**
     * Gets the background of the {@code Display}.
     *
     * @return The background, as a {@code Rectangle2D.Float}.
     * @see Rectangle2D.Float
     */
    public Rectangle2D.Float getBackground() {
        return background;
    }

    /**
     * Gets the color of the background of the {@code Display}.
     *
     * @return The color of the background, as a {@code Color}.
     * @see Color
     */
    public Color getBackgroundColor() {
        return drawingCanvas.getBackground();
    }

    /**
     * Gets the displayed icon of the {@code Display}.
     *
     * @return The icon of the {@code Display}.
     * @see BufferedImage
     */
    public BufferedImage getIcon() {
        return (BufferedImage) outputDisplay.getIconImage();
    }

    /**
     * Gets the {@code JFrame} of the {@code Display}.
     *
     * @return The {@code JFrame} of the {@code Display}.
     */
    public Frame getJFrame() {
        return outputDisplay;
    }

    /**
     * Gets the {@code Canvas} of the {@code Display}.
     *
     * @return The {@code Canvas} of the {@code Display}.
     */
    public Canvas getCanvas() {
        return drawingCanvas;
    }

    /**
     * Gets the {@code Graphics2D} object associated with this {@code Display}, set to the transformation of the current
     * scene's camera.
     *
     * @return The {@code Graphics2D} object which is associated with the {@code Display}.
     * @see Graphics2D
     */
    public Graphics2D getGraphics() {
        return prepareGraphics((Graphics2D) drawingCanvas.getBufferStrategy().getDrawGraphics(), Camera.DEFAULT);
    }

    /**
     * Sets the true title of the {@code Display}.
     * <p>
     * Setting this resets the displayed title.
     *
     * @param newTitle The new title.
     */
    public void setTitle(String newTitle) {
        displayTitle = newTitle;
        vanityDisplayTitle = newTitle;
    }

    /**
     * Sets the title that the end user sees to the specified title.
     *
     * @param vanityTitle The new title that the user will see.
     */
    public void setDisplayedTitle(String vanityTitle) {
        vanityDisplayTitle = vanityTitle;
        outputDisplay.setTitle(vanityDisplayTitle);
    }

    /**
     * Enables or disables displaying the FPS in the title bar of the display.
     *
     * @param enable Boolean parameter that enables/disables the fps being displayed.
     */
    public void showFPSInTitle(boolean enable) {
        shouldDisplayFPSInTitle = enable;

        if (shouldDisplayFPSInTitle) {
            setDisplayedTitle(String.format("%s | FPS: ", displayTitle));
        } else {
            setDisplayedTitle(displayTitle);
        }
    }

    /**
     * Sets the {@code Display}'s viewed resolution.
     * <p>
     * This is the resolution that the viewer sees, and is a scaled version of the game resolution.
     *
     * @param res The new resolution to be set to.
     */
    public void setViewerResolution(Point res) {
        FastJEngine.runningCheck();
        viewerResolution = res.copy();
    }

    /**
     * Sets the {@code Display}'s game resolution.
     * <p>
     * This is the resolution that the game itself is intended for, and looks the best on.
     *
     * @param res The new resolution to be set to.
     */
    public void setInternalResolution(Point res) {
        FastJEngine.runningCheck();
        internalResolution = res.copy();
    }

    /**
     * Sets the display's background color.
     *
     * @param newColor The {@code Color} to be set to.
     */
    public void setBackgroundColor(Color newColor) {
        drawingCanvas.setBackground(newColor);
    }

    /**
     * Sets the display's icon.
     *
     * @param newIcon The icon for the display's icon to be set to.
     */
    public void setIcon(BufferedImage newIcon) {
        outputDisplay.setIconImage(newIcon);
    }

    /**
     * Resizes the {@code Display} to the specified size.
     *
     * @param newResolution The size for the screen to be set to, as a {@code Point}.
     */
    public void resizeDisplay(Point newResolution) {
        // set display size
        outputDisplay.getContentPane().setPreferredSize(new Dimension(newResolution.x, newResolution.y));
        drawingCanvas.setPreferredSize(new Dimension(newResolution.x, newResolution.y));

        // set res points
        lastResolution = viewerResolution.copy();
        viewerResolution = newResolution.copy();

        // set background size
        background.width = internalResolution.x;
        background.height = internalResolution.y;

        // revalidate/pack
        drawingCanvas.revalidate();
        outputDisplay.revalidate();
        outputDisplay.pack();
    }

    /**
     * Sets the {@code Display}'s full-screen state to the specified parameter.
     *
     * @param enable Boolean to set whether the display should be in full-screen mode.
     */
    public void setFullscreen(boolean enable) {
        if (isFullscreen == enable) return;

        switchingScreenState = true;

        isFullscreen = enable;
        outputDisplay.setVisible(false);

        if (isFullscreen) {
            // update res & background
            lastResolution = viewerResolution.copy();
            viewerResolution = getDefaultMonitorDimensions();

            // prepare for full screen
            outputDisplay.dispose();
            outputDisplay.setUndecorated(true);
            outputDisplay.setResizable(false);

            // set full-screen
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .setFullScreenWindow(outputDisplay);
        } else {
            // disable full-screen
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .setFullScreenWindow(outputDisplay);

            // update display
            outputDisplay.dispose();
            outputDisplay.setUndecorated(false);
            outputDisplay.setResizable(true);

            // update res & background
            Point displayResSave = viewerResolution.copy();
            viewerResolution = lastResolution.copy();
            lastResolution = displayResSave;

            outputDisplay.pack();
        }

        outputDisplay.revalidate();
        outputDisplay.setVisible(true);
        drawingCanvas.requestFocusInWindow();

        switchingScreenState = false;
    }

    /** Disables full-screen mode without showing the screen. */
    private void disableFullscreenInvisibly() {
        if (!isFullscreen) return;

        switchingScreenState = true;

        // disable full-screen
        isFullscreen = false;
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(outputDisplay);

        // update display
        outputDisplay.dispose();
        outputDisplay.setUndecorated(false);
        outputDisplay.setResizable(true);

        // update res & background
        Point displayResSave = viewerResolution.copy();
        viewerResolution = lastResolution.copy();
        lastResolution = displayResSave;

        // prepare window
        outputDisplay.pack();
        outputDisplay.revalidate();
        drawingCanvas.requestFocusInWindow();

        switchingScreenState = false;
    }

    /**
     * Sets the display's windowed full-screen state to the specified parameter.
     *
     * @param enable Boolean to set whether the display should be in windowed full-screen mode.
     */
    public void setWindowedFullscreen(boolean enable) {
        if (enable == isWindowedFullscreen) return;

        switchingScreenState = true;

        if (isFullscreen) disableFullscreenInvisibly();

        isWindowedFullscreen = enable;
        outputDisplay.setExtendedState((isWindowedFullscreen) ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);
        showTitleBar(enable);
        outputDisplay.setVisible(true);

        switchingScreenState = false;
    }

    /**
     * Sets whether the title bar of the {@code Display} should be shown.
     *
     * @param enable Boolean to determine whether the title bar of the {@code Display} should be shown.
     */
    public void showTitleBar(boolean enable) {
        if (outputDisplay.isUndecorated() == !enable) return;

        outputDisplay.setVisible(false);

        outputDisplay.dispose();
        outputDisplay.setUndecorated(!enable);
        outputDisplay.pack();

        outputDisplay.setTitle(vanityDisplayTitle);
        outputDisplay.setLocationRelativeTo(null);
        outputDisplay.setVisible(true);
    }

    /**
     * Renders the specified game objects and GUI objects, within the viewing area of the Camera.
     *
     * @param gameObjects The game objects to be rendered.
     * @param gui         The GUI objects to be rendered.
     * @param camera      The camera that the user will view the game from.
     * @see Drawable
     * @see Camera
     */
    public void render(Map<String, Drawable> gameObjects, Map<String, Drawable> gui, Camera camera) {
        if (!outputDisplay.isVisible()) return;

        try {
            BufferStrategy drawBuffer;
            do {
                drawBuffer = drawingCanvas.getBufferStrategy();
            } while (drawBuffer == null);

            Graphics2D drawGraphics = prepareGraphics((Graphics2D) drawBuffer.getDrawGraphics(), camera);
            drawGraphics.clearRect(
                    (int) (background.x - camera.getTranslation().x),
                    (int) (background.y - camera.getTranslation().y),
                    (int) background.width + 1,
                    (int) background.height + 1
            );

            for (Drawable obj : gameObjects.values()) {
                try {
                    if (!isOnScreen(obj, camera)) continue;
                    obj.render(drawGraphics);
                } catch (NullPointerException e) {
                    nullWarnCheck(obj, false, e);
                } catch (Exception e) {
                    FastJEngine.error(CrashMessages.RENDER_ERROR.errorMessage + " | Origin: Game Object Drawable " + obj.getID(), e);
                    return;
                }
            }

            for (Drawable guiObj : gui.values()) {
                try {
                    if (!isOnScreen(guiObj, camera)) continue;
                    guiObj.renderAsGUIObject(drawGraphics, camera);
                } catch (NullPointerException e) {
                    nullWarnCheck(guiObj, true, e);
                } catch (Exception e) {
                    FastJEngine.error(CrashMessages.RENDER_ERROR.errorMessage + " | Origin: GUI Drawable " + guiObj.getID(), e);
                    return;
                }
            }

            drawBuffer.show();
            drawGraphics.dispose();
        } catch (IllegalStateException e) {
            if (!switchingScreenState && !FastJEngine.isRunning()) {
                FastJEngine.error(CrashMessages.illegalAction(getClass()), e);
            }
        }
    }

    /**
     * If there is a null pointer in the render method, this checks to make sure everything else in the game engine is
     * in order before outputting a warning.
     *
     * @param obj           The {@code Drawable} causing the null pointer.
     * @param isGUIDrawable Boolean that determines whether or not the {@code Drawable} is part
     * @param e             The null pointer exception.
     */
    private void nullWarnCheck(Drawable obj, boolean isGUIDrawable, NullPointerException e) {
        if (!FastJEngine.getLogicManager().isSwitchingScenes()) {
            FastJEngine.warning("Null pointer for " + (isGUIDrawable ? "GUI" : "Game Object") + " Drawawble with id: " + obj.getID());
            e.printStackTrace();
            if (isGUIDrawable) {
                FastJEngine.getLogicManager().getCurrentScene().refreshGUIObjectList();
            } else {
                FastJEngine.getLogicManager().getCurrentScene().refreshGameObjectList();
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
     * @see RenderingHints
     * @see RenderingHints.Key
     */
    public void modifyRenderSettings(RenderingHints.Key renderHintKey, Object renderHintValue) {
        renderHints.remove(renderHintKey);
        renderHints.put(renderHintKey, renderHintValue);
    }

    /**
     * Gets the specified monitor.
     *
     * @param monitorIndicated The index number of the monitor to get.
     * @return The specified monitor, as a {@code GraphicsDevice}.
     * @see GraphicsDevice
     */
    public static GraphicsDevice getMonitor(int monitorIndicated) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitorIndicated];
    }

    /**
     * Gets the default monitor.
     *
     * @return The default monitor, as a {@code GraphicsDevice}.
     * @see GraphicsDevice
     */
    public static GraphicsDevice getDefaultMonitor() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    /**
     * Gets the refresh rate of the specified monitor.
     *
     * @param monitorIndicated The monitor to get the refresh rate of.
     * @return The indicated monitor's refresh rate, as an integer value.
     */
    public static int getMonitorRefreshRate(int monitorIndicated) {
        return getMonitor(monitorIndicated).getDisplayMode().getRefreshRate();
    }

    /**
     * Gets the refresh rate of the default monitor.
     *
     * @return The default monitor's refresh rate, as an integer value.
     */
    public static int getDefaultMonitorRefreshRate() {
        return getDefaultMonitor().getDisplayMode().getRefreshRate();
    }

    /**
     * Gets the dimensions of the specified monitor.
     *
     * @param monitorIndicated The monitor to get the dimensions of.
     * @return The indicated monitor's dimensions, as a {@code Point}.
     * @see Point
     */
    public static Point getMonitorDimensions(int monitorIndicated) {
        DisplayMode monitorMode = getMonitor(monitorIndicated).getDisplayMode();
        return new Point(monitorMode.getWidth(), monitorMode.getHeight());
    }

    /**
     * Gets the dimensions of the default monitor.
     *
     * @return The default monitor's dimensions, as a {@code Point}.
     * @see Point
     */
    public static Point getDefaultMonitorDimensions() {
        DisplayMode monitorMode = getDefaultMonitor().getDisplayMode();
        return new Point(monitorMode.getWidth(), monitorMode.getHeight());
    }

    /**
     * Gets the amount of monitors the user has.
     *
     * @return The amount of monitors that the user has.
     */
    public static int getMonitorCount() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
    }

    /**
     * Gets the value that determines whether the {@code Drawable} is visible on screen.
     *
     * @param drawable The {@code Drawable} to check.
     * @param camera   The {@code Camera} to check the drawable with.
     * @return A boolean that represents whether the polygon is visible on screen.
     * @see Drawable
     * @see Camera
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
        if (!isReady) {
            initDisplay();
            resizeDisplay(viewerResolution);
            setRenderHints();
            isReady = true;
        }
    }

    /**
     * Changes the background to the location of the camera.
     *
     * @param camera The camera for the background to be translated to.
     * @see Camera
     */
    public void setBackgroundToCameraPos(Camera camera) {
        final Pointf translation = camera.getTranslation();
        background.setRect(-translation.x, -translation.y, internalResolution.x, internalResolution.y);
    }

    /** Displays the {@code Display}. */
    public void open() {
        isClosed = false;
        outputDisplay.setVisible(true);
        drawingCanvas.requestFocusInWindow();
    }

    /** Closes and disposes of the {@code Display}. */
    public void close() {
        isClosed = true;
        outputDisplay.dispose();
    }

    /** Initializes the display, and all of its components. */
    private void initDisplay() {
        System.setProperty("sun.awt.noerasebackground", "true");
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        Keyboard.init();

        // JFrame display
        outputDisplay = new JFrame(vanityDisplayTitle);
        outputDisplay.getContentPane().setPreferredSize(new Dimension(viewerResolution.x, viewerResolution.y));

        outputDisplay.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        outputDisplay.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent displayEvent) {
                FastJEngine.closeGame();
            }
        });

        outputDisplay.getContentPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!isReady || isFullscreen) return;

                Dimension newSize = outputDisplay.getContentPane().getSize();
                resizeDisplay(new Point(newSize.width, newSize.height));
            }
        });

        // Canvas
        drawingCanvas = new Canvas();
        drawingCanvas.setPreferredSize(new Dimension(viewerResolution.x, viewerResolution.y));
        drawingCanvas.setFocusable(true);

        drawingCanvas.addMouseListener(mouse);
        drawingCanvas.addMouseMotionListener(mouse);
        drawingCanvas.addMouseWheelListener(mouse);
        drawingCanvas.addKeyListener(kb);

        outputDisplay.getContentPane().add(drawingCanvas);
        outputDisplay.pack();
        outputDisplay.setLocationRelativeTo(null);

        drawingCanvas.createBufferStrategy(3);

        // set background rectangle
        background = new Rectangle2D.Float(0, 0, internalResolution.x, internalResolution.y);
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
     * @see Camera
     * @see Graphics2D
     */
    private Graphics2D prepareGraphics(Graphics2D g, Camera camera) {
        g.setRenderingHints(renderHints);
        g.scale(getResolutionScale().x, getResolutionScale().y);
        g.transform(camera.getTransformation());

        return g;
    }
}
