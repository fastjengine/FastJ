package unittest.testcases.graphics;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.shapes.Model2D;
import io.github.lucasstarsz.fastj.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class DrawUtilTests {

    private static final Path tempModelDirectoryPath = Path.of("temp");

    @BeforeClass
    public static void createTempModelFolder_forReadWriteTests() throws IOException {
        Files.createDirectory(tempModelDirectoryPath);
        System.out.println("Temporary Model Directory created at: " + tempModelDirectoryPath.toAbsolutePath().toString());
    }

    @AfterClass
    public static void deleteTempModelFolder() throws IOException {
        try (Stream<Path> pathWalker = Files.walk(tempModelDirectoryPath)) {
            pathWalker.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        System.out.println("Deleted directory \"" + tempModelDirectoryPath.toAbsolutePath().toString() + "\".");
    }

    @Test
    public void checkReadAndWriteModel_shouldMatchOriginal() {
        Pointf[] expectedModelSquare = DrawUtil.createBox(25f, 25f, 50f);
        Pointf[] expectedModelTriangle = {
                new Pointf(15f, 25f),
                new Pointf(50f, 20f),
                new Pointf(85f, 25f)
        };

        Polygon2D[] expectedHouseArray = {
                new Polygon2D(expectedModelSquare),
                new Polygon2D(expectedModelTriangle)
        };
        Model2D expectedHouse = new Model2D(expectedHouseArray, false);

        String pathToModel = tempModelDirectoryPath.toAbsolutePath().toString() + File.separator + "temp_house_model.psdf";
        DrawUtil.writeToPSDF(pathToModel, expectedHouse);


        Polygon2D[] actualHouseArray = DrawUtil.load2DModel(pathToModel);
        Model2D actualHouse = new Model2D(actualHouseArray, false);

        assertArrayEquals("The actual Polygon2D array should match the expected array.", expectedHouseArray, actualHouseArray);
        assertEquals("The actual Model2D should match the expected Model2D.", expectedHouse, actualHouse);
    }

    @Test
    public void checkCreateCollisionOutline_withTwoSquares_shouldMatchExpected() {
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
                new Polygon2D(square1Points),
                new Polygon2D(square2Points)
        };
        Pointf[] actualOutline = DrawUtil.createCollisionOutline(squareArray);

        assertArrayEquals("The actual outline should match the expected outline.", expectedOutline, actualOutline);
    }

    @Test
    public void checkCreateCollisionOutline_withThreeSquares_shouldMatchExpected() {
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
                new Polygon2D(square1Points),
                new Polygon2D(square2Points),
                new Polygon2D(square3Points)
        };
        Pointf[] actualOutline = DrawUtil.createCollisionOutline(squareArray);

        assertArrayEquals("The actual outline should match the expected outline.", expectedOutline, actualOutline);
    }

    @Test
    public void checkGenerateBox_withFloatXAndY_andFloatSize() {
        Pointf[] generatedResult = DrawUtil.createBox(5f, 5f, 35f);
        Pointf[] expectedResult = {
                new Pointf(5f, 5f),
                new Pointf(40f, 5f),
                new Pointf(40f, 40f),
                new Pointf(5f, 40f)
        };

        assertArrayEquals("The generated Pointf array should match the hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateBox_withFloatXAndY_andFloatSizeXAndY() {
        Pointf[] generatedResult = DrawUtil.createBox(5f, 5f, 20f, 15f);
        Pointf[] expectedResult = {
                new Pointf(5f, 5f),
                new Pointf(25f, 5f),
                new Pointf(25f, 20f),
                new Pointf(5f, 20f)
        };

        assertArrayEquals("The generated Pointf array should match hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateBox_withFloatXAndY_andPointfSize() {
        Pointf[] generatedResult = DrawUtil.createBox(5f, 5f, new Pointf(25f, 50f));
        Pointf[] expectedResult = {
                new Pointf(5f, 5f),
                new Pointf(30f, 5f),
                new Pointf(30f, 55f),
                new Pointf(5f, 55f)
        };

        assertArrayEquals("The generated Pointf array should match the hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateBox_withPointfLocation_andFloatSize() {
        Pointf[] generatedResult = DrawUtil.createBox(new Pointf(5f, 10f), 35f);
        Pointf[] expectedResult = {
                new Pointf(5f, 10f),
                new Pointf(40f, 10f),
                new Pointf(40f, 45f),
                new Pointf(5f, 45f)
        };

        assertArrayEquals("The generated Pointf array should match hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateBox_withPointfLocationY_andPointfSize() {
        Pointf[] generatedResult = DrawUtil.createBox(new Pointf(10f, 15f), new Pointf(25f, 40f));
        Pointf[] expectedResult = {
                new Pointf(10f, 15f),
                new Pointf(35f, 15f),
                new Pointf(35f, 55f),
                new Pointf(10f, 55f)
        };

        assertArrayEquals("The generated Pointf array should match hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateBox_fromBufferedImage() {
        BufferedImage image = new BufferedImage(15, 40, BufferedImage.TYPE_INT_RGB);

        Pointf[] generatedResult = DrawUtil.createBoxFromImage(image);
        Pointf[] expectedResult = {
                new Pointf(0f, 0f),
                new Pointf(15f, 0f),
                new Pointf(15f, 40f),
                new Pointf(0f, 40f)
        };

        assertArrayEquals("The generated Pointf array should match hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateBox_fromBufferedImage_withPointfLocation() {
        BufferedImage image = new BufferedImage(50, 25, BufferedImage.TYPE_INT_RGB);

        Pointf[] generatedResult = DrawUtil.createBoxFromImage(image, new Pointf(10f, 0f));
        Pointf[] expectedResult = {
                new Pointf(10f, 0f),
                new Pointf(60f, 0f),
                new Pointf(60f, 25f),
                new Pointf(10f, 25f)
        };

        assertArrayEquals("The generated Pointf array should match hand-made Pointf array.", expectedResult, generatedResult);
    }

    @Test
    public void checkGenerateRectangleFloat_withHandwrittenPointfArray() {
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

        assertEquals("The two rectangles should be equal.", expectedRect, actualRect);
    }

    @Test
    public void checkGenerateRectangleFloat_withPointfArrayFromGeneratedBox() {
        float rectX = 0f;
        float rectY = 5f;
        float rectWidth = 25f;
        float rectHeight = 30f;

        Pointf[] points = DrawUtil.createBox(rectX, rectY, rectWidth, rectHeight);

        Rectangle2D.Float expectedRect = DrawUtil.createRect(points);
        Rectangle2D.Float actualRect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectHeight);

        assertEquals("The two rectangles should be equal.", expectedRect, actualRect);
    }

    @Test
    public void checkGenerateRectangleFloat_withPointfLocation_andSizeFromBufferedImage() {
        float rectX = 0f;
        float rectY = 5f;
        int rectWidth = 25;
        int rectHeight = 30;

        BufferedImage image = new BufferedImage(rectWidth, rectHeight, BufferedImage.TYPE_INT_RGB);
        Pointf location = new Pointf(rectX, rectY);

        Rectangle2D.Float expectedRect = DrawUtil.createRectFromImage(image, location);
        Rectangle2D.Float actualRect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectHeight);

        assertEquals("The two rectangles should be equal.", expectedRect, actualRect);
    }

    @Test
    public void checkGetCenterOfPointfArray() {
        Pointf[] square = {
                new Pointf(13f),
                new Pointf(37f, 13f),
                new Pointf(37f),
                new Pointf(13f, 37f)
        };

        Pointf expectedCenterOfSquare = new Pointf(25f, 25f);
        Pointf actualCenterOfSquare = DrawUtil.centerOf(square);

        assertEquals("The actual center of the square should match the expected center.", expectedCenterOfSquare, actualCenterOfSquare);
    }

    @Test
    public void checkGetPointsOfPath2DFloat_shouldMatchOriginalPointfArray() {
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

        assertArrayEquals("The actual array of Pointfs should match the expected array.", expectedPoints, actualPoints);
    }

    @Test
    public void checkPathLengthGetter() {
        Path2D.Float path = new Path2D.Float();
        int expectedLength = 25;

        path.moveTo(0f, 0f);
        for (int i = 0; i < expectedLength; i++) {
            path.lineTo(i, i);
        }
        path.closePath();
        int actualLength = DrawUtil.lengthOfPath(path);

        assertEquals("The length of the path should be the same as the original intended length.", expectedLength, actualLength);
    }

    @Test
    public void checkGenerateColors_shouldNotFail() {
        int generatedColorCount = 255;
        Color[] generatedRGBColors = new Color[generatedColorCount];

        for (int i = 0; i < generatedColorCount; i++) {
            generatedRGBColors[i] = DrawUtil.randomColor();
        }
        System.out.println("Generated RGB colors: " + Arrays.toString(generatedRGBColors));
    }

    @Test
    public void checkGenerateColorsWithRandomAlpha_shouldNotFail() {
        int generatedColorCount = 255;
        Color[] generatedRGBAColors = new Color[generatedColorCount];

        for (int i = 0; i < generatedColorCount; i++) {
            generatedRGBAColors[i] = DrawUtil.randomColorWithAlpha();
        }
        System.out.println("Generated RGBA colors: " + Arrays.toString(generatedRGBAColors));
    }
}
