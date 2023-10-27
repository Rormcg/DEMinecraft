/**
 * Represents a face of a Shape3D
 */
package geom.basic;

import img.*;

import java.awt.Graphics;
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
		/*int[] x = new int[points.length];
		int[] y = new int[points.length];
		for(int i = 0; i < points.length; i++) {
			x[i] = (int)points[i].getX();
			y[i] = (int)points[i].getY();
		}
		
		g.setColor(color);
		g.fillPolygon(x, y, points.length);*/
		g.drawImage(FormatImage.format(image.img, this), 0, 0, null);
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
	
	public void setPoints(RVector3D[] p) {
		points = p;
	}
	
	public String toString() {
		return "";
	}
	
}
