package loganleo.tank.entitiy;

import loganleo.tank.ConfigManager;
import loganleo.tank.Dir;
import loganleo.tank.ResourceManager;
import loganleo.tank.TankFrame;
import loganleo.tank.fireStrategy.FireStrategy;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x;
    private int y;
    private Dir dir;
    private boolean moving;
    private static final int SPEED = ConfigManager.TANK_SPEED;
    private boolean alive = true;
    // to generate random direction of enemies
    private Group group = Group.BAD;
    private static final int WIDTH = ResourceManager.goodTankD.getWidth();
    private static final int HEIGHT = ResourceManager.goodTankD.getHeight();
    private static final Random random = new Random();

    private static final float CHANGE_DIR_FREQUENCY = 0.03F;

    private Rectangle tankRect = new Rectangle();

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        this.tankRect.x = this.x;
        this.tankRect.y = this.y;
        this.tankRect.width = WIDTH;
        this.tankRect.height = HEIGHT;
    }

    public Tank() {
    }

    public Rectangle getTankRect() {
        return this.tankRect;
    }

    public void setTankRect(Rectangle tankRect) {
        this.tankRect = tankRect;
    }

    public static int getWidth() {
        return WIDTH;
    }
    public static int getHeight() {
        return HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTankDir(Dir dir) {
        this.dir = dir;
    }

    public int getY() {
        return y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getTankDir() {
        return dir;
    }

    public boolean isAlive() {
        return alive;
    }

    public void paint(Graphics g) {
        // draw tank
        switch (this.dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.tankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.tankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.tankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.tankD, x, y, null);
                break;
        }
        move();
    }

    private void randomDir() {
        if (this.group == Group.GOOD) {
            return;
        }
        if (random.nextInt(100) < (int) (CHANGE_DIR_FREQUENCY * 100)) {
            this.dir = Dir.values()[random.nextInt(4)];
        }
    }

    private void move() {
        // if not moving, do not move
        if (!moving) {
            return;
        }
        switch (dir) {
            case LEFT:
                this.x -= SPEED;
                break;
            case RIGHT:
                this.x += SPEED;
                break;
            case UP:
                this.y -= SPEED;
                break;
            case DOWN:
                this.y += SPEED;
                break;
            default:
                break;
        }


        this.randomDir();
        this.boundsCheck();
        // update rect;
        this.tankRect.x = this.x;
        this.tankRect.y = this.y;
    }

    private void boundsCheck() {
        boolean isMyTank = this.group == Group.GOOD;
        if (this.x < 0) {
            this.x = 0;
            if (!isMyTank) {
                this.dir = Dir.RIGHT;
            }
        }
        if (this.y < 0) {
            this.y = 0;
            if (!isMyTank) {
                this.dir = Dir.DOWN;
            }
        }
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) {
            this.x = TankFrame.GAME_WIDTH - Tank.WIDTH;
            if (!isMyTank) {
                this.dir = Dir.LEFT;
            }
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) {
            this.y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
            if (!isMyTank) {
                this.dir = Dir.UP;
            }
        }

    }

    public void fire(FireStrategy fireStrategy) {
        fireStrategy.fire(this);
    }

    public void die() {
        this.alive = false;
    }
}
