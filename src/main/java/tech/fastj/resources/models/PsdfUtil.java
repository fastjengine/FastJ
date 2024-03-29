package tech.fastj.resources.models;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.graphics.gradients.LinearGradientBuilder;
import tech.fastj.graphics.gradients.RadialGradientBuilder;
import tech.fastj.graphics.textures.Textures;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.resources.images.ImageResource;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WIP Utility class for reading and writing {@link Polygon2D} to and from the {@code .PSDF} file format.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public class PsdfUtil {

    private static final String LineSeparator = System.lineSeparator();

    private PsdfUtil() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Parses the specified file contents into a {@code Polygon2D} array.
     *
     * @param modelPath path of the model -- currently unused.
     * @param lines     The .psdf file, split line by line.
     * @return An array of {@code Polygon2D}s.
     */
    public static Polygon2D[] parse(Path modelPath, List<String> lines) {
        Polygon2D[] polygons = null;
        int polygonsIndex = 0;

        List<Pointf> polygonPoints = new ArrayList<>();
        List<Point> altIndexes = new ArrayList<>();
        boolean shouldRender = Drawable.DefaultShouldRender;

        RenderStyle renderStyle = Polygon2D.DefaultRenderStyle;
        Paint fillPaint = Polygon2D.DefaultFill;
        String texturePath = "";
        BasicStroke outlineStroke = Polygon2D.DefaultOutlineStroke;
        Color outlineColor = Polygon2D.DefaultOutlineColor;

        Pointf translation = Transform2D.DefaultTranslation.copy();
        float rotation = Transform2D.DefaultRotation;
        Pointf scale = Transform2D.DefaultScale.copy();

        for (String words : lines) {
            String[] tokens = words.split("\\s+");
            switch (tokens[0]) {
                case ParsingKeys.Empty -> {
                }
                case ParsingKeys.Amount -> polygons = parsePolygonCount(tokens);
                case ParsingKeys.RenderStyle -> renderStyle = parseRenderStyle(tokens);
                case ParsingKeys.FillPaintColor, ParsingKeys.FillPaintLinearGradient, ParsingKeys.FillPaintRadialGradient ->
                    fillPaint = parsePaint(tokens);
                case ParsingKeys.FillPaintTexture -> texturePath = parseTexture(tokens);
                case ParsingKeys.OutlineStroke -> outlineStroke = parseOutlineStroke(tokens);
                case ParsingKeys.OutlineColor -> outlineColor = parseOutlineColor(tokens);
                case ParsingKeys.ShouldRender -> shouldRender = parseShouldRender(tokens);
                case ParsingKeys.AlternateIndex -> altIndexes.add(parseAltIndex(tokens));
                case ParsingKeys.Transform -> {
                    translation = parseTranslation(tokens);
                    rotation = parseRotation(tokens);
                    scale = parseScale(tokens);
                }
                case ParsingKeys.MeshPoint -> {
                    polygonPoints.add(parseMeshPoint(tokens));

                    // if end of polygon, add polygon to array
                    if (tokens.length == 4 && tokens[3].equals(";")) {
                        assert polygons != null;

                        polygons[polygonsIndex] = Polygon2D.create(polygonPoints.toArray(new Pointf[0]), altIndexes.toArray(new Point[0]), shouldRender)
                            .withRenderStyle(renderStyle)
                            .withOutline(outlineStroke, outlineColor)
                            .withTransform(translation, rotation, scale)
                            .build();

                        if (!texturePath.isBlank()) {
                            fillPaint = Textures.create(Path.of(texturePath), DrawUtil.createRect(polygons[polygonsIndex].getBounds()));
                        }

                        polygons[polygonsIndex].setFill(fillPaint);

                        // reset values
                        polygonPoints.clear();
                        altIndexes.clear();
                        shouldRender = Drawable.DefaultShouldRender;

                        renderStyle = Polygon2D.DefaultRenderStyle;
                        fillPaint = Polygon2D.DefaultFill;
                        texturePath = "";
                        outlineStroke = Polygon2D.DefaultOutlineStroke;
                        outlineColor = Polygon2D.DefaultOutlineColor;

                        translation = Transform2D.DefaultTranslation.copy();
                        rotation = Transform2D.DefaultRotation;
                        scale = Transform2D.DefaultScale.copy();

                        // increase array location
                        polygonsIndex++;
                    }

                }
                default -> throw new IllegalStateException("Invalid .psdf token: \"" + tokens[0] + "\".");
            }
        }

        return polygons;
    }

    private static Polygon2D[] parsePolygonCount(String[] tokens) {
        return new Polygon2D[Integer.parseInt(tokens[1])];
    }

    private static Paint parsePaint(String[] tokens) {
        switch (tokens[0]) {
            case ParsingKeys.FillPaintColor -> {
                return new Color(
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2]),
                    Integer.parseInt(tokens[3]),
                    Integer.parseInt(tokens[4])
                );
            }
            case ParsingKeys.FillPaintLinearGradient -> {
                LinearGradientBuilder linearGradientBuilder = Gradients.linearGradient(
                    new Pointf(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2])
                    ),
                    new Pointf(
                        Float.parseFloat(tokens[3]),
                        Float.parseFloat(tokens[4])
                    )
                );

                int colorTokens = (tokens.length - 5);
                for (int i = 5; i <= colorTokens + 1; i += 4) {
                    linearGradientBuilder.withColor(
                        new Color(
                            Integer.parseInt(tokens[i]),
                            Integer.parseInt(tokens[i + 1]),
                            Integer.parseInt(tokens[i + 2]),
                            Integer.parseInt(tokens[i + 3])
                        )
                    );
                }

                return linearGradientBuilder.build();
            }
            case ParsingKeys.FillPaintRadialGradient -> {
                RadialGradientBuilder radialGradientBuilder = Gradients.radialGradient(
                    new Pointf(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2])
                    ),
                    Float.parseFloat(tokens[3])
                );

                int colorCount = (tokens.length - 4);
                for (int i = 4; i <= colorCount + 1; i += 4) {
                    radialGradientBuilder.withColor(
                        new Color(
                            Integer.parseInt(tokens[i]),
                            Integer.parseInt(tokens[i + 1]),
                            Integer.parseInt(tokens[i + 2]),
                            Integer.parseInt(tokens[i + 3])
                        )
                    );
                }

                return radialGradientBuilder.build();
            }
            default -> throw new IllegalStateException("Invalid fill paint type: " + tokens[0]);
        }
    }

    private static String parseTexture(String[] tokens) {
        StringBuilder pathBuilder = new StringBuilder();

        if (!tokens[1].startsWith("\"") || !tokens[tokens.length - 1].endsWith("\"")) {
            throw new IllegalArgumentException(
                "The given path " + Arrays.toString(tokens) + " does not start and end with quotation marks."
            );
        }

        if (tokens.length == 2) {
            pathBuilder.append(tokens[1], 1, tokens[1].length() - 1);
        } else if (tokens.length == 3) {
            pathBuilder.append(tokens[1], 1, tokens[1].length());
            pathBuilder.append(tokens[2], 0, tokens[2].length() - 1);
        } else {
            pathBuilder.append(tokens[1], 1, tokens.length);
            for (int i = 2; i < tokens.length - 1; i++) {
                pathBuilder.append(tokens[i]);
            }
            pathBuilder.append(tokens[tokens.length - 1], 0, tokens[tokens.length - 1].length() - 1);
        }

        return pathBuilder.toString();
    }

    private static BasicStroke parseOutlineStroke(String[] tokens) {
        int basicStrokeCap = switch (Integer.parseInt(tokens[2])) {
            case BasicStroke.CAP_BUTT -> BasicStroke.CAP_BUTT;
            case BasicStroke.CAP_ROUND -> BasicStroke.CAP_ROUND;
            case BasicStroke.CAP_SQUARE -> BasicStroke.CAP_SQUARE;
            default -> throw new IllegalStateException("Invalid BasicStroke Cap value: " + Integer.parseInt(tokens[2]));
        };

        int basicStrokeJoinStyle = switch (Integer.parseInt(tokens[3])) {
            case BasicStroke.JOIN_MITER -> BasicStroke.JOIN_MITER;
            case BasicStroke.JOIN_ROUND -> BasicStroke.JOIN_ROUND;
            case BasicStroke.JOIN_BEVEL -> BasicStroke.JOIN_BEVEL;
            default -> throw new IllegalStateException("Invalid BasicStroke Join value: " + Integer.parseInt(tokens[3]));
        };

        return new BasicStroke(Float.parseFloat(tokens[1]), basicStrokeCap, basicStrokeJoinStyle, Float.parseFloat(tokens[4]), null, 0.0f);
    }

    private static Color parseOutlineColor(String[] tokens) {
        return new Color(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
    }

    private static RenderStyle parseRenderStyle(String[] tokens) {
        return switch (tokens[1]) {
            case ParsingKeys.RenderStyleFill -> RenderStyle.Fill;
            case ParsingKeys.RenderStyleOutline -> RenderStyle.Outline;
            case ParsingKeys.RenderStyleFillAndOutline -> RenderStyle.FillAndOutline;
            default -> throw new IllegalStateException("Invalid render style: " + tokens[1]);
        };
    }

    private static Pointf parseTranslation(String[] tokens) {
        return new Pointf(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
    }

    private static float parseRotation(String[] tokens) {
        return Float.parseFloat(tokens[3]);
    }

    private static Pointf parseScale(String[] tokens) {
        return new Pointf(Float.parseFloat(tokens[4]), Float.parseFloat(tokens[5]));
    }

    private static boolean parseShouldRender(String[] tokens) {
        return Boolean.parseBoolean(tokens[1]);
    }

    private static Point parseAltIndex(String[] tokens) {
        return new Point(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
    }

    private static Pointf parseMeshPoint(String[] tokens) {
        return new Pointf(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
    }

    /**
     * Writes the given {@link Model2D model} to the given file path.
     *
     * @param destinationPath The {@link Path filepath} to write the model to.
     * @param model           The model to write.
     */
    public static void write(Path destinationPath, Model2D model) {
        StringBuilder fileContents = new StringBuilder();
        writePolygonAmount(fileContents, model.getPolygons());

        for (int i = 0; i < model.getPolygons().length; i++) {
            Polygon2D polygon = model.getPolygons()[i];

            writeRenderStyle(fileContents, polygon.getRenderStyle());
            writeFill(fileContents, polygon.getFill());
            writeOutline(fileContents, polygon.getOutlineStroke(), polygon.getOutlineColor());
            writeTransform(fileContents, polygon.getTranslation(), polygon.getRotation(), polygon.getScale());
            writeShouldRender(fileContents, polygon.shouldRender());
            writeAltIndexes(fileContents, polygon);
            writeMeshPoints(fileContents, polygon);

            // if there are more objects after this object, then add a new line.
            if (i != model.getPolygons().length - 1) {
                fileContents.append(LineSeparator);
            }
        }

        try {
            Files.writeString(destinationPath, fileContents, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException(CrashMessages.theGameCrashed("a .psdf file writing error."), exception);
        }
    }

    private static void writePolygonAmount(StringBuilder fileContents, Polygon2D[] polygons) {
        fileContents.append(PsdfUtil.ParsingKeys.Amount)
            .append(' ')
            .append(polygons.length)
            .append(LineSeparator);
    }

    private static void writeRenderStyle(StringBuilder fileContents, RenderStyle renderStyle) {
        String renderStyleString = switch (renderStyle) {
            case Fill -> ParsingKeys.RenderStyleFill;
            case Outline -> ParsingKeys.RenderStyleOutline;
            case FillAndOutline -> ParsingKeys.RenderStyleFillAndOutline;
        };

        fileContents.append(PsdfUtil.ParsingKeys.RenderStyle)
            .append(' ')
            .append(renderStyleString)
            .append(LineSeparator);
    }

    private static void writeFill(StringBuilder fileContents, Paint paint) {
        if (paint instanceof LinearGradientPaint linearGradientPaint) {
            writeFillLinearGradient(fileContents, linearGradientPaint);
        } else if (paint instanceof RadialGradientPaint radialGradientPaint) {
            writeFillRadialGradient(fileContents, radialGradientPaint);
        } else if (paint instanceof Color color) {
            writeFillColor(fileContents, color);
        } else if (paint instanceof TexturePaint texturePaint) {
            writeTexture(fileContents, texturePaint);
        } else {
            FastJEngine.error(
                CrashMessages.UnimplementedMethodError.errorMessage,
                new UnsupportedOperationException(
                    "Writing paints other than LinearGradientPaint, RadialGradientPaint, or Color is not supported."
                        + System.lineSeparator()
                        + "Check the github to confirm you are on the latest version, as that version may have more implemented features."
                )
            );
        }
    }

    private static void writeFillLinearGradient(StringBuilder fileContents, LinearGradientPaint linearGradientPaint) {
        fileContents.append(PsdfUtil.ParsingKeys.FillPaintLinearGradient)
            .append(' ')
            .append(linearGradientPaint.getStartPoint().getX())
            .append(' ')
            .append(linearGradientPaint.getStartPoint().getY())
            .append(' ')
            .append(linearGradientPaint.getEndPoint().getX())
            .append(' ')
            .append(linearGradientPaint.getEndPoint().getY());

        for (Color color : linearGradientPaint.getColors()) {
            if (color == null) {
                continue;
            }

            fileContents.append(' ')
                .append(color.getRed())
                .append(' ')
                .append(color.getGreen())
                .append(' ')
                .append(color.getBlue())
                .append(' ')
                .append(color.getAlpha());
        }
    }

    private static void writeFillRadialGradient(StringBuilder fileContents, RadialGradientPaint radialGradientPaint) {
        fileContents.append(PsdfUtil.ParsingKeys.FillPaintRadialGradient)
            .append(' ')
            .append(radialGradientPaint.getCenterPoint().getX())
            .append(' ')
            .append(radialGradientPaint.getCenterPoint().getY())
            .append(' ')
            .append(radialGradientPaint.getRadius());

        for (Color color : radialGradientPaint.getColors()) {
            if (color == null) {
                continue;
            }

            fileContents.append(' ')
                .append(color.getRed())
                .append(' ')
                .append(color.getGreen())
                .append(' ')
                .append(color.getBlue())
                .append(' ')
                .append(color.getAlpha());
        }
    }

    private static void writeFillColor(StringBuilder fileContents, Color color) {
        fileContents.append(PsdfUtil.ParsingKeys.FillPaintColor)
            .append(' ')
            .append(color.getRed())
            .append(' ')
            .append(color.getGreen())
            .append(' ')
            .append(color.getBlue())
            .append(' ')
            .append(color.getAlpha());
    }

    private static void writeTexture(StringBuilder fileContents, TexturePaint texturePaint) {
        fileContents.append(ParsingKeys.FillPaintTexture)
            .append(' ')
            .append('\"')
            .append(FastJEngine.getResourceManager(ImageResource.class).tryFindPathOfResource(texturePaint.getImage()).toString())
            .append('\"');
    }

    private static void writeOutline(StringBuilder fileContents, BasicStroke outlineStroke, Color outlineColor) {
        fileContents.append(LineSeparator)
            .append(PsdfUtil.ParsingKeys.OutlineStroke)
            .append(' ')
            .append(outlineStroke.getLineWidth())
            .append(' ')
            .append(outlineStroke.getEndCap())
            .append(' ')
            .append(outlineStroke.getLineJoin())
            .append(' ')
            .append(outlineStroke.getMiterLimit())
            .append(LineSeparator)
            .append(PsdfUtil.ParsingKeys.OutlineColor)
            .append(' ')
            .append(outlineColor.getRed())
            .append(' ')
            .append(outlineColor.getGreen())
            .append(' ')
            .append(outlineColor.getBlue())
            .append(' ')
            .append(outlineColor.getAlpha())
            .append(LineSeparator);
    }

    private static void writeTransform(StringBuilder fileContents, Pointf translation, float rotation, Pointf scale) {
        fileContents.append(PsdfUtil.ParsingKeys.Transform)
            .append(' ')
            .append(translation.x)
            .append(' ')
            .append(translation.y)
            .append(' ')
            .append(rotation)
            .append(' ')
            .append(scale.x)
            .append(' ')
            .append(scale.y)
            .append(LineSeparator);
    }

    private static void writeShouldRender(StringBuilder fileContents, boolean shouldRender) {
        fileContents.append(PsdfUtil.ParsingKeys.ShouldRender)
            .append(' ')
            .append(shouldRender)
            .append(LineSeparator);
    }

    private static void writeAltIndexes(StringBuilder fileContents, Polygon2D polygon) {
        if (polygon.getAlternateIndexes() == null) {
            return;
        }

        for (int j = 0; j < polygon.getAlternateIndexes().length; j++) {
            Point pt = polygon.getAlternateIndexes()[j];
            fileContents.append(ParsingKeys.AlternateIndex)
                .append(' ')
                .append(pt.x)
                .append(' ')
                .append(pt.y)
                .append(LineSeparator);
        }
    }

    private static void writeMeshPoints(StringBuilder fileContents, Polygon2D polygon) {
        Pointf[] pts = polygon.getPoints();
        for (int j = 0; j < pts.length; j++) {
            Pointf pt = pts[j];
            fileContents.append(PsdfUtil.ParsingKeys.MeshPoint)
                .append(' ')
                .append(pt.x)
                .append(' ')
                .append(pt.y)
                .append(j == pts.length - 1 ? " ;" : "")
                .append(LineSeparator);
        }
    }

    /** Keys for parsing .psdf files. */
    public static class ParsingKeys {
        private ParsingKeys() {
            throw new java.lang.IllegalStateException();
        }

        /** Empty line. */
        public static final String Empty = "";
        /** Number of {@link Polygon2D} in file. */
        public static final String Amount = "amt";
        /** Indicator of a {@link RenderStyle} present. */
        public static final String RenderStyle = "rs";
        /** {@link Polygon2D} uses {@link RenderStyle#Fill}. */
        public static final String RenderStyleFill = "fill";
        /** {@link Polygon2D} uses {@link RenderStyle#Outline}. */
        public static final String RenderStyleOutline = "outline";
        /** {@link Polygon2D} uses {@link RenderStyle#FillAndOutline}. */
        public static final String RenderStyleFillAndOutline = "filloutline";
        /** Indicator of a {@link Color color} present. */
        public static final String FillPaintColor = "c";
        /** Indicator of a {@link LinearGradientPaint linear gradient} present. */
        public static final String FillPaintLinearGradient = "lg";
        /** Indicator of a {@link RadialGradientPaint radial gradient} present. */
        public static final String FillPaintRadialGradient = "rg";
        /** Indicator of a {@link TexturePaint texture} present. */
        public static final String FillPaintTexture = "tx";
        /** Indicator of a {@link BasicStroke outline stroke} present. */
        public static final String OutlineStroke = "otls";
        /** Indicator of a {@link Color outline color} present. */
        public static final String OutlineColor = "otlc";
        /** Indicator of a {@link Transform2D transform} present. */
        public static final String Transform = "tfm";
        /** Indicator of whether the {@link Polygon2D} should be rendered. */
        public static final String ShouldRender = "sr";
        /** Indicator of a mesh point value. */
        public static final String MeshPoint = "p";
        /** Indicator of an alternate index value. */
        public static final String AlternateIndex = "aip";
    }
}
