package unittest.testcases.graphics.game;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.Text2D;

import java.awt.Color;
import java.awt.Font;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.HeadlessHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static unittest.HeadlessHelper.runFastJWith;

class Text2DTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(HeadlessHelper.isEnvironmentHeadless);
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andPointfTranslationParam() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

            Text2D text2D = new Text2D(text, randomTranslation);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(Text2D.DefaultColor, text2D.getColor(), "The actual color should match the expected color.");
            assertEquals(Text2D.DefaultFont, text2D.getFont(), "The actual font should match the default font.");
            assertEquals(Text2D.DefaultShow, text2D.shouldRender(), "The actual show variable should match the default show variable.");
        });
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andPointfTranslationParam_andColorParam_andFontParam_andShowParam() {
        runFastJWith(() -> {
            String text = "Hello, world!";

            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            Color randomColor = DrawUtil.randomColorWithAlpha();
            Font randomFont = DrawUtil.randomFont();
            boolean shouldRender = Maths.randomBoolean();

            Text2D text2D = new Text2D(text, randomTranslation, randomColor, randomFont, shouldRender);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(randomColor, text2D.getColor(), "The actual color should match the expected random color.");
            assertEquals(randomFont, text2D.getFont(), "The actual font should match the expected random font.");
            assertEquals(shouldRender, text2D.shouldRender(), "The actual show variable should match the expected random show variable.");
        });
    }

    @Test
    void checkText2DConstructor_withStringTextParam_andPointfTranslationParam_andColorParam_andFontParam_andShowParam_usingMethodChaining() {
        runFastJWith(() -> {
            String text = "Hello, world!";

            Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
            Color randomColor = DrawUtil.randomColorWithAlpha();
            Font randomFont = DrawUtil.randomFont();
            boolean shouldRender = Maths.randomBoolean();

            Text2D text2D = (Text2D) new Text2D(text, randomTranslation)
                    .setColor(randomColor)
                    .setFont(randomFont)
                    .setShouldRender(shouldRender);

            assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
            assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
            assertEquals(randomColor, text2D.getColor(), "The actual color should match the expected random color.");
            assertEquals(randomFont, text2D.getFont(), "The actual font should match the expected random font.");
            assertEquals(shouldRender, text2D.shouldRender(), "The actual show variable should match the expected random show variable.");
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
    void checkRotateText2D_shouldThrowException() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            Pointf originalTranslation = Pointf.Origin.copy();
            float randomRotation = Maths.random(-50f, 50f);

            Text2D text2D = new Text2D(text, originalTranslation);
            Throwable exception = assertThrows(IllegalStateException.class, () -> text2D.rotate(randomRotation, Pointf.Origin));

            String expectedErrorMessage = "ERROR: The game crashed, due to the call of a method not yet implemented.";
            String actualErrorMessage = exception.getMessage();
            assertEquals(expectedErrorMessage, actualErrorMessage);
        });
    }

    @Test
    void checkScaleText2D_shouldThrowException() {
        runFastJWith(() -> {
            String text = "Hello, world!";
            Pointf originalTranslation = Pointf.Origin.copy();
            Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

            Text2D text2D = new Text2D(text, originalTranslation);
            Throwable exception = assertThrows(IllegalStateException.class, () -> text2D.scale(randomScale, originalTranslation));

            String expectedErrorMessage = "ERROR: The game crashed, due to the call of a method not yet implemented.";
            String actualErrorMessage = exception.getMessage();
            assertEquals(expectedErrorMessage, actualErrorMessage);
        });
    }
}
