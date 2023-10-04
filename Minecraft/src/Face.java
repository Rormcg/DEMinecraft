/**
 * Represents a face of a Shape3D
 */

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class Face implements Comparable {
	private RVector3D[] points;
	private Color color;
	
	Face() {
		points = null;
		color = Color.RED;
	}
	
	/*Face(RVector3D[] p) {
		points = p;
	}*/
	
	Face(RVector3D... p) {
		points = p;
		color = Color.RED;
	}
	
	public void draw(Graphics g) {
		int[] x = new int[points.length];
		int[] y = new int[points.length];
		for(int i = 0; i < points.length; i++) {
			x[i] = (int)points[i].getX();
			y[i] = (int)points[i].getY();
		}
		
		g.setColor(color);
		g.fillPolygon(x, y, points.length);
	}
	
	public void update() {
		
	}
	
	/**
	 * Compares this to another Face
	 * this is greater than the other Face if this has the greatest 'Z' value among its points
	 * If both have the same greatest 'Z' value, check which has the next greatest 'Z' value, etc.
	 * @param o Face to be compared against this
	 * @return negative if this is less than o, zero if the two are equal, positive if this is 
	 * greater than o
	 */
	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Face)) throw new ClassCastException("Incompatible Types");
		Face f = (Face) o;
		
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
