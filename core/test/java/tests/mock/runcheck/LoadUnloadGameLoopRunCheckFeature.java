package tests.mock.runcheck;

import tech.fastj.App;
import tech.fastj.feature.GameLoopFeature;

public class LoadUnloadGameLoopRunCheckFeature implements GameLoopFeature {

    private Boolean isAppRunningWhenLoading;
    private Boolean isAppRunningWhenUnloading;
    private Boolean isAppRunningDuringGameLoop;

    public Boolean isAppRunningWhenLoading() {
        return isAppRunningWhenLoading;
    }

    public Boolean isAppRunningWhenUnloading() {
        return isAppRunningWhenUnloading;
    }

    public Boolean isAppRunningDuringGameLoop() {
        return isAppRunningDuringGameLoop;
    }

    @Override
    public void load(App app) {
        isAppRunningWhenLoading = app.isRunning();
    }

    @Override
    public void unload(App app) {
        isAppRunningWhenUnloading = app.isRunning();
    }

    @Override
    public void gameLoop(App app) {
        isAppRunningDuringGameLoop = app.isRunning();
    }
}
