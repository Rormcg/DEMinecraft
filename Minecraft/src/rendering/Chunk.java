package rendering;

import java.awt.Graphics;
import java.util.Arrays;

import geom.basic.RVector3D;
//import entities.Camera;
import geom.block.*;

/**
 * @author Rory McGuire
 * Helps to separate blocks into manageable 16x16 columns
 */
public class Chunk {
	
	private Block[][][] blocks;
	private Block[] drawableBlocks;
	
	public final static int WIDTH = 10; //The number of blocks wide (x) a chunk will be
	public final static int LENGTH = 10; //The number of blocks long (z) a chunk will be
	public final static int DEPTH = 1; //The number of blocks deep (y) a chunk will be
	
	public final static int GROUND_LEVEL = 200;
	
	private RVector3D midpoint;
	
	//x and z coordinates are with respect to other Chunks
	public Chunk(int xc, int zc) {
		//fill out the Chunk based on the x and y coordinates
		blocks = new Block[LENGTH][WIDTH][DEPTH];
		drawableBlocks = new Block[LENGTH * WIDTH * DEPTH];
		
		for(int z = 0; z < LENGTH; z++) {
			for(int x = 0; x < WIDTH; x++) {
				for(int y = 0; y < DEPTH; y++) {
					//blocks[z][x][y] = new Block(zc*LENGTH + z, xc*WIDTH + x, y*DEPTH);
					//blocks[z][x][y] = new Grass(zc*LENGTH + z, xc*WIDTH + x, y*DEPTH);
					blocks[z][x][y] = new Grass((xc*WIDTH + x) * Block.SIZE, y*DEPTH*Block.SIZE + GROUND_LEVEL, (zc*LENGTH + z) * Block.SIZE);
					//System.out.println(y*DEPTH*Block.SIZE);
					drawableBlocks[WIDTH * z * DEPTH + DEPTH * x + y] = blocks[z][x][y];
				}
			}
		}
		
		midpoint = new RVector3D(blocks[0][0][0].getPoint(0).getX() + 0.5 * WIDTH * Block.SIZE,
				blocks[0][0][0].getPoint(0).getY() + 0.5 * DEPTH * Block.SIZE,
				blocks[0][0][0].getPoint(0).getZ() + 0.5 * LENGTH * Block.SIZE);
		
		/*
		for(int i = 0; i < drawableBlocks.length; i++) {
			if(drawableBlocks[i] == null) {
				//System.out.println(i);
			}
		}*/
		//Arrays.sort(drawableBlocks);
	}
	
	public void rotate(double xr, double yr, double zr) {
		for(int z = 0; z < LENGTH; z++) {
			for(int x = 0; x < WIDTH; x++) {
				for(int y = 0; y < DEPTH; y++) {
					blocks[z][x][y].rotate(xr, yr, zr, midpoint);
					//drawableBlocks[WIDTH * z * DEPTH + DEPTH * x + y];
				}
			}
		}
	}
	
	public void draw(Graphics g) {
		Arrays.sort(drawableBlocks);
		for(Block b : drawableBlocks) {
			//if(b.inFrontOfCamera())
				b.draw(g);
		}
		
		/*for(int z = 0; z < blocks.length; z++) {
			for(int x = 0; x < blocks[z].length; x++) {
				for(int y = 0; y < blocks[z][x].length; y++) {
					blocks[z][x][y].draw(g, c);;
				}
			}
		}*/
	}
	
	/*public RVector3D midpoint() {
		return new RVector3D(blocks[0][0][0].getPoint(0).getX() + 0.5 * WIDTH * Block.SIZE,
				blocks[0][0][0].getPoint(0).getY() + 0.5 * DEPTH * Block.SIZE,
				blocks[0][0][0].getPoint(0).getZ() + 0.5 * LENGTH * Block.SIZE);
	}*/

}
