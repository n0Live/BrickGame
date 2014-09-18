package com.kry.brickgame.splashes;

import com.kry.brickgame.Board;

public abstract class Splash extends Board {

	public final static int width = 10;
	public final static int height = 8;

	/**
	 * Table frames to draw them on the board: [frame][y][x]
	 */
	private Cell[][][] frameTable;

	/**
	 * Number of animation frames
	 */
	private int frames;
	/**
	 * Current frame
	 */
	private int frame;

	public Splash(Cell[][][] frameTable) {
		super(width, height);
		this.frameTable = frameTable;
		this.frames = frameTable.length;
		this.frame = 0;
	}

	/**
	 * @return next animation frame
	 */
	public Board getNextFrame() {
		int fromX, toX;
		int fromY, toY;

		clearBoard();

		// adapts output to fit the frame
		fromY = 0;
		if (frameTable[frame].length < height) {
			toY = frameTable[frame].length;
		} else {
			toY = height;
		}
		if (frameTable[frame][0].length < width) {
			fromX = (width / 2) - (frameTable[frame][0].length / 2) - 1;
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
		// do not show a zero frame (it's empty)
		if (frame < (frames - 1)) {
			frame++;
		} else {
			frame = 0;
		}

		return this;
	}

}
