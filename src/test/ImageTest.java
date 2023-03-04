import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTest {
    @Test
    public void test() throws IOException {

        BufferedImage image = ImageIO.read(new File("./src/loganleo/tank/images/GoodTank1.png"));
        Assert.assertNotNull(image);
        BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("loganleo/tank/images/GoodTank1.png"));
        Assert.assertNotNull(image);
        Assert.assertEquals(image2, image);
    }
}
