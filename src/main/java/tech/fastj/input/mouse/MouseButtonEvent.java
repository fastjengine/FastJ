package tech.fastj.input.mouse;

import java.awt.event.MouseEvent;

public class MouseButtonEvent implements MouseActionEvent {

    private final MouseEvent mouseEvent;
    private final int button;
    private final int clickCount;

    public MouseButtonEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        this.button = mouseEvent.getButton();
        this.clickCount = mouseEvent.getClickCount();
    }

    @Override
    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public int getMouseButton() {
        return button;
    }

    public int getClickCount() {
        return clickCount;
    }
}
