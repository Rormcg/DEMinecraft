package rendering;

import java.awt.Graphics;
import java.util.Arrays;

//import entities.Camera;
import geom.block.*;

/**
 * @author Rory McGuire
 * Helps to separate blocks into manageable 16x16 columns
 */
public class Chunk {
	
	private Block[][][] blocks;
	private Block[] drawableBlocks;
	
	public final static int WIDTH = 16; //The number of blocks wide (x) a chunk will be
	public final static int LENGTH = 16; //The number of blocks long (z) a chunk will be
	public final static int DEPTH = 20; //The number of blocks deep (y) a chunk will be
	
	//x and z coordinates are with respect to other Chunks
	public Chunk(int xc, int zc) {
		//fill out the Chunk based on the x and y coordinates
		blocks = new Block[LENGTH][WIDTH][DEPTH];
		drawableBlocks = new Block[LENGTH * WIDTH * DEPTH];
		
		for(int z = 0; z < blocks.length; z++) {
			for(int x = 0; x < blocks[z].length; x++) {
				for(int y = 0; y < blocks[z][x].length; y++) {
					//blocks[z][x][y] = new Block(zc*LENGTH + z, xc*WIDTH + x, y*DEPTH);
					blocks[z][x][y] = new Grass(zc*LENGTH + z, xc*WIDTH + x, y*DEPTH);
					drawableBlocks[z * LENGTH + x * WIDTH + y] = blocks[z][x][y];
				}
			}
		}
		//Arrays.sort(drawableBlocks);
	}
	
	public void draw(Graphics g) {
		Arrays.sort(drawableBlocks);
		for(Block b : drawableBlocks) {
			if(b.inFrontOfCamera()) b.draw(g);
		}
		
		/*for(int z = 0; z < blocks.length; z++) {
			for(int x = 0; x < blocks[z].length; x++) {
				for(int y = 0; y < blocks[z][x].length; y++) {
					blocks[z][x][y].draw(g, c);;
				}
			}
		}*/
	}

}
