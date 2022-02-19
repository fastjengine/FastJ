package tech.fastj;

/**
 * TODO: Documentation
 * TODO: Handle App Features and Services
 *
 * @author Andrew Dey
 */
public class AppBuilder {
    private final App app;

    AppBuilder(App app) {
        this.app = app;
    }

    public App build() {
        return app;
    }
}
