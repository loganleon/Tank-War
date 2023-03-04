package loganleo.tank.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    public static BufferedImage rotateImage(final BufferedImage image, final int degree) {
        int w = image.getWidth();
        int h = image.getHeight();

        int type = image.getColorModel().getTransparency();
        BufferedImage result = new BufferedImage(w, h, type);
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // rotate
        graphics2D.rotate(Math.toRadians(degree), (float) w / 2, (float) h / 2);
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        return result;
    }
}
