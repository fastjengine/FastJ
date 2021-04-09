package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MathsTests {

    @Test
    public void checkGenerateRandoms_ensureWithinExpectedRange() {
        double minimumRandomRange = 3.5d;
        double maximumRandomRange = 7.5d;

        String assertFailMessage = "Generated random value should be within expected range.";
        for (int i = 0; i < 255; i++) {
            double generatedRandom = Maths.random(minimumRandomRange, maximumRandomRange);
            assertTrue(generatedRandom >= minimumRandomRange && generatedRandom <= maximumRandomRange, assertFailMessage);
        }
    }

    @Test
    public void checkGenerateRandoms_ensureMatchAtLeastOneEdge() {
        double leftEdge = 3.5d;
        double rightEdge = 7.5d;

        String assertFailMessage = "Generated random value should match at least one edge.";
        for (int i = 0; i < 255; i++) {
            double generatedRandom = Maths.randomAtEdge(leftEdge, rightEdge);
            assertTrue(generatedRandom == leftEdge || generatedRandom == rightEdge, assertFailMessage);
        }
    }

    @Test
    public void checkSnapDoubleValueToEdge_whenExpectedIsRightEdge() {
        double leftEdge = 5.0d;
        double rightEdge_expected = 6.0d;
        double valueToSnap = 5.6d;

        double actualEdge = Maths.snap(valueToSnap, leftEdge, rightEdge_expected);
        assertEquals(rightEdge_expected, actualEdge, "Actual edge should match the expected edge (right edge).");
    }

    @Test
    public void checkSnapDoubleValueToEdge_whenExpectedIsLeftEdge() {
        double leftEdge_expected = 5.0d;
        double rightEdge = 6.0d;
        double valueToSnap = 5.4d;

        double actualEdge = Maths.snap(valueToSnap, leftEdge_expected, rightEdge);
        assertEquals(leftEdge_expected, actualEdge, "Actual edge should match the expected edge (left edge).");
    }

    @Test
    public void checkSnapDoubleValueToEdge_whenEdgesAreEquidistant() {
        double leftEdge = 5.0d;
        double rightEdge_expected = 6.0d;
        double valueToSnap = 5.5d;

        double actualEdge = Maths.snap(valueToSnap, leftEdge, rightEdge_expected);
        assertEquals(rightEdge_expected, actualEdge, "Actual edge should match the right edge.");
    }

    @Test
    public void checkMagnitudeWithDoubleValues() {
        assertEquals(0d, Maths.magnitude(0, 0), "The magnitude should be equal to 0.0.");
        assertEquals(1d, Maths.magnitude(0, 1), "The magnitude should be equal to 1.0.");
        assertEquals(Math.sqrt(2), Maths.magnitude(1, 1), "The magnitude should be equal to the square root of 2.0.");
        assertEquals(Math.sqrt(2), Maths.magnitude(-1, -1), "The magnitude should be equal to the square root of 2.0.");
        assertEquals(Math.sqrt(25), Maths.magnitude(3, 4), "The magnitude should be equal to 5.");
    }

    @Test
    public void checkMagnitudeWithPointfObjects() {
        assertEquals(0d, Maths.magnitude(new Pointf()), "The magnitude should be equal to 0.0.");
        assertEquals(1d, Maths.magnitude(new Pointf(0, 1)), "The magnitude should be equal to 1.0.");
        assertEquals(Math.sqrt(2), Maths.magnitude(new Pointf(1)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals(Math.sqrt(2), Maths.magnitude(new Pointf(-1)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals(Math.sqrt(25), Maths.magnitude(new Pointf(3, 4)), "The magnitude should be equal to 5.");
    }

    @Test
    public void checkMagnitudeWithPointObjects() {
        assertEquals(0d, Maths.magnitude(new Point()), "The magnitude should be equal to 0.0.");
        assertEquals(1d, Maths.magnitude(new Point(0, 1)), "The magnitude should be equal to 1.0.");
        assertEquals(Math.sqrt(2), Maths.magnitude(new Point(1)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals(Math.sqrt(2), Maths.magnitude(new Point(-1)), "The magnitude should be equal to the square root of 2.0.");
        assertEquals(Math.sqrt(25), Maths.magnitude(new Point(3, 4)), "The magnitude should be equal to 5.");
    }
}
