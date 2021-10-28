package unittest.testcases.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import tech.fastj.math.Maths;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

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
    void checkPointCreation_withDimensionObjectParam() {
        Dimension dimension = new Dimension(3, 4);
        Point pt = new Point(dimension);

        assertEquals(3, pt.x, "The x value of the Point should equal 3.");
        assertEquals(4, pt.y, "The y value of the Point should equal 4.");
    }

    @Test
    void checkPointCreation_withAWTPointObjectParam() {
        java.awt.Point awtPt = new java.awt.Point(3, 4);
        Point pt = new Point(awtPt);

        assertEquals(3, pt.x, "The x value of the Point should equal 3.");
        assertEquals(4, pt.y, "The y value of the Point should equal 4.");
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
    void checkPointRotation_aroundOrigin() {
        Point pt = new Point(25, 5);
        int angle = 1337;

        Point expectedRotation = new Point(-10, 23);
        pt.rotate(angle);
        assertEquals(expectedRotation, pt, "The resulting rotation of the Point should equal the expected rotated value of " + expectedRotation + ".");
    }

    @Test
    void checkPointRotation_aroundOtherPoint() {
        Point pt = new Point(25, 5);
        Point pt2 = new Point(13, 37);
        int angle = 1337;

        Point expectedRotation = new Point(41, 55);
        Point actualRotation = pt.rotate(angle, pt2);
        assertEquals(expectedRotation, actualRotation, "The resulting rotation of the Point should equal the expected rotated value of " + expectedRotation + ".");
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

        pt.set(5);

        assertEquals(5, pt.x, "The x value of the Point should be equal 5");
        assertEquals(5, pt.y, "The y value of the Point should be equal 5");
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
    void checkPointNormalization_whenMagnitudeIsZero_usingFloatingPointDivision() {
        Point pt = new Point();
        float expectedNormalizedX = 0f;
        float expectedNormalizedY = 0f;

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
    void checkPointNormalization_whenMagnitudeIsZero_usingIntegerDivision() {
        Point pt = new Point();
        int expectedNormalizedX = 0;
        int expectedNormalizedY = 0;

        Point expectedNormalization = new Point(expectedNormalizedX, expectedNormalizedY);
        Point actualNormalization = pt.integerNormalized();
        assertEquals(expectedNormalization, actualNormalization, String.format("The Point's normalized value when using floating-point division should equal the expected values of %s", expectedNormalization));
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
    void checkConversionToAWTPoint() {
        Point pt = new Point(13, 37);
        java.awt.Point awtPt = pt.asAWTPoint();

        assertEquals(13, awtPt.x, "The width value of the AWTPoint should equal 13.");
        assertEquals(37, awtPt.y, "The height value of the AWTPoint should equal 37.");
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
    void checkEqualsAgainstDimension_whenPointAndDimensionAreNotEqual() {
        Point pt = new Point(13, 37);
        Dimension dimension = new Dimension(25, 5);

        assertFalse(pt.equalsDimension(dimension), "The Point and Dimension should not be equal in their x/width and y/height values.");

        Point pt2 = new Point(13, 37);
        Dimension dimension2 = new Dimension(13, 5);

        assertFalse(pt2.equalsDimension(dimension2), "The Point and Dimension should not be equal in their x/width and y/height values.");
    }

    @Test
    void checkEqualsAgainstAWTPoint() {
        Point pt = new Point(13, 37);
        java.awt.Point awtPt = new java.awt.Point(13, 37);

        assertTrue(pt.equalsAWTPoint(awtPt), "The Point and AWTPoint should be equal in their x/width and y/height values.");
    }

    @Test
    void checkEqualsAgainstAWTPoint_whenPointAndAWTPointAreNotEqual() {
        Point pt = new Point(13, 37);
        java.awt.Point awtPt = new java.awt.Point(25, 5);

        assertFalse(pt.equalsAWTPoint(awtPt), "The Point and AWTPoint should not be equal in their x/width and y/height values.");

        Point pt2 = new Point(13, 37);
        java.awt.Point awtPt2 = new java.awt.Point(13, 5);

        assertFalse(pt2.equalsAWTPoint(awtPt2), "The Point and AWTPoint should not be equal in their x/width and y/height values.");
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
    void static_checkPointRotation_usingIntegerMath() {
        Point pt = new Point(25, 5);
        int angle = 1337;

        Point expectedRotation = new Point(-10, 23);
        Point actualRotation = Point.integerRotate(pt, angle);
        assertEquals(expectedRotation, actualRotation, "The resulting rotation of the Point should equal the expected rotated value of " + expectedRotation + ".");
    }

    @Test
    void static_checkPointRotation_aroundOrigin_usingFloatingPointMath() {
        Point pt = new Point(25, 5);
        float angle = 1337f;

        Pointf expectedRotation = new Pointf(-10.495626682523f, 23.234496347912f);
        Pointf actualRotation = Point.rotate(pt, angle);
        assertEquals(expectedRotation, actualRotation, "The resulting rotation of the Point should equal the expected rotated value of " + expectedRotation + ".");
    }

    @Test
    void static_checkPointRotation_aroundOtherPointf_usingFloatingPointMath() {
        Point pt = new Point(25, 5);
        Pointf ptf = new Pointf(13f, 37f);
        float angle = 1337f;

        Pointf expectedRotation = new Pointf(41.4804f, 55.8909f);
        Pointf actualRotation = Point.rotate(pt, angle, ptf);
        assertEquals(expectedRotation, actualRotation, "The resulting rotation of the Point should equal the expected rotated value of " + expectedRotation + ".");
    }

    @Test
    void static_checkPointRotation_aroundOtherPoint_usingIntegerMath() {
        Point pt = new Point(25, 5);
        Point pt2 = new Point(13, 37);
        int angle = 1337;

        Point expectedRotation = new Point(41, 55);
        Point actualRotation = Point.integerRotate(pt, angle, pt2);
        assertEquals(expectedRotation, actualRotation, "The resulting rotation of the Point should equal the expected rotated value of " + expectedRotation + ".");
    }

    @Test
    void static_checkPointAngleCalculation() {
        Point ptf = new Point(1, 0);
        Point ptf2 = new Point(0, 1);
        float expectedAngle = (float) Math.toRadians(90d);
        float actualAngle = Point.angle(ptf, ptf2);

        assertEquals(expectedAngle, actualAngle, "The resulting angle of the two Points should equal the expected angle of " + expectedAngle + ".");

        Point ptf3 = new Point(13, 37);
        Point ptf4 = new Point(25, 5);
        float expectedAngle2 = (float) Math.toRadians(59.33d);
        float actualAngle2 = Point.angle(ptf3, ptf4);

        assertTrue(Maths.floatEquals(expectedAngle2, actualAngle2), "The resulting angle of the two Points should equal the expected angle of " + expectedAngle2 + ".");
    }

    @Test
    void static_checkPointSignedAngleCalculation() {
        Point ptf = new Point(13, 37);
        Point ptf2 = new Point(25, 5);

        float expectedAngle = (float) Math.toRadians(-59.33d);
        float actualAngle = Point.signedAngle(ptf, ptf2);

        assertTrue(Maths.floatEquals(expectedAngle, actualAngle), "The resulting signed angle of the two Points should equal the expected angle of " + expectedAngle + ".");

        float expectedAngle2 = (float) Math.toRadians(59.33d);
        float actualAngle2 = Point.signedAngle(ptf2, ptf);

        assertTrue(Maths.floatEquals(expectedAngle2, actualAngle2), "The resulting signed angle of the two Points should equal the expected angle of " + expectedAngle2 + ".");
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
