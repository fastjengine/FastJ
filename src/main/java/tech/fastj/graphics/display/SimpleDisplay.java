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
import java.util.concurrent.TimeUnit;

import tech.fastj.gameloop.CoreLoopState;

/**
 * A simple implementation of {@link Display} which includes the following features:
 * <ul>
 *     <li>Window resizing</li>
 *     <li>Fullscreen toggling</li>
 *     <li>Enabling/Disabling the title bar</li>
 *     <li>Window title modification</li>
 *     <li>Window events</li>
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
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" opened", displayTitle);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Open, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" closing", displayTitle);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Closing, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);

                // TODO: find out why this only works as intended during FixedUpdate
                while (FastJEngine.getGameLoop().getCurrentGameLoopState().getCoreLoopState() != CoreLoopState.FixedUpdate) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                FastJEngine.runLater(FastJEngine::closeGame);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" closed", displayTitle);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Close, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" iconified", displayTitle);

                suspendedState = displayState;
                display.updateDisplayState(DisplayState.Iconified);
                FastJEngine.debug("window \"{}\" suspended {} state", displayTitle, suspendedState);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Iconify, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" de-iconified", displayTitle);

                suspendedState = null;
                display.updateDisplayState(oldDisplayState);
                FastJEngine.debug("window \"{}\" un-suspended {} state", displayTitle, suspendedState);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.DeIconify, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" activated", displayTitle);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Activate, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
                SimpleDisplay display = SimpleDisplay.this;
                FastJEngine.debug("window \"{}\" de-activated", displayTitle);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Deactivate, windowEvent, display);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }
        });

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if (displayState == DisplayState.FullScreen) {
                    return;
                }

                Point newSize = new Point(window.getSize());
                FastJEngine.debug("window \"{}\" resize event to {}", displayTitle, newSize);
                resizeDisplay(newSize);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Resize, componentEvent, SimpleDisplay.this);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {
                Point newLocation = new Point(componentEvent.getComponent().getLocation());
                FastJEngine.trace("window \"{}\" moved to {}", displayTitle, newLocation);

                DisplayEvent<SimpleDisplay> displayEvent = new DisplayEvent<>(DisplayEventType.Move, componentEvent, SimpleDisplay.this);
                FastJEngine.getGameLoop().fireEvent(displayEvent);
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

    /**
     * Gets the display's second most recent state.
     *
     * @return The display's old {@link DisplayState}.
     */
    public DisplayState getOldDisplayState() {
        return oldDisplayState;
    }

    /**
     * Gets the display's state from when it was {@link DisplayState#Iconified suspended (iconified)}.
     *
     * @return The display's suspended {@link DisplayState}.
     */
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
        FastJEngine.debug("resized \"{}\" to {}", displayTitle, newResolution);
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
        FastJEngine.debug("Updating window \"{}\"'s state -- current: {}, next: {}", displayTitle, displayState, nextState);
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
