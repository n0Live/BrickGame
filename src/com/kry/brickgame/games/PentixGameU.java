package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.splashes.PentixSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class PentixGameU extends TetrisGameM {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new PentixSplash();

	/**
	 * The Tetris with the Pentominoes figures
	 * <p>
	 * Feature: shifting board content
	 * 
	 * @see TetrisGameM#TetrisGameM(int, int, Rotation, int)
	 */
	public PentixGameU(int speed, int level, Rotation rotation, int type) {
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