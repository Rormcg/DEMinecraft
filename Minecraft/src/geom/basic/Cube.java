package geom.basic;

import entities.Camera;

public class Cube extends Rect3D {
   
   public Cube(RVector3D[] nodes) {
      super(nodes);
   }
   
   //draws from point [x,y,z], which is the backmost, leftmost, topmost (lowest x, lowest y, lowest z) node of the cube
   public Cube(double x, double y, double z, double s) {
      super(x, y, z, s, s, s);
   }
   
   public Cube(RVector3D pos, double s) {
      this(pos.getX(), pos.getY(), pos.getX(), s);
   }
   
   public Cube(RVector3D[] nodes, Camera c) {
	   super(nodes, c);
   }
   
   public Cube(double x, double y, double z, double s, Camera c) {
      super(x, y, z, s, s, s, c);
   }
   
   public Cube(RVector3D pos, double s, Camera c) {
      this(pos.getX(), pos.getY(), pos.getX(), s, c);
   }
}