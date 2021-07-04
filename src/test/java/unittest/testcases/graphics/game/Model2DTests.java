package unittest.testcases.graphics.game;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.Drawable;
import tech.fastj.graphics.Transform2D;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.util.DrawUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Model2DTests {

    @Test
    void checkModel2DCreation_withPolygon2DArrayParam() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };

        Model2D model2D = Model2D.fromPolygons(polygons);

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(Drawable.DefaultShouldRender, model2D.shouldRender(), "The created model's 'shouldRender' option should match the default shouldRender option.");
        assertEquals(Transform2D.DefaultTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(Transform2D.DefaultRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(Transform2D.DefaultScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };

        boolean shouldRender = Maths.randomBoolean();

        Model2D model2D = Model2D.create(polygons, shouldRender).build();

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'shouldRender' option should match the default shouldRender option.");
        assertEquals(Transform2D.DefaultTranslation, model2D.getTranslation(), "The created model's translation should match the default translation.");
        assertEquals(Transform2D.DefaultRotation, model2D.getRotation(), "The created model's rotation should match the default rotation.");
        assertEquals(Transform2D.DefaultScale, model2D.getScale(), "The created model's scaling should match the default scale.");
    }

    @Test
    void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam_andRandomlyGeneratedTransformParams() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };

        boolean shouldRender = Maths.randomBoolean();
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);
        float expectedNormalizedRotation = randomRotation % 360;

        Model2D model2D = Model2D.create(polygons, shouldRender)
                .withTransform(randomTranslation, randomRotation, randomScale)
                .build();

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'shouldRender' option should match the random shouldRender option.");
        assertEquals(randomTranslation, model2D.getTranslation(), "The created model's translation should match the random translation.");
        assertEquals(randomRotation, model2D.getRotation(), "The created model's rotation should match the random rotation.");
        assertEquals(expectedNormalizedRotation, model2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
        assertEquals(randomScale, model2D.getScale(), "The created model's scaling should match the random scale.");
    }

    @Test
    void checkModel2DCreation_withPolygon2DArrayParam_andRandomlyGeneratedShowParam_andRandomlyGeneratedTransformParams_usingMethodChaining() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };

        boolean shouldRender = Maths.randomBoolean();
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);
        float expectedNormalizedRotation = randomRotation % 360;

        Model2D model2D = (Model2D) Model2D.fromPolygons(polygons)
                .setTranslation(randomTranslation)
                .setRotation(randomRotation)
                .setScale(randomScale)
                .setShouldRender(shouldRender);

        assertArrayEquals(polygons, model2D.getPolygons(), "The created model's Polygon2D array should match the original Polygon2D array.");
        assertEquals(shouldRender, model2D.shouldRender(), "The created model's 'shouldRender' option should match the expected shouldRender option.");
        assertEquals(randomTranslation, model2D.getTranslation(), "The created model's translation should match the expected translation.");
        assertEquals(randomRotation, model2D.getRotation(), "The created model's rotation should match the expected rotation.");
        assertEquals(expectedNormalizedRotation, model2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
        assertEquals(randomScale, model2D.getScale(), "The created model's scaling should match the expected scale.");
    }

    @Test
    void checkModel2DBoundsCreation_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);

        Polygon2D[] polygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };

        Pointf[] expectedBounds = {
                square1[0].copy(),
                new Pointf(square2[1].x, square1[1].y),
                square2[2].copy(),
                new Pointf(square1[3].x, square2[3].y)
        };

        Pointf[] actualBounds = Model2D.fromPolygons(polygons).getBounds();

        assertArrayEquals(expectedBounds, actualBounds, "The actual bounds generated by the Model2D should match the expected bounds.");
    }

    @Test
    void checkModel2DTranslation_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D polygon2D : expectedPolygons) {
            polygon2D.translate(randomTranslation);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.translate(randomTranslation);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getTranslation(), model2D.getTranslation(), "The translation of the Model2D should equal to the translation of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DRotation_aroundOrigin_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        float randomRotation = Maths.random(-5000f, 5000f);

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D polygon2D : expectedPolygons) {
            polygon2D.rotate(randomRotation, Pointf.Origin);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.rotate(randomRotation, Pointf.Origin);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getRotation(), model2D.getRotation(), "The rotation of the Model2D should equal to the rotation of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DRotation_aroundModelCenter_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf expectedModelCenter = Pointf.subtract(square2[2], square1[0]).divide(2f).add(square1[0]);
        float randomRotation = Maths.random(-5000f, 5000f);

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D expectedPolygon : expectedPolygons) {
            expectedPolygon.rotate(randomRotation, expectedModelCenter);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.rotate(randomRotation);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getRotation(), model2D.getRotation(), "The rotation of the Model2D should equal to the rotation of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DRotation_aroundRandomCenter_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomCenter = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D expectedPolygon : expectedPolygons) {
            expectedPolygon.rotate(randomRotation, randomCenter);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.rotate(randomRotation, randomCenter);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getRotation(), model2D.getRotation(), "The rotation of the Model2D should equal to the rotation of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DScaling_aroundOrigin_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D polygon2D : expectedPolygons) {
            polygon2D.scale(randomScaling, Pointf.Origin);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.scale(randomScaling, Pointf.Origin);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getScale(), model2D.getScale(), "The scale of the Model2D should equal to the scale of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DScaling_aroundModelCenter_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf expectedModelCenter = Pointf.subtract(square2[2], square1[0]).divide(2f).add(square1[0]);

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D expectedPolygon : expectedPolygons) {
            expectedPolygon.scale(randomScaling, expectedModelCenter);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.scale(randomScaling);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getScale(), model2D.getScale(), "The scale of the Model2D should equal to the scale of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DScaling_aroundRandomCenter_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomCenter = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D expectedPolygon : expectedPolygons) {
            expectedPolygon.scale(randomScaling, randomCenter);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.scale(randomScaling, randomCenter);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getScale(), model2D.getScale(), "The scale of the Model2D should equal to the scale of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }

    @Test
    void checkModel2DScaling_usingScaleAsFloat_shouldMatchExpected() {
        Pointf[] square1 = DrawUtil.createBox(Pointf.Origin, 50f);
        Pointf[] square2 = DrawUtil.createBox(Pointf.add(Pointf.Origin, 25f), 50f);
        float randomScaling = Maths.random(-50f, 50f);
        Pointf expectedModelCenter = Pointf.subtract(square2[2], square1[0]).divide(2f).add(square1[0]);

        Polygon2D[] expectedPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        for (Polygon2D expectedPolygon : expectedPolygons) {
            expectedPolygon.scale(new Pointf(randomScaling), expectedModelCenter);
        }

        Polygon2D[] actualPolygons = {
                Polygon2D.fromPoints(square1),
                Polygon2D.fromPoints(square2)
        };
        Model2D model2D = Model2D.fromPolygons(actualPolygons);
        model2D.scale(randomScaling);

        for (Polygon2D polygon2D : expectedPolygons) {
            assertEquals(polygon2D.getScale(), model2D.getScale(), "The scale of the Model2D should equal to the scale of the Polygon2Ds.");
            assertEquals(polygon2D.getTransformation(), model2D.getTransformation(), "The transformation of the Model2D should equal to the transformation of the Polygon2Ds.");
        }
    }
}
