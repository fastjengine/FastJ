package io.github.lucasstarsz.fastj.engine;

/** Enumerator defining what types of hardware acceleration are supported. */
public enum HWAccel {
    /**
     * Enables Direct 3D Draw hardware acceleration.
     *
     * <b>NOTE:</b> This is only supported on Windows machines.
     */
    DIRECT3D("d3d", "transaccel", "ddforcevram"),
    /** Enables OpenGL hardware acceleration. */
    OPENGL("opengl"),
    /** Leaves the configuration of hardware acceleration as the OS-decided default. */
    DEFAULT(),
    /** Disables all hardware acceleration. Instead, software rendering will be used. */
    CPU_RENDER("noddraw");

    private final String[] hardwareProperties;

    HWAccel(String... properties) {
        hardwareProperties = properties;
    }

    static void setHardwareAcceleration(HWAccel accelType) {
        for (HWAccel accel : HWAccel.values()) {
            if (accel != accelType) {
                for (String property : accel.hardwareProperties) {
                    System.setProperty("sun.java2d." + property, "False");
                }
            }
        }
        for (String property : accelType.hardwareProperties) {
            System.setProperty("sun.java2d." + property, "True");
        }
    }
}
