package tech.fastj.graphics.ui.elements;

import tech.fastj.graphics.ui.UIElement;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.input.mouse.Mouse;
import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseActionListener;
import tech.fastj.input.mouse.MouseButtons;
import tech.fastj.input.mouse.events.MouseButtonEvent;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.systems.control.GameHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * A {@link UIElement} that can be assigned an action on left click.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Button extends UIElement<MouseButtonEvent> implements MouseActionListener {

    /** The default size of a {@link Button}: (100f, 25f). */
    public static final Pointf DefaultSize = new Pointf(100f, 25f);
    /** The default text value of a {@link Button}: an empty string. */
    public static final String DefaultText = "";
    /** {@link Paint} representing the default color value of {@code (192, 192, 192)}. */
    public static final Paint DefaultFill = Color.lightGray;
    /** {@link Font} representing the default font of {@code Tahoma 16px}. */
    public static final Font DefaultFont = new Font("Tahoma", Font.PLAIN, 16);

    private static final BufferedImage GraphicsHelper = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);

    private Paint paint;

    private Font font;
    private String text;
    private Rectangle2D.Float textBounds;
    private boolean hasMetrics;

    /**
     * Constructs a button with a default location and size.
     *
     * @param origin The game handler to add the button as a gui object to.
     */
    public Button(GameHandler origin) {
        this(origin, Transform2D.DefaultTranslation, DefaultSize);
    }

    /**
     * Constructs a button with the specified location and initial size.
     *
     * @param origin      The game handler to add the button as a gui object to.
     * @param location    The location to create the button at.
     * @param initialSize The initial size of the button, though the button will get larger if the text outgrows it.
     */
    public Button(GameHandler origin, Pointf location, Pointf initialSize) {
        super(origin);
        if (initialSize.x < Maths.FloatPrecision || initialSize.y < Maths.FloatPrecision) {
            throw new IllegalArgumentException(
                "The size " + initialSize + " is too small." +
                    System.lineSeparator() +
                    "The minimum size in both x and y directions is " + Maths.FloatPrecision + "."
            );
        }

        super.setOnActionCondition(event -> Mouse.interactsWith(Button.this, MouseAction.Press) && Mouse.isMouseButtonPressed(MouseButtons.Left));

        Pointf[] buttonCoords = DrawUtil.createBox(Pointf.origin(), initialSize);
        super.setCollisionPath(DrawUtil.createPath(buttonCoords));

        this.paint = DefaultFill;
        this.font = DefaultFont;
        this.text = DefaultText;

        translate(location);
        Graphics2D graphics = GraphicsHelper.createGraphics();
        setMetrics(graphics);
        graphics.dispose();

        origin.inputManager().addMouseActionListener(this);
    }

    /**
     * Gets the {@link Paint} object for the button.
     *
     * @return The Button's {@code Paint}.
     */
    public Paint getFill() {
        return paint;
    }

    /**
     * Sets the {@link Paint} object for the button.
     *
     * @param paint The new paint for the button.
     * @return The {@link Button}, for method chaining.
     */
    public Button setFill(Paint paint) {
        this.paint = paint;
        return this;
    }

    /**
     * Gets the text for the button.
     *
     * @return The Button's text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text for the button.
     *
     * @param text The new text for the button.
     * @return The {@link Button}, for method chaining.
     */
    public Button setText(String text) {
        this.text = text;
        Graphics2D graphics = GraphicsHelper.createGraphics();
        setMetrics(graphics);
        graphics.dispose();

        return this;
    }

    /**
     * Gets the {@link Font} object for the button.
     *
     * @return The Button's {@code Font}.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the {@link Font} for the button.
     *
     * @param font The new {@code Font} object for the button.
     * @return The {@link Button}, for method chaining.
     */
    public Button setFont(Font font) {
        this.font = font;
        Graphics2D graphics = GraphicsHelper.createGraphics();
        setMetrics(graphics);
        graphics.dispose();

        return this;
    }

    /**
     * Sets the Button's {@code onAction} event to the specified action.
     *
     * @param action The action to set.
     * @return The {@code Button}, for method chaining.
     */
    @Override
    public Button setOnAction(Consumer<MouseButtonEvent> action) {
        onActionEvents.clear();
        onActionEvents.add(action);
        return this;
    }

    /**
     * Adds the specified action to the Button's {@code onAction} events.
     *
     * @param action The action to add.
     * @return The {@code Button}, for method chaining.
     */
    @Override
    public Button addOnAction(Consumer<MouseButtonEvent> action) {
        onActionEvents.add(action);
        return this;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        Paint oldPaint = g.getPaint();
        Font oldFont = g.getFont();
        Rectangle2D.Float renderCopy = (Rectangle2D.Float) collisionPath.getBounds2D();

        g.transform(getTransformation());

        g.setPaint(paint);
        g.fill(renderCopy);
        g.setPaint(Color.black);
        g.draw(renderCopy);

        if (!hasMetrics) {
            setMetrics(g);
        }

        g.setFont(font);
        g.drawString(text, textBounds.x, textBounds.y);

        g.setPaint(oldPaint);
        g.setFont(oldFont);
        g.setTransform(oldTransform);
    }

    @Override
    public void destroy(GameHandler origin) {
        super.destroyTheRest(origin);
        paint = null;
        origin.inputManager().removeMouseActionListener(this);
    }

    /**
     * Fires the button's {@code onAction} event(s), if its condition is met.
     *
     * @param mouseButtonEvent The mouse event causing the {@code onAction} event(s) to be fired.
     */
    @Override
    public void onMousePressed(MouseButtonEvent mouseButtonEvent) {
        if (onActionCondition.condition(mouseButtonEvent)) {
            onActionEvents.forEach(action -> action.accept(mouseButtonEvent));
            mouseButtonEvent.consume();
        }
    }

    /**
     * Sets up the necessary boundaries for creating text metrics, and aligns the text with the button.
     * <p>
     * If the text metrics show that the text does not fit in the button, the button will be resized to fit the text.
     *
     * @param g {@code Graphics2D} object that the {@code Text2D} is rendered on.
     */
    private void setMetrics(Graphics2D g) {
        hasMetrics = false;

        FontMetrics fm = g.getFontMetrics(font);

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        Rectangle2D.Float renderPathBounds = (Rectangle2D.Float) collisionPath.getBounds2D();

        textBounds = new Rectangle2D.Float(
            (renderPathBounds.width - textWidth) / 2f,
            textHeight,
            textWidth,
            textHeight
        );

        Rectangle2D.Float newPathBounds = (Rectangle2D.Float) super.collisionPath.getBounds2D();
        if (renderPathBounds.width < textBounds.width) {
            float diff = (textBounds.width - renderPathBounds.width) / 2f;
            newPathBounds.width = textBounds.width + diff;
        }

        if (renderPathBounds.height < textBounds.height) {
            float diff = (textBounds.height - renderPathBounds.height) / 2f;
            newPathBounds.height = textBounds.height + diff;
        }

        super.setCollisionPath(DrawUtil.createPath(DrawUtil.createBox(newPathBounds)));

        g.dispose();
        hasMetrics = true;
    }
}
