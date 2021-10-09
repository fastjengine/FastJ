package tech.fastj.graphics.dialog;

import javax.swing.JOptionPane;

public class DialogUtil {

    public static void showMessageDialog(DialogConfig dialogConfig) {
        JOptionPane.showMessageDialog(
                dialogConfig.dialogParent(),
                dialogConfig.promptText(),
                dialogConfig.title(),
                JOptionPane.INFORMATION_MESSAGE,
                dialogConfig.icon()
        );
    }

    public static String showInputDialog(DialogConfig dialogConfig) {
        return (String) JOptionPane.showInputDialog(
                dialogConfig.dialogParent(),
                dialogConfig.promptText(),
                dialogConfig.title(),
                JOptionPane.QUESTION_MESSAGE,
                dialogConfig.icon(),
                null,
                null
        );
    }

    public static boolean showConfirmationDialog(DialogConfig dialogConfig) {
        int result = JOptionPane.showConfirmDialog(
                dialogConfig.dialogParent(),
                dialogConfig.promptText(),
                dialogConfig.title(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
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

    public static int showOptionDialog(DialogConfig dialogConfig, DialogOptions dialogOption, DialogMessageTypes dialogMessageType, Object[] options, Object initialOption) {
        return JOptionPane.showOptionDialog(
                dialogConfig.dialogParent(),
                dialogConfig.promptText(),
                dialogConfig.title(),
                dialogOption.jOption,
                dialogMessageType.jOptionMessageType,
                dialogConfig.icon(),
                options,
                initialOption
        );
    }
}
