package unittest.mock;

import io.github.lucasstarsz.fastj.math.Pointf;

import io.github.lucasstarsz.fastj.graphics.gameobject.GameObject;

import io.github.lucasstarsz.fastj.systems.behaviors.Behavior;

public class MockBehavior implements Behavior {

    private Pointf pointf;

    @Override
    public void init(GameObject obj) {
        pointf = new Pointf();
    }

    @Override
    public void update(GameObject obj) {
        pointf.add(1f);
    }

    @Override
    public void destroy() {
        pointf = null;
    }

    public Pointf getPointf() {
        return pointf.copy();
    }
}
