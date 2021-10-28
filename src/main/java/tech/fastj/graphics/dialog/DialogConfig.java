package tech.fastj.graphics.dialog;

import javax.swing.Icon;
import java.awt.Component;

public class DialogConfig {

    public static final Component DefaultParentComponent = null;
    public static final String DefaultTitle = "Dialog";
    public static final String DefaultPromptText = "";
    public static final Icon DefaultIcon = null;

    private final Component parentComponent;
    private final String title;
    private final Object prompt;
    private final Icon icon;

    DialogConfig(Component parentComponent, String title, Object prompt, Icon icon) {
        this.parentComponent = parentComponent;
        this.title = title;
        this.prompt = prompt;
        this.icon = icon;
    }

    public static DialogConfigBuilder create() {
        return new DialogConfigBuilder();
    }

    public Component dialogParent() {
        return parentComponent;
    }

    public String title() {
        return title;
    }

    public Object prompt() {
        return prompt;
    }

    public Icon icon() {
        return icon;
    }
}
