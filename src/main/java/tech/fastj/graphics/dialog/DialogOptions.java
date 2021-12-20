package tech.fastj.graphics.dialog;

import javax.swing.JOptionPane;

/**
 * A set of options regarding {@link DialogUtil dialog creation}, wrapping over {@link JOptionPane}.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public enum DialogOptions {

    /**
     * The default option -- used primarily for {@link DialogUtil#showOptionDialog(DialogConfig, DialogMessageTypes,
     * Object[], Object) option dialogs}, as they tend to provide their own options instead of needing specific ones.
     *
     * @see JOptionPane#DEFAULT_OPTION
     */
    Default(JOptionPane.DEFAULT_OPTION),

    /**
     * Options only specifying {@code "ok"}.
     *
     * @see JOptionPane#OK_OPTION
     */
    Ok(JOptionPane.OK_OPTION),

    /**
     * Options to specify {@code "yes"} and {@code "no"}.
     *
     * @see JOptionPane#YES_NO_OPTION
     */
    YesNo(JOptionPane.YES_NO_OPTION),

    /**
     * Options to specify {@code "yes"}, {@code "no"}, and {@code "cancel"}.
     *
     * @see JOptionPane#YES_NO_CANCEL_OPTION
     */
    YesNoCancel(JOptionPane.YES_NO_CANCEL_OPTION),

    /**
     * Options to specify {@code "ok"} and {@code "cancel"}.
     *
     * @see JOptionPane#OK_CANCEL_OPTION
     */
    OkCancel(JOptionPane.OK_CANCEL_OPTION);

    /** The corresponding {@link JOptionPane} option integer. */
    public final int jOption;

    DialogOptions(int jOption) {
        this.jOption = jOption;
    }
}
