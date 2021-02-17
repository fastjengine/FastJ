package io.github.lucasstarsz.fastj.game.ui;

import io.github.lucasstarsz.fastj.framework.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.framework.render.util.DrawUtil;
import io.github.lucasstarsz.fastj.framework.io.mouse.MouseActionListener;
import io.github.lucasstarsz.fastj.framework.math.Pointf;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class Button extends Polygon2D implements MouseActionListener {
    static final Pointf[] defaultBox = DrawUtil.createBox(0f, 0f, 100f, 20f);

    public Button() {
        this(defaultBox);
    }

    private Button(Pointf[] points) {
        super(points);
        super.setColor(Color.black);
        super.setScale(new Pointf(2f));
    }

    @Override
    public void onMousePressed(MouseEvent mouseEvent) {

    }
}
