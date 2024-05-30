package geom.block;

import geom.basic.RVector3D;
import img.Img;

public class Stone extends Block {
	public static final Img[] IMAGES = {Img.STONE, Img.STONE, Img.STONE,
										Img.STONE, Img.STONE, Img.STONE};
	
	public Stone(RVector3D[] nodes) {
		super(nodes);
		setImages(IMAGES);
	}

	public Stone(double x, double y, double z) {
		super(x, y, z);
		setImages(IMAGES);
	}

	public Stone(RVector3D pos) {
		super(pos);
		setImages(IMAGES);
	}

}
