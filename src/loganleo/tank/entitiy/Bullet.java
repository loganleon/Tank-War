package loganleo.tank.entitiy;

import loganleo.tank.ConfigManager;
import loganleo.tank.Dir;
import loganleo.tank.ResourceManager;
import loganleo.tank.TankFrame;

import java.awt.*;

public class Bullet {
    private static final int SPEED = ConfigManager.BULLET_SPEED;
    private static final int WIDTH = ResourceManager.bulletD.getWidth();
    private static final int HEIGHT = ResourceManager.bulletD.getHeight();
    private int x, y;
    private Dir dir;

    private boolean alive = true;
    private Group group = Group.BAD;

    // to prevent from initializing too many objects, share the same one
    private Rectangle bulletRect = new Rectangle();


    public Rectangle getBulletRect() {
        return this.bulletRect;
    }

    public void setBulletRect(Rectangle bulletRect) {
        this.bulletRect = bulletRect;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isAlive() {
        return alive;
    }
    public static int getWidth() {
        return WIDTH;
    }
    public static int getHeight() {
        return HEIGHT;
    }

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        this.bulletRect.x = this.x;
        this.bulletRect.y = this.y;
        this.bulletRect.width = WIDTH;
        this.bulletRect.height = HEIGHT;
    }

    public void paint(Graphics g) {
        switch (this.dir) {
            case LEFT:
                g.drawImage(ResourceManager.bulletL, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(ResourceManager.bulletU, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceManager.bulletR, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(ResourceManager.bulletD, this.x, this.y, null);
                break;
        }
        move();
    }
    private void move() {
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
        this.bulletRect.x = this.x;
        this.bulletRect.y = this.y;
        if (this.x < 0 || this.y < 0 || this.x > TankFrame.GAME_WIDTH || this.y > TankFrame.GAME_HEIGHT) {
            this.die();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean checkHit(Tank tank) {
        // if the same group, do not hit
        if (this.group == tank.getGroup()) {
            return false;
        }
        // if hit tank
        if (bulletRect.intersects(tank.getTankRect())) {
            this.die();
            tank.die();
            return true;
        }
        return false;
    }

    public void die() {
        this.alive = false;
    }
}
