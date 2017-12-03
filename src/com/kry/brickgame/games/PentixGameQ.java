package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;

/**
 * @author noLive
 */
public class PentixGameQ extends TetrisGameI {
	private static final long serialVersionUID = -574749332005947736L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.PentixSplashQ";
	
	/**
	 * The Tetris with the Pentominoes figures
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public PentixGameQ(int speed, int level, Rotation rotation, int type) {
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
