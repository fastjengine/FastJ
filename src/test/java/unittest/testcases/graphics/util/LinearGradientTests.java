package unittest.testcases.graphics.util;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.util.gradients.Gradients;

import java.awt.Color;
import java.awt.LinearGradientPaint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinearGradientTests {

    private static Pointf randomPointf() {
        return new Pointf(
                Maths.random(0.0f, 100.0f),
                Maths.random(0.0f, 100.0f)
        );
    }

    private static Color randomColor() {
        return new Color(
                Maths.random(0.0f, 1.0f),
                Maths.random(0.0f, 1.0f),
                Maths.random(0.0f, 1.0f),
                Maths.random(0.0f, 1.0f)
        );
    }

    @Test
    void checkCreateLinearGradient_withTwoRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();

        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, 1.0f},
                new Color[]{randomColor1, randomColor2}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateLinearGradient_withThreeRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();

        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, 0.5f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateLinearGradient_withFourRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();

        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, (1.0f / 3.0f), (2.0f / 3.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateLinearGradient_withFiveRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();

        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateLinearGradient_withSixRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();

        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateLinearGradient_withSevenRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();


        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, (1.0f / 6.0f), (2.0f / 6.0f), (3.0f / 6.0f), (4.0f / 6.0f), (5.0f / 6.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .withColor(randomColor7)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateLinearGradient_withEightRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();
        Color randomColor8 = randomColor();


        Pointf randomStartingPoint = randomPointf();
        Pointf randomEndingPoint = randomPointf();

        LinearGradientPaint expectedLinearGradientPaint = new LinearGradientPaint(
                randomStartingPoint.x,
                randomStartingPoint.y,
                randomEndingPoint.x,
                randomEndingPoint.y,
                new float[]{0.0f, (1.0f / 7.0f), (2.0f / 7.0f), (3.0f / 7.0f), (4.0f / 7.0f), (5.0f / 7.0f), (6.0f / 7.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7, randomColor8}
        );

        LinearGradientPaint actualLinearGradientPaint = Gradients.linearGradient()
                .position(randomStartingPoint, randomEndingPoint)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .withColor(randomColor7)
                .withColor(randomColor8)
                .build();

        assertEquals(expectedLinearGradientPaint.getStartPoint(), actualLinearGradientPaint.getStartPoint(), "The created linear gradient's starting point should match the expected linear gradient's starting point.");
        assertEquals(expectedLinearGradientPaint.getEndPoint(), actualLinearGradientPaint.getEndPoint(), "The created linear gradient's ending point should match the expected linear gradient's ending point.");
        assertArrayEquals(expectedLinearGradientPaint.getColors(), actualLinearGradientPaint.getColors(), "The created linear gradient's colors should match the expected linear gradient's colors.");
        assertEquals(expectedLinearGradientPaint.getCycleMethod(), actualLinearGradientPaint.getCycleMethod(), "The created linear gradient's cycle method should match the expected linear gradient's cycle method.");
        assertEquals(expectedLinearGradientPaint.getColorSpace(), actualLinearGradientPaint.getColorSpace(), "The created linear gradient's color space should match the expected linear gradient's color space.");
        assertEquals(expectedLinearGradientPaint.getTransform(), actualLinearGradientPaint.getTransform(), "The created linear gradient's transform should match the expected linear gradient's transform.");
        assertEquals(expectedLinearGradientPaint.getTransparency(), actualLinearGradientPaint.getTransparency(), "The created linear gradient's transparency should match the expected linear gradient's transparency.");

        for (int i = 0; i < expectedLinearGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedLinearGradientPaint.getFractions()[i],
                            actualLinearGradientPaint.getFractions()[i]
                    ),
                    "The created linear gradient's fractions should match the expected linear gradient's fractions."
            );
        }
    }
}
