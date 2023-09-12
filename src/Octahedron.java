
import java.awt.Graphics;
import java.awt.Color;

public class Octahedron extends Shape3D{
   
   Octahedron(RVector3D[] nodes) {
      super(nodes);
   }
   
   //draws from point [x,y,z], which is topmost (lowest y) point on the Octahedron
   Octahedron(double x, double y, double z, double w, double h, double d) {
      RVector3D[] nodes = new RVector3D[6];
      
      nodes[0] = new RVector3D(x, y, z);
      nodes[1] = new RVector3D(x - w*0.5, y + h, z - d*0.5);
      nodes[2] = new RVector3D(x + w*0.5, y + h, z - d*0.5);
      nodes[3] = new RVector3D(x - w*0.5, y + h, z + d*0.5);
      nodes[4] = new RVector3D(x + w*0.5, y + h, z + d*0.5);
      nodes[5] = new RVector3D(x, y + 2*h, z);
      
      int[][] lines = {
      {0, 1},
      {0, 2},
      {0, 3},
      {0, 4},
      {1, 2},
      {1, 3},
      {2, 4},
      {3, 4},
      {5, 4},
      {5, 3},
      {5, 2},
      {5, 1},};
      super.setLines(lines);
      super.setPoints(nodes);
   }
   
   Octahedron(RVector3D pos, double w, double h, double d) {
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