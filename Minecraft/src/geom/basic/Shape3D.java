package geom.basic;

import java.awt.Graphics;

import entities.Camera;

public abstract class Shape3D {
   private RVector3D[] points;
   private int[][] lines;
   private Face[] faces;
   private Camera camera;
   private RVector3D vel;
   
   Shape3D() {
      points = null;
      lines = null;
      faces = null;
      camera = null;
      vel = null;
   }
   
   Shape3D(RVector3D[] nodes) {
      points = nodes;
      lines = null;
      faces = null;
      camera = null;
      vel = null;
   }
   
   public abstract void draw(Graphics g);
   public void draw(Graphics g, Camera c) {
	   draw(g);
   }
   public abstract void update();
   
   //public abstract boolean collidedWith(Shape3D other);
   
   public boolean inFrontOfCamera() {
	   return true;
   }
   
   public RVector3D[] getPoints() {
      return points;
   }
   
   public RVector3D getPoint(int i) {
	   return points[i];
   }
   
   public void setPoints(RVector3D[] a) {
      points = a;
   }
   
   public void setLines(int[][] a) {
      lines = a;
   }
   
   public void setCamera(Camera c) {
	   camera = c;
   }
   
   public void setVel(RVector3D r) {
	   vel = r;
   }
   
   public Camera getCamera() {
	   return camera;
   }
   
   public int[][] getLines() {
      return lines;
   }
   
   public Face[] getFaces() {
	   return faces;
   }
   
   public RVector3D getVel() {
	   return vel;
   }
   
   public void setFaces(Face[] f) {
	   faces = f;
   }
   
   public void moveBy(RVector3D r) {
	   for(RVector3D p: points) {
		   p.add(r);
	   }
   }
   
   public void rotate(double x, double y, double z) {
      for(int i = 0; i < points.length; i++) {
         points[i].rotate(x, y, z);
      }
   }
   
   public void rotate(double x, double y, double z, RVector3D anchor) {
	      for(int i = 0; i < points.length; i++) {
	         points[i].rotate(x, y, z, anchor);
	      }
	   }
}