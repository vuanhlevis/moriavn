package game.camera;

import game.GameWindow;
import game.base.GameObject;
import game.base.Settings;
import game.base.Vector2D;
import game.player.Player;

/**
 * Created by Admin on 8/4/2017.
 */
public class Camera extends GameObject {


    public Camera(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

}