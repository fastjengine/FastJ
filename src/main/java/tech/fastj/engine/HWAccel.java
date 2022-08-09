package tech.fastj.engine;

/** Enumerator defining what types of hardware acceleration are supported in FastJ. */
public enum HWAccel {
    /**
     * Enables Direct 3D Draw hardware acceleration.
     * <p>
     * <b>NOTE:</b> This is only supported on Windows machines.
     */
    Direct3D("d3d", "transaccel", "ddforcevram"),
    /**
     * Enables X11 hardware acceleration.
     * <p>
     * <b>NOTE:</b> This is only supported on Linux machines.
     */
    X11("xrender"),
    /**
     * Enables Metal pipeline hardware acceleration.
     * <p>
     * <b>NOTE:</b> This is only supported on macOS machines.
     */
    Metal("metal"),
    /** Enables OpenGL hardware acceleration, which is available for most platforms. */
    OpenGL("opengl"),
    /** Disables all hardware acceleration. Instead, software rendering will be used which is available for all platforms. */
    CpuRender("noddraw"),
    /**
     * Leaves the configuration of hardware acceleration as the OS-decided default.
     * <p>
     * For <b>most</b> systems, this will usually be {@link #Direct3D Direct3D} for Windows, {@link #X11 xrender} for Linux, and
     * {@link #Metal Metal} for macOS.
     */
    Default();

    private final String[] hardwareProperties;

    HWAccel(String... properties) {
        hardwareProperties = properties;
    }

    /**
     * Sets the hardware acceleration to the specified {@link HWAccel} parameter.
     * <p>
     * If for some reason you want to do some reflection to try and call this method while the game engine is running, let me save you some
     * time. Regardless of calling this method, it will never affect the hardware acceleration being used after any form of java2d/AWT/Swing
     * method is called.
     *
     * @param accelType The type of hardware acceleration to use.
     */
    static void setHardwareAcceleration(HWAccel accelType) {
        for (HWAccel accel : HWAccel.values()) {
            if (accel == accelType) {
                continue;
            }

            for (String property : accel.hardwareProperties) {
                System.setProperty("sun.java2d." + property, "False");
            }
        }

        for (String property : accelType.hardwareProperties) {
            System.setProperty("sun.java2d." + property, "True");
        }
    }
}
