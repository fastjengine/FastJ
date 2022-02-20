package tests.unit;

import org.junit.jupiter.api.Test;
import tech.fastj.App;
import tests.integration.mock.simpleapp.SimpleApp;
import tests.integration.mock.simpleapp.SimpleCleanupFeature;
import tests.integration.mock.simpleapp.SimpleFeature;
import tests.integration.mock.simpleapp.SimpleStartupFeature;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTests {

    @Test
    void checkAppStartsNotRunning() {
        SimpleApp app = App.create(SimpleApp.class).build();
        assertFalse(app.isRunning(), "App should not be running.");
        assertFalse(app.shouldRun(), "App should not need to run yet -- it has not been told to run.");
    }

    @Test
    void checkAppAddsFeatures() {
        SimpleApp app = App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withStartupFeature(SimpleStartupFeature.class)
                .withCleanupFeature(SimpleCleanupFeature.class)
                .build();

        assertNotNull(app.getFeature(SimpleFeature.class), "The feature should have been added.");
        assertNotNull(app.getStartupFeature(SimpleStartupFeature.class), "The startup feature should have been added.");
        assertNotNull(app.getCleanupFeature(SimpleCleanupFeature.class), "The cleanup feature should have been added.");
    }
}
