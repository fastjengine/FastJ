package tech.fastj.graphics.dialog;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JPanel;

/**
 * Configuration class for creating {@link DialogUtil dialogs}.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
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

    /**
     * Grabs an instance of a {@link DialogConfigBuilder} to use for creating a dialog.
     *
     * @return The {@link DialogConfigBuilder} instance.
     */
    public static DialogConfigBuilder create() {
        return new DialogConfigBuilder();
    }

    /**
     * The Swing-based parent of the dialog to create.
     *
     * @return The dialog parent.
     */
    public Component dialogParent() {
        return parentComponent;
    }

    /**
     * The {@code String} representing the title of the dialog.
     *
     * @return The title {@code String}.
     */
    public String title() {
        return title;
    }

    /**
     * The prompt for the dialog. This can be almost anything -- a simple {@code int}, a {@code String}, even a {@link
     * JPanel} containing a bunch of {@code Component}s for custom dialog content.
     *
     * @return The prompt {@code Object}.
     */
    public Object prompt() {
        return prompt;
    }

    /**
     * The {@link Icon} used for the dialog. This is often provided by the dialog's {@link DialogMessageTypes message
     * type}.
     *
     * @return The {@link Icon} instance.
     */
    public Icon icon() {
        return icon;
    }
}
