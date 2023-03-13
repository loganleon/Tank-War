package loganleo.tank.fireStrategy;

import loganleo.tank.TankFrame;
import loganleo.tank.entitiy.Bullet;
import loganleo.tank.entitiy.Tank;

// make this class a Singleton, so that no more than on instance is initialized
public class DefaultFireStrategy implements FireStrategy{

    private DefaultFireStrategy(){}

    private static class SingletonHolder {
        private static final DefaultFireStrategy INSTANCE = new DefaultFireStrategy();
    }

    public static DefaultFireStrategy getInstance() {
        return SingletonHolder.INSTANCE;
    }


    @Override
    public void fire(Tank t) {
        int bulletX = t.getX() + Tank.getWidth() / 2 - Bullet.getWidth() / 2;
        int bulletY = t.getY() + Tank.getWidth() / 2;
        TankFrame.bullets.add( new Bullet(bulletX, bulletY, t.getTankDir(), t.getGroup()));
    }

}
