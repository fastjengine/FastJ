package unittest.testcases.graphics.game;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.Transform2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Polygon2DTests {

    @Test
    void checkPolygon2DCreation_withPointfArrayParam() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D polygon2D = Polygon2D.fromPoints(square);

        assertEquals(Polygon2D.DefaultFill, polygon2D.getFill(), "The created polygon's paint should match the default paint.");
        assertEquals(Polygon2D.DefaultRenderStyle, polygon2D.getRenderStyle(), "The created polygon's render style option should match the default render style.");
        assertEquals(Drawable.DefaultShouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the default shouldRender option.");
        assertEquals(Polygon2D.DefaultOutlineStroke, polygon2D.getOutlineStroke(), "The created polygon's outline stroke option should match the default outline stroke.");
        assertEquals(Polygon2D.DefaultOutlineColor, polygon2D.getOutlineColor(), "The created polygon's outline color option should match the default outline color.");
        assertEquals(Transform2D.DefaultTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(Transform2D.DefaultRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(Transform2D.DefaultScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedRenderStyleFillOutlineParams_andShouldRenderParam() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        RenderStyle renderStyle = RenderStyle.values()[Maths.randomInteger(0, RenderStyle.values().length - 1)];
        boolean shouldRender = Maths.randomBoolean();

        Polygon2D polygon2D = Polygon2D.create(square, renderStyle, shouldRender)
                .withFill(randomColor)
                .build();

        assertEquals(randomColor, polygon2D.getFill(), "The created polygon's paint should match the randomly generated paint.");
        assertEquals(renderStyle, polygon2D.getRenderStyle(), "The created polygon's render style option should match the default render style.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated shouldRender option.");
        assertEquals(Polygon2D.DefaultOutlineStroke, polygon2D.getOutlineStroke(), "The created polygon's outline stroke option should match the default outline stroke.");
        assertEquals(Polygon2D.DefaultOutlineColor, polygon2D.getOutlineColor(), "The created polygon's outline color option should match the default outline color.");
        assertEquals(Transform2D.DefaultTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(Transform2D.DefaultRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(Transform2D.DefaultScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedRenderStyleFillOutlineParams_andShouldRenderParam_andRandomlyGeneratedTransformParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        RenderStyle renderStyle = RenderStyle.values()[Maths.randomInteger(0, RenderStyle.values().length - 1)];
        BasicStroke outlineStroke = DrawUtil.randomOutlineStroke();
        Color outlineColor = DrawUtil.randomColorWithAlpha();
        boolean shouldRender = Maths.randomBoolean();

        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);
        float expectedNormalizedRotation = randomRotation % 360;

        Polygon2D polygon2D = Polygon2D.create(square, renderStyle, shouldRender)
                .withTransform(randomTranslation, randomRotation, randomScale)
                .withOutline(outlineStroke, outlineColor)
                .withFill(randomColor)
                .build();

        assertEquals(randomColor, polygon2D.getFill(), "The created polygon's paint should match the randomly generated paint.");
        assertEquals(renderStyle, polygon2D.getRenderStyle(), "The created polygon's render style option should match the default render style.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated shouldRender option.");
        assertEquals(outlineStroke, polygon2D.getOutlineStroke(), "The created polygon's outline stroke option should match the randomly generated outline stroke.");
        assertEquals(outlineColor, polygon2D.getOutlineColor(), "The created polygon's outline color option should match the randomly generated outline color.");
        assertEquals(randomTranslation, polygon2D.getTranslation(), "The created polygon's translation should match the randomly generated translation.");
        assertEquals(randomRotation, polygon2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
        assertEquals(expectedNormalizedRotation, polygon2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
        assertEquals(randomScale, polygon2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedRenderStyleFillOutlineParams_andShouldRenderParam_andRandomlyGeneratedTransformParams_usingMethodChaining() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        RenderStyle renderStyle = RenderStyle.values()[Maths.randomInteger(0, RenderStyle.values().length - 1)];
        BasicStroke outlineStroke = DrawUtil.randomOutlineStroke();
        Color outlineColor = DrawUtil.randomColorWithAlpha();
        boolean shouldRender = Maths.randomBoolean();

        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);
        float expectedNormalizedRotation = randomRotation % 360;

        Polygon2D polygon2D = (Polygon2D) Polygon2D.fromPoints(square)
                .setOutline(outlineStroke, outlineColor)
                .setRenderStyle(renderStyle)
                .setFill(randomColor)
                .setShouldRender(shouldRender)
                .setTransform(randomTranslation, randomRotation, randomScale);

        assertEquals(randomColor, polygon2D.getFill(), "The created polygon's paint should match the randomly generated paint.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated shouldRender option.");
        assertEquals(renderStyle, polygon2D.getRenderStyle(), "The created polygon's render style option should match the default render style.");
        assertEquals(outlineStroke, polygon2D.getOutlineStroke(), "The created polygon's outline stroke option should match the randomly generated outline stroke.");
        assertEquals(outlineColor, polygon2D.getOutlineColor(), "The created polygon's outline color option should match the randomly generated outline color.");
        assertEquals(randomTranslation, polygon2D.getTranslation(), "The created polygon's translation should match the randomly generated translation.");
        assertEquals(randomRotation, polygon2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
        assertEquals(expectedNormalizedRotation, polygon2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
        assertEquals(randomScale, polygon2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    void checkModifyPointsOfPolygon2D_withTransformResetting() {
        Pointf[] squarePoints = DrawUtil.createBox(Pointf.Origin, 5f);
        Pointf translationBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));
        float rotationBeforeReset = Maths.random(0f, 100f);
        Pointf scaleBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));

        Polygon2D square = Polygon2D.create(squarePoints)
                .withTransform(translationBeforeReset, rotationBeforeReset, scaleBeforeReset)
                .build();

        Pointf[] newSquarePoints = DrawUtil.createBox(Pointf.Origin.copy().add(1f), 20f);
        square.modifyPoints(newSquarePoints, true, true, true);

        assertArrayEquals(newSquarePoints, square.getPoints(), "The expected points should match the square's points -- no transformations have been performed.");
        assertArrayEquals(newSquarePoints, square.getOriginalPoints(), "The expected points should match the square's points.");
        assertEquals(Transform2D.DefaultTranslation, square.getTranslation(), "The square's translation should match the default Transform2D.translation.");
        assertEquals(Transform2D.DefaultRotation, square.getRotation(), "The square's rotation should match the default Transform2D.rotation.");
        assertEquals(Transform2D.DefaultScale, square.getScale(), "The square's scale should match the default Transform2D.scale.");
    }

    @Test
    void checkModifyPointsOfPolygon2D_withoutTransformResetting() {
        Pointf[] squarePoints = DrawUtil.createBox(Pointf.Origin, 5f);
        Pointf translationBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));
        float rotationBeforeReset = Maths.random(0f, 100f);
        Pointf scaleBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));

        Polygon2D square = Polygon2D.create(squarePoints)
                .withTransform(translationBeforeReset, rotationBeforeReset, scaleBeforeReset)
                .build();

        Pointf[] newSquarePoints = DrawUtil.createBox(Pointf.Origin.copy().add(1f), 20f);
        square.modifyPoints(newSquarePoints, false, false, false);

        assertFalse(Arrays.deepEquals(newSquarePoints, square.getPoints()), "The expected points should not match the square's transformed points -- no transformations have been reset.");
        assertArrayEquals(newSquarePoints, square.getOriginalPoints(), "The expected points should match the square's points.");
        assertEquals(translationBeforeReset, square.getTranslation(), "The square's translation should match the translation before point modification.");
        assertEquals(rotationBeforeReset, square.getRotation(), "The square's rotation should match the rotation before point modification.");
        assertEquals(scaleBeforeReset, square.getScale(), "The square's scale should match the scale before point modification.");
    }

    @Test
    void checkPolygon2DTranslation_shouldMatchExpected() {
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, 5f);
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

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.translate(randomTranslation);
        Pointf[] actualTranslatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedTranslatedPoints, actualTranslatedPoints, "The actual Pointf array, which has been translated, should match the expected Pointf array.");
    }

    @Test
    void checkPolygon2DRotation_aroundOrigin_shouldMatchExpected() {
        float randomRotationInDegrees = Maths.random(0f, 100f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

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

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.rotate(randomRotationInDegrees, Pointf.Origin);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated about the origin, should match the expected Pointf array.");
    }

    @Test
    void checkPolygon2DRotation_aroundPolygonCenter_shouldMatchExpected() {
        float randomRotationInDegrees = Maths.random(0f, 100f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(size / 2f),
                originalPoints[1].copy().subtract(size / 2f),
                originalPoints[2].copy().subtract(size / 2f),
                originalPoints[3].copy().subtract(size / 2f),
        };

        Pointf[] expectedRotatedPoints = {
                new Pointf(
                        pointsAtOrigin[0].x * cosOfRotation - pointsAtOrigin[0].y * sinOfRotation,
                        pointsAtOrigin[0].y * cosOfRotation + pointsAtOrigin[0].x * sinOfRotation
                ).add(size / 2f),
                new Pointf(
                        pointsAtOrigin[1].x * cosOfRotation - pointsAtOrigin[1].y * sinOfRotation,
                        pointsAtOrigin[1].y * cosOfRotation + pointsAtOrigin[1].x * sinOfRotation
                ).add(size / 2f),
                new Pointf(
                        pointsAtOrigin[2].x * cosOfRotation - pointsAtOrigin[2].y * sinOfRotation,
                        pointsAtOrigin[2].y * cosOfRotation + pointsAtOrigin[2].x * sinOfRotation
                ).add(size / 2f),
                new Pointf(
                        pointsAtOrigin[3].x * cosOfRotation - pointsAtOrigin[3].y * sinOfRotation,
                        pointsAtOrigin[3].y * cosOfRotation + pointsAtOrigin[3].x * sinOfRotation
                ).add(size / 2f)
        };

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.rotate(randomRotationInDegrees);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated about its center, should match the expected Pointf array.");
    }

    @Test
    void checkPolygon2DRotation_aroundRandomPoint_shouldMatchExpected() {
        float randomRotationInDegrees = Maths.random(0f, 100f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);
        float size = 5f;
        Pointf randomCenter = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(randomCenter),
                originalPoints[1].copy().subtract(randomCenter),
                originalPoints[2].copy().subtract(randomCenter),
                originalPoints[3].copy().subtract(randomCenter),
        };

        Pointf[] expectedRotatedPoints = {
                new Pointf(
                        pointsAtOrigin[0].x * cosOfRotation - pointsAtOrigin[0].y * sinOfRotation,
                        pointsAtOrigin[0].y * cosOfRotation + pointsAtOrigin[0].x * sinOfRotation
                ).add(randomCenter),
                new Pointf(
                        pointsAtOrigin[1].x * cosOfRotation - pointsAtOrigin[1].y * sinOfRotation,
                        pointsAtOrigin[1].y * cosOfRotation + pointsAtOrigin[1].x * sinOfRotation
                ).add(randomCenter),
                new Pointf(
                        pointsAtOrigin[2].x * cosOfRotation - pointsAtOrigin[2].y * sinOfRotation,
                        pointsAtOrigin[2].y * cosOfRotation + pointsAtOrigin[2].x * sinOfRotation
                ).add(randomCenter),
                new Pointf(
                        pointsAtOrigin[3].x * cosOfRotation - pointsAtOrigin[3].y * sinOfRotation,
                        pointsAtOrigin[3].y * cosOfRotation + pointsAtOrigin[3].x * sinOfRotation
                ).add(randomCenter)
        };

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.rotate(randomRotationInDegrees, randomCenter);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();
        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated about " + randomCenter + ", should match the expected Pointf array.");
    }

    @Test
    void checkPolygon2DScaling_aroundOrigin_shouldMatchExpected() {
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf newScale = Pointf.add(randomScaling, Transform2D.DefaultScale);
        Pointf[] expectedScaledPoints = {
                originalPoints[0].copy().multiply(newScale),
                originalPoints[1].copy().multiply(newScale),
                originalPoints[2].copy().multiply(newScale),
                originalPoints[3].copy().multiply(newScale)
        };

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.scale(randomScaling, Pointf.Origin);
        Pointf[] actualScaledPoints = polygon2D.getPoints();
        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled, should match the expected Pointf array.");
    }

    @Test
    void checkPolygon2DScaling_aroundPolygonCenter_shouldMatchExpected() {
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf newScale = Pointf.add(randomScaling, Transform2D.DefaultScale);
        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(size / 2f),
                originalPoints[1].copy().subtract(size / 2f),
                originalPoints[2].copy().subtract(size / 2f),
                originalPoints[3].copy().subtract(size / 2f),
        };
        Pointf[] expectedScaledPoints = {
                pointsAtOrigin[0].copy().multiply(newScale).add(size / 2f),
                pointsAtOrigin[1].copy().multiply(newScale).add(size / 2f),
                pointsAtOrigin[2].copy().multiply(newScale).add(size / 2f),
                pointsAtOrigin[3].copy().multiply(newScale).add(size / 2f)
        };

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.scale(randomScaling);
        Pointf[] actualScaledPoints = polygon2D.getPoints();
        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled around its center, should match the expected Pointf array.");
    }

    @Test
    void checkPolygon2DScaling_aroundRandomPoint_shouldMatchExpected() {
        Pointf randomCenter = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf newScale = Pointf.add(randomScaling, Transform2D.DefaultScale);
        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(randomCenter),
                originalPoints[1].copy().subtract(randomCenter),
                originalPoints[2].copy().subtract(randomCenter),
                originalPoints[3].copy().subtract(randomCenter),
        };
        Pointf[] expectedScaledPoints = {
                pointsAtOrigin[0].copy().multiply(newScale).add(randomCenter),
                pointsAtOrigin[1].copy().multiply(newScale).add(randomCenter),
                pointsAtOrigin[2].copy().multiply(newScale).add(randomCenter),
                pointsAtOrigin[3].copy().multiply(newScale).add(randomCenter)
        };

        Polygon2D polygon2D = Polygon2D.fromPoints(originalPoints);
        polygon2D.scale(randomScaling, randomCenter);
        Pointf[] actualScaledPoints = polygon2D.getPoints();
        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled around " + randomScaling + ", should match the expected Pointf array.");
    }
}
