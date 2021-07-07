package tech.fastj.graphics.util;

import tech.fastj.engine.CrashMessages;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.io.PsdfUtil;
import tech.fastj.graphics.io.SupportedFileFormats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModelUtil {

    private ModelUtil() {
        throw new java.lang.IllegalStateException();
    }

    private static final Map<String, Function<List<String>, Polygon2D[]>> ModelParser = Map.of(
            SupportedFileFormats.Psdf, PsdfUtil::parse
    );

    private static final Map<String, BiConsumer<Path, Model2D>> ModelWriter = Map.of(
            SupportedFileFormats.Psdf, PsdfUtil::write
    );

    /**
     * Gets a {@code Polygon2D} array, loaded from a {@code .psdf} file.
     * <p>
     * This method allows the user to load an array of {@code Polygon2D}s from a single file, decreasing the amount of
     * models that have to be programmed in.
     * <p>
     * Furthermore, this allows for easy use of the {@code Model2D} class, allowing you to directly use the resulting
     * array from this method to create a {@code Model2D} object.
     *
     * @param fileLocation Location of the file.
     * @return An array of {@code Polygon2D}s.
     */
    public static Polygon2D[] loadModel(Path fileLocation) {
        if (!Files.exists(fileLocation, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalArgumentException("A file was not found at \"" + fileLocation.toAbsolutePath() + "\".");
        }

        String fileExtension = getFileExtension(fileLocation);
        checkFileExtension(fileExtension);

        List<String> lines;
        try {
            lines = Files.readAllLines(fileLocation);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    CrashMessages.theGameCrashed("an issue while trying to parse file \"" + fileLocation.toAbsolutePath() + "\"."),
                    exception
            );
        }

        return ModelParser.get(fileExtension).apply(lines);
    }

    /**
     * Writes a model file containing the current state of the {@code Polygon2D}s that make up the specified {@code
     * Model2D}.
     *
     * @param destinationPath The destination path of the model file that will be written.
     * @param model           The {@code Model2D} that will be written to the file.
     */
    public static void writeModel(Path destinationPath, Model2D model) {
        String fileExtension = getFileExtension(destinationPath);
        checkFileExtension(fileExtension);
        ModelWriter.get(fileExtension).accept(destinationPath, model);
    }

    private static String getFileExtension(Path filePath) {
        return filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
    }

    private static void checkFileExtension(String fileExtension) {
        if (Arrays.stream(SupportedFileFormats.values()).noneMatch(fileFormat -> fileFormat.equalsIgnoreCase(fileExtension))) {
            throw new IllegalArgumentException(
                    "Unsupported file extension \"" + fileExtension + "\"."
                            + System.lineSeparator()
                            + "This engine only supports files of the following extensions: " + SupportedFileFormats.valuesString
            );
        }
    }
}
