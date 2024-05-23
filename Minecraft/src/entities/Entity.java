package entities;

import geom.basic.RVector3D;
import geom.basic.Rect3D;

public class Entity {
	private RVector3D pos;
	private RVector3D vel;
	private RVector3D acc;
	
	private RVector3D aim;
	private RVector3D aVel;
	
	private Rect3D collisionBox;
	
	
	public Entity(double x, double y, double z) {
		pos = new RVector3D(x, y, z);
		aim = new RVector3D(0, 1, 0);
		vel = new RVector3D(0, 0, 0);
		acc = new RVector3D (0, 0, 0);
		aVel = new RVector3D(0, 0, 0);
		
		collisionBox = new Rect3D(0, 0, 0, 0, 0, 0);
	}
	
	public Entity(RVector3D p) {
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Entity() {
		this(0, 0, 0);
	}
	
	public void update() {
		pos.add(vel);
		vel.add(acc);
	}
	
	public void moveTo(double x, double y, double z) {
		pos.moveTo(x, y, z);
	}
	
	public void move(double x, double y, double z) {
		moveTo(pos.getX() + x, pos.getY() +  y, pos.getZ() + z);
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
	
	public RVector3D getPos() {
		return pos;
	}
	
	public RVector3D getAim() {
		return aim;
	}
}
