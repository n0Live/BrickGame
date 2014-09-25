package com.kry.brickgame.games;

import com.kry.brickgame.Board;

/**
 * @author noLive
 * 
 */
public class TetrisGameS extends TetrisGameK {

	/**
	 * The Tetris with the board is upside down and the changing of the figures
	 * instead of rotating
	 * 
	 * @see TetrisGame#TetrisGame(int, int, Rotation, int)
	 */
	public TetrisGameS(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	@Override
	protected synchronized void fireBoardChanged(Board board) {
		Board newBoard = board.clone();

		// draws the inverted board
		for (int i = 0; i < board.getHeight(); i++) {
			newBoard.setRow(board.getRow(i), board.getHeight() - i - 1);
		}

		super.fireBoardChanged(newBoard);
	}

	@Override
	protected synchronized void firePreviewChanged(Board board) {
		Board newBoard = board.clone();

		// draws the inverted board
		for (int i = 0; i < board.getHeight(); i++) {
			newBoard.setRow(board.getRow(i), board.getHeight() - i - 1);
		}

		super.firePreviewChanged(newBoard);
	}

	@Override
	public void keyPressed(KeyPressed key) {
		// swap the up and down buttons
		if (key == KeyPressed.KeyDown)
			keys.add(KeyPressed.KeyUp);
		else if (key == KeyPressed.KeyUp)
			keys.add(KeyPressed.KeyDown);
		else
			keys.add(key);
	}

	@Override
	public void keyReleased(KeyPressed key) {
		// swap the up and down buttons
		if (key == KeyPressed.KeyDown)
			keys.remove(KeyPressed.KeyUp);
		else if (key == KeyPressed.KeyUp)
			keys.remove(KeyPressed.KeyDown);
		else
			keys.remove(key);
	}

}
