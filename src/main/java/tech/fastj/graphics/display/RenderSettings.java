package tech.fastj.graphics.display;

import tech.fastj.math.Maths;

import java.awt.RenderingHints;

/**
 * Based on the {@link RenderingHints} class, the {@code RenderSettings} class provides a simple way to set rendering options using
 * {@link FastJCanvas#modifyRenderSettings(RenderSettings)}.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class RenderSettings {

    final RenderingHints.Key key;
    final Object value;

    /** Descriptive version of the {@link RenderSettings}' key. */
    public final String keyDescriptor;
    /** Descriptive version of the {@link RenderSettings}' value. */
    public final String valueDescriptor;

    RenderSettings(RenderingHints.Key key, String keyDescriptor, Object value, String valueDescriptor) {
        this.key = key;
        this.keyDescriptor = keyDescriptor;
        this.value = value;
        this.valueDescriptor = valueDescriptor;
    }

    @Override
    public String toString() {
        return "RenderSettings{" +
            "key='" + keyDescriptor + '\'' +
            ", value='" + valueDescriptor + '\'' +
            '}';
    }

    /** Render settings to enable/disable anti-aliasing. */
    public static class Antialiasing {
        private Antialiasing() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Antialiasing";
        /** Render setting "enabled" descriptor string. */
        public static final String VStringEnable = "Enabled";
        /** Render setting "disabled" descriptor string. */
        public static final String VStringDisable = "Disabled";

        /** Enables anti-aliasing. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_ANTIALIASING, KString, RenderingHints.VALUE_ANTIALIAS_ON, VStringEnable);
        /** Disables anti-aliasing. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_ANTIALIASING, KString, RenderingHints.VALUE_ANTIALIAS_OFF, VStringDisable);
    }

    /** Render settings to enable/disable text anti-aliasing, as well as specific optimizations for LCD screens. */
    public static class TextAntialiasing {
        private TextAntialiasing() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Text Antialiasing";
        /** Render setting "enabled" descriptor string. */
        public static final String VStringOn = "Enabled";
        /** Render setting "disabled" descriptor string. */
        public static final String VStringOff = "Disabled";
        /** Render setting "text antialiasing: gasp" descriptor string. */
        public static final String VStringGasp = "Gasp";
        /** Render setting "text antialiasing: LCD HRGB" descriptor string. */
        public static final String VStringLcdHrgb = "LCD HRGB";
        /** Render setting "text antialiasing: LCD HBGR" descriptor string. */
        public static final String VStringLdcHbgr = "LCD HBGR";
        /** Render setting "text antialiasing: LCD VRGB" descriptor string. */
        public static final String VStringLcdVrgb = "LCD VRGB";
        /** Render setting "text antialiasing: LCD VBGR" descriptor string. */
        public static final String VStringLcdVbgr = "LCD VBGR";

        /** Enables text anti-aliasing. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_ON, VStringOn);
        /** Disables text anti-aliasing. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF, VStringOff);
        /**
         * Enables anti-aliasing based on font resource specifications.
         * <p>
         * This setting uses information in font resources that specifies for each point size whether to enable or disable anti-aliasing.
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_GASP
         */
        public static final RenderSettings Gasp = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP, VStringGasp);
        /**
         * Enables anti-aliasing with LCD HRGB optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (R, G, B) such that the
         * horizontal subpixel resolution is three times the full pixel horizontal resolution (HRGB).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_HRGB
         */
        public static final RenderSettings LcdHrgb = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB, VStringLcdHrgb);
        /**
         * Enables anti-aliasing with LCD HBGR optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (B, G, R) such that the
         * horizontal subpixel resolution is three times the full pixel horizontal resolution (HBGR).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_HBGR
         */
        public static final RenderSettings LcdHbgr = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR, VStringLdcHbgr);
        /**
         * Enables anti-aliasing with LCD VRGB optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (R, G, B) such that the
         * horizontal subpixel resolution is three times the full pixel horizontal resolution (VRGB).
         *
         * @see RenderingHints#VALUE_TEXT_ANTIALIAS_LCD_VRGB
         */
        public static final RenderSettings LcdVrgb = new RenderSettings(RenderingHints.KEY_TEXT_ANTIALIASING, KString, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB, VStringLcdVrgb);
        /**
         * Enables anti-aliasing with LCD VBGR optimization.
         * <p>
         * This setting requests that text is optimized for an LCD display with sub-pixels displayed left to right (B, G, R) such that the
         * horizontal subpixel resolution is three times the full pixel horizontal resolution (VBGR).
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

        /** Render setting descriptor string. */
        public static final String KString = "Rendering Quality";
        /** Render setting "low value" descriptor string. */
        public static final String VStringLow = "Low";
        /** Render setting "high value" descriptor string. */
        public static final String VStringHigh = "High";

        /** Opts for fast rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_RENDERING, KString, RenderingHints.VALUE_RENDER_SPEED, VStringLow);
        /** Opts for quality-accurate rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_RENDERING, KString, RenderingHints.VALUE_RENDER_QUALITY, VStringHigh);
    }

    /** Render settings to change color rendering quality. */
    public static class ColorRenderingQuality {
        private ColorRenderingQuality() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Color Rendering Quality";
        /** Render setting "low value" descriptor string. */
        public static final String VStringLow = "Low";
        /** Render setting "high value" descriptor string. */
        public static final String VStringHigh = "High";

        /** Opts for fast color rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_COLOR_RENDERING, KString, RenderingHints.VALUE_COLOR_RENDER_SPEED, VStringLow);
        /** Opts for quality-accurate color rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_COLOR_RENDERING, KString, RenderingHints.VALUE_COLOR_RENDER_QUALITY, VStringHigh);
    }

    /** Render settings that change the image interpolation algorithm. */
    public static class ImageInterpolation {
        private ImageInterpolation() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Image Interpolation";
        /** Render setting "bilinear image interpolation" descriptor string. */
        public static final String VStringBilinear = "Bilinear";
        /** Render setting "bicubic image interpolation" descriptor string. */
        public static final String VStringBicubic = "Bicubic";
        /** Render setting "nearest-neighbor image interpolation" descriptor string. */
        public static final String VStringNearestNeighbor = "Nearest Neighbor";

        /**
         * Sets image interpolation as bilinear.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_BILINEAR
         */
        public static final RenderSettings Bilinear = new RenderSettings(RenderingHints.KEY_INTERPOLATION, KString, RenderingHints.VALUE_INTERPOLATION_BILINEAR, VStringBilinear);
        /**
         * Sets image interpolation as bilinear.
         *
         * @see RenderingHints#VALUE_INTERPOLATION_BICUBIC
         */
        public static final RenderSettings Bicubic = new RenderSettings(RenderingHints.KEY_INTERPOLATION, KString, RenderingHints.VALUE_INTERPOLATION_BICUBIC, VStringBicubic);
        /**
         * Sets image interpolation as nearest-neighbor.
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

        /** Render setting descriptor string. */
        public static final String KString = "Alpha Interpolation Quality";
        /** Render setting "low value" descriptor string. */
        public static final String VStringLow = "Low";
        /** Render setting "high value" descriptor string. */
        public static final String VStringHigh = "High";

        /** Opts for fast alpha-blend rendering algorithms. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_ALPHA_INTERPOLATION, KString, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED, VStringLow);
        /** Opts for quality-accurate alpha-blend rendering algorithms. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_ALPHA_INTERPOLATION, KString, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY, VStringHigh);
    }

    /** Render settings that change how polygon outlines are rendered. */
    public static class OutlineRendering {
        private OutlineRendering() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Outline Rendering";
        /** Render setting "normalized" descriptor string. */
        public static final String VStringNormalize = "Normalize";
        /** Render setting "unmodified" descriptor string. */
        public static final String VStringUnmodified = "Unmodified";

        /** Normalizes polygon outlines when rendering to improve rendering uniformity. */
        public static final RenderSettings Normalize = new RenderSettings(RenderingHints.KEY_STROKE_CONTROL, KString, RenderingHints.VALUE_STROKE_NORMALIZE, VStringNormalize);
        /** Does not normalize polygon outlines when rendering, leaving it unmodified. */
        public static final RenderSettings Unmodified = new RenderSettings(RenderingHints.KEY_STROKE_CONTROL, KString, RenderingHints.VALUE_STROKE_PURE, VStringUnmodified);
    }

    /** Render settings that change how images are picked based on resolution. */
    public static class ImageVariantResolution {
        private ImageVariantResolution() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Image Variant Resolution";
        /** Render setting "standard size" descriptor string. */
        public static final String VStringStandard = "Standard";
        /** Render setting "fit to canvas" descriptor string. */
        public static final String VStringFitToCanvas = "Fit to Canvas";
        /** Render setting "fit to screen DPI" descriptor string. */
        public static final String VStringFitToScreenDPI = "Fit to Screen DPI";

        /** Specifies to always use the standard resolution of an image. */
        public static final RenderSettings Standard = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, KString, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE, VStringStandard);
        /** Specifies to choose image resolutions based on the camera transformation and screen DPI. */
        public static final RenderSettings FitToCanvas = new RenderSettings(RenderingHints.KEY_RESOLUTION_VARIANT, KString, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT, VStringFitToCanvas);
        /** Specifies to choose image resolutions based on the screen DPI. */
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

        /** Render setting descriptor string. */
        public static final String KString = "Text LCD Contrast";
        /** Render setting "lowest value" descriptor string. */
        public static final String VStringLowest = "Lowest";
        /** Render setting "low value" descriptor string. */
        public static final String VStringLow = "Low";
        /** Render setting "medium value" descriptor string. */
        public static final String VStringMedium = "Medium";
        /** Render setting "high value" descriptor string. */
        public static final String VStringHigh = "High";
        /** Render setting "highest value" descriptor string. */
        public static final String VStringHighest = "Highest";
        /** Render setting "custom value" descriptor string. */
        public static final String VStringCustom = "Custom";

        /** Specifies the minimum possible contrast for text on LCD screens. */
        public static final RenderSettings Minimum = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 250, VStringLowest);
        /** Specifies low contrast for text on LCD screens. */
        public static final RenderSettings Low = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 213, VStringLow);
        /** Specifies medium contrast for text on LCD screens. */
        public static final RenderSettings Medium = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 175, VStringMedium);
        /** Specifies high contrast for text on LCD screens. */
        public static final RenderSettings High = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 138, VStringHigh);
        /** Specifies the maximum possible contrast for text on LCD screens. */
        public static final RenderSettings Maximum = new RenderSettings(RenderingHints.KEY_TEXT_LCD_CONTRAST, KString, 100, VStringHighest);

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

        /** Render setting descriptor string. */
        public static final String KString = "Dithering";
        /** Render setting "enabled" descriptor string. */
        public static final String VStringEnable = "Enabled";
        /** Render setting "disabled" descriptor string. */
        public static final String VStringDisable = "Disabled";

        /** Enables dithering when rendering polygons. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_DITHERING, KString, RenderingHints.VALUE_DITHER_ENABLE, VStringEnable);
        /** Disables dithering when rendering polygons. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_DITHERING, KString, RenderingHints.VALUE_DITHER_DISABLE, VStringDisable);
    }

    /** Render settings that enable/disable subpixel text rendering. */
    public static class SubpixelTextRendering {
        private SubpixelTextRendering() {
            throw new IllegalStateException();
        }

        /** Render setting descriptor string. */
        public static final String KString = "Subpixel Text Rendering";
        /** Render setting "enabled" descriptor string. */
        public static final String VStringEnable = "Enabled";
        /** Render setting "disabled" descriptor string. */
        public static final String VStringDisable = "Disabled";

        /** Enables subpixel text rendering. */
        public static final RenderSettings Enable = new RenderSettings(RenderingHints.KEY_FRACTIONALMETRICS, KString, RenderingHints.VALUE_FRACTIONALMETRICS_ON, VStringEnable);
        /** Disables subpixel text rendering. */
        public static final RenderSettings Disable = new RenderSettings(RenderingHints.KEY_FRACTIONALMETRICS, KString, RenderingHints.VALUE_FRACTIONALMETRICS_OFF, VStringDisable);
    }
}
