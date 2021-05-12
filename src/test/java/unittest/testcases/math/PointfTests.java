package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;

import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointfTests {

    @Test
    void checkPointfCreation_withNoConstructorParams() {
        Pointf ptf = new Pointf();
        assertEquals(0f, ptf.x, "The x value of the Pointf should default to 0f.");
        assertEquals(0f, ptf.y, "The y value of the Pointf should default to 0f.");
    }

    @Test
    void checkPointfCreation_withOneConstructorParam() {
        Pointf ptf = new Pointf(5f);
        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfCreation_withTwoConstructorParams() {
        Pointf ptf = new Pointf(3f, 4f);
        assertEquals(3f, ptf.x, "The x value of the Pointf should equal 3f.");
        assertEquals(4f, ptf.y, "The y value of the Pointf should equal 4f.");
    }

    @Test
    void checkPointfCreation_withPointfObjectParam() {
        Pointf ptf = new Pointf(3f, 4f);
        Pointf ptf2 = new Pointf(ptf);

        assertEquals(3f, ptf2.x, "The x value of the Pointf should equal 3f.");
        assertEquals(4f, ptf2.y, "The y value of the Pointf should equal 4f.");
    }

    @Test
    void checkPointfAddition_withFloatValues() {
        Pointf ptf = new Pointf();
        ptf.add(5f);

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfAddition_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        ptf.add(ptf2);

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfSubtraction_withFloatValues() {
        Pointf ptf = new Pointf();
        ptf.subtract(5f);

        assertEquals(-5f, ptf.x, "The x value of the Pointf should equal -5f.");
        assertEquals(-5f, ptf.y, "The y value of the Pointf should equal -5f.");
    }

    @Test
    void checkPointfSubtraction_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        ptf.subtract(ptf2);

        assertEquals(-5f, ptf.x, "The x value of the Pointf should equal -5f.");
        assertEquals(-5f, ptf.y, "The y value of the Pointf should equal -5f.");
    }

    @Test
    void checkPointfMultiplication_withFloatValues() {
        Pointf ptf = new Pointf(1f);
        ptf.multiply(5f);

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfMultiplication_withPointfObjects() {
        Pointf ptf = new Pointf(1f);
        Pointf ptf2 = new Pointf(5f);
        ptf.multiply(ptf2);

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfDivision_withFloatValues() {
        Pointf ptf = new Pointf(25f);
        ptf.divide(5f);

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfDivision_withPointfObjects() {
        Pointf ptf = new Pointf(25f);
        Pointf ptf2 = new Pointf(5f);
        ptf.divide(ptf2);

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkArithmeticChaining_withPointfObjectsAndFloatValues() {
        Pointf ptf = new Pointf()
                .add(2f)                                    // (2f, 2f)
                .add(new Pointf(3f))                    // (5f, 5f)
                .multiply(new Pointf(3f, 4f))    // (15f, 20f)
                .multiply(2f)                               // (30f, 40f)
                .subtract(15f)                              // (15f, 25f)
                .subtract(new Pointf(-5f, 5f))   // (20f, 20f)
                .divide(new Pointf(2f))                 // (10f, 10f)
                .divide(2f);                                // (5f, 5f)

        assertEquals(5f, ptf.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, ptf.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void checkPointfCopyingForEquality() {
        Pointf original = new Pointf(13f, 37f);
        Pointf copy = original.copy();

        assertEquals(original, copy, "The two Pointf objects should have the same x and y values -- (13f, 37f).");
    }

    @Test
    void checkPointfResettingForEquality_withPointfAtOrigin() {
        Pointf ptf = new Pointf(13f, 37f);
        ptf.reset();

        assertEquals(new Pointf(), ptf, "The Pointf's x and y values should have been reset to (0f, 0f).");
    }

    @Test
    void checkPointfSetting() {
        Pointf ptf = new Pointf();
        ptf.set(13f, 37f);

        assertEquals(13f, ptf.x, "The x value of the Pointf should equal 13f.");
        assertEquals(37f, ptf.y, "The y value of the Pointf should equal 37f.");
    }

    @Test
    void checkPointfMagnitude_usingFloatingPointMagnitude() {
        Pointf pt = new Pointf(2f, 2f);
        float expectedPtMagnitude = (float) Math.sqrt(8f);
        float actualPtMagnitude = pt.magnitude();
        assertEquals(expectedPtMagnitude, actualPtMagnitude, "The first Pointf's magnitude should equal the expected magnitude of (float) √8f.");

        Pointf pt2 = new Pointf(3f, 3f);
        float expectedPt2Magnitude = (float) Math.sqrt(18f);
        float actualPt2Magnitude = pt2.magnitude();
        assertEquals(expectedPt2Magnitude, actualPt2Magnitude, "The second Pointf's magnitude should equal the expected magnitude of (float) √18f.");
    }

    @Test
    void checkPointfSquareMagnitude() {
        Pointf pt = new Pointf(13f, 37f);
        float expectedSquareMagnitude = 1538f;
        float actualSquareMagnitude = pt.squareMagnitude();
        assertEquals(expectedSquareMagnitude, actualSquareMagnitude, "The Pointf's square magnitude should equal the expected magnitude of 1538f.");
    }

    @Test
    void checkPointfNormalization() {
        float expectedNormalizedX = 0.3314859950125598f;
        float expectedNormalizedY = 0.9434601396511317f;

        Pointf pt = new Pointf(13, 37);
        Pointf expectedNormalization = new Pointf(expectedNormalizedX, expectedNormalizedY);
        Pointf actualNormalization = pt.normalized();
        assertEquals(expectedNormalization, actualNormalization, String.format("The Pointf's normalized value should equal the expected values of %s", expectedNormalization));
    }

    @Test
    void checkEqualsAgainstPoint() {
        Pointf ptf = new Pointf(13f, 37f);
        Point pt = new Point(13, 37);

        assertTrue(ptf.equalsPoint(pt), "The Pointf and Point should be equal in their x and y values.");
    }

    @Test
    void checkIntersectionsWithRectangleObject() {
        Pointf ptf = new Pointf(13f, 37f);
        Rectangle rect = new Rectangle(0, 0, 20, 40);

        assertTrue(ptf.intersects(rect), "Pointf should intersect with Rectangle.");
    }

    @Test
    void checkIntersectionsWithRectangle2DFloatObject() {
        Pointf ptf = new Pointf(13f, 37f);
        Rectangle2D.Float rectf = new Rectangle2D.Float(0f, 0f, 20f, 40f);

        assertTrue(ptf.intersects(rectf), "Pointf should intersect with Rectangle2D.Float.");
    }

    @Test
    void checkIntersectionsWithRectangle2DDoubleObject() {
        Pointf ptf = new Pointf(13f, 37f);
        Rectangle2D.Double rectd = new Rectangle2D.Double(0d, 0d, 20d, 40d);

        assertTrue(ptf.intersects(rectd), "Pointf should intersect with Rectangle2D.Double.");
    }

    @Test
    void checkIntersectionWithPath2DFloatObject() {
        Pointf ptf = new Pointf(13f, 37f);

        Path2D.Float pathf = new Path2D.Float();
        pathf.moveTo(0f, 0f);
        pathf.lineTo(20f, 0f);
        pathf.lineTo(20f, 40f);
        pathf.lineTo(0f, 40f);
        pathf.closePath();

        assertTrue(ptf.intersects(pathf), "Pointf should intersect with Path2D.Float.");
    }

    @Test
    void checkIntersectionWithPath2DDoubleObject() {
        Pointf ptf = new Pointf(13f, 37f);

        Path2D.Double pathd = new Path2D.Double();
        pathd.moveTo(0d, 0d);
        pathd.lineTo(20d, 0d);
        pathd.lineTo(20d, 40d);
        pathd.lineTo(0d, 40d);
        pathd.closePath();

        assertTrue(ptf.intersects(pathd), "Pointf should intersect with Path2D.Double.");
    }

    @Test
    void checkIntersectionWithGeneralPathObject() {
        Pointf ptf = new Pointf(13f, 37f);

        GeneralPath pathg = new GeneralPath();
        pathg.moveTo(0f, 0f);
        pathg.lineTo(20f, 0f);
        pathg.lineTo(20f, 40f);
        pathg.lineTo(0f, 40f);
        pathg.closePath();

        assertTrue(ptf.intersects(pathg), "Pointf should intersect with GeneralPath.");
    }


    @Test
    void static_checkPointfAddition_withFloatValues() {
        Pointf ptf = new Pointf();
        Pointf added = Pointf.add(ptf, 5f);

        assertEquals(5f, added.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, added.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void static_checkPointfAddition_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        Pointf added = Pointf.add(ptf, ptf2);

        assertEquals(5f, added.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, added.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void static_checkPointfSubtraction_withFloatValues() {
        Pointf ptf = new Pointf();
        Pointf subtracted = Pointf.subtract(ptf, 5f);

        assertEquals(-5f, subtracted.x, "The x value of the Pointf should equal -5f.");
        assertEquals(-5f, subtracted.y, "The y value of the Pointf should equal -5f.");
    }

    @Test
    void static_checkPointfSubtraction_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        Pointf subtracted = Pointf.subtract(ptf, ptf2);

        assertEquals(-5f, subtracted.x, "The x value of the Pointf should equal -5f.");
        assertEquals(-5f, subtracted.y, "The y value of the Pointf should equal -5f.");
    }

    @Test
    void static_checkPointfMultiplication_withFloatValues() {
        Pointf ptf = new Pointf(1f);
        Pointf multiplied = Pointf.multiply(ptf, 5f);

        assertEquals(5f, multiplied.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, multiplied.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void static_checkPointfMultiplication_withPointfObjects() {
        Pointf ptf = new Pointf(1f);
        Pointf ptf2 = new Pointf(5f);
        Pointf multiplied = Pointf.multiply(ptf, ptf2);

        assertEquals(5f, multiplied.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, multiplied.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void static_checkPointfDivision_withFloatValues() {
        Pointf ptf = new Pointf(25f);
        Pointf divided = Pointf.divide(ptf, 5f);

        assertEquals(5f, divided.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, divided.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void static_checkPointfDivision_withPointfObjects() {
        Pointf ptf = new Pointf(25f);
        Pointf ptf2 = new Pointf(5f);
        Pointf divided = Pointf.divide(ptf, ptf2);

        assertEquals(5f, divided.x, "The x value of the Pointf should equal 5f.");
        assertEquals(5f, divided.y, "The y value of the Pointf should equal 5f.");
    }

    @Test
    void static_checkDotProduct() {
        Pointf ptf = new Pointf(25f);
        Pointf ptf2 = new Pointf(5f);

        float expectedDotProduct = 250f;
        float actualDotProduct = Pointf.dot(ptf, ptf2);
        assertEquals(expectedDotProduct, actualDotProduct, "The resulting dot product of the two Pointfs should equal the expected dot product of 250f.");
    }

    @Test
    void static_checkCrossProduct() {
        Pointf pt = new Pointf(25.5f, 5.25f);
        Pointf pt2 = new Pointf(13.37f, 37.13f);

        float expectedCrossProduct = 876.6225f;
        float actualCrossProduct = Pointf.cross(pt, pt2);
        assertEquals(expectedCrossProduct, actualCrossProduct, "The resulting cross product of the two Pointfs should equal the expected cross product of 876.6225f.");
    }
}
