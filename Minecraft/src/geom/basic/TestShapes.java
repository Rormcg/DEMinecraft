package geom.basic;


import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JComponent;
//import java.util.ArrayList;
//import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TestShapes extends JComponent implements ActionListener, MouseListener{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
   //FIELDS
   private int screenWidth, screenHeight;
   private JFrame frame;
   private Shape3D[] shapes;
   
   TestShapes() {
      screenWidth = 500;
      screenHeight = 500;
      
      //shapes = new Shape3D[1];
      //shapes = new Shape3D[]{new Cube(-50, -50, -50, 100)};
      //shapes = new Shape3D[]{new Octahedron(0, -100, 0, 70, 100, 70)};
      shapes = new Shape3D[]{new Rect3D(-100, -50, -40, 200, 100, 80)};
      //shapes = new Shape3D[]{new Pyramid(-50, -50, -50, 100, 100, 100)};
      
      /*      
      for(int i = 0; i < shapes.length; i++) {
         shapes[i] = new Cube(-50, -50, -50, 100);
      }*/
      
      frame = new JFrame("3D Shapes");
      Container content = frame.getContentPane();
      content.setBackground(Color.WHITE);
      content.add(this);
      
      Timer t = new Timer(1, this);
      t.start();
      
      frame.setSize(screenWidth+17, screenHeight+40);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.addMouseListener(this);
   }
   
   public void actionPerformed(ActionEvent e) {
      for(int i = 0; i < shapes.length; i++) {
         shapes[i].update();
      }
      repaint();
   }
   
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      
      g.setColor(Color.GREEN);
      
      g2.translate(screenWidth / 2, screenHeight / 2);
      for(int i = 0; i < shapes.length; i++) {
         shapes[i].draw(g);
      }
      g2.translate(-screenWidth / 2, -screenHeight / 2);
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
		@SuppressWarnings("unused")
		TestShapes m = new TestShapes();
   }
}