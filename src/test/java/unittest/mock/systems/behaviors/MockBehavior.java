package unittest.mock.systems.behaviors;

import tech.fastj.graphics.game.GameObject;
import tech.fastj.logging.Log;
import tech.fastj.math.Pointf;
import tech.fastj.systems.behaviors.Behavior;

import java.util.Objects;
import java.util.UUID;

public class MockBehavior implements Behavior {

    private final UUID id = UUID.randomUUID();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MockBehavior that = (MockBehavior) o;
        return id.equals(that.id) && Objects.equals(pointf, that.pointf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pointf);
    }
}
