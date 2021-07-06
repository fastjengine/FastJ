package tech.fastj.example.modelreadwrite;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.graphics.util.ModelUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        /* Model Reading/Writing */

        /* WARNING: If you're not familiar with FastJ, you'll need to read through the
         * "model2d" example first.
         *
         * Creating models from pure code is convenient, but quite cumbersome to read through. Let's
         * learn how to read and write them to a file format instead. */

        // We'll start with the model code from the "model2d" example.
        Pointf[] smallSquareMesh1 = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D smallSquare1 = Polygon2D.fromPoints(smallSquareMesh1);

        Pointf[] smallSquareMesh2 = DrawUtil.createBox(50f, 50f, 25f);
        Polygon2D smallSquare2 = Polygon2D.fromPoints(smallSquareMesh2);

        Polygon2D[] smallSquaresArray = {smallSquare1, smallSquare2};
        Model2D smallSquares = Model2D.fromPolygons(smallSquaresArray);


        /* Now that that's out of the way, let's write this Model2D to a file.
         * DrawUtil contains the function for writing Model2D objects to a file. With this, we'll
         * write the file, and then print out the contents.
         *
         * Model2D objects can be written to the PSDF (Polygon Structural Data Format) format,
         * which is a format written specifically for use with the FastJ engine.
         * At a later point in time, the engine will support writing Model2D objects to more
         * standard formats, but until then we'll be using the PSDF format.
         */


        /* This is the path which we will be writing to. It resolves to
         * "src/example/resources/smallSquares.psdf". */
        Path smallSquaresFilePath = Path.of("src/example/resources/smallSquares.psdf");

        // And now, we can call the model-writing method.
        // We can write the model using ModelUtil.writeModel(path, model).
        ModelUtil.writeModel(smallSquaresFilePath, smallSquares);


        /* As a small aside, this gets the file we just wrote to and prints out its contents.
         * The code is not the primary focus of the example -- this is just to give a visual. */
        try {
            String smallSquaresFileContents = Files.readString(smallSquaresFilePath);
            FastJEngine.log("Contents of smallSquares.psdf: \n" + smallSquaresFileContents);
        } catch (IOException exception) {
            exception.printStackTrace();
        }


        /* Next up, we'll read the file back.
         * Since we already have the string denoting the location of the file, we can reuse it. */

        // We can read the model from the file using ModelUtil.loadModel(path).
        Polygon2D[] smallSquaresArrayFromFile = ModelUtil.loadModel(smallSquaresFilePath);

        // This nets us a Polygon2D array -- we can use this to create a new Model2D.
        Model2D smallSquaresFromFile = Model2D.fromPolygons(smallSquaresArrayFromFile);

        /* Here, we check for equality between the original and the read file.
         * As expected, the two contain the exact same contents. */
        FastJEngine.log("smallSquaresFromFile == smallSquares? " + smallSquaresFromFile.equals(smallSquares));


        /* Another small aside: this removes the file we created earlier in the program.
         * This code is not the primary focus of the example -- it's just to clean up the work we did. */
        try {
            boolean wasDeleted = Files.deleteIfExists(smallSquaresFilePath);
            if (!wasDeleted) {
                FastJEngine.warning(
                        "The file at \"" + smallSquaresFilePath.toAbsolutePath() + "\" was not deleted. Whoever is reading this, should go delete that file."
                                + System.lineSeparator()
                                + "Then, report this error as an issue in the engine repository."
                );
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
