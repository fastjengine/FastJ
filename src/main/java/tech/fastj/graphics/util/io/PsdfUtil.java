package tech.fastj.graphics.util.io;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.Transform2D;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.gradients.Gradients;
import tech.fastj.graphics.util.gradients.LinearGradientBuilder;
import tech.fastj.graphics.util.gradients.RadialGradientBuilder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PsdfUtil {

    private static final String LineSeparator = System.lineSeparator();

    /**
     * Parses the specified file contents into a {@code Polygon2D} array.
     *
     * @param lines The .psdf file, split line by line.
     * @return An array of {@code Polygon2D}s.
     */
    public static Polygon2D[] parse(List<String> lines) {
        Polygon2D[] polygons = null;
        int polygonsIndex = 0;

        List<Pointf> polygonPoints = new ArrayList<>();
        boolean shouldRender = Drawable.DefaultShouldRender;

        RenderStyle renderStyle = Polygon2D.DefaultRenderStyle;
        Paint fillPaint = Polygon2D.DefaultPaint;
        BasicStroke outlineStroke = Polygon2D.DefaultOutlineStroke;
        Color outlineColor = Polygon2D.DefaultOutlineColor;

        Pointf translation = Transform2D.DefaultTranslation.copy();
        float rotation = Transform2D.DefaultRotation;
        Pointf scale = Transform2D.DefaultScale.copy();

        for (String words : lines) {
            String[] tokens = words.split("\\s+");
            switch (tokens[0]) {
                case ParsingKeys.Amount: {
                    polygons = parsePolygonCount(tokens);
                    break;
                }
                case ParsingKeys.FillPaint_Color:
                case ParsingKeys.FillPaint_LinearGradient:
                case ParsingKeys.FillPaint_RadialGradient: {
                    fillPaint = parsePaint(tokens);
                    break;
                }
                case ParsingKeys.Outline_Stroke: {
                    outlineStroke = parseOutlineStroke(tokens);
                    break;
                }
                case ParsingKeys.Outline_Color: {
                    outlineColor = parseOutlineColor(tokens);
                    break;
                }
                case ParsingKeys.RenderStyle: {
                    renderStyle = parseRenderStyle(tokens);
                    break;
                }
                case ParsingKeys.Transform: {
                    translation = parseTranslation(tokens);
                    rotation = parseRotation(tokens);
                    scale = parseScale(tokens);
                    break;
                }
                case ParsingKeys.ShouldRender: {
                    shouldRender = parseShouldRender(tokens);
                    break;
                }
                case ParsingKeys.MeshPoint: {
                    polygonPoints.add(parseMeshPoint(tokens));

                    // if end of polygon, add polygon to array
                    if (tokens.length == 4 && tokens[3].equals(";")) {
                        assert polygons != null;
                        polygons[polygonsIndex] = Polygon2D.create(polygonPoints.toArray(new Pointf[0]), renderStyle, shouldRender)
                                .withFill(fillPaint)
                                .withOutline(outlineStroke, outlineColor)
                                .withTransform(translation, rotation, scale)
                                .build();

                        // reset values
                        polygonPoints.clear();
                        shouldRender = Drawable.DefaultShouldRender;

                        renderStyle = Polygon2D.DefaultRenderStyle;
                        fillPaint = Polygon2D.DefaultPaint;
                        outlineStroke = Polygon2D.DefaultOutlineStroke;
                        outlineColor = Polygon2D.DefaultOutlineColor;

                        translation = Transform2D.DefaultTranslation.copy();
                        rotation = Transform2D.DefaultRotation;
                        scale = Transform2D.DefaultScale.copy();

                        // increase array location
                        polygonsIndex++;
                    }

                    break;
                }
            }
        }
        return polygons;
    }

    private static Polygon2D[] parsePolygonCount(String[] tokens) {
        return new Polygon2D[Integer.parseInt(tokens[1])];
    }

    private static Paint parsePaint(String[] tokens) {
        switch (tokens[0]) {
            case ParsingKeys.FillPaint_Color: {
                return new Color(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
            }
            case ParsingKeys.FillPaint_LinearGradient: {
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
            case ParsingKeys.FillPaint_RadialGradient: {
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
            default: {
                throw new IllegalStateException("Invalid fill paint type: " + tokens[0]);
            }
        }
    }

    private static BasicStroke parseOutlineStroke(String[] tokens) {
        int basicStrokeCap;
        switch (Integer.parseInt(tokens[2])) {
            case BasicStroke.CAP_BUTT: {
                basicStrokeCap = BasicStroke.CAP_BUTT;
                break;
            }
            case BasicStroke.CAP_ROUND: {
                basicStrokeCap = BasicStroke.CAP_ROUND;
                break;
            }
            case BasicStroke.CAP_SQUARE: {
                basicStrokeCap = BasicStroke.CAP_SQUARE;
                break;
            }
            default: {
                throw new IllegalStateException("Invalid BasicStroke Cap value: " + Integer.parseInt(tokens[2]));
            }
        }

        int basicStrokeJoinStyle;
        switch (Integer.parseInt(tokens[3])) {
            case BasicStroke.JOIN_MITER: {
                basicStrokeJoinStyle = BasicStroke.JOIN_MITER;
                break;
            }
            case BasicStroke.JOIN_ROUND: {
                basicStrokeJoinStyle = BasicStroke.JOIN_ROUND;
                break;
            }
            case BasicStroke.JOIN_BEVEL: {
                basicStrokeJoinStyle = BasicStroke.JOIN_BEVEL;
                break;
            }
            default: {
                throw new IllegalStateException("Invalid BasicStroke Join value: " + Integer.parseInt(tokens[3]));
            }
        }

        return new BasicStroke(Integer.parseInt(tokens[1]), basicStrokeCap, basicStrokeJoinStyle, Integer.parseInt(tokens[4]), null, 0.0f);
    }

    private static Color parseOutlineColor(String[] tokens) {
        return new Color(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
    }

    private static RenderStyle parseRenderStyle(String[] tokens) {
        switch (tokens[1]) {
            case ParsingKeys.RenderStyle_Fill: {
                return RenderStyle.Fill;
            }
            case ParsingKeys.RenderStyle_Outline: {
                return RenderStyle.Outline;
            }
            case ParsingKeys.RenderStyle_FillAndOutline: {
                return RenderStyle.FillAndOutline;
            }
            default: {
                throw new IllegalStateException("Invalid render style: " + tokens[1]);
            }
        }
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

    private static Pointf parseMeshPoint(String[] tokens) {
        return new Pointf(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
    }

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
        fileContents.append(PsdfUtil.ParsingKeys.RenderStyle).append(' ');
        switch (renderStyle) {
            case Fill: {
                fileContents.append(PsdfUtil.ParsingKeys.RenderStyle_Fill);
            }
            case Outline: {
                fileContents.append(PsdfUtil.ParsingKeys.RenderStyle_Outline);
            }
            case FillAndOutline: {
                fileContents.append(PsdfUtil.ParsingKeys.RenderStyle_FillAndOutline);
            }
        }
        fileContents.append(LineSeparator);
    }

    private static void writeFill(StringBuilder fileContents, Paint paint) {
        if (paint instanceof LinearGradientPaint) {
            writeFill_LinearGradient(fileContents, (LinearGradientPaint) paint);
        } else if (paint instanceof RadialGradientPaint) {
            writeFill_RadialGradient(fileContents, (RadialGradientPaint) paint);
        } else if (paint instanceof Color) {
            writeFill_Color(fileContents, (Color) paint);
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

    private static void writeFill_LinearGradient(StringBuilder fileContents, LinearGradientPaint linearGradientPaint) {
        fileContents.append(PsdfUtil.ParsingKeys.FillPaint_LinearGradient)
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

    private static void writeFill_RadialGradient(StringBuilder fileContents, RadialGradientPaint radialGradientPaint) {
        fileContents.append(PsdfUtil.ParsingKeys.FillPaint_RadialGradient)
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

    private static void writeFill_Color(StringBuilder fileContents, Color color) {
        fileContents.append(PsdfUtil.ParsingKeys.FillPaint_Color)
                .append(' ')
                .append(color.getRed())
                .append(' ')
                .append(color.getGreen())
                .append(' ')
                .append(color.getBlue())
                .append(' ')
                .append(color.getAlpha());
    }

    private static void writeOutline(StringBuilder fileContents, BasicStroke outlineStroke, Color outlineColor) {
        fileContents.append(LineSeparator)
                .append(PsdfUtil.ParsingKeys.Outline_Stroke)
                .append(' ')
                .append(outlineStroke.getLineWidth())
                .append(' ')
                .append(outlineStroke.getEndCap())
                .append(' ')
                .append(outlineStroke.getLineJoin())
                .append(' ')
                .append(outlineStroke.getMiterLimit())
                .append(LineSeparator)
                .append(PsdfUtil.ParsingKeys.Outline_Color)
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

    private static void writeMeshPoints(StringBuilder fileContents, Polygon2D polygon) {
        for (int j = 0; j < polygon.getPoints().length; j++) {
            Pointf pt = polygon.getPoints()[j];
            fileContents.append(PsdfUtil.ParsingKeys.MeshPoint)
                    .append(' ')
                    .append(pt.x)
                    .append(' ')
                    .append(pt.y)
                    .append(j == polygon.getPoints().length - 1 ? " ;" : "")
                    .append(LineSeparator);
        }
    }

    /** Keys for parsing .psdf files. */
    public static class ParsingKeys {
        public static final String Amount = "amt";
        public static final String RenderStyle = "rs";
        public static final String RenderStyle_Fill = "fill";
        public static final String RenderStyle_Outline = "outline";
        public static final String RenderStyle_FillAndOutline = "filloutline";
        public static final String FillPaint_Color = "c";
        public static final String FillPaint_LinearGradient = "lg";
        public static final String FillPaint_RadialGradient = "rg";
        public static final String Outline_Stroke = "otls";
        public static final String Outline_Color = "otlc";
        public static final String Transform = "tfm";
        public static final String ShouldRender = "sr";
        public static final String MeshPoint = "p";
    }
}
