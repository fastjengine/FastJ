package tech.fastj.input.mouse.events;

import tech.fastj.math.Pointf;

import java.awt.event.MouseEvent;

public class MouseMotionEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final Pointf mouseLocation;
    private final boolean isDrag;

    private MouseMotionEvent(MouseEvent mouseEvent, boolean isDrag) {
        this.mouseEvent = mouseEvent;
        this.mouseLocation = new Pointf(mouseEvent.getX(), mouseEvent.getY());
        this.isDrag = isDrag;
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    public Pointf getMouseLocation() {
        return mouseLocation;
    }

    public boolean isDrag() {
        return isDrag;
    }

    public static MouseMotionEvent fromMouseEvent(MouseEvent mouseEvent, boolean isDrag) {
        return new MouseMotionEvent(mouseEvent, isDrag);
    }
}
