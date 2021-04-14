package io.github.lucasstarsz.fastj.graphics;

import io.github.lucasstarsz.fastj.graphics.gameobject.shapes.Model2D;
import io.github.lucasstarsz.fastj.graphics.gameobject.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;

import io.github.lucasstarsz.fastj.engine.CrashMessages;
import io.github.lucasstarsz.fastj.engine.FastJEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that provides supplementary methods for working with {@link Drawable}s.
 * <p>
 * This class is also used to load 2-dimensional model files, of the {@code ".psdf"} file extension.
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public final class DrawUtil {

    private static final String PsdfReadErrorMessage = CrashMessages.theGameCrashed("a .psdf file reading error.");
    private static final String PsdfWriteErrorMessage = CrashMessages.theGameCrashed("a .psdf file reading error.");

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
    public static Polygon2D[] load2DModel(String fileLocation) {
        // check for correct file extension
        if (!fileLocation.substring(fileLocation.lastIndexOf(".") + 1).equalsIgnoreCase("psdf")) {
            FastJEngine.error(PsdfReadErrorMessage,
                    new IllegalArgumentException("Unsupported file type."
                            + System.lineSeparator()
                            + "This engine currently only supports files of the extension \".psdf\".")
            );
        }

        return parseModelFile(fileLocation);
    }

    /**
     * Parses the content of the file at the location of the string into a {@code Polygon2D} array.
     *
     * @param fileLocation The location of the .psdf file.
     * @return An array of {@code Polygon2D}s.
     */
    private static Polygon2D[] parseModelFile(String fileLocation) {
        try {
            Polygon2D[] result = null;
            List<String> lines = Files.readAllLines(Paths.get(fileLocation));
            List<Pointf> polygonPoints = new ArrayList<>();
            Color polygonColor = null;
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
                    case "c": { // set color r, g, b, a
                        polygonColor = new Color(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
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
                            result[arrayLoc] = new Polygon2D(polygonPoints.toArray(new Pointf[0]), polygonColor, fillPolygon, renderPolygon);

                            // reset values
                            polygonColor = null;
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
    public static void writeToPSDF(String destPath, Model2D model) {
        try {
            String sep = System.lineSeparator();
            StringBuilder fileContents = new StringBuilder();

            // write object count
            fileContents.append("amt ").append(model.getPolygons().length).append(sep);

            for (int i = 0; i < model.getPolygons().length; i++) {
                Polygon2D obj = model.getPolygons()[i];
                Color c = obj.getColor();

                // Write obj color, fill, show
                fileContents.append("c ").append(c.getRed()).append(' ').append(c.getGreen()).append(' ').append(c.getBlue()).append(' ').append(c.getAlpha())
                        .append(sep)
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

    /**
     * Creates a {@code Pointf} array representing an outline of the specified {@code Polygon2D} array.
     * <p>
     * <b>NOTE:</b> This method likely will not provide a completely accurate outline of the array
     * of {@code Polygon2D} objects.
     *
     * @param polyList The Array of {@code Polygon2D}s that will be used to create the outline of {@code Pointf}s.
     * @return A {@code Pointf} array that makes up the outline of the specified {@code Polygon2D} array.
     */
    public static Pointf[] createCollisionOutline(Polygon2D[] polyList) {
        List<Pointf> polyListPoints = new ArrayList<>();
        for (Polygon2D obj : polyList) {
            polyListPoints.addAll(Arrays.asList(obj.getPoints()));
        }

        for (int i = (polyListPoints.size() - 1); i > -1; i--) {
            int intersectionCount = 0;
            // if a point intersects with more than one polygon, then it is an inner point and should be removed
            for (Polygon2D polygon : polyList) {
                if (Path2D.Float.intersects(polygon.collisionPath.getPathIterator(null), polyListPoints.get(i).x - 1f, polyListPoints.get(i).y - 1f, 2f, 2f)) {
                    intersectionCount++;
                    if (intersectionCount == 2) {
                        polyListPoints.remove(i);
                        break;
                    }
                }
            }
        }

        Pointf[] unshiftedResult = polyListPoints.toArray(new Pointf[0]);
        Pointf center = centerOf(unshiftedResult);
        Arrays.sort(unshiftedResult, (a, b) -> {
            // thank goodness for stackoverflow...
            if (a.x - center.x >= 0 && b.x - center.x < 0)
                return 1;
            if (a.x - center.x < 0 && b.x - center.x >= 0)
                return -1;
            if (a.x - center.x == 0 && b.x - center.x == 0) {
                if (a.y - center.y >= 0 || b.y - center.y >= 0)
                    return (a.y > b.y) ? 1 : -1;
                return (b.y > a.y) ? 1 : -1;
            }

            // compute the cross product of vectors (center -> a) x (center -> b)
            float det = (a.x - center.x) * (b.y - center.y) - (b.x - center.x) * (a.y - center.y);
            if (det < 0f)
                return 1;
            if (det > 0f)
                return -1;

            // points a and b are on the same line from the center
            // check which point is closer to the center
            float d1 = (a.x - center.x) * (a.x - center.x) + (a.y - center.y) * (a.y - center.y);
            float d2 = (b.x - center.x) * (b.x - center.x) + (b.y - center.y) * (b.y - center.y);
            return (d1 > d2) ? 1 : -1;
        });

        /* Now, we just need to shift the array values to match the original order. */

        // calculate amount to shift
        int shiftAmount = 0;
        for (int i = 0; i < unshiftedResult.length; i++) {
            if (unshiftedResult[i].equals(polyListPoints.get(0))) {
                shiftAmount = i;
                break;
            }
        }

        // in case shifting isn't needed
        if (shiftAmount == 0) {
            return unshiftedResult;
        }

        // do some shifting
        Pointf[] shiftedResult = new Pointf[unshiftedResult.length];

        System.arraycopy(unshiftedResult, shiftAmount, shiftedResult, 0, shiftedResult.length - shiftAmount);
        System.arraycopy(unshiftedResult, 0, shiftedResult, shiftedResult.length - shiftAmount, shiftAmount);

        return shiftedResult;
    }

    /**
     * Creates a {@code Path2D.Float} based on the specified {@code Pointf} array.
     *
     * @param pts The {@code Pointf} array to create the {@code Path2D.Float} from.
     * @return The resulting {@code Path2D.Float}.
     */
    public static Path2D.Float createPath(Pointf[] pts) {
        Path2D.Float p = new Path2D.Float();

        p.moveTo(pts[0].x, pts[0].y);
        for (int i = 1; i < pts.length; i++) {
            p.lineTo(pts[i].x, pts[i].y);
        }
        p.closePath();

        return p;
    }

    /**
     * Checks for equality in length and point values between two {@link Path2D} objects.
     *
     * Order does not matter for equality checking.
     *
     * @param path1 The first {@code Path2D} specified.
     * @param path2 The second {@code Path2D} specified.
     * @return Whether the two {@code Path2D}s are equal.
     */
    public static boolean pathEquals(Path2D path1, Path2D path2) {
        if (path1 == path2) {
            return true;
        }

        int pLength1 = lengthOfPath(path1);
        int pLength2 = lengthOfPath(path2);
        if (pLength1 != pLength2) {
            throw new IllegalStateException("Path lengths differ\nPath 1 had a length of " + pLength1 + ", but path 2 had a length of " + pLength2 + ".");
        }

        PathIterator pathIt1 = path1.getPathIterator(null);
        PathIterator pathIt2 = path2.getPathIterator(null);

        double[] coords1 = new double[2];
        double[] coords2 = new double[2];

        while (true) {
            pathIt1.currentSegment(coords1);
            pathIt2.currentSegment(coords2);

            if (coords1[0] != coords2[0] || coords1[1] != coords2[1]) {
                return false;
            }

            if (!pathIt1.isDone() && !pathIt2.isDone()) {
                pathIt1.next();
                pathIt2.next();
            } else {
                break;
            }
        }

        return true;
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified x, y, width, and height floats.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(x, y),
     *     new Pointf(x + width, y),
     *     new Pointf(x + width, y + height),
     *     new Pointf(x, y + height)
     * };
     * </pre>
     *
     * @param x      The x location.
     * @param y      The y location.
     * @param width  The width.
     * @param height The height.
     * @return A 4 {@code Pointf} array based on the x, y, width, and height specified.
     */
    public static Pointf[] createBox(float x, float y, float width, float height) {
        return new Pointf[]{
                new Pointf(x, y),
                new Pointf(x + width, y),
                new Pointf(x + width, y + height),
                new Pointf(x, y + height)
        };
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified x, y, and size floats.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(x, y),
     *     new Pointf(x + size, y),
     *     new Pointf(x + size, y + size),
     *     new Pointf(x, y + size)
     * };
     * </pre>
     *
     * @param x    The x location.
     * @param y    The y location.
     * @param size The width and height.
     * @return A 4 {@code Pointf} array based on the x, y, and size specified.
     */
    public static Pointf[] createBox(float x, float y, float size) {
        return createBox(x, y, size, size);
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified location {@code Pointf} and size float.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(location.x, location.y),
     *     new Pointf(location.x + size, location.y),
     *     new Pointf(location.x + size, location.y + size),
     *     new Pointf(location.x, location.y + size)
     * };
     * </pre>
     *
     * @param location The x and y locations.
     * @param size     The width and height.
     * @return A 4 {@code Pointf} array based on the location and size specified.
     */
    public static Pointf[] createBox(Pointf location, float size) {
        return createBox(location.x, location.y, size);
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified x and y floats, and size {@code Pointf}.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(x, y),
     *     new Pointf(x + size.x, y),
     *     new Pointf(x + size.x, y + size.y),
     *     new Pointf(x, y + size.y)
     * };
     * </pre>
     *
     * @param x    The x location.
     * @param y    The y location.
     * @param size The width and height.
     * @return A 4 {@code Pointf} array based on the x, y, and size specified.
     */
    public static Pointf[] createBox(float x, float y, Pointf size) {
        return createBox(x, y, size.x, size.y);
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified location {@code Pointf} and size {@code
     * Pointf}.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(location.x, location.y),
     *     new Pointf(location.x + size.x, location.y),
     *     new Pointf(location.x + size.x, location.y + size.y),
     *     new Pointf(location.x, location.y + size.y)
     * };
     * </pre>
     *
     * @param location The x and y locations.
     * @param size     The width and height.
     * @return A 4 {@code Pointf} array based on the location and size specified.
     */
    public static Pointf[] createBox(Pointf location, Pointf size) {
        return createBox(location.x, location.y, size.x, size.y);
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the {@code Rectangle2D.Float} parameter.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(r.x, r.y),
     *     new Pointf(r.x + r.width, r.y),
     *     new Pointf(r.x + r.width, r.y + r.height),
     *     new Pointf(r.x, r.y + r.height)
     * };
     * </pre>
     *
     * @param r The rectangle.
     * @return A 4 {@code Pointf} array based on the location and size specified.
     */
    public static Pointf[] createBox(Rectangle2D.Float r) {
        return createBox(r.x, r.y, r.width, r.height);
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified {@code BufferedImage} and the location {@code
     * Pointf}.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(location.x, location.y),
     *     new Pointf(location.x + source.getWidth(), location.y),
     *     new Pointf(location.x + source.getWidth(), location.y + source.getHeight()),
     *     new Pointf(location.x, location.y + source.getHeight())
     * };
     * </pre>
     *
     * @param source   The source for the width and height.
     * @param location The x and y locations.
     * @return A 4 {@code Pointf} array based on the location and size specified.
     */
    public static Pointf[] createBoxFromImage(BufferedImage source, Pointf location) {
        return new Pointf[]{
                new Pointf(location.x, location.y),
                new Pointf(location.x + source.getWidth(), location.y),
                new Pointf(location.x + source.getWidth(), location.y + source.getHeight()),
                new Pointf(location.x, location.y + source.getHeight())
        };
    }

    /**
     * Creates a {@code Pointf} array of 4 points, based on the specified {@code BufferedImage}.
     * <p>
     * This creates an array with the following values:
     * <pre>
     * new Pointf[] {
     *     new Pointf(0, 0),
     *     new Pointf(source.getWidth(), 0),
     *     new Pointf(source.getWidth(), source.getHeight()),
     *     new Pointf(0, source.getHeight())
     * };
     * </pre>
     *
     * @param source The source for the width and height.
     * @return A 4 {@code Pointf} array based on the location and size specified.
     */
    public static Pointf[] createBoxFromImage(BufferedImage source) {
        return createBoxFromImage(source, new Pointf());
    }

    /**
     * Creates a {@code Rectangle2D.Float} based on the specified {@code Pointf} array.
     *
     * <b>NOTE:</b> the {@code pts} parameter must have a length of 4.
     *
     * @param pts The {@code Pointf} array used to create the rectangle.
     * @return The resultant {@code Rectangle2D.Float}.
     */
    public static Rectangle2D.Float createRect(Pointf[] pts) {
        if (pts.length != 4) {
            FastJEngine.error(
                    CrashMessages.theGameCrashed("a rectangle creation error."),
                    new IllegalArgumentException("The length of the parameter point array must be 4.")
            );
        }

        return new Rectangle2D.Float(pts[0].x, pts[0].y, pts[1].x - pts[0].x, pts[3].y - pts[0].y);
    }

    /**
     * Creates a {@code Rectangle2D.Float} based on the specified {@code BufferedImage} and the location {@code
     * Pointf}.
     *
     * @param source      The source for the width and height.
     * @param translation The x and y locations.
     * @return The resultant {@code Rectangle2D.Float}.
     */
    public static Rectangle2D.Float createRectFromImage(BufferedImage source, Pointf translation) {
        return new Rectangle2D.Float(translation.x, translation.y, source.getWidth(), source.getHeight());
    }

    /**
     * Gets the numerical x and y center of the specified {@code Pointf} array.
     *
     * @param points The array to get the center of.
     * @return The center of the array.
     */
    public static Pointf centerOf(Pointf[] points) {
        Pointf result = new Pointf();
        for (Pointf p : points) result.add(p);
        return result.divide(points.length);
    }

    /**
     * Gets a {@code Pointf} array that represents the points of the {@code Path2D.Float} parameter.
     *
     * @param path The path to get the points of.
     * @return The resultant array of points.
     */
    public static Pointf[] pointsOfPath(Path2D.Float path) {
        List<Pointf> pointList = new ArrayList<>();
        float[] coords = new float[2];
        int numSubPaths = 0;

        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                    pointList.add(new Pointf(coords[0], coords[1]));
                    numSubPaths++;
                    break;
                case PathIterator.SEG_LINETO:
                    pointList.add(new Pointf(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_CLOSE:
                    if (numSubPaths > 1) {
                        throw new IllegalArgumentException("Path contains multiple sub-paths");
                    }
                    return pointList.toArray(new Pointf[0]);
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        throw new IllegalArgumentException("Unclosed path");
    }

    /**
     * Gets the amount of points in the specified {@code Path2D}.
     *
     * @param path The path to check point amount of.
     * @return The amount of points in the path.
     */
    public static int lengthOfPath(Path2D path) {
        int count = 0;
        double[] coords = new double[2];

        int numSubPaths = 0;

        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                    break;
                case PathIterator.SEG_CLOSE:
                    numSubPaths++;
                    break;
                case PathIterator.SEG_LINETO:
                    count++;
                    break;
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }

        return count + numSubPaths;
    }

    /**
     * Generates a random {@code Color}, while leaving the alpha to its default value (255).
     *
     * @return The randomly generated {@code Color}.
     */
    public static Color randomColor() {
        return new Color((int) Maths.random(0, 255), (int) Maths.random(0, 255), (int) Maths.random(0, 255), 255);
    }

    /**
     * Generates a random {@code Color}, including the alpha level of the color.
     *
     * @return The randomly generated {@code Color}.
     */
    public static Color randomColorWithAlpha() {
        return new Color((int) Maths.random(0, 255), (int) Maths.random(0, 255), (int) Maths.random(0, 255), (int) Maths.random(0, 255));
    }

    /**
     * Generates a random {@link Font} using the available fonts on the system, with a font size within the range of
     * {@code 1-256}, inclusive.
     *
     * @return The randomly generated {@link Font}.
     */
    public static Font randomFont() {
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        int fontRandom = Maths.randomInteger(0, 2);
        int font = fontRandom == 0
                ? Font.PLAIN
                : fontRandom == 1
                ? Font.BOLD
                : Font.ITALIC;

        return new Font(fontNames[Maths.randomInteger(0, fontNames.length - 1)], font, Maths.randomInteger(1, 256));
    }
}
