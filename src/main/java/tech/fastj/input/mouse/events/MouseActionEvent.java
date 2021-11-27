package tech.fastj.input.mouse.events;

import tech.fastj.input.InputActionEvent;

import java.awt.event.MouseEvent;

public interface MouseActionEvent extends InputActionEvent {
    @Override
    MouseEvent getRawEvent();
}
