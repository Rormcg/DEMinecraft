/**
 * Represents a face of a Shape3D
 */

import java.awt.Graphics;
import java.awt.Color;

public class Face {
	private RVector3D[] points;
	
	Face() {
		points = null;
	}
	
	Face(RVector3D[] p) {
		points = p;
	}
	
	public void draw(Graphics g) {
		
	}
	
	public void update() {
		
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
