package unittest.mock.graphics;

import tech.fastj.math.Maths;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

public class MockBoundariesDrawable extends Drawable {

    public MockBoundariesDrawable() {
        setBounds(
                DrawUtil.createBox(
                        Maths.random(0.0f, 400.0f),
                        Maths.random(0.0f, 400.0f),
                        Maths.random(1.0f, 400.0f)
                )
        );
    }

    @Override
    public void destroy(Scene originScene) {
    }
}
