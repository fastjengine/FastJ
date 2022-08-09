package tech.fastj.engine;

/**
 * A simple and effective enum for creating crash messages in FastJ.
 *
 * @author Andrew Dey
 * @since 1.0.0
 */
public enum CrashMessages {

    /** "The game crashed, due to a scene error." */
    SceneError(theGameCrashed("a scene error.")),
    /** "The game crashed, due to a rendering error." */
    RenderError(theGameCrashed("a rendering error.")),
    /** "The game crashed, due to a configuration error." */
    ConfigurationError(theGameCrashed("a configuration error.")),
    /** "The game crashed, due to a method being called that can only be called before the program starts." */
    CalledAfterRunError(theGameCrashed("a method being called that can only be called before the program starts.")),
    /** "The game crashed, due to the call of a method not yet implemented." */
    UnimplementedMethodError(theGameCrashed("the call of a method not yet implemented."));

    /** The error message of the enum constant. */
    public final String errorMessage;

    CrashMessages(String message) {
        errorMessage = message;
    }

    /**
     * Creates a string depicting an error caused by an illegal action in the method of the specified class.
     * <p>
     * Example:
     * {@snippet lang = "java":
     * Class<MyClass> classCausingTheError = MyClass.class;
     * String errorMessage = CrashMessages.illegalAction(classCausingTheError);
     * // "The game crashed, due to an illegal action in class MyClass."
     *}
     *
     * @param className The class to generate the error message about.
     * @return The generated error message.
     */
    public static String illegalAction(Class<?> className) {
        return theGameCrashed("an illegal action in class " + className.getSimpleName() + ".");
    }

    /**
     * Creates a string starting with {@code "The game crashed, due to "}, adding the specified error message to the end.
     *
     * @param errorMessage The error message to add to the end of the returned string.
     * @return {@code "The game crashed, due to " + errorMessage}
     */
    public static String theGameCrashed(String errorMessage) {
        return "The game crashed, due to " + errorMessage;
    }
}
