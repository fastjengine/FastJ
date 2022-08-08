package tech.fastj.graphics.game;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.math.Pointf;
import tech.fastj.systems.control.GameHandler;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;

public class Light2D extends GameObject {

    private final AlphaComposite alphaComposite;
    private final RadialGradientPaint gradient;

    protected Light2D(Pointf location, Pointf size, RadialGradientPaint gradient, AlphaComposite alphaComposite) {
        this.alphaComposite = alphaComposite;
        this.gradient = gradient;
        setCollisionPath(DrawUtil.createPath(DrawUtil.createBox(location, size)));
    }

    public static Light2DBuilder create(Pointf location, Pointf size) {
        return new Light2DBuilder(location, size, Drawable.DefaultShouldRender);
    }

    public static Light2DBuilder create(Pointf location, Pointf size, boolean shouldRender) {
        return new Light2DBuilder(location, size, shouldRender);
    }

    @Override
    public void destroy(GameHandler origin) {
        super.destroyTheRest(origin);
    }

    @Override
    public void render(Graphics2D g) {
        Composite oldComposite = g.getComposite();
        Paint oldPaint = g.getPaint();

        g.setComposite(alphaComposite);
        g.setPaint(gradient);
        g.fill(getCollisionPath());

        g.setComposite(oldComposite);
        g.setPaint(oldPaint);
    }
}
