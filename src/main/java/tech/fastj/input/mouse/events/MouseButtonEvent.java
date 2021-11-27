package tech.fastj.input.mouse.events;

import java.awt.event.MouseEvent;

public class MouseButtonEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final int button;
    private final int clickCount;

    private MouseButtonEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        this.button = mouseEvent.getButton();
        this.clickCount = mouseEvent.getClickCount();
    }

    @Override
    public MouseEvent getRawEvent() {
        return mouseEvent;
    }

    public int getMouseButton() {
        return button;
    }

    public int getClickCount() {
        return clickCount;
    }

    public static MouseButtonEvent fromMouseEvent(MouseEvent mouseEvent) {
        return new MouseButtonEvent(mouseEvent);
    }
}
