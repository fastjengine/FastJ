package unittest.testcases.input.keyboard;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.math.Point;
import tech.fastj.graphics.display.FastJCanvas;

import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.Keys;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.input.keyboard.events.KeyboardTypedEvent;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import unittest.EnvironmentHelper;
import unittest.mock.systems.control.MockConfigurableScene;
import unittest.mock.systems.control.MockSceneManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KeyboardActionEventTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
    }

    @Test
    @Order(0)
    void checkKeyboardActionListener_KeyboardStateEvents() throws AWTException {
        AtomicBoolean didKeyPress = new AtomicBoolean();
        AtomicBoolean didKeyRelease = new AtomicBoolean();
        AtomicBoolean wasKeySPressed = new AtomicBoolean();
        AtomicBoolean wasKeySReleased = new AtomicBoolean();
        AtomicBoolean hasUpdated = new AtomicBoolean();
        Keys expectedKey = Keys.S;
        Robot robot = new Robot();

        MockConfigurableScene mockConfigurableScene = new MockConfigurableScene(
                (canvas, scene) -> {
                    // wait for canvas to show up on screen
                    while (!canvas.getRawCanvas().isShowing()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // initial focus + robot mouse positioning
                    if (!canvas.getRawCanvas().hasFocus()) {
                        canvas.getRawCanvas().requestFocusInWindow();
                    }
                    Point canvasLocationOnScreen = new Point(canvas.getRawCanvas().getLocationOnScreen());
                    robot.mouseMove(canvasLocationOnScreen.x, canvasLocationOnScreen.y);
                    Log.info(KeyboardActionEventTests.class, "Mouse pointer located at {}", MouseInfo.getPointerInfo().getLocation());

                    scene.inputManager.addKeyboardActionListener(new KeyboardActionListener() {
                        @Override
                        public void onKeyRecentlyPressed(KeyboardStateEvent keyboardStateEvent) {
                            Log.info(KeyboardActionEventTests.class, "press {}", keyboardStateEvent);
                            didKeyPress.set(true);
                            wasKeySPressed.set(keyboardStateEvent.getKey() == expectedKey);
                        }

                        @Override
                        public void onKeyReleased(KeyboardStateEvent keyboardStateEvent) {
                            Log.info(KeyboardActionEventTests.class, "release {}", keyboardStateEvent);
                            didKeyRelease.set(true);
                            wasKeySReleased.set(keyboardStateEvent.getKey() == expectedKey);
                        }
                    });
                },
                (canvas, scene) -> {
                    if (hasUpdated.get()) {
                        return;
                    }
                    hasUpdated.set(true);

                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.keyPress(Keys.S.getKeyCode());
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    robot.keyRelease(Keys.S.getKeyCode());
                }
        );

        MockSceneManager mockSceneManager = new MockSceneManager(mockConfigurableScene) {
            @Override
            public void render(FastJCanvas display) {
                if (!hasUpdated.get() || !didKeyPress.get() || !didKeyRelease.get()) {
                    return;
                }

                FastJEngine.closeGame();
            }
        };
        FastJEngine.init("Robot mouse button", mockSceneManager);
        FastJEngine.run();

        assertTrue(wasKeySPressed.get(), "The S key should have been pressed.");
        assertTrue(wasKeySReleased.get(), "The S key should have been released.");
    }

    @Test
    @Order(1)
    void checkKeyboardActionListener_KeyboardTypedEvents() throws AWTException {
        AtomicBoolean didKeyType = new AtomicBoolean();
        AtomicReference<String> keyTyped = new AtomicReference<>();
        AtomicBoolean hasUpdated = new AtomicBoolean();
        Keys expectedKey = Keys.S;
        Robot robot = new Robot();

        MockConfigurableScene mockConfigurableScene = new MockConfigurableScene(
                (canvas, scene) -> {
                    // wait for canvas to show up on screen
                    while (!canvas.getRawCanvas().isShowing()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // initial focus + robot mouse positioning
                    if (!canvas.getRawCanvas().hasFocus()) {
                        canvas.getRawCanvas().requestFocusInWindow();
                    }
                    Point canvasLocationOnScreen = new Point(canvas.getRawCanvas().getLocationOnScreen());
                    robot.mouseMove(canvasLocationOnScreen.x, canvasLocationOnScreen.y);
                    Log.info(KeyboardActionEventTests.class, "Mouse pointer located at {}", MouseInfo.getPointerInfo().getLocation());

                    scene.inputManager.addKeyboardActionListener(new KeyboardActionListener() {
                        @Override
                        public void onKeyTyped(KeyboardTypedEvent keyboardTypedEvent) {
                            Log.info(KeyboardActionEventTests.class, "typed {}", keyboardTypedEvent);
                            didKeyType.set(true);
                            keyTyped.set(keyboardTypedEvent.getKeyName());
                        }
                    });
                },
                (canvas, scene) -> {
                    if (hasUpdated.get()) {
                        return;
                    }
                    hasUpdated.set(true);

                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.keyPress(Keys.S.getKeyCode());
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    robot.keyRelease(Keys.S.getKeyCode());
                }
        );

        MockSceneManager mockSceneManager = new MockSceneManager(mockConfigurableScene) {
            @Override
            public void render(FastJCanvas display) {
                if (!hasUpdated.get() || !didKeyType.get()) {
                    return;
                }

                FastJEngine.closeGame();
            }
        };
        FastJEngine.init("Robot mouse button", mockSceneManager);
        FastJEngine.run();

        assertEquals(expectedKey.name().toLowerCase(), keyTyped.get(), "\"s\" should have been typed.");
    }
}
