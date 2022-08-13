package tech.fastj.graphics.util;

import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;

/**
 * Record storing both points and alternate indexes for creating {@link Polygon2D polygon instances}.
 * <p>
 * See {@link Polygon2D} for more information on points and alternate indexes.
 *
 * @param points     The {@link Pointf} array defining vertexes of the shape.
 * @param altIndexes The {@link Point} array defining special values for the vertex array ({@link Polygon2D#QuadCurve Quadratic} and
 *                   {@link Polygon2D#BezierCurve Bezier} curves, as well as {@link Polygon2D#MovePath moving to a new shape}).
 * @author Andrew Dey
 * @since 1.7.0
 */
public record PointsAndAlts(Pointf[] points, Point[] altIndexes) {}
