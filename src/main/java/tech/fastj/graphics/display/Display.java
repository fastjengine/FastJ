package tech.fastj.graphics.display;

import tech.fastj.math.Point;
import tech.fastj.math.Pointf;

import java.awt.Window;

/**
 * The basic infrastructure all FastJ Displays should have.
 *
 * <ul>
 *     <li>A {@link DisplayState display state}</li>
 *     <li>A {@link Window window} to display the content on</li>
 *     <li>{@link #open()} and {@link #close()} functionalities</li>
 *     <li>Screen {@link #getScreenSize() size} &amp; {@link #getScreenCenter() centerpoint} convenience methods</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public interface Display {

    /** {@return the {@code Display}'s current screen state} */
    DisplayState getDisplayState();

    /** {@return the {@code Display}'s {@link Window window}} */
    Window getWindow();

    /** {@return The {@link Display}'s size} */
    default Point getScreenSize() {
        return new Point(getWindow().getSize());
    }

    /** {@return the centerpoint of the {@link Display}} */
    default Pointf getScreenCenter() {
        return new Point(getWindow().getSize()).asPointf().divide(2f);
    }

    /** Displays the {@link Display} and its underlying {@link #getWindow() window}. */
    default void open() {
        Window window = getWindow();
        window.revalidate();
        window.setVisible(true);
        window.requestFocusInWindow();
    }

    /** Closes and disposes of the {@link Display display}'s {@link #getWindow() window}. */
    default void close() {
        Window window = getWindow();
        window.setVisible(false);
        window.dispose();
    }
}
