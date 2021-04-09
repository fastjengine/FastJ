package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointTests {

    @Test
    public void checkPointCreation_withNoConstructorParams() {
        Point pt = new Point();
        assertEquals(0, pt.x, "The x value of the Point should default to 0.");
        assertEquals(0, pt.y, "The y value of the Point should default to 0.");
    }

    @Test
    public void checkPointCreation_withOneConstructorParam() {
        Point pt = new Point(5);
        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkPointCreation_withTwoConstructorParams() {
        Point pt = new Point(3, 4);
        assertEquals(3, pt.x, "The x value of the Point should equal 3.");
        assertEquals(4, pt.y, "The y value of the Point should equal 4.");
    }

    @Test
    public void checkPointCreation_withPointObjectParam() {
        Point pt = new Point(3, 4);
        Point pt2 = new Point(pt);

        assertEquals(3, pt2.x, "The x value of the Point should equal 3.");
        assertEquals(4, pt2.y, "The y value of the Point should equal 4.");
    }

    @Test
    public void checkPointAddition_withIntegerValues() {
        Point pt = new Point();
        pt.add(5);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkPointAddition_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        pt.add(pt2);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkPointSubtraction_withIntegerValues() {
        Point pt = new Point();
        pt.subtract(5);

        assertEquals(-5, pt.x, "The x value of the Point should equal -5.");
        assertEquals(-5, pt.y, "The y value of the Point should equal -5.");
    }

    @Test
    public void checkPointSubtraction_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        pt.subtract(pt2);

        assertEquals(-5, pt.x, "The x value of the Point should equal -5.");
        assertEquals(-5, pt.y, "The y value of the Point should equal -5.");
    }

    @Test
    public void checkPointMultiplication_withIntegerValues() {
        Point pt = new Point(1);
        pt.multiply(5);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkPointMultiplication_withPointObjects() {
        Point pt = new Point(1);
        Point pt2 = new Point(5);
        pt.multiply(pt2);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkPointDivision_withIntegerValues() {
        Point pt = new Point(25);
        pt.divide(5);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkPointDivision_withPointObjects() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);
        pt.divide(pt2);

        assertEquals(5, pt.x, "The x value of the Point should equal 5.");
        assertEquals(5, pt.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void checkArithmeticChaining_withPointObjectsAndIntegerValues() {
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
    public void checkPointCopyingForEquality() {
        Point original = new Point(13, 37);
        Point copy = original.copy();

        assertEquals(original, copy, "The two points should have the same x and y values -- (13, 37).");
    }

    @Test
    public void checkPointResettingForEquality_withPointAtOrigin() {
        Point pt = new Point(13, 37);
        pt.reset();

        assertEquals(new Point(), pt, "The point's x and y values should have been reset to (0, 0).");
    }

    @Test
    public void checkPointSetting() {
        Point pt = new Point();
        pt.set(13, 37);

        assertEquals(13, pt.x, "The x value of the Point should equal 13.");
        assertEquals(37, pt.y, "The y value of the Point should equal 37.");
    }

    @Test
    public void checkConversionToPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = pt.asPointf();

        assertEquals(13f, ptf.x, "The x value of the Pointf should equal 13f.");
        assertEquals(37f, ptf.y, "The y value of the Pointf should equal 37f.");
    }

    @Test
    public void checkEqualsAgainstPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = new Pointf(13f, 37f);

        assertTrue(pt.equalsPointf(ptf), "The Point and Pointf should be equal in their x and y values.");
    }

    @Test
    public void checkIntersectionsWithRectangleObject() {
        Point pt = new Point(13, 37);
        Rectangle rect = new Rectangle(0, 0, 20, 40);

        assertTrue(pt.intersects(rect), "Point should intersect with Rectangle.");
    }

    @Test
    public void checkIntersectionsWithRectangle2DFloatObject() {
        Point pt = new Point(13, 37);
        Rectangle2D.Float rectf = new Rectangle2D.Float(0f, 0f, 20f, 40f);

        assertTrue(pt.intersects(rectf), "Point should intersect with Rectangle2D.Float.");
    }

    @Test
    public void checkIntersectionsWithRectangle2DDoubleObject() {
        Point pt = new Point(13, 37);
        Rectangle2D.Double rectd = new Rectangle2D.Double(0d, 0d, 20d, 40d);

        assertTrue(pt.intersects(rectd), "Point should intersect with Rectangle2D.Double.");
    }

    @Test
    public void checkIntersectionWithPath2DFloatObject() {
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
    public void checkIntersectionWithPath2DDoubleObject() {
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
    public void checkIntersectionWithGeneralPathObject() {
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
    public void static_checkPointAddition_withIntegerValues() {
        Point pt = new Point();
        Point added = Point.add(pt, 5);

        assertEquals(5, added.x, "The x value of the Point should equal 5.");
        assertEquals(5, added.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void static_checkPointAddition_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        Point added = Point.add(pt, pt2);

        assertEquals(5, added.x, "The x value of the Point should equal 5.");
        assertEquals(5, added.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void static_checkPointSubtraction_withIntegerValues() {
        Point pt = new Point();
        Point subtracted = Point.subtract(pt, 5);

        assertEquals(-5, subtracted.x, "The x value of the Point should equal -5.");
        assertEquals(-5, subtracted.y, "The y value of the Point should equal -5.");
    }

    @Test
    public void static_checkPointSubtraction_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        Point subtracted = Point.subtract(pt, pt2);

        assertEquals(-5, subtracted.x, "The x value of the Point should equal -5.");
        assertEquals(-5, subtracted.y, "The y value of the Point should equal -5.");
    }

    @Test
    public void static_checkPointMultiplication_withIntegerValues() {
        Point pt = new Point(1);
        Point multiplied = Point.multiply(pt, 5);

        assertEquals(5, multiplied.x, "The x value of the Point should equal 5.");
        assertEquals(5, multiplied.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void static_checkPointMultiplication_withPointObjects() {
        Point pt = new Point(1);
        Point pt2 = new Point(5);
        Point multiplied = Point.multiply(pt, pt2);

        assertEquals(5, multiplied.x, "The x value of the Point should equal 5.");
        assertEquals(5, multiplied.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void static_checkPointDivision_withIntegerValues() {
        Point pt = new Point(25);
        Point divided = Point.divide(pt, 5);

        assertEquals(5, divided.x, "The x value of the Point should equal 5.");
        assertEquals(5, divided.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void static_checkPointDivision_withPointObjects() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);
        Point divided = Point.divide(pt, pt2);

        assertEquals(5, divided.x, "The x value of the Point should equal 5.");
        assertEquals(5, divided.y, "The y value of the Point should equal 5.");
    }

    @Test
    public void static_checkConversionToPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = Point.toPointf(pt);

        assertEquals(13f, ptf.x, "The x value of the Pointf should equal 13f.");
        assertEquals(37f, ptf.y, "The y value of the Pointf should equal 37f.");
    }
}
