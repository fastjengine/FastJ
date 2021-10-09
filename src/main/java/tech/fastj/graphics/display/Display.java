package tech.fastj.graphics.display;

import tech.fastj.math.Point;
import tech.fastj.math.Pointf;

import java.awt.Window;

/**
 * The basic infrastructure all FastJ {@code Display}s should have:
 * <ul>
 *     <li>A {@link DisplayState display state}</li>
 *     <li>A {@link Window window} to display the content on</li>
 *     <li>{@link #open()} and {@link #close()} functionalities</li>
 *     <li>Screen size &amp; screen center convenience methods</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public interface Display {

    /**
     * Gets the {@code Display}'s current screen state.
     *
     * @return The {@code Display}'s state.
     */
    DisplayState getDisplayState();

    /**
     * Gets the {@code Display}'s {@link Window window} instance.
     *
     * @return The {@code Display}'s window.
     */
    Window getWindow();

    /**
     * Gets the size of the {@code Display}.
     *
     * @return The {@code Display}'s size.
     */
    default Point getScreenSize() {
        return new Point(getWindow().getSize());
    }

    /**
     * Gets the centerpoint of the {@code Display}.
     *
     * @return The {@code Display}'s centerpoint.
     */
    default Pointf getScreenCenter() {
        return new Point(getWindow().getSize()).asPointf().divide(2f);
    }

    /** Displays the {@code Display}. */
    default void open() {
        Window window = getWindow();
        window.revalidate();
        window.setVisible(true);
        window.requestFocusInWindow();
    }

    /** Closes and disposes of the {@code Display}. */
    default void close() {
        Window window = getWindow();
        window.setVisible(false);
        window.dispose();
    }
}
