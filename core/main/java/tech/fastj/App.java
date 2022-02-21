package tech.fastj;

import tech.fastj.feature.AppFeature;
import tech.fastj.feature.CleanupFeature;
import tech.fastj.feature.Feature;
import tech.fastj.feature.GameLoopFeature;
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
    private final Map<Class<? extends GameLoopFeature>, GameLoopFeature> gameLoopFeatures;
    private final Map<Class<? extends StartupFeature>, StartupFeature> startupFeatures;
    private final Map<Class<? extends CleanupFeature>, CleanupFeature> cleanupFeatures;

    private volatile boolean isRunning;

    protected App() {
        features = new LinkedHashMap<>(16, 0.75f, true);
        gameLoopFeatures = new LinkedHashMap<>(16, 0.75f, true);
        startupFeatures = new LinkedHashMap<>(16, 0.75f, true);
        cleanupFeatures = new LinkedHashMap<>(16, 0.75f, true);
        isRunning = false;
    }

    /**
     * Whether the app is currently running.
     * <p>
     * This may be true regardless of whether the app should be running (i.e. the engine is still considered running
     * while in the process of closing.)
     * <p>
     * It may take a while before it is able to completely stop running.
     *
     * @return Whether the app is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Gracefully stops the app's execution.
     *
     * @param shouldCleanup        Determines whether {@link CleanupFeature}s should be activated.
     * @param shouldUnloadFeatures Determines whether {@link Feature#unload(App) standard features should be unloaded}.
     */
    public void stop(boolean shouldCleanup, boolean shouldUnloadFeatures) {
        // TODO: stop run threads
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

    @SuppressWarnings("unchecked")
    public <T extends GameLoopFeature> T getGameLoopFeature(Class<T> gameLoopFeatureClass) {
        return (T) gameLoopFeatures.get(gameLoopFeatureClass);
    }

    @Override
    public synchronized void run() {
        isRunning = true;

        // TODO: display app start

        // startup
        for (StartupFeature startupFeature : startupFeatures.values()) {
            startupFeature.cleanup(this);
        }
        for (Feature feature : features.values()) {
            feature.load(this);
        }

        // game runtime
        for (GameLoopFeature gameLoopFeature : gameLoopFeatures.values()) {
            // TODO: add run threads
            // TODO: run game loop features
        }

        // cleanup
        for (CleanupFeature cleanupFeature : cleanupFeatures.values()) {
            cleanupFeature.cleanup(this);
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
            // Since game loop features are a subset of features, this protects for both cases.
            return;
        }

        T feature = instantiateFeature(featureClass);

        if (isMissingDependencies(feature)) {
            throw new IllegalArgumentException("Cannot add feature " + featureClass.getName()
                    + ", because it is missing the following dependencies: " + getMissingDependencies(this, feature)
            );
        }

        features.put(featureClass, feature);

        if (feature instanceof GameLoopFeature gameLoopFeature) {
            // TODO: debug log of game loop feature detected
            gameLoopFeatures.put(gameLoopFeature.getClass(), gameLoopFeature);
        }
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
     *
     * @param appClass The class of the app to create. Bound by generic type {@code T}.
     * @param args     The arguments for constructing the app. <b>Does not yet support primitives.</b>
     * @param <T>      The type of the {@link App} to create. {@code T} must extend {@link App}.
     * @return The app builder.
     */
    public static <T extends App> AppHelper<T> create(Class<T> appClass, Object... args) {
        return new AppHelper<>(appClass, args);
    }
}
