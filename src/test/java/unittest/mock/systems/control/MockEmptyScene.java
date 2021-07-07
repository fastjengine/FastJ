package unittest.mock.systems.control;

import tech.fastj.graphics.display.Display;

import tech.fastj.systems.control.Scene;

import java.util.UUID;

public class MockEmptyScene extends Scene {

    public MockEmptyScene() {
        super(UUID.randomUUID().toString());
    }

    @Override
    public void load(Display display) {
    }

    @Override
    public void unload(Display display) {
    }

    @Override
    public void update(Display display) {
    }
}
