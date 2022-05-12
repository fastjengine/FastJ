package tech.fastj.input.keyboard.events;

import tech.fastj.input.InputActionEvent;

import java.awt.event.KeyEvent;

public abstract class KeyboardActionEvent extends InputActionEvent {

    @Override
    public abstract KeyEvent getRawEvent();

    public abstract String getKeyName();
}
