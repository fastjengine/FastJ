package unittest.testcases.math;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.math.PointfMath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointfMathTests {
    @Test
    public void checkVectorAddition() {
        assertEquals(new Pointf(2, 2), PointfMath.add(new Pointf(1, 1), new Pointf(1, 1)));
        assertEquals(new Pointf(4, 4), PointfMath.add(new Pointf(2, 2), new Pointf(2, 2)));
    }

    @Test
    public void checkVectorSubtraction() {
        assertEquals(new Pointf(1, 1), PointfMath.subtract(new Pointf(2, 2), new Pointf(1, 1)));
        assertEquals(new Pointf(5, 5), PointfMath.subtract(new Pointf(10, 10), new Pointf(5, 5)));
    }
}
