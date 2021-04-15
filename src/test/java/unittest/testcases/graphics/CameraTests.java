package unittest.testcases.graphics;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.Camera;

import java.awt.geom.AffineTransform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraTests {

    @Test
    public void createCamera_shouldMatchDefaultCamera() {
        Camera camera = new Camera();
        assertEquals(Camera.Default, camera, "The created camera should match the default camera.");
    }

    @Test
    public void createCamera_withTranslationPointf_andDefaultRotation_shouldMatchExpected() {
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Camera camera = new Camera(randomTranslation);

        assertEquals(randomTranslation, camera.getTranslation(), "The created camera's translation should match the expected translation.");
    }

    @Test
    public void createCamera_withDefaultTranslation_andRotationFloat_shouldMatchExpected() {
        float randomRotation = Maths.random(-50f, 50f);
        Camera camera = new Camera(randomRotation);

        assertEquals(randomRotation, camera.getRotation(), "The created camera's rotation should match the expected rotation.");
    }

    @Test
    public void createCamera_withTranslationPointf_andRotationFloat_shouldMatchExpected() {
        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-50f, 50f);
        Camera camera = new Camera(randomTranslation, randomRotation);

        assertEquals(randomTranslation, camera.getTranslation(), "The created camera's translation should match the expected translation.");
        assertEquals(randomRotation, camera.getRotation(), "The created camera's rotation should match the expected rotation.");
    }

    @Test
    public void checkCameraTranslation_shouldMatchExpected() {
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
        assertEquals(expectedTransform, camera.getTransformation(), "After translating the camera 3 times, the camera's transform should match the expected transform.");
    }

    @Test
    public void checkCameraRotation_shouldMatchExpected() {
        Camera camera = new Camera();
        System.out.println(camera.getTransformation());

        float randomRotation1 = Maths.random(-50f, 50f);
        float randomRotation2 = Maths.random(-50f, 50f);
        float randomRotation3 = Maths.random(-50f, 50f);
        camera.rotate(randomRotation1);
        camera.rotate(randomRotation2);
        camera.rotate(randomRotation3);
        float expectedRotation = randomRotation1 + randomRotation2 + randomRotation3;

        AffineTransform expectedTransform = new AffineTransform();
        expectedTransform.rotate(Math.toRadians(randomRotation1 + randomRotation2 + randomRotation3));

        assertEquals(expectedRotation, camera.getRotation(), "After rotating the camera 3 times, the camera's rotation should match the expected rotation.");
        assertEquals(expectedTransform, camera.getTransformation(), "After rotating the camera 3 times, the camera's transform should match the expected transform.");
    }
}
