package entities;

import geom.basic.RVector3D;

public class Camera extends Entity {
	
	public Camera() {
		this(0, 0, 0);
	}
	
	public Camera(RVector3D p) {
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Camera(double x, double y, double z) {
		super(x, y, z);
	}
	
}
