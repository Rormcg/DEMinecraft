package geom.block;

import geom.basic.RVector3D;
import img.Img;

public class Deepslate extends Block {
	public static final Img[] IMAGES = {Img.DEEPSLATE, Img.DEEPSLATE, Img.DEEPSLATE,
										Img.DEEPSLATE, Img.DEEPSLATE, Img.DEEPSLATE};
	
	public Deepslate(RVector3D[] nodes) {
		super(nodes);
		setImages(IMAGES);
	}

	public Deepslate(double x, double y, double z) {
		super(x, y, z);
		setImages(IMAGES);
	}

	public Deepslate(RVector3D pos) {
		super(pos);
		setImages(IMAGES);
	}

}
