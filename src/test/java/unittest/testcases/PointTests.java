package unittest.testcases;

import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.Test;

import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PointTests {

    @Test
    public void checkPointCreation_withNoConstructorParams() {
        Point pt = new Point();
        assertEquals("The x value of the Point should default to 0.", 0, pt.x);
        assertEquals("The y value of the Point should default to 0.", 0, pt.y);
    }

    @Test
    public void checkPointCreation_withOneConstructorParam() {
        Point pt = new Point(5);
        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointCreation_withTwoConstructorParams() {
        Point pt = new Point(3, 4);
        assertEquals("The x value of the Point should equal 3.", 3, pt.x);
        assertEquals("The y value of the Point should equal 4.", 4, pt.y);
    }

    @Test
    public void checkPointCreation_withPointObjectParam() {
        Point pt = new Point(3, 4);
        Point pt2 = new Point(pt);

        assertEquals("The x value of the Point should equal 3.", 3, pt2.x);
        assertEquals("The y value of the Point should equal 4.", 4, pt2.y);
    }

    @Test
    public void checkPointAddition_withIntegerValues() {
        Point pt = new Point();
        pt.add(5);

        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointAddition_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        pt.add(pt2);

        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointSubtraction_withIntegerValues() {
        Point pt = new Point();
        pt.subtract(5);

        assertEquals("The x value of the Point should equal -5.", -5, pt.x);
        assertEquals("The y value of the Point should equal -5.", -5, pt.y);
    }

    @Test
    public void checkPointSubtraction_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        pt.subtract(pt2);

        assertEquals("The x value of the Point should equal -5.", -5, pt.x);
        assertEquals("The y value of the Point should equal -5.", -5, pt.y);
    }

    @Test
    public void checkPointMultiplication_withIntegerValues() {
        Point pt = new Point(1);
        pt.multiply(5);

        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointMultiplication_withPointObjects() {
        Point pt = new Point(1);
        Point pt2 = new Point(5);
        pt.multiply(pt2);

        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointDivision_withIntegerValues() {
        Point pt = new Point(25);
        pt.divide(5);

        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointDivision_withPointObjects() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);
        pt.divide(pt2);

        assertEquals("The x value of the Point should equal 5.", 5, pt.x);
        assertEquals("The y value of the Point should equal 5.", 5, pt.y);
    }

    @Test
    public void checkPointCopyingforEquality() {
        Point original = new Point(13, 37);
        Point copy = original.copy();

        assertEquals("The two points should have the same x and y values -- (13, 37).", original, copy);
    }

    @Test
    public void checkPointResettingForEquality_withPointAtOrigin() {
        Point pt = new Point(13, 37);
        pt.reset();

        assertEquals("The point's x and y values should have been reset to (0, 0).", new Point(), pt);
    }

    @Test
    public void checkPointSetting() {
        Point pt = new Point();
        pt.set(13, 37);

        assertEquals("The x value of the Point should equal 13.", 13, pt.x);
        assertEquals("The y value of the Point should equal 37.", 37, pt.y);
    }

    @Test
    public void checkConversionToPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = pt.asPointf();

        assertEquals("The x value of the Pointf should equal 13f.", 13f, ptf.x);
        assertEquals("The y value of the Pointf should equal 37f.", 37f, ptf.y);
    }

    @Test
    public void checkEqualsAgainstPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = new Pointf(13f, 37f);

        assertTrue("The Point and Pointf should be equal in their x and y values.", pt.equalsPointf(ptf));
    }

    @Test
    public void checkIntersectionsWithRectangle2DObject() {
        Pointf ptf = new Pointf(13f, 37f);

        Rectangle rect = new Rectangle(0, 0, 20, 40);
        Rectangle2D.Float rectf = new Rectangle2D.Float(0f, 0f, 20f, 40f);
        Rectangle2D.Double rectd = new Rectangle2D.Double(0d, 0d, 20d, 40d);

        assertTrue("Pointf should intersect with Rectangle.", ptf.intersects(rect));
        assertTrue("Pointf should intersect with Rectangle2D.Float.", ptf.intersects(rectf));
        assertTrue("Pointf should intersect with Rectangle2D.Double.", ptf.intersects(rectd));
    }

    @Test
    public void checkIntersectionWithPath2DObject() {
        Point pt = new Point(13, 37);

        Path2D.Float pathf = new Path2D.Float();
        pathf.moveTo(0f, 0f);
        pathf.lineTo(20f, 0f);
        pathf.lineTo(20f, 40f);
        pathf.lineTo(0f, 40f);
        pathf.closePath();


        Path2D.Double pathd = new Path2D.Double();
        pathd.moveTo(0f, 0f);
        pathd.lineTo(20f, 0f);
        pathd.lineTo(20f, 40f);
        pathd.lineTo(0f, 40f);
        pathd.closePath();

        GeneralPath pathg = new GeneralPath();
        pathg.moveTo(0f, 0f);
        pathg.lineTo(20f, 0f);
        pathg.lineTo(20f, 40f);
        pathg.lineTo(0f, 40f);
        pathg.closePath();

        assertTrue("Point should intersect with Path2D.Float.", pt.intersects(pathf));
        assertTrue("Point should intersect with Path2D.Double.", pt.intersects(pathd));
        assertTrue("Point should intersect with GeneralPath.", pt.intersects(pathg));
    }


    @Test
    public void static_checkPointAddition_withIntegerValues() {
        Point pt = new Point();
        Point added = Point.add(pt, 5);

        assertEquals("The x value of the Point should equal 5.", 5, added.x);
        assertEquals("The y value of the Point should equal 5.", 5, added.y);
    }

    @Test
    public void static_checkPointAddition_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        Point added = Point.add(pt, pt2);

        assertEquals("The x value of the Point should equal 5.", 5, added.x);
        assertEquals("The y value of the Point should equal 5.", 5, added.y);
    }

    @Test
    public void static_checkPointSubtraction_withIntegerValues() {
        Point pt = new Point();
        Point subtracted = Point.subtract(pt, 5);

        assertEquals("The x value of the Point should equal -5.", -5, subtracted.x);
        assertEquals("The y value of the Point should equal -5.", -5, subtracted.y);
    }

    @Test
    public void static_checkPointSubtraction_withPointObjects() {
        Point pt = new Point();
        Point pt2 = new Point(5);
        Point subtracted = Point.subtract(pt, pt2);

        assertEquals("The x value of the Point should equal -5.", -5, subtracted.x);
        assertEquals("The y value of the Point should equal -5.", -5, subtracted.y);
    }

    @Test
    public void static_checkPointMultiplication_withIntegerValues() {
        Point pt = new Point(1);
        Point multiplied = Point.multiply(pt, 5);

        assertEquals("The x value of the Point should equal 5.", 5, multiplied.x);
        assertEquals("The y value of the Point should equal 5.", 5, multiplied.y);
    }

    @Test
    public void static_checkPointMultiplication_withPointObjects() {
        Point pt = new Point(1);
        Point pt2 = new Point(5);
        Point multiplied = Point.multiply(pt, pt2);

        assertEquals("The x value of the Point should equal 5.", 5, multiplied.x);
        assertEquals("The y value of the Point should equal 5.", 5, multiplied.y);
    }

    @Test
    public void static_checkPointDivision_withIntegerValues() {
        Point pt = new Point(25);
        Point divided = Point.divide(pt, 5);

        assertEquals("The x value of the Point should equal 5.", 5, divided.x);
        assertEquals("The y value of the Point should equal 5.", 5, divided.y);
    }

    @Test
    public void static_checkPointDivision_withPointObjects() {
        Point pt = new Point(25);
        Point pt2 = new Point(5);
        Point divided = Point.divide(pt, pt2);

        assertEquals("The x value of the Point should equal 5.", 5, divided.x);
        assertEquals("The y value of the Point should equal 5.", 5, divided.y);
    }

    @Test
    public void static_checkConversionToPointf() {
        Point pt = new Point(13, 37);
        Pointf ptf = Point.toPointf(pt);

        assertEquals("The x value of the Pointf should equal 13f.", 13f, ptf.x);
        assertEquals("The y value of the Pointf should equal 37f.", 37f, ptf.y);
    }
}
