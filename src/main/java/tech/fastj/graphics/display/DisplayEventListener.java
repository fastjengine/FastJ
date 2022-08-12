package tech.fastj.graphics.display;

import tech.fastj.gameloop.event.EventObserver;

/**
 * Listener definition for {@link DisplayEvent display events}.
 *
 * @param <T> The type of {@link Display display} this listener is designed for.
 */
public interface DisplayEventListener<T extends Display> extends EventObserver<DisplayEvent<T>> {

    /**
     * Action called for a {@link DisplayEventType#Move display move} event.
     *
     * @param displayEvent The event received.
     */
    default void displayMoved(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Resize display resize} event.
     *
     * @param displayEvent The event received.
     */
    default void displayResized(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Open display open} event.
     *
     * @param displayEvent The event received.
     */
    default void displayOpened(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Closing display closing} event.
     *
     * @param displayEvent The event received.
     */
    default void displayClosing(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Close display close} event.
     *
     * @param displayEvent The event received.
     */
    default void displayClosed(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Iconify display iconify} event.
     *
     * @param displayEvent The event received.
     */
    default void displayIconified(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#DeIconify display deiconify} event.
     *
     * @param displayEvent The event received.
     */
    default void displayDeiconified(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Activate display activate} event.
     *
     * @param displayEvent The event received.
     */
    default void displayActivated(DisplayEvent<T> displayEvent) {
    }

    /**
     * Action called for a {@link DisplayEventType#Deactivate display deactivate} event.
     *
     * @param displayEvent The event received.
     */
    default void displayDeactivated(DisplayEvent<T> displayEvent) {
    }

    /**
     * General event receiving method.
     *
     * @param displayEvent The event received.
     */
    @Override
    default void eventReceived(DisplayEvent<T> displayEvent) {
        switch (displayEvent.getEventType()) {
            case Open -> displayOpened(displayEvent);
            case Closing -> displayClosing(displayEvent);
            case Close -> displayClosed(displayEvent);
            case Activate -> displayActivated(displayEvent);
            case Deactivate -> displayDeactivated(displayEvent);
            case Iconify -> displayIconified(displayEvent);
            case DeIconify -> displayDeiconified(displayEvent);
            case Move -> displayMoved(displayEvent);
            case Resize -> displayResized(displayEvent);
        }
    }
}
