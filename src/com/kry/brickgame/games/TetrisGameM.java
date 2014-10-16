package com.kry.brickgame.games;

import com.kry.brickgame.Board;

import static com.kry.brickgame.games.GameUtils.*;

/**
 * @author noLive
 * 
 */
public class TetrisGameM extends TetrisGameI {

	/**
	 * The Tetris with the shifting board content
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameM(int speed, int level, Rotation rotation, int type) {
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
