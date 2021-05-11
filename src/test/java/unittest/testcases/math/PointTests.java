package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointTests {

    @Test
    void checkPointCreation_withNoConstructorParams() {
        Point pt = new Point();
        assertEquals(0, pt.x, "The x value of the Point should default to 0.");
        assertEquals(0, pt.y, "The y value of the Point should default to 0.");
    }

    @Test
    void checkPointCreation_withOneConstructorParam() {
        Point pt = new Point(5);
        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointCreation_withTwoConstructorParams() {
        Point pt = new Point(3, 4);
        assertEquals(3, pt.x, "The x value of the Point should equal 3.");
        assertEquals(4, pt.y, "The y value of the Point should equal 4.");
    }

    @Test
    void checkPointCreation_withPointObjectParam() {
        Point pt = new Point(3, 4);
        Point pt2 = new Point(pt);

        assertEquals(3, pt2.x, "The x value of the Point should equal 3.");
        assertEquals(4, pt2.y, "The y value of the Point should equal 4.");
    }

    @Test
    void checkPointAddition_withIntegerValues() {
        Point pt = new Point();
        pt.add(5);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointAddition_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        pt.add(pt2);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointSubtraction_withIntegerValues() {
        Point pt = new Point();
        pt.subtract(5);

        assertEquals(-5, pt.x, "The x value of the Point should equal -5.");
        assertEquals(-5, pt.y, "The y value of the Point should equal -5.");
    }

    @Test
    void checkPointSubtraction_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        pt.subtract(pt2);

        assertEquals(-5, pt.x, "The x value of the Point should equal -5.");
        assertEquals(-5, pt.y, "The y value of the Point should equal -5.");
    }

    @Test
    void checkPointMultiplication_withIntegerValues() {
        Point pt = new Point(1);
        pt.multiply(5);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointMultiplication_withPointObjects() {
        Point pt = new Point(1);
        Point pt2 = new Point(5);
        pt.multiply(pt2);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointDivision_withIntegerValues() {
        Point pt = new Point(25);
        pt.divide(5);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointDivision_withPointObjects() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);
        pt.divide(pt2);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkArithmeticChaining_withPointObjectsAndIntegerValues() {
        Point pt = new Point()
                .add(2)                                // (2, 2)
                .add(new Point(3))                 // (5, 5)
                .multiply(new Point(3, 4))  // (15, 20)
                .multiply(2)                           // (30, 40)
                .subtract(15)                          // (15, 25)
                .subtract(new Point(-5, 5)) // (20, 20)
                .divide(new Point(2))              // (10, 10)
                .divide(2);                            // (5, 5)

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    void checkPointCopyingForEquality() {
        Point original = new Point(13, 37);
        Point copy = original.copy();

        assertEquals(original, copy, "The two points should have the same x and y values -- (13, 37).");
    }

    @Test
    void checkPointResettingForEquality_withPointAtOrigin() {
        Point pt = new Point(13, 37);
        pt.reset();

        assertEquals(new Point(), pt, "The point's x and y values should have been reset to (0, 0).");
    }

    @Test
    void checkPointSetting() {
        Point pt = new Point();
        pt.set(13, 37);

        assertEquals(13, pt.x, "The x value of the Point should equal 13.");
        assertEquals(37, pt.y, "The y value of the Point should equal 37.");
    }

    @Test
    void checkPointMagnitude_usingIntegerMagnitude() {
        Point pt = new Point(2, 2);
        int expectedPtMagnitude = (int) Math.sqrt(8);
        int actualPtMagnitude = pt.integerMagnitude();
        assertEquals(expectedPtMagnitude, actualPtMagnitude, "The first Point's magnitude should equal the expected magnitude of (int) √8.");

        Point pt2 = new Point(3, 3);
        int expectedPt2Magnitude = (int) Math.sqrt(18);
        int actualPt2Magnitude = pt2.integerMagnitude();
        assertEquals(expectedPt2Magnitude, actualPt2Magnitude, "The second Point's magnitude should equal the expected magnitude of (int) √18.");
    }

    @Test
    void checkPointMagnitude_usingFloatingPointMagnitude() {
        Point pt = new Point(2, 2);
        float expectedPtMagnitude = (float) Math.sqrt(8);
        float actualPtMagnitude = pt.magnitude();
        assertEquals(expectedPtMagnitude, actualPtMagnitude, "The first Point's magnitude should equal the expected magnitude of (float) √8.");

        Point pt2 = new Point(3, 3);
        float expectedPt2Magnitude = (float) Math.sqrt(18f);
        float actualPt2Magnitude = pt2.magnitude();
        assertEquals(expectedPt2Magnitude, actualPt2Magnitude, "The second Point's magnitude should equal the expected magnitude of (float) √18.");
    }

    @Test
    void checkPointSquareMagnitude() {
        Point pt = new Point(13, 37);
        float expectedSquareMagnitude = 1538;
        float actualSquareMagnitude = pt.squareMagnitude();
        assertEquals(expectedSquareMagnitude, actualSquareMagnitude, "The Point's square magnitude should equal the expected magnitude of 1538.");
    }

    @Test
    void checkPointNormalization_usingFloatingPointDivision() {
        // as floating points, the decimals are kept
        float expectedNormalizedX = 0.3314859950125598f;
        float expectedNormalizedY = 0.9434601396511317f;

        Point pt = new Point(13, 37);
        Pointf expectedNormalization = new Pointf(expectedNormalizedX, expectedNormalizedY);
        Pointf actualNormalization = pt.normalized();
        assertEquals(expectedNormalization, actualNormalization, String.format("The Point's normalized value when using floating-point division should equal the expected values of %s", expectedNormalization));
    }

    @Test
    void checkPointNormalization_usingIntegerDivision() {
        // these are integers -- decimals are cut off, leaving the expected values at (0, 0)
        int expectedNormalizedX = (int) 0.3314859950125598;
        int expectedNormalizedY = (int) 0.9434601396511317;

        Point pt = new Point(13, 37);
        Point expectedNormalization = new Point(expectedNormalizedX, expectedNormalizedY);
        Point actualNormalization = pt.integerNormalized();
        assertEquals(expectedNormalization, actualNormalization, String.format("The Point's normalized value when using integer division should equal the expected values of %s", expectedNormalization));
    }

    @Test
    void checkConversionToPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = pt.asPointf();

        assertEquals(13f, ptf.x, "The x value of the Pointf should equal 13f.");
        assertEquals(37f, ptf.y, "The y value of the Pointf should equal 37f.");
    }

    @Test
    void checkConversionToDimension() {
        Point pt = new Point(13, 37);
        Dimension dimension = pt.asDimension();

        assertEquals(13, dimension.width, "The width value of the Dimension should equal 13.");
        assertEquals(37, dimension.height, "The height value of the Dimension should equal 37.");
    }

    @Test
    void checkEqualsAgainstPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = new Pointf(13f, 37f);

        assertTrue(pt.equalsPointf(ptf), "The Point and Pointf should be equal in their x and y values.");
    }

    @Test
    void checkEqualsAgainstDimension() {
        Point pt = new Point(13, 37);
        Dimension dimension = new Dimension(13, 37);

        assertTrue(pt.equalsDimension(dimension), "The Point and Dimension should be equal in their x/width and y/height values.");
    }

    @Test
    void checkIntersectionsWithRectangleObject() {
        Point pt = new Point(13, 37);
        Rectangle rect = new Rectangle(0, 0, 20, 40);

        assertTrue(pt.intersects(rect), "Point should intersect with Rectangle.");
    }

    @Test
    void checkIntersectionsWithRectangle2DFloatObject() {
        Point pt = new Point(13, 37);
        Rectangle2D.Float rectf = new Rectangle2D.Float(0f, 0f, 20f, 40f);

        assertTrue(pt.intersects(rectf), "Point should intersect with Rectangle2D.Float.");
    }

    @Test
    void checkIntersectionsWithRectangle2DDoubleObject() {
        Point pt = new Point(13, 37);
        Rectangle2D.Double rectd = new Rectangle2D.Double(0d, 0d, 20d, 40d);

        assertTrue(pt.intersects(rectd), "Point should intersect with Rectangle2D.Double.");
    }

    @Test
    void checkIntersectionWithPath2DFloatObject() {
        Point pt = new Point(13, 37);

        Path2D.Float pathf = new Path2D.Float();
        pathf.moveTo(0f, 0f);
        pathf.lineTo(20f, 0f);
        pathf.lineTo(20f, 40f);
        pathf.lineTo(0f, 40f);
        pathf.closePath();

        assertTrue(pt.intersects(pathf), "Point should intersect with Path2D.Float.");
    }

    @Test
    void checkIntersectionWithPath2DDoubleObject() {
        Point pt = new Point(13, 37);

        Path2D.Double pathd = new Path2D.Double();
        pathd.moveTo(0d, 0d);
        pathd.lineTo(20d, 0d);
        pathd.lineTo(20d, 40d);
        pathd.lineTo(0d, 40d);
        pathd.closePath();

        assertTrue(pt.intersects(pathd), "Point should intersect with Path2D.Double.");
    }

    @Test
    void checkIntersectionWithGeneralPathObject() {
        Point pt = new Point(13, 37);

        GeneralPath pathg = new GeneralPath();
        pathg.moveTo(0f, 0f);
        pathg.lineTo(20f, 0f);
        pathg.lineTo(20f, 40f);
        pathg.lineTo(0f, 40f);
        pathg.closePath();

        assertTrue(pt.intersects(pathg), "Point should intersect with GeneralPath.");
    }


    @Test
    void static_checkPointAddition_withIntegerValues() {
        Point pt = new Point();
        Point added = Point.add(pt, 5);

        assertEquals(5, added.x, "The x value of the Point should equal 5.");
        assertEquals(5, added.y, "The y value of the Point should equal 5.");
    }

    @Test
    void static_checkPointAddition_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        Point added = Point.add(pt, pt2);

        assertEquals(5, added.x, "The x value of the Point should equal 5.");
        assertEquals(5, added.y, "The y value of the Point should equal 5.");
    }

    @Test
    void static_checkPointSubtraction_withIntegerValues() {
        Point pt = new Point();
        Point subtracted = Point.subtract(pt, 5);

        assertEquals(-5, subtracted.x, "The x value of the Point should equal -5.");
        assertEquals(-5, subtracted.y, "The y value of the Point should equal -5.");
    }

    @Test
    void static_checkPointSubtraction_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        Point subtracted = Point.subtract(pt, pt2);

        assertEquals(-5, subtracted.x, "The x value of the Point should equal -5.");
        assertEquals(-5, subtracted.y, "The y value of the Point should equal -5.");
    }

    @Test
    void static_checkPointMultiplication_withIntegerValues() {
        Point pt = new Point(1);
        Point multiplied = Point.multiply(pt, 5);

        assertEquals(5, multiplied.x, "The x value of the Point should equal 5.");
        assertEquals(5, multiplied.y, "The y value of the Point should equal 5.");
    }

    @Test
    void static_checkPointMultiplication_withPointObjects() {
        Point pt = new Point(1);
        Point pt2 = new Point(5);
        Point multiplied = Point.multiply(pt, pt2);

        assertEquals(5, multiplied.x, "The x value of the Point should equal 5.");
        assertEquals(5, multiplied.y, "The y value of the Point should equal 5.");
    }

    @Test
    void static_checkPointDivision_withIntegerValues() {
        Point pt = new Point(25);
        Point divided = Point.divide(pt, 5);

        assertEquals(5, divided.x, "The x value of the Point should equal 5.");
        assertEquals(5, divided.y, "The y value of the Point should equal 5.");
    }

    @Test
    void static_checkPointDivision_withPointObjects() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);
        Point divided = Point.divide(pt, pt2);

        assertEquals(5, divided.x, "The x value of the Point should equal 5.");
        assertEquals(5, divided.y, "The y value of the Point should equal 5.");
    }

    @Test
    void static_checkDotProduct() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);

        int expectedDotProduct = 250;
        int actualDotProduct = Point.dot(pt, pt2);
        assertEquals(expectedDotProduct, actualDotProduct, "The resulting dot product of the two Points should equal the expected dot product of 250.");
    }

    @Test
    void static_checkCrossProduct() {
        Point pt = new Point(25, 5);
        Point pt2 = new Point(13, 37);

        int expectedCrossProduct = 860;
        int actualCrossProduct = Point.cross(pt, pt2);
        assertEquals(expectedCrossProduct, actualCrossProduct, "The resulting cross product of the two Points should equal the expected cross product of 860.");
    }

    @Test
    void static_checkConversionToPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = Point.toPointf(pt);

        assertEquals(13f, ptf.x, "The x value of the Pointf should equal 13f.");
        assertEquals(37f, ptf.y, "The y value of the Pointf should equal 37f.");
    }
}
