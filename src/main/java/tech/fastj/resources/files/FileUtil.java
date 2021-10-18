package tech.fastj.resources.files;

import tech.fastj.engine.CrashMessages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Class that provides supplementary methods for working with files and {@link Path}s.
 *
 * @author Andrew Dey
 * @since 1.5.0
 */
public class FileUtil {

    /**
     * Gets the file extension of the specified path.
     * <p>
     * This method does <b>not</b> account for file extensions with more than one dot ({@code .}) -- in cases like
     * those, only the last part of the extension will be returned.
     * <p>
     * For paths which contain no file extension, an empty {@code String} will be returned.
     *
     * @param filePath The {@code Path} to get the file extension of.
     * @return The {@code Path}'s file extension.
     */
    public static String getFileExtension(Path filePath) {
        if (filePath.toString().contains(".")) {
            return filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * Reads all lines of a file, as an abstraction of {@link Files#readAllLines(Path)} which handles the possible
     * {@link IOException}.
     *
     * @param filePath The {@code Path} of the file to read.
     * @return The lines of the file.
     */
    public static List<String> readFileLines(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    CrashMessages.theGameCrashed("an issue while trying to read file \"" + filePath.toAbsolutePath() + "\"."),
                    exception
            );
        }
    }
}
