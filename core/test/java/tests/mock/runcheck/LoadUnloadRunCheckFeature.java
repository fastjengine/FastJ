package tests.mock.runcheck;

import tech.fastj.App;

import tech.fastj.feature.Feature;

public class LoadUnloadRunCheckFeature implements Feature {

    private Boolean isAppRunningWhenLoading;
    private Boolean isAppRunningWhenUnloading;

    public Boolean isAppRunningWhenLoading() {
        return isAppRunningWhenLoading;
    }

    public Boolean isAppRunningWhenUnloading() {
        return isAppRunningWhenUnloading;
    }

    @Override
    public void load(App app) {
        isAppRunningWhenLoading = app.isRunning();
    }

    @Override
    public void unload(App app) {
        isAppRunningWhenUnloading = app.isRunning();
    }
}
