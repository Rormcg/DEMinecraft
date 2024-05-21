package geom.basic;

import java.awt.Graphics;

import entities.Camera;

public abstract class Shape3D {
   private RVector3D[] points;
   private int[][] lines;
   private Face[] faces;
   private Camera camera;
   
   Shape3D() {
      points = null;
      lines = null;
      faces = null;
      camera = null;
   }
   
   Shape3D(RVector3D[] nodes) {
      points = nodes;
      lines = null;
      faces = null;
      camera = null;
   }
   
   public abstract void draw(Graphics g);
   public void draw(Graphics g, Camera c) {
	   draw(g);
   }
   public abstract void update();
   
   public RVector3D[] getPoints() {
      return points;
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
   
   public Camera getCamera() {
	   return camera;
   }
   
   public int[][] getLines() {
      return lines;
   }
   
   public Face[] getFaces() {
	   return faces;
   }
   
   public void setFaces(Face[] f) {
	   faces = f;
   }
   
   public void rotate(double x, double y, double z) {
      for(int i = 0; i < points.length; i++) {
         points[i].rotate(x, y, z);
      }
   }
}