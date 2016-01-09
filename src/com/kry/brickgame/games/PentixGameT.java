package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;

/**
 * @author noLive
 */
public class PentixGameT extends TetrisGameL {
	private static final long serialVersionUID = 1224464643577203324L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.PentixSplashT";
	
	/**
	 * The Tetris with the Pentominoes figures
	 * <p>
	 * Feature: addition of new lines every few seconds and changing of the
	 * figures instead of rotating
	 * 
	 * @see TetrisGameL#TetrisGameL(int, int, Rotation, int)
	 */
	public PentixGameT(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}
	
	@Override
	TetrisShape getNextFigure(TetrisShape aTetrisShape) {
		return TetrisShape.getNextShape(aTetrisShape, false);
	}
	
	@Override
	TetrisShape getPrevFigure(TetrisShape aTetrisShape) {
		return TetrisShape.getPrevShape(aTetrisShape, false);
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