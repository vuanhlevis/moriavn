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
        this.velocity.y +=  Player.gravity;
        PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.width, boxCollider.height, Brick.class);
        if (body != null) {
            float detalY = Mathx.sign(velocity.y);
            while (Physics.bodyInRect(position.add(0, detalY), boxCollider.width, boxCollider.height, Brick.class) == null) {
                position.addUp(0, detalY);
            }

            this.velocity.y = 0;
        }
        this.position.addUp(velocity);
        moveHorizontal();


    }

    private void moveHorizontal() {
        if (isActive) {
            PhysicsBody body = Physics.bodyInRect(position.add(velocity.x, 0), boxCollider.width, boxCollider.height, Brick.class);
            if (body != null) {
                float deltaX = Mathx.sign(velocity.x);
                while (Physics.bodyInRect(position.add(deltaX, 0), boxCollider.width, boxCollider.height, Brick.class) == null) {
                    position.addUp(deltaX, 0);
                }
                this.velocity.x = 0;
            }
        }
    }


    private void hitPlayer() {
        Player player = Physics.bodyInRect(this.boxCollider,Player.class);
        if (player!= null && player.getBoxCollider().screenPosition.y + 15 < this.boxCollider.screenPosition.y
                && player.alive && player.screenPosition.x - this.boxCollider.screenPosition.x < 30) {
            this.isActive = false;
//
            player.velocity.set(0,-15);

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
