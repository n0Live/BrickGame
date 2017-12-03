package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;

/**
 * @author noLive
 */
public class PentixGameR extends TetrisGameJ {
	private static final long serialVersionUID = -2432575886307114174L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.PentixSplashR";
	
	/**
	 * The Tetris with the Pentominoes figures
	 * <p>
	 * Feature: addition of new lines every few seconds
	 * 
	 * @see TetrisGameJ#TetrisGameJ(int, int, Rotation, int)
	 */
	public PentixGameR(int speed, int level, Rotation rotation, int type) {
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
	

    @Override protected int getSpeedOfFirstLevel() {
        return super.getSpeedOfFirstLevel() + 50;
    }

    @Override protected int getSpeedOfTenthLevel() {
        return super.getSpeedOfTenthLevel() + 50;
    }
}