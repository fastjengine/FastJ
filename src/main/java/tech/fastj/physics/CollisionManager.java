package tech.fastj.physics;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private List<Collider> colliders = new ArrayList<>();

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public boolean checkCollisions() {
        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                if (colliders.get(i).checkCollision(colliders.get(j))) {
                    System.out.println("Collision detected between collider " + i + " and collider " + j);
                    return true;
                }
            }
        }
        return false;
    }
}
