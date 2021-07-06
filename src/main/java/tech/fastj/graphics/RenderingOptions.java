package tech.fastj.graphics;

import tech.fastj.math.Maths;

import java.awt.RenderingHints;

public class RenderingOptions {

    enum Antialiasing {
        Enabled(RenderingHints.VALUE_ANTIALIAS_ON),
        Disabled(RenderingHints.VALUE_ANTIALIAS_OFF);

        final RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
        final Object keyValue;

        Antialiasing(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum TextAntialiasing {
        Enabled(RenderingHints.VALUE_TEXT_ANTIALIAS_ON),
        Disabled(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF),
        Gasp(RenderingHints.VALUE_TEXT_ANTIALIAS_GASP),
        LcdHrgb(RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB),
        LcdHbgr(RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR),
        LcdVrgb(RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB),
        LcdVbgr(RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR);

        final RenderingHints.Key key = RenderingHints.KEY_TEXT_ANTIALIASING;
        final Object keyValue;

        TextAntialiasing(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum GeneralRendering {
        Speed(RenderingHints.VALUE_RENDER_SPEED),
        Quality(RenderingHints.VALUE_RENDER_QUALITY);

        final RenderingHints.Key key = RenderingHints.KEY_RENDERING;
        final Object keyValue;

        GeneralRendering(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum ColorRendering {
        Speed(RenderingHints.VALUE_COLOR_RENDER_SPEED),
        Quality(RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        final RenderingHints.Key key = RenderingHints.KEY_COLOR_RENDERING;
        final Object keyValue;

        ColorRendering(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum ImageInterpolation {
        Bilinear(RenderingHints.VALUE_INTERPOLATION_BILINEAR),
        Bicubic(RenderingHints.VALUE_INTERPOLATION_BICUBIC),
        NearestNeighbor(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        final RenderingHints.Key key = RenderingHints.KEY_INTERPOLATION;
        final Object keyValue;

        ImageInterpolation(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum AlphaInterpolation {
        Speed(RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED),
        Quality(RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        final RenderingHints.Key key = RenderingHints.KEY_ALPHA_INTERPOLATION;
        final Object keyValue;

        AlphaInterpolation(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum StrokeNormalization {
        Normalize(RenderingHints.VALUE_STROKE_NORMALIZE),
        Unmodified(RenderingHints.VALUE_STROKE_PURE);

        final RenderingHints.Key key = RenderingHints.KEY_STROKE_CONTROL;
        final Object keyValue;

        StrokeNormalization(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum ImageResolution {
        Standard(RenderingHints.VALUE_RESOLUTION_VARIANT_BASE),
        FitToSize(RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT),
        FitToScreenDPI(RenderingHints.VALUE_RESOLUTION_VARIANT_DPI_FIT);

        final RenderingHints.Key key = RenderingHints.KEY_RESOLUTION_VARIANT;
        final Object keyValue;

        ImageResolution(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum TextLCDContrast {
        Minimum(250),
        Low(213),
        Medium(175),
        High(138),
        Maximum(100);

        private static final int MinimumValue = 250;
        private static final int MaximumValue = 100;
        final RenderingHints.Key key = RenderingHints.KEY_TEXT_LCD_CONTRAST;
        final Object keyValue;

        TextLCDContrast(Object keyValue) {
            this.keyValue = keyValue;
        }

        public Object percentage(float percentage) {
            if (percentage > 1.0f || percentage < 0.0f) {
                throw new IllegalArgumentException("A percentage " + percentage + " is out of range.");
            }

            return Maths.denormalize(percentage, MaximumValue, MinimumValue);
        }
    }

    enum Dithering {
        Enabled(RenderingHints.VALUE_DITHER_ENABLE),
        Disabled(RenderingHints.VALUE_DITHER_DISABLE);

        final RenderingHints.Key key = RenderingHints.KEY_DITHERING;
        final Object keyValue;

        Dithering(Object keyValue) {
            this.keyValue = keyValue;
        }
    }

    enum SubpixelTextRendering {
        Enabled(RenderingHints.VALUE_FRACTIONALMETRICS_ON),
        Disabled(RenderingHints.VALUE_FRACTIONALMETRICS_OFF);

        final RenderingHints.Key key = RenderingHints.KEY_DITHERING;
        final Object keyValue;

        SubpixelTextRendering(Object keyValue) {
            this.keyValue = keyValue;
        }
    }
}
