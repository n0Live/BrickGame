package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;

/**
 * @author noLive
 */
public class PentixGameU extends TetrisGameM {
	private static final long serialVersionUID = 1655253672922418636L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.PentixSplashU";
	
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
	TetrisShape getRandomShape() {
		return TetrisShape.getRandomShapeAndRotate();
	}
	
	@Override
	TetrisShape getRandomShapeAndSuper(int[] superShapes) {
		return TetrisShape.getRandomShapeAndSuper(superShapes);
	}
	
}