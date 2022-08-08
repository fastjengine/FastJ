package tech.fastj.input;

import tech.fastj.gameloop.event.Event;

import java.awt.event.InputEvent;

public abstract class InputActionEvent extends Event {
    public abstract InputEvent getRawEvent();
}
