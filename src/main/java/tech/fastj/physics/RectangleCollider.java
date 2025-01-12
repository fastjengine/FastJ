package tech.fastj.physics;

import java.awt.geom.Rectangle2D;


public class RectangleCollider extends Collider {
    private final Rectangle2D shape;

    public RectangleCollider(Rectangle2D shape) {
        this.shape = shape;
    }

    @Override
    public boolean checkCollision(Collider other) {
        if (other instanceof RectangleCollider) {
            RectangleCollider otherRectangle = (RectangleCollider) other;

            // Έλεγχος αν τα δύο ορθογώνια επικαλύπτονται
            return shape.intersects(otherRectangle.shape);
        }
        return false;
    }
}
