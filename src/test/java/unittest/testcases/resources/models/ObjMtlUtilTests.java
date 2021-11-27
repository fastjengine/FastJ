package unittest.testcases.resources.models;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import tech.fastj.engine.FastJEngine;

import tech.fastj.math.Pointf;

import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.resources.images.ImageResource;
import tech.fastj.resources.images.ImageResourceManager;
import tech.fastj.resources.models.ModelUtil;
import tech.fastj.resources.models.SupportedModelFormats;

import unittest.EnvironmentHelper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ObjMtlUtilTests {

    private static final Logger Log = LoggerFactory.getLogger(ObjMtlUtilTests.class);

    private static final Path tempModelDirectoryPath = Path.of("temp_" + ObjMtlUtilTests.class.getSimpleName());
    private static final Path pathToModel = Path.of(tempModelDirectoryPath + File.separator + "temp_house_model.obj");
    private static final Path pathToMaterial = Path.of(tempModelDirectoryPath + File.separator + "temp_house_model.mtl");
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

    private static final Pointf[] expectedHouseDoorMesh = DrawUtil.createBox(20f, 35f, 15f);
    private static final Polygon2D expectedHouseDoor = Polygon2D.fromPoints(expectedHouseDoorMesh);
    private static final Color expectedHouseDoorGradient = new Color(88, 243, 240, 226);

    private static final Polygon2D[] expectedHouseArray = {
            expectedHouseWalls.setFill(expectedHouseWallsGradient),
            expectedHouseRoof,
            expectedHouseDoor.setFill(expectedHouseDoorGradient)
    };
    private static final Model2D expectedHouse = Model2D.create(expectedHouseArray, false).build();

    @BeforeAll
    @AfterAll
    public static void onlyRunIfNotHeadless() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);
    }

    @BeforeAll
    public static void createTempDirectoryAndFile_forReadWriteTests() throws IOException {
        Files.createDirectory(tempModelDirectoryPath);
        Log.debug("Temporary directory created at: {}", tempModelDirectoryPath.toAbsolutePath());
        Files.createFile(pathToTempFile);
        Log.debug("Temporary .txt file created at: {}", pathToTempFile.toAbsolutePath());
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
        Log.debug("Deleted directory and files at:  \"{}\".", tempModelDirectoryPath.toAbsolutePath());
    }

    @Test
    @Order(1)
    void checkWriteObj_fileShouldMatchExpectedContent() throws IOException {
        List<String> expectedObjContent = List.of(
                "mtllib temp_house_model.mtl",
                "",
                "v 25.000000 25.000000 0.000000",
                "v 75.000000 25.000000 0.000000",
                "v 75.000000 75.000000 0.000000",
                "v 25.000000 75.000000 0.000000",
                "v 15.000000 25.000000 0.001000",
                "v 50.000000 20.000000 0.001000",
                "v 85.000000 25.000000 0.001000",
                "v 20.000000 35.000000 0.002000",
                "v 35.000000 35.000000 0.002000",
                "v 35.000000 50.000000 0.002000",
                "v 20.000000 50.000000 0.002000",
                "",
                "vt 0.000000 0.000000",
                "vt 1.000000 0.000000",
                "vt 1.000000 1.000000",
                "vt 0.000000 1.000000",
                "vt 0.000000 1.000000",
                "vt 0.500000 0.000000",
                "vt 1.000000 1.000000",
                "vt 0.000000 0.000000",
                "vt 1.000000 0.000000",
                "vt 1.000000 1.000000",
                "vt 0.000000 1.000000",
                "",
                "g Polygon2D_1",
                "usemtl Polygon2D_material_fill_1",
                "f 1/1 2/2 3/3 4/4",
                "usemtl Polygon2D_material_outline_1",
                "l 1 2 3 4",
                "",
                "g Polygon2D_2",
                "usemtl Polygon2D_material_outline_2",
                "l 5 6 7",
                "",
                "g Polygon2D_3",
                "usemtl Polygon2D_material_fill_3",
                "f 8/8 9/9 10/10 11/11",
                ""
        );

        ModelUtil.writeModel(pathToModel, expectedHouse);
        List<String> actualObjContent = Files.readAllLines(pathToModel);
        for (int i = 0; i < actualObjContent.size(); i++) {
            assertEquals(expectedObjContent.get(i), actualObjContent.get(i), "Each line of the actual .obj content should match the expected .obj content.");
        }
    }

    @Test
    @Order(2)
    void checkWriteObjGeneratedMtl() throws IOException {
        assertTrue(Files.exists(pathToMaterial), "Writing the .obj file should generate the material as a .mtl.");

        List<String> expectedMtlContent = List.of(
                "newmtl Polygon2D_material_fill_1",
                "Ka 1.000000 1.000000 1.000000",
                "Kd 1.000000 1.000000 1.000000",
                "Ks 0.000000 0.000000 0.000000",
                "Ns 1.000000",
                "d 1.000000",
                "illum 1",
                "map_Kd temp_house_model_gradient_1.png",
                "",
                "newmtl Polygon2D_material_outline_1",
                "Ka 0.588235 0.588235 0.588235",
                "Kd 0.588235 0.588235 0.588235",
                "Ks 0.000000 0.000000 0.000000",
                "Ns 1.000000",
                "d 0.588235",
                "illum 1",
                "",
                "newmtl Polygon2D_material_outline_2",
                "Ka 0.000000 0.000000 0.000000",
                "Kd 0.000000 0.000000 0.000000",
                "Ks 0.000000 0.000000 0.000000",
                "Ns 1.000000",
                "d 1.000000",
                "illum 1",
                "",
                "newmtl Polygon2D_material_fill_3",
                "Ka 0.345098 0.952941 0.941176",
                "Kd 0.345098 0.952941 0.941176",
                "Ks 0.000000 0.000000 0.000000",
                "Ns 1.000000",
                "d 0.886275",
                "illum 1",
                ""
        );

        List<String> actualMtlContent = Files.readAllLines(pathToMaterial);
        for (int i = 0; i < actualMtlContent.size(); i++) {
            assertEquals(expectedMtlContent.get(i), actualMtlContent.get(i), "Each line of the actual .mtl content should match the expected .mtl content.");
        }
    }

    @Test
    @Order(2)
    void checkWriteObjGeneratedTextures() {
        Path expectedTexturePath1 = Path.of(tempModelDirectoryPath + File.separator + "temp_house_model_gradient_1.png");
        assertTrue(Files.exists(expectedTexturePath1), "Writing the .obj file should generate the gradient as a .png.");
    }

    @Test
    @Order(2)
    void checkReadObj_shouldMatchOriginal() {
        // TODO: either re-add resource managers on init() or don't remove them on exit()
        // so that this monstrosity doesn't need to occur
        try {
            FastJEngine.getResourceManager(ImageResource.class);
        } catch (IllegalStateException exception) {
            FastJEngine.addResourceManager(new ImageResourceManager(), ImageResource.class);
        }
        Polygon2D[] actualHouseArray = ModelUtil.loadModel(pathToModel);
        Model2D actualHouse = Model2D.create(actualHouseArray, false).build();

        for (int i = 0; i < actualHouse.getPolygons().length; i++) {
            RenderStyle renderStyle = actualHouseArray[i].getRenderStyle();
            assertEquals(expectedHouseArray[i].getRenderStyle(), renderStyle, "The created polygon's render style option should match the default render style.");

            if (renderStyle == RenderStyle.Fill || renderStyle == RenderStyle.FillAndOutline) {
                if (expectedHouseArray[i].getFill() instanceof Color) {
                    assertEquals(expectedHouseArray[i].getFill(), actualHouseArray[i].getFill(), "The created polygon's color should match the expected color.");
                } else {
                    assertTrue(actualHouseArray[i].getFill() instanceof TexturePaint, "As a result of the loss of data exporting gradients to .obj, the polygon's gradient should now be a texture.");
                }
            }

            if (renderStyle == RenderStyle.Outline || renderStyle == RenderStyle.FillAndOutline) {
                assertEquals(Polygon2D.DefaultOutlineStroke, actualHouseArray[i].getOutlineStroke(), "As a result of the loss of data exporting outline strokes to .obj, the polygon's outline stroke should now be the default.");
                assertEquals(expectedHouseArray[i].getOutlineColor(), actualHouseArray[i].getOutlineColor(), "The created polygon's outline color should match the expected outline color.");
            }

            assertEquals(expectedHouseArray[i].getTranslation(), actualHouseArray[i].getTranslation(), "The created polygon's translation should match the randomly generated translation.");
            assertEquals(expectedHouseArray[i].getRotation(), actualHouseArray[i].getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
            assertEquals(expectedHouseArray[i].getRotationWithin360(), actualHouseArray[i].getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
            assertEquals(expectedHouseArray[i].getScale(), actualHouseArray[i].getScale(), "The created polygon's scaling should match the randomly generated scale.");
            assertArrayEquals(expectedHouseArray[i].getOriginalPoints(), actualHouseArray[i].getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
        }
    }

    @Test
    void tryReadObj_withIncorrectFileExtension() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> ModelUtil.loadModel(pathToTempFile));

        String expectedExceptionMessage = "Unsupported file extension \"txt\"."
                + System.lineSeparator()
                + "This engine only supports files of the following extensions: " + SupportedModelFormats.valuesString;
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }

    @Test
    void tryReadObj_withFileThatDoesNotExist() {
        Path invalid_pathThatDoesNotResolveToFile = Path.of(UUID.randomUUID() + ".obj");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> ModelUtil.loadModel(invalid_pathThatDoesNotResolveToFile));
        String expectedExceptionMessage = "A file was not found at \"" + invalid_pathThatDoesNotResolveToFile.toAbsolutePath() + "\".";
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }
}
