package unittest.mock.systems.control;

import tech.fastj.systems.control.Scene;

import java.util.UUID;

public class MockEmptyScene extends Scene {
    public MockEmptyScene() {
        super(UUID.randomUUID().toString());
    }
}
