package tech.fastj.input.mouse.events;

import tech.fastj.math.Pointf;

import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;

public class MouseMotionEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf mouseLocation;
    private final MouseAction eventType;

    private MouseMotionEvent(MouseEvent mouseEvent, MouseAction eventType) {
        this.mouseEvent = mouseEvent;
        this.mouseLocation = new Pointf(mouseEvent.getX(), mouseEvent.getY());
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

    public Pointf getMouseLocation() {
        return mouseLocation;
    }

    public static MouseMotionEvent fromMouseEvent(MouseEvent mouseEvent, MouseAction eventType) {
        return new MouseMotionEvent(mouseEvent, eventType);
    }
}
