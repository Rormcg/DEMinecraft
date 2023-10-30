package geom.block;

import geom.basic.RVector3D;
import img.Img;

public class Grass extends Block {
	public static final Img[] IMAGES = {Img.GRASS_SIDE, Img.GRASS_SIDE, Img.GRASS_SIDE,
										Img.DIRT_SIDE, Img.GRASS_SIDE, Img.GRASS_TOP};
	
	public Grass(RVector3D[] nodes) {
		super(nodes);
		setImages(IMAGES);
	}

	public Grass(double x, double y, double z, double s) {
		super(x, y, z, s);
		setImages(IMAGES);
	}

	public Grass(RVector3D pos, double s) {
		super(pos, s);
		setImages(IMAGES);
	}

}
