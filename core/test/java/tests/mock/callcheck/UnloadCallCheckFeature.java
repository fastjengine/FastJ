package tests.mock.callcheck;

import tech.fastj.App;

import tech.fastj.feature.Feature;

public class UnloadCallCheckFeature implements Feature {

    private boolean wasCalled;

    public boolean wasCalled() {
        return wasCalled;
    }

    @Override
    public void load(App app) {
    }

    @Override
    public void unload(App app) {
        wasCalled = true;
    }
}
