package game;

import game.base.GameObject;
import game.base.GameObjectPool;
import game.base.Settings;
import game.base.inputs.InputManager;
import game.base.platforms.Brick;
import game.base.platforms.Stone;
import game.base.renderer.ImageRenderer;
import game.enemy.Enemy;
import game.enemy.EnemyTurtle;
import game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Created by levua on 8/3/2017.
 */
public class GameWindow extends JFrame{

    BufferedImage backBufferImage;
    Graphics2D backBufferGraphics2D;

    InputManager inputManager = InputManager.instance;

    long lastTimeUpdate = -1;

    public GameWindow() {
        setupWindow();
        setupBackBuffer();
        setupInputs();
        addBrick();
        addEnemy();
        addPlayer();

        setupStartupScene();
        this.setVisible(true);
    }

    private void addEnemy() {
        Enemy enemy = GameObjectPool.recycle(Enemy.class);
        enemy.position.set(400, 150);
        EnemyTurtle enemyTurtle = GameObjectPool.recycle(EnemyTurtle.class);
        enemyTurtle.position.set(600,150);
    }



    private void addBrick() {
        for(int i = 0, platformX = 10; i < 20; i++, platformX += 30) {
            Brick brick = GameObjectPool.recycle(Brick.class);
            brick.position.set(platformX, 485);

        }

        addPlatform(130, Settings.GAMEPLAY_HEIGHT - 100);
        addPlatform(130, 300 - 30 * 2);
        addPlatform(60, 150);
        addPlatform(400, 400);

    }

    private void addPlatform(int x, int y) {
        Brick brick = new Brick();
        brick.position.set(x, y);
        GameObject.add(brick);
    }

    private void addPlayer() {
        Player player = new Player();
        player.position.set(20, 100);
        GameObject.add(player);
    }

    private void setupStartupScene() {

    }

    private void setupBackBuffer() {
        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics2D = (Graphics2D) backBufferImage.getGraphics();
    }

    private void setupInputs() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.keyReleased(e);
            }
        });
    }


    public void loop() {
        while (true) {

            if (lastTimeUpdate == -1) {
                lastTimeUpdate = System.currentTimeMillis();
            }

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTimeUpdate > 17) {

                lastTimeUpdate = currentTime;

                run();

                render();
            }
        }
    }

    private void run() {
        GameObject.runAll();
        GameObject.runAllActions();
//        SceneManager.instance.changeSceneIfNeeded();
    }

    private void render() {
        backBufferGraphics2D.setColor(Color.BLACK);
        backBufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        GameObject.renderAll(backBufferGraphics2D);

        Graphics2D g2d = (Graphics2D) this.getGraphics();
        g2d.drawImage(backBufferImage,0,0,null);
    }

    private void setupWindow() {
        this.setSize(Settings.GAMEPLAY_WIDTH, Settings.GAMEPLAY_HEIGHT);
        this.setResizable(false);
        this.setTitle("Platformer begin");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
