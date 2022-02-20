package tech.fastj;

import tech.fastj.feature.CleanupFeature;
import tech.fastj.feature.Feature;
import tech.fastj.feature.StartupFeature;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * TODO: Documentation
 * TODO: Handle App Features and Services
 *
 * @author Andrew Dey
 */
public class AppHelper<T extends App> {

    private final Class<T> appClass;
    private final Object[] appArgs;
    private final Set<Class<? extends Feature>> features = new LinkedHashSet<>();
    private final Set<Class<? extends StartupFeature>> startupFeatures = new LinkedHashSet<>();
    private final Set<Class<? extends CleanupFeature>> cleanupFeatures = new LinkedHashSet<>();

    AppHelper(Class<T> appClass, Object... appArgs) {
        this.appClass = appClass;
        this.appArgs = appArgs;
    }

    public AppHelper<T> withFeature(Class<? extends Feature> featureClass) {
        if (!features.add(featureClass)) {
            // TODO: warn of duplicate feature
        }
        return this;
    }

    public AppHelper<T> withStartupFeature(Class<? extends StartupFeature> startupFeatureClass) {
        if (!startupFeatures.add(startupFeatureClass)) {
            // TODO: warn of duplicate feature
        }
        return this;
    }

    public AppHelper<T> withCleanupFeature(Class<? extends CleanupFeature> cleanupFeatureClass) {
        if (!cleanupFeatures.add(cleanupFeatureClass)) {
            // TODO: warn of duplicate feature
        }
        return this;
    }

    public T build() {
        T app = instantiateApp(appClass, appArgs);
        for (var feature : features) {
            app.addFeature(feature);
        }
        for (var startupFeature : startupFeatures) {
            app.addStartupFeature(startupFeature);
        }
        for (var cleanupFeature : cleanupFeatures) {
            app.addCleanupFeature(cleanupFeature);
        }
        return app;
    }

    private static <T extends App> T instantiateApp(Class<T> appClass, Object... args) {
        try {
            Class<?>[] argsClasses = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                argsClasses[i] = args[i].getClass();
            }

            return appClass.getConstructor(argsClasses).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            // TODO: log error
            throw new IllegalStateException("Error while creating app " + appClass.getName(), exception);
        }
    }
}
