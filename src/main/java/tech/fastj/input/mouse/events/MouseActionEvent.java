package tech.fastj.input.mouse.events;

import tech.fastj.input.InputActionEvent;
import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;

public abstract class MouseActionEvent extends InputActionEvent {
    @Override
    public abstract MouseEvent getRawEvent();

    public abstract MouseAction getEventType();
}
