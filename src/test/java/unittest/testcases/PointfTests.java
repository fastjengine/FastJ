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

public class PointfTests {

    @Test
    public void checkPointfCreation_withNoConstructorParams() {
        Pointf ptf = new Pointf();
        assertEquals("The x value of the Pointf should default to 0f.", 0f, ptf.x);
        assertEquals("The y value of the Pointf should default to 0f.", 0f, ptf.y);
    }

    @Test
    public void checkPointfCreation_withOneConstructorParam() {
        Pointf ptf = new Pointf(5f);
        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfCreation_withTwoConstructorParams() {
        Pointf ptf = new Pointf(3f, 4f);
        assertEquals("The x value of the Pointf should equal 3f.", 3f, ptf.x);
        assertEquals("The y value of the Pointf should equal 4f.", 4f, ptf.y);
    }

    @Test
    public void checkPointfCreation_withPointfObjectParam() {
        Pointf ptf = new Pointf(3f, 4f);
        Pointf ptf2 = new Pointf(ptf);

        assertEquals("The x value of the Pointf should equal 3f.", 3f, ptf2.x);
        assertEquals("The y value of the Pointf should equal 4f.", 4f, ptf2.y);
    }

    @Test
    public void checkPointfAddition_withFloatValues() {
        Pointf ptf = new Pointf();
        ptf.add(5f);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfAddition_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        ptf.add(ptf2);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfSubtraction_withFloatValues() {
        Pointf ptf = new Pointf();
        ptf.subtract(5f);

        assertEquals("The x value of the Pointf should equal -5f.", -5f, ptf.x);
        assertEquals("The y value of the Pointf should equal -5f.", -5f, ptf.y);
    }

    @Test
    public void checkPointfSubtraction_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        ptf.subtract(ptf2);

        assertEquals("The x value of the Pointf should equal -5f.", -5f, ptf.x);
        assertEquals("The y value of the Pointf should equal -5f.", -5f, ptf.y);
    }

    @Test
    public void checkPointfMultiplication_withFloatValues() {
        Pointf ptf = new Pointf(1f);
        ptf.multiply(5f);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfMultiplication_withPointfObjects() {
        Pointf ptf = new Pointf(1f);
        Pointf ptf2 = new Pointf(5f);
        ptf.multiply(ptf2);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfDivision_withFloatValues() {
        Pointf ptf = new Pointf(25f);
        ptf.divide(5f);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfDivision_withPointfObjects() {
        Pointf ptf = new Pointf(25f);
        Pointf ptf2 = new Pointf(5f);
        ptf.divide(ptf2);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkArithmeticChaining_withPointfObjectsAndFloatValues() {
        Pointf ptf = new Pointf()
                .add(2f)                                    // (2f, 2f)
                .add(new Pointf(3f))                    // (5f, 5f)
                .multiply(new Pointf(3f, 4f))    // (15f, 20f)
                .multiply(2f)                               // (30f, 40f)
                .subtract(15f)                              // (15f, 25f)
                .subtract(new Pointf(-5f, 5f))   // (20f, 20f)
                .divide(new Pointf(2f))                 // (10f, 10f)
                .divide(2f);                                // (5f, 5f)

        assertEquals("The x value of the Pointf should equal 5f.", 5f, ptf.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, ptf.y);
    }

    @Test
    public void checkPointfCopyingforEquality() {
        Pointf original = new Pointf(13f, 37f);
        Pointf copy = original.copy();

        assertEquals("The two Pointfs should have the same x and y values -- (13f, 37f).", original, copy);
    }

    @Test
    public void checkPointfResettingForEquality_withPointfAtOrigin() {
        Pointf ptf = new Pointf(13f, 37f);
        ptf.reset();

        assertEquals("The Pointf's x and y values should have been reset to (0f, 0f).", new Pointf(), ptf);
    }

    @Test
    public void checkPointfSetting() {
        Pointf ptf = new Pointf();
        ptf.set(13f, 37f);

        assertEquals("The x value of the Pointf should equal 13f.", 13f, ptf.x);
        assertEquals("The y value of the Pointf should equal 37f.", 37f, ptf.y);
    }

    @Test
    public void checkEqualsAgainstPoint() {
        Pointf ptf = new Pointf(13f, 37f);
        Point pt = new Point(13, 37);

        assertTrue("The Pointf and Point should be equal in their x and y values.", ptf.equalsPoint(pt));
    }

    @Test
    public void checkIntersectionsWithRectangleObject() {
        Pointf ptf = new Pointf(13f, 37f);
        Rectangle rect = new Rectangle(0, 0, 20, 40);

        assertTrue("Pointf should intersect with Rectangle.", ptf.intersects(rect));
    }

    @Test
    public void checkIntersectionsWithRectangle2DFloatObject() {
        Pointf ptf = new Pointf(13f, 37f);
        Rectangle2D.Float rectf = new Rectangle2D.Float(0f, 0f, 20f, 40f);

        assertTrue("Pointf should intersect with Rectangle2D.Float.", ptf.intersects(rectf));
    }

    @Test
    public void checkIntersectionsWithRectangle2DDoubleObject() {
        Pointf ptf = new Pointf(13f, 37f);
        Rectangle2D.Double rectd = new Rectangle2D.Double(0d, 0d, 20d, 40d);

        assertTrue("Pointf should intersect with Rectangle2D.Double.", ptf.intersects(rectd));
    }

    @Test
    public void checkIntersectionWithPath2DFloatObject() {
        Pointf ptf = new Pointf(13f, 37f);

        Path2D.Float pathf = new Path2D.Float();
        pathf.moveTo(0f, 0f);
        pathf.lineTo(20f, 0f);
        pathf.lineTo(20f, 40f);
        pathf.lineTo(0f, 40f);
        pathf.closePath();

        assertTrue("Pointf should intersect with Path2D.Float.", ptf.intersects(pathf));
    }

    @Test
    public void checkIntersectionWithPath2DDoubleObject() {
        Pointf ptf = new Pointf(13f, 37f);

        Path2D.Double pathd = new Path2D.Double();
        pathd.moveTo(0d, 0d);
        pathd.lineTo(20d, 0d);
        pathd.lineTo(20d, 40d);
        pathd.lineTo(0d, 40d);
        pathd.closePath();

        assertTrue("Pointf should intersect with Path2D.Double.", ptf.intersects(pathd));
    }

    @Test
    public void checkIntersectionWithGeneralPathObject() {
        Pointf ptf = new Pointf(13f, 37f);

        GeneralPath pathg = new GeneralPath();
        pathg.moveTo(0f, 0f);
        pathg.lineTo(20f, 0f);
        pathg.lineTo(20f, 40f);
        pathg.lineTo(0f, 40f);
        pathg.closePath();

        assertTrue("Pointf should intersect with GeneralPath.", ptf.intersects(pathg));
    }


    @Test
    public void static_checkPointfAddition_withFloatValues() {
        Pointf ptf = new Pointf();
        Pointf added = Pointf.add(ptf, 5f);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, added.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, added.y);
    }

    @Test
    public void static_checkPointfAddition_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        Pointf added = Pointf.add(ptf, ptf2);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, added.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, added.y);
    }

    @Test
    public void static_checkPointfSubtraction_withFloatValues() {
        Pointf ptf = new Pointf();
        Pointf subtracted = Pointf.subtract(ptf, 5f);

        assertEquals("The x value of the Pointf should equal -5f.", -5f, subtracted.x);
        assertEquals("The y value of the Pointf should equal -5f.", -5f, subtracted.y);
    }

    @Test
    public void static_checkPointfSubtraction_withPointfObjects() {
        Pointf ptf = new Pointf();
        Pointf ptf2 = new Pointf(5f);
        Pointf subtracted = Pointf.subtract(ptf, ptf2);

        assertEquals("The x value of the Pointf should equal -5f.", -5f, subtracted.x);
        assertEquals("The y value of the Pointf should equal -5f.", -5f, subtracted.y);
    }

    @Test
    public void static_checkPointfMultiplication_withFloatValues() {
        Pointf ptf = new Pointf(1f);
        Pointf multiplied = Pointf.multiply(ptf, 5f);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, multiplied.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, multiplied.y);
    }

    @Test
    public void static_checkPointfMultiplication_withPointfObjects() {
        Pointf ptf = new Pointf(1f);
        Pointf ptf2 = new Pointf(5f);
        Pointf multiplied = Pointf.multiply(ptf, ptf2);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, multiplied.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, multiplied.y);
    }

    @Test
    public void static_checkPointfDivision_withFloatValues() {
        Pointf ptf = new Pointf(25f);
        Pointf divided = Pointf.divide(ptf, 5f);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, divided.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, divided.y);
    }

    @Test
    public void static_checkPointfDivision_withPointfObjects() {
        Pointf ptf = new Pointf(25f);
        Pointf ptf2 = new Pointf(5f);
        Pointf divided = Pointf.divide(ptf, ptf2);

        assertEquals("The x value of the Pointf should equal 5f.", 5f, divided.x);
        assertEquals("The y value of the Pointf should equal 5f.", 5f, divided.y);
    }
}
