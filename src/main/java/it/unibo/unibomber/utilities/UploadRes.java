package it.unibo.unibomber.utilities;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
  try {
   inputStream = new FileInputStream("./src/main/res/" + fileName);
   try {
    img = ImageIO.read(inputStream);

   } catch (IOException e) {
    e.printStackTrace();
   } finally {
    try {
     inputStream.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  }
  return img;
 }
}
