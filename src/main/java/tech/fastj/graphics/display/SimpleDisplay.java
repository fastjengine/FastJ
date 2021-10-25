package tech.fastj.graphics.display;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Point;

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A simple implementation of {@link Display} which includes the following features:
 * <ul>
 *     <li>Window resizing</li>
 *     <li>Fullscreen toggling</li>
 *     <li>Enabling/Disabling the title bar</li>
 *     <li>Window title string modification</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public class SimpleDisplay implements Display {

    /** The default title used in a {@code SimpleDisplay}. */
    public static final String DefaultTitle = "New FastJ Program";
    /** The default size of a {@code SimpleDisplay}. */
    public static final Point DefaultDisplaySize = new Point(1280, 720);

    private final JFrame window;

    private String displayTitle;

    private DisplayState displayState;
    private DisplayState oldDisplayState;
    private DisplayState suspendedState;

    private static final Map<DisplayEvent, BiConsumer<SimpleDisplay, WindowEvent>> windowEvents = Map.of(
            DisplayEvent.Opened, (display, windowEvent) -> FastJEngine.log("window \"" + display.displayTitle + "\" opened"),
            DisplayEvent.Closing, (display, windowEvent) -> {
                FastJEngine.log("window \"" + display.displayTitle + " closing");
                FastJEngine.forceCloseGame();
                display.close();
            },
            DisplayEvent.Closed, (display, windowEvent) -> FastJEngine.log("window \"" + display.displayTitle + "\" closed"),
            DisplayEvent.Iconified, (display, windowEvent) -> {
                FastJEngine.log("window \"" + display.displayTitle + "\" iconified");
                display.suspendedState = display.displayState;
                display.updateDisplayState(DisplayState.Iconified);
                FastJEngine.log("suspended " + display.suspendedState);
            },
            DisplayEvent.DeIconified, (display, windowEvent) -> {
                FastJEngine.log("window \"" + display.displayTitle + "\" de-iconified");
                display.suspendedState = null;
                display.updateDisplayState(display.oldDisplayState);
                FastJEngine.log("removed suspended " + display.suspendedState);
            },
            DisplayEvent.Activated, (display, windowEvent) -> FastJEngine.log("window \"" + display.displayTitle + "\" activated"),
            DisplayEvent.Deactivated, (display, windowEvent) -> FastJEngine.log("window \"" + display.displayTitle + "\" de-activated")
    );

    public SimpleDisplay() {
        this(DefaultTitle, DefaultDisplaySize);
    }

    public SimpleDisplay(String initialWindowName, Point initialDisplaySize) {
        window = new JFrame();
        setTitle(initialWindowName);

        window.setSize(initialDisplaySize.asDimension());
        window.setPreferredSize(initialDisplaySize.asDimension());
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
                pipeEvent(windowEvent);
            }

            void pipeEvent(WindowEvent windowEvent) {
                windowEvents.get(DisplayEvent.fromEvent(windowEvent)).accept(SimpleDisplay.this, windowEvent);
            }
        });

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (displayState == DisplayState.FullScreen) {
                    return;
                }

                Point newSize = new Point(window.getSize());
                FastJEngine.log("window \"" + displayTitle + "\" resize event to " + newSize);
                resizeDisplay(newSize);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                Point newLocation = new Point(e.getComponent().getLocation());
                FastJEngine.log("window \"" + displayTitle + "\" moved to " + newLocation);
            }
        });

        updateDisplayState(DisplayState.Windowed);
    }

    /**
     * Gets the value that determines whether the {@code SimpleDisplay} is showing the title bar.
     *
     * @return The status of whether the {@code SimpleDisplay} is showing the title bar.
     */
    public boolean isShowingTitleBar() {
        return !window.isUndecorated();
    }

    /**
     * Gets the title of the {@code SimpleDisplay}.
     *
     * @return The title.
     */
    public String getTitle() {
        return displayTitle;
    }

    @Override
    public DisplayState getDisplayState() {
        return displayState;
    }

    public DisplayState getOldDisplayState() {
        return oldDisplayState;
    }

    public DisplayState getSuspendedState() {
        return suspendedState;
    }

    @Override
    public JFrame getWindow() {
        return window;
    }

    /**
     * Resizes the {@code SimpleDisplay} to the specified size.
     *
     * @param newResolution The size for the screen to be set to, as a {@code Point}.
     */
    public void resizeDisplay(Point newResolution) {
        window.setSize(newResolution.asDimension());
        FastJEngine.log("resized \"" + displayTitle + "\" to " + newResolution);
        FastJEngine.getCanvas().resize(newResolution);
        revalidateWindow();
    }

    /** Toggles on/off the {@link #getWindow() window}'s full-screen mode. */
    public void toggleFullScreen() {
        if (displayState == DisplayState.Iconified) {
            updateDisplayState(suspendedState);
            window.setExtendedState(JFrame.NORMAL);
        }

        if (displayState == DisplayState.FullScreen) {
            toggleFullScreenWindow();
            disposeWindow();
            window.pack();
            updateDisplayState(oldDisplayState);
        } else {
            disposeWindow();
            toggleFullScreenWindow();
            updateDisplayState(DisplayState.FullScreen);
        }

        revalidateWindow();
    }

    /**
     * Sets whether the title bar of the {@code SimpleDisplay} should be shown.
     *
     * @param enable Boolean to determine whether the title bar of the {@code SimpleDisplay} should be shown.
     */
    public void showTitleBar(boolean enable) {
        if (isShowingTitleBar() == enable) {
            return;
        }

        window.setVisible(false);

        window.dispose();
        window.setUndecorated(!enable);
        window.pack();

        window.setTitle(displayTitle);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Sets the title of the {@code SimpleDisplay}.
     * <p>
     * Setting this resets the displayed title.
     *
     * @param newTitle The new title.
     */
    public void setTitle(String newTitle) {
        displayTitle = newTitle;
        window.setTitle(displayTitle);
    }

    /**
     * Sets the icon used for the {@code SimpleDisplay}.
     *
     * @param icon The new icon image to use.
     */
    public void setIcon(Image icon) {
        window.setIconImage(icon);
    }

    private void updateDisplayState(DisplayState nextState) {
        FastJEngine.log(String.format("Current window state: %s, next window state: %s", displayState, nextState));
        oldDisplayState = displayState;
        displayState = nextState;
    }

    private void toggleFullScreenWindow() {
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(window);
    }

    private void disposeWindow() {
        window.setVisible(false);
        window.dispose();
        window.setUndecorated(!isShowingTitleBar());
    }

    private void revalidateWindow() {
        window.revalidate();
        window.setVisible(true);
        window.requestFocus();
    }
}
