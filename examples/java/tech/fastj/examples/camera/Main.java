package tech.fastj.examples.camera;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.display.RenderSettings;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.input.keyboard.Keyboard;
import tech.fastj.input.keyboard.KeyboardActionListener;
import tech.fastj.input.keyboard.Keys;
import tech.fastj.input.keyboard.events.KeyboardStateEvent;
import tech.fastj.math.Pointf;
import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {

    private static final float CameraSpeed = 400f;
    private Pointf inputTranslation;

    private Text2D controlsHint;
    private Pointf controlsHintOffset;

    @Override
    public void init(FastJCanvas canvas) {
        canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable);

        Polygon2D box1 = Polygon2D.fromPoints(DrawUtil.createBox(0f, 0f, 50f));
        Polygon2D box2 = Polygon2D.fromPoints(DrawUtil.createBox(canvas.getCanvasCenter(), 50f));
        Polygon2D box3 = Polygon2D.fromPoints(DrawUtil.createBox(canvas.getResolution().asPointf(), 50f));
        Polygon2D box4 = Polygon2D.fromPoints(DrawUtil.createBox(0f, canvas.getResolution().y, 50f));
        Polygon2D box5 = Polygon2D.fromPoints(DrawUtil.createBox(canvas.getCanvasCenter().multiply(1.5f), 50f));

        drawableManager().addGameObject(box1);
        drawableManager().addGameObject(box2);
        drawableManager().addGameObject(box3);
        drawableManager().addGameObject(box4);
        drawableManager().addGameObject(box5);

        controlsHint = Text2D.fromText("WASD to move the camera, R to reset.");
        controlsHintOffset = new Pointf(canvas.getResolution().x - controlsHint.width() - 30f, 10f);
        controlsHint.setTranslation(controlsHintOffset);
        drawableManager().addGameObject(controlsHint);

        inputTranslation = new Pointf();

        inputManager().addKeyboardActionListener(new KeyboardActionListener() {
            @Override
            public void onKeyRecentlyPressed(KeyboardStateEvent event) {
                if (event.isConsumed() || event.getKey() != Keys.R) {
                    return;
                }

                getCamera().reset();
                controlsHint.setTranslation(controlsHintOffset);
            }
        });
    }

    @Override
    public void update(FastJCanvas canvas) {
        pollMovement();
        moveCamera();
        resetTransformations();
    }

    private void pollMovement() {
        if (Keyboard.isKeyDown(Keys.A)) {
            inputTranslation.x += CameraSpeed * FastJEngine.getDeltaTime();
        }

        if (Keyboard.isKeyDown(Keys.D)) {
            inputTranslation.x -= CameraSpeed * FastJEngine.getDeltaTime();
        }

        if (Keyboard.isKeyDown(Keys.W)) {
            inputTranslation.y += CameraSpeed * FastJEngine.getDeltaTime();
        }

        if (Keyboard.isKeyDown(Keys.S)) {
            inputTranslation.y -= CameraSpeed * FastJEngine.getDeltaTime();
        }
    }

    private void moveCamera() {
        if (!inputTranslation.equals(Pointf.origin())) {
            getCamera().translate(inputTranslation);
            controlsHint.setTranslation(Pointf.multiply(getCamera().getTranslation(), -1f).add(controlsHintOffset));
        }
    }

    private void resetTransformations() {
        inputTranslation.reset();
    }

    public static void main(String[] args) {
        /* FastJ Camera */

        /* Controlling the camera in FastJ is similar to a player controller -- except all the
         * movement is inverted.
         * With that in mind, the best way to show this camera is by example. Try running the
         * program!
         */

        FastJEngine.init("Game Camera!", new Main());
        FastJEngine.run();
    }
}
