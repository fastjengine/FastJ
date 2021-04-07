package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Point;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MathsTests {

    @Test
    public void checkMagnitudeWithDoubleValues() {
        assertEquals("The magnitude should be equal to 0.0.", 0d, Maths.magnitude(0, 0));
        assertEquals("The magnitude should be equal to 1.0.", 1d, Maths.magnitude(0, 1));
        assertEquals("The magnitude should be equal to the square root of 2.0.", Math.sqrt(2), Maths.magnitude(1, 1));
        assertEquals("The magnitude should be equal to the square root of 2.0.", Math.sqrt(2), Maths.magnitude(-1, -1));
        assertEquals("The magnitude should be equal to 5.", Math.sqrt(25), Maths.magnitude(3, 4));
    }

    @Test
    public void checkMagnitudeWithPointfObjects() {
        assertEquals("The magnitude should be equal to 0.0.", 0d, Maths.magnitude(new Pointf()));
        assertEquals("The magnitude should be equal to 1.0.", 1d, Maths.magnitude(new Pointf(0, 1)));
        assertEquals("The magnitude should be equal to the square root of 2.0.", Math.sqrt(2), Maths.magnitude(new Pointf(1)));
        assertEquals("The magnitude should be equal to the square root of 2.0.", Math.sqrt(2), Maths.magnitude(new Pointf(-1)));
        assertEquals("The magnitude should be equal to 5.", Math.sqrt(25), Maths.magnitude(new Pointf(3, 4)));
    }

    @Test
    public void checkMagnitudeWithPointObjects() {
        assertEquals("The magnitude should be equal to 0.0.", 0d, Maths.magnitude(new Point()));
        assertEquals("The magnitude should be equal to 1.0.", 1d, Maths.magnitude(new Point(0, 1)));
        assertEquals("The magnitude should be equal to the square root of 2.0.", Math.sqrt(2), Maths.magnitude(new Point(1)));
        assertEquals("The magnitude should be equal to the square root of 2.0.", Math.sqrt(2), Maths.magnitude(new Point(-1)));
        assertEquals("The magnitude should be equal to 5.", Math.sqrt(25), Maths.magnitude(new Point(3, 4)));
    }
}
