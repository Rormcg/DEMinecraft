
import java.awt.Graphics;
import java.awt.Color;

public class Cube extends Rect3D {
   
   Cube(RVector3D[] nodes) {
      super(nodes);
   }
   
   //draws from point [x,y,z], which is the backmost, leftmost, topmost (lowest x, lowest y, lowest z) node of the cube
   Cube(double x, double y, double z, double s) {
      super(x, y, z, s, s, s);
   }
   
   Cube(RVector3D pos, double s) {
      this(pos.getX(), pos.getY(), pos.getX(), s);
   }
}