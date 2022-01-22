package unittest.testcases.graphics.ui.elements;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;

import tech.fastj.graphics.ui.elements.Button;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.SimpleManager;

import unittest.mock.systems.control.MockEmptySimpleManager;
import unittest.mock.systems.control.MockNameSettingScene;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ButtonTests {

    @Test
    void checkButtonConstructor_withSimpleManagerParameter() {
        SimpleManager simpleManager = new MockEmptySimpleManager();
        Pointf[] defaultInitialSize = DrawUtil.createBox(Transform2D.DefaultTranslation, Button.DefaultSize);
        Button button = new Button(simpleManager);

        assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
        assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
        assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
        assertEquals(Transform2D.DefaultTranslation, button.getTranslation(), "The created button translation should match an origin translation.");
        assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
        assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
        assertArrayEquals(defaultInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the default initial size.");
    }

    @Test
    void checkButtonConstructor_withSceneParameter() {
        MockNameSettingScene scene = new MockNameSettingScene("button testing");

        Pointf[] defaultInitialSize = DrawUtil.createBox(Transform2D.DefaultTranslation, Button.DefaultSize);
        Button button = new Button(scene);

        assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
        assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
        assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
        assertEquals(Transform2D.DefaultTranslation, DrawUtil.pointsOfPath(button.getCollisionPath())[0], "The created button's location should match an origin translation.");
        assertEquals(Transform2D.DefaultTranslation, button.getTranslation(), "The created button translation should match an origin translation.");
        assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
        assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
        assertArrayEquals(defaultInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the default initial size.");
    }

    @Test
    void checkButtonConstructor_withSimpleManagerParameter_andLocation_andInitialSize() {
        SimpleManager simpleManager = new MockEmptySimpleManager();
        Pointf randomLocation = new Pointf(Maths.random(-1000f, 1000f), Maths.random(-1000f, 1000f));
        Pointf randomInitialSize = new Pointf(Maths.random(Maths.FloatPrecision, 1000f), Maths.random(Maths.FloatPrecision, 1000f));
        Pointf[] generatedInitialSize = DrawUtil.createBox(randomLocation, randomInitialSize);

        Button button = new Button(simpleManager, randomLocation, randomInitialSize);

        assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
        assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
        assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
        assertEquals(randomLocation, DrawUtil.pointsOfPath(button.getCollisionPath())[0], "The created button's location should match the generated location.");
        assertEquals(randomLocation, button.getTranslation(), "The created button translation should match the location translation.");
        assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
        assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
        assertArrayEquals(generatedInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the generated initial size.");
    }

    @Test
    void checkButtonConstructor_withSceneParameter_andLocation_andInitialSize() {
        MockNameSettingScene scene = new MockNameSettingScene("button testing");
        Pointf randomLocation = new Pointf(Maths.random(-1000f, 1000f), Maths.random(-1000f, 1000f));
        Pointf randomInitialSize = new Pointf(Maths.random(Maths.FloatPrecision, 1000f), Maths.random(Maths.FloatPrecision, 1000f));
        Pointf[] generatedInitialSize = DrawUtil.createBox(randomLocation, randomInitialSize);

        Button button = new Button(scene, randomLocation, randomInitialSize);

        assertEquals(Button.DefaultText, button.getText(), "The actual text should match the expected default text.");
        assertEquals(Button.DefaultFill, button.getFill(), "The actual fill paint should match the expected default fill paint.");
        assertEquals(Button.DefaultFont, button.getFont(), "The actual font should match the default font.");
        assertEquals(randomLocation, DrawUtil.pointsOfPath(button.getCollisionPath())[0], "The created button's location should match the generated location.");
        assertEquals(randomLocation, button.getTranslation(), "The created button translation should match the location translation.");
        assertEquals(Transform2D.DefaultRotation, button.getRotation(), "The created button's rotation should match an origin rotation.");
        assertEquals(Transform2D.DefaultScale, button.getScale(), "The created button's scaling should match an origin scale.");
        assertArrayEquals(generatedInitialSize, DrawUtil.pointsOfPath(button.getCollisionPath()), "The created button's actual initial size should match the generated initial size.");
    }

    @Test
    void tryButtonConstructor_withSceneParameter_withInvalidSize() {
        MockNameSettingScene scene = new MockNameSettingScene("invalid button testing");
        Pointf invalid_randomInitialSize = new Pointf(Maths.random(-1000f, -Maths.FloatPrecision), Maths.random(-1000f, -Maths.FloatPrecision));
        assertThrows(IllegalArgumentException.class, () -> new Button(scene, Pointf.origin(), invalid_randomInitialSize), "It should not be possible to construct a button with a negative size.");
    }

    @Test
    void tryButtonConstructor_withSimpleManagerParameter_withInvalidSize() {
        SimpleManager simpleManager = new MockEmptySimpleManager();
        Pointf invalid_randomInitialSize = new Pointf(Maths.random(-1000f, -Maths.FloatPrecision), Maths.random(-1000f, -Maths.FloatPrecision));
        assertThrows(IllegalArgumentException.class, () -> new Button(simpleManager, Pointf.origin(), invalid_randomInitialSize), "It should not be possible to construct a button with a negative size.");
    }
}
