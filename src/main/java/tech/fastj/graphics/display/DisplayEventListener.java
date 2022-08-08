package tech.fastj.graphics.display;

import tech.fastj.gameloop.event.EventObserver;

public interface DisplayEventListener<T extends Display> extends EventObserver<DisplayEvent<T>> {

    default void displayMoved(DisplayEvent<T> displayEvent) {
    }

    default void displayResized(DisplayEvent<T> displayEvent) {
    }

    default void displayOpened(DisplayEvent<T> displayEvent) {
    }

    default void displayClosing(DisplayEvent<T> displayEvent) {
    }

    default void displayClosed(DisplayEvent<T> displayEvent) {
    }

    default void displayIconified(DisplayEvent<T> displayEvent) {
    }

    default void displayDeiconified(DisplayEvent<T> displayEvent) {
    }

    default void displayActivated(DisplayEvent<T> displayEvent) {
    }

    default void displayDeactivated(DisplayEvent<T> displayEvent) {
    }

    @Override
    default void eventReceived(DisplayEvent<T> displayEvent) {
        switch (displayEvent.getEventType()) {
            case Open: {
                displayOpened(displayEvent);
                break;
            }
            case Closing: {
                displayClosing(displayEvent);
                break;
            }
            case Close: {
                displayClosed(displayEvent);
                break;
            }
            case Activate: {
                displayActivated(displayEvent);
                break;
            }
            case Deactivate: {
                displayDeactivated(displayEvent);
                break;
            }
            case Iconify: {
                displayIconified(displayEvent);
                break;
            }
            case DeIconify: {
                displayDeiconified(displayEvent);
                break;
            }
            case Move: {
                displayMoved(displayEvent);
                break;
            }
            case Resize: {
                displayResized(displayEvent);
                break;
            }
        }
    }
}
