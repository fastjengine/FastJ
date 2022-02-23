package tests.mock.runcheck;

import tech.fastj.App;

import tech.fastj.feature.StartupFeature;

public class StartupRunCheckFeature implements StartupFeature {

    private Boolean isAppRunningOnStartup;

    public Boolean isAppRunningOnStartup() {
        return isAppRunningOnStartup;
    }

    @Override
    public void startup(App app) {
        isAppRunningOnStartup = app.isRunning();
    }
}
