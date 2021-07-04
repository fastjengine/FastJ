package unittest.testcases.graphics.game;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.util.DrawUtil;

import java.awt.Color;
import java.awt.Font;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static unittest.EnvironmentHelper.runFastJWith;

class Text2DTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
    }

    @Test
    void checkText2DConstructor_withStringTextParam() {
        runFastJWith(() -> {
            String text = "Hello, world!";

            Text2D text2D = new Text2D(text);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(Text2D.DefaultPaint, text2D.getFill(), "The actual color should match the expected color.");
            assertEquals(Text2D.DefaultFont, text2D.getFont(), "The actual font should match the default font.");
            assertEquals(Drawable.DefaultShouldRender, text2D.shouldRender(), "The actual show variable should match the default show variable.");
        });
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andPointfTranslationParam() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

            Text2D text2D = new Text2D(text, randomTranslation);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(Text2D.DefaultPaint, text2D.getFill(), "The actual color should match the expected color.");
            assertEquals(Text2D.DefaultFont, text2D.getFont(), "The actual font should match the default font.");
            assertEquals(Drawable.DefaultShouldRender, text2D.shouldRender(), "The actual show variable should match the default show variable.");
        });
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andRandomlyGeneratedTranslationParam_andRandomlyGeneratedColorFontShowParams() {
        runFastJWith(() -> {
            String text = "Hello, world!";

            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            Color randomColor = DrawUtil.randomColorWithAlpha();
            Font randomFont = DrawUtil.randomFont();
            boolean randomShouldRender = Maths.randomBoolean();

            Text2D text2D = new Text2D(text, randomTranslation, randomColor, randomFont, randomShouldRender);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(randomColor, text2D.getFill(), "The actual color should match the expected random color.");
            assertEquals(randomFont, text2D.getFont(), "The actual font should match the expected random font.");
            assertEquals(randomShouldRender, text2D.shouldRender(), "The actual show variable should match the expected random show variable.");
        });
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andRandomlyGeneratedTransformationParams_andRandomlyGeneratedColorFontShowParams() {
        runFastJWith(() -> {
            String text = "Hello, world!";

            Color randomColor = DrawUtil.randomColorWithAlpha();
            Font randomFont = DrawUtil.randomFont();
            boolean randomShouldRender = Maths.randomBoolean();

            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            float randomRotation = Maths.random(-5000f, 5000f);
            float expectedNormalizedRotation = randomRotation % 360;

            Text2D text2D = new Text2D(text, randomTranslation, randomRotation, randomScale, randomColor, randomFont, randomShouldRender);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(randomRotation, text2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
            assertEquals(expectedNormalizedRotation, text2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
            assertEquals(randomScale, text2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
            assertEquals(randomColor, text2D.getFill(), "The actual color should match the expected random color.");
            assertEquals(randomFont, text2D.getFont(), "The actual font should match the expected random font.");
            assertEquals(randomShouldRender, text2D.shouldRender(), "The actual show variable should match the expected random show variable.");
        });
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andRandomlyGeneratedTransformationParams_andRandomlyGeneratedColorFontShowParams_usingMethodChaining() {
        runFastJWith(() -> {
            String text = "Hello, world!";

            Color randomColor = DrawUtil.randomColorWithAlpha();
            Font randomFont = DrawUtil.randomFont();
            boolean randomShouldRender = Maths.randomBoolean();

            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            float randomRotation = Maths.random(-5000f, 5000f);
            float expectedNormalizedRotation = randomRotation % 360;

            Text2D text2D = (Text2D) new Text2D(text)
                    .setFill(randomColor)
                    .setFont(randomFont)
                    .setTranslation(randomTranslation)
                    .setRotation(randomRotation)
                    .setScale(randomScale)
                    .setShouldRender(randomShouldRender);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(randomRotation, text2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
            assertEquals(expectedNormalizedRotation, text2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
            assertEquals(randomScale, text2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
            assertEquals(randomColor, text2D.getFill(), "The actual color should match the expected random color.");
            assertEquals(randomFont, text2D.getFont(), "The actual font should match the expected random font.");
            assertEquals(randomShouldRender, text2D.shouldRender(), "The actual show variable should match the expected random show variable.");
        });
    }

    @Test
    void checkTranslateText2D_shouldMatchExpected() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            Pointf originalTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

            Text2D text2D = new Text2D(text, originalTranslation);
            text2D.translate(randomTranslation);

            Pointf expectedTranslation = Pointf.add(originalTranslation, randomTranslation);
            Pointf actualTranslation = text2D.getTranslation();

            assertEquals(expectedTranslation, actualTranslation, "The actual translation should match the expected translation.");
        });
    }

    @Test
    void checkRotateText2D_shouldMatchExpected() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            float randomRotation = Maths.random(-50f, 50f);

            Text2D text2D = new Text2D(text);

            assertDoesNotThrow(() -> text2D.rotate(randomRotation, Pointf.Origin), "Rotating Text2D objects is implemented, and should not throw an exception.");
            assertEquals(randomRotation, text2D.getRotation(), "The actual rotation should match the expected rotation.");
        });
    }

    @Test
    void checkScaleText2D_shouldMatchExpected() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

            Text2D text2D = new Text2D(text);

            assertDoesNotThrow(() -> text2D.scale(randomScale), "Scaling Text2D objects is implemented, and should not throw an exception.");
            assertEquals(Pointf.add(randomScale, 1f), text2D.getScale(), "The actual scale should match the expected scale.");
        });
    }
}
