package tech.fastj.graphics.dialog;

import javax.swing.Icon;
import java.awt.Component;
import java.util.Objects;

public class DialogConfigBuilder {

    private Component parentComponent = DialogConfig.DefaultParentComponent;
    private String title = DialogConfig.DefaultTitle;
    private String promptText = DialogConfig.DefaultPromptText;
    private Icon icon = DialogConfig.DefaultIcon;

    DialogConfigBuilder() {
    }

    public DialogConfigBuilder withParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
        return this;
    }

    public DialogConfigBuilder withTitle(String title) {
        this.title = Objects.requireNonNull(title, "The title should not be null.");
        return this;
    }

    public DialogConfigBuilder withPromptText(String promptText) {
        this.promptText = Objects.requireNonNull(promptText, "The prompt text should not be null.");
        return this;
    }

    public DialogConfigBuilder withIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    public DialogConfig build() {
        return new DialogConfig(parentComponent, title, promptText, icon);
    }
}
