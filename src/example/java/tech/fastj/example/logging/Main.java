package tech.fastj.example.logging;

import tech.fastj.engine.FastJEngine;

public class Main {
    public static void main(String[] args) {
        /* FastJ Logging */

        /* FastJ's logging solution is not very robust just yet. It currently makes use of the
         * standard out/err streams (System.out and System.err). While not performant, it is currently sufficient for the engine's usage. */

        /* First up, "FastJEngine#log". This method prints out in this style:
         * "INFO: <your message here, without the braces>" */

        FastJEngine.log("Hello! This is an informational logging statement.");

        /* Next, "FastJEngine#warning". This method prints out in this style:
         * "WARNING: <your message here, without the braces>" */

        FastJEngine.warning("Be careful now! This is a warning.");

        /* Lastly, "FastJEngine#error". This method is special, because it not only prints out the
         * error -- it crashes the engine with an exception as well.
         * This method prints in this style:
         * "ERROR: <your message here, without the braces>"
         * "Exception <the rest of the stack trace would be here>" */

        /* Under normal circumstances, this method also requires that you add an exception -- the
         * exception that caused a need to call "FastJEngine#error".
         * For the sake of demonstration, we will create an exception ourselves. */
        IllegalStateException exampleException = new IllegalStateException("This is an example exception.");
        FastJEngine.error("Oh dear, looks like something went wrong. This is an error message.", exampleException);
    }
}
