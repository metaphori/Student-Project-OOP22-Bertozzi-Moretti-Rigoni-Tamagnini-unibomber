package it.unibo.unibomber.utilities;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
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
        FileInputStream inputStream;
        final Logger logger = Logger.getLogger(UploadRes.class.getName());
        try {
            inputStream = new FileInputStream("./src/main/res/" + fileName);
            img = ImageIO.read(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.log(SEVERE, e.getMessage());
        }
        return img;
    }
}
