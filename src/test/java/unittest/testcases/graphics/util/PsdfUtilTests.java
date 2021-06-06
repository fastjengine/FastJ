package unittest.testcases.graphics.util;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;
import tech.fastj.graphics.util.PsdfUtil;
import tech.fastj.graphics.util.gradients.Gradients;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
    private static final String pathToModel = tempModelDirectoryPath.toAbsolutePath() + File.separator + "temp_house_model.psdf";

    private static final Pointf[] expectedHouseWallsMesh = DrawUtil.createBox(25f, 25f, 50f);
    private static final Polygon2D expectedHouseWalls = new Polygon2D(expectedHouseWallsMesh);
    private static final RadialGradientPaint expectedHouseWallsGradient = Gradients.radialGradient(expectedHouseWalls)
            .withColor(Color.magenta)
            .withColor(Color.lightGray)
            .build();

    private static final Pointf[] expectedHouseRoofMesh = {
            new Pointf(15f, 25f),
            new Pointf(50f, 20f),
            new Pointf(85f, 25f)
    };
    private static final Polygon2D expectedHouseRoof = new Polygon2D(expectedHouseRoofMesh);
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
    private static final Polygon2D expectedHouseDoor = new Polygon2D(expectedHouseDoorMesh);
    private static final Color expectedHouseDoorGradient = new Color(88, 243, 240, 226);

    private static final Polygon2D[] expectedHouseArray = {
            expectedHouseWalls.setPaint(expectedHouseWallsGradient),
            expectedHouseRoof.setPaint(expectedHouseRoofGradient),
            expectedHouseDoor.setPaint(expectedHouseDoorGradient)
    };
    private static final Model2D expectedHouse = new Model2D(expectedHouseArray, false);

    @BeforeAll
    public static void createTempModelFolder_forReadWriteTests() throws IOException {
        Files.createDirectory(tempModelDirectoryPath);
        System.out.println("Temporary Model Directory created at: " + tempModelDirectoryPath.toAbsolutePath());
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
        System.out.println("Deleted directory \"" + tempModelDirectoryPath.toAbsolutePath() + "\".");
    }

    @Test
    @Order(1)
    void checkWritePsdf_fileShouldMatchExpectedContent() throws IOException {
        List<String> expectedContent = List.of("amt 3",
                "rg 50.0 50.0 35.35534 255 0 255 255 192 192 192 255",
                "f true",
                "s true",
                "p 25 25",
                "p 75 25",
                "p 75 75",
                "p 25 75 ;",
                "",
                "lg 15.0 20.0 85.0 25.0 0 0 255 255 0 255 255 255 255 255 0 255 0 0 0 255 255 0 0 255 64 64 64 255 255 200 0 255 255 255 255 255",
                "f true",
                "s true",
                "p 15 25",
                "p 50 20",
                "p 85 25 ;",
                "",
                "c 88 243 240 226",
                "f true",
                "s true",
                "p 20 35",
                "p 35 35",
                "p 35 50",
                "p 20 50 ;"
        );


        System.out.println(pathToModel);
        PsdfUtil.writePsdf(pathToModel, expectedHouse);
        List<String> actualContent = Files.readAllLines(Path.of(pathToModel));

        for (int i = 0; i < actualContent.size(); i++) {
            assertEquals(expectedContent.get(i), actualContent.get(i), "Each line of the actual content should match the expected content.");
        }
    }

    @Test
    @Order(2)
    void checkReadPsdf_shouldMatchOriginal() {
        Polygon2D[] actualHouseArray = PsdfUtil.loadPsdf(pathToModel);
        Model2D actualHouse = new Model2D(actualHouseArray, false);

        assertArrayEquals(expectedHouseArray, actualHouseArray, "The actual Polygon2D array should match the expected array.");
        assertEquals(expectedHouse, actualHouse, "The actual Model2D should match the expected Model2D.");
    }

    @Test
    void tryReadPsdf_withIncorrectFileExtension() {
        String invalid_path = UUID.randomUUID() + ".notsupported";
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> PsdfUtil.loadPsdf(invalid_path));

        String expectedExceptionMessage = "Unsupported file type."
                + System.lineSeparator()
                + "This engine currently only supports files of the extension \".psdf\".";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryReadPsdf_withFileThatDoesNotExist() {
        String invalid_pathThatDoesNotResolveToFile = UUID.randomUUID() + ".psdf";

        Throwable exception = assertThrows(IllegalStateException.class, () -> PsdfUtil.loadPsdf(invalid_pathThatDoesNotResolveToFile)).getCause();
        assertEquals(NoSuchFileException.class, exception.getClass(), "The exception's class should match the expected class.");

        assertEquals(invalid_pathThatDoesNotResolveToFile, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }
}
