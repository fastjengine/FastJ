package io.github.lucasstarsz.fastj.engine;

/** A simple and effective enum for creating crash messages in FastJ. */
public enum CrashMessages {

    /** "The game crashed, due to a scene error." */
    SCENE_ERROR(theGameCrashed("a scene error.")),
    /** "The game crashed, due to a rendering error." */
    RENDER_ERROR(theGameCrashed("a rendering error.")),
    /** "The game crashed, due to a configuration error." */
    CONFIGURATION_ERROR(theGameCrashed("a configuration error.")),
    /** "The game crashed, due to a method being called that can only be called before the program starts. */
    CALLED_AFTER_RUN_ERROR(theGameCrashed("a method being called that can only be called before the program starts.")),
    /** "The game crashed, due to the call of a method not yet implemented. */
    UNIMPLEMENTED_METHOD_ERROR(theGameCrashed("the call of a method not yet implemented."));

    /** The error message of the enum constant. */
    public final String errorMessage;

    CrashMessages(String message) {
        errorMessage = message;
    }

    /**
     * Gets a string depicting an error caused by an illegal action in the method of the specified class.
     * <p>
     * Example:
     * <pre>{@code
     * Class classCausingTheError = MyClass.class;
     * System.out.println(CrashMessages.illegalAction(classCausingTheError));
     *
     * // prints the following:
     * //
     * // The game crashed, due to an illegal action in class MyClass.
     * }</pre>
     *
     * @param className The class to generate the error message about.
     * @return The generated error message.
     */
    public static String illegalAction(Class<?> className) {
        return theGameCrashed("an illegal action in class " + className.getSimpleName() + ".");
    }

    /**
     * Gets a string starting with {@code "The game crashed, due to "}, and adds the specified error message to the end.
     *
     * @param errorMessage The error message to add to the end of the returned string.
     * @return The generated error message.
     */
    public static String theGameCrashed(String errorMessage) {
        return "The game crashed, due to " + errorMessage;
    }
}
