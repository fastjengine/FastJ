package unittest.testcases.graphics;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.Camera;

import java.awt.geom.AffineTransform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraTests {

    @Test
    void createCamera_shouldMatchDefaultCamera() {
        Camera camera = new Camera();
        assertEquals(Camera.Default, camera, "The created camera should match the default camera.");
    }

    @Test
    void createCamera_withTranslationPointf_andRotationFloat_andScalePointf_shouldMatchExpected() {
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        float expectedNormalizedRotation = randomRotation % 360;
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));

        Camera camera = new Camera(randomTranslation, randomRotation, randomScale);

        assertEquals(randomTranslation, camera.getTranslation(), "The created camera's translation should match the expected translation.");
        assertEquals(randomRotation, camera.getRotation(), "The created camera's rotation should match the expected rotation.");
        assertEquals(expectedNormalizedRotation, camera.getRotationWithin360(), "The created camera's normalized rotation should match the normalized rotation.");
    }

    @Test
    void checkCameraTranslation_shouldMatchExpected() {
        Camera camera = new Camera();

        Pointf randomTranslation1 = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomTranslation2 = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomTranslation3 = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        camera.translate(randomTranslation1);
        camera.translate(randomTranslation2);
        camera.translate(randomTranslation3);
        Pointf expectedTranslation = Pointf.add(randomTranslation1, randomTranslation2).add(randomTranslation3);

        AffineTransform expectedTransform = new AffineTransform();
        expectedTransform.translate(randomTranslation1.x + randomTranslation2.x + randomTranslation3.x, randomTranslation1.y + randomTranslation2.y + randomTranslation3.y);

        assertEquals(expectedTranslation, camera.getTranslation(), "After translating the camera 3 times, the camera's translation should match the expected translation.");
    }

    @Test
    void checkCameraRotation_shouldMatchExpected() {
        Camera camera = new Camera();

        float randomRotation1 = Maths.random(-50f, 50f);
        float randomRotation2 = Maths.random(-50f, 50f);
        float randomRotation3 = Maths.random(-50f, 50f);
        camera.rotate(randomRotation1);
        camera.rotate(randomRotation2);
        camera.rotate(randomRotation3);
        float expectedRotation = randomRotation1 + randomRotation2 + randomRotation3;
        float expectedNormalizedRotation = expectedRotation % 360;

        AffineTransform expectedTransform = new AffineTransform();
        expectedTransform.rotate(Math.toRadians(randomRotation1 + randomRotation2 + randomRotation3));

        assertEquals(expectedRotation, camera.getRotation(), "After rotating the camera 3 times, the camera's rotation should match the expected rotation.");
        assertEquals(expectedNormalizedRotation, camera.getRotationWithin360(), "The created camera's normalized rotation should match the normalized rotation.");
    }

    @Test
    void checkCameraScale_shouldMatchExpected() {
        Camera camera = new Camera();

        Pointf randomScale1 = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale2 = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale3 = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        camera.scale(randomScale1);
        camera.scale(randomScale2);
        camera.scale(randomScale3);
        Pointf expectedScale = Pointf.add(randomScale1, randomScale2).add(randomScale3).add(Transform2D.DefaultScale);

        AffineTransform expectedTransform = new AffineTransform();
        expectedTransform.scale(randomScale1.x + randomScale2.x + randomScale3.x, randomScale1.y + randomScale2.y + randomScale3.y);

        assertEquals(expectedScale, camera.getScale(), "After scaling the camera 3 times, the camera's scale should match the expected scale.");
    }
}
