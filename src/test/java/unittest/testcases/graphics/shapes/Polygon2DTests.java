package unittest.testcases.graphics.shapes;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.GameObject;
import io.github.lucasstarsz.fastj.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Polygon2DTests {

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D polygon2D = new Polygon2D(square);

        assertEquals(Polygon2D.defaultColor, polygon2D.getColor(), "The created polygon's color should match the default color.");
        assertEquals(Polygon2D.defaultFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the default fill option.");
        assertEquals(Polygon2D.defaultShow, polygon2D.shouldRender(), "The created polygon's 'show' option should match the default show option.");
        assertEquals(GameObject.originTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(GameObject.originRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(GameObject.originScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomAtEdge(0d, 1d) != 0d;
        boolean shouldRender = Maths.randomAtEdge(0d, 1d) != 0d;

        Polygon2D polygon2D = new Polygon2D(square, randomColor, shouldFill, shouldRender);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(GameObject.originTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(GameObject.originRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(GameObject.originScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams_andRandomlyGeneratedTransformParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomAtEdge(0d, 1d) != 0d;
        boolean shouldRender = Maths.randomAtEdge(0d, 1d) != 0d;

        Pointf randomTranslation = new Pointf((float) Maths.random(-50f, 50f), (float) Maths.random(-50f, 50f));
        float randomRotation = (float) Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf((float) Maths.random(-50f, 50f), (float) Maths.random(-50f, 50f));

        Polygon2D polygon2D = new Polygon2D(square, randomTranslation, randomRotation, randomScale, randomColor, shouldFill, shouldRender);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(randomTranslation, polygon2D.getTranslation(), "The created polygon's translation should match the randomly generated translation.");
        assertEquals(randomRotation, polygon2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
        assertEquals(randomScale, polygon2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams_andRandomlyGeneratedTransformParams_usingMethodChaining() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomAtEdge(0d, 1d) != 0d;
        boolean shouldRender = Maths.randomAtEdge(0d, 1d) != 0d;

        Pointf randomTranslation = new Pointf((float) Maths.random(-50f, 50f), (float) Maths.random(-50f, 50f));
        float randomRotation = (float) Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf((float) Maths.random(-50f, 50f), (float) Maths.random(-50f, 50f));

        Polygon2D polygon2D = (Polygon2D) new Polygon2D(square)
                .setColor(randomColor)
                .setFilled(shouldFill)
                .setTranslation(randomTranslation)
                .setRotation(randomRotation)
                .setScale(randomScale)
                .setShouldRender(shouldRender);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(randomTranslation, polygon2D.getTranslation(), "The created polygon's translation should match the randomly generated translation.");
        assertEquals(randomRotation, polygon2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
        assertEquals(randomScale, polygon2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }
}
