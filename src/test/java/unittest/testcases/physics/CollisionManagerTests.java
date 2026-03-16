package unittest.testcases.physics;

import org.junit.jupiter.api.Test;
import java.awt.geom.Rectangle2D;
import tech.fastj.physics.RectangleCollider;
import tech.fastj.physics.CollisionManager;

public class CollisionManagerTests {

    @Test
    void testCollisionDetection() {

        RectangleCollider collider1 = new RectangleCollider(new Rectangle2D.Double(0, 0, 50, 50));
        RectangleCollider collider2 = new RectangleCollider(new Rectangle2D.Double(25, 25, 50, 50));


        CollisionManager collisionManager = new CollisionManager();
        collisionManager.addCollider(collider1);
        collisionManager.addCollider(collider2);


        collisionManager.checkCollisions();
    }

    @Test
    void testNoCollision() {

        RectangleCollider collider1 = new RectangleCollider(new Rectangle2D.Double(0, 0, 50, 50));
        RectangleCollider collider2 = new RectangleCollider(new Rectangle2D.Double(100, 100, 50, 50));


        CollisionManager collisionManager = new CollisionManager();
        collisionManager.addCollider(collider1);
        collisionManager.addCollider(collider2);


        collisionManager.checkCollisions();
    }
}
