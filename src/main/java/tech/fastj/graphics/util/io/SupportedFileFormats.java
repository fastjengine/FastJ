package tech.fastj.graphics.util.io;

import java.util.Arrays;

public class SupportedFileFormats {
    public static final String Psdf = "psdf";

    public static final String valuesString = Arrays.toString(values());

    private SupportedFileFormats() {
        throw new java.lang.IllegalStateException();
    }

    public static String[] values() {
        return new String[]{
                Psdf
        };
    }
}
