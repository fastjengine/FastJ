package unittest.testcases.graphics.shapes;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.gameobject.GameObject;
import io.github.lucasstarsz.fastj.graphics.gameobject.shapes.Model2D;
import io.github.lucasstarsz.fastj.graphics.gameobject.shapes.Polygon2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Model2DTests {

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        Model2D model2D = new Model2D(polygons);

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(Model2D.DefaultShow, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(GameObject.DefaultTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(GameObject.DefaultRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(GameObject.DefaultScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        boolean shouldRender = Maths.randomBoolean();

        Model2D model2D = new Model2D(polygons, shouldRender);

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(GameObject.DefaultTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(GameObject.DefaultRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(GameObject.DefaultScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam_andRandomlyGeneratedTransformParams() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        boolean shouldRender = Maths.randomBoolean();
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Model2D model2D = new Model2D(polygons, randomTranslation, randomRotation, randomScale, shouldRender);

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(randomTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(randomRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(randomScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam_andRandomlyGeneratedTransformParams_usingMethodChaining() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        boolean shouldRender = Maths.randomBoolean();
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Model2D model2D = (Model2D) new Model2D(polygons)
                .setTranslation(randomTranslation)
                .setRotation(randomRotation)
                .setScale(randomScale)
                .setShouldRender(shouldRender);

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(randomTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(randomRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(randomScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DBoundsCreation_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        Pointf[] expectedBounds = {
                square1[0].copy(),
                new Pointf(square2[1].x, square1[1].y),
                square2[2].copy(),
                new Pointf(square1[3].x, square2[3].y)
        };

        Pointf[] actualBounds = new Model2D(polygons).getBounds();

        assertArrayEquals(expectedBounds, actualBounds, "The actual bounds generated by the Model2D should match the expected bounds.");
    }

    @Test
    public void checkModel2DTranslation_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Polygon2D[] expectedPolygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        for (Polygon2D polygon2D : expectedPolygons) {
            polygon2D.translate(randomTranslation);
        }

        Polygon2D[] actualPolygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        Model2D model2D = new Model2D(actualPolygons);
        model2D.translate(randomTranslation);

        assertArrayEquals(expectedPolygons, model2D.getPolygons(), "The array of actual translated Polygon2Ds should match the expected Polygon2Ds.");
    }

    @Test
    public void checkModel2DRotation_aroundOrigin_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        float randomRotation = Maths.random(-50f, 50f);

        Polygon2D[] expectedPolygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        for (Polygon2D polygon2D : expectedPolygons) {
            polygon2D.rotate(randomRotation, Pointf.Origin);
        }

        Polygon2D[] actualPolygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        Model2D model2D = new Model2D(actualPolygons);
        model2D.rotate(randomRotation, Pointf.Origin);

        assertArrayEquals(expectedPolygons, model2D.getPolygons(), "The array of actual rotated Polygon2Ds should match the expected Polygon2Ds.");
    }

    @Test
    public void checkModel2DScaling_atOrigin_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Polygon2D[] expectedPolygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        for (Polygon2D polygon2D : expectedPolygons) {
            polygon2D.scale(randomScaling, Pointf.Origin);
        }

        Polygon2D[] actualPolygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        Model2D model2D = new Model2D(actualPolygons);
        model2D.scale(randomScaling, Pointf.Origin);

        assertArrayEquals(expectedPolygons, model2D.getPolygons(), "The array of actual scaled Polygon2Ds should match the expected Polygon2Ds.");
    }
}
