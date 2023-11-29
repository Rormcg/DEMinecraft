package geom.block;

import geom.basic.Cube;
import geom.basic.RVector3D;

public class Block extends Cube {
	
	public static double SIZE = 100;
	
	public Block(RVector3D[] nodes) {
		super(nodes);
	}
	
	public Block(double x, double y, double z) {
		super(x, y, z, SIZE);
	}

	public Block(RVector3D pos) {
		super(pos, SIZE);
	}

}
