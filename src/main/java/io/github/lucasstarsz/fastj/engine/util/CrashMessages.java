package io.github.lucasstarsz.fastj.engine.util;

/** An enum defining a simple and effective way to create and get crash messages in FastJ. */
public enum CrashMessages {

    SCENE_ERROR(theGameCrashed("a scene error.")),
    RENDER_ERROR(theGameCrashed("a rendering error.")),
    CONFIGURATION_ERROR(theGameCrashed("a configuration error.")),
    CALLED_AFTER_RUN_ERROR(theGameCrashed("a method being called that can only be called before the program starts.")),
    UNIMPLEMENTED_METHOD_ERROR(theGameCrashed("the call of a method not yet implemented."));

    public final String errorMessage;

    CrashMessages(String message) {
        errorMessage = message;
    }

    /**
     * Gets a string that depicts an error caused by an illegal action in the method of the
     * specified class.
     *
     * @param className The class to generate the error message about.
     * @return A string containing the generated error message.
     */
    public static String illegalAction(Class<?> className) {
        return theGameCrashed(" an illegal action in the " + className.getSimpleName() + " class.");
    }

    /**
     * Gets a string with {@code "The game crashed, due to "}, and adds the specified error message
     * to the end.
     *
     * @param errorMessage The error message to add to the end of the returned string.
     * @return A string containing the generated error message.
     */
    public static String theGameCrashed(String errorMessage) {
        return "The game crashed, due to " + errorMessage;
    }
}
