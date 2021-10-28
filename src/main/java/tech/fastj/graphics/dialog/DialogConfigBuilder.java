package tech.fastj.graphics.dialog;

import java.awt.Component;
import java.util.Objects;

import javax.swing.Icon;

public class DialogConfigBuilder {

    private Component parentComponent = DialogConfig.DefaultParentComponent;
    private String title = DialogConfig.DefaultTitle;
    private Object prompt = DialogConfig.DefaultPromptText;
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

    public DialogConfigBuilder withPrompt(Object prompt) {
        this.prompt = Objects.requireNonNull(prompt, "The prompt should not be null.");
        return this;
    }

    public DialogConfigBuilder withIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    public DialogConfig build() {
        return new DialogConfig(parentComponent, title, prompt, icon);
    }
}
