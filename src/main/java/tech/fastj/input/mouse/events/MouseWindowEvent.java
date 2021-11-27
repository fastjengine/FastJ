package tech.fastj.input.mouse.events;

import tech.fastj.math.Pointf;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseWindowEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf windowInteractionPosition;

    private MouseWindowEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        this.windowInteractionPosition = new Pointf(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    public Pointf getWindowInteractionPosition() {
        return windowInteractionPosition;
    }

    public static MouseWindowEvent fromMouseEvent(MouseEvent mouseEvent) {
        return new MouseWindowEvent(mouseEvent);
    }
}
