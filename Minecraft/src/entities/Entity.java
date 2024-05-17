package entities;

import geom.basic.RVector3D;

public class Entity {
	private RVector3D pos;
	
	public Entity(double x, double y, double z) {
		pos = new RVector3D(x, y, z);
	}
	
	public Entity(RVector3D p) {
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Entity() {
		this(0, 0, 0);
	}
	
	public void moveTo(double x, double y, double z) {
		pos.moveTo(x, y, z);
	}
	
	public RVector3D getPos() {
		return pos;
	}
}
