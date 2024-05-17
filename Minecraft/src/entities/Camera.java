package entities;

import geom.basic.RVector3D;

public class Camera extends Entity {
	private RVector3D aim;
	
	public Camera() {
		this(0, 0, 0);
	}
	
	public Camera(RVector3D p) {
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Camera(double x, double y, double z) {
		super(x, y, z);
		aim = new RVector3D(1, 0, 0);
	}
	
	public void rotate(double x, double y, double z) {
		aim.rotate(x, y, z);
	}
	
	public void rotate(RVector3D r) {
		aim.rotate(r);
	}
	
	public void setAim(RVector3D r) {
		aim = new RVector3D(r);
		aim.normalize();
	}
}
