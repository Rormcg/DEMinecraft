package rendering;

import geom.block.Block;

/**
 * @author Rory McGuire
 * Helps to separate blocks into manageable 16x16 columns
 */
public class Chunk {
	
	private Block[][][] blocks;
	
	public final static int WIDTH = 16; //The number of blocks wide (x) a chunk will be
	public final static int HEIGHT = 16; //The number of blocks high (y) a chunk will be
	public final static int DEPTH = 20; //The number of blocks deep (z) a chunk will be
	
	//x and y coordinates are with respect to other Chunks
	public Chunk(int x, int y) {
		//fill out the Chunk based on the x and y coordinates
		blocks = new Block[WIDTH][HEIGHT][DEPTH];
		
		for(int w = 0; w < blocks.length; w++) {
			for(int h = 0; w < blocks[w].length; h++) {
				for(int d = 0; d < blocks[w][h].length; d++) {
					blocks[w][h][d] = new Block(x*WIDTH*Block.SIZE + w*Block.SIZE, y*HEIGHT*Block.SIZE + h*Block.SIZE, d*Block.SIZE);
				}
			}
		}
	}

}
