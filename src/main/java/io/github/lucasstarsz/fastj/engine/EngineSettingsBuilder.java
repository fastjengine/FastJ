package io.github.lucasstarsz.fastj.engine;

import io.github.lucasstarsz.fastj.math.Point;

public class EngineSettingsBuilder {

    private int initialFramesPerSecond;
    private int initialUpdatesPerSecond;
    private Point initialViewerResolution;
    private Point initialInternalResolution;
    private HWAccel hardwareAccelerationType;
    
    EngineSettingsBuilder() {
    }

    public EngineSettingsBuilder withFPSTarget(int fpsTarget) {
        initialFramesPerSecond = fpsTarget;
        return this;
    }

    public EngineSettingsBuilder withUPSTarget(int upsTarget) {
        initialUpdatesPerSecond = upsTarget;
        return this;
    }

    public EngineSettingsBuilder withViewerResolution(Point viewerResolution) {
        if ((viewerResolution.x | viewerResolution.y) < 1) {
            FastJEngine.error(CrashMessages.CONFIGURATION_ERROR.errorMessage, new IllegalArgumentException("Viewer resolution values must be at least 1."));
        }

        initialViewerResolution = viewerResolution;
        return this;
    }

    public EngineSettingsBuilder withInternalResolution(Point internalResolution) {
        if ((internalResolution.x | internalResolution.y) < 1) {
            FastJEngine.error(CrashMessages.CONFIGURATION_ERROR.errorMessage, new IllegalArgumentException("internal resolution values must be at least 1."));
        }

        initialInternalResolution = internalResolution;
        return this;
    }

    public EngineSettingsBuilder withHardwareAcceleration(HWAccel hardwareAcceleration) {
        hardwareAccelerationType = hardwareAcceleration;
        return this;
    }

    public EngineSettings build() {
        EngineSettings engineSettings = new EngineSettings(
                initialFramesPerSecond,
                initialUpdatesPerSecond,
                initialViewerResolution.copy(),
                initialInternalResolution.copy(),
                hardwareAccelerationType
        );

        initialFramesPerSecond = 0;
        initialUpdatesPerSecond = 0;
        initialViewerResolution.reset();
        initialInternalResolution.reset();
        hardwareAccelerationType = null;

        return engineSettings;
    }
}
