package tech.fastj.graphics.display;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

/**
 * AWT window events, abstracted for easier identification and use with FastJ.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum DisplayEventType {

    Open(WindowEvent.WINDOW_OPENED),
    Close(WindowEvent.WINDOW_CLOSED),
    Closing(WindowEvent.WINDOW_CLOSING),
    Activate(WindowEvent.WINDOW_ACTIVATED),
    Deactivate(WindowEvent.WINDOW_DEACTIVATED),
    Iconify(WindowEvent.WINDOW_ICONIFIED),
    DeIconify(WindowEvent.WINDOW_DEICONIFIED),
    Move(ComponentEvent.COMPONENT_MOVED),
    Resize(ComponentEvent.COMPONENT_RESIZED);

    public final int awtId;

    DisplayEventType(int awtId) {
        this.awtId = awtId;
    }

    public static DisplayEventType fromEvent(WindowEvent awtWindowEvent) {
        int eventId = awtWindowEvent.getID();
        for (DisplayEventType displayEventType : values()) {
            if (displayEventType.awtId == eventId) {
                return displayEventType;
            }
        }

        throw new IllegalArgumentException("Invalid display event id: " + eventId);
    }
}
