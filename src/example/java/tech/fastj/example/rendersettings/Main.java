package tech.fastj.example.rendersettings;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.display.RenderSettings;
import tech.fastj.graphics.game.Text2D;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
        /* RenderSettings */

        /* FastJ provides a way to modify global rendering options through the Display class. Using
         * Display.modifyRenderSettings(RenderSettings), we can change how FastJ renders Drawables.
         *
         * FastJ offers the following types of rendering settings that can be modified in this way:
         * - Anti-Aliasing
         * - Text Anti-Aliasing
         * - General Rendering Quality
         * - Color Rendering Quality
         * - Image Interpolation Style
         * - Alpha Interpolation Quality
         * - Outline Rendering Style
         * - Image Variant Resolution
         * - Text LCD Contrast
         * - Dithering
         * - Subpixel Text Rendering
         *
         * The RenderSettings class provides a very easy way to access these settings. */

        /* For this example, we will enable text anti-aliasing and set general rendering quality to high.
         * To do so, we will use RenderSettings.TextAntialiasing.Enable and RenderSettings.GeneralRenderingQuality.High.
         *
         * Feel free to mess around with other render settings to help you determine what they do -- don't forget to
         * look at their documentation as well. */

        display.modifyRenderSettings(RenderSettings.TextAntialiasing.Enable);
        display.modifyRenderSettings(RenderSettings.GeneralRenderingQuality.High);


        /* As a small aside, I've added a Text2D object to help the visualization of how settings affect a program's
         * final look.
         * This code is not the primary focus of the example -- this is just to give a visual. */
        Text2D visualAid = Text2D.create("Render Settings are useful! (-.-)")
                .withTransform(new Pointf(100f, 100f), Transform2D.DefaultRotation, new Pointf(3.0f, 3.0f))
                .build();
        drawableManager.addGameObject(visualAid);
    }

    @Override
    public void update(Display display) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Render Settings!", new Main());
        FastJEngine.run();
    }
}
