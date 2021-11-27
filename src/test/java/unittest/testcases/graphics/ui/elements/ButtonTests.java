package unittest.testcases.graphics.ui.elements;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import tech.fastj.engine.FastJEngine;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;

import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.ui.elements.Button;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.SceneManager;
import tech.fastj.systems.control.SimpleManager;

import unittest.EnvironmentHelper;
import unittest.mock.systems.control.MockNameSettingScene;
import unittest.mock.systems.control.MockSceneManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ButtonTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
    }

    @Test
    void checkButtonConstructor_withSimpleManagerParameter() {
        SimpleManager simpleManager = new SimpleManager() {
            @Override
            public void init(FastJCanvas canvas) {
                Pointf[] defaultInitialSize = DrawUtil.createBox(Transform2D.DefaultTranslation, Button.DefaultSize);
                Button button = new Button(this);

                assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
                assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
                assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
                assertEquals(Transform2D.DefaultTranslation, button.getTranslation(), "The created button translation should match an origin translation.");
                assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
                assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
                assertArrayEquals(defaultInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the default initial size.");

                FastJEngine.forceCloseGame();
            }

            @Override
            public void update(FastJCanvas canvas) {
            }
        };

        FastJEngine.init("test button constructor with scene parameter", simpleManager);
        try {
            FastJEngine.run();
        } catch (NullPointerException ignored) {
            // Exception caught to prevent game window opening
        }
    }

    @Test
    void checkButtonConstructor_withSceneParameter() {
        MockNameSettingScene scene = new MockNameSettingScene("button testing") {
            @Override
            public void load(FastJCanvas canvas) {
                Pointf[] defaultInitialSize = DrawUtil.createBox(Transform2D.DefaultTranslation, Button.DefaultSize);
                Button button = new Button(this);

                assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
                assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
                assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
                assertEquals(Transform2D.DefaultTranslation, DrawUtil.pointsOfPath(button.getCollisionPath())[0], "The created button's location should match an origin translation.");
                assertEquals(Transform2D.DefaultTranslation, button.getTranslation(), "The created button translation should match an origin translation.");
                assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
                assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
                assertArrayEquals(defaultInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the default initial size.");

                FastJEngine.forceCloseGame();
            }
        };

        SceneManager mockSceneManager = new MockSceneManager() {
            @Override
            public void init(FastJCanvas canvas) {
                addScene(scene);
                setCurrentScene(scene);
                loadCurrentScene();
            }
        };

        FastJEngine.init("test button constructor with scene parameter", mockSceneManager);
        try {
            FastJEngine.run();
        } catch (NullPointerException ignored) {
            // Exception caught to prevent game window opening
        }
    }

    @Test
    void checkButtonConstructor_withSimpleManagerParameter_andLocation_andInitialSize() {
        SimpleManager simpleManager = new SimpleManager() {
            @Override
            public void init(FastJCanvas canvas) {
                Pointf randomLocation = new Pointf(Maths.random(-1000f, 1000f), Maths.random(-1000f, 1000f));
                Pointf randomInitialSize = new Pointf(Maths.random(-1000f, 1000f), Maths.random(-1000f, 1000f));
                Pointf[] generatedInitialSize = DrawUtil.createBox(randomLocation, randomInitialSize);

                Button button = new Button(this, randomLocation, randomInitialSize);

                assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
                assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
                assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
                assertEquals(randomLocation, DrawUtil.pointsOfPath(button.getCollisionPath())[0], "The created button's location should match the generated location.");
                assertEquals(randomLocation, button.getTranslation(), "The created button translation should match the location translation.");
                assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
                assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
                assertArrayEquals(generatedInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the generated initial size.");


                FastJEngine.forceCloseGame();
            }

            @Override
            public void update(FastJCanvas canvas) {
            }
        };

        FastJEngine.init("test button constructor with scene parameter", simpleManager);
        try {
            FastJEngine.run();
        } catch (NullPointerException ignored) {
            // Exception caught to prevent game window opening
        }
    }

    @Test
    void checkButtonConstructor_withSceneParameter_andLocation_andInitialSize() {
        MockNameSettingScene scene = new MockNameSettingScene("button testing") {
            @Override
            public void load(FastJCanvas canvas) {
                Pointf randomLocation = new Pointf(Maths.random(-1000f, 1000f), Maths.random(-1000f, 1000f));
                Pointf randomInitialSize = new Pointf(Maths.random(-1000f, 1000f), Maths.random(-1000f, 1000f));
                Pointf[] generatedInitialSize = DrawUtil.createBox(randomLocation, randomInitialSize);

                Button button = new Button(this, randomLocation, randomInitialSize);

                assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
                assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
                assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
                assertEquals(randomLocation, DrawUtil.pointsOfPath(button.getCollisionPath())[0], "The created button's location should match the generated location.");
                assertEquals(randomLocation, button.getTranslation(), "The created button translation should match the location translation.");
                assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
                assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
                assertArrayEquals(generatedInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the generated initial size.");

                FastJEngine.forceCloseGame();
            }
        };

        SceneManager mockSceneManager = new MockSceneManager() {
            @Override
            public void init(FastJCanvas canvas) {
                addScene(scene);
                setCurrentScene(scene);
                loadCurrentScene();
            }
        };

        FastJEngine.init("test button constructor with scene parameter", mockSceneManager);
        try {
            FastJEngine.run();
        } catch (NullPointerException ignored) {
            // Exception caught to prevent game window opening
        }
    }
}
