package io.github.lucasstarsz.fastj.engine;

/**
 * Enumerator which defines what types of hardware acceleration are supported.
 * <p>
 * The types of hardware acceleration are as follows:
 * <ul>
 *     	<li>{@code DIRECT3D} - Enables Direct 3D Draw hardware acceleration.
 *         	<ul><li><b>NOTE:</b> This is only supported on Windows machines.</li></ul></li>
 * 		<li>{@code OPENGL} - Enables OpenGL hardware acceleration.</li>
 * 		<li>{@code DEFAULT} - Leaves the configuration of hardware acceleration to its default.</li>
 * 		<li>{@code CPU_RENDER} - Disables all hardware acceleration. Instead, software rendering will be used.</li>
 * </ul>
 */
public enum HWAccel {
    DIRECT3D("d3d", "transaccel", "ddforcevram"),
    OPENGL("opengl"),
    DEFAULT(),
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
