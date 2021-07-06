package unittest.testcases.math;

import tech.fastj.math.Maths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathsTests {

    @Test
    void checkGenerateRandoms_ensureWithinExpectedRange() {
        float minimumRandomRange = 3.5f;
        float maximumRandomRange = 7.5f;

        String assertFailMessage = "Generated random value should be within expected range.";
        for (int i = 0; i < 255; i++) {
            float generatedRandom = Maths.random(minimumRandomRange, maximumRandomRange);
            assertTrue(generatedRandom >= minimumRandomRange && generatedRandom <= maximumRandomRange, assertFailMessage);
        }
    }

    @Test
    void tryGenerateRandoms_withInvalidParameters_shouldThrowException() {
        float minimumRandomRange = 3.5f;
        float maximumRandomRange = 7.5f;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Maths.random(maximumRandomRange, minimumRandomRange));
        String expectedExceptionMessage = "The minimum must be less than the maximum.";
        String actualExceptionMessage = exception.getMessage();
        assertEquals(expectedExceptionMessage, actualExceptionMessage, "The Maths#random(min, max) method should throw an error when the maximum is less than the minimum.");
    }

    @Test
    void checkGenerateRandomIntegers_ensureWithinExpectedRange() {
        int minimumRandomRange = 13;
        int maximumRandomRange = 37;

        String assertFailMessage = "Generated random value should be within expected range.";
        for (int i = 0; i < 255; i++) {
            int generatedRandom = Maths.randomInteger(minimumRandomRange, maximumRandomRange);
            assertTrue(generatedRandom >= minimumRandomRange && generatedRandom <= maximumRandomRange, assertFailMessage);
        }
    }

    @Test
    void tryGenerateRandomIntegers_withInvalidParameters_shouldThrowException() {
        int minimumRandomRange = 13;
        int maximumRandomRange = 37;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Maths.randomInteger(maximumRandomRange, minimumRandomRange));
        String expectedExceptionMessage = "The minimum must be less than the maximum.";
        String actualExceptionMessage = exception.getMessage();
        assertEquals(expectedExceptionMessage, actualExceptionMessage, "The Maths#random(min, max) method should throw an error when the maximum is less than the minimum.");
    }

    @Test
    void checkGenerateRandomsAtEdges_ensureMatchAtLeastOneEdge() {
        float leftEdge = 3.5f;
        float rightEdge = 7.5f;

        String assertFailMessage = "Generated random value should match at least one edge.";
        for (int i = 0; i < 255; i++) {
            float generatedRandom = Maths.randomAtEdge(leftEdge, rightEdge);
            assertTrue(generatedRandom == leftEdge || generatedRandom == rightEdge, assertFailMessage);
        }
    }

    @Test
    void tryGenerateRandomAtEdge_withInvalidParameters_shouldThrowException() {
        float leftEdge = 3.5f;
        float rightEdge = 7.5f;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Maths.randomAtEdge(rightEdge, leftEdge));
        String expectedExceptionMessage = "The left edge must be less than the right edge.";
        String actualExceptionMessage = exception.getMessage();
        assertEquals(expectedExceptionMessage, actualExceptionMessage, "The Maths#randomAtEdge(leftEdge, rightEdge) method should throw an error when the right edge is less than the left edge.");
    }

    @Test
    void checkSnapFloatValueToEdge_whenExpectedIsRightEdge() {
        float leftEdge = 5.0f;
        float rightEdge_expected = 6.0f;
        float valueToSnap = 5.6f;

        float actualEdge = Maths.snap(valueToSnap, leftEdge, rightEdge_expected);
        assertEquals(rightEdge_expected, actualEdge, "Actual edge should match the expected edge (right edge).");
    }

    @Test
    void checkSnapFloatValueToEdge_whenExpectedIsLeftEdge() {
        float leftEdge_expected = 5.0f;
        float rightEdge = 6.0f;
        float valueToSnap = 5.4f;

        float actualEdge = Maths.snap(valueToSnap, leftEdge_expected, rightEdge);
        assertEquals(leftEdge_expected, actualEdge, "Actual edge should match the expected edge (left edge).");
    }

    @Test
    void checkSnapFloatValueToEdge_whenEdgesAreEquidistant() {
        float leftEdge = 5.0f;
        float rightEdge_expected = 6.0f;
        float valueToSnap = 5.5f;

        float actualEdge = Maths.snap(valueToSnap, leftEdge, rightEdge_expected);
        assertEquals(rightEdge_expected, actualEdge, "Actual edge should match the right edge.");
    }

    @Test
    void trySnapFloatValueToEdge_butLeftEdgeIsLargerThanRightEdge() {
        float leftEdgeLargerThanRightEdge = 7.0f;
        float rightEdge = 6.0f;
        float valueToSnap = 5.5f;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Maths.snap(valueToSnap, leftEdgeLargerThanRightEdge, rightEdge));
        String expectedExceptionMessage = "The left edge must be less than the right edge.";
        String actualExceptionMessage = exception.getMessage();
        assertEquals(expectedExceptionMessage, actualExceptionMessage, "The Maths#snap(num, leftEdge, rightEdge) method should throw an error when the right edge is less than the left edge.");
    }

    @Test
    void checkMagnitudeWithFloatValues() {
        assertEquals(0d, Maths.magnitude(0f, 0f), "The magnitude should be equal to 0.0.");
        assertEquals(1d, Maths.magnitude(0f, 1f), "The magnitude should be equal to 1.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(1f, 1f), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(-1f, -1f), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(25d), Maths.magnitude(3f, 4f), "The magnitude should be equal to 5.");
    }

    @Test
    void checkFloatEquals() {
        double a = 0.00000000001d;
        float a1 = 0.00000000001f;
        float a2 = 0.0001f;
        float a3 = 0.0002f;
        float a4 = 0.0003f;
        float a5 = 0.0006f;
        float a6 = 0.1f;

        assertTrue(Maths.floatEquals((float) a, a1), "The two floats should be equal -- their difference is less than " + Maths.FloatPrecision + ".");
        assertTrue(Maths.floatEquals((float) a, a2), "The two floats should be equal -- their difference is less than " + Maths.FloatPrecision + ".");
        assertFalse(Maths.floatEquals((float) a, a6), "The two floats should not be equal -- their difference is more than " + Maths.FloatPrecision + ".");
        assertTrue(Maths.floatEquals(a2, a3), "The two floats should be equal -- their difference is less than " + Maths.FloatPrecision + ".");
        assertTrue(Maths.floatEquals(a3, a4), "The two floats should be equal -- their difference is less than " + Maths.FloatPrecision + ".");
        assertTrue(Maths.floatEquals(a2, a4), "The two floats should be equal -- their difference is less than " + Maths.FloatPrecision + ".");
        assertFalse(Maths.floatEquals(a2, a5), "The two floats should not be equal -- their difference is equal to " + Maths.FloatPrecision + ".");
        assertFalse(Maths.floatEquals(a5, a6), "The two floats should not be equal -- their difference is more than " + Maths.FloatPrecision + ".");
    }

    @Test
    void checkLerpValues() {
        assertEquals(5f, Maths.lerp(5f, 2f, 0f), "The lerped value should be 5f.");
        assertEquals(-1f, Maths.lerp(5f, 2f, 2f), "The lerped value should be -1f.");
    }

    @Test
    void checkValueWithinRange_withFloatingPointValues() {
        float value = 35f;
        float minRange = 13f;
        float maxRange = 37f;
        float lowerThanRange = 5f;
        float higherThanRange = 40f;

        float actualValueInRange = Maths.withinRange(value, minRange, maxRange);
        float actualValueLowerThanRange = Maths.withinRange(lowerThanRange, minRange, maxRange);
        float actualValueHigherThanRange = Maths.withinRange(higherThanRange, minRange, maxRange);
        assertEquals(value, actualValueInRange, "The value should be within the given range -- as such, it should be the same value that is returned.");
        assertEquals(minRange, actualValueLowerThanRange, "The value should be lower than the given range -- as such, the minimum in the range should be returned.");
        assertEquals(maxRange, actualValueHigherThanRange, "The value should be higher than the given range -- as such, the maximum in the range should be returned.");
    }

    @Test
    void checkValueWithinRange_withIntegerValues() {
        int value = 35;
        int minRange = 13;
        int maxRange = 37;
        int lowerThanRange = 5;
        int higherThanRange = 40;

        int actualValueInRange = Maths.withinIntegerRange(value, minRange, maxRange);
        int actualValueLowerThanRange = Maths.withinIntegerRange(lowerThanRange, minRange, maxRange);
        int actualValueHigherThanRange = Maths.withinIntegerRange(higherThanRange, minRange, maxRange);
        assertEquals(value, actualValueInRange, "The value should be within the given range -- as such, it should be the same value that is returned.");
        assertEquals(minRange, actualValueLowerThanRange, "The value should be lower than the given range -- as such, the minimum in the range should be returned.");
        assertEquals(maxRange, actualValueHigherThanRange, "The value should be higher than the given range -- as such, the maximum in the range should be returned.");
    }

    @Test
    void tryValueWithinRange_withFloatingPointValues_butMinimumIsHigherThanMaximum() {
        float value = 35f;
        float minRangeHigherThanMaxRange = 37f;
        float maxRange = 13f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.withinRange(value, minRangeHigherThanMaxRange, maxRange));
        String expectedErrorMessage = "The minimum must be less than the maximum.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void tryValueWithinRange_withIntegerValues_butMinimumIsHigherThanMaximum() {
        int value = 35;
        int minRangeHigherThanMaxRange = 37;
        int maxRange = 13;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.withinIntegerRange(value, minRangeHigherThanMaxRange, maxRange));
        String expectedErrorMessage = "The minimum must be less than the maximum.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void checkNormalization_usingFloatingPointMath() {
        float value = 5f;
        float minimum = 0f;
        float maximum = 25f;

        float expectedNormalizedValue = 0.2f;
        float actualNormalizedValue = Maths.normalize(value, minimum, maximum);
        assertEquals(expectedNormalizedValue, actualNormalizedValue, "The normalized value should match the expected normalized value.");
    }

    @Test
    void tryNormalization_usingFloatingPointMath_butMinimumIsLargerThanMaximum() {
        float value = 5f;
        float minimumHigherThanMaximum = 37f;
        float maximum = 25f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.normalize(value, minimumHigherThanMaximum, maximum));
        String expectedErrorMessage = "The provided number must be more than the minimum number.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void tryNormalization_usingFloatingPointMath_butMaximumIsSmallerThanMinimum() {
        float value = 5f;
        float minimum = 0f;
        float maximumLowerThanMinimum = -13f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.normalize(value, minimum, maximumLowerThanMinimum));
        String expectedErrorMessage = "The provided number must be less than the maximum number.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void checkDenormalization_usingFloatingPointMath() {
        float normalizedValue = 0.2f;
        float minimum = 0f;
        float maximum = 25f;

        float expectedDenormalizedValue = 5f;
        float actualDenormalizedValue = Maths.denormalize(normalizedValue, minimum, maximum);
        assertEquals(expectedDenormalizedValue, actualDenormalizedValue, "The de-normalized value should match the expected de-normalized value.");
    }

    @Test
    void tryDenormalization_usingFloatingPointMath_butMinimumIsLargerThanMaximum() {
        float normalizedValue = 0.2f;
        float minimumHigherThanMaximum = 37f;
        float maximum = 25f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.denormalize(normalizedValue, minimumHigherThanMaximum, maximum));
        String expectedErrorMessage = "The minimum must be less than the maximum.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void tryDenormalization_usingFloatingPointMath_butProvidedNumberIsSmallerThanZero() {
        float normalizedValueLessThanZero = -37.13f;
        float minimum = 0f;
        float maximum = 25f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.denormalize(normalizedValueLessThanZero, minimum, maximum));
        String expectedErrorMessage = "The normalized number must be more than 0.0f.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void tryDenormalization_usingFloatingPointMath_butProvidedNumberIsLargerThanOne() {
        float normalizedValueMoreThanOne = 13.37f;
        float minimum = 0f;
        float maximum = 25f;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Maths.denormalize(normalizedValueMoreThanOne, minimum, maximum));
        String expectedErrorMessage = "The normalized number must be less than 1.0f.";
        assertEquals(expectedErrorMessage, exception.getMessage(), "The function call should throw an error, and the error message should match the expected error message.");
    }

    @Test
    void checkInverseLerpValues() {
        float testOne = Maths.inverseLerp(10f, 20f, 10.0f);
        float testTwo = Maths.inverseLerp(10f, 20f, 19.6f);
        float testThree = Maths.inverseLerp(10f, 20f, 20.0f);

        assertEquals(0.0f, testOne, "Expected value was 0.0, instead got " + testOne);
        assertEquals(0.96000004f, testTwo, "Expected value was 0.96000004, instead got " + testTwo);
        assertEquals(1.0f, testThree, "Expected value was 1.0, instead got " + testThree);
    }
}
