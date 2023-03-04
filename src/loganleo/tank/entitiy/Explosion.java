package loganleo.tank.entitiy;

import loganleo.tank.ResourceManager;

import java.awt.*;

public class Explosion {
    public static int WIDTH = ResourceManager.explosion[0].getWidth();
    public static int HEIGHT = ResourceManager.explosion[0].getHeight();

    private int x, y;
    private boolean alive = true;

    private int step = 0;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        if (step >= ResourceManager.explosion.length) {
            this.alive = false;
        }
        g.drawImage(ResourceManager.explosion[step++], x, y, null);
    }

    public boolean isAlive() {
        if (step >= ResourceManager.explosion.length) {
            this.alive = false;
        }
        return this.alive;
    }
}
