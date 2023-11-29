/**
 * Represents a face of a Shape3D
 */
package geom.basic;

import img.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Color;

//import java.util.ArrayList;

public class Face implements Comparable<Face> {
	private RVector3D[] points;
	private Color color;
	private Img image;
	
	public Face() {
		points = null;
		color = Color.RED;
		image = null;
	}
	
	/*Face(RVector3D[] p) {
		points = p;
	}*/
	
	public Face(Img img, RVector3D... p) {
		points = p;
		color = Color.RED;
		image = img;
	}
	
	public Face(RVector3D... p) {
		points = p;
		color = Color.RED;
		image = null;
	}

	public Face(Img img, RVector... p) {
		points = new RVector3D[p.length];
		for(int i = 0; i < p.length; i++) {
			points[i] = new RVector3D(p[i].getX(), p[i].getY(), 0);
		}
		
		color = Color.RED;
		image = img;
	}
	
	public Face(RVector... p) {
		points = new RVector3D[p.length];
		for(int i = 0; i < p.length; i++) {
			points[i] = new RVector3D(p[i].getX(), p[i].getY(), 0);
		}
		
		color = Color.RED;
		image = null;
	}
	
	public void draw(Graphics g) {
		if(this.image == null) {
			int[] x = new int[points.length];
			int[] y = new int[points.length];
			for(int i = 0; i < points.length; i++) {
				x[i] = (int)points[i].getX();
				y[i] = (int)points[i].getY();
			}
			
			g.setColor(color);
			g.fillPolygon(x, y, points.length);
		} else {
			//g.drawImage(FormatImage.format(image.img, this), 0, 0, null);
			drawImage(g);
		}
		
	    g.setColor(Color.RED);

        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for(int i = 0; i < points.length; i++) {
           xPoints[i] = (int)points[i].getX();
           yPoints[i] = (int)points[i].getY();
        }
        g.drawPolygon(xPoints, yPoints, points.length);
		
	}
	
	//Must be a parallelogram
	public void drawImage(BufferedImage img, Graphics g) {
		//RVector[] points = (RVector[])f.getPoints();
		
		//for(int i = 0; i < points.length; i++) {
			//points[i] = (RVector3D)RVector.rotate(points[i], 90, new RVector((points[2].getX() - points[0].getX()) * 0.5, (points[2].getY() - points[0].getY()) * 0.5));
		//}
		
		//init values to determine the min/max values of the formatted image to determine the dimensions
		RVector start = new RVector(Double.MAX_VALUE, Double.MAX_VALUE);
		RVector end = new RVector(-Double.MAX_VALUE, -Double.MAX_VALUE);
		for(int i = 0; i < points.length; i ++) {
			if(points[i].getX() < start.getX()) start.setX(points[i].getX());
			if(points[i].getX() > end.getX()) end.setX(points[i].getX());
			if(points[i].getY() < start.getY()) start.setY(points[i].getY());
			if(points[i].getY() > end.getY()) end.setY(points[i].getY());
		}
		
		

		BufferedImage img2 = new BufferedImage((int)(end.getX() - start.getX()), (int)(end.getY() - start.getY()), BufferedImage.TYPE_INT_ARGB);//img.getType());
		//BufferedImage img2 = new BufferedImage(500, 500, img.getType());
		
		Graphics2D g2D = img2.createGraphics();
		
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
		//rotation = 0;
		//rotate points around the anchor to account for rotation
		//RVector anchor2 = new RVector(img2.getWidth() / 2.0, img2.getHeight() / 2.0);
		//RVector point1 = RVector.rotate(points[1], -rotation, anchor);

		//RVector point3 = RVector.rotate(points[3], -rotation, anchor);
		
		//temporary points array that has been rotated according to the previously found rotation
		//RVector[] tempPoints = new RVector[points.length];
		//for(int i = 0; i < tempPoints.length; i++) {
			//tempPoints[i] = RVector.rotate(points[i], rotation, RVector.midpoint(points[0], points[2]));
		//}
		
		
		//RVector point2 = RVector.rotate(RVector.sub(points[2], start), -rotation, anchor);
		//RVector unshearedPoint2 = RVector.rotate(new RVector(newDimensions.getX(), newDimensions.getY()), -rotation, anchor);
		RVector point2 = new RVector(points[2]);
		RVector unshearedPoint2 = end;//new RVector(img.getWidth(), img.getHeight());
		//System.out.println(point2 + " " + unshearedPoint2);
		double shearX = (point2.getX() - unshearedPoint2.getX()) / point2.getY();
		double shearY = 0;//(point2.getY() - unshearedPoint2.getY()) / point2.getX();
		
		//double shearX = point3.slope(point2);

		//repeat for the y shear factor
		//RVector point0 = RVector.rotate(points[0], -rotation, anchor);
		//RVector unshearedPoint0 = RVector.rotate(new RVector(0, 0), -rotation, anchor);
		//double shearY = (point0.getY() - unshearedPoint0.getY()) / point0.getX();
		//double shearY =  point0.slope(point3);
		//System.out.println(point3.slope(point2) + " " + point0.slope(point3));
		System.out.println(shearX + " " + shearY);
		
		//set the shear(x and y distortion) to go from img to img2
		AffineTransform shear = AffineTransform.getShearInstance(shearX, shearY);

		
		//combine the transformations
		AffineTransform a = new AffineTransform();
		//a.concatenate(scale);
		//a.concatenate(rotate);
		a.concatenate(translate);
		a.concatenate(shear);
		
		g2D.transform(a);

		//g2D.setColor(new Color(0, 0, 0, 0));
		//g2D.drawRect(0, 0, img2.getWidth(), img2.getHeight());
		//g2D.translate((int)(img.getWidth() / 2.0), (int)(img.getHeight() / 2.0));
		g2D.drawImage(img, (int)(-newDimensions.getX() / 2.0), (int)(-newDimensions.getY() / 2.0), (int)newDimensions.getX(), (int)newDimensions.getY(), null);
		
		//g2D.setColor(Color.CYAN);
		//g2D.drawRect(0, 0, 10, 10);
		g2D.dispose();

		g.drawImage(img2, (int)start.getX(), (int)start.getY(), null);
		//return img2;
	}
	
	public void drawImage(Graphics g) {
		drawImage(image.img, g);
	}
	
	public void update() {
		
	}
	
	/**
	 * Compares this to another Face
	 * this is greater than the other Face if this has the greater 'Z' value for its average Z value
	 * Essentially, compares which Face is the most "forward"/"closer to the screen" in order to 
	 * know which Face to draw first
	 * @param f Face to be compared against this
	 * @return negative if this is less than f, zero if the two are equal, positive if this is 
	 * greater than f
	 */
	@Override
	public int compareTo(Face f) {
		
		//if this 'mid Z' = f 'mid Z', then return 0, otherwise return the difference between the two
		return (Math.abs(calcMidZ(this) - calcMidZ(f)) > 0.0001) ? (int)(calcMidZ(this) - calcMidZ(f)) : 0;
		
		/*
		ArrayList<Double> a = new ArrayList<Double>(); //list of indices of previously used greatest points in this.points
		ArrayList<Double> b = new ArrayList<Double>(); //list of indices of previously used greatest points in f.points
		for(int i = 0; i < points.length && i < f.points.length; i++) {
			double thisBig = -Double.MAX_VALUE; //biggest z value in this
			double fBig = -Double.MAX_VALUE; //biggest z value in f
			for(int j = 0; j < points.length; j ++) {
				if(points[j].getZ() > thisBig && !a.contains(points[j].getZ())) {
					thisBig = points[j].getZ();
				}
			}
			for(int j = 0; j < f.points.length; j ++) {
				if(f.points[j].getZ() > fBig && !b.contains(f.points[j].getZ())) {
					fBig = f.points[j].getZ();
				}
			}
			//System.out.println(thisBig - fBig);
			if(Math.abs(thisBig - fBig) >= 0.0001) {
				//System.out.println((int)((thisBig - fBig)*1000));
				return (int)((thisBig - fBig)*1000);
			}
			a.add(thisBig);
			b.add(fBig);
		}
		
		return 0;
		*/
	}

	public boolean containsPoint(RVector p) {
		return Face.containsPoint(points, p);
	}

	//check if a 2D point falls within the 2D plane of this quadrilateral
	public static boolean containsPoint(RVector3D[] points, RVector p) {
		//if() System.out.println("H");
		return 
			((points[1].slope(points[2]) == Double.POSITIVE_INFINITY && p.getX() <= points[1].getX()) || 
			(points[1].slope(points[2]) > 0 && p.getY() - points[1].getY() >= points[1].slope(points[2]) * (p.getX() - points[1].getX())) ||
			(points[1].slope(points[2]) <= 0 && p.getY() - points[1].getY() <= points[1].slope(points[2]) * (p.getX() - points[1].getX()))) &&
		((points[0].slope(points[1]) == Double.POSITIVE_INFINITY && p.getX() <= points[0].getX()) || 
		(points[0].slope(points[1]) > 0 && p.getY() - points[0].getY() <= points[0].slope(points[1]) * (p.getX() - points[0].getX())) ||
		(points[0].slope(points[1]) <= 0 && p.getY() - points[0].getY() >= points[0].slope(points[1]) * (p.getX() - points[0].getX()))) &&
			((points[3].slope(points[0]) == Double.POSITIVE_INFINITY && p.getX() >= points[3].getX()) || 
			(points[3].slope(points[0]) <= 0 && p.getY() - points[3].getY() >= points[3].slope(points[0]) * (p.getX() - points[3].getX())) ||
			(points[3].slope(points[0]) > 0 && p.getY() - points[3].getY() <= points[3].slope(points[0]) * (p.getX() - points[3].getX()))) &&
		((points[2].slope(points[3]) == Double.POSITIVE_INFINITY && p.getX() >= points[2].getX()) || 
		(points[2].slope(points[3]) > 0 && p.getY() - points[2].getY() >= points[2].slope(points[3]) * (p.getX() - points[2].getX())) ||
		(points[2].slope(points[3]) <= 0 && p.getY() - points[2].getY() <= points[2].slope(points[3]) * (p.getX() - points[2].getX())));
	}
	
	//Returns the Z of the middle of the Face f
	private double calcMidZ(Face f) {
		int z = 0;
		
		for(int i = 0; i < f.points.length; i++) {
			z += f.points[i].getZ();
		}
		
		return z / f.points.length;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public RVector3D[] getPoints() {
		return points;
	}
	
	public void setImage(Img img) {
		image = img;
	}
	
	public void setPoints(RVector3D[] p) {
		points = p;
	}
	
	public String toString() {
		return "";
	}
	
}
