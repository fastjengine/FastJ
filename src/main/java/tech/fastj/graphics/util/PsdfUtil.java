package tech.fastj.graphics.util;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.gradients.Gradients;
import tech.fastj.graphics.util.gradients.LinearGradientBuilder;
import tech.fastj.graphics.util.gradients.RadialGradientBuilder;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PsdfUtil {

    private static final String PsdfReadErrorMessage = CrashMessages.theGameCrashed("a .psdf file reading error.");
    private static final String PsdfWriteErrorMessage = CrashMessages.theGameCrashed("a .psdf file writing error.");

    /**
     * Gets a {@code Polygon2D} array, loaded from a {@code .psdf} file.
     * <p>
     * This method allows the user to load an array of {@code Polygon2D}s from a single file, decreasing the amount of
     * models that have to be programmed in.
     * <p>
     * Furthermore, this allows for easy use of the {@code Model2D} class, allowing you to directly use the resulting
     * array from this method to create a {@code Model2D} object.
     *
     * @param fileLocation Location of the file.
     * @return An array of {@code Polygon2D}s.
     */
    public static Polygon2D[] loadPsdf(String fileLocation) {
        // check for correct file extension
        if (!fileLocation.substring(fileLocation.lastIndexOf(".") + 1).equalsIgnoreCase("psdf")) {
            FastJEngine.error(PsdfReadErrorMessage,
                    new IllegalArgumentException("Unsupported file type."
                            + System.lineSeparator()
                            + "This engine currently only supports files of the extension \".psdf\".")
            );
        }

        return parsePsdf(fileLocation);
    }

    /**
     * Parses the content of the file at the location of the string into a {@code Polygon2D} array.
     *
     * @param fileLocation The location of the .psdf file.
     * @return An array of {@code Polygon2D}s.
     */
    private static Polygon2D[] parsePsdf(String fileLocation) {
        try {
            Polygon2D[] result = null;
            List<String> lines = Files.readAllLines(Paths.get(fileLocation));
            List<Pointf> polygonPoints = new ArrayList<>();
            Paint paint = null;
            boolean fillPolygon = false;
            boolean renderPolygon = false;
            int arrayLoc = 0;

            /* Checks through each line in the list, and sets variables based on the
             * first word of each line. */
            for (String words : lines) {
                String[] tokens = words.split("\\s+");
                switch (tokens[0]) {
                    case "amt": { // sets the total amount of polygons in the array
                        result = new Polygon2D[Integer.parseInt(tokens[1])];
                        break;
                    }
                    case "c": { // set paint as Color
                        paint = new Color(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                        break;
                    }
                    case "lg": { // set paint as LinearGradientPaint
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

                        paint = linearGradientBuilder.build();
                        break;
                    }
                    case "rg": { // set paint as RadialGradientPaint
                        RadialGradientBuilder radialGradientBuilder = Gradients.radialGradient();
                        radialGradientBuilder.position(
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

                        paint = radialGradientBuilder.build();
                        break;
                    }
                    case "f": { // set fill value
                        fillPolygon = Boolean.parseBoolean(tokens[1]);
                        break;
                    }
                    case "s": { // set show value
                        renderPolygon = Boolean.parseBoolean(tokens[1]);
                        break;
                    }
                    case "p": { // add point to point list
                        polygonPoints.add(new Pointf(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));

                        // if end of polygon, add polygon to array
                        if (tokens.length == 4 && tokens[3].equals(";")) {
                            assert result != null;
                            result[arrayLoc] = new Polygon2D(polygonPoints.toArray(new Pointf[0]), paint, fillPolygon, renderPolygon);

                            // reset values
                            paint = null;
                            fillPolygon = false;
                            renderPolygon = false;
                            polygonPoints.clear();

                            // increase array location
                            arrayLoc++;
                        }

                        break;
                    }
                }
            }
            return result;
        } catch (IOException e) {
            FastJEngine.error(PsdfReadErrorMessage, e);
            return null;
        }
    }

    /**
     * Writes a {@code .psdf} file containing the current state of the {@code Polygon2D}s that make up the specified
     * {@code Model2D}.
     *
     * @param destPath The destination path of the {@code .psdf} file that will be written.
     * @param model    The {@code Model2D} that will be written to the file.
     */
    public static void writePsdf(String destPath, Model2D model) {
        try {
            String sep = System.lineSeparator();
            StringBuilder fileContents = new StringBuilder();

            // write object count
            fileContents.append("amt ").append(model.getPolygons().length).append(sep);

            for (int i = 0; i < model.getPolygons().length; i++) {
                Polygon2D obj = model.getPolygons()[i];
                Paint paint = obj.getPaint();

                // Write obj paint, fill, show
                if (paint instanceof LinearGradientPaint) {
                    LinearGradientPaint linearGradientPaint = (LinearGradientPaint) paint;
                    fileContents.append("lg ")
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
                } else if (paint instanceof RadialGradientPaint) {
                    RadialGradientPaint radialGradientPaint = (RadialGradientPaint) paint;
                    fileContents.append("rg ")
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
                } else if (paint instanceof Color) {
                    Color color = (Color) paint;
                    fileContents.append("c ")
                            .append(color.getRed())
                            .append(' ')
                            .append(color.getGreen())
                            .append(' ')
                            .append(color.getBlue())
                            .append(' ')
                            .append(color.getAlpha());
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

                fileContents.append(sep)
                        .append("f ").append(obj.isFilled())
                        .append(sep)
                        .append("s ").append(obj.shouldRender())
                        .append(sep);

                // Write each point in object
                for (int j = 0; j < obj.getPoints().length; j++) {
                    Pointf pt = obj.getPoints()[j];
                    fileContents.append("p ")
                            .append((int) pt.x == pt.x ? Integer.toString((int) pt.x) : pt.x)
                            .append(' ')
                            .append((int) pt.y == pt.y ? Integer.toString((int) pt.y) : pt.y)
                            .append(j == obj.getPoints().length - 1 ? " ;" : "")
                            .append(sep);
                }

                // if there are more objects after this object, then add a new line.
                if (i != model.getPolygons().length - 1) fileContents.append(sep);
            }

            Files.writeString(Paths.get(destPath), fileContents, StandardCharsets.UTF_8);
        } catch (IOException e) {
            FastJEngine.error(PsdfWriteErrorMessage, e);
        }
    }
}
