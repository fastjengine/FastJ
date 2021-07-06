package unittest.testcases.graphics.util;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.RenderStyle;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.graphics.util.ModelUtil;
import tech.fastj.graphics.util.gradients.Gradients;
import tech.fastj.graphics.util.io.SupportedFileFormats;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PsdfUtilTests {

    private static final Path tempModelDirectoryPath = Path.of("temp");
    private static final Path pathToModel = Path.of(tempModelDirectoryPath.toAbsolutePath() + File.separator + "temp_house_model.psdf");
    private static final Path pathToTempFile = Path.of(tempModelDirectoryPath.toAbsolutePath() + File.separator + "temp_file.txt");

    private static final Pointf[] expectedHouseWallsMesh = DrawUtil.createBox(25f, 25f, 50f);
    private static final RenderStyle expectedHouseWallsRenderStyle = RenderStyle.FillAndOutline;
    private static final BasicStroke expectedHouseWallsOutlineStroke = new BasicStroke(5.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 15f);
    private static final Color expectedHouseWallsOutlineColor = new Color(150, 150, 150, 150);
    private static final Polygon2D expectedHouseWalls = Polygon2D.create(expectedHouseWallsMesh, expectedHouseWallsRenderStyle)
            .withOutline(expectedHouseWallsOutlineStroke, expectedHouseWallsOutlineColor)
            .build();
    private static final RadialGradientPaint expectedHouseWallsGradient = Gradients.radialGradient(expectedHouseWalls)
            .withColor(Color.magenta)
            .withColor(Color.lightGray)
            .build();

    private static final Pointf[] expectedHouseRoofMesh = {
            new Pointf(15f, 25f),
            new Pointf(50f, 20f),
            new Pointf(85f, 25f)
    };
    private static final RenderStyle expectedHouseRoofRenderStyle = RenderStyle.Outline;
    private static final BasicStroke expectedHouseRoofOutlineStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f);
    private static final Polygon2D expectedHouseRoof = Polygon2D.create(expectedHouseRoofMesh, expectedHouseRoofRenderStyle)
            .withOutline(expectedHouseRoofOutlineStroke, Polygon2D.DefaultOutlineColor)
            .build();
    private static final LinearGradientPaint expectedHouseRoofGradient = Gradients.linearGradient(expectedHouseRoof, Boundary.TopLeft, Boundary.BottomRight)
            .withColor(Color.blue)
            .withColor(Color.cyan)
            .withColor(Color.yellow)
            .withColor(Color.black)
            .withColor(Color.red)
            .withColor(Color.darkGray)
            .withColor(Color.orange)
            .withColor(Color.white)
            .build();

    private static final Pointf[] expectedHouseDoorMesh = DrawUtil.createBox(20f, 35f, 15f);
    private static final BasicStroke expectedHouseDoorOutlineStroke = new BasicStroke(25.13f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 0.0f);
    private static final Color expectedHouseDoorOutlineColor = new Color(40, 200, 0, 1);
    private static final Polygon2D expectedHouseDoor = Polygon2D.create(expectedHouseDoorMesh)
            .withOutline(expectedHouseDoorOutlineStroke, expectedHouseDoorOutlineColor)
            .build();
    private static final Color expectedHouseDoorGradient = new Color(88, 243, 240, 226);

    private static final Polygon2D[] expectedHouseArray = {
            expectedHouseWalls.setFill(expectedHouseWallsGradient),
            expectedHouseRoof.setFill(expectedHouseRoofGradient),
            expectedHouseDoor.setFill(expectedHouseDoorGradient)
    };
    private static final Model2D expectedHouse = Model2D.create(expectedHouseArray, false).build();

    @BeforeAll
    public static void createTempDirectoryAndFile_forReadWriteTests() throws IOException {
        Files.createDirectory(tempModelDirectoryPath);
        System.out.println("Temporary directory created at: " + tempModelDirectoryPath.toAbsolutePath());
        Files.createFile(pathToTempFile);
        System.out.println("Temporary .txt file created at: " + pathToTempFile.toAbsolutePath());
    }

    @AfterAll
    public static void deleteTempModelFolder() throws IOException {
        try (Stream<Path> pathWalker = Files.walk(tempModelDirectoryPath)) {
            pathWalker.sorted(Comparator.reverseOrder()).forEach(file -> {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    throw new IllegalStateException("The file system didn't like that.", e);
                }
            });
        }
        System.out.println("Deleted directory and files at \"" + tempModelDirectoryPath.toAbsolutePath() + "\".");
    }

    @Test
    @Order(1)
    void checkWritePsdf_fileShouldMatchExpectedContent() throws IOException {
        List<String> expectedContent = List.of(
                "amt 3",
                "rs filloutline",
                "rg 50.0 50.0 35.35534 255 0 255 255 192 192 192 255",
                "otls 5.5 1 0 15.0",
                "otlc 150 150 150 150",
                "tfm 0.0 0.0 0.0 1.0 1.0",
                "sr true",
                "p 25.0 25.0",
                "p 75.0 25.0",
                "p 75.0 75.0",
                "p 25.0 75.0 ;",
                "",
                "rs outline",
                "lg 15.0 20.0 85.0 25.0 0 0 255 255 0 255 255 255 255 255 0 255 0 0 0 255 255 0 0 255 64 64 64 255 255 200 0 255 255 255 255 255",
                "otls 1.0 0 2 0.0",
                "otlc 0 0 0 255",
                "tfm 0.0 0.0 0.0 1.0 1.0",
                "sr true",
                "p 15.0 25.0",
                "p 50.0 20.0",
                "p 85.0 25.0 ;",
                "",
                "rs fill",
                "c 88 243 240 226",
                "otls 25.13 2 1 0.0",
                "otlc 40 200 0 1",
                "tfm 0.0 0.0 0.0 1.0 1.0",
                "sr true",
                "p 20.0 35.0",
                "p 35.0 35.0",
                "p 35.0 50.0",
                "p 20.0 50.0 ;"
        );

        ModelUtil.writeModel(pathToModel, expectedHouse);
        List<String> actualContent = Files.readAllLines(pathToModel);

        for (int i = 0; i < actualContent.size(); i++) {
            assertEquals(expectedContent.get(i), actualContent.get(i), "Each line of the actual content should match the expected content.");
        }
    }

    @Test
    @Order(2)
    void checkReadPsdf_shouldMatchOriginal() {
        Polygon2D[] actualHouseArray = ModelUtil.loadModel(pathToModel);
        Model2D actualHouse = Model2D.create(actualHouseArray, false).build();

        assertArrayEquals(expectedHouseArray, actualHouseArray, "The actual Polygon2D array should match the expected array.");
        assertEquals(expectedHouse, actualHouse, "The actual Model2D should match the expected Model2D.");
    }

    @Test
    void tryReadPsdf_withIncorrectFileExtension() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> ModelUtil.loadModel(pathToTempFile));

        String expectedExceptionMessage = "Unsupported file extension \"txt\"."
                + System.lineSeparator()
                + "This engine only supports files of the following extensions: " + SupportedFileFormats.valuesString;
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryReadPsdf_withFileThatDoesNotExist() {
        Path invalid_pathThatDoesNotResolveToFile = Path.of(UUID.randomUUID() + ".psdf");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> ModelUtil.loadModel(invalid_pathThatDoesNotResolveToFile));
        String expectedExceptionMessage = "A file was not found at \"" + invalid_pathThatDoesNotResolveToFile.toAbsolutePath() + "\".";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }
}
