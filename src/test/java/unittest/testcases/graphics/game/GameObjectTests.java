package unittest.testcases.graphics.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import tech.fastj.math.Maths;

import tech.fastj.graphics.game.GameObject;

import tech.fastj.systems.behaviors.Behavior;
import tech.fastj.systems.control.Scene;

import unittest.mock.graphics.MockGameObject;
import unittest.mock.systems.behaviors.MockBehavior;
import unittest.mock.systems.control.MockEmptyScene;
import org.junit.jupiter.api.Test;

class GameObjectTests {

  @Test
  void checkCreateGameObject_behaviorsShouldBeEmpty() {
    GameObject gameObject = new MockGameObject();
    assertEquals(
        0,
        gameObject.getBehaviors().size(),
        "When initially created, a GameObject's behavior list should contain no behaviors.");
  }

  @Test
  void checkAddBehaviorToGameObject_shouldAllowMultiple() {
    GameObject gameObject = new MockGameObject();

    Behavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();
    int behaviorCount = 255;

    for (int i = 0; i < behaviorCount; i++) {
      gameObject.addBehavior(mockBehavior, mockScene);
    }

    assertEquals(
        behaviorCount,
        gameObject.getBehaviors().size(),
        "When a behavior is added multiple times, the GameObject's behavior count should increase.");
  }

  @Test
  void checkClearBehaviorsFromGameObject_shouldRemoveAll() {
    GameObject gameObject = new MockGameObject();

    Behavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();
    int behaviorCount = 255;

    for (int i = 0; i < behaviorCount; i++) {
      gameObject.addBehavior(mockBehavior, mockScene);
    }
    assertNotEquals(0, gameObject.getBehaviors().size());

    gameObject.clearAllBehaviors();
    assertEquals(
        0,
        gameObject.getBehaviors().size(),
        "After clearing all behaviors from the GameObject, it should not contain any behaviors.");
  }

  @Test
  void checkRemoveBehaviorFromGameObject() {
    GameObject gameObject = new MockGameObject();

    Behavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();
    int behaviorCount = 255;

    for (int i = 0; i < behaviorCount; i++) {
      gameObject.addBehavior(mockBehavior, mockScene);
    }

    int expectedBehaviorCount = 200;
    for (int i = 0; i < behaviorCount - expectedBehaviorCount; i++) {
      gameObject.removeBehavior(mockBehavior, mockScene);
    }

    assertEquals(
        expectedBehaviorCount,
        gameObject.getBehaviors().size(),
        "When behaviors are removed, the behavior count should decrease.");
  }

  @Test
  void checkAddAndRemoveBehaviorsFromGameObject_usingMethodChaining() {
    Behavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();

    GameObject gameObject =
        new MockGameObject()
            .addBehavior(mockBehavior, mockScene) // 1
            .addBehavior(mockBehavior, mockScene) // 2
            .addBehavior(mockBehavior, mockScene) // 3
            .removeBehavior(mockBehavior, mockScene) // 2
            .addBehavior(mockBehavior, mockScene) // 3
            .removeBehavior(mockBehavior, mockScene) // 2
            .removeBehavior(mockBehavior, mockScene) // 1
            .addBehavior(mockBehavior, mockScene); // 2

    assertEquals(
        2,
        gameObject.getBehaviors().size(),
        "After the sequence of adding and removing behaviors, the remaining behavior count should be 5.");
  }

  @Test
  void checkInitBehaviors_shouldInitializePointf() {
    GameObject gameObject = new MockGameObject();
    MockBehavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();

    gameObject.addBehavior(mockBehavior, mockScene);
    gameObject.initBehaviors();

    assertNotNull(
        mockBehavior.getPointf(),
        "After initializing the GameObject's behaviors, its Pointf should not be null.");
  }

  @Test
  void checkUpdateBehaviors_shouldIncrementPointf() {
    GameObject gameObject = new MockGameObject();
    MockBehavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();

    gameObject.addBehavior(mockBehavior, mockScene);
    gameObject.initBehaviors();

    int expectedIncrement = 15;
    for (int i = 0; i < expectedIncrement; i++) {
      gameObject.updateBehaviors();
    }

    boolean condition =
        Maths.floatEquals(expectedIncrement, mockBehavior.getPointf().x)
            && Maths.floatEquals(expectedIncrement, mockBehavior.getPointf().y);
    assertTrue(condition, "After updating, the behavior's Pointf should have incremented.");
  }

  @Test
  void checkDestroyBehaviors_shouldMakePointfNull() {
    GameObject gameObject = new MockGameObject();
    MockBehavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();

    gameObject.addBehavior(mockBehavior, mockScene);
    gameObject.initBehaviors(); // pointf is not null here
    assertNotNull(mockBehavior.getPointf());

    gameObject.destroyAllBehaviors(); // pointf is null here
    assertNull(
        mockBehavior.getPointf(),
        "After destroying the GameObject's behaviors, the Pointf should be null.");
  }

  @Test
  void tryUpdateBehaviorWithoutInitializing_shouldThrowNullPointerException() {
    GameObject gameObject = new MockGameObject();
    MockBehavior mockBehavior = new MockBehavior();
    Scene mockScene = new MockEmptyScene();

    gameObject.addBehavior(mockBehavior, mockScene);

    assertThrows(
        NullPointerException.class,
        gameObject::updateBehaviors,
        "Trying to update the behavior without initializing it should throw a null pointer exception on the Pointf.");
  }
}
