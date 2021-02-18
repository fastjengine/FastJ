package io.github.lucasstarsz.fastj.framework.io.mouse;

public enum MouseButtons {
    LEFT(1),
    RIGHT(3),
    MIDDLE(2);

    final int buttonValue;

    MouseButtons(int buttonValue) {
        this.buttonValue = buttonValue;
    }
}
