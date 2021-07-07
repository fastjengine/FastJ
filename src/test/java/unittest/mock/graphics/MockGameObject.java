package unittest.mock.graphics;

import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.control.Scene;

import java.awt.Graphics2D;

public class MockGameObject extends GameObject {
    @Override
    public void destroy(Scene originScene) {

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
}
