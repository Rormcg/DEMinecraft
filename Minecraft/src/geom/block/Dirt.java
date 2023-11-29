package geom.block;

import geom.basic.RVector3D;
import img.Img;

public class Dirt extends Block {

	public static final Img[] IMAGES = {Img.DIRT_SIDE, Img.DIRT_SIDE, Img.DIRT_SIDE,
										Img.DIRT_SIDE, Img.DIRT_SIDE, Img.DIRT_SIDE};		
	public Dirt(RVector3D[] nodes) {
		super(nodes);
		setImages(IMAGES);
	}
	
	public Dirt(double x, double y, double z) {
		super(x, y, z);
		setImages(IMAGES);
	}
	
	public Dirt(RVector3D pos) {
		super(pos);
		setImages(IMAGES);
	}

}
