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
        assertEquals(GameObject.defaultTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(GameObject.defaultRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(GameObject.defaultScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomAtEdge(0f, 1f) != 0f;
        boolean shouldRender = Maths.randomAtEdge(0f, 1f) != 0f;

        Polygon2D polygon2D = new Polygon2D(square, randomColor, shouldFill, shouldRender);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(GameObject.defaultTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(GameObject.defaultRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(GameObject.defaultScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams_andRandomlyGeneratedTransformParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomAtEdge(0f, 1f) != 0f;
        boolean shouldRender = Maths.randomAtEdge(0f, 1f) != 0f;

        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

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
        boolean shouldFill = Maths.randomAtEdge(0f, 1f) != 0f;
        boolean shouldRender = Maths.randomAtEdge(0f, 1f) != 0f;

        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

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

    @Test
    public void checkModifyPointsOfPolygon2D_withTransformResetting() {
        Pointf[] squarePoints = DrawUtil.createBox(Pointf.origin, 5f);
        Pointf translationBeforeReset = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );
        float rotationBeforeReset = Maths.random(0f, 1f);
        Pointf scaleBeforeReset = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );

        Polygon2D square = (Polygon2D) new Polygon2D(squarePoints)
                .setTranslation(translationBeforeReset)
                .setRotation(rotationBeforeReset)
                .setScale(scaleBeforeReset);

        Pointf[] newSquarePoints = DrawUtil.createBox(Pointf.origin.copy().add(1f), 20f);
        square.modifyPoints(newSquarePoints, true, true, true);

        assertArrayEquals(newSquarePoints, square.getPoints(), "The expected points should match the square's points -- no transformations have been performed.");
        assertArrayEquals(newSquarePoints, square.getOriginalPoints(), "The expected points should match the square's points.");
        assertEquals(GameObject.defaultTranslation, square.getTranslation(), "The square's translation should match the default GameObject translation.");
        assertEquals(GameObject.defaultRotation, square.getRotation(), "The square's rotation should match the default GameObject rotation.");
        assertEquals(GameObject.defaultScale, square.getScale(), "The square's scale should match the default GameObject scale.");
    }

    @Test
    public void checkModifyPointsOfPolygon2D_withoutTransformResetting() {
        Pointf[] squarePoints = DrawUtil.createBox(Pointf.origin, 5f);
        Pointf translationBeforeReset = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );
        float rotationBeforeReset = Maths.random(0f, 1f);
        Pointf scaleBeforeReset = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );

        Polygon2D square = (Polygon2D) new Polygon2D(squarePoints)
                .setTranslation(translationBeforeReset)
                .setRotation(rotationBeforeReset)
                .setScale(scaleBeforeReset);

        Pointf[] newSquarePoints = DrawUtil.createBox(Pointf.origin.copy().add(1f), 20f);
        square.modifyPoints(newSquarePoints, false, false, false);

        assertArrayEquals(newSquarePoints, square.getPoints(), "The expected points should match the square's points -- no transformations have been performed.");
        assertArrayEquals(newSquarePoints, square.getOriginalPoints(), "The expected points should match the square's points.");
        assertEquals(translationBeforeReset, square.getTranslation(), "The square's translation should match the translation before point modification.");
        assertEquals(rotationBeforeReset, square.getRotation(), "The square's rotation should match the rotation before point modification.");
        assertEquals(scaleBeforeReset, square.getScale(), "The square's scale should match the scale before point modification.");
    }

    @Test
    public void checkPolygon2DTranslation_shouldMatchExpected() {
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.origin, 5f);
        Pointf randomTranslation = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );
        Pointf[] expectedTranslatedPoints = {
                originalPoints[0].copy().add(randomTranslation),
                originalPoints[1].copy().add(randomTranslation),
                originalPoints[2].copy().add(randomTranslation),
                originalPoints[3].copy().add(randomTranslation)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.translate(randomTranslation);
        Pointf[] actualTranslatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedTranslatedPoints, actualTranslatedPoints, "The actual Pointf array, which has been translated, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DRotation_shouldMatchExpected() {
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.origin, 5f);
        float randomRotationInDegrees = Maths.random(0f, 1f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);

        Pointf[] expectedRotatedPoints = {
                new Pointf(
                        originalPoints[0].x * cosOfRotation - originalPoints[0].y * sinOfRotation,
                        originalPoints[0].y * cosOfRotation + originalPoints[0].x * sinOfRotation
                ),
                new Pointf(
                        originalPoints[1].x * cosOfRotation - originalPoints[1].y * sinOfRotation,
                        originalPoints[1].y * cosOfRotation + originalPoints[1].x * sinOfRotation
                ),
                new Pointf(
                        originalPoints[2].x * cosOfRotation - originalPoints[2].y * sinOfRotation,
                        originalPoints[2].y * cosOfRotation + originalPoints[2].x * sinOfRotation
                ),
                new Pointf(
                        originalPoints[3].x * cosOfRotation - originalPoints[3].y * sinOfRotation,
                        originalPoints[3].y * cosOfRotation + originalPoints[3].x * sinOfRotation
                )
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.rotate(randomRotationInDegrees, Pointf.origin);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DScaling_atOrigin_shouldMatchExpected() {
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.origin, 5f);
        Pointf randomScaling = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        Pointf[] expectedScaledPoints = {
                originalPoints[0].copy().multiply(Pointf.add(randomScaling, polygon2D.getScale())),
                originalPoints[1].copy().multiply(Pointf.add(randomScaling, polygon2D.getScale())),
                originalPoints[2].copy().multiply(Pointf.add(randomScaling, polygon2D.getScale())),
                originalPoints[3].copy().multiply(Pointf.add(randomScaling, polygon2D.getScale()))
        };

        polygon2D.scale(randomScaling, Pointf.origin);
        Pointf[] actualScaledPoints = polygon2D.getPoints();

        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled, should match the expected Pointf array.");
    }
}
