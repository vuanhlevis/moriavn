package game.player;

import game.Utils;
import game.base.GameObject;
import game.base.Mathx;
import game.base.Settings;
import game.base.Vector2D;
import game.base.inputs.InputManager;
import game.base.physics.BoxCollider;
import game.base.physics.Physics;
import game.base.physics.PhysicsBody;
import game.base.platforms.Brick;
import game.base.platforms.Stone;
import game.base.renderer.ImageRenderer;
import game.enemy.Enemy;

/**
 * Created by levua on 8/3/2017.
 */
public class Player extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    public boolean alive;

    public static final float gravity = 2f;
    public Vector2D velocity;
    public static Player instance = new Player();

//    private float skinWidth = 4;
//    private float skinHeight = 4;

    public Player() {
        super();
        this.alive = true;
        this.velocity = new Vector2D();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("green_square.png"));
        this.boxCollider = new BoxCollider(30, 30);
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.velocity.y += gravity;

        this.velocity.x = 0;
        System.out.println(this.boxCollider);

        if (InputManager.instance.leftPressed && alive) {
            if (this.position.x > 0)
                this.velocity.x -= 5;
            if (this.position.x <= Settings.GAMEPLAY_WIDTH/4)
            {
                GameObject.setAll(+5);

            }


        }

        if (InputManager.instance.rightPressed && alive) {
            if (this.position.x < Settings.GAMEPLAY_WIDTH)
            this.velocity.x += 5;
            if (this.position.x >= Settings.GAMEPLAY_WIDTH/2)
            {
                GameObject.setAll(-5);
            }

        }

        if (InputManager.instance.upPressed && alive) {
            if (Physics.bodyInRect(position.add(0, 1), boxCollider.width, boxCollider.height, Brick.class) != null)
                this.velocity.y -= 20;
        }

        moveHorizontal();
        this.position.x += velocity.x;

        moveVertical();
        this.position.y += velocity.y;

        hitEnemy();
    }

    private void hitEnemy() {
        if (alive) {
            PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.width, boxCollider.height, Enemy.class);
            if (body != null) {
                float detalY = Mathx.sign(velocity.y);
                while (Physics.bodyInRect(position.add(0, detalY), boxCollider.width, boxCollider.height, Enemy.class) == null) {
                    position.addUp(0, detalY);
                }

                this.velocity.y = 0;
            }
        }
    }


    private void moveHorizontal() {
        if (alive) {
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


    private void moveVertical() {
        if (alive) {
            PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.width, boxCollider.height, Brick.class);
            if (body != null) {
                float detalY = Mathx.sign(velocity.y);
                while (Physics.bodyInRect(position.add(0, detalY), boxCollider.width, boxCollider.height, Brick.class) == null) {
                    position.addUp(0, detalY);
                }

                this.velocity.y = 0;
            }
        }

    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
