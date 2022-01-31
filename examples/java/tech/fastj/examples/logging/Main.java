package tech.fastj.examples.logging;

import tech.fastj.engine.FastJEngine;
import tech.fastj.logging.Log;
import tech.fastj.math.Pointf;

public class Main {
    public static void main(String[] args) {
        /* FastJ Logging */

        /* FastJEngine makes use of SLF4J (Simple Logging Facade for Java) for its logging solution.
         * This allows the user to use a specific logging framework of their choice -- if such a
         * choice isn't needed, FastJ templates will include SimpleLogger as a dependency.
         * Otherwise, you may choose whichever logging framework you like, so long as it supports
         * SLF4J! */


        /* The first topic to cover is an easy one -- logging formatting.
         * "{}" in a log message means there is an argument to be put there -- an argument of any
         * type. */
        String brackets = "Open and closed brackets";
        String argument = "an argument for message formatting.";
        FastJEngine.log("{} next to each other signifies {}.", brackets, argument);

        // Let's demonstrate a bit further, and add a Pointf to the mix.
        FastJEngine.log("Did you know the origin of the FastJ canvas is {}?", Pointf.origin());


        /* Now, onto log levels. FastJ, copying SLF4J, uses five levels -- "LogLevel"s -- of
         * logging, in the following order:
         *
         * Error - The lowest level of logging. Nothing but exceptions and errors will be logged.
         * Warn - The second-lowest level of logging. Warnings and Error content will be logged.
         * Info - The middle level of logging. General information and Warn content will be logged.
         * Debug - The second-highest level of logging. Debugging information will be logged.
         * Trace - The highest level of logging. All content will be logged at this level.
         *
         * And of course, they each have corresponding methods in FastJ. */


        /* First up, "FastJEngine#log". The engine's LogLevel has to be set to "LogLevel.Info" or
         * higher for this to print out. */
        FastJEngine.log("Hello! This is an informational logging statement.");
        /* If you want to log content as a specific class, you can use the underlying "Log#info"
         * method. */
        Log.info(Main.class, "Hello! This is also an informational logging statement.");

        /* Next, "FastJEngine#warning". The engine's LogLevel has to be set to "LogLevel.Warn" or
         * higher for this to print out. */
        FastJEngine.warning("Be careful now! This is a warning.");
        /* If you want to warn about content as a specific class, you can use the underlying
         * "Log#warn" method. */
        Log.warn(Main.class, "Be careful now! Now this, this is a warning.");

        /* This trend continues on.
         * "FastJEngine#debug" and "Log#debug" both relate to "LogLevel.Debug", and
         * "FastJEngine#trace" and "Log#trace" both relate to "LogLevel.Trace". */
        FastJEngine.debug("Quite the debug message, don't you think so?.");
        Log.debug(Main.class, "I do say, this is an interesting debug message.");

        FastJEngine.trace("I wonder if I'll be able to see this trace among all the other logs...");
        Log.trace(Main.class, "Perhaps if you saw the first trace message, maybe you'll see this one too...");

        /* Lastly, "FastJEngine#error". This method not only prints force closes the game and prints
         * out the error, but it crashes the engine with an exception as well. The engine's
         * LogLevel has to be set to "LogLevel.Error" or higher for this to print out.
         *
         * Under normal circumstances, this method also requires that you add an exception -- the
         * exception that caused a need to call "FastJEngine#error".
         * For the sake of demonstration, we will create an exception ourselves. */
        IllegalStateException exampleException = new IllegalStateException("This is an example exception.");
        Log.error(Main.class, "Oh dear, looks like something else went wrong!", exampleException);

        // Feel free to uncomment the line below -- but be warned, logging an error using
        // "FastJEngine#error" will throw an exception in this program.
        // FastJEngine.error("Oh dear, looks like something went wrong in FastJ.", exampleException);
    }
}
