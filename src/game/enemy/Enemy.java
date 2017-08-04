package game.enemy;

import game.Utils;
import game.base.GameObject;
import game.base.Mathx;
import game.base.Vector2D;
import game.base.physics.BoxCollider;
import game.base.physics.Physics;
import game.base.physics.PhysicsBody;
import game.base.platforms.Brick;
import game.base.renderer.ImageRenderer;
import game.player.Player;


/**
 * Created by levua on 8/3/2017.
 */
public class Enemy extends GameObject implements PhysicsBody {
    Vector2D velocity;
    BoxCollider boxCollider;
    private float gravity = 2f;

    public Enemy() {
        super();
        this.boxCollider = new BoxCollider(30,30);
        this.renderer = new ImageRenderer(Utils.loadAssetImage("yellow_square.jpg"));
        this.children.add(boxCollider);
        this.velocity = new Vector2D();
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        hitPlayer();
        velocity.x = -1;
        this.velocity.y +=  gravity;
        PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.width, boxCollider.height, Brick.class);
        if (body != null) {
            float detalY = Mathx.sign(velocity.y);
            while (Physics.bodyInRect(position.add(0, detalY), boxCollider.width, boxCollider.height, Brick.class) == null) {
                position.addUp(0, detalY);
            }

            this.velocity.y = 0;
        }
        this.position.addUp(velocity);

    }

    private void hitPlayer() {
        Player player = Physics.bodyInRect(this.boxCollider,Player.class);
        if (player!= null && player.getBoxCollider().screenPosition.y + 15 < this.boxCollider.screenPosition.y
                && player.alive && player.screenPosition.x - this.boxCollider.screenPosition.x < 30) {
            this.isActive = false;
//
//            this.velocity.y = 0;
            player.velocity.set(0,-15);

            System.out.println(this.boxCollider);
            System.out.println(player.getBoxCollider());
        }
        else if (player!= null) {
            if (player.alive) {
                player.position.addUp(0,-30);
                player.velocity.set(0,-15);
                player.alive = false;

            }

        }
    }


    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
