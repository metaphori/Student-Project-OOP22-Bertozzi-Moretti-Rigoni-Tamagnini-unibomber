package it.unibo.unibomber.utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import static java.util.logging.Level.SEVERE;

import javax.imageio.ImageIO;

/**
 * Upload resources class.
 */
public final class UploadRes {
    /**
     * UploadRes constructor.
     */
    private UploadRes() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param fileName
     * @return Bufferd image from name of sprite.
     */
    public static BufferedImage getSpriteAtlas(final String fileName) {
        BufferedImage img = null;
        InputStream inputStream;
        final Logger logger = Logger.getLogger(UploadRes.class.getName());
        try {
            //System.out.println(UploadRes.class.getResource("/it/unibo/"));
            inputStream = UploadRes.class.getResourceAsStream("/it/unibo/sprites/" + fileName);
            img = ImageIO.read(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.log(SEVERE, e.getMessage());
        }
        return img;
    }
}
