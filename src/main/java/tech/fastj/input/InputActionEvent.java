package tech.fastj.input;

import tech.fastj.gameloop.event.GameEvent;

import java.awt.event.InputEvent;

public abstract class InputActionEvent extends GameEvent {
    public abstract InputEvent getRawEvent();
}
