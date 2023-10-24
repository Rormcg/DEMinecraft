package img;

import geom.RVector;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JComponent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

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
							new RVector(50, 150),
							new RVector(100, 150)};*/
      RVector points[] = {new RVector(50, 100),
							new RVector(100, 100),
							new RVector(70, 150),
							new RVector(120, 150)};
	   g.drawImage(FormatImage.format(Img.GRASS_SIDE, points), 50, 50, null);
	   
      g.setColor(Color.RED);
      g.drawRect(50, 50, 50, 50);
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