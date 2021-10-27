package tech.fastj.graphics.ui.elements;

import tech.fastj.engine.FastJEngine;

import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;

import tech.fastj.graphics.display.Camera;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.input.mouse.Mouse;
import tech.fastj.input.mouse.MouseAction;
import tech.fastj.input.mouse.MouseButtons;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * A {@link UIElement} that can be assigned an action on left click.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public class Button extends UIElement {

  /** The default size of a {@link Button}: (100f, 25f). */
  public static final Pointf DefaultSize = new Pointf(100f, 25f);
  /** The default text value of a {@link Button}: an empty string. */
  public static final String DefaultText = "";
  /** {@link Paint} representing the default color value of {@code (0, 255, 255)}. */
  public static final Paint DefaultFill = Color.cyan;
  /** {@link Font} representing the default font of {@code Tahoma 16px}. */
  public static final Font DefaultFont = new Font("Tahoma", Font.PLAIN, 16);

  private Paint paint;
  private Path2D.Float renderPath;

  private Font font;
  private String text;
  private Rectangle2D.Float textBounds;
  private boolean hasMetrics;

  /**
   * Constructs a button with a default location and size.
   *
   * @param origin The scene to add the button as a gui object to.
   */
  public Button(Scene origin) {
    this(origin, Transform2D.DefaultTranslation, DefaultSize);
  }

  /**
   * Constructs a button with a default location and size.
   *
   * @param origin The simple manager to add the button as a gui object to.
   */
  public Button(SimpleManager origin) {
    this(origin, Transform2D.DefaultTranslation, DefaultSize);
  }

  /**
   * Constructs a button with the specified location and initial size.
   *
   * @param origin The scene to add the button as a gui object to.
   * @param location The location to create the button at.
   * @param initialSize The initial size of the button, though the button will get larger if the
   *     text outgrows it.
   */
  public Button(Scene origin, Pointf location, Pointf initialSize) {
    super(origin);
    super.setOnActionCondition(
        event ->
            Mouse.interactsWith(Button.this, MouseAction.Press)
                && Mouse.isMouseButtonPressed(MouseButtons.Left));

    Pointf[] buttonCoords = DrawUtil.createBox(location, initialSize);

    renderPath = DrawUtil.createPath(buttonCoords);
    super.setCollisionPath(renderPath);

    this.paint = DefaultFill;
    this.font = DefaultFont;
    this.text = DefaultText;

    setMetrics(FastJEngine.getDisplay().getGraphics());
  }

  /**
   * Constructs a button with the specified location and initial size.
   *
   * @param origin The simple manager to add the button as a gui object to.
   * @param location The location to create the button at.
   * @param initialSize The initial size of the button, though the button will get larger if the
   *     text outgrows it.
   */
  public Button(SimpleManager origin, Pointf location, Pointf initialSize) {
    super(origin);
    super.setOnActionCondition(
        event ->
            Mouse.interactsWith(Button.this, MouseAction.Press)
                && Mouse.isMouseButtonPressed(MouseButtons.Left));

    Pointf[] buttonCoords = DrawUtil.createBox(location, initialSize);

    renderPath = DrawUtil.createPath(buttonCoords);
    super.setCollisionPath(renderPath);

    this.paint = DefaultFill;
    this.font = DefaultFont;
    this.text = DefaultText;

    setMetrics(FastJEngine.getDisplay().getGraphics());
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
    setMetrics(FastJEngine.getDisplay().getGraphics());
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
    setMetrics(FastJEngine.getDisplay().getGraphics());
    return this;
  }

  @Override
  public void renderAsGUIObject(Graphics2D g, Camera camera) {
    if (!shouldRender()) {
      return;
    }

    AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
    Paint oldPaint = g.getPaint();
    Font oldFont = g.getFont();

    g.transform(getTransformation());

    try {
      g.transform(camera.getTransformation().createInverse());
    } catch (NoninvertibleTransformException exception) {
      throw new IllegalStateException(
          "Couldn't create an inverse transform of " + camera.getTransformation(), exception);
    }

    Rectangle2D.Float renderCopy = (Rectangle2D.Float) renderPath.getBounds2D();

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
  public void destroy(Scene origin) {
    paint = null;
    renderPath = null;
    super.destroyTheRest(origin);
  }

  @Override
  public void destroy(SimpleManager origin) {
    paint = null;
    renderPath = null;
    super.destroyTheRest(origin);
  }

  /**
   * Sets up the necessary boundaries for creating text metrics, and aligns the text with the
   * button.
   *
   * <p>If the text metrics show that the text does not fit in the button, the button will be
   * resized to fit the text.
   *
   * @param g {@code Graphics2D} object that the {@code Text2D} is rendered on.
   */
  private void setMetrics(Graphics2D g) {
    hasMetrics = false;

    FontMetrics fm = g.getFontMetrics(font);

    int textWidth = fm.stringWidth(text);
    int textHeight = fm.getHeight();
    Rectangle2D.Float renderPathBounds = (Rectangle2D.Float) renderPath.getBounds2D();

    textBounds =
        new Rectangle2D.Float(
            getTranslation().x + (renderPathBounds.width - textWidth) / 2f,
            getTranslation().y + textHeight,
            textWidth,
            textHeight);

    if (renderPathBounds.width < textBounds.width) {
      float diff = (textBounds.width - renderPathBounds.width) / 2f;
      renderPathBounds.width = textBounds.width;
      textBounds.x += diff;
    }

    if (renderPathBounds.height < textBounds.height) {
      renderPathBounds.height = textBounds.height;
    }

    g.dispose();
    hasMetrics = true;
  }
}
