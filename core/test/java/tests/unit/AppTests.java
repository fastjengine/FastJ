package tests.unit;

import tech.fastj.App;

import tests.mock.constructorargs.MultiConstructorApp;
import tests.mock.constructorargs.SingleConstructorApp;
import tests.mock.simpleapp.SimpleApp;
import tests.mock.simpleapp.SimpleCleanupFeature;
import tests.mock.simpleapp.SimpleDependentFeature;
import tests.mock.simpleapp.SimpleFeature;
import tests.mock.simpleapp.SimpleGameLoopFeature;
import tests.mock.simpleapp.SimpleStartupFeature;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppTests {

    @Test
    void checkAppStartsNotRunning() {
        SimpleApp app = App.create(SimpleApp.class).build();
        assertFalse(app.isRunning(), "App should not be running.");
    }

    @Test
    void checkAppBuilderAddsFeatures() {
        SimpleApp app = App.create(SimpleApp.class)
                .withFeature(SimpleFeature.class)
                .withFeature(SimpleGameLoopFeature.class)
                .withStartupFeature(SimpleStartupFeature.class)
                .withCleanupFeature(SimpleCleanupFeature.class)
                .build();

        assertNotNull(app.getFeature(SimpleFeature.class), "The feature should have been added.");
        assertNotNull(app.getGameLoopFeature(SimpleGameLoopFeature.class), "The game loop feature should have been added.");
        assertNotNull(app.getStartupFeature(SimpleStartupFeature.class), "The startup feature should have been added.");
        assertNotNull(app.getCleanupFeature(SimpleCleanupFeature.class), "The cleanup feature should have been added.");
    }

    @Test
    void tryAddAppFeature_butIsMissingDependency() {
        Throwable error = assertThrows(
                IllegalArgumentException.class, () -> App.create(SimpleApp.class)
                        .withFeature(SimpleDependentFeature.class)
                        .build()
        );

        String expectedMissingDependencyErrorMessage = "Cannot add feature " + SimpleDependentFeature.class.getName()
                + ", because it is missing the following dependencies: " + Set.of(SimpleFeature.class);
        assertEquals(
                error.getMessage(),
                expectedMissingDependencyErrorMessage,
                "The error message for a missing dependency should match."
        );
    }

    @Test
    void checkAppBuilderUsesSingleConstructor() {
        String arg1 = UUID.randomUUID().toString();
        String arg2 = UUID.randomUUID().toString();
        int arg3 = UUID.randomUUID().hashCode();
        SingleConstructorApp app = App.create(SingleConstructorApp.class, arg1, arg2, arg3).build();

        assertEquals(arg1, app.arg1, "The first argument should match the first input argument.");
        assertEquals(arg2, app.arg2, "The second argument should match the second input argument.");
        assertEquals(arg3, app.arg3, "The third argument should match the third input argument.");
    }

    @Test
    void checkAppBuilderUsesMultiConstructor_withNoArgs() {
        MultiConstructorApp app = App.create(MultiConstructorApp.class).build();

        assertEquals(MultiConstructorApp.DefaultArgValue, app.arg1, "The first argument should match the default argument value.");
        assertEquals(MultiConstructorApp.DefaultArgValue, app.arg2, "The second argument should match the default argument value.");
        assertEquals(MultiConstructorApp.DefaultArgValue, app.arg3, "The third argument should match the default argument value.");
    }

    @Test
    void checkAppBuilderUsesMultiConstructor_withOneArg() {
        String arg1 = UUID.randomUUID().toString();
        MultiConstructorApp app = App.create(MultiConstructorApp.class, arg1).build();

        assertEquals(arg1, app.arg1, "The first argument should match the first input argument.");
        assertEquals(MultiConstructorApp.DefaultArgValue, app.arg2, "The second argument should match the default argument value.");
        assertEquals(MultiConstructorApp.DefaultArgValue, app.arg3, "The third argument should match the default argument value.");
    }

    @Test
    void checkAppBuilderUsesMultiConstructor_withTwoArgs() {
        String arg1 = UUID.randomUUID().toString();
        String arg2 = UUID.randomUUID().toString();
        MultiConstructorApp app = App.create(MultiConstructorApp.class, arg1, arg2).build();

        assertEquals(arg1, app.arg1, "The first argument should match the first input argument.");
        assertEquals(arg2, app.arg2, "The second argument should match the second input argument.");
        assertEquals(MultiConstructorApp.DefaultArgValue, app.arg3, "The third argument should match the default argument value.");
    }

    @Test
    void checkAppBuilderUsesMultiConstructor_withThreeArgs() {
        String arg1 = UUID.randomUUID().toString();
        String arg2 = UUID.randomUUID().toString();
        String arg3 = UUID.randomUUID().toString();
        MultiConstructorApp app = App.create(MultiConstructorApp.class, arg1, arg2, arg3).build();

        assertEquals(arg1, app.arg1, "The first argument should match the first input argument.");
        assertEquals(arg2, app.arg2, "The second argument should match the second input argument.");
        assertEquals(arg3, app.arg3, "The third argument should match the third input argument.");
    }
}
