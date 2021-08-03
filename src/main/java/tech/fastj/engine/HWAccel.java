package tech.fastj.engine;

/** Enumerator defining what types of hardware acceleration are supported. */
public enum HWAccel {
    /**
     * Enables Direct 3D Draw hardware acceleration.
     * <p>
     * <b>NOTE:</b> This is only supported on Windows machines.
     */
    Direct3D("d3d", "transaccel", "ddforcevram"),
    /** Enables OpenGL hardware acceleration. */
    OpenGL("opengl"),
    /** Enables X11 hardware acceleration. */
    X11("x11-based"),
    /** Leaves the configuration of hardware acceleration as the OS-decided default. */
    Default(),
    /** Disables all hardware acceleration. Instead, software rendering will be used. */
    CpuRender("noddraw");

    private final String[] hardwareProperties;

    HWAccel(String... properties) {
        hardwareProperties = properties;
    }

    /**
     * Sets the hardware acceleration to the specified {@link HWAccel} parameter.
     * <p>
     * If for some reason you want to do some reflection to try and call this method while the game engine is running,
     * let me save you some time. Regardless of calling this method, it will never affect the hardware acceleration
     * being used after any form of {@code FastJEngine#init} is called.
     *
     * @param accelType The type of hardware acceleration to use.
     */
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
