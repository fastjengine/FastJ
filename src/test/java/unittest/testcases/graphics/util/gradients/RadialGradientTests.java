package unittest.testcases.graphics.util.gradients;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.graphics.gradients.RadialGradientBuilder;

import java.awt.Color;
import java.awt.RadialGradientPaint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RadialGradientTests {

    private static float randomFloat() {
        return Maths.random(0.0f, 100.0f);
    }

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
    void checkCreateRadialGradient_withTwoRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 1.0f},
                new Color[]{randomColor1, randomColor2}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withThreeRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 0.5f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withFourRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, (1.0f / 3.0f), (2.0f / 3.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withFiveRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withSixRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withSevenRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();


        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, (1.0f / 6.0f), (2.0f / 6.0f), (3.0f / 6.0f), (4.0f / 6.0f), (5.0f / 6.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .withColor(randomColor7)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withEightRandomColors_usingRandomLocations() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();
        Color randomColor8 = randomColor();


        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, (1.0f / 7.0f), (2.0f / 7.0f), (3.0f / 7.0f), (4.0f / 7.0f), (5.0f / 7.0f), (6.0f / 7.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7, randomColor8}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .withColor(randomColor7)
                .withColor(randomColor8)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withTwoRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 1.0f},
                new Color[]{randomColor1, randomColor2}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withThreeRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 0.5f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withFourRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, (1.0f / 3.0f), (2.0f / 3.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3, randomColor4)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withFiveRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3, randomColor4, randomColor5)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withSixRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withSevenRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();


        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, (1.0f / 6.0f), (2.0f / 6.0f), (3.0f / 6.0f), (4.0f / 6.0f), (5.0f / 6.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void checkCreateRadialGradient_withEightRandomColors_usingRandomLocations_usingWithColorsMethod() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();
        Color randomColor8 = randomColor();


        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientPaint expectedRadialGradientPaint = new RadialGradientPaint(
                randomCenterpoint.x,
                randomCenterpoint.y,
                randomRadius,
                new float[]{0.0f, (1.0f / 7.0f), (2.0f / 7.0f), (3.0f / 7.0f), (4.0f / 7.0f), (5.0f / 7.0f), (6.0f / 7.0f), 1.0f},
                new Color[]{randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7, randomColor8}
        );

        RadialGradientPaint actualRadialGradientPaint = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7, randomColor8)
                .build();

        assertEquals(expectedRadialGradientPaint.getCenterPoint(), actualRadialGradientPaint.getCenterPoint(), "The created Radial gradient's starting point should match the expected Radial gradient's starting point.");
        assertEquals(expectedRadialGradientPaint.getRadius(), actualRadialGradientPaint.getRadius(), "The created Radial gradient's ending point should match the expected Radial gradient's ending point.");
        assertArrayEquals(expectedRadialGradientPaint.getColors(), actualRadialGradientPaint.getColors(), "The created Radial gradient's colors should match the expected Radial gradient's colors.");
        assertEquals(expectedRadialGradientPaint.getCycleMethod(), actualRadialGradientPaint.getCycleMethod(), "The created Radial gradient's cycle method should match the expected Radial gradient's cycle method.");
        assertEquals(expectedRadialGradientPaint.getColorSpace(), actualRadialGradientPaint.getColorSpace(), "The created Radial gradient's color space should match the expected Radial gradient's color space.");
        assertEquals(expectedRadialGradientPaint.getTransform(), actualRadialGradientPaint.getTransform(), "The created Radial gradient's transform should match the expected Radial gradient's transform.");
        assertEquals(expectedRadialGradientPaint.getTransparency(), actualRadialGradientPaint.getTransparency(), "The created Radial gradient's transparency should match the expected Radial gradient's transparency.");

        for (int i = 0; i < expectedRadialGradientPaint.getFractions().length; i++) {
            assertTrue(
                    Maths.floatEquals(
                            expectedRadialGradientPaint.getFractions()[i],
                            actualRadialGradientPaint.getFractions()[i]
                    ),
                    "The created Radial gradient's fractions should match the expected Radial gradient's fractions."
            );
        }
    }

    @Test
    void tryCreateRadialGradient_butPositionUsesARadiusThatIsTooSmall_radiusWithValueOfZero() {
        Pointf randomCenterpoint = randomPointf();
        float invalid_radius = 0.0f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Gradients.radialGradient(randomCenterpoint, invalid_radius));

        String expectedExceptionMessage = "The radius for a gradient must be larger than 0.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butPositionUsesARadiusThatIsTooSmall_radiusWithValueLessThanZero() {
        Pointf randomCenterpoint = randomPointf();
        float invalid_radius = Maths.random(-100.0f, -1.0f);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Gradients.radialGradient(randomCenterpoint, invalid_radius));

        String expectedExceptionMessage = "The radius for a gradient must be larger than 0.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butTooManyColorsWereAdded_usingWithColor() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();
        Color randomColor8 = randomColor();
        Color invalid_randomColor9 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientBuilder RadialGradientBuilder = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1)
                .withColor(randomColor2)
                .withColor(randomColor3)
                .withColor(randomColor4)
                .withColor(randomColor5)
                .withColor(randomColor6)
                .withColor(randomColor7)
                .withColor(randomColor8);

        Throwable exception = assertThrows(IllegalStateException.class, () -> RadialGradientBuilder.withColor(invalid_randomColor9));

        String expectedExceptionMessage = "Gradients cannot contain more than " + Gradients.MaximumColorCount + " colors.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butTooManyColorsWereAdded_usingWithColors() {
        Color randomColor1 = randomColor();
        Color randomColor2 = randomColor();
        Color randomColor3 = randomColor();
        Color randomColor4 = randomColor();
        Color randomColor5 = randomColor();
        Color randomColor6 = randomColor();
        Color randomColor7 = randomColor();
        Color randomColor8 = randomColor();
        Color invalid_randomColor9 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientBuilder RadialGradientBuilder = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColors(randomColor1, randomColor2, randomColor3, randomColor4, randomColor5, randomColor6, randomColor7, randomColor8);

        Throwable exception = assertThrows(IllegalStateException.class, () -> RadialGradientBuilder.withColors(invalid_randomColor9));

        String expectedExceptionMessage = "Gradients cannot contain more than " + Gradients.MaximumColorCount + " colors.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butAddedNullColor_usingWithColor() {
        Color invalid_nullColor = null;

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientBuilder RadialGradientBuilder = Gradients.radialGradient(randomCenterpoint, randomRadius);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> RadialGradientBuilder.withColor(invalid_nullColor));

        String expectedExceptionMessage = "Gradients cannot contain null color values.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butAddedNullColor_usingWithColors() {
        Color invalid_nullColor = null;

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientBuilder RadialGradientBuilder = Gradients.radialGradient(randomCenterpoint, randomRadius);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> RadialGradientBuilder.withColors(invalid_nullColor));

        String expectedExceptionMessage = "Gradients cannot contain null color values.";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butTooFewColorsWereAddedToBuild_withColorCountOfZero() {
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientBuilder RadialGradientBuilder = Gradients.radialGradient(randomCenterpoint, randomRadius);
        Throwable exception = assertThrows(IllegalStateException.class, RadialGradientBuilder::build);

        String expectedExceptionMessage = "Gradients must contain at least 2 colors."
                + System.lineSeparator()
                + "This gradient builder only contains the following gradients: []";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryCreateRadialGradient_butTooFewColorsWereAddedToBuild_withColorCountOfOne() {
        Color randomColor1 = randomColor();

        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        RadialGradientBuilder RadialGradientBuilder = Gradients.radialGradient(randomCenterpoint, randomRadius)
                .withColor(randomColor1);
        Throwable exception = assertThrows(IllegalStateException.class, RadialGradientBuilder::build);

        String expectedExceptionMessage = "Gradients must contain at least 2 colors."
                + System.lineSeparator()
                + "This gradient builder only contains the following gradients: [" + randomColor1 + "]";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }
}
