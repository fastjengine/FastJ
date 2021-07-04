package unittest.testcases.graphics;

import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.util.DrawUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import unittest.EnvironmentHelper;
import unittest.mock.graphics.MockDrawable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static unittest.EnvironmentHelper.runFastJWith;

class DrawableTests {

    @Test
    void checkGenerateDrawableIDs_noneShouldMatch() {
        int generatedDrawableCount = 255;
        Map<UUID, String> drawableIDs = new HashMap<>(generatedDrawableCount);
        for (int i = 0; i < generatedDrawableCount; i++) {
            Drawable drawable = new MockDrawable();
            drawableIDs.put(drawable.getUUID(), drawable.getID());
        }

        assertEquals(generatedDrawableCount, drawableIDs.keySet().size(), "Each Drawable should have a unique ID.");
    }

    @Test
    void checkCollision_betweenPolygon2D_andModel2D() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D polygon2D = Polygon2D.fromPoints(square);

        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Polygon2D[] polygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(polygons);

        assertTrue(polygon2D.collidesWith(model2D) && model2D.collidesWith(polygon2D), "The Model2D and Polygon2D should be intersecting.");
    }

    @Test
    void checkCollision_betweenPolygon2D_andText2D() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);

        runFastJWith(() -> {
            String text = "Hello, world!";
            Text2D text2D = Text2D.fromText(text);

            Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);
            Polygon2D polygon2D = Polygon2D.fromPoints(square);

            assertTrue(text2D.collidesWith(polygon2D) && polygon2D.collidesWith(text2D), "The Polygon2D and Text2D should be intersecting.");
        });
    }

    @Test
    void checkCollision_betweenText2D_andModel2D() {
        assumeFalse(EnvironmentHelper.IsEnvironmentHeadless);

        runFastJWith(() -> {
            String text = "Hello, world!";
            Text2D text2D = Text2D.fromText(text);

            Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
            Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
            Polygon2D[] polygons = {
                    Polygon2D.fromPoints(square1),
                    Polygon2D.fromPoints(square2)
            };
            Model2D model2D = Model2D.fromPolygons(polygons);

            assertTrue(text2D.collidesWith(model2D) && model2D.collidesWith(text2D), "The Model2D and Text2D should be intersecting.");
        });
    }
}
