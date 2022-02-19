package tech.fastj;

import java.util.Map;

/**
 * TODO: Documentation
 *
 * @author Andrew Dey
 */
public abstract class App implements Runnable {
    private Map<Class<? extends Feature>, Feature> features;



    public static AppBuilder create(App app) {
        return new AppBuilder(app);
    }
}
