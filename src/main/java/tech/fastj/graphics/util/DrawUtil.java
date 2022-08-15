package tech.fastj.graphics.util;

import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.logging.Log;
import tech.fastj.math.Maths;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that provides supplementary methods for working with {@link Drawable}s.
 * <p>
 * This class is also used to load 2-dimensional model files, of the {@code ".psdf"} file extension.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public final class DrawUtil {

    private DrawUtil() {
        throw new java.lang.IllegalStateException();
    }

    /**
     * Creates a {@code Pointf} array representing an outline of the specified {@code Polygon2D} array.
     * <p>
     * <b>NOTE:</b> This method likely will not provide a completely accurate outline of the array
     * of {@code Polygon2D} objects.
     *
     * @param polygons The Array of {@code Polygon2D}s that will be used to create the outline of {@code Pointf}s.
     * @return A {@code Pointf} array that makes up the outline of the specified {@code Polygon2D} array.
     */
    public static Pointf[] createCollisionOutline(Polygon2D[] polygons) {
        List<Pointf> polygonsPoints = new ArrayList<>();
        for (Polygon2D polygon : polygons) {
            polygonsPoints.addAll(Arrays.asList(polygon.getPoints()));
        }

        for (int i = (polygonsPoints.size() - 1); i > -1; i--) {
            int intersectionCount = 0;
            // if a point intersects with more than one polygon, then it is an inner point and should be removed
            for (Polygon2D polygon : polygons) {
                if (Path2D.Float.intersects(polygon.getCollisionPath()
                    .getPathIterator(null), polygonsPoints.get(i).x - 1f, polygonsPoints.get(i).y - 1f, 2f, 2f)) {
                    intersectionCount++;
                    if (intersectionCount == 2) {
                        polygonsPoints.remove(i);
                        break;
                    }
                }
            }
        }

        Pointf[] unshiftedResult = polygonsPoints.toArray(new Pointf[0]);
        Pointf center = centerOf(unshiftedResult);
        Arrays.sort(unshiftedResult, (a, b) -> {
            // thank goodness for stackoverflow...
            if (a.x - center.x >= 0 && b.x - center.x < 0) {
                return 1;
            }
            if (a.x - center.x < 0 && b.x - center.x >= 0) {
                return -1;
            }
            if (a.x - center.x == 0 && b.x - center.x == 0) {
                if (a.y - center.y >= 0 || b.y - center.y >= 0) {
                    return (a.y > b.y) ? 1 : -1;
                }

                return (b.y > a.y) ? 1 : -1;
            }

            // compute the cross product of vectors (center -> a) x (center -> b)
            float det = (a.x - center.x) * (b.y - center.y) - (b.x - center.x) * (a.y - center.y);
            if (det < 0f) {
                return 1;
            }
            if (det > 0f) {
                return -1;
            }

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
            if (unshiftedResult[i].equals(polygonsPoints.get(0))) {
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
     * Creates a {@code Path2D.Float} based on the specified points and alternate indexes.
     * <p>
     * See {@link Polygon2D} to learn more about alternate indexes.
     *
     * @param pts        The {@code Pointf} array to create the {@code Path2D.Float} from.
     * @param altIndexes The {@code Point} array defining alternate indexes for creating the {@code Path2D.Float} from.
     * @return The resulting {@code Path2D.Float}.
     */
    public static Path2D.Float createPath(Pointf[] pts, Point[] altIndexes) {
        if (altIndexes == null || altIndexes.length == 0) {
            return createPath(pts);
        }

        Path2D.Float p = new Path2D.Float();

        p.moveTo(pts[0].x, pts[0].y);
        for (int i = 1, ai = 0; i < pts.length; i++) {
            if (altIndexes[ai].x == i) {
                switch (altIndexes[ai++].y) {
                    case Polygon2D.MovePath -> p.moveTo(pts[i].x, pts[i].y);
                    case Polygon2D.QuadCurve -> {
                        p.quadTo(pts[i].x, pts[i].y, pts[i + 1].x, pts[i + 1].y);
                        i += 1;
                    }
                    case Polygon2D.BezierCurve -> {
                        p.curveTo(pts[i].x, pts[i].y, pts[i + 1].x, pts[i + 1].y, pts[i + 2].x, pts[i + 2].y);
                        i += 2;
                    }
                    default -> throw new UnsupportedOperationException(
                        "No known path option for " + altIndexes[ai - 1].y + " on index " + i + " of the path."
                    );
                }
            } else {
                p.lineTo(pts[i].x, pts[i].y);
            }
        }
        p.closePath();

        return p;
    }

    /**
     * Checks for equality in length and point values between two {@link Path2D} objects.
     * <p>
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
     * Checks for equality between two {@link Paint} objects as best as possible.
     *
     * @param paint1 The first {@code Paint} specified.
     * @param paint2 The second {@code Paint} specified.
     * @return Whether the two {@code Paint}s are equal.
     */
    public static boolean paintEquals(Paint paint1, Paint paint2) {
        if (paint1 == paint2) {
            return true;
        }

        if (paint1 == null || paint2 == null || paint1.getClass() != paint2.getClass()) {
            return false;
        }

        if (paint1 instanceof RadialGradientPaint radialGradientPaint1) {
            var radialGradientPaint2 = (RadialGradientPaint) paint2;

            return radialGradientPaint1.getCenterPoint().equals(radialGradientPaint2.getCenterPoint())
                && radialGradientPaint1.getFocusPoint().equals(radialGradientPaint2.getFocusPoint())
                && Maths.floatEquals(radialGradientPaint1.getRadius(), radialGradientPaint2.getRadius())
                && mGradientEquals(radialGradientPaint1, radialGradientPaint2);
        }

        if (paint1 instanceof LinearGradientPaint linearGradientPaint1) {
            var linearGradientPaint2 = (LinearGradientPaint) paint2;

            return linearGradientPaint1.getStartPoint().equals(linearGradientPaint2.getStartPoint())
                && linearGradientPaint1.getEndPoint().equals(linearGradientPaint2.getEndPoint())
                && mGradientEquals(linearGradientPaint1, linearGradientPaint2);
        }

        if (paint1 instanceof GradientPaint gradientPaint1) {
            var gradientPaint2 = (GradientPaint) paint2;

            return gradientPaint1.isCyclic() == gradientPaint2.isCyclic()
                && gradientPaint1.getTransparency() == gradientPaint2.getTransparency()
                && gradientPaint1.getColor1().equals(gradientPaint2.getColor1())
                && gradientPaint1.getColor2().equals(gradientPaint2.getColor2())
                && gradientPaint1.getPoint1().equals(gradientPaint2.getPoint1())
                && gradientPaint1.getPoint2().equals(gradientPaint2.getPoint2());
        }

        if (paint1 instanceof TexturePaint texturePaint1) {
            var texturePaint2 = (TexturePaint) paint2;

            return texturePaint1.getTransparency() == texturePaint2.getTransparency()
                && texturePaint1.getAnchorRect().equals(texturePaint2.getAnchorRect())
                && texturePaint1.getImage().equals(texturePaint2.getImage());
        }

        return paint1.equals(paint2);
    }

    /**
     * Shorthand for checking equality between two {@link MultipleGradientPaint} objects.
     *
     * @param mGradientPaint1 The first {@code Paint} specified.
     * @param mGradientPaint2 The second {@code Paint} specified.
     * @return Whether the two {@code Paint}s are equal.
     */
    private static boolean mGradientEquals(MultipleGradientPaint mGradientPaint1, MultipleGradientPaint mGradientPaint2) {
        return mGradientPaint1.getTransparency() == mGradientPaint2.getTransparency()
            && mGradientPaint1.getTransform().equals(mGradientPaint2.getTransform())
            && mGradientPaint1.getColorSpace().equals(mGradientPaint2.getColorSpace())
            && mGradientPaint1.getCycleMethod().equals(mGradientPaint2.getCycleMethod())
            && Arrays.deepEquals(mGradientPaint1.getColors(), mGradientPaint2.getColors())
            && Arrays.equals(mGradientPaint1.getFractions(), mGradientPaint2.getFractions());
    }

    /**
     * {@return a set of circle points and alternate indexes from the given centerpoint and radius}
     *
     * @param x      The centerpoint {@code x} of the circle.
     * @param y      The centerpoint {@code y} of the circle.
     * @param radius The radius of the circle.
     */
    public static PointsAndAlts createCircle(float x, float y, float radius) {
        return createCircle(new Pointf(x, y), radius);
    }

    /**
     * {@return a set of circle points and alternate indexes from the given centerpoint and radius}
     *
     * @param xy     The centerpoint {@code x} and {@code y} of the circle.
     * @param radius The radius of the circle.
     */
    public static PointsAndAlts createCircle(float xy, float radius) {
        return createCircle(new Pointf(xy), radius);
    }

    /**
     * {@return a set of circle points and alternate indexes from the given centerpoint and radius}
     *
     * @param center The centerpoint of the circle.
     * @param radius The radius of the circle.
     */
    public static PointsAndAlts createCircle(Pointf center, float radius) {
        float curveOffset = (float) ((4f / 3f) * (Math.sqrt(2) - 1) * radius);
        return new PointsAndAlts(
            new Pointf[] {
                Pointf.subtract(center, radius, 0f),

                Pointf.subtract(center, radius, curveOffset),
                Pointf.subtract(center, curveOffset, radius),
                Pointf.subtract(center, 0f, radius),

                Pointf.subtract(center, -curveOffset, radius),
                Pointf.add(center, radius, -curveOffset),
                Pointf.add(center, radius, 0f),

                Pointf.add(center, radius, curveOffset),
                Pointf.add(center, curveOffset, radius),
                Pointf.add(center, 0f, radius),

                Pointf.add(center, -curveOffset, radius),
                Pointf.subtract(center, radius, -curveOffset),
                Pointf.subtract(center, radius, 0f)
            },
            new Point[] {
                new Point(1, Polygon2D.BezierCurve),
                new Point(4, Polygon2D.BezierCurve),
                new Point(7, Polygon2D.BezierCurve),
                new Point(10, Polygon2D.BezierCurve)
            }
        );
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
        return new Pointf[] {
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
     * Creates a {@code Pointf} array of 4 points, based on the specified location {@code Pointf} and size {@code Pointf}.
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
     * Creates a {@code Pointf} array of 4 points, based on the specified {@code BufferedImage} and the location {@code Pointf}.
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
        return new Pointf[] {
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
        return createBoxFromImage(source, Pointf.origin());
    }

    /**
     * Creates a {@code Rectangle2D.Float} based on the specified {@code Pointf} array.
     * <p>
     * <b>NOTE:</b> the {@code pts} parameter must have a length of 4.
     *
     * @param pts The {@code Pointf} array used to create the rectangle.
     * @return The resultant {@code Rectangle2D.Float}.
     */
    public static Rectangle2D.Float createRect(Pointf[] pts) {
        if (pts.length != 4) {
            throw new IllegalArgumentException("The length of the parameter point array must be 4.");
        }

        return new Rectangle2D.Float(pts[0].x, pts[0].y, pts[1].x - pts[0].x, pts[3].y - pts[0].y);
    }

    /**
     * Creates a {@code Rectangle2D.Float} based on the specified {@code BufferedImage} and the location {@code Pointf}.
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
        Pointf result = Pointf.origin();
        for (Pointf p : points) {
            result.add(p);
        }
        return result.divide(points.length);
    }

    /**
     * Gets a {@code Pointf} array that represents the points of the {@code Path2D.Float} parameter.
     * <p>
     * This method will record all curves and other points in the given path, without indication as to where they may be. Use
     * {@link #pointsOfPathWithAlt(Path2D.Float)} to get an array of alternate indexes for the path which represent what actions were
     * taken.
     *
     * @param path The path to get the points of.
     * @return The resultant array of points.
     */
    public static Pointf[] pointsOfPath(Path2D.Float path) {
        List<Pointf> pointList = new ArrayList<>();
        float[] coords = new float[6];

        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO, PathIterator.SEG_LINETO -> pointList.add(new Pointf(coords[0], coords[1]));
                case PathIterator.SEG_CUBICTO -> {
                    pointList.add(new Pointf(coords[0], coords[1]));
                    pointList.add(new Pointf(coords[2], coords[3]));
                    pointList.add(new Pointf(coords[4], coords[5]));
                }
                case PathIterator.SEG_QUADTO -> {
                    pointList.add(new Pointf(coords[0], coords[1]));
                    pointList.add(new Pointf(coords[2], coords[3]));
                }
                case PathIterator.SEG_CLOSE -> {
                    return pointList.toArray(new Pointf[0]);
                }
            }
        }

        return pointList.toArray(new Pointf[0]);
    }

    /**
     * Gets a {@code Pointf} array that represents the points of the {@code Path2D.Float} parameter. as well as a {@code Point} array that
     * indicates the location of curves and other {@link Path2D} iteration guides.
     *
     * @param path The path to get the points of.
     * @return The resultant array of points.
     */
    public static PointsAndAlts pointsOfPathWithAlt(Path2D.Float path) {
        List<Pointf> pointList = new ArrayList<>();
        List<Point> alternateIndexes = new ArrayList<>();
        float[] coords = new float[6];
        int numSubPaths = 0;

        PathIterator pi = path.getPathIterator(null);
        while (!pi.isDone()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO -> {
                    pointList.add(new Pointf(coords[0], coords[1]));
                    numSubPaths++;
                    if (numSubPaths > 1) {
                        alternateIndexes.add(new Point(pointList.size() - 1, Polygon2D.MovePath));
                    }
                }
                case PathIterator.SEG_LINETO -> pointList.add(new Pointf(coords[0], coords[1]));
                case PathIterator.SEG_CUBICTO -> {
                    alternateIndexes.add(new Point(pointList.size(), Polygon2D.BezierCurve));
                    pointList.add(new Pointf(coords[0], coords[1]));
                    pointList.add(new Pointf(coords[2], coords[3]));
                    pointList.add(new Pointf(coords[4], coords[5]));
                }
                case PathIterator.SEG_QUADTO -> {
                    alternateIndexes.add(new Point(pointList.size(), Polygon2D.QuadCurve));
                    pointList.add(new Pointf(coords[0], coords[1]));
                    pointList.add(new Pointf(coords[2], coords[3]));
                }
                case PathIterator.SEG_CLOSE -> {
                    if (!pi.isDone()) {
                        Log.warn(DrawUtil.class, "tried to close path iterator before done");
                        break;
                    }
                    return new PointsAndAlts(pointList.toArray(new Pointf[0]), alternateIndexes.toArray(new Point[0]));
                }
            }
            pi.next();
        }

        return new PointsAndAlts(pointList.toArray(new Pointf[0]), alternateIndexes.toArray(new Point[0]));
    }

    /**
     * Gets the amount of points in the specified {@code Path2D}.
     *
     * @param path The path to check point amount of.
     * @return The amount of points in the path.
     */
    public static int lengthOfPath(Path2D path) {
        int count = 0;
        double[] coords = new double[6];

        int numSubPaths = 0;

        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO -> {
                }
                case PathIterator.SEG_CLOSE -> numSubPaths++;
                case PathIterator.SEG_LINETO -> count++;
                case PathIterator.SEG_QUADTO -> count += 2;
                case PathIterator.SEG_CUBICTO -> count += 3;
                default -> throw new IllegalArgumentException("unknown path segment type " + pi.currentSegment(coords));
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
        return new Color(
            (int) Maths.random(0, 255),
            (int) Maths.random(0, 255),
            (int) Maths.random(0, 255),
            255
        );
    }

    /**
     * Generates a random {@code Color}, including the alpha level of the color.
     *
     * @return The randomly generated {@code Color}.
     */
    public static Color randomColorWithAlpha() {
        return new Color(
            (int) Maths.random(0, 255),
            (int) Maths.random(0, 255),
            (int) Maths.random(0, 255),
            (int) Maths.random(0, 255)
        );
    }

    /**
     * Generates a random {@link Font} using the available fonts on the system, with a font size within the range of {@code 1-256},
     * inclusive.
     *
     * @return The randomly generated {@link Font}.
     */
    public static Font randomFont() {
        Font[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        String randomFontName = fontNames[Maths.randomInteger(0, fontNames.length - 1)].getFontName();

        int styleValue = Maths.randomInteger(0, 2);
        int randomFontStyle;
        if (styleValue == 0) {
            randomFontStyle = Font.PLAIN;
        } else if (styleValue == 1) {
            randomFontStyle = Font.BOLD;
        } else {
            randomFontStyle = Font.ITALIC;
        }

        int randomFontSize = Maths.randomInteger(1, 256);

        return new Font(randomFontName, randomFontStyle, randomFontSize);
    }

    /** {@return a randomly generated {@link BasicStroke outline stroke} with a size from {@code 0} to {@code 32}} */
    public static BasicStroke randomOutlineStroke() {
        int cap = Maths.randomInteger(0, 2);
        int randomCap;
        if (cap == 0) {
            randomCap = BasicStroke.CAP_BUTT;
        } else if (cap == 1) {
            randomCap = BasicStroke.CAP_ROUND;
        } else {
            randomCap = BasicStroke.CAP_SQUARE;
        }

        int join = Maths.randomInteger(0, 2);
        int randomJoin;
        if (join == 0) {
            randomJoin = BasicStroke.JOIN_MITER;
        } else if (join == 1) {
            randomJoin = BasicStroke.JOIN_ROUND;
        } else {
            randomJoin = BasicStroke.JOIN_BEVEL;
        }

        return new BasicStroke(
            Maths.random(0.0f, 32.0f),
            randomCap,
            randomJoin,
            randomJoin == BasicStroke.JOIN_MITER ? Maths.random(1.0f, 64.0f) : 0.0f
        );
    }

    /**
     * Creates a new {@link Color}, applying to it the linear interpolation of the two {@link Color}s specified.
     *
     * @param c  The starting value.
     * @param c1 The ending value.
     * @param t  The interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will be used to linear
     *           interpolate all 4 of the {@code Color}'s values (red, blue, green, alpha).
     * @return A new {@code Color}, linearly interpolated as specified.
     * @see Maths#lerp(float, float, float)
     */
    public static Color colorLerp(Color c, Color c1, float t) {
        return new Color(
            Maths.clamp((int) Maths.lerp(c.getRed(), c1.getRed(), t), 0, 255),
            Maths.clamp((int) Maths.lerp(c.getGreen(), c1.getGreen(), t), 0, 255),
            Maths.clamp((int) Maths.lerp(c.getBlue(), c1.getBlue(), t), 0, 255),
            Maths.clamp((int) Maths.lerp(c.getAlpha(), c1.getAlpha(), t), 0, 255)
        );
    }

    /**
     * Creates a new {@link Color}, applying to it the linear interpolation of the two {@link Color}s specified.
     *
     * @param c  The starting value.
     * @param c1 The ending value.
     * @param t1 The first interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will be used to linear
     *           interpolate the {@code Color}'s red value.
     * @param t2 The second interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will be used to linear
     *           interpolate the {@code Color}'s green value.
     * @param t3 The third interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will be used to linear
     *           interpolate the {@code Color}'s blue value.
     * @param t4 The fourth interpolation value to work with (preferably within a range of 0.0 to 1.0). This value will be used to linear
     *           interpolate the {@code Color}'s alpha value.
     * @return A new {@code Color}, linearly interpolated as specified.
     * @see Maths#lerp(float, float, float)
     */
    public static Color colorLerp(Color c, Color c1, float t1, float t2, float t3, float t4) {
        return new Color(
            Maths.clamp((int) Maths.lerp(c.getRed(), c1.getRed(), t1), 0, 255),
            Maths.clamp((int) Maths.lerp(c.getGreen(), c1.getGreen(), t2), 0, 255),
            Maths.clamp((int) Maths.lerp(c.getBlue(), c1.getBlue(), t3), 0, 255),
            Maths.clamp((int) Maths.lerp(c.getAlpha(), c1.getAlpha(), t4), 0, 255)
        );
    }

    /**
     * Creates a new {@link Color}, applying to it the linear interpolation of the two {@link Color}s specified.
     *
     * @param c  The starting value.
     * @param c1 The ending value.
     * @param v  The value representing the "result" of linear interpolation between the two {@code Color}s. This value is used to calculate
     *           inverse linear interpolation of the colors' {@code red}, {@code green}, {@code blue}, and {@code alpha} values.
     * @return An array of floats containing the resulting inverse linear interpolations of the colors' {@code red}, {@code green},
     * {@code blue}, and {@code alpha} values.
     * @see Maths#inverseLerp(float, float, float)
     */
    public static float[] inverseColorLerp(Color c, Color c1, float v) {
        return new float[] {
            Maths.inverseLerp(c.getRed(), c1.getRed(), v),
            Maths.inverseLerp(c.getGreen(), c1.getGreen(), v),
            Maths.inverseLerp(c.getBlue(), c1.getBlue(), v),
            Maths.inverseLerp(c.getAlpha(), c1.getAlpha(), v),
        };
    }

    /**
     * Creates a new {@link Color}, applying to it the linear interpolation of the two {@link Color}s specified.
     *
     * @param c  The starting value.
     * @param c1 The ending value.
     * @param v1 The first value representing the "result" of linear interpolation between the two {@code Color}s. This value is used to
     *           calculate inverse linear interpolation of the colors' {@code red} value.
     * @param v2 The second value representing the "result" of linear interpolation between the two {@code Color}s. This value is used to
     *           calculate inverse linear interpolation of the colors' {@code green} value.
     * @param v3 The third value representing the "result" of linear interpolation between the two {@code Color}s. This value is used to
     *           calculate inverse linear interpolation of the colors' {@code blue} value.
     * @param v4 The fourth value representing the "result" of linear interpolation between the two {@code Color}s. This value is used to
     *           calculate inverse linear interpolation of the colors' {@code alpha} value.
     * @return An array of floats containing the resulting inverse linear interpolations of the colors' {@code red}, {@code green},
     * {@code blue}, and {@code alpha} values.
     * @see Maths#lerp(float, float, float)
     */
    public static float[] inverseColorLerp(Color c, Color c1, float v1, float v2, float v3, float v4) {
        return new float[] {
            Maths.inverseLerp(c.getRed(), c1.getRed(), v1),
            Maths.inverseLerp(c.getGreen(), c1.getGreen(), v2),
            Maths.inverseLerp(c.getBlue(), c1.getBlue(), v3),
            Maths.inverseLerp(c.getAlpha(), c1.getAlpha(), v4),
        };
    }
}
