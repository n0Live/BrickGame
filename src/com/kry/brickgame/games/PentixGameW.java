package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;

/**
 * @author noLive
 */
public class PentixGameW extends TetrisGameO {
	private static final long serialVersionUID = -7520142259843012080L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.PentixSplashW";
	
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
	

    @Override protected int getSpeedOfFirstLevel() {
        return super.getSpeedOfFirstLevel() + 50;
    }

    @Override protected int getSpeedOfTenthLevel() {
        return super.getSpeedOfTenthLevel() + 50;
    }
}