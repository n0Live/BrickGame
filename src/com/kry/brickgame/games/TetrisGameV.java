package com.kry.brickgame.games;

import com.kry.brickgame.Board;

import static com.kry.brickgame.games.GameUtils.*;

/**
 * @author noLive
 * 
 */
public class TetrisGameV extends TetrisGameR {

	/**
	 * The Tetris with the board is upside down, the addition of new lines every
	 * few seconds and the shifting board content
	 * 
	 * @see TetrisGame#TetrisGame(int, int, Rotation, int)
	 */
	public TetrisGameV(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	@Override
	protected void pieceDropped() {
		super.pieceDropped();
		if (getStatus() != Status.GameOver) {
			Board board = getBoard().clone();
			board = boardHorizontalShift(board, 1);
			setBoard(board);
		}
	}

}
