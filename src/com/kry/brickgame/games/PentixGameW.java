package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.splashes.PentixSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 */
public class PentixGameW extends TetrisGameO {
	private static final long serialVersionUID = -7520142259843012080L;
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new PentixSplash();
	
	/**
	 * The Tetris with the Pentominoes figures
	 * <p>
	 * Feature: changing of the figures instead of rotating and shifting board
	 * content
	 * 
	 * @see TetrisGameO#TetrisGameO(int, int, Rotation, int)
	 */
	public PentixGameW(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}
	
	@Override
	protected TetrisShape getNextFigure(TetrisShape aTetrisShape) {
		return TetrisShape.getNextShape(aTetrisShape, false);
	}
	
	@Override
	protected TetrisShape getPrevFigure(TetrisShape aTetrisShape) {
		return TetrisShape.getPrevShape(aTetrisShape, false);
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