package unittest.mock.systems.tags;

import tech.fastj.graphics.Drawable;
import tech.fastj.systems.control.GameHandler;

import java.util.UUID;

public class MockTaggableEntity extends Drawable {

    public static String generateTag() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void destroy(GameHandler origin) {
        super.destroyTheRest(origin);
    }
}
