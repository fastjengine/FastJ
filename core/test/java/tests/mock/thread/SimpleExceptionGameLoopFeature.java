package tests.mock.thread;

import tech.fastj.App;

import tech.fastj.feature.GameLoopFeature;

public class SimpleExceptionGameLoopFeature implements GameLoopFeature {

    @Override
    public void load(App app) {
        System.out.println("Simple exception-throwing game loop feature loaded.");
    }

    @Override
    public void unload(App app) {
        System.out.println("Simple exception-throwing game loop feature unloaded.");
    }

    @Override
    public void gameLoop(App app) {
        System.out.println("Running exception-throwing game loop.");
        throw new RuntimeException();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
