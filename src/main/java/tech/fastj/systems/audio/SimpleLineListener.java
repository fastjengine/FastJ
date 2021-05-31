package tech.fastj.systems.audio;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SimpleLineListener implements LineListener {

    private Runnable lineOpenAction;
    private Runnable lineCloseAction;
    private Runnable lineStartAction;
    private Runnable lineStopAction;

    public SimpleLineListener(Runnable lineOpenAction, Runnable lineCloseAction, Runnable lineStartAction, Runnable lineStopAction) {
        this.lineOpenAction = lineOpenAction;
        this.lineCloseAction = lineCloseAction;
        this.lineStartAction = lineStartAction;
        this.lineStopAction = lineStopAction;
    }

    public Runnable getLineOpenAction() {
        return lineOpenAction;
    }

    public Runnable getLineCloseAction() {
        return lineCloseAction;
    }

    public Runnable getLineStartAction() {
        return lineStartAction;
    }

    public Runnable getLineStopAction() {
        return lineStopAction;
    }

    public void setLineOpenAction(Runnable lineOpenAction) {
        this.lineOpenAction = lineOpenAction;
    }

    public void setLineCloseAction(Runnable lineCloseAction) {
        this.lineCloseAction = lineCloseAction;
    }

    public void setLineStartAction(Runnable lineStartAction) {
        this.lineStartAction = lineStartAction;
    }

    public void setLineStopAction(Runnable lineStopAction) {
        this.lineStopAction = lineStopAction;
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type lineEventType = event.getType();

        if (LineEvent.Type.OPEN.equals(lineEventType)) {
            lineOpenAction.run();
        } else if (LineEvent.Type.CLOSE.equals(lineEventType)) {
            lineCloseAction.run();
        } else if (LineEvent.Type.START.equals(lineEventType)) {
            lineStartAction.run();
        } else if (LineEvent.Type.STOP.equals(lineEventType)) {
            lineStopAction.run();
        }
    }
}
