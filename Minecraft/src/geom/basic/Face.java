/**
 * Represents a face of a Shape3D
 */
package geom.basic;

import img.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;

import entities.Camera;

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
		Graphics2D g2D = (Graphics2D) g;
		if(this.image == null) {			
			g.setColor(color);
			g.fillPolygon(xIntPoints(), yIntPoints(), points.length);
		} else {
			//g.drawImage(FormatImage.format(image.img, this), 0, 0, null);
			drawImage(g2D);
		}
		
	    g.setColor(Color.RED);
	    //System.out.println("H");
	    //g.fillOval(10, 10, 10, 10);
        
        g.drawPolygon(xIntPoints(), yIntPoints(), points.length);
		
	}
	
	public void draw(Graphics g, Camera c) {
		Graphics2D g2D = (Graphics2D) g;
		if(this.image == null) {			
			g.setColor(color);
			g.fillPolygon(xIntPointsRelativeTo(c), yIntPointsRelativeTo(c), points.length);
		} else {
			//g.drawImage(FormatImage.format(image.img, this), 0, 0, null);
			drawImage(g2D);
		}
		
	    g.setColor(Color.RED);
	    //System.out.println("H");
	    //g.fillOval(10, 10, 10, 10);
        
        g.drawPolygon(xIntPointsRelativeTo(c), yIntPointsRelativeTo(c), points.length);
	}
	
	//Must be a parallelogram
	public void drawImage(BufferedImage img, Graphics2D g) {
		
		Polygon p = new Polygon(xIntPoints(), yIntPoints(), points.length);
		
		 // Create a transformation for mapping the image to the face
        Rectangle bounds = p.getBounds();
        double scaleX = bounds.getWidth() / image.img.getWidth();
        double scaleY = bounds.getHeight() / image.img.getHeight();

        // Transform and draw the image to fit the polygon bounds
        AffineTransform transform = new AffineTransform();
        transform.translate(bounds.getX(), bounds.getY());
        transform.scale(scaleX, scaleY);
        
        /*
        Point[] src = {
        		
        }
        
        Point[] dest = {
        		
        }
        
        AffineTransform transform = createTransform(src, dest);
         */
        
        g.setClip(p);
        g.drawImage(image.img, transform, null);
        g.setClip(null);
	}
	
	public void drawImage(Graphics2D g) {
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
	
	public double[] xPoints() {
		double[] p = new double[points.length];
		for(int i = 0; i < p.length; i++) {
			p[i] = points[i].getX();
		}
		return p;
	}
	
	public double[] yPoints() {
		double[] p = new double[points.length];
		for(int i = 0; i < p.length; i++) {
			p[i] = points[i].getY();
		}
		return p;
	}
	
	public int[] xIntPoints() {
		int[] p = new int[points.length];
		for(int i = 0; i < p.length; i++) {
			p[i] = (int)points[i].getX();
		}
		return p;
	}
	
	public int[] yIntPoints() {
		int[] p = new int[points.length];
		for(int i = 0; i < p.length; i++) {
			p[i] = (int)points[i].getY();
		}
		return p;
	}
	
	public int[] xIntPointsRelativeTo(Camera c) {
		int[] p = new int[points.length];
		for(int i = 0; i < p.length; i++) {
			p[i] = (int)(points[i].getX() - c.getPos().getX());
			RVector3D temp = new RVector3D(p[i], 0, 0);
			RVector rotation = c.getAim().rotation();
			temp.rotate(rotation.getX(), rotation.getY(), 0);
			p[i] = (int)temp.getX();
		}
		return p;
	}
	
	public int[] yIntPointsRelativeTo(Camera c) {
		int[] p = new int[points.length];
		for(int i = 0; i < p.length; i++) {
			p[i] = (int)(points[i].getY() - c.getPos().getY());
			RVector3D temp = new RVector3D(0, p[i], 0);
			RVector rotation = c.getAim().rotation();
			temp.rotate(rotation.getX(), rotation.getY(), 0);
			p[i] = (int)temp.getY();
		}
		return p;
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
