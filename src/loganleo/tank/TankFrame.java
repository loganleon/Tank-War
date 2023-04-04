package loganleo.tank;

import loganleo.tank.entitiy.Bullet;
import loganleo.tank.entitiy.Explosion;
import loganleo.tank.entitiy.Tank;
import loganleo.tank.entitiy.Group;
import loganleo.tank.fireStrategy.DefaultFireStrategy;
import loganleo.tank.fireStrategy.FireStrategy;
import loganleo.tank.fireStrategy.FourDirFireStrategy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 960;
    private static final Random random = new Random();

    // one class, only one myTank
    private static final Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD);
    // use LinkedList, because we will frequently delete items from it. LinkedList delete has better Time Complexity
    public static final List<Bullet> bullets = new LinkedList<>();
    private static final List<Tank> enemies = new LinkedList<>();
    private static final List<Explosion> explosions = new LinkedList<>();


    private static final FireStrategy myFireStrategy = ConfigManager.getMyFireStrategy();
    private static final FireStrategy enemyStrategy = ConfigManager.getEnemyFireStrategy();

    private static final float ENEMY_SHOOT_PROBABILITY = ConfigManager.ENEMY_SHOOT_PROBABILITY;

    static {
        for (int i = 0; i < ConfigManager.ENEMY_COUNT; i++) {
            enemies.add(new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD){{
                this.setMoving(true);
            }});
        }

    }
    public TankFrame() {
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("Tank War");
        this.setVisible(true);
        // listen the input key of keyboard
        this.addKeyListener(new MyKeyListener());
        // enable quit by clicking quit icon
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("number of bullets:" + bullets.size(), 10, 60);
        g.drawString("number of enemies:" + enemies.size(), 10, 80);
        g.drawString("number of explosions:" + explosions.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);

        // check whether hit tanks
        for (Bullet bullet : bullets) {
            for (Tank enemy : enemies) {
                if (bullet.checkHit(enemy)) {
                    explosions.add(new Explosion(enemy.getX() + Tank.getWidth() / 2 - Explosion.WIDTH / 2, enemy.getY() + Tank.getHeight() / 2 - Explosion.HEIGHT / 2));
                }
            }
        }


        // can not use iterator, will cause trouble
        // draw bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            // if bullets fly out of the frame, delete it
            if (!b.isAlive()) {
                bullets.remove(b);
                continue;
            }
            b.paint(g);
        }
        // draw enemy tanks
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            if (!enemy.isAlive()) {
                enemies.remove(enemy);
                continue;
            }
            enemy.paint(g);
            // to randomly let enemies fire
            if (random.nextInt(100) > (int) (ENEMY_SHOOT_PROBABILITY * 100)) {
                enemy.fire(enemyStrategy);
            }
        }
        // draw explosion
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            if (!explosion.isAlive()) {
                explosions.remove(explosion);
                continue;
            }
            explosions.get(i).paint(g);
        }
    }

    // prevent the screen from flashing
    // generate an image in advance, and paint all elements on it. Then render the whole painting in one time
    // update called before paint
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        // background screen
        Graphics gOffScreen = offScreenImage.getGraphics();

        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        // draw tank and the bullets on the image we generated
        paint(gOffScreen);

        g.drawImage(offScreenImage, 0, 0, null);
    }

    private static class MyKeyListener extends KeyAdapter {

        boolean leftPressed = false;
        boolean upPressed = false;
        boolean rightPressed = false;
        boolean downPressed = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_UP:
                    upPressed = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    break;
                default:
                    break;
            }
            setTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    leftPressed = false;
                    break;
                case KeyEvent.VK_UP:
                    upPressed = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = false;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = false;
                    break;
                // after release control key, fire
                case KeyEvent.VK_CONTROL:
                    myTank.fire(myFireStrategy);
                default:

            }
            setTankDir();
        }

        private void setTankDir() {
            // set moving
            myTank.setMoving(true);
            // after key release, set tank not moving
            if (!leftPressed && !rightPressed && !upPressed && !downPressed) {
                myTank.setMoving(false);
                return;
            }
            if (leftPressed) {
                myTank.setTankDir(Dir.LEFT);
            }
            if (rightPressed) {
                myTank.setTankDir(Dir.RIGHT);
            }
            if (upPressed) {
                myTank.setTankDir(Dir.UP);
            }
            if (downPressed) {
                myTank.setTankDir(Dir.DOWN);
            }
        }
    }
}
