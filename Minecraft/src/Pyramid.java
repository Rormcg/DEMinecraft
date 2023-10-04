
import java.awt.Graphics;
import java.util.Arrays;
import java.awt.Color;

public class Pyramid extends Shape3D{
   
   Pyramid(RVector3D[] nodes) {
      super(nodes);
   }
   
   //draws from point [x,y,z], which is topmost (lowest y) point on the pyramid
   Pyramid(double x, double y, double z, double w, double h, double d) {
      RVector3D[] nodes = new RVector3D[5];
      
      nodes[0] = new RVector3D(x, y, z);
      nodes[1] = new RVector3D(x - w*0.5, y + h, z - d*0.5);
      nodes[2] = new RVector3D(x + w*0.5, y + h, z - d*0.5);
      nodes[3] = new RVector3D(x - w*0.5, y + h, z + d*0.5);
      nodes[4] = new RVector3D(x + w*0.5, y + h, z + d*0.5);
      
      int[][] lines = {
      {0, 1},
      {0, 2},
      {0, 3},
      {0, 4},
      {1, 2},
      {1, 3},
      {2, 4},
      {3, 4}};
      super.setLines(lines);
      super.setPoints(nodes);
      
      Face[] f = {
    	      new Face(nodes[0], nodes[1], nodes[2]),
    	      new Face(nodes[0], nodes[1], nodes[3]),
    	      new Face(nodes[0], nodes[3], nodes[4]),
    	      new Face(nodes[0], nodes[2], nodes[4]),
    	      new Face(nodes[1], nodes[2], nodes[4], nodes[3])};
      
      for(int i = 0; i < f.length; i++) {
    	  f[i].setColor(new Color((int)(Math.random() * 255 + 1), (int)(Math.random() * 255 + 1), (int)(Math.random() * 255 + 1)));
      }
      
      super.setFaces(f);
   }
   
   Pyramid(RVector3D pos, double w, double h, double d) {
      this(pos.getX(), pos.getY(), pos.getX(), w, h, d);
   }
   
   @Override
   public void draw(Graphics g) {
      RVector3D[] h = super.getPoints();
      
      /*
      for(int i = 0; i < getLines().length; i++) {
         g.drawLine((int)getPoints()[getLines()[i][0]].getX(), (int)getPoints()[getLines()[i][0]].getY(),
                    (int)getPoints()[getLines()[i][1]].getX(), (int)getPoints()[getLines()[i][1]].getY());
      }
      */
      
      Arrays.sort(getFaces()); //get the proper order for drawing each face (from the farthest back to the closest)
      for(int i = 0; i < getFaces().length; i++) {
    	  getFaces()[i].draw(g);
      }
   }
   
   @Override
   public void update() {
      rotate(1, 2, 0);
   }
}