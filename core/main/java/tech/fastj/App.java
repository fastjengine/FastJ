package tech.fastj;

import tech.fastj.feature.AppFeature;
import tech.fastj.feature.CleanupFeature;
import tech.fastj.feature.Feature;
import tech.fastj.feature.StartupFeature;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: Documentation
 *
 * @author Andrew Dey
 */
public abstract class App implements Runnable {

    private final Map<Class<? extends Feature>, Feature> features;
    private final Map<Class<? extends StartupFeature>, StartupFeature> startupFeatures;
    private final Map<Class<? extends CleanupFeature>, CleanupFeature> cleanupFeatures;

    private volatile boolean isRunning;
    private volatile boolean shouldRun;

    protected App() {
        features = new LinkedHashMap<>(16, 0.75f, true);
        startupFeatures = new LinkedHashMap<>(16, 0.75f, true);
        cleanupFeatures = new LinkedHashMap<>(16, 0.75f, true);
        isRunning = false;
        shouldRun = false;
    }

    /**
     * Sets whether the app should run.
     * <p>
     * If this is set to {@code false}, the app will attempt to stop running gracefully -- any remaining update/render calls will be
     * concluded before the app's game loop exits. <b>This does not cause the JVM to exit.</b>
     *
     * @param shouldRun Sets whether the app should run.
     */
    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

    /**
     * Whether the app is currently running.
     * <p>
     * This may be true regardless of whether the app {@link #shouldRun() should} be running.
     * <p>
     * When the app is told to stop running through {@link #setShouldRun(boolean) boolean setting} or from a game crash, it may take
     * a while before it is able to completely stop running.
     *
     * @return Whether the app is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Whether the app should be running.
     * <p>
     * You can tell the app to stop running with {@link #setShouldRun(boolean)}.
     *
     * @return Whether the app should be running.
     */
    public boolean shouldRun() {
        return shouldRun;
    }

    @SuppressWarnings("unchecked")
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        return (T) features.get(featureClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends StartupFeature> T getStartupFeature(Class<T> startupFeatureClass) {
        return (T) startupFeatures.get(startupFeatureClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends CleanupFeature> T getCleanupFeature(Class<T> cleanupFeatureClass) {
        return (T) cleanupFeatures.get(cleanupFeatureClass);
    }

    @Override
    public synchronized void run() {
        isRunning = true;

        // TODO: display app start

        // startup
        for (StartupFeature startupFeature : startupFeatures.values()) {
            startupFeature.load(this);
            startupFeature.unload(this);
        }
        for (Feature feature : features.values()) {
            feature.load(this);
        }

        // game runtime
        while (shouldRun) {
            // TODO: implement game loop
        }

        // cleanup
        for (CleanupFeature cleanupFeature : cleanupFeatures.values()) {
            cleanupFeature.load(this);
            cleanupFeature.unload(this);
        }
        for (Feature feature : features.values()) {
            feature.unload(this);
        }

        // TODO: display app end

        isRunning = false;
    }

    <T extends StartupFeature> void addStartupFeature(Class<T> startupFeatureClass) {
        if (startupFeatures.get(startupFeatureClass) != null) {
            // TODO: warn of feature already added
            return;
        }

        T startupFeature = instantiateFeature(startupFeatureClass);

        if (isMissingDependencies(startupFeature)) {
            throw new IllegalArgumentException("Cannot add feature " + startupFeatureClass.getName()
                    + ", because it is missing the following dependencies: " + getMissingDependencies(this, startupFeature)
            );
        }

        startupFeatures.put(startupFeatureClass, startupFeature);
    }

    <T extends CleanupFeature> void addCleanupFeature(Class<T> cleanupFeatureClass) {
        if (cleanupFeatures.get(cleanupFeatureClass) != null) {
            // TODO: warn of feature already added
            return;
        }

        T cleanupFeature = instantiateFeature(cleanupFeatureClass);

        if (isMissingDependencies(cleanupFeature)) {
            throw new IllegalArgumentException("Cannot add feature " + cleanupFeatureClass.getName()
                    + ", because it is missing the following dependencies: " + getMissingDependencies(this, cleanupFeature)
            );
        }

        cleanupFeatures.put(cleanupFeatureClass, cleanupFeature);
    }

    <T extends Feature> void addFeature(Class<T> featureClass) {
        if (features.get(featureClass) != null) {
            // TODO: warn of feature already added
            return;
        }

        T feature = instantiateFeature(featureClass);

        if (isMissingDependencies(feature)) {
            throw new IllegalArgumentException("Cannot add feature " + featureClass.getName()
                    + ", because it is missing the following dependencies: " + getMissingDependencies(this, feature)
            );
        }

        features.put(featureClass, feature);
    }

    private static <T extends AppFeature> T instantiateFeature(Class<T> featureClass) {
        try {
            // TODO: add support for constructor args
            return featureClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            // TODO: log error
            throw new IllegalStateException("Error while creating feature " + featureClass.getName(), exception);
        }
    }

    private static Set<Class<? extends AppFeature>> getMissingDependencies(App app, AppFeature appFeature) {
        return appFeature.dependencies().stream()
                .filter(dependency -> !app.features.containsKey(dependency))
                .filter(dependency -> !app.startupFeatures.containsKey(dependency))
                .filter(dependency -> !app.cleanupFeatures.containsKey(dependency))
                .collect(Collectors.toSet());
    }

    private boolean isMissingDependencies(AppFeature appFeature) {
        for (var dependency : appFeature.dependencies()) {
            if (!features.containsKey(dependency)
                    && !startupFeatures.containsKey(dependency)
                    && !cleanupFeatures.containsKey(dependency)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create an {@link AppHelper} to assist in creating an {@link App}.
     * @param appClass The class of the app to create. Bound by generic type {@code T}.
     * @param args The arguments for constructing the app. <b>Does not yet support primitives.</b>
     * @param <T> The type of the {@link App} to create. {@code T} must extend {@link App}.
     * @return The app builder.
     */
    public static <T extends App> AppHelper<T> create(Class<T> appClass, Object... args) {
        return new AppHelper<>(appClass, args);
    }
}
