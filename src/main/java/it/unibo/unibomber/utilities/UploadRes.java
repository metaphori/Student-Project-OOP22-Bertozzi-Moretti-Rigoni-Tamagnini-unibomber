package it.unibo.unibomber.utilities;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UploadRes {
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("./src/main/res/"+fileName);
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
