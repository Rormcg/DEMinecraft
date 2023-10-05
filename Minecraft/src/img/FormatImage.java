package img;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FormatImage {
	
	public static BufferedImage format(Image i) {
		BufferedImage img = i.img;
		
		int[][] pixels = new int[img.getHeight()][img.getWidth()];
		
		for(int r = 0; r < img.getHeight(); r++) {
			for(int c = 0; c < img.getWidth(); c++) {
				pixels[r][c] = img.getRGB(r, c);
			}
		}
		
		//Format pixels array
		//For empty pixels, use Color.TRANSLUCENT
		
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for(int r = 0; r < img2.getHeight(); r++) {
			for(int c = 0; c < img2.getWidth(); c++) {
				img2.setRGB(r, c, pixels[r][c]);
			}
		}
		
		return img2;
	}

}
