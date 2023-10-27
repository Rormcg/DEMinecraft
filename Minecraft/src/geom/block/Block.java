package geom.block;

import geom.basic.Cube;
import geom.basic.RVector3D;

public class Block extends Cube {
	
	public Block(RVector3D[] nodes) {
		super(nodes);
		// TODO Auto-generated constructor stub
	}

	public Block(double x, double y, double z, double s) {
		super(x, y, z, s);
		// TODO Auto-generated constructor stub
	}

	public Block(RVector3D pos, double s) {
		super(pos, s);
		// TODO Auto-generated constructor stub
	}

}
