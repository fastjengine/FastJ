package tech.fastj.input.mouse.events;

import tech.fastj.input.InputActionEvent;
import tech.fastj.input.mouse.MouseAction;

import java.awt.event.MouseEvent;

public interface MouseActionEvent extends InputActionEvent {
    @Override
    MouseEvent getRawEvent();

    MouseAction getEventType();
}
