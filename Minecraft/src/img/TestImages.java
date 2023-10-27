package img;

import geom.RVector;

import javax.swing.JFrame;
//import javax.swing.Timer;
import javax.swing.JComponent;
//import java.util.ArrayList;
//import javax.swing.JOptionPane;

import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TestImages extends JComponent implements ActionListener, MouseListener{
	
	//FIELDS
   private int screenWidth, screenHeight;
   private JFrame frame;
   
   TestImages() {
      screenWidth = 500;
      screenHeight = 500;
      
      frame = new JFrame("3D Shapes");
      Container content = frame.getContentPane();
      content.setBackground(Color.WHITE);
      content.add(this);
      
      //Timer t = new Timer(1, this);
      //t.start();
      
      frame.setSize(screenWidth+17, screenHeight+40);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.addMouseListener(this);
   }
   
   public void actionPerformed(ActionEvent e) {
      
      repaint();
   }
   
   public void paintComponent(Graphics g) {
      //Graphics2D g2 = (Graphics2D) g;
      
	   /*RVector points[] = {new RVector(50, 100),
							new RVector(100, 100),
							new RVector(100, 150),
							new RVector(50, 150)};
      int xPos = 50; //smallest x value in points
      int yPos = 100; //smallest y value in points*/

      /*RVector points[] = {new RVector(50, 100),
							new RVector(100, 90),
							new RVector(110, 140),
                     new RVector(60, 150)};
      int xPos = 50; //smallest x value in points
      int yPos = 90; //smallest y value in points*/

      RVector points[] = {new RVector(50, 90),
							new RVector(100, 100),
							new RVector(110, 150),
                     new RVector(60, 140)};
      int xPos = 50; //smallest x value in points
      int yPos = 90; //smallest y value in points

	   g.drawImage(FormatImage.format(Img.GRASS_SIDE.img, points), xPos, yPos, null);
	   
      g.setColor(Color.RED);

      int[] xPoints = new int[points.length];
      int[] yPoints = new int[points.length];
      for(int i = 0; i < points.length; i++) {
         xPoints[i] = (int)points[i].getX();
         yPoints[i] = (int)points[i].getY();
      }
      g.drawPolygon(xPoints, yPoints, points.length);
   }
   
   public void mousePressed(MouseEvent e) {    }

   public void mouseReleased(MouseEvent e) {    }

   public void mouseEntered(MouseEvent e) {
       
   }
   
   public void mouseExited(MouseEvent e) {
       
   }

   public void mouseClicked(MouseEvent e) {
       
   }		
	
	
}

class Run {
   public static void main(String[] args) {
      TestImages m = new TestImages();
   }
}