package tests.mock.simpleapp;

import tech.fastj.App;
import tech.fastj.feature.GameLoopFeature;

public class SimpleGameLoopFeature implements GameLoopFeature {

    @Override
    public void load(App app) {
        System.out.println("Simple game loop feature loaded.");
    }

    @Override
    public void unload(App app) {
        System.out.println("Simple game loop feature unloaded.");
    }

    @Override
    public void gameLoop(App app) {
        System.out.println("Simple game loop started.");

        // game loop

        System.out.println("Simple game loop ended.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
