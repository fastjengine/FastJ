package tech.fastj.graphics.dialog;

import javax.swing.JOptionPane;

/**
 * A set of message types regarding {@link DialogConfig dialog configuration}, wrapping over {@link JOptionPane}.
 * <p>
 * These types are also what control the default icon specified for a given dialog.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum DialogMessageTypes {

    /**
     * Message type for informational dialogs.
     *
     * @see JOptionPane#INFORMATION_MESSAGE
     */
    Information(JOptionPane.INFORMATION_MESSAGE),

    /**
     * Message type for warning dialogs.
     *
     * @see JOptionPane#WARNING_MESSAGE
     */
    Warning(JOptionPane.WARNING_MESSAGE),

    /**
     * Message type for error dialogs.
     *
     * @see JOptionPane#ERROR_MESSAGE
     */
    Error(JOptionPane.ERROR_MESSAGE),

    /**
     * Message type for question dialogs.
     *
     * @see JOptionPane#QUESTION_MESSAGE
     */
    Question(JOptionPane.QUESTION_MESSAGE),

    /**
     * Message type for plain dialogs. No default icon is provided for this option type.
     *
     * @see JOptionPane#PLAIN_MESSAGE
     */
    Plain(JOptionPane.PLAIN_MESSAGE);

    /** The corresponding {@link JOptionPane} message integer. */
    public final int jOptionMessageType;

    DialogMessageTypes(int jOptionMessageType) {
        this.jOptionMessageType = jOptionMessageType;
    }
}
