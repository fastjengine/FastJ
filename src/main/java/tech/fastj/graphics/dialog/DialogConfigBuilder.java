package tech.fastj.graphics.dialog;

import java.awt.Component;
import java.util.Objects;

import javax.swing.Icon;

/**
 * A builder class for creating {@link DialogConfig} objects.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public class DialogConfigBuilder {

    private Component parentComponent = DialogConfig.DefaultParentComponent;
    private String title = DialogConfig.DefaultTitle;
    private Object prompt = DialogConfig.DefaultPromptText;
    private Icon icon = DialogConfig.DefaultIcon;

    DialogConfigBuilder() {
    }

    /**
     * Sets the builder's dialog parent.
     *
     * @param parentComponent The parent {@code Component} to be used in the resulting {@code DialogConfig}.
     * @return The {@code DialogConfigBuilder}, for method chaining.
     */
    public DialogConfigBuilder withParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
        return this;
    }

    /**
     * Sets the builder's dialog title.
     *
     * @param title The {@code String} title to be used in the resulting {@code DialogConfig}.
     * @return The {@code DialogConfigBuilder}, for method chaining.
     */
    public DialogConfigBuilder withTitle(String title) {
        this.title = Objects.requireNonNull(title, "The title should not be null.");
        return this;
    }

    /**
     * Sets the builder's dialog prompt.
     *
     * @param prompt The prompt to be used in the resulting {@code DialogConfig}.
     * @return The {@code DialogConfigBuilder}, for method chaining.
     */
    public DialogConfigBuilder withPrompt(Object prompt) {
        this.prompt = Objects.requireNonNull(prompt, "The prompt should not be null.");
        return this;
    }

    /**
     * Sets the builder's dialog icon.
     *
     * @param icon The {@link Icon} to be used in the resulting {@code DialogConfig}.
     * @return The {@code DialogConfigBuilder}, for method chaining.
     */
    public DialogConfigBuilder withIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Creates a new {@link DialogConfig} object, using the data provided by earlier method calls.
     *
     * @return The resulting {@link DialogConfig}.
     */
    public DialogConfig build() {
        return new DialogConfig(parentComponent, title, prompt, icon);
    }
}
