package tech.fastj.graphics.dialog;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Convenience methods, combined with {@link DialogConfig}, to create simple Swing-style dialogs.
 * <p>
 * When working with Swing components directly, it is highly recommended to use {@link SwingUtilities#invokeLater(Runnable)}.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public class DialogUtil {

    private DialogUtil() {
        throw new IllegalStateException();
    }

    /**
     * Creates a {@link JOptionPane#showMessageDialog(Component, Object, String, int, Icon) dialog with a message for the user}.
     * <p>
     * This dialog uses the {@link DialogMessageTypes#Information information} message type.
     *
     * @param dialogConfig The dialog's configuration style.
     */
    public static void showMessageDialog(DialogConfig dialogConfig) {
        JOptionPane.showMessageDialog(
            dialogConfig.dialogParent(),
            dialogConfig.prompt(),
            dialogConfig.title(),
            DialogMessageTypes.Information.jOptionMessageType,
            dialogConfig.icon()
        );
    }

    /**
     * Creates a
     * {@link JOptionPane#showInputDialog(Component, Object, String, int, Icon, Object[], Object) dialog with a simple text box for the user
     * to input to}.
     * <p>
     * This dialog uses the {@link DialogMessageTypes#Question question} message type.
     *
     * @param dialogConfig The dialog's configuration style.
     * @return The string of input from the user.
     */
    public static String showInputDialog(DialogConfig dialogConfig) {
        return (String) JOptionPane.showInputDialog(
            dialogConfig.dialogParent(),
            dialogConfig.prompt(),
            dialogConfig.title(),
            DialogMessageTypes.Question.jOptionMessageType,
            dialogConfig.icon(),
            null,
            null
        );
    }

    /**
     * Creates a
     * {@link JOptionPane#showConfirmDialog(Component, Object, String, int, int, Icon) dialog with a set of buttons to confirm or deny a
     * specified action}.
     * <p>
     * This dialog uses the {@link DialogMessageTypes#Question question} message type.
     *
     * @param dialogConfig  The dialog's configuration style.
     * @param dialogOptions The types of options to provide. In most multi-option cases, {@link DialogOptions#YesNoCancel} should suffice.
     * @return Whether the user confirmed ({@code true}) or denied ({@code false}).
     */
    public static boolean showConfirmationDialog(DialogConfig dialogConfig, DialogOptions dialogOptions) {
        int result = JOptionPane.showConfirmDialog(
            dialogConfig.dialogParent(),
            dialogConfig.prompt(),
            dialogConfig.title(),
            dialogOptions.jOption,
            DialogMessageTypes.Question.jOptionMessageType,
            dialogConfig.icon()
        );

        switch (result) {
            case JOptionPane.YES_OPTION: {
                return true;
            }
            case JOptionPane.NO_OPTION:
            default: {
                return false;
            }
        }
    }

    /**
     * Creates a
     * {@link JOptionPane#showOptionDialog(Component, Object, String, int, int, Icon, Object[], Object) dialog with a set of buttons to
     * choose from, as options}.
     *
     * @param dialogConfig      The dialog's configuration style.
     * @param dialogMessageType The type of the dialog message.
     * @param options           The options for the user to pick from.
     * @param initialOption     The initial highlighted option.
     * @return The index of the option the user chose. If the user exited the dialog or otherwise did not pick an option, {@code -1} is the
     * returned value.
     */
    public static int showOptionDialog(DialogConfig dialogConfig, DialogMessageTypes dialogMessageType, Object[] options, Object initialOption) {
        return JOptionPane.showOptionDialog(
            dialogConfig.dialogParent(),
            dialogConfig.prompt(),
            dialogConfig.title(),
            DialogOptions.Default.jOption,
            dialogMessageType.jOptionMessageType,
            dialogConfig.icon(),
            options,
            initialOption
        );
    }
}
