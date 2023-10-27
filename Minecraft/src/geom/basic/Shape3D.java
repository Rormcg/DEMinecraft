package geom.basic;

import java.awt.Graphics;

public abstract class Shape3D {
   private RVector3D[] points;
   private int[][] lines;
   private Face[] faces;
   
   Shape3D() {
      points = null;
   }
   
   Shape3D(RVector3D[] nodes) {
      points = nodes;
   }
   
   public abstract void draw(Graphics g);
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