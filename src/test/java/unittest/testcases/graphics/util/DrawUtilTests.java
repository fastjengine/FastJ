package unittest.testcases.graphics.util;

import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;
import tech.fastj.systems.collections.Pair;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DrawUtilTests {

    private static final Logger Log = LoggerFactory.getLogger(DrawUtilTests.class);

    @Test
    void checkCreateCollisionOutline_withTwoSquares_shouldMatchExpected() {
        Pointf[] square1Points = DrawUtil.createBox(0f, 0f, 50f);
        Pointf[] square2Points = DrawUtil.createBox(25f, 25f, 50f);
        Pointf[] expectedOutline = {
                square1Points[0].copy(),
                square1Points[1].copy(),
                square2Points[1].copy(),
                square2Points[2].copy(),
                square2Points[3].copy(),
                square1Points[3].copy()
        };

        Polygon2D[] squareArray = {
                Polygon2D.fromPoints(square1Points),
                Polygon2D.fromPoints(square2Points)
        };
        Pointf[] actualOutline = DrawUtil.createCollisionOutline(squareArray);

        assertArrayEquals(expectedOutline, actualOutline, "The actual outline should match the expected outline.");
    }

    @Test
    void checkCreateCollisionOutline_withThreeSquares_shouldMatchExpected() {
        Pointf[] square1Points = DrawUtil.createBox(0f, 0f, 60f);
        Pointf[] square2Points = DrawUtil.createBox(40f, 20f, 60f);
        Pointf[] square3Points = DrawUtil.createBox(20f, 40f, 60f);
        Pointf[] expectedOutline = {
                square1Points[0].copy(),
                square1Points[1].copy(),
                square2Points[1].copy(),
                square2Points[2].copy(),
                square3Points[2].copy(),
                square3Points[3].copy(),
                square1Points[3].copy()
        };

        Polygon2D[] squareArray = {
                Polygon2D.fromPoints(square1Points),
                Polygon2D.fromPoints(square2Points),
                Polygon2D.fromPoints(square3Points)
        };
        Pointf[] actualOutline = DrawUtil.createCollisionOutline(squareArray);

        assertArrayEquals(expectedOutline, actualOutline, "The actual outline should match the expected outline.");
    }

    @Test
    void checkGeneratePath2D_withPointfArray() {
        Pointf[] polygon = {
                Pointf.origin(),
                new Pointf(50f, 30f),
                new Pointf(-5f, 47f),
                new Pointf(45f, 20f),
                new Pointf(5f, 15f)
        };
        Path2D.Float expectedPath = new Path2D.Float();
        expectedPath.moveTo(polygon[0].x, polygon[0].y);
        expectedPath.lineTo(polygon[1].x, polygon[1].y);
        expectedPath.lineTo(polygon[2].x, polygon[2].y);
        expectedPath.lineTo(polygon[3].x, polygon[3].y);
        expectedPath.lineTo(polygon[4].x, polygon[4].y);
        expectedPath.closePath();

        Path2D.Float actualPath = DrawUtil.createPath(polygon);

        assertTrue(DrawUtil.pathEquals(expectedPath, actualPath), "The actual path should match the expected path.");
    }

    @Test
    void checkComparePath2Ds_wherePathsAreEqual() {
        Pointf[] square1 = DrawUtil.createBox(50f, 50f, 50f);
        Pointf[] square2 = DrawUtil.createBox(50f, 50f, 50f);
        Pointf[] square3 = DrawUtil.createBox(5f, 50f, 50f);

        Path2D.Float squarePath1 = DrawUtil.createPath(square1);
        Path2D.Float squarePath2 = DrawUtil.createPath(square2);
        Path2D.Float squarePath3 = DrawUtil.createPath(square3);

        assertTrue(DrawUtil.pathEquals(squarePath1, squarePath2), "Square path 1 should match the points of square path 2.");
        assertFalse(DrawUtil.pathEquals(squarePath2, squarePath3), "Square path 2 should not match the points of square path 3.");
    }

    @Test
    void tryComparePath2Ds_wherePathsAreNotEqual() {
        Pointf[] square = DrawUtil.createBox(50f, 50f, 50f);
        Pointf[] triangle = {
                new Pointf(0f, 25f),
                new Pointf(25f, 15f),
                new Pointf(50f, 25f)
        };

        Path2D.Float path1 = DrawUtil.createPath(square);
        Path2D.Float path2 = DrawUtil.createPath(triangle);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> DrawUtil.pathEquals(path1, path2));

        String expectedMessage = "Path lengths differ\nPath 1 had a length of 4, but path 2 had a length of 3.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage,
                "An IllegalStateException should be thrown stating the path's lengths are inequal, as well as each path's length."
        );
    }

    @Test
    void comparePaints_withNullPaintParameters() {
        Paint paint1_null = null;
        Color paint2 = Color.red;

        assertFalse(DrawUtil.paintEquals(paint1_null, paint2), "The two paints should not be equal.");
        assertFalse(DrawUtil.paintEquals(paint2, paint1_null), "The two paints should not be equal.");
    }

    @Test
    void checkGenerateBox_withFloatXAndY_andFloatSize() {
        Pointf[] generatedResult = DrawUtil.createBox(5f, 5f, 35f);
        Pointf[] expectedResult = {
                new Pointf(5f, 5f),
                new Pointf(40f, 5f),
                new Pointf(40f, 40f),
                new Pointf(5f, 40f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match the hand-made Pointf array.");
    }

    @Test
    void checkGenerateBox_withFloatXAndY_andFloatSizeXAndY() {
        Pointf[] generatedResult = DrawUtil.createBox(5f, 5f, 20f, 15f);
        Pointf[] expectedResult = {
                new Pointf(5f, 5f),
                new Pointf(25f, 5f),
                new Pointf(25f, 20f),
                new Pointf(5f, 20f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match hand-made Pointf array.");
    }

    @Test
    void checkGenerateBox_withFloatXAndY_andPointfSize() {
        Pointf[] generatedResult = DrawUtil.createBox(5f, 5f, new Pointf(25f, 50f));
        Pointf[] expectedResult = {
                new Pointf(5f, 5f),
                new Pointf(30f, 5f),
                new Pointf(30f, 55f),
                new Pointf(5f, 55f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match the hand-made Pointf array.");
    }

    @Test
    void checkGenerateBox_withPointfLocation_andFloatSize() {
        Pointf[] generatedResult = DrawUtil.createBox(new Pointf(5f, 10f), 35f);
        Pointf[] expectedResult = {
                new Pointf(5f, 10f),
                new Pointf(40f, 10f),
                new Pointf(40f, 45f),
                new Pointf(5f, 45f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match hand-made Pointf array.");
    }

    @Test
    void checkGenerateBox_withPointfLocationY_andPointfSize() {
        Pointf[] generatedResult = DrawUtil.createBox(new Pointf(10f, 15f), new Pointf(25f, 40f));
        Pointf[] expectedResult = {
                new Pointf(10f, 15f),
                new Pointf(35f, 15f),
                new Pointf(35f, 55f),
                new Pointf(10f, 55f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match hand-made Pointf array.");
    }

    @Test
    void checkGenerateBox_fromBufferedImage() {
        BufferedImage image = new BufferedImage(15, 40, BufferedImage.TYPE_INT_RGB);

        Pointf[] generatedResult = DrawUtil.createBoxFromImage(image);
        Pointf[] expectedResult = {
                new Pointf(0f, 0f),
                new Pointf(15f, 0f),
                new Pointf(15f, 40f),
                new Pointf(0f, 40f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match hand-made Pointf array.");
    }

    @Test
    void checkGenerateBox_fromBufferedImage_withPointfLocation() {
        BufferedImage image = new BufferedImage(50, 25, BufferedImage.TYPE_INT_RGB);

        Pointf[] generatedResult = DrawUtil.createBoxFromImage(image, new Pointf(10f, 0f));
        Pointf[] expectedResult = {
                new Pointf(10f, 0f),
                new Pointf(60f, 0f),
                new Pointf(60f, 25f),
                new Pointf(10f, 25f)
        };

        assertArrayEquals(expectedResult, generatedResult, "The generated Pointf array should match hand-made Pointf array.");
    }

    @Test
    void checkGenerateRectangleFloat_withHandwrittenPointfArray() {
        float rectX = 0f;
        float rectY = 5f;
        float rectWidth = 25f;
        float rectHeight = 30f;

        Pointf[] points = {
                new Pointf(rectX, rectY),
                new Pointf(rectX + rectWidth, rectY),
                new Pointf(rectX + rectWidth, rectY + rectHeight),
                new Pointf(rectX, rectY + rectHeight)
        };

        Rectangle2D.Float expectedRect = DrawUtil.createRect(points);
        Rectangle2D.Float actualRect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectHeight);

        assertEquals(expectedRect, actualRect, "The two rectangles should be equal.");
    }

    @Test
    void tryGenerateRectangleFloat_withHandwrittenPointfArray_butArraySizeIsNotFour() {
        float rectX = 0f;
        float rectY = 5f;
        float rectWidth = 25f;
        float rectHeight = 30f;

        Pointf[] points = {
                new Pointf(rectX, rectY),
                new Pointf(rectX + rectWidth, rectY),
                new Pointf(rectX + rectWidth, rectY + rectHeight)
        };

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> DrawUtil.createRect(points));
        String expectedExceptionMessage = "The length of the parameter point array must be 4.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void checkGenerateRectangleFloat_withPointfArrayFromGeneratedBox() {
        float rectX = 0f;
        float rectY = 5f;
        float rectWidth = 25f;
        float rectHeight = 30f;

        Pointf[] points = DrawUtil.createBox(rectX, rectY, rectWidth, rectHeight);

        Rectangle2D.Float expectedRect = DrawUtil.createRect(points);
        Rectangle2D.Float actualRect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectHeight);

        assertEquals(expectedRect, actualRect, "The two rectangles should be equal.");
    }

    @Test
    void checkGenerateRectangleFloat_withPointfLocation_andSizeFromBufferedImage() {
        float rectX = 0f;
        float rectY = 5f;
        int rectWidth = 25;
        int rectHeight = 30;

        BufferedImage image = new BufferedImage(rectWidth, rectHeight, BufferedImage.TYPE_INT_RGB);
        Pointf location = new Pointf(rectX, rectY);

        Rectangle2D.Float expectedRect = DrawUtil.createRectFromImage(image, location);
        Rectangle2D.Float actualRect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectHeight);

        assertEquals(expectedRect, actualRect, "The two rectangles should be equal.");
    }

    @Test
    void checkGetCenterOfPointfArray() {
        Pointf[] square = {
                new Pointf(13f),
                new Pointf(37f, 13f),
                new Pointf(37f),
                new Pointf(13f, 37f)
        };

        Pointf expectedCenterOfSquare = new Pointf(25f, 25f);
        Pointf actualCenterOfSquare = DrawUtil.centerOf(square);

        assertEquals(expectedCenterOfSquare, actualCenterOfSquare, "The actual center of the square should match the expected center.");
    }

    @Test
    void checkGetPointsOfPath2DFloat_shouldMatchOriginalPointfArray() {
        float top = 0f;
        float bottom = 75f;
        float left = 0f;
        float right = 75f;
        float leftCenter = 25f;
        float rightCenter = 50f;
        float topCenter = 25f;
        float bottomCenter = 50f;
        Pointf[] expectedPoints = {
                new Pointf(leftCenter, top),
                new Pointf(rightCenter, top),
                new Pointf(right, topCenter),
                new Pointf(right, bottomCenter),
                new Pointf(rightCenter, bottom),
                new Pointf(leftCenter, bottom),
                new Pointf(left, bottomCenter),
                new Pointf(left, topCenter)
        };

        Path2D.Float pathFromExpectedPoints = new Path2D.Float();
        pathFromExpectedPoints.moveTo(expectedPoints[0].x, expectedPoints[0].y);
        for (int i = 1; i < expectedPoints.length; i++) {
            pathFromExpectedPoints.lineTo(expectedPoints[i].x, expectedPoints[i].y);
        }
        pathFromExpectedPoints.closePath();

        Pointf[] actualPoints = DrawUtil.pointsOfPath(pathFromExpectedPoints);

        assertArrayEquals(expectedPoints, actualPoints, "The actual array of Pointfs should match the expected array.");
    }

    @Test
    void checkGetPointsOfPath2DFloat_andAlternateIndexes_shouldMatchOriginalPointfArray() {
        float top = 0f;
        float bottom = 75f;
        float left = 0f;
        float right = 75f;
        float leftCenter = 25f;
        float rightCenter = 50f;
        float topCenter = 25f;
        float bottomCenter = 50f;
        Pointf[] expectedPoints = {
                new Pointf(leftCenter, top),
                new Pointf(rightCenter, top),
                new Pointf(right, topCenter),
                new Pointf(right, bottomCenter),
                new Pointf(rightCenter, bottom),
                new Pointf(leftCenter, bottom),
                new Pointf(left, bottomCenter),
                new Pointf(left, topCenter)
        };
        Pointf[] expectedCurvesAfterPoints = {
                new Pointf(left - 50f, topCenter),
                new Pointf(right + 50f, topCenter + 50f),
                new Pointf(leftCenter, topCenter + 100f),
                new Pointf(right, bottomCenter),
                new Pointf(leftCenter, bottomCenter + 100f)
        };
        Point[] expectedAltIndexes = {
                new Point(expectedPoints.length, Polygon2D.BezierCurve),
                new Point(expectedPoints.length + 3, Polygon2D.QuadCurve)
        };

        Path2D.Float pathFromExpectedPoints = new Path2D.Float();
        pathFromExpectedPoints.moveTo(expectedPoints[0].x, expectedPoints[0].y);
        for (int i = 1; i < expectedPoints.length; i++) {
            pathFromExpectedPoints.lineTo(expectedPoints[i].x, expectedPoints[i].y);
        }
        pathFromExpectedPoints.curveTo(
                expectedCurvesAfterPoints[0].x,
                expectedCurvesAfterPoints[0].y,
                expectedCurvesAfterPoints[1].x,
                expectedCurvesAfterPoints[1].y,
                expectedCurvesAfterPoints[2].x,
                expectedCurvesAfterPoints[2].y
        );
        pathFromExpectedPoints.quadTo(
                expectedCurvesAfterPoints[3].x,
                expectedCurvesAfterPoints[3].y,
                expectedCurvesAfterPoints[4].x,
                expectedCurvesAfterPoints[4].y
        );
        pathFromExpectedPoints.closePath();

        Pair<Pointf[], Point[]> actualPoints = DrawUtil.pointsOfPathWithAlt(pathFromExpectedPoints);
        Pointf[] allExpectedPoints = Stream.concat(Arrays.stream(expectedPoints), Arrays.stream(expectedCurvesAfterPoints))
                        .toArray(Pointf[]::new);

        assertArrayEquals(allExpectedPoints, actualPoints.getLeft(), "The actual array of Pointfs should match the expected array.");
        assertArrayEquals(expectedAltIndexes, actualPoints.getRight(), "The actual array of alternate index Points should match the expected array.");
    }

    @Test
    void checkPathLengthGetter() {
        Path2D.Float path = new Path2D.Float();
        int expectedLength = 26 + 5;

        path.moveTo(0f, 0f);

        int initialPosition = 1;
        int linesCreated = expectedLength - 1 - 5;
        for (int i = initialPosition; i <= linesCreated; i++) {
            path.lineTo(i, i * 2);
        }
        path.curveTo(0f, 0f, 100f, 5f, 50f, 200f);
        path.quadTo(75f, 225f, 50f, 250f);
        path.closePath();

        int actualLength = DrawUtil.lengthOfPath(path);
        assertEquals(expectedLength, actualLength, "The length of the path should be the same as the original intended length.");
    }

    @Test
    void checkPathLengthGetter_withMultipleSubpaths() {
        Path2D.Float path = new Path2D.Float();
        int subpathCount = 5;
        int expectedLength = 26 * subpathCount;

        int initialPosition = 1;
        int linesCreated = (expectedLength / subpathCount) - 1;
        for (int j = 0; j < subpathCount; j++) {
            path.moveTo(0f, 0f);
            for (int i = initialPosition; i <= linesCreated; i++) {
                path.lineTo(i, i * 2);
            }
            path.closePath();
        }

        int actualLength = DrawUtil.lengthOfPath(path);
        assertEquals(expectedLength, actualLength, "The length of the path should be the same as the original intended length.");
    }

    @Test
    void checkGenerateColors_shouldNotFail() {
        int generatedColorCount = 255;
        Color[] generatedRGBColors = new Color[generatedColorCount];

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedColorCount; i++) {
                        generatedRGBColors[i] = DrawUtil.randomColor();
                    }
                },
                "Errors should not be produced while generating random RGB colors."
        );

        Log.trace("Generated RGB colors: {}", Arrays.toString(generatedRGBColors));
    }

    @Test
    void checkGenerateColorsWithRandomAlpha_shouldNotFail() {
        int generatedColorCount = 255;
        Color[] generatedRGBAColors = new Color[generatedColorCount];

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedColorCount; i++) {
                        generatedRGBAColors[i] = DrawUtil.randomColorWithAlpha();
                    }
                },
                "Errors should not be produced while generating random RGBA colors."
        );

        Log.trace("Generated RGBA colors: {}", Arrays.toString(generatedRGBAColors));
    }

    @Test
    void checkGenerateFonts_shouldNotFail() {
        int generatedFontCount = 255;
        Font[] generatedFonts = new Font[generatedFontCount];

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedFontCount; i++) {
                        generatedFonts[i] = DrawUtil.randomFont();
                    }
                },
                "Errors should not be produced while generating random fonts."
        );

        Log.trace("Generated fonts: {}", Arrays.toString(generatedFonts));
    }

    @Test
    void checkGenerateOutlineStrokes_shouldNotFail() {
        int generatedOutlineStrokeCount = 255;

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedOutlineStrokeCount; i++) {
                        DrawUtil.randomOutlineStroke();
                    }
                },
                "Errors should not be produced while generating random outline strokes."
        );
    }

    @Test
    void checkColorLerp_withOneLerpValue() {
        Color color = new Color(0, 0, 0, 0);
        Color color2 = new Color(50, 100, 200, 255);

        float lerpValue1 = 0.5f;
        float lerpValue2 = 0.0f;
        float lerpValue3 = 1.0f;
        float lerpValue4 = -2.0f;
        float lerpValue5 = 5f;

        Color expectedColorLerpResult1 = new Color(25, 50, 100, 127);
        Color expectedColorLerpResult2 = new Color(0, 0, 0, 0);
        Color expectedColorLerpResult3 = new Color(50, 100, 200, 255);
        Color expectedColorLerpResult4 = new Color(0, 0, 0, 0);
        Color expectedColorLerpResult5 = new Color(250, 255, 255, 255);
        Color actualColorLerpResult1 = DrawUtil.colorLerp(color, color2, lerpValue1);
        Color actualColorLerpResult2 = DrawUtil.colorLerp(color, color2, lerpValue2);
        Color actualColorLerpResult3 = DrawUtil.colorLerp(color, color2, lerpValue3);
        Color actualColorLerpResult4 = DrawUtil.colorLerp(color, color2, lerpValue4);
        Color actualColorLerpResult5 = DrawUtil.colorLerp(color, color2, lerpValue5);

        assertEquals(expectedColorLerpResult1, actualColorLerpResult1, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult1 + ".");
        assertEquals(expectedColorLerpResult2, actualColorLerpResult2, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult2 + ".");
        assertEquals(expectedColorLerpResult3, actualColorLerpResult3, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult3 + ".");
        assertEquals(expectedColorLerpResult4, actualColorLerpResult4, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult4 + ".");
        assertEquals(expectedColorLerpResult5, actualColorLerpResult5, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult5 + ".");
    }

    @Test
    void checkColorLerp_withFourLerpValues() {
        Color color = new Color(0, 0, 0, 0);
        Color color2 = new Color(50, 100, 200, 255);

        float lerpValue1 = 5.0f;
        float lerpValue2 = 0.0f;
        float lerpValue3 = 1.0f;
        float lerpValue4 = -2.0f;

        Color expectedColorLerpResult1 = new Color(250, 0, 200, 0);
        Color expectedColorLerpResult2 = new Color(0, 100, 0, 255);
        Color expectedColorLerpResult3 = new Color(50, 0, 255, 0);
        Color expectedColorLerpResult4 = new Color(0, 255, 0, 255);
        Color actualColorLerpResult1 = DrawUtil.colorLerp(color, color2, lerpValue1, lerpValue2, lerpValue3, lerpValue4);
        Color actualColorLerpResult2 = DrawUtil.colorLerp(color, color2, lerpValue2, lerpValue3, lerpValue4, lerpValue1);
        Color actualColorLerpResult3 = DrawUtil.colorLerp(color, color2, lerpValue3, lerpValue4, lerpValue1, lerpValue2);
        Color actualColorLerpResult4 = DrawUtil.colorLerp(color, color2, lerpValue4, lerpValue1, lerpValue2, lerpValue3);

        assertEquals(expectedColorLerpResult1, actualColorLerpResult1, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult1 + ".");
        assertEquals(expectedColorLerpResult2, actualColorLerpResult2, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult2 + ".");
        assertEquals(expectedColorLerpResult3, actualColorLerpResult3, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult3 + ".");
        assertEquals(expectedColorLerpResult4, actualColorLerpResult4, "The resulting lerped Color should equal the expected lerp result " + expectedColorLerpResult4 + ".");
    }

    @Test
    void checkColorInverseLerp_withOneValue() {
        Color color = new Color(0, 50, 100, 205);
        Color color2 = new Color(25, 200, 200, 255);

        float inverseLerpValue1 = 0f;
        float inverseLerpValue2 = 50f;
        float inverseLerpValue3 = 150f;
        float inverseLerpValue4 = 255f;

        float[] expectedInverseColorLerpResult1 = {0.0f, -1 / 3f, -1.0f, -4.1f};
        float[] expectedInverseColorLerpResult2 = {2.0f, 0.0f, -0.5f, -3.1f};
        float[] expectedInverseColorLerpResult3 = {6.0f, 2 / 3f, 0.5f, -1.1f};
        float[] expectedInverseColorLerpResult4 = {10.2f, 1 + (11 / 30f), 1.55f, 1.0f};
        float[] actualInverseColorLerpResult1 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue1);
        float[] actualInverseColorLerpResult2 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue2);
        float[] actualInverseColorLerpResult3 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue3);
        float[] actualInverseColorLerpResult4 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue4);

        assertArrayEquals(expectedInverseColorLerpResult1, actualInverseColorLerpResult1, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult1) + ".");
        assertArrayEquals(expectedInverseColorLerpResult2, actualInverseColorLerpResult2, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult2) + ".");
        assertArrayEquals(expectedInverseColorLerpResult3, actualInverseColorLerpResult3, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult3) + ".");
        assertArrayEquals(expectedInverseColorLerpResult4, actualInverseColorLerpResult4, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult4) + ".");
    }

    @Test
    void checkColorInverseLerp_withFourValues() {
        Color color = new Color(0, 50, 100, 205);
        Color color2 = new Color(25, 200, 200, 255);

        float inverseLerpValue1 = 0f;
        float inverseLerpValue2 = 50f;
        float inverseLerpValue3 = 150f;
        float inverseLerpValue4 = 255f;

        float[] expectedInverseColorLerpResult1 = {0.0f, 0.0f, 0.5f, 1.0f};
        float[] expectedInverseColorLerpResult2 = {2.0f, 2 / 3f, 1.55f, -4.1f};
        float[] expectedInverseColorLerpResult3 = {6.0f, 1 + (11 / 30f), -1.0f, -3.1f};
        float[] expectedInverseColorLerpResult4 = {10.2f, -1 / 3f, -0.5f, -1.1f};
        float[] actualInverseColorLerpResult1 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue1, inverseLerpValue2, inverseLerpValue3, inverseLerpValue4);
        float[] actualInverseColorLerpResult2 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue2, inverseLerpValue3, inverseLerpValue4, inverseLerpValue1);
        float[] actualInverseColorLerpResult3 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue3, inverseLerpValue4, inverseLerpValue1, inverseLerpValue2);
        float[] actualInverseColorLerpResult4 = DrawUtil.inverseColorLerp(color, color2, inverseLerpValue4, inverseLerpValue1, inverseLerpValue2, inverseLerpValue3);

        assertArrayEquals(expectedInverseColorLerpResult1, actualInverseColorLerpResult1, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult1) + ".");
        assertArrayEquals(expectedInverseColorLerpResult2, actualInverseColorLerpResult2, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult2) + ".");
        assertArrayEquals(expectedInverseColorLerpResult3, actualInverseColorLerpResult3, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult3) + ".");
        assertArrayEquals(expectedInverseColorLerpResult4, actualInverseColorLerpResult4, "The resulting inverse lerped Color should equal the expected inverse lerp result " + Arrays.toString(expectedInverseColorLerpResult4) + ".");
    }
}
