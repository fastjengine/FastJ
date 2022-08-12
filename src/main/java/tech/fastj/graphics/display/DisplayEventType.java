package tech.fastj.graphics.display;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

/**
 * Event types for {@link DisplayEvent display events}.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum DisplayEventType {

    /** Event type for when the window {@link WindowEvent#WINDOW_OPENED opens}. */
    Open(WindowEvent.WINDOW_OPENED),
    /** Event type for when the window {@link WindowEvent#WINDOW_CLOSED closes}. */
    Close(WindowEvent.WINDOW_CLOSED),
    /** Event type for when the window {@link WindowEvent#WINDOW_CLOSING is closing}. */
    Closing(WindowEvent.WINDOW_CLOSING),
    /** Event type for when the window {@link WindowEvent#WINDOW_ACTIVATED is activated}. */
    Activate(WindowEvent.WINDOW_ACTIVATED),
    /** Event type for when the window {@link WindowEvent#WINDOW_DEACTIVATED is deactivated}. */
    Deactivate(WindowEvent.WINDOW_DEACTIVATED),
    /** Event type for when the window {@link WindowEvent#WINDOW_ICONIFIED is iconified}. */
    Iconify(WindowEvent.WINDOW_ICONIFIED),
    /** Event type for when the window {@link WindowEvent#WINDOW_DEICONIFIED is deiconified}. */
    DeIconify(WindowEvent.WINDOW_DEICONIFIED),
    /** Event type for when the window {@link ComponentEvent#COMPONENT_MOVED is moved}. */
    Move(ComponentEvent.COMPONENT_MOVED),
    /** Event type for when the window {@link ComponentEvent#COMPONENT_RESIZED is resized}. */
    Resize(ComponentEvent.COMPONENT_RESIZED);

    /** The corresponding AWT id for the event. */
    public final int awtId;

    DisplayEventType(int awtId) {
        this.awtId = awtId;
    }

    /**
     * {@return the event type from the given component event}.
     *
     * @param componentEvent The component event to get from.
     * @throws IllegalArgumentException if the event id does not match any of the possible display event types.
     */
    public static DisplayEventType fromEvent(ComponentEvent componentEvent) {
        int eventId = componentEvent.getID();
        for (DisplayEventType displayEventType : values()) {
            if (displayEventType.awtId == eventId) {
                return displayEventType;
            }
        }

        throw new IllegalArgumentException("Invalid display event id: " + eventId);
    }
}
