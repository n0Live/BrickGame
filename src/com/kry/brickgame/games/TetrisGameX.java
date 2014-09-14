package com.kry.brickgame.games;

import com.kry.brickgame.Board;

/**
 * @author noLive
 * 
 */
public class TetrisGameX extends TetrisGameT {

	/**
	 * The Tetris with the board is upside down, the addition of new lines every
	 * few seconds, the changing of the figures instead of rotating and the
	 * shifting board content
	 */
	public TetrisGameX(int speed, int level, int type) {
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
