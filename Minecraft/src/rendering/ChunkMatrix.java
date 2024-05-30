package rendering;

import java.awt.Graphics;
import java.util.Arrays;

import geom.basic.RVector3D;

public class ChunkMatrix {
	
	private Chunk[] chunks;
	
	public ChunkMatrix(Chunk[] c) {
		chunks = c;
	}
	
	public void draw(Graphics g) {
		Arrays.sort(chunks);
		for(Chunk c: chunks) {
			c.draw(g);
		}
	}
	
	public void rotate(double x, double y, double z) {
		for(Chunk c: chunks) {
			c.rotate(x,  y, z, Chunk.ORIGIN);
		}
	}
}
