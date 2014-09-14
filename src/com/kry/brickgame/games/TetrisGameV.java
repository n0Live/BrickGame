package com.kry.brickgame.games;

import com.kry.brickgame.Board;

/**
 * @author noLive
 * 
 */
public class TetrisGameV extends TetrisGameR {

	/**
	 * The Tetris with the board is upside down, the addition of new lines every
	 * few seconds and the shifting board content
	 */
	public TetrisGameV(int speed, int level, int type) {
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
