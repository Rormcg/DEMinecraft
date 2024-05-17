package entities;

import geom.basic.RVector3D;

public class Player extends Entity {
	private Camera camera;
	
	public Player(double x, double y, double z) {
		super(x, y, z);
		camera = new Camera(x, y, z);
	}
	
	public Player(RVector3D p) {
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Player() {
		this(0, 0, 0);
	}

}
