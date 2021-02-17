package io.github.lucasstarsz.fastj.game.scripts;

import io.github.lucasstarsz.fastj.framework.graphics.GameObject;
import io.github.lucasstarsz.fastj.framework.io.keyboard.Keyboard;
import io.github.lucasstarsz.fastj.framework.io.keyboard.Keys;
import io.github.lucasstarsz.fastj.framework.math.Pointf;
import io.github.lucasstarsz.fastj.framework.systems.behaviors.Behavior;

public class PlayerScript implements Behavior {

    private Pointf travel;
    private float speed;

    public PlayerScript(float speed) {
        this.speed = speed;
    }

    @Override
    public void init(GameObject obj) {
        travel = new Pointf();
    }

    @Override
    public void update(GameObject obj) {
        if (Keyboard.isKeyDown(Keys.w)) travel.y -= speed;
        else if (Keyboard.isKeyDown(Keys.s)) travel.y += speed;

        if (Keyboard.isKeyDown(Keys.a)) travel.x -= speed;
        else if (Keyboard.isKeyDown(Keys.d)) travel.x += speed;

        obj.translate(travel);
        travel.reset();
    }

    @Override
    public void destroy() {
        travel = null;
    }
}
