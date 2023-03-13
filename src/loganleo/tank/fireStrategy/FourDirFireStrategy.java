package loganleo.tank.fireStrategy;

import loganleo.tank.Dir;
import loganleo.tank.TankFrame;
import loganleo.tank.entitiy.Bullet;
import loganleo.tank.entitiy.Tank;

public class FourDirFireStrategy implements FireStrategy{

    private FourDirFireStrategy() {}

    private static class SingletonHolder {
        private static final FourDirFireStrategy INSTANCE = new FourDirFireStrategy();
    }

    public static FourDirFireStrategy getInstance() {
        return SingletonHolder.INSTANCE;
    }
    @Override
    public void fire(Tank t) {
        int bulletX = t.getX() + Tank.getWidth() / 2 - Bullet.getWidth() / 2;
        int bulletY = t.getY() + Tank.getWidth() / 2;
        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            TankFrame.bullets.add(new Bullet(bulletX, bulletY, dir, t.getGroup()));
        }
    }
}
