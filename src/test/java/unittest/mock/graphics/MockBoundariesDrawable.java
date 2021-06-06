package unittest.mock.graphics;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.Scene;

public class MockBoundariesDrawable extends Drawable {

    public MockBoundariesDrawable() {
        setBounds(DrawUtil.createBox(0.0f, 0.0f, 1.0f));
    }

    @Override
    public void destroy(Scene originScene) {
    }
}
