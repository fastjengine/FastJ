package tech.fastj.graphics.display;

import java.awt.event.WindowEvent;

/**
 * AWT window events, abstracted for easier identification and use with FastJ.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum DisplayEvent {

    Opened(WindowEvent.WINDOW_OPENED),
    Closed(WindowEvent.WINDOW_CLOSED),
    Closing(WindowEvent.WINDOW_CLOSING),
    Activated(WindowEvent.WINDOW_ACTIVATED),
    Deactivated(WindowEvent.WINDOW_DEACTIVATED),
    Iconified(WindowEvent.WINDOW_ICONIFIED),
    DeIconified(WindowEvent.WINDOW_DEICONIFIED);

    public final int awtId;

    DisplayEvent(int awtId) {
        this.awtId = awtId;
    }

    public static DisplayEvent fromEvent(WindowEvent awtWindowEvent) {
        int eventId = awtWindowEvent.getID();
        for (DisplayEvent displayEvent : values()) {
            if (displayEvent.awtId == eventId) {
                return displayEvent;
            }
        }

        throw new IllegalArgumentException("Invalid AWT id: " + eventId);
    }
}
