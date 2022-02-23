package tests.mock.callcheck;

import tech.fastj.App;

import tech.fastj.feature.CleanupFeature;

public class CleanupCallCheckFeature implements CleanupFeature {

    private boolean wasCalled;

    public boolean wasCalled() {
        return wasCalled;
    }

    @Override
    public void cleanup(App app) {
        wasCalled = true;
    }
}
