package com.kry.brickgame.games;

import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.shapes.TetrisShape.Figures;

/**
 * @author noLive
 */
public class TetrisGameK extends TetrisGameI {
	private static final long serialVersionUID = -2765291209922783752L;
	
	/**
	 * The Tetris with the changing of the figures instead of rotating
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameK(int speed, int level, Rotation rotation, int type) {
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
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (keys.isEmpty() || getStatus() == Status.None) return;
		if (getStatus() == Status.Running && !isFallingFinished
		        && containsKey(KeyPressed.KeyRotate) && !curPiece.isSuperShape()) {
			TetrisShape rotatedPiece;
			if (getRotation() == Rotation.COUNTERCLOCKWISE) {
				rotatedPiece = getPrevFigure(curPiece);
			} else {
				rotatedPiece = getNextFigure(curPiece);
			}
			
			if (tryMove(rotatedPiece, curX, curY)) {
				GameSound.playEffect(Effects.turn);
			}
			keys.remove(KeyPressed.KeyRotate);
		} else {
			super.processKeys();
		}
	}
	
}
