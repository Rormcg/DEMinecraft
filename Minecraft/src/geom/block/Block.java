package geom.block;

import geom.basic.Cube;
import geom.basic.RVector3D;

public class Block extends Cube {
	
	public static double SIZE = 100;
	
	public Block(RVector3D[] nodes) {
		super(nodes);
	}
	
	//will place the block at a location relative to Block.SIZE
	//x, y, z refer to the coordinates with respect to other blocks,
	//actual coordinates are derived from Blocks.SIZE and the relative coordinates together
	public Block(double x, double y, double z) {
		super(x*SIZE, y*SIZE, z*SIZE, SIZE);
	}

	public Block(RVector3D pos) {
		super(RVector3D.mult(pos, SIZE), SIZE);
	}

}
