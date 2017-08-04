package game.base.physics;

import game.base.Vector2D;

import java.util.Vector;

/**
 * Created by levua on 8/3/2017.
 */
public class Physics {
    private static Vector<PhysicsBody> bodies = new Vector<>();

    public static void add(PhysicsBody body) {
        bodies.add(body);
    }

    public static <T extends PhysicsBody> T bodyInRect (BoxCollider boxCollider, Class<T> classz) {
        for (PhysicsBody body : bodies) {
            if (body.isActive() && body.getBoxCollider().collideWith(boxCollider)) {
                if (body.getClass() == classz) {
                    return (T) body;
                }
            }
        }
        return null;
    }

    public static <T extends PhysicsBody> T bodyInRect (Vector2D position, float width, float height, Class<T> classz ) {
        float left = position.x - width / 2;
        float right = position.x + width / 2;
        float top = position.y - height / 2;
        float bottom = position.y + height / 2;

        for (PhysicsBody body : bodies) {
            if (body.isActive() && body.getBoxCollider().collideWith(top,bottom,left,right)) {
                if (body.getClass() == classz) {
                    return (T) body;
                }
            }
        }
        return null;
    }

    public static void clear() {
        bodies.clear();
    }
}
