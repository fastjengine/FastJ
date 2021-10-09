package tech.fastj.graphics.dialog;

import javax.swing.JOptionPane;

public enum DialogOptions {
    Default(JOptionPane.DEFAULT_OPTION),
    YesNo(JOptionPane.YES_NO_OPTION),
    YesNoCancel(JOptionPane.YES_NO_CANCEL_OPTION),
    OkCancel(JOptionPane.OK_CANCEL_OPTION);

    public final int jOption;

    DialogOptions(int jOption) {
        this.jOption = jOption;
    }
}
