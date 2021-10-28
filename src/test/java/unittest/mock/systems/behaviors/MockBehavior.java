package unittest.mock.systems.behaviors;

import tech.fastj.math.Pointf;

import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.Behavior;

public class MockBehavior implements Behavior {

    private Pointf pointf;

    @Override
    public void init(GameObject gameObject) {
        pointf = new Pointf();
    }

    @Override
    public void update(GameObject gameObject) {
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
