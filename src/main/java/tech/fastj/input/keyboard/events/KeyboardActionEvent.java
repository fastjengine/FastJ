package tech.fastj.input.keyboard.events;

import tech.fastj.input.InputActionEvent;

import java.awt.event.KeyEvent;

public interface KeyboardActionEvent extends InputActionEvent {
    @Override
    KeyEvent getRawEvent();

    String getKeyName();
}
