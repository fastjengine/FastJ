package tech.fastj.resources.models;

import java.util.Arrays;

/**
 * All file formats FastJ supports for model reading/writing.
 * <p>
 * List of supported formats:
 * <ul>
 *     <li>{@link #Psdf}</li>
 *     <li>{@link #Obj} -- WIP support</li>
 * </ul>
 *
 * @author Andrew Dey
 * @author <a href="https://github.com/Sammie156">Sammie156</a>
 * @since 1.5.0
 */
public class SupportedModelFormats {

    /** Read/write support for the {@code .PSDF} file format. */
    public static final String Psdf = "psdf";

    /** WIP read/write support for the {@code .OBJ} (and {@code .MTL}) file format. */
    public static final String Obj = "obj";

    /** Convenience string containing all supported file formats. */
    public static final String ValuesString = Arrays.toString(values());

    private SupportedModelFormats() {
        throw new java.lang.IllegalStateException();
    }

    /** Convenience array of the supported file formats. */
    public static String[] values() {
        return new String[] {
            Psdf,
            Obj
        };
    }
}
