package tech.fastj.graphics.display;

import tech.fastj.math.Maths;

import java.awt.RenderingHints;

/**
 * Based on the {@link RenderingHints} class, the {@code RenderSettings} class provides a simple way to set rendering
 * options using {@link Display#modifyRenderSettings(RenderSettings)}.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class RenderSettings {

    final RenderingHints.Key key;
    final Object value;

    RenderSettings(RenderingHints.Key key, Object value) {
        this.key = key;
        this.value = value;
    }

    /** Render settings to enable/disable anti-aliasing. */
    public static class Antialiasing {
        private Antialiasing() {
            throw new IllegalStateException();
        }

        /** Render setting that enables anti-aliasing. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /** Render setting that disables anti-aliasing. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    /** Render settings to enable/disable text anti-aliasing, as well as specific optimizations for LCD screens. */
    public static class TextAntialiasing {
        private TextAntialiasing() {
            throw new IllegalStateException();
        }

        /** Render setting that enables text anti-aliasing. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        /** Render setting that disables text anti-aliasing. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        /**
         * Render setting that enables anti-aliasing based on font resource specifications.
         * <p>
         * This setting uses information in font resources that specifies for each point size whether to enable or
         * disable anti-aliasing.
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_GASP
         */
        public static final RenderSettings Gasp = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        /**
         * Render setting that enables anti-aliasing with LCD HRGB optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (R,
         * G, B) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (HRGB).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_HRGB
         */
        public static final RenderSettings LcdHrgb = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        /**
         * Render setting that enables anti-aliasing with LCD HBGR optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (B,
         * G, R) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (HBGR).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_HBGR
         */
        public static final RenderSettings LcdHbgr = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
        /**
         * Render setting that enables anti-aliasing with LCD VRGB optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (R,
         * G, B) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (VRGB).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_VRGB
         */
        public static final RenderSettings LcdVrgb = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB);
        /**
         * Render setting that enables anti-aliasing with LCD VBGR optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (B,
         * G, R) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (VBGR).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_VBGR
         */
        public static final RenderSettings LcdVbgr = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR);
    }

    /** Render settings to change general rendering quality. */
    public static class GeneralRenderingQuality {
        private GeneralRenderingQuality() {
            throw new IllegalStateException();
        }

        /** Render setting that opts for fast rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        /** Render setting that opts for quality-accurate rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    /** Render settings to change color rendering quality. */
    public static class ColorRenderingQuality {
        private ColorRenderingQuality() {
            throw new IllegalStateException();
        }

        /** Render setting that opts for fast color rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        /** Render setting that opts for quality-accurate color rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    /** Render settings that change the image interpolation algorithm. */
    public static class ImageInterpolation {
        private ImageInterpolation() {
            throw new IllegalStateException();
        }

        /**
         * Render setting that sets image interpolation as bilinear.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_BILINEAR
         */
        public static final RenderSettings Bilinear = new RenderSettings(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        /**
         * Render setting that sets image interpolation as bilinear.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_BICUBIC
         */
        public static final RenderSettings Bicubic = new RenderSettings(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        /**
         * Render setting that sets image interpolation as nearest-neighbor.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_NEAREST_NEIGHBOR
         */
        public static final RenderSettings NearestNeighbor = new RenderSettings(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }

    /** Render settings that change alpha interpolation quality. */
    public static class AlphaInterpolationQuality {
        private AlphaInterpolationQuality() {
            throw new IllegalStateException();
        }

        /** Render setting that opts for fast alpha-blend rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        /** Render setting that opts for quality-accurate alpha-blend rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    }

    /** Render settings that change how polygon outlines are rendered. */
    public static class OutlineRenderingStyle {
        private OutlineRenderingStyle() {
            throw new IllegalStateException();
        }

        /** Render setting that normalizes polygon outlines when rendering to improve rendering uniformity. */
        public static final RenderSettings Normalize = new RenderSettings(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        /** Render setting that does not normalize polygon outlines when rendering, leaving it unmodified. */
        public static final RenderSettings Unmodified = new RenderSettings(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    /** Render settings that change how images are picked based on resolution. */
    public static class ImageVariantResolution {
        private ImageVariantResolution() {
            throw new IllegalStateException();
        }

        /** Render setting that specifies to always use the standard resolution of an image. */
        public static final RenderSettings Standard = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE);
        /** Render setting that specifies to choose image resolutions based on the camera transformation and screen DPI. */
        public static final RenderSettings FitToSize = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT);
        /** Render setting that specifies to choose image resolutions based on the screen DPI. */
        public static final RenderSettings FitToScreenDPI = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_DPI_FIT);
    }

    /**
     * Render settings that change the contrast level of text on LCD screens.
     * <p>
     * This setting should only be used in conjunction with one of the following settings:
     * <ul>
     *     <li>{@link TextAntialiasing#LcdHrgb}</li>
     *     <li>{@link TextAntialiasing#LcdHbgr}</li>
     *     <li>{@link TextAntialiasing#LcdVrgb}</li>
     *     <li>{@link TextAntialiasing#LcdVbgr}</li>
     * </ul>
     */
    public static class TextLCDContrast {
        private TextLCDContrast() {
            throw new IllegalStateException();
        }

        /** Render setting that specifies the minimum possible contrast for text on LCD screens. */
        public static final RenderSettings Minimum = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, 250);
        /** Render setting that specifies low contrast for text on LCD screens. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, 213);
        /** Render setting that specifies medium contrast for text on LCD screens. */
        public static final RenderSettings Medium = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, 175);
        /** Render setting that specifies high contrast for text on LCD screens. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, 138);
        /** Render setting that specifies the maximum possible contrast for text on LCD screens. */
        public static final RenderSettings Maximum = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);

        private static final int MinimumValue = 250;
        private static final int MaximumValue = 100;

        /**
         * Generates a custom percentage of contrast to use as a rendering option.
         *
         * @param percentage The {@code float} value representing the contrast level to be generated.
         * @return A {@link RenderSettings} instance with the LCD contrast provided.
         */
        public static RenderSettings percentage(float percentage) {
            if (percentage > 1.0f || percentage < 0.0f) {
                throw new IllegalArgumentException("A percentage " + percentage + " is out of range.");
            }

            return new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, Maths.denormalize((1.0f - percentage), MaximumValue, MinimumValue));
        }
    }

    /** Render settings that enable/disable dithering when rendering polygons. */
    public static class Dithering {
        private Dithering() {
            throw new IllegalStateException();
        }

        /** Render setting that enables dithering when rendering polygons. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        /** Render setting that disables dithering when rendering polygons. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
    }

    /** Render settings that enable/disable subpixel text rendering. */
    public static class SubpixelTextRendering {
        private SubpixelTextRendering() {
            throw new IllegalStateException();
        }

        /** Render setting that enables subpixel text rendering. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        /** Render setting that disables subpixel text rendering. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
    }
}
