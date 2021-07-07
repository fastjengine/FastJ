package tech.fastj.example.engineconfig;

import tech.fastj.engine.FastJEngine;
import tech.fastj.engine.HWAccel;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.display.Display;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {
    @Override
    public void init(Display display) {
        /* As a small aside, this is a small inclusion in order to show how configuring FastJ
         * affects rendering.
         * The code is not the primary focus of the example -- this is just to give a visual. */
        Pointf[] squareMesh = DrawUtil.createBox(50f, 50f, 100f);
        Polygon2D square = Polygon2D.fromPoints(squareMesh);
        drawableManager.addGameObject(square);
    }

    @Override
    public void update(Display display) {
        // Empty -- this example does not make use of this method.
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, Configuration!", new Main());


        /* FastJEngine Configuration */

        /* When using the other examples or FastJ in general, you may have noticed that the game
         * engine defaults to a 1280*720 window. This is part of FastJEngine's default
         * configuration.
         *
         * With that said, let's get started with the first two types of configuration:
         * 1. configureViewerResolution
         * 2. configureInternalResolution
         *
         * Note 1: Both of these configurations require use of a Point. A Point is similar to that
         * of a Pointf, but it only allows integer values.
         * Note 2: Configurations should always be done after initializing the game engine.
         */


        /* "FastJEngine#configureViewerResolution" configures the viewer resolution -- the size of
         * the window that the player plays in.
         * By default, this is configured to 720p -- 1280*720. Of course, you can change this to
         * any set of values you would like, as long as both values are at least 1.
         *
         * For this example, I've set it to 640*480. Feel free to mess around with the numbers! */
        FastJEngine.configureViewerResolution(new Point(640, 480));

        /* "FastJEngine#configureInternalResolution" configures the internal resolution -- the size
         * of the actual game canvas (where the game gets rendered).
         * By default, this is also configured to 720p -- 1280*720. Like the viewer resolution,
         * this can be set to any values that are at least 1.
         *
         * For this example, I've set this to 640*480. Feel free to mess around with the numbers! */
        FastJEngine.configureInternalResolution(new Point(640, 480));


        /* Now, we'll move onto configureHardwareAcceleration.
         * By making use of java2d, FastJ supports a few hardware-accelerated graphics APIs:
         * - OpenGL
         * - Direct3D
         *
         * With that in mind, "FastJEngine#configureHardwareAcceleration" allows you to configure
         * the type of hardware acceleration your game uses. This is set using the "HWAccel" enum.
         *
         * For this example, we're going to set the hardware acceleration to OpenGL. */
        FastJEngine.configureHardwareAcceleration(HWAccel.OpenGL);


        /* Lastly, FPS and UPS configuration.
         *
         * - FPS: Frames Per Second (how many times the game renders to the screen in a second)
         * - UPS: Updates Per Second (how many times the game updates in a second)
         *
         * FPS can be configured with "FastJEngine#setTargetFPS".
         * UPS can be configured with "FastJEngine#setTargetUPS".
         *
         * FPS defaults to the refresh rate of your default monitor -- check your monitor's specifications to determine
         * what this value is. On the other hand, UPS defaults to 60. */
        FastJEngine.setTargetFPS(60);
        FastJEngine.setTargetUPS(30);

        FastJEngine.run();
    }
}
