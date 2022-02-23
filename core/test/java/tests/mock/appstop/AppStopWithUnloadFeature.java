package tests.mock.appstop;

import tech.fastj.App;

import tech.fastj.feature.GameLoopFeature;

import java.util.List;

public class AppStopWithUnloadFeature implements GameLoopFeature {

    private List<Runnable> runnables;

    public List<Runnable> getRunnables() {
        return runnables;
    }

    @Override
    public void load(App app) {
    }

    @Override
    public void unload(App app) {
    }

    @Override
    public void gameLoop(App app) {
        runnables = app.stop(false, true);
    }
}
