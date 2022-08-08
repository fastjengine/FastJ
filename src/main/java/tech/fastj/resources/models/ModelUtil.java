package tech.fastj.resources.models;

import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.resources.files.FileUtil;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ModelUtil {

    private ModelUtil() {
        throw new java.lang.IllegalStateException();
    }

    private static final Map<String, BiFunction<Path, List<String>, Polygon2D[]>> ModelParser = Map.of(
            SupportedModelFormats.Psdf, PsdfUtil::parse,
            SupportedModelFormats.Obj, ObjUtil::parse
    );

    private static final Map<String, BiConsumer<Path, Model2D>> ModelWriter = Map.of(
            SupportedModelFormats.Psdf, PsdfUtil::write,
            SupportedModelFormats.Obj, ObjUtil::write
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
     * @param modelPath File location of the model.
     * @return An array of {@code Polygon2D}s.
     */
    public static Polygon2D[] loadModel(Path modelPath) {
        if (!Files.exists(modelPath, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalArgumentException("A file was not found at \"" + modelPath.toAbsolutePath() + "\".");
        }

        String fileExtension = FileUtil.getFileExtension(modelPath);
        checkFileExtension(fileExtension);

        List<String> lines = FileUtil.readFileLines(modelPath);
        return ModelParser.get(fileExtension).apply(modelPath, lines);
    }

    /**
     * Writes a model file containing the current state of the {@code Polygon2D}s that make up the specified {@code
     * Model2D}.
     *
     * @param destinationPath The destination path of the model file that will be written.
     * @param model           The {@code Model2D} that will be written to the file.
     */
    public static void writeModel(Path destinationPath, Model2D model) {
        String fileExtension = FileUtil.getFileExtension(destinationPath);
        checkFileExtension(fileExtension);
        ModelWriter.get(fileExtension).accept(destinationPath, model);
    }

    private static void checkFileExtension(String fileExtension) {
        if (Arrays.stream(SupportedModelFormats.values()).noneMatch(fileFormat -> fileFormat.equalsIgnoreCase(fileExtension))) {
            throw new IllegalArgumentException(
                    "Unsupported file extension \"" + fileExtension + "\"."
                            + System.lineSeparator()
                            + "This engine only supports files of the following extensions: " + SupportedModelFormats.valuesString
            );
        }
    }
}
