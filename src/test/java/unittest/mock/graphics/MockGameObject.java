package unittest.mock.graphics;

import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;

import io.github.lucasstarsz.fastj.systems.control.Scene;

import java.awt.Graphics2D;

public class MockGameObject extends GameObject {
    @Override
    public void destroy(Scene originScene) {

    }

    @Override
    public Pointf getTranslation() {
        return GameObject.DefaultTranslation;
    }

    @Override
    public float getRotation() {
        return GameObject.DefaultRotation;
    }

    @Override
    public Pointf getScale() {
        return GameObject.DefaultScale;
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
