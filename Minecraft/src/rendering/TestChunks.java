package rendering;

import javax.swing.JFrame;
import javax.swing.Timer;

import entities.Camera;
import geom.basic.RVector3D;
import geom.block.Grass;

import javax.swing.JComponent;
//import java.util.ArrayList;
//import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
//import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TestChunks extends JComponent implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// FIELDS
	private int screenWidth, screenHeight;
	private JFrame frame;
	private Chunk[] chunks;
	private Camera camera;
	
	private int testCounter = 0;
	
	TestChunks() {
		screenWidth = 500;
		screenHeight = 500;
		
		chunks = new Chunk[]{new Chunk(0, 0)};
		camera = new Camera(0, -100, 0);
		camera.setAim(new RVector3D(0, 1, 5));
		
		frame = new JFrame("Chunks");
		Container content = frame.getContentPane();
		content.setBackground(Color.WHITE);
		content.add(this);

		Timer t = new Timer(50, this);
		t.start();
		
		frame.setSize(screenWidth + 17, screenHeight + 40);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(testCounter > 100) {
			camera.rotate(0, 1, 0);
			
			for(Chunk c: chunks) {
				c.rotate(15,  0,  7);
			}
			
			
			repaint();
		}
		testCounter ++;
		System.out.println(testCounter);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Chunk c: chunks) {
			c.draw(g);
		}
		
		
   }

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

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
		TestChunks m = new TestChunks();
	}
}