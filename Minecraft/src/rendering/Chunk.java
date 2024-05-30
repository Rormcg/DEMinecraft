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
public class Chunk implements Comparable<Chunk>{
	
	private Block[][][] blocks;
	private Block[] drawableBlocks;
	
	public final static int WIDTH = 9; //The number of blocks wide (x) a chunk will be
	public final static int LENGTH = 9; //The number of blocks long (z) a chunk will be
	public final static int DEPTH = 8; //The number of blocks deep (y) a chunk will be

	//public final static RVector3D ORIGIN = new RVector3D(0, 250, 0);
	//public final static RVector3D ORIGIN = new RVector3D(250, 250, 0);
	public final static RVector3D ORIGIN = TestChunks.singleChunk ? new RVector3D(180, 250, 0) : new RVector3D(300, 300, 0);
	
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
					if(y < 1) {
						blocks[z][x][y] = new Grass((xc*WIDTH + x) * Block.SIZE + ORIGIN.getX(), y*Block.SIZE + ORIGIN.getY(), (zc*LENGTH + z) * Block.SIZE);
					} else if(y < 3) {
						blocks[z][x][y] = new Dirt((xc*WIDTH + x) * Block.SIZE + ORIGIN.getX(), y*Block.SIZE + ORIGIN.getY(), (zc*LENGTH + z) * Block.SIZE);
					} else if(y < 6){
						blocks[z][x][y] = new Stone((xc*WIDTH + x) * Block.SIZE + ORIGIN.getX(), y*Block.SIZE + ORIGIN.getY(), (zc*LENGTH + z) * Block.SIZE);
					} else {
						blocks[z][x][y] = new Deepslate((xc*WIDTH + x) * Block.SIZE + ORIGIN.getX(), y*Block.SIZE + ORIGIN.getY(), (zc*LENGTH + z) * Block.SIZE);
					}
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
	
	public void rotate(double xr, double yr, double zr, RVector3D r) {
		for(int z = 0; z < LENGTH; z++) {
			for(int x = 0; x < WIDTH; x++) {
				for(int y = 0; y < DEPTH; y++) {
					blocks[z][x][y].rotate(xr, yr, zr, r);
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

	@Override
	public int compareTo(Chunk o) {
		return (int)Integer.compare((int)midpoint().getZ(), (int)o.midpoint().getZ());
	}
	
	//for use with compareTo only (not currently accurate enough for rotation)
	private RVector3D midpoint() {
		return new RVector3D(blocks[(int)(LENGTH * 0.5)][(int)(WIDTH * 0.5)][(int)(DEPTH * 0.5)].getPoint(0).getX() + 0.5 * WIDTH * Block.SIZE,
				blocks[(int)(LENGTH * 0.5)][(int)(WIDTH * 0.5)][(int)(DEPTH * 0.5)].getPoint(0).getY() + 0.5 * DEPTH * Block.SIZE,
				blocks[(int)(LENGTH * 0.5)][(int)(WIDTH * 0.5)][(int)(DEPTH * 0.5)].getPoint(0).getZ() + 0.5 * LENGTH * Block.SIZE);
	}

}
