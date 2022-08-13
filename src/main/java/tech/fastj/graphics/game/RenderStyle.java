package tech.fastj.graphics.game;

/**
 * Style for rendering a {@link Polygon2D}.
 * <p>
 * The render style controls what of a {@link Polygon2D shape} is rendered -- {@link RenderStyle#Outline outline},
 * {@link RenderStyle#Fill fill}, or {@link RenderStyle#FillAndOutline both}. It is separate from
 * {@link Polygon2D#setShouldRender(boolean) enabling/disabling overall rendering} of a {@link Polygon2D}.
 */
public enum RenderStyle {
    /** Tells the {@link Polygon2D} to only render the fill of the shape. */
    Fill,
    /** Tells the {@link Polygon2D} to only render the outline of the shape. */
    Outline,
    /** Tells the {@link Polygon2D} to render both the fill and outline of the shape. */
    FillAndOutline
}
