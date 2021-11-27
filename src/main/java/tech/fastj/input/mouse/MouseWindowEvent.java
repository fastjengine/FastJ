package tech.fastj.input.mouse;

import tech.fastj.math.Pointf;

import java.awt.event.MouseEvent;

public class MouseWindowEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf windowInteractionPosition;

    public MouseWindowEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        this.windowInteractionPosition = new Pointf(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public Pointf getWindowInteractionPosition() {
        return windowInteractionPosition;
    }
}
