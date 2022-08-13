package unittest.mock.systems.behaviors;

import tech.fastj.graphics.game.GameObject;
import tech.fastj.logging.Log;
import tech.fastj.math.Pointf;
import tech.fastj.systems.behaviors.Behavior;

public class MockBehavior implements Behavior {

    private Pointf pointf;

    @Override
    public void init(GameObject gameObject) {
        pointf = Pointf.origin();
    }

    @Override
    public void fixedUpdate(GameObject gameObject) {
        if (pointf == null) {
            Log.warn(
                MockBehavior.class,
                "Game Object {} containing mock behavior tried calling fixed update while pointf was null",
                gameObject
            );
        }

        pointf.add(1f);
    }

    @Override
    public void destroy() {
        pointf = null;
    }

    public Pointf getPointf() {
        return pointf;
    }
}
