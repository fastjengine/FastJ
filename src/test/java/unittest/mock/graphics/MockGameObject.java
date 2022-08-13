package unittest.mock.graphics;

import tech.fastj.graphics.game.GameObject;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.systems.control.GameHandler;

import java.awt.Graphics2D;
import java.util.Objects;
import java.util.UUID;

public class MockGameObject extends GameObject {

    private final UUID id = UUID.randomUUID();

    @Override
    public void destroy(GameHandler origin) {
    }

    @Override
    public Pointf getTranslation() {
        return Transform2D.DefaultTranslation;
    }

    @Override
    public float getRotation() {
        return Transform2D.DefaultRotation;
    }

    @Override
    public Pointf getScale() {
        return Transform2D.DefaultScale;
    }

    @Override
    public void translate(Pointf translationMod) {
    }

    @Override
    public void rotate(float rotationMod, Pointf centerpoint) {
    }

    @Override
    public void scale(Pointf scaleMod, Pointf centerpoint) {
    }

    @Override
    public void render(Graphics2D g) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MockGameObject that = (MockGameObject) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
