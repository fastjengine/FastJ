package tech.fastj.input.mouse.events;

import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;

public class MouseButtonEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final int button;
    private final int clickCount;
    private final MouseAction eventType;

    private MouseButtonEvent(MouseEvent mouseEvent, MouseAction eventType) {
        this.mouseEvent = mouseEvent;
        this.eventType = eventType;
        this.button = mouseEvent.getButton();
        this.clickCount = mouseEvent.getClickCount();
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    @Override
    public MouseAction getEventType() {
        return eventType;
    }

    public int getMouseButton() {
        return button;
    }

    public int getClickCount() {
        return clickCount;
    }

    public static MouseButtonEvent fromMouseEvent(MouseEvent mouseEvent, MouseAction eventType) {
        return new MouseButtonEvent(mouseEvent, eventType);
    }
}
