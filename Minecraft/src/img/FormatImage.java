package img;

import geom.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FormatImage {
	
	
	//Must be a parallelogram
	public static BufferedImage format(Img i, RVector[] points) {
		if(points.length != 4) throw new IllegalArgumentException("RVector[] points must be length of 4");
		
		//Order the points so as to follow this pattern: 0   1
		//												 3   2
		
		RVector[] temp = new RVector[points.length]; //reordered version of points
		temp[0] = points[0];
		temp[1] = points[1];
		
		//put the two smallest y-values at temp[0] and temp[1] and the two greatest at temp[2] and temp[3]
		for(int j = 0; j < points.length; j++) {
			if(points[j].getY() < temp[1].getY()) {
				for(int x = 1; x < temp.length - 1; x++) {
					temp[x+1] = temp[x];
				}
				temp[1] = points[j];
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
		BufferedImage img2 = new BufferedImage((int)(endX - startX), (int)(endY - startY), BufferedImage.TYPE_INT_RGB);
		
		//Format img pixels into img2
		//For empty pixels, use Color.TRANSLUCENT
		//for(int r = 0; r < (int)(endY - startY); r ++) {
			//for(int c = 0; c < (int)(endX - startX); c ++) {
		for(int r = 0; r < img2.getHeight(); r ++) {
			for(int c = 0; c < img2.getWidth(); c ++) {
				//check for (r, c) is in the quad; use inequalities
				if(r - points[1].getY() <= points[1].slope(points[2]) * (c - points[1].getX()) &&
				r - points[0].getY() >= points[0].slope(points[1]) * (c - points[0].getX()) &&
				r - points[1].getY() >= points[1].slope(points[0]) * (c - points[1].getX()) &&
				r - points[2].getY() <= points[2].slope(points[3]) * (c - points[2].getX())) {
					
					int tempR = (int)(RVector.distance(new RVector(r, c), RVector.solutionPointSlope(points[0].slope(points[3]), r, c, points[0].slope(points[1]), points[0].getX(), points[0].getY())) / RVector.distance(points[0], points[3]) * img.getHeight());
					int tempC = (int)(RVector.distance(new RVector(r, c), RVector.solutionPointSlope(points[0].slope(points[1]), r, c, points[0].slope(points[3]), points[0].getX(), points[0].getY())) / RVector.distance(points[0], points[1]) * img.getWidth());
					img2.setRGB(r, c, img.getRGB(tempR, tempC));
					//newPixels[r][c] = pixels[tempR][tempC];
				}
			}
		}
		
		return img2;
	}

}
