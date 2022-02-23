package tests.mock.simpleapp;

import tech.fastj.App;

import tech.fastj.feature.GameLoopFeature;

import java.util.concurrent.TimeUnit;

public class SimpleTimedGameLoopFeature implements GameLoopFeature {

    public static final int DefaultLoopTime = 1;
    public static final TimeUnit DefaultLoopTimeUnit = TimeUnit.SECONDS;
    private Exception interruptedException;
    private boolean finishedSleep;

    public Exception getInterruptedException() {
        return interruptedException;
    }

    public boolean isFinishedSleeping() {
        return finishedSleep;
    }

    @Override
    public void load(App app) {
    }

    @Override
    public void unload(App app) {
    }

    @Override
    public void gameLoop(App app) {
        try {
            System.out.println("sleep time");
            DefaultLoopTimeUnit.sleep(DefaultLoopTime);
            finishedSleep = true;
        } catch (InterruptedException exception) {
            interruptedException = exception;
            Thread.currentThread().interrupt();
        }
    }
}
