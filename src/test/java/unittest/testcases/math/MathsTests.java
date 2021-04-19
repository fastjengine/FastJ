package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MathsTests {

    @Test
    public void checkGenerateRandoms_ensureWithinExpectedRange() {
        float minimumRandomRange = 3.5f;
        float maximumRandomRange = 7.5f;

        String assertFailMessage = "Generated random value should be within expected range.";
        for (int i = 0; i < 255; i++) {
            float generatedRandom = Maths.random(minimumRandomRange, maximumRandomRange);
            assertTrue(generatedRandom >= minimumRandomRange && generatedRandom <= maximumRandomRange, assertFailMessage);
        }
    }

    @Test
    public void checkGenerateRandomIntegers_ensureWithinExpectedRange() {
        int minimumRandomRange = 13;
        int maximumRandomRange = 37;

        String assertFailMessage = "Generated random value should be within expected range.";
        for (int i = 0; i < 255; i++) {
            int generatedRandom = Maths.randomInteger(minimumRandomRange, maximumRandomRange);
            assertTrue(generatedRandom >= minimumRandomRange && generatedRandom <= maximumRandomRange, assertFailMessage);
        }
    }

    @Test
    public void checkGenerateRandomsAtEdges_ensureMatchAtLeastOneEdge() {
        float leftEdge = 3.5f;
        float rightEdge = 7.5f;

        String assertFailMessage = "Generated random value should match at least one edge.";
        for (int i = 0; i < 255; i++) {
            float generatedRandom = Maths.randomAtEdge(leftEdge, rightEdge);
            assertTrue(generatedRandom == leftEdge || generatedRandom == rightEdge, assertFailMessage);
        }
    }

    @Test
    public void checkSnapFloatValueToEdge_whenExpectedIsRightEdge() {
        float leftEdge = 5.0f;
        float rightEdge_expected = 6.0f;
        float valueToSnap = 5.6f;

        float actualEdge = Maths.snap(valueToSnap, leftEdge, rightEdge_expected);
        assertEquals(rightEdge_expected, actualEdge, "Actual edge should match the expected edge (right edge).");
    }

    @Test
    public void checkSnapFloatValueToEdge_whenExpectedIsLeftEdge() {
        float leftEdge_expected = 5.0f;
        float rightEdge = 6.0f;
        float valueToSnap = 5.4f;

        float actualEdge = Maths.snap(valueToSnap, leftEdge_expected, rightEdge);
        assertEquals(leftEdge_expected, actualEdge, "Actual edge should match the expected edge (left edge).");
    }

    @Test
    public void checkSnapFloatValueToEdge_whenEdgesAreEquidistant() {
        float leftEdge = 5.0f;
        float rightEdge_expected = 6.0f;
        float valueToSnap = 5.5f;

        float actualEdge = Maths.snap(valueToSnap, leftEdge, rightEdge_expected);
        assertEquals(rightEdge_expected, actualEdge, "Actual edge should match the right edge.");
    }

    @Test
    public void checkMagnitudeWithFloatValues() {
        assertEquals(0d, Maths.magnitude(0f, 0f), "The magnitude should be equal to 0.0.");
        assertEquals(1d, Maths.magnitude(0f, 1f), "The magnitude should be equal to 1.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(1f, 1f), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(-1f, -1f), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(25d), Maths.magnitude(3f, 4f), "The magnitude should be equal to 5.");
    }

    @Test
    public void checkMagnitudeWithPointfObjects() {
        assertEquals(0f, Maths.magnitude(new Pointf()), "The magnitude should be equal to 0.0.");
        assertEquals(1f, Maths.magnitude(new Pointf(0f, 1f)), "The magnitude should be equal to 1.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(new Pointf(1f)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(new Pointf(-1f)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(25d), Maths.magnitude(new Pointf(3f, 4f)), "The magnitude should be equal to 5.");
    }

    @Test
    public void checkMagnitudeWithPointObjects() {
        assertEquals(0f, Maths.magnitude(new Point()), "The magnitude should be equal to 0.0.");
        assertEquals(1f, Maths.magnitude(new Point(0, 1)), "The magnitude should be equal to 1.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(new Point(1)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(2d), Maths.magnitude(new Point(-1)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals((float) Math.sqrt(25d), Maths.magnitude(new Point(3, 4)), "The magnitude should be equal to 5.");
    }

    @Test
    public void checkFloatEquals() {
        double a = 0.00000000001d;
        float a1 = 0.00000000001f;
        float a2 = 0.00001f;
        float a3 = 0.00002f;
        float a4 = 0.1f;

        assertTrue(Maths.floatEquals((float) a, a1), "The two floats should be equal -- their difference is less than 0.000001f.");
        assertTrue(Maths.floatEquals((float) a, a2), "The two floats should be equal -- their difference is less than 0.000001f.");
        assertFalse(Maths.floatEquals((float) a, a4), "The two floats should not be equal -- their difference is more than 0.000001f.");
        assertFalse(Maths.floatEquals(a2, a3), "The two floats should not be equal -- their difference is equal to 0.000001f.");
    }

    @Test
    public void checkLerpValues() {
        assertEquals(5, Maths.lerp(5f, 2f, 0f), "The lerped value should be 5f.");
        assertEquals(-1, Maths.lerp(5f, 2f, 2f), "The lerped value should be -1f.");
    }
}
