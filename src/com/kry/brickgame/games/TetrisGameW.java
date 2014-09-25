package com.kry.brickgame.games;

import com.kry.brickgame.Board;

/**
 * @author noLive
 * 
 */
public class TetrisGameW extends TetrisGameS {

	/**
	 * The Tetris with the board is upside down, the changing of the figures
	 * instead of rotating and the shifting board content
	 * 
	 * @see TetrisGame#TetrisGame(int, int, Rotation, int)
	 */
	public TetrisGameW(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
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
