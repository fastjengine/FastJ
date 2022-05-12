package tech.fastj.input;

import tech.fastj.gameloop.event.GameEvent;

import java.awt.event.InputEvent;

public interface InputActionEvent extends GameEvent {
    InputEvent getRawEvent();
}
