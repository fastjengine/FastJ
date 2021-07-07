package tech.fastj.graphics.util;

import tech.fastj.math.Point;
import tech.fastj.graphics.display.Display;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * Class that provides supplementary methods for working with {@link Display}s.
 * <p>
 * This class mainly focuses on abstractions for working with {@link GraphicsEnvironment} and its methods.
 */
public class DisplayUtil {

    private DisplayUtil() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Gets the specified monitor.
     *
     * @param monitorIndicated The index number of the monitor to get.
     * @return The specified monitor.
     */
    public static GraphicsDevice getMonitor(int monitorIndicated) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitorIndicated];
    }

    /**
     * Gets the default monitor.
     *
     * @return The default monitor.
     */
    public static GraphicsDevice getDefaultMonitor() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    /**
     * Gets all monitors currently available.
     *
     * @return The monitors currently available.
     */
    public static GraphicsDevice[] getMonitors() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    }

    /**
     * Gets the refresh rate of the specified monitor.
     *
     * @param monitorIndicated The monitor to get the refresh rate of.
     * @return The indicated monitor's refresh rate.
     */
    public static int getMonitorRefreshRate(int monitorIndicated) {
        return getMonitor(monitorIndicated).getDisplayMode().getRefreshRate();
    }

    /**
     * Gets the refresh rate of the default monitor.
     *
     * @return The default monitor's refresh rate.
     */
    public static int getDefaultMonitorRefreshRate() {
        return getDefaultMonitor().getDisplayMode().getRefreshRate();
    }

    /**
     * Gets the dimensions of the specified monitor.
     *
     * @param monitorIndicated The monitor to get the dimensions of.
     * @return The indicated monitor's dimensions.
     */
    public static Point getMonitorDimensions(int monitorIndicated) {
        DisplayMode monitorMode = getMonitor(monitorIndicated).getDisplayMode();
        return new Point(monitorMode.getWidth(), monitorMode.getHeight());
    }

    /**
     * Gets the dimensions of the default monitor.
     *
     * @return The default monitor's dimensions.
     */
    public static Point getDefaultMonitorDimensions() {
        DisplayMode monitorMode = getDefaultMonitor().getDisplayMode();
        return new Point(monitorMode.getWidth(), monitorMode.getHeight());
    }

    /**
     * Gets the amount of monitors the user has.
     *
     * @return The amount of monitors that the user has.
     */
    public static int getMonitorCount() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
    }
}
