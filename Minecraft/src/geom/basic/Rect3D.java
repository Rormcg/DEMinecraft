package geom.basic;

import java.awt.Graphics;
import java.util.Arrays;

import entities.Camera;
import img.Img;

import java.awt.Color;

public class Rect3D extends Shape3D implements Comparable<Rect3D> {

	Rect3D(RVector3D[] nodes) {
		if(nodes == null || nodes.length != 8) throw new IllegalArgumentException("Rect3D should have 8 nodes");
		int[][] lines = {{ 0, 1 }, { 0, 2 }, { 0, 4 }, { 1, 3 }, { 1, 5 }, { 2, 3 }, { 2, 6 }, { 3, 7 }, { 4, 5 },
				{ 4, 6 }, { 6, 7 }, { 5, 7 } };
		super.setLines(lines);
		super.setPoints(nodes);

		Face[] f = { new Face(nodes[0], nodes[2], nodes[3], nodes[1]), new Face(nodes[4], nodes[0], nodes[1], nodes[5]),
				new Face(nodes[6], nodes[4], nodes[5], nodes[7]), new Face(nodes[5], nodes[1], nodes[3], nodes[7]),
				new Face(nodes[2], nodes[6], nodes[7], nodes[3]), new Face(nodes[4], nodes[0], nodes[2], nodes[6]) };

		for (int i = 0; i < f.length; i++) {
			f[i].setColor(new Color((int) (Math.random() * 255 + 1), (int) (Math.random() * 255 + 1),
					(int) (Math.random() * 255 + 1)));
		}

		super.setFaces(f);
	}

	// draws from point [x,y,z], which is the backmost, leftmost, topmost (lowest x,
	// lowest y, lowest z) node of the cube
	Rect3D(double x, double y, double z, double w, double h, double d) {
		RVector3D[] nodes = new RVector3D[8];

		nodes[0] = new RVector3D(x, y, z);
		nodes[1] = new RVector3D(x, y + h, z);
		nodes[2] = new RVector3D(x + w, y, z);
		nodes[3] = new RVector3D(x + w, y + h, z);
		nodes[4] = new RVector3D(x, y, z + d);
		nodes[5] = new RVector3D(x, y + h, z + d);
		nodes[6] = new RVector3D(x + w, y, z + d);
		nodes[7] = new RVector3D(x + w, y + h, z + d);

		int[][] lines = { { 0, 1 }, { 0, 2 }, { 0, 4 }, { 1, 3 }, { 1, 5 }, { 2, 3 }, { 2, 6 }, { 3, 7 }, { 4, 5 },
				{ 4, 6 }, { 6, 7 }, { 5, 7 } };
		super.setLines(lines);
		super.setPoints(nodes);

		Face[] f = { new Face(nodes[0], nodes[2], nodes[3], nodes[1]), new Face(nodes[4], nodes[0], nodes[1], nodes[5]),
				new Face(nodes[6], nodes[4], nodes[5], nodes[7]), new Face(nodes[5], nodes[1], nodes[3], nodes[7]),
				new Face(nodes[2], nodes[6], nodes[7], nodes[3]), new Face(nodes[4], nodes[0], nodes[2], nodes[6]) };

		for (int i = 0; i < f.length; i++) {
			f[i].setColor(new Color((int) (Math.random() * 255 + 1), (int) (Math.random() * 255 + 1),
					(int) (Math.random() * 255 + 1)));
		}

		super.setFaces(f);
	}

	Rect3D(RVector3D pos, double w, double h, double d) {
		this(pos.getX(), pos.getY(), pos.getZ(), w, h, d);
	}
	
	Rect3D(RVector3D[] nodes, Camera c) {
		this(nodes);
		super.setCamera(c);
	}

	Rect3D(double x, double y, double z, double w, double h, double d, Camera c) {
		this(x, y, z, w, h, d);
		super.setCamera(c);
	}

	Rect3D(RVector3D pos, double w, double h, double d, Camera c) {
		this(pos.getX(), pos.getY(), pos.getZ(), w, h, d);
		super.setCamera(c);
	}
	
	

	@Override
	public void draw(Graphics g) {
		/*
		 * RVector3D[] h = super.getPoints();
		 * 
		 * for(int i = 0; i < getLines().length; i++) {
		 * g.drawLine((int)getPoints()[getLines()[i][0]].getX(),
		 * (int)getPoints()[getLines()[i][0]].getY(),
		 * (int)getPoints()[getLines()[i][1]].getX(),
		 * (int)getPoints()[getLines()[i][1]].getY()); }
		 */
		Arrays.sort(getFaces()); // get the proper order for drawing each face (from the farthest back to the
									// closest)
		for (int i = 0; i < getFaces().length; i++) {
			getFaces()[i].draw(g);
		}
	}

	@Override
	public void draw(Graphics g, Camera c) {
		Arrays.sort(getFaces()); // get the proper order for drawing each face (from the farthest back to the closest)
		for (int i = 0; i < getFaces().length; i++) {
			getFaces()[i].draw(g, c);
		}
	}

	@Override
	public void update() {
		// rotate(1, 2, 0);
	}

	public void setImages(Img[] img) {
		for (int i = 0; i < getFaces().length; i++) {
			getFaces()[i].setImage(img[i]);
		}
	}
	
	/**
	 * Assumes the two rects are not rotated
	 * @param other The other rect to check for collisions with
	 * @return whether the two have collided(intersecting)
	 */
	public boolean collidedWith(Rect3D other) {
		RVector3D pos = getPoint(0);
		RVector3D opos = other.getPoint(0);
		
		return (pos.getX() + width() > opos.getX() && pos.getX() < opos.getX() + other.width()) &&
				(pos.getY() + height() > opos.getY() && pos.getY() < opos.getY() + other.height()) &&
				(pos.getZ() + depth() > opos.getZ() && pos.getZ() < opos.getZ() + other.depth());
	}
	
	/**
	 * Finds and returns the middle of the shape as an RVector3D
	 * @return the coordinates of the middle point of the shape
	 */
	public RVector3D midpoint() {
		return new RVector3D((getPoints()[0].getX() + getPoints()[7].getX()) / 2,
				(getPoints()[0].getY() + getPoints()[7].getY()) / 2,
				(getPoints()[0].getZ() + getPoints()[7].getZ()) / 2);
	}
	
	public double width() {
		return getPoint(2).getX() - getPoint(0).getX();
	}
	
	public double height() {
		return getPoint(1).getY() - getPoint(0).getY();
	}
	
	public double depth() {
		return getPoint(7).getZ() - getPoint(0).getY();
	}
	
	public boolean inFrontOfCamera() {
		
		
		return true;
	}
	
	@Override
	public int compareTo(Rect3D o) {
		
		//return (int)RVector3D.sub(midpoint(), o.midpoint()).signedMagnitude();
		return (int)(RVector3D.sub(RVector3D.sub(midpoint(), getCamera().getPos()),
				RVector3D.sub(o.midpoint(), getCamera().getPos())).signedMagnitude());
	}
}