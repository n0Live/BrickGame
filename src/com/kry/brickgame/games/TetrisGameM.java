package com.kry.brickgame.games;

import com.kry.brickgame.Board;

/**
 * @author noLive
 * 
 */
public class TetrisGameM extends TetrisGame {

	/**
	 * The Tetris with the shifting board content
	 */
	public TetrisGameM(int speed, int level, int type) {
		super(speed, level, type);
	}
	
	@Override
	protected void pieceDropped() {
		super.pieceDropped();
		if (getStatus() != Status.GameOver){
			Board board = getBoard().clone();
			board = horizontalShift(board, 1);
			setBoard(board);
		}
	}


}
