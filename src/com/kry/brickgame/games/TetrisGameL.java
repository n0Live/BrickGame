package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.shapes.TetrisShape.Figures;

/**
 * @author noLive
 */
public class TetrisGameL extends TetrisGameJ {
	private static final long serialVersionUID = 751057972065508388L;
	
	/**
	 * The Tetris with the addition of new lines every few seconds and the
	 * changing of the figures instead of rotating
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameL(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}
	
	/**
	 * Get instance of next shape in {@link Figures}. If the
	 * {@code aTetrisShape} is the last item in Figures, then returns the first
	 * item with the next rotation angle.
	 * 
	 * @param aTetrisShape
	 *            the figure for which get the next
	 * @return the next figure from {@code aTetrisShape}
	 */
	@SuppressWarnings("static-method")
	TetrisShape getNextFigure(TetrisShape aTetrisShape) {
		return TetrisShape.getNextTetraminoes(aTetrisShape);
	}
	
	/**
	 * Get instance of previous shape in {@link Figures}. If the
	 * {@code aTetrisShape} is the first item in Figures, then returns the last
	 * item with the previous rotation angle.
	 * 
	 * @param aTetrisShape
	 *            the figure for which get the previous
	 * @return the previous figure from {@code aTetrisShape}
	 */
	@SuppressWarnings("static-method")
	TetrisShape getPrevFigure(TetrisShape aTetrisShape) {
		return TetrisShape.getPrevTetraminoes(aTetrisShape);
	}
	
	@Override
	protected TetrisShape rotateFigure(TetrisShape figure) {
		TetrisShape result;
		if (getRotation() == Rotation.COUNTERCLOCKWISE) {
			result = getPrevFigure(figure);
		} else {
			result = getNextFigure(figure);
		}
		return result;
	}
	
}
