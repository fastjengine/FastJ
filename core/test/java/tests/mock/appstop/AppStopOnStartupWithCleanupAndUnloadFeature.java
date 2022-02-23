package tests.mock.appstop;

import tech.fastj.App;
import tech.fastj.feature.StartupFeature;

import java.util.List;

public class AppStopOnStartupWithCleanupAndUnloadFeature implements StartupFeature {

    private List<Runnable> runnables;

    public List<Runnable> getRunnables() {
        return runnables;
    }

    @Override
    public void startup(App app) {
        runnables = app.stop(true, true);
    }
}
