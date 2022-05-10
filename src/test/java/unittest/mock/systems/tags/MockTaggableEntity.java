package unittest.mock.systems.tags;

import tech.fastj.graphics.Drawable;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

import java.util.UUID;

public class MockTaggableEntity extends Drawable {

    public static String generateTag() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void destroy(Scene origin) {
        destroyTheRest(origin);
    }

    @Override
    public void destroy(SimpleManager origin) {
        destroyTheRest(origin);
    }
}
