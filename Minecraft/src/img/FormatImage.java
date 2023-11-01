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
		//BufferedImage img2 = new BufferedImage(500, 500, img.getType());
		
		/*for(int i = 0; i < img2.getHeight(); i++) {
			for(int j = 0; j < img2.getWidth(); j++) {
				img2.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
			}
		}*/
		
		Graphics2D g2D = img2.createGraphics();
		//g2D.setBackground(new Color(255, 0, 0, 0));
		
		//create the transformation for upscaling/downscaling the image
		RVector scaleFactor = new RVector(points[0].distance(points[1]) / img.getWidth(),
										points[1].distance(points[2]) / img.getHeight());
		RVector newDimensions = new RVector((img.getWidth() * scaleFactor.getX()) , (img.getHeight() * scaleFactor.getY()));
		//AffineTransform scale = AffineTransform.getScaleInstance(scaleFactor.getX(), scaleFactor.getY());
		
		//set the rotation required to go from img to img2
		// TO-DO: check all are using radians/degrees
		System.out.println((img2.getWidth()) + " " + img2.getWidth() + " " +
							newDimensions.getX() + " " + newDimensions.getY());
		//RVector translation = new RVector(img2.getWidth() - (img.getWidth() * scaleFactor.getX()),
		//								img2.getHeight() - (img.getHeight() * scaleFactor.getY()));
		RVector translation = new RVector(img2.getWidth() / 2.0, img2.getHeight() / 2.0);//img2.getWidth() - img.getWidth() * scaleFactor.getX(), img2.getHeight() - img.getHeight() * scaleFactor.getY());
		System.out.println(translation);
		AffineTransform translate = AffineTransform.getTranslateInstance(translation.getX(), translation.getY());
		
		double rotation = new RVector(img.getWidth(), img.getHeight()).findRotationTo(RVector.sub(points[2], points[0]));
		System.out.println(rotation);
		//RVector anchor = new RVector(img2.getWidth() / 2.0, img2.getHeight() / 2.0);
		RVector anchor = new RVector(newDimensions.getX() / 2.0, newDimensions.getY() / 2.0);
		//anchor = new RVector();
		AffineTransform rotate = AffineTransform.getRotateInstance(rotation, anchor.getX(), anchor.getY());
		//System.out.println(rotation);
		//System.out.println(points[1].sub(points[0]).radians());
		//System.out.println(new RVector(img.getWidth(), img.getHeight()));
		//System.out.println(points[2].sub(points[0]));
		//AffineTransform rotate = AffineTransform.getRotateInstance(Math.PI/4, 400,-200);
		
		//find the x and y shear factors:
		
		//rotate points around the anchor to account for rotation
		RVector anchor2 = new RVector(img2.getWidth() / 2.0, img2.getHeight() / 2.0);
		RVector point1 = RVector.rotate(points[1], -rotation, anchor2);

		RVector point3 = RVector.rotate(points[3], -rotation, anchor2);
		RVector point2 = RVector.rotate(points[2], -rotation, anchor2);
		double shearX = -0.7;//1/point3.slope(point2);

		//repeat for the y shear factor
		RVector point0 = RVector.rotate(points[0], -rotation, anchor2);
		double shearY =  -0.35;//1/point0.slope(point3);
		System.out.println(point3.slope(point2) + " " + point0.slope(point3));
		System.out.println(shearX + " " + shearY);
		
		//set the shear(x and y distortion) to go from img to img2
		AffineTransform shear = AffineTransform.getShearInstance(shearX, shearY);

		
		//combine the transformations
		AffineTransform a = new AffineTransform();
		//a.concatenate(scale);
		a.concatenate(rotate);
		a.concatenate(translate);
		//a.concatenate(shear);
		
		g2D.transform(a);

		//g2D.setColor(new Color(0, 0, 0, 0));
		//g2D.drawRect(0, 0, img2.getWidth(), img2.getHeight());
		//g2D.translate((int)(img.getWidth() / 2.0), (int)(img.getHeight() / 2.0));
		g2D.drawImage(img, (int)(-newDimensions.getX() / 2.0) , (int)(-newDimensions.getY() / 2.0), (int)newDimensions.getX(), (int)newDimensions.getY(), null);
		
		//g2D.setColor(Color.CYAN);
		//g2D.drawRect(0, 0, 10, 10);
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
