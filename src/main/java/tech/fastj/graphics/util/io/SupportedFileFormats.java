package tech.fastj.graphics.util.io;

import java.util.Arrays;
import java.util.stream.Stream;

public class SupportedFileFormats {
    public static final String Psdf = "psdf";

    public static final String[] values = {
            Psdf
    };

    public static final Stream<String> valuesStream = Arrays.stream(values);
    public static final String valuesString = Arrays.toString(values);
}
