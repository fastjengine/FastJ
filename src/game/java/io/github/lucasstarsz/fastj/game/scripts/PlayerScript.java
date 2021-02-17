package io.github.lucasstarsz.fastj.game.scripts;

import io.github.lucasstarsz.fastj.engine.graphics.Drawable;
import io.github.lucasstarsz.fastj.engine.io.Key;
import io.github.lucasstarsz.fastj.engine.io.Keyboard;
import io.github.lucasstarsz.fastj.engine.systems.behaviors.Behavior;
import io.github.lucasstarsz.fastj.engine.util.math.Pointf;

public class PlayerScript implements Behavior {

    private Pointf travel;

    @Override
    public void init(Drawable obj) {
        travel = new Pointf();
    }

    @Override
    public void update(Drawable obj) {
        if (Keyboard.isKeyDown(Key.w)) travel.y--;
        else if (Keyboard.isKeyDown(Key.s)) travel.y++;

        if (Keyboard.isKeyDown(Key.a)) travel.x--;
        else if (Keyboard.isKeyDown(Key.d)) travel.x++;

        obj.translate(travel);
        travel.reset();
    }

    @Override
    public void destroy() {
        travel = null;
    }
}
