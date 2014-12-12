package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.boardHorizontalShift;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * @author noLive
 */
public class TetrisGameM extends TetrisGameI {
	private static final long serialVersionUID = 21601260655217157L;

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
			Board board = getBoard();
			
			board = boardHorizontalShift(board, 1);
			setBoard(board);
		}
	}

}
