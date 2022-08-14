package unittest.testcases.graphics.gradients;

import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;

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
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomLinearGradient(mockBoundariesDrawable, randomBeginningBoundary(), randomEndingBoundary());
                }
            },
            "Errors should not be produced while generating random linear gradients (using a drawable with random boundaries)."
        );
    }

    @Test
    void checkGenerateLinearGradientPaintsWithRandomAlpha_usingDrawableAndRandomBoundaries_shouldNotFail() {
        int generatedGradientCount = 255;
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomLinearGradientWithAlpha(mockBoundariesDrawable, randomBeginningBoundary(), randomEndingBoundary());
                }
            },
            "Errors should not be produced while generating random linear gradients with random alpha (using a drawable with random boundaries)."
        );
    }

    @Test
    void checkGenerateLinearGradientPaints_usingRandomStartingAndEndingPointfs_shouldNotFail() {
        int generatedGradientCount = 255;
        Pointf randomStartingPosition = randomPointf();
        Pointf randomEndingPosition = randomPointf();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomLinearGradient(randomStartingPosition, randomEndingPosition);
                }
            },
            "Errors should not be produced while generating random linear gradients (using random starting and ending points)."
        );
    }

    @Test
    void checkGenerateLinearGradientPaintsWithRandomAlpha_usingRandomStartingAndEndingPointfs_shouldNotFail() {
        int generatedGradientCount = 255;
        Pointf randomStartingPosition = randomPointf();
        Pointf randomEndingPosition = randomPointf();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomLinearGradientWithAlpha(randomStartingPosition, randomEndingPosition);
                }
            },
            "Errors should not be produced while generating random linear gradients with random alpha (using random starting and ending points)."
        );
    }

    @Test
    void checkGenerateRadialGradientPaints_usingDrawable_shouldNotFail() {
        int generatedGradientCount = 255;
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomRadialGradient(mockBoundariesDrawable);
                }
            },
            "Errors should not be produced while generating random radial gradients (using a drawable)."
        );
    }

    @Test
    void checkGenerateRadialGradientPaintsWithRandomAlpha_usingDrawable_shouldNotFail() {
        int generatedGradientCount = 255;
        Drawable mockBoundariesDrawable = new MockBoundariesDrawable();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomRadialGradientWithAlpha(mockBoundariesDrawable);
                }
            },
            "Errors should not be produced while generating random radial gradients with random alpha (using a drawable)."
        );
    }

    @Test
    void checkGenerateRadialGradientPaints_usingRandomPointfCenterAndRandomFloatRadius_shouldNotFail() {
        int generatedGradientCount = 255;
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomRadialGradient(randomCenterpoint, randomRadius);
                }
            },
            "Errors should not be produced while generating random radial gradients (using a random center and radius)."
        );
    }

    @Test
    void checkGenerateRadialGradientPaintsWithRandomAlpha_usingRandomPointfCenterAndRandomFloatRadius_shouldNotFail() {
        int generatedGradientCount = 255;
        Pointf randomCenterpoint = randomPointf();
        float randomRadius = randomFloat();

        assertDoesNotThrow(() -> {
                for (int i = 0; i < generatedGradientCount; i++) {
                    Gradients.randomRadialGradientWithAlpha(randomCenterpoint, randomRadius);
                }
            },
            "Errors should not be produced while generating random radial gradients with alpha (using a random center and radius)."
        );
    }
}
