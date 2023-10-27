package img;

//import geom.*;
import geom.basic.Face;
import geom.basic.RVector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FormatImage {
	
	
	//Must be a parallelogram
	public static BufferedImage format(BufferedImage img, Face f) {
		RVector[] points = (RVector[])f.getPoints();

		//init values to determine the min/max values of the formatted image to determine the dimensions
		double startY = Double.MAX_VALUE;
		double endY = -Double.MAX_VALUE;
		double startX = Double.MAX_VALUE;
		double endX = -Double.MAX_VALUE;
		for(int i = 0; i < points.length; i ++) {
			if(points[i].getX() < startX) startX = points[i].getX();
			if(points[i].getX() > endX) endX = points[i].getX();
			if(points[i].getY() < startY) startY = points[i].getY();
			if(points[i].getY() > endY) endY = points[i].getY();
		}

		BufferedImage img2 = new BufferedImage((int)(endX - startX), (int)(endY - startY), img.getType());

		Graphics2D g2D = img2.createGraphics();

		AffineTransform a = new AffineTransform();
		
		//set the scaling required to go from img to img2
		g2D.scale(points[0].distance(points[1]) / img.getWidth(), points[1].distance(points[2]) / img.getHeight());

		//find the x and y shear factors

		//find the intersection between points[3, 2] and the line perpendicular to points[0, 1] that also intersects points[0]
		//this will find the point that points[3] that should be in if there was no shear transformation
		RVector temp = RVector.solutionPointSlope(points[3].slope(points[2]), points[3].getX(), points[3].getY(), points[0].perpendicularSlope(points[1]), points[0].getX(), points[0].getY());
		//find the difference between that intersection and the actual position of points[3], then represent that as a factor of the width of img2
		double shearX = points[3].distance(temp) / points[3].distance(points[2]);

		//repeat for the y shear factor
		temp = RVector.solutionPointSlope(points[1].slope(points[2]), points[1].getX(), points[1].getY(), points[0].perpendicularSlope(points[3]), points[0].getX(), points[0].getY());
		double shearY = points[1].distance(temp) / points[1].distance(points[2]);


		//set the shear(x and y distortion) to go from img to img2
		a.setToShear(shearX, shearY);
		g2D.transform(a);

		//set the rotation required to go from img to img2
		a.setToRotation(new RVector(img.getWidth(), img.getHeight()).findRotationTo(points[0].sub(points[2])), img.getWidth(), img.getHeight());
		g2D.transform(a);

		g2D.drawImage(img, 0, 0, null);
		g2D.setColor(Color.CYAN);
		g2D.drawRect(0, 0, 10, 10);
		g2D.dispose();

		return img2;

		/*if(points.length != 4) throw new IllegalArgumentException("RVector[] points must be length of 4");
		
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
		if(temp[3].getX() > temp[2].getX()) {
			RVector h = temp[2];
			temp[2] = temp[3];
			temp[3] = h;
		}
		
		points = temp;
		for(int j = 0; j < points.length; j++) {
			System.out.println(points[j]);
		}
		
		//init values to determine the edges of the formatted image
		double startY = points[0].getY() > points[1].getY() ? points[1].getY() : points[0].getY();
		double endY = points[3].getY() > points[2].getY() ? points[3].getY() : points[2].getY();
		double startX = points[0].getX() > points[3].getX() ? points[3].getX() : points[0].getX();
		double endX = points[1].getX() > points[2].getX() ? points[1].getX() : points[2].getX();
		
		System.out.println(startX + "\n" + startY + "\n" + endX + "\n" + endY);

		BufferedImage img = i.img;
		BufferedImage img2 = new BufferedImage((int)(endX - startX), (int)(endY - startY), BufferedImage.TYPE_INT_RGB);
		
		System.out.println(img2.getWidth() + ", " + img2.getHeight());

		//Format img pixels into img2
		//For empty pixels, use Color.TRANSLUCENT
		for(int r = 0; r < img2.getHeight(); r ++) {
			for(int c = 0; c < img2.getWidth(); c ++) {
				//check for (r, c) is in the quad
				if(f.containsPoint(new RVector(c + startX, r + startY))) {
					//System.out.println();
					int tempR = (int)((RVector.distance(new RVector(c, r), RVector.solutionPointSlope(points[0].slope(points[3]), c, r, points[0].slope(points[1]), points[0].getX() - startX, points[0].getY() - startY)) / RVector.distance(points[0], points[3])) * img.getHeight());
					int tempC = (int)((RVector.distance(new RVector(c, r), RVector.solutionPointSlope(points[0].slope(points[1]), c, r, points[0].slope(points[3]), points[0].getX() - startX, points[0].getY() - startY)) / RVector.distance(points[0], points[1])) * img.getWidth());
					
					//System.out.println(c + " " + r + ": " + tempC + ", " + tempR + ": " + img.getWidth() + ", " + img.getHeight());
					//System.out.println(RVector.solutionPointSlope(points[0].slope(points[3]), r, c, points[0].slope(points[1]), points[0].getX(), points[0].getY()));// / RVector.distance(points[0], points[3])) * img.getHeight());
					//System.out.println(points[0].slope(points[3]));
					
					try{
					img2.setRGB(c, r, img.getRGB(tempC, tempR));
					} catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("Out of Bounds!");
						//img2.setRGB(r, c, Color.YELLOW.getRGB());
					}
				} else {
					//img2.setRGB(c, r, Color.TRANSLUCENT);
					img2.setRGB(c, r, Color.YELLOW.getRGB());
				}
			}
		}
		
		return img2;*/
	}

	//Must be a parallelogram
	public static BufferedImage format(BufferedImage img, RVector[] points) {
		return format(img, new Face(points));
	}
	

}
