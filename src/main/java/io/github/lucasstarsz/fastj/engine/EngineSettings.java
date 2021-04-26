package io.github.lucasstarsz.fastj.engine;

import io.github.lucasstarsz.fastj.math.Point;

public class EngineSettings {
    public static final EngineSettings DefaultSettings = builder()
            .withFPSTarget(FastJEngine.DefaultFPS)
            .withUPSTarget(FastJEngine.DefaultUPS)
            .withViewerResolution(FastJEngine.DefaultWindowResolution)
            .withInternalResolution(FastJEngine.DefaultInternalResolution)
            .withHardwareAcceleration(HWAccel.DEFAULT)
            .build();

    private static final EngineSettingsBuilder SettingsBuilder = new EngineSettingsBuilder();

    private final int initialFramesPerSecond;
    private final int initialUpdatesPerSecond;
    private final Point initialViewerResolution;
    private final Point initialInternalResolution;
    private final HWAccel hardwareAccelerationType;

    EngineSettings(int initialFramesPerSecond, int initialUpdatesPerSecond, Point initialViewerResolution, Point initialInternalResolution, HWAccel hardwareAccelerationType) {
        this.initialFramesPerSecond = initialFramesPerSecond;
        this.initialUpdatesPerSecond = initialUpdatesPerSecond;
        this.initialViewerResolution = initialViewerResolution;
        this.initialInternalResolution = initialInternalResolution;
        this.hardwareAccelerationType = hardwareAccelerationType;
    }

    public int initialFramesPerSecond() {
        return initialFramesPerSecond;
    }

    public int initialUpdatesPerSecond() {
        return initialUpdatesPerSecond;
    }

    public Point initialViewerResolution() {
        return initialViewerResolution;
    }

    public Point initialInternalResolution() {
        return initialInternalResolution;
    }

    public HWAccel hardwareAccelerationType() {
        return hardwareAccelerationType;
    }

    public static EngineSettingsBuilder builder() {
        return SettingsBuilder;
    }
}
