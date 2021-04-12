package unittest.testcases.graphics.shapes;

import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.GameObject;
import io.github.lucasstarsz.fastj.graphics.shapes.Model2D;
import io.github.lucasstarsz.fastj.graphics.shapes.Polygon2D;
import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Model2DTests {

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        Model2D model2D = new Model2D(polygons);

        assertArrayEquals(polygons, model2D.getObjects(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(Model2D.DefaultShow, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(GameObject.defaultTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(GameObject.defaultRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(GameObject.defaultScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        boolean shouldRender = Maths.randomBoolean();

        Model2D model2D = new Model2D(polygons, shouldRender);

        assertArrayEquals(polygons, model2D.getObjects(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(GameObject.defaultTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(GameObject.defaultRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(GameObject.defaultScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam_andRandomlyGeneratedTransformParams() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.origin, 25f), 50f);

        Polygon2D[] polygons = {
                new Polygon2D(square1),
                new Polygon2D(square2)
        };

        boolean shouldRender = Maths.randomBoolean();
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Model2D model2D = new Model2D(polygons, randomTranslation, randomRotation, randomScale, shouldRender);

        assertArrayEquals(polygons, model2D.getObjects(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(randomTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(randomRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(randomScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    public void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam_andRandomlyGeneratedTransformParams_usingMethodChaining() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.origin, 25f), 50f);

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

        assertArrayEquals(polygons, model2D.getObjects(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'show' option should match the default show option.");
        assertEquals(randomTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(randomRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(randomScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }
}
