package com.kry.brickgame.splashes;

import com.kry.brickgame.boards.Board;

public abstract class Splash extends Board {
	private static final long serialVersionUID = 681833325724653718L;
	
	public final static int width = 10;
	public final static int height = 8;
	
	/**
	 * Default delay between frames
	 */
	private static final int DEFAULT_DELAY = 500;
	
	/**
	 * Table frames to draw them on the board: [frame][y][x]
	 */
	private final Cell[][][] frameTable;
	
	/**
	 * Number of animation frames
	 */
	private final int frames;
	/**
	 * Current frame
	 */
	private int frame;
	
	/**
	 * Creates a deep copy of a 3-dimensional array of {@code Cell}
	 * 
	 * @param array
	 *            source array
	 * @return copy of the source array
	 */
	private static Cell[][][] get3DArrayCopy(Cell[][][] array) {
		Cell[][][] newArray = array.clone();
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].clone();
			for (int j = 0; j < array[i].length; j++) {
				newArray[i][j] = array[i][j].clone();
			}
		}
		return newArray;
	}
	
	public Splash(Cell[][][] frameTable) {
		super(width, height);
		this.frameTable = get3DArrayCopy(frameTable);
		frames = frameTable.length;
		frame = 0;
	}
	
	/**
	 * Returns delay between frames in milliseconds
	 * 
	 * @return delay between frames
	 */
	@SuppressWarnings("static-method")
	public int getDelay() {
		return DEFAULT_DELAY;
	}
	
	/**
	 * @return next animation frame
	 */
	public Board getNextFrame() {
		int fromX, toX;
		int fromY, toY;
		
		// cleaning don't need, draws over the previous frame
		// clearBoard();
		
		// adapts output to fit the frame
		fromY = 0;
		if (frameTable[frame].length < height) {
			toY = frameTable[frame].length;
		} else {
			toY = height;
		}
		if (frameTable[frame][0].length < width) {
			fromX = width / 2 - frameTable[frame][0].length / 2 - 1;
			toX = fromX + frameTable[frame][0].length;
		} else {
			fromX = 0;
			toX = width;
		}
		
		for (int y = fromY; y < toY; y++) {
			for (int x = fromX; x < toX; x++) {
				// [height - y - 1] - draw upside down
				setCell(frameTable[frame][toY - y - 1][x - fromX], x, y);
			}
		}
		
		// scroll the frames in a circle
		if (frame < frames - 1) {
			frame++;
		} else {
			frame = 0;
		}
		
		return this;
	}
	
}
