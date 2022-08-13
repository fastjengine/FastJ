package tech.fastj.graphics.game;

import tech.fastj.graphics.Drawable;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.systems.control.GameHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * {@link GameObject Game object} subclass for drawing text.
 * <h2>Creating Text</h2>
 * This type of game object simply renders text, of which you can {@link #setText(String) set}. You can also change the
 * {@link #setFont(Font) font} and {@link #setFill(Paint) fill color} of the text.
 * <p>
 * You can create one using {@link Text2D#create(String)}, which uses a {@link Text2DBuilder builder} to streamline the process.
 * Alternatively, {@link Text2D#fromText(String)} lets you create an instance using only text -- all other values are their defaults.
 * <p>
 * Below is an example of creating text, rendered with anti-aliasing enabled.
 * {@snippet lang = "java":
 * import tech.fastj.engine.FastJEngine;
 * import tech.fastj.graphics.display.FastJCanvas;
 * import tech.fastj.graphics.display.RenderSettings;
 * import tech.fastj.graphics.game.GameObject;
 * import tech.fastj.graphics.game.Text2D;
 * import tech.fastj.graphics.util.DrawUtil;
 * import tech.fastj.systems.control.SimpleManager;
 *
 * public class Game extends SimpleManager {
 *
 *     @Override
 *     public void init(FastJCanvas canvas) {
 *         // enable anti-aliasing
 *         canvas.modifyRenderSettings(RenderSettings.Antialiasing.Enable);
 *
 *         Text2D helloText = Text2D.fromText("Hello, FastJ!"); // @highlight
 *
 *         // add game objects to be rendered
 *         drawableManager().addGameObject(helloText);
 *     }
 *
 *     public static void main(String[] args) {
 *         FastJEngine.init("Simple Shapes in FastJ", new Game());
 *     }
 * }}
 * <p>
 * Useful Information:
 * <ul>
 *     <li>{@link #setText(String) Changing text}</li>
 *     <li>{@link #setFont(Font) Changing text font}</li>
 *     <li>{@link #setFill(Paint) Changing fill color}</li>
 * </ul>
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Text2D extends GameObject {

    /** {@link Paint} representing the default color value of {@code (0, 0, 0)}. */
    public static final Paint DefaultFill = Color.black;
    /** {@link Font} representing the default font of {@code Tahoma 16px}. */
    public static final Font DefaultFont = new Font("Tahoma", Font.PLAIN, 16);
    /** {@code String} representing default text -- an empty string. */
    public static final String DefaultText = "";

    private static final Pointf OriginInstance = Pointf.origin();
    private static final BufferedImage GraphicsHelper = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);

    private String text;
    private Paint fillPaint;
    private Font font;

    private boolean hasMetrics;

    /**
     * {@code Text2D} constructor that takes in a string of text.
     * <p>
     * This constructor defaults the fill paint to {@link #DefaultFill}, the font to {@link #DefaultFont}, and sets the {@code shouldRender}
     * boolean to {@link Drawable#DefaultShouldRender}.
     *
     * @param text {@code String} that defines the text for the {@code Text2D}.
     */
    protected Text2D(String text) {
        this.text = Objects.requireNonNullElse(text, DefaultText);
        setFont(DefaultFont);
        setFill(DefaultFill);
    }

    /**
     * Gets a {@link Text2DBuilder} instance while setting the eventual {@link Text2D}'s {@code text} field.
     *
     * @param text {@code String} that defines the text for the {@code Text2D}.
     * @return A {@code Text2DBuilder} instance for creating a {@code Text2D}.
     */
    public static Text2DBuilder create(String text) {
        return new Text2DBuilder(text, Drawable.DefaultShouldRender);
    }

    /**
     * Gets a {@link Text2DBuilder} instance while setting the eventual {@link Text2D}'s {@code text} and {@code shouldRender} fields.
     *
     * @param text         {@code String} that defines the text for the {@code Text2D}.
     * @param shouldRender {@code boolean} that defines whether the {@code Text2D} would be rendered to the screen.
     * @return A {@code Text2DBuilder} instance for creating a {@code Text2D}.
     */
    public static Text2DBuilder create(String text, boolean shouldRender) {
        return new Text2DBuilder(text, shouldRender);
    }

    /**
     * Creates a {@code Text2D} from the specified text.
     *
     * @param text {@code String} that defines the text for the {@code Text2D}.
     * @return The resulting {@code Text2D}.
     */
    public static Text2D fromText(String text) {
        return new Text2DBuilder(text, DefaultShouldRender).build();
    }

    /** {@return the {@link Text2D}'s displayed text} */
    public String getText() {
        return text;
    }

    /** {@return the {@link Text2D}'s {@link Paint fill color}} */
    public Paint getFill() {
        return fillPaint;
    }

    /** {@return the {@link Text2D}'s {@link Font text font}} */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the {@link Text2D}'s text.
     *
     * @param newText The new text value.
     * @return The {@link Text2D} instance, for method chaining.
     */
    public Text2D setText(String newText) {
        text = Objects.requireNonNullElse(newText, DefaultText);
        Graphics2D graphics = GraphicsHelper.createGraphics();
        setMetrics(graphics);
        graphics.dispose();

        return this;
    }

    /**
     * Sets the {@link Text2D}'s {@link Paint fill paint}.
     *
     * @param newPaint The new {@link Paint} value.
     * @return The {@link Text2D} instance, for method chaining.
     */
    public Text2D setFill(Paint newPaint) {
        fillPaint = newPaint;
        return this;
    }

    /**
     * Sets the {@code Text2D}'s {@code Font}.
     *
     * @param newFont The new {@code Font} value.
     * @return The {@code Text2D} instance, for method chaining.
     */
    public Text2D setFont(Font newFont) {
        font = newFont;
        Graphics2D graphics = GraphicsHelper.createGraphics();
        setMetrics(graphics);
        graphics.dispose();

        return this;
    }

    @Override
    public void render(Graphics2D g) {
        if (!hasMetrics) {
            setMetrics(g);
        }

        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        Font oldFont = g.getFont();
        Paint oldPaint = g.getPaint();

        g.transform(getTransformation());
        g.setFont(font);
        g.setPaint(fillPaint);

        g.drawString(text, OriginInstance.x, font.getSize2D());

        g.setTransform(oldTransform);
        g.setFont(oldFont);
        g.setPaint(oldPaint);
    }

    @Override
    public void destroy(GameHandler origin) {
        text = DefaultText;
        fillPaint = DefaultFill;
        font = DefaultFont;
        hasMetrics = false;

        super.destroyTheRest(origin);
    }

    /**
     * Sets up the necessary boundaries for creating the {@code Text2D}'s metrics.
     * <p>
     * This also sets the resulting metrics as the {@code Text2D}'s collision path.
     *
     * @param g {@code Graphics2D} object that the {@code Text2D} is rendered on.
     */
    private void setMetrics(Graphics2D g) {
        hasMetrics = false;

        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        final Rectangle2D.Float bounds = new Rectangle2D.Float(
            Transform2D.DefaultTranslation.x,
            Transform2D.DefaultTranslation.y,
            textWidth,
            textHeight
        );

        setCollisionPath(createMetricsPath(bounds));

        g.dispose();
        hasMetrics = true;
    }

    /**
     * Gets a {@code Path2D.Float} that is based on the parameter {@code Rectangle2D.Float}.
     *
     * @param rect The rectangle which the result {@code Path2D.Float} is based on.
     * @return The newly created {@code Path2D.Float}.
     */
    private Path2D.Float createMetricsPath(Rectangle2D.Float rect) {
        Path2D.Float result = new Path2D.Float();

        result.moveTo(rect.x, rect.y);
        result.lineTo(rect.x + rect.width, rect.y);
        result.lineTo(rect.x + rect.width, rect.y + rect.height);
        result.lineTo(rect.x, rect.y + rect.height);
        result.closePath();

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Text2D text2D = (Text2D) o;
        return Objects.equals(text, text2D.text)
            && Objects.equals(fillPaint, text2D.fillPaint)
            && Objects.equals(font, text2D.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, fillPaint, font, hasMetrics);
    }
}
