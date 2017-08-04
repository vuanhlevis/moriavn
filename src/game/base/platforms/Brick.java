package game.base.platforms;

import game.Utils;
import game.base.GameObject;
import game.base.physics.BoxCollider;
import game.base.physics.Physics;
import game.base.physics.PhysicsBody;
import game.base.renderer.ImageRenderer;

import javax.swing.*;

/**
 * Created by levua on 8/3/2017.
 */
public class Brick extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;

    public Brick() {
        super();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("brick/2.png"));
        this.boxCollider = new BoxCollider(30, 30);
        this.children.add(boxCollider);
    }


    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }


}
