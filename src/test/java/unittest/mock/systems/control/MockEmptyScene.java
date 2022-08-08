package unittest.mock.systems.control;

import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.systems.control.Scene;

import java.util.UUID;

public class MockEmptyScene extends Scene {

    public MockEmptyScene() {
        super(UUID.randomUUID().toString());
    }

    @Override
    public void load(FastJCanvas canvas) {
    }

    @Override
    public void unload(FastJCanvas canvas) {
    }

    @Override
    public void fixedUpdate(FastJCanvas canvas) {
    }

    @Override
    public void update(FastJCanvas canvas) {
    }
}
