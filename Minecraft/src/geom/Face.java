/**
 * Represents a face of a Shape3D
 */
package geom;

import java.awt.Graphics;
import java.awt.Color;

//import java.util.ArrayList;

public class Face implements Comparable<Face> {
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
