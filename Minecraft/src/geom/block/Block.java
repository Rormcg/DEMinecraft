package geom.block;

import entities.Camera;
import geom.basic.Cube;
import geom.basic.RVector3D;
import img.Img;

public class Block extends Cube {
	
	public static double SIZE = 30;
	public Img[] images;
	
	public Block(RVector3D[] nodes) {
		super(nodes);
	}
	
	//will place the block at a location relative to Block.SIZE
	//x, y, z refer to the coordinates with respect to other blocks,
	//actual coordinates are derived from Blocks.SIZE and the relative coordinates together
	public Block(double x, double y, double z) {
		//super(x*SIZE, y*SIZE, z*SIZE, SIZE);
		super(x, y, z, SIZE);
	}

	public Block(RVector3D pos) {
		//super(RVector3D.mult(pos, SIZE), SIZE);
		super(pos, SIZE);
	}
	
	public Block(RVector3D[] nodes, Camera c) {
		super(nodes, c);
	}
	
	public Block(double x, double y, double z, Camera c) {
		super(x, y, z, SIZE, c);
	}

	public Block(RVector3D pos, Camera c) {
		super(pos, SIZE, c);
	}

}
