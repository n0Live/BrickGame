package com.kry.brickgame.games;

import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.splashes.PentixSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class PentixGameV extends TetrisGameN {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new PentixSplash();

	/**
	 * The Tetris with the Pentominoes figures
	 * <p>
	 * Feature: addition of new lines every few seconds and shifting board
	 * content
	 * 
	 * @see TetrisGameN#TetrisGameN(int, int, Rotation, int)
	 */
	public PentixGameV(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	@Override
	protected TetrisShape getRandomShape() {
		return TetrisShape.getRandomShapeAndRotate();
	}

	@Override
	protected TetrisShape getRandomShapeAndSuper(int[] superShapes) {
		return TetrisShape.getRandomShapeAndSuper(superShapes);
	}

}