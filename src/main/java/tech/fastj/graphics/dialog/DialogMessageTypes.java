package tech.fastj.graphics.dialog;

import javax.swing.JOptionPane;

public enum DialogMessageTypes {
    Information(JOptionPane.INFORMATION_MESSAGE),
    Warning(JOptionPane.WARNING_MESSAGE),
    Error(JOptionPane.ERROR_MESSAGE),
    Question(JOptionPane.QUESTION_MESSAGE),
    Plain(JOptionPane.PLAIN_MESSAGE);

    public final int jOptionMessageType;

    DialogMessageTypes(int jOptionMessageType) {
        this.jOptionMessageType = jOptionMessageType;
    }
}
