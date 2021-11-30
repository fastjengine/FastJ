package tech.fastj.input.mouse.events;

import tech.fastj.math.Pointf;

import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;

public class MouseWindowEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf windowInteractionPosition;
    private final MouseAction eventType;

    private MouseWindowEvent(MouseEvent mouseEvent, MouseAction eventType) {
        this.mouseEvent = mouseEvent;
        this.windowInteractionPosition = new Pointf(mouseEvent.getX(), mouseEvent.getY());
        this.eventType = eventType;
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    @Override
    public MouseAction getEventType() {
        return eventType;
    }

    public Pointf getWindowInteractionPosition() {
        return windowInteractionPosition;
    }

    public static MouseWindowEvent fromMouseEvent(MouseEvent mouseEvent, MouseAction eventType) {
        return new MouseWindowEvent(mouseEvent, eventType);
    }
}
