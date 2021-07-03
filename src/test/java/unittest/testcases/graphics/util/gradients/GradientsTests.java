package unittest.testcases.graphics.util.gradients;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.gradients.Gradients;

import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import unittest.mock.graphics.MockBoundariesDrawable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GradientsTests {

    private static float randomFloat() {
        return Maths.random(1.0f, 100.0f);
    }

    private static Pointf randomPointf() {
        return new Pointf(
                Maths.random(0.0f, 100.0f),
                Maths.random(0.0f, 100.0f)
        );
    }

    private static Boundary randomBeginningBoundary() {
        return Boundary.values()[Maths.randomInteger(0, (Boundary.values().length / 2) - 1)];
    }

    private static Boundary randomEndingBoundary() {
        return Boundary.values()[Maths.randomInteger(Boundary.values().length / 2, Boundary.values().length - 1)];
    }

    @Test
    void checkGenerateLinearGradientPaints_usingDrawableAndRandomBoundaries_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedLinearGradients[i] = Gradients.randomLinearGradient(
                                mockBoundariesDrawable,
                                randomBeginningBoundary(),
                                randomEndingBoundary()
                        );
                    }
                },
                "Errors should not be produced while generating random linear gradients (using a drawable with random boundaries)."
        );

        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateLinearGradientPaintsWithRandomAlpha_usingDrawableAndRandomBoundaries_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedLinearGradients[i] = Gradients.randomLinearGradientWithAlpha(
                                mockBoundariesDrawable,
                                randomBeginningBoundary(),
                                randomEndingBoundary()
                        );
                    }
                },
                "Errors should not be produced while generating random linear gradients with random alpha (using a drawable with random boundaries)."
        );

        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateLinearGradientPaints_usingRandomStartingAndEndingPointfs_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Pointf randomStartingPosition = randomPointf();
        Pointf randomEndingPosition = randomPointf();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedLinearGradients[i] = Gradients.randomLinearGradient(
                                randomStartingPosition,
                                randomEndingPosition
                        );
                    }
                },
                "Errors should not be produced while generating random linear gradients (using random starting and ending points)."
        );

        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateLinearGradientPaintsWithRandomAlpha_usingRandomStartingAndEndingPointfs_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Pointf randomStartingPosition = randomPointf();
        Pointf randomEndingPosition = randomPointf();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedLinearGradients[i] = Gradients.randomLinearGradientWithAlpha(
                                randomStartingPosition,
                                randomEndingPosition
                        );
                    }
                },
                "Errors should not be produced while generating random linear gradients with random alpha (using random starting and ending points)."
        );

        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateRadialGradientPaints_usingDrawable_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedRadialGradients[i] = Gradients.randomRadialGradient(mockBoundariesDrawable);
                    }
                },
                "Errors should not be produced while generating random radial gradients (using a drawable)."
        );

        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }

    @Test
    void checkGenerateRadialGradientPaintsWithRandomAlpha_usingDrawable_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedRadialGradients[i] = Gradients.randomRadialGradientWithAlpha(mockBoundariesDrawable);
                    }
                },
                "Errors should not be produced while generating random radial gradients with random alpha (using a drawable)."
        );

        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }

    @Test
    void checkGenerateRadialGradientPaints_usingRandomPointfCenterAndRandomFloatRadius_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedRadialGradients[i] = Gradients.randomRadialGradient(
                                randomCenterpoint,
                                randomRadius
                        );
                    }
                },
                "Errors should not be produced while generating random radial gradients (using a random center and radius)."
        );

        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }

    @Test
    void checkGenerateRadialGradientPaintsWithRandomAlpha_usingRandomPointfCenterAndRandomFloatRadius_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        assertDoesNotThrow(() -> {
                    for (int i = 0; i < generatedGradientCount; i++) {
                        generatedRadialGradients[i] = Gradients.randomRadialGradientWithAlpha(
                                randomCenterpoint,
                                randomRadius
                        );
                    }
                },
                "Errors should not be produced while generating random radial gradients with alpha (using a random center and radius)."
        );

        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }
}
