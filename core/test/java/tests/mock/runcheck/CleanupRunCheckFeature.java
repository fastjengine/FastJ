package tests.mock.runcheck;

import tech.fastj.App;
import tech.fastj.feature.CleanupFeature;

public class CleanupRunCheckFeature implements CleanupFeature {

    private Boolean isAppRunningOnCleanup;

    public Boolean isAppRunningOnCleanup() {
        return isAppRunningOnCleanup;
    }

    @Override
    public void cleanup(App app) {
        isAppRunningOnCleanup = app.isRunning();
    }
}
