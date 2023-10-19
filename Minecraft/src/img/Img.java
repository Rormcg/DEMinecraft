package img;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public enum Img {
	
	DIRT_SIDE("dirtSide.jpeg"),
	GRASS_SIDE("grassSide.jpg"),
	GRASS_TOP("grassTop.png");
	
	public BufferedImage img;
	
	Img(String filename) {
		File f = new File(String.format("Minecraft%sresources%s" + filename, File.separator));
		
		try {
			img = ImageIO.read(f);
		} catch(IOException e) {
			System.out.println("Failed to load image: " + filename);
		}
	}
}
