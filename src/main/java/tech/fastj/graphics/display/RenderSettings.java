package tech.fastj.graphics.display;

import tech.fastj.math.Maths;

import java.awt.RenderingHints;

/**
 * Based on the {@link RenderingHints} class, the {@code RenderSettings} class provides a simple way to set rendering
 * options using {@link FastJCanvas#modifyRenderSettings(RenderSettings)}.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class RenderSettings {

    final RenderingHints.Key key;
    final Object value;
    /** String version of the {@code RenderSettings}' key. */
    public final String keyString;
    /** String version of the {@code RenderSettings}' value. */
    public final String valueString;

    RenderSettings(RenderingHints.Key key, String keyString, Object value, String valueString) {
        this.key = key;
        this.keyString = keyString;
        this.value = value;
        this.valueString = valueString;
    }

    @Override
    public String toString() {
        return "RenderSettings{" +
                "key='" + keyString + '\'' +
                ", value='" + valueString + '\'' +
                '}';
    }

    /** Render settings to enable/disable anti-aliasing. */
    public static class Antialiasing {
        private Antialiasing() {
            throw new IllegalStateException();
        }

        public static final String KString = "Antialiasing";
        public static final String VStringEnable = "Enabled";
        public static final String VStringDisable = "Disabled";

        /** Render setting that enables anti-aliasing. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_ANTIALIASING, KString, RenderingHints.VALUE_ANTIALIAS_ON, VStringEnable);
        /** Render setting that disables anti-aliasing. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_ANTIALIASING, KString, RenderingHints.VALUE_ANTIALIAS_OFF, VStringDisable);
    }

    /** Render settings to enable/disable text anti-aliasing, as well as specific optimizations for LCD screens. */
    public static class TextAntialiasing {
        private TextAntialiasing() {
            throw new IllegalStateException();
        }

        public static final String KString = "Text Antialiasing";
        public static final String VStringOn = "Enabled";
        public static final String VStringOff = "Disabled";
        public static final String VStringGasp = "Gasp";
        public static final String VStringLcdHrgb = "LCD HRGB";
        public static final String VStringLdcHbgr = "LCD HBGR";
        public static final String VStringLcdVrgb = "LCD VRGB";
        public static final String VStringLcdVbgr = "LCD VBGR";

        /** Render setting that enables text anti-aliasing. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_ON, VStringOn);
        /** Render setting that disables text anti-aliasing. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF, VStringOff);
        /**
         * Render setting that enables anti-aliasing based on font resource specifications.
         * <p>
         * This setting uses information in font resources that specifies for each point size whether to enable or
         * disable anti-aliasing.
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_GASP
         */
        public static final RenderSettings Gasp = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP, VStringGasp);
        /**
         * Render setting that enables anti-aliasing with LCD HRGB optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (R,
         * G, B) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (HRGB).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_HRGB
         */
        public static final RenderSettings LcdHrgb = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB, VStringLcdHrgb);
        /**
         * Render setting that enables anti-aliasing with LCD HBGR optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (B,
         * G, R) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (HBGR).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_HBGR
         */
        public static final RenderSettings LcdHbgr = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR, VStringLdcHbgr);
        /**
         * Render setting that enables anti-aliasing with LCD VRGB optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (R,
         * G, B) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (VRGB).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_VRGB
         */
        public static final RenderSettings LcdVrgb = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB, VStringLcdVrgb);
        /**
         * Render setting that enables anti-aliasing with LCD VBGR optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (B,
         * G, R) such that the horizontal subpixel resolution is three times the full pixel horizontal resolution
         * (VBGR).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_VBGR
         */
        public static final RenderSettings LcdVbgr = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR, VStringLcdVbgr);
    }

    /** Render settings to change general rendering quality. */
    public static class RenderingQuality {
        private RenderingQuality() {
            throw new IllegalStateException();
        }

        public static final String KString = "Rendering Quality";
        public static final String VStringLow = "Low";
        public static final String VStringHigh = "High";

        /** Render setting that opts for fast rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_RENDERING, KString, RenderingHints.VALUE_RENDER_SPEED, VStringLow);
        /** Render setting that opts for quality-accurate rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_RENDERING, KString, RenderingHints.VALUE_RENDER_QUALITY, VStringHigh);
    }

    /** Render settings to change color rendering quality. */
    public static class ColorRenderingQuality {
        private ColorRenderingQuality() {
            throw new IllegalStateException();
        }

        public static final String KString = "Color Rendering Quality";
        public static final String VStringLow = "Low";
        public static final String VStringHigh = "High";

        /** Render setting that opts for fast color rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_COLOR_RENDERING, KString, RenderingHints.VALUE_COLOR_RENDER_SPEED, VStringLow);
        /** Render setting that opts for quality-accurate color rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_COLOR_RENDERING, KString, RenderingHints.VALUE_COLOR_RENDER_QUALITY, VStringHigh);
    }

    /** Render settings that change the image interpolation algorithm. */
    public static class ImageInterpolation {
        private ImageInterpolation() {
            throw new IllegalStateException();
        }

        public static final String KString = "Image Interpolation";
        public static final String VStringBilinear = "Bilinear";
        public static final String VStringBicubic = "Bicubic";
        public static final String VStringNearestNeighbor = "Nearest Neighbor";

        /**
         * Render setting that sets image interpolation as bilinear.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_BILINEAR
         */
        public static final RenderSettings Bilinear = new RenderSettings(RenderingHints.KEY_INTERPOLATION, KString, RenderingHints.VALUE_INTERPOLATION_BILINEAR, VStringBilinear);
        /**
         * Render setting that sets image interpolation as bilinear.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_BICUBIC
         */
        public static final RenderSettings Bicubic = new RenderSettings(RenderingHints.KEY_INTERPOLATION, KString, RenderingHints.VALUE_INTERPOLATION_BICUBIC, VStringBicubic);
        /**
         * Render setting that sets image interpolation as nearest-neighbor.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_NEAREST_NEIGHBOR
         */
        public static final RenderSettings NearestNeighbor = new RenderSettings(RenderingHints.KEY_INTERPOLATION, KString, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VStringNearestNeighbor);
    }

    /** Render settings that change alpha interpolation quality. */
    public static class AlphaInterpolationQuality {
        private AlphaInterpolationQuality() {
            throw new IllegalStateException();
        }

        public static final String KString = "Alpha Interpolation Quality";
        public static final String VStringLow = "Low";
        public static final String VStringHigh = "High";

        /** Render setting that opts for fast alpha-blend rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_ALPHA_INTERPOLATION, KString, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED, VStringLow);
        /** Render setting that opts for quality-accurate alpha-blend rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_ALPHA_INTERPOLATION, KString, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY, VStringHigh);
    }

    /** Render settings that change how polygon outlines are rendered. */
    public static class OutlineRendering {
        private OutlineRendering() {
            throw new IllegalStateException();
        }

        public static final String KString = "Outline Rendering";
        public static final String VStringNormalize = "Normalize";
        public static final String VStringUnmodified = "Unmodified";

        /** Render setting that normalizes polygon outlines when rendering to improve rendering uniformity. */
        public static final RenderSettings Normalize = new RenderSettings(RenderingHints.KEY_STROKE_CONTROL, KString, RenderingHints.VALUE_STROKE_NORMALIZE, VStringNormalize);
        /** Render setting that does not normalize polygon outlines when rendering, leaving it unmodified. */
        public static final RenderSettings Unmodified = new RenderSettings(RenderingHints.KEY_STROKE_CONTROL, KString, RenderingHints.VALUE_STROKE_PURE, VStringUnmodified);
    }

    /** Render settings that change how images are picked based on resolution. */
    public static class ImageVariantResolution {
        private ImageVariantResolution() {
            throw new IllegalStateException();
        }

        public static final String KString = "Image Variant Resolution";
        public static final String VStringStandard = "Standard";
        public static final String VStringFitToCanavas = "Fit to Canvas";
        public static final String VStringFitToScreenDPI = "Fit to Screen DPI";

        /** Render setting that specifies to always use the standard resolution of an image. */
        public static final RenderSettings Standard = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, KString, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE, VStringStandard);
        /** Render setting that specifies to choose image resolutions based on the camera transformation and screen DPI. */
        public static final RenderSettings FitToCanvas = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, KString, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT, VStringFitToCanavas);
        /** Render setting that specifies to choose image resolutions based on the screen DPI. */
        public static final RenderSettings FitToScreenDPI = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, KString, RenderingHints.VALUE_RESOLUTION_VARIANT_DPI_FIT, VStringFitToScreenDPI);
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

        public static final String KString = "Text LCD Contrast";
        public static final String VStringMinimum = "Minimum";
        public static final String VStringLow = "Low";
        public static final String VStringMedium = "Medium";
        public static final String VStringHigh = "High";
        public static final String VStringMaximum = "Maximum";
        public static final String VStringCustom = "Custom";

        /** Render setting that specifies the minimum possible contrast for text on LCD screens. */
        public static final RenderSettings Minimum = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 250, VStringMinimum);
        /** Render setting that specifies low contrast for text on LCD screens. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 213, VStringLow);
        /** Render setting that specifies medium contrast for text on LCD screens. */
        public static final RenderSettings Medium = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 175, VStringMedium);
        /** Render setting that specifies high contrast for text on LCD screens. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 138, VStringHigh);
        /** Render setting that specifies the maximum possible contrast for text on LCD screens. */
        public static final RenderSettings Maximum = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 100, VStringMaximum);

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

            return new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, Maths.denormalize((1.0f - percentage), MaximumValue, MinimumValue), VStringCustom);
        }
    }

    /** Render settings that enable/disable dithering when rendering polygons. */
    public static class Dithering {
        private Dithering() {
            throw new IllegalStateException();
        }

        public static final String KString = "Dithering";
        public static final String VStringEnable = "Enabled";
        public static final String VStringDisable = "Disabled";

        /** Render setting that enables dithering when rendering polygons. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_DITHERING, KString, RenderingHints.VALUE_DITHER_ENABLE, VStringEnable);
        /** Render setting that disables dithering when rendering polygons. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_DITHERING, KString, RenderingHints.VALUE_DITHER_DISABLE, VStringDisable);
    }

    /** Render settings that enable/disable subpixel text rendering. */
    public static class SubpixelTextRendering {
        private SubpixelTextRendering() {
            throw new IllegalStateException();
        }

        public static final String KString = "Subpixel Text Rendering";
        public static final String VStringEnable = "Enabled";
        public static final String VStringDisable = "Disabled";

        /** Render setting that enables subpixel text rendering. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_FRACTIONALMETRICS, KString, RenderingHints.VALUE_FRACTIONALMETRICS_ON, VStringEnable);
        /** Render setting that disables subpixel text rendering. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_FRACTIONALMETRICS, KString, RenderingHints.VALUE_FRACTIONALMETRICS_OFF, VStringDisable);
    }
}
