package unittest.testcases.graphics.text;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.gameobject.text.Text2D;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.systems.control.Scene;
import io.github.lucasstarsz.fastj.systems.render.Display;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unittest.mock.MockManager;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class Text2DTests {

    @BeforeAll
    public static void onlyRunIfNotHeadless() {
        // because idk how to run github actions in non-headless mode
        assumeTrue(
                !GraphicsEnvironment.isHeadless(),
                "Well that's unfortunate... this device isn't running in headless mode, so Text2D tests cannot be conducted."
        );
    }

    @Test
    public void checkText2DConstructor_withStringTextParam_andPointfTranslationParam() {
        FastJEngine.init("For those sweet, sweet testing purposes", new MockManager(new Scene("") {
            @Override
            public void load(Display display) {
                String text = "Hello, world!";
                Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

                Text2D text2D = new Text2D(text, randomTranslation);

                assertEquals(text, text2D.getText(), "The actual text should match the expected text.");
                assertEquals(randomTranslation, text2D.getTranslation(), "The actual translation should match the expected translation.");
                assertEquals(Text2D.DefaultColor, text2D.getColor(), "The actual color should match the expected color.");
                assertEquals(Text2D.DefaultFont, text2D.getFont(), "The actual font should match the default font.");
                assertEquals(Text2D.DefaultShow, text2D.shouldRender(), "The actual show variable should match the default show variable.");
            }

            @Override
            public void unload(Display display) {

            }

            @Override
            public void update(Display display) {
                FastJEngine.closeGame();
            }
        }));

        FastJEngine.run();
    }

    @Test
    public void checkText2DConstructor_withStringTextParam_andPointfTranslationParam_andColorParam_andFontParam_andShowParam() {
        FastJEngine.init("For those sweet, sweet testing purposes", new MockManager(new Scene("") {
            @Override
            public void load(Display display) {
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
            }

            @Override
            public void unload(Display display) {
            }

            @Override
            public void update(Display display) {
                FastJEngine.closeGame();
            }
        }));

        FastJEngine.run();
    }

    @Test
    public void checkText2DConstructor_withStringTextParam_andPointfTranslationParam_andColorParam_andFontParam_andShowParam_usingMethodChaining() {
        FastJEngine.init("For those sweet, sweet testing purposes", new MockManager(new Scene("") {
            @Override
            public void load(Display display) {
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
            }

            @Override
            public void unload(Display display) {
            }

            @Override
            public void update(Display display) {
                FastJEngine.closeGame();
            }
        }));

        FastJEngine.run();
    }
}
