package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.boardHorizontalShift;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * @author noLive
 */
public class TetrisGameP extends TetrisGameL {
	private static final long serialVersionUID = 3734423526420480573L;

	/**
	 * The Tetris with the addition of new lines every few seconds, and the
	 * changing of the figures instead of rotating, and the shifting board
	 * content
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameP(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	@Override
	protected void pieceDropped() {
		super.pieceDropped();
		if (getStatus() != Status.GameOver) {
			Board board = getBoard();
			
			board = boardHorizontalShift(board, 1);
			setBoard(board);
		}
	}

}
