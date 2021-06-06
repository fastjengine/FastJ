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

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedLinearGradients[i] = Gradients.randomLinearGradient(
                    mockBoundariesDrawable,
                    randomBeginningBoundary(),
                    randomEndingBoundary()
            );
        }
        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateLinearGradientPaintsWithRandomAlpha_usingDrawableAndRandomBoundaries_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedLinearGradients[i] = Gradients.randomLinearGradientWithAlpha(
                    mockBoundariesDrawable,
                    randomBeginningBoundary(),
                    randomEndingBoundary()
            );
        }
        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateLinearGradientPaints_usingRandomStartingAndEndingPointfs_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Pointf randomStartingPosition = randomPointf();
        Pointf randomEndingPosition = randomPointf();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedLinearGradients[i] = Gradients.randomLinearGradient(
                    randomStartingPosition,
                    randomEndingPosition
            );
        }
        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateLinearGradientPaintsWithRandomAlpha_usingRandomStartingAndEndingPointfs_shouldNotFail() {
        int generatedGradientCount = 255;
        LinearGradientPaint[] generatedLinearGradients = new LinearGradientPaint[generatedGradientCount];
        Pointf randomStartingPosition = randomPointf();
        Pointf randomEndingPosition = randomPointf();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedLinearGradients[i] = Gradients.randomLinearGradientWithAlpha(
                    randomStartingPosition,
                    randomEndingPosition
            );
        }
        System.out.println("Generated linear gradients: " + Arrays.toString(generatedLinearGradients));
    }

    @Test
    void checkGenerateRadialGradientPaints_usingDrawable_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedRadialGradients[i] = Gradients.randomRadialGradient(mockBoundariesDrawable);
        }
        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }

    @Test
    void checkGenerateRadialGradientPaintsWithRandomAlpha_usingDrawable_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedRadialGradients[i] = Gradients.randomRadialGradientWithAlpha(mockBoundariesDrawable);
        }
        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }

    @Test
    void checkGenerateRadialGradientPaints_usingRandomPointfCenterAndRandomFloatRadius_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedRadialGradients[i] = Gradients.randomRadialGradient(
                    randomCenterpoint,
                    randomRadius
            );
        }
        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }

    @Test
    void checkGenerateRadialGradientPaintsWithRandomAlpha_usingRandomPointfCenterAndRandomFloatRadius_shouldNotFail() {
        int generatedGradientCount = 255;
        RadialGradientPaint[] generatedRadialGradients = new RadialGradientPaint[generatedGradientCount];
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        for (int i = 0; i < generatedGradientCount; i++) {
            generatedRadialGradients[i] = Gradients.randomRadialGradientWithAlpha(
                    randomCenterpoint,
                    randomRadius
            );
        }
        System.out.println("Generated radial gradients: " + Arrays.toString(generatedRadialGradients));
    }
}
