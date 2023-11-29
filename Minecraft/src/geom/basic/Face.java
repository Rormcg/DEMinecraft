/**
 * Represents a face of a Shape3D
 */
package geom.basic;

import img.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
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
		//TO DO: CHECK IF IMAGE IS REVERSED, IF SO, DO NOT DRAW

		
		//Define important values for the edges of the image
		RVector start = new RVector(Double.MAX_VALUE, Double.MAX_VALUE);
		RVector end = new RVector(-Double.MAX_VALUE, -Double.MAX_VALUE);
		for(int i = 0; i < points.length; i ++) {
			if(points[i].getX() < start.getX()) start.setX(points[i].getX());
			if(points[i].getX() > end.getX()) end.setX(points[i].getX());
			if(points[i].getY() < start.getY()) start.setY(points[i].getY());
			if(points[i].getY() > end.getY()) end.setY(points[i].getY());
		}
		
		//Do not draw if this image is squished to width/height of 0
		if(end.x - start.x <= 0.000001 || end.y - start.y <= 0.000001) {
			return;
		}
		
		//The image to be eventually drawn
		BufferedImage img2 = new BufferedImage((int)(end.getX() - start.getX()), (int)(end.getY() - start.getY()), BufferedImage.TYPE_INT_ARGB);//img.getType());
		
		//The Graphics to transform the image onto
		Graphics2D g2D = img2.createGraphics();
		
		//TRANSLATION//
		RVector translation = new RVector(img2.getWidth() / 2.0, img2.getHeight() / 2.0);//img2.getWidth() - img.getWidth() * scaleFactor.getX(), img2.getHeight() - img.getHeight() * scaleFactor.getY());
		//System.out.println(translation);
		
		
		//ROTATE//
		//*By multiples of 90 degrees*//
		
		//Create a new array of points that will be rotated with the transformations
		//and is translated to be in the top right corner of the graphics
		RVector[] newPoints = new RVector[points.length];
		for(int i = 0; i < points.length; i++) {
			//copy over the values from points, but they should be placed in the top right of the Graphics
			newPoints[i] = new RVector(points[i].x - start.x, points[i].y - start.y);
		}
		
		int rotations = 0;
		//Rotate until newPoints[0] is in the 4th quadrant
		while(newPoints[0].degrees() <= 180 && newPoints[0].degrees() > 270) {
			newPoints[0].rotate(-90);
			rotations++;
		}
		
		
		//SCALE//
		//determine the factor for scaling the image
		//RVector scaleFactor = new RVector(newPoints[0].distance(newPoints[1]) / img.getWidth(),
										//newPoints[1].distance(newPoints[2]) / img.getHeight());
		RVector newDimensions = new RVector(newPoints[0].distance(newPoints[1]) , newPoints[1].distance(newPoints[2]));
		//RVector newDimensions = new RVector(newPoints[1].x - newPoints[0].x, newPoints[2].y - newPoints[1].y);
		
		//SHEAR//
		RVector point2 = new RVector(newPoints[2]);
		RVector unshearedPoint2 = new RVector(newDimensions.x*0.5 + img2.getWidth()*0.5,newDimensions.y*0.5 + img2.getHeight()*0.5);
		System.out.println(point2 + " " + unshearedPoint2);
		/*for(RVector r : newPoints) {
			System.out.println(r);
		}*/
		double shearX = (point2.getX() - unshearedPoint2.getX()) / unshearedPoint2.getY();
		double shearY = (point2.getY() - unshearedPoint2.getY()) / unshearedPoint2.getX();
		System.out.println(shearX + " " + shearY);
		//set the shear(x and y distortion) to go from img to img2
		
		
		
		//Alter the matrix of Graphics
		g2D.translate(translation.getX(), translation.getY());
		g2D.shear(shearX, shearY);
		g2D.rotate(rotations*(Math.PI/2));
		
		//Draw the image onto the distorted Graphics matrix of img2
		
		//g2D.setColor(Color.RED);
		//g2D.fillOval(20, 20, 20, 20);
		
		g2D.drawImage(img, (int)(-newDimensions.getX() / 2.0), (int)(-newDimensions.getY() / 2.0), (int)newDimensions.getX(), (int)newDimensions.getY(), null);
		g2D.dispose();
		
		//now draw img2
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
