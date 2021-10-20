package tech.fastj.examples.logging;

import tech.fastj.engine.FastJEngine;

public class Main {
    public static void main(String[] args) {
        /* FastJ Logging */

        // TODO: Finish update and amend last commit

        /* FastJEngine makes use of SLF4J (Simple Logging Facade for Java) for its logging solution.
         * This allows the user to use a specific logging framework of their choice -- if such a choice isn't needed,
         * FastJ templates will include SimpleLogger as a dependency.
         * Otherwise, you may choose whichever logging framework you like, so long as it supports SLF4J! */

        /* First up, "FastJEngine#log". The engine's LogLevel has to be set to "LogLevel.Info" or higher for this to
         * print out. */

        FastJEngine.log("Hello! This is an informational logging statement.");

        /* Next, "FastJEngine#warning". The engine's LogLevel has to be set to "LogLevel.Warn" or higher for this to
         * print out. */

        FastJEngine.warning("Be careful now! This is a warning.");

        /* Lastly, "FastJEngine#error". This method not only prints force closes the game and prints out the error, but
         * it crashes the engine with an exception as well. The engine's LogLevel has to be set to "LogLevel.Error" or
         * higher for this to print out.
         *
         * Under normal circumstances, this method also requires that you add an exception -- the
         * exception that caused a need to call "FastJEngine#error".
         * For the sake of demonstration, we will create an exception ourselves. */
        IllegalStateException exampleException = new IllegalStateException("This is an example exception.");
        FastJEngine.error("Oh dear, looks like something went wrong. This is an error message.", exampleException);
    }
}
