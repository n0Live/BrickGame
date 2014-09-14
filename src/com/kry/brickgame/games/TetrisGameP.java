package com.kry.brickgame.games;

import com.kry.brickgame.Board;

/**
 * @author noLive
 * 
 */
public class TetrisGameP extends TetrisGameL {

	/**
	 * The Tetris with the addition of new lines every few seconds and the
	 * changing of the figures instead of rotating and the shifting board
	 * content
	 */
	public TetrisGameP(int speed, int level, int type) {
		super(speed, level, type);
	}

	@Override
	protected void pieceDropped() {
		super.pieceDropped();
		if (getStatus() != Status.GameOver) {
			Board board = getBoard().clone();
			board = horizontalShift(board, 1);
			setBoard(board);
		}
	}

}
