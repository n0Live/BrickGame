package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;

/**
 * @author noLive
 */
public class PentixGameX extends TetrisGameP {
	private static final long serialVersionUID = -6598238012412470344L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.PentixSplashX";
	
	/**
	 * The Tetris with the Pentominoes figures
	 * <p>
	 * Feature: addition of new lines every few seconds, and changing of the
	 * figures instead of rotating, and shifting board content
	 * 
	 * @see TetrisGameP#TetrisGameP(int, int, Rotation, int)
	 */
	public PentixGameX(int speed, int level, Rotation rotation, int type) {
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