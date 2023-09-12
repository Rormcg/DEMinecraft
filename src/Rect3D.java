
import java.awt.Graphics;
import java.awt.Color;

public class Rect3D extends Shape3D{
   
   Rect3D(RVector3D[] nodes) {
      super(nodes);
   }
   
   //draws from point [x,y,z], which is the backmost, leftmost, topmost (lowest x, lowest y, lowest z) node of the cube
   Rect3D(double x, double y, double z, double w, double h, double d) {
      RVector3D[] nodes = new RVector3D[8];
      
      nodes[0] = new RVector3D(x, y, z);
      nodes[1] = new RVector3D(x, y + h, z);
      nodes[2] = new RVector3D(x + w, y, z);
      nodes[3] = new RVector3D(x + w, y + h, z);
      nodes[4] = new RVector3D(x, y, z + d);
      nodes[5] = new RVector3D(x, y + h, z + d);
      nodes[6] = new RVector3D(x + w, y, z + d);
      nodes[7] = new RVector3D(x + w, y + h, z + d);
      
      int[][] lines = {
      {0, 1},
      {0, 2},
      {0, 4},
      {1, 3},
      {1, 5},
      {2, 3},
      {2, 6},
      {3, 7},
      {4, 5},
      {4, 6},
      {6, 7},
      {5, 7}};
      super.setLines(lines);
      super.setPoints(nodes);
   }
   
   Rect3D(RVector3D pos, double w, double h, double d) {
      this(pos.getX(), pos.getY(), pos.getX(), w, h, d);
   }
   
   @Override
   public void draw(Graphics g) {
      RVector3D[] h = super.getPoints();
      
      for(int i = 0; i < getLines().length; i++) {
         g.drawLine((int)getPoints()[getLines()[i][0]].getX(), (int)getPoints()[getLines()[i][0]].getY(),
                    (int)getPoints()[getLines()[i][1]].getX(), (int)getPoints()[getLines()[i][1]].getY());
      }
   }
   
   @Override
   public void update() {
      rotate(1, 2, 0);
   }
}