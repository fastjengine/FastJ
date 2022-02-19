package tech.fastj;

/**
 * TODO: documentation
 *
 * @author Andrew Dey
 */
public interface Feature {

    void start();

    // TODO: action should supply some sort of event
    void onAction();

    void stop();
}
