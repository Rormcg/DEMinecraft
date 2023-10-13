package img;

import geom.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FormatImage {
	
	
	//Must be a convex quadrilateral
	public static BufferedImage format(Image i, RVector[] points) {
		if(points.length != 4) throw new IllegalArgumentException("RVector[] points must be length of 4");
		
		//Order the points so as to follow this pattern: 0   1
		//												 3   2
		
		RVector[] temp = new RVector[points.length]; //reordered version of points
		double greatestY1 = -Double.MAX_VALUE;
		double greatestY2 = -Double.MAX_VALUE;
		for(int j = 0; j < points.length; j++) {
			if(points[j].getY() >= greatestY2) {
				if(points[j].getY() > greatestY1) {
					greatestY2 = greatestY1;
					greatestY1 = points[j].getY();
					for(int x = 0; x < temp.length - 1; x++) {
						temp[x+1] = temp[x];
					}
					temp[0] = points[j];
				} else {
					greatestY2 = points[j].getY();
					for(int x = 1; x < temp.length - 1; x++) {
						temp[x+1] = temp[x];
					}
					temp[1] = points[j];
				}
			} else {
				temp[3] = temp[2];
				temp[2] = points[j];
			}
		}
		
		if(temp[0].getX() > temp[1].getX()) {
			RVector h = temp[0];
			temp[0] = temp[1];
			temp[1] = h;
		}
		if(temp[2].getX() < temp[3].getX()) {
			RVector h = temp[2];
			temp[2] = temp[3];
			temp[3] = h;
		}
		
		points = temp;
		
		//init values to determine the edges of the formatted image
		double startY = points[0].getY() > points[1].getY() ? points[1].getY() : points[0].getY();
		double endY = points[3].getY() > points[2].getY() ? points[3].getY() : points[2].getY();
		double startX = points[0].getX() > points[3].getX() ? points[3].getX() : points[0].getX();
		double endX = points[1].getX() > points[2].getX() ? points[1].getX() : points[2].getX();
		
		BufferedImage img = i.img;
		int[][] pixels = new int[img.getHeight()][img.getWidth()]; //fill with img's pixels
		int[][] newPixels = new int[(int)(endY - startY)][(int)(endX - startX)]; //fill with empty pixels
		
		for(int r = 0; r < img.getHeight(); r++) {
			for(int c = 0; c < img.getWidth(); c++) {
				pixels[r][c] = img.getRGB(r, c);
			}
		}
		
		for(int r = 0; r < newPixels[0].length; r++) {
			for(int c = 0; c < newPixels.length; c++) {
				newPixels[r][c] = Color.TRANSLUCENT;
			}
		}
		
		//Format pixels array into newPixels
		//For empty pixels, use Color.TRANSLUCENT
		for(int r = 0; r < (int)(endY - startY); r ++) {
			for(int c = 0; c < (int)(endX - startX); c ++) {
				//check for (r, c) is in the quad; use inequalities
				if(r - points[1].getY() <= points[1].slope(points[2]) * (c - points[1].getX()) &&
				r - points[0].getY() >= points[0].slope(points[1]) * (c - points[0].getX()) &&
				r - points[1].getY() >= points[1].slope(points[0]) * (c - points[1].getX()) &&
				r - points[2].getY() <= points[2].slope(points[3]) * (c - points[2].getX())) {
					
					
					//newPixels[r][c] = pixels[][];
				}
			}
		}
		
		BufferedImage img2 = new BufferedImage((int)(endX - startX), (int)(endY - startY), BufferedImage.TYPE_INT_RGB);
		
		for(int r = 0; r < img2.getHeight(); r++) {
			for(int c = 0; c < img2.getWidth(); c++) {
				img2.setRGB(r, c, pixels[r][c]);
			}
		}
		
		return img2;
	}

}
