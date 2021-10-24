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
    private final String promptText;
    private final Icon icon;

    DialogConfig(Component parentComponent, String title, String promptText, Icon icon) {
        this.parentComponent = parentComponent;
        this.title = title;
        this.promptText = promptText;
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

    public String promptText() {
        return promptText;
    }

    public Icon icon() {
        return icon;
    }
}
