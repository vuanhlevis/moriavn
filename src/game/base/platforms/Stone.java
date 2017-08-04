package game.base.platforms;

import com.sun.prism.PhongMaterial;
import game.Utils;
import game.base.GameObject;
import game.base.physics.BoxCollider;
import game.base.physics.PhysicsBody;
import game.base.renderer.ImageRenderer;

/**
 * Created by levua on 8/3/2017.
 */
public class Stone extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;

    public Stone() {
        super();
        this.boxCollider = new BoxCollider(30,30);
        this.renderer = new ImageRenderer(Utils.loadAssetImage("yellow_square.jpg"));
        this.children.add(boxCollider);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
