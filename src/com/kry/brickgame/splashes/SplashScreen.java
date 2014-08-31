package com.kry.brickgame.splashes;

import com.kry.brickgame.Board;
import com.kry.brickgame.BoardNumbers;
import com.kry.brickgame.Main;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameSelector;

/**
 * @author noLive
 * 
 */
public class SplashScreen extends Game {

	public SplashScreen() {
		super();
	}

	public void start() {
		super.start();

		setStatus(Status.DoSomeWork);
		insertNumbers();

		// Splash screen will be run in a separate thread
		Thread splashScreenThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!interrupted()) {
					animatedInvertBoard();
					blinkNumbers(5);
				}
			}
		}, "SplashScreen");

		splashScreenThread.start();

		// by pressing any key status sets to Status.None
		while (getStatus() != Status.None) {
			processKeys();
		}

		splashScreenThread.interrupt();
		Main.gameSelector = new GameSelector();
		Main.setGame(Main.gameSelector);
	}

	/**
	 * Draws a "9999" on the main board
	 */
	private void insertNumbers() {
		Board board = getBoard();
		BoardNumbers boardNumber = new BoardNumbers();

		boardNumber.setNumber(boardNumber.intToNumbers(9));

		insertCells(boardNumber.getBoard(),// upper left
				1, board.getHeight() - boardNumber.getHeight() - 1);
		insertCells(boardNumber.getBoard(),// lower left
				1, boardNumber.getHeight());
		insertCells(
				boardNumber.getBoard(),// upper right
				board.getWidth() - boardNumber.getWidth() - 1,
				board.getHeight() - boardNumber.getHeight() * 2);
		insertCells(boardNumber.getBoard(),// lower right
				board.getWidth() - boardNumber.getWidth() - 1, 1);
	}

	/**
	 * Animated horizontal moving and inverting cells
	 * 
	 * @param fromX
	 *            the starting x-coordinate
	 * @param toX
	 *            the finishing x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @return {@code false} if the current thread was interrupted, otherwise -
	 *         {@code true}
	 */
	private boolean horizontalMove(int fromX, int toX, int y) {
		Board board = getBoard();
		// define the direction by coordinates
		boolean isRightDirection = (toX >= fromX);

		// left to right
		if (isRightDirection) {
			for (int i = fromX; i <= toX; i++) {

				if (interrupted())
					return false;

				// invert cells
				board.setCell(((board.getCell(i, y) == Cell.Empty) ? Cell.Full
						: Cell.Empty), i, y);
				fireBoardChanged(board);
				justSleep(ANIMATION_DELAY);
			}
			// right to left
		} else {
			for (int i = fromX; i >= toX; i--) {

				if (interrupted())
					return false;

				// invert cells
				board.setCell(((board.getCell(i, y) == Cell.Empty) ? Cell.Full
						: Cell.Empty), i, y);
				fireBoardChanged(board);
				justSleep(ANIMATION_DELAY);
			}
		}
		return true;
	}

	/**
	 * Animated vertical moving and inverting cells
	 * 
	 * @param fromY
	 *            the starting y-coordinate
	 * @param toY
	 *            the finishing y-coordinate
	 * @param x
	 *            the x-coordinate
	 * @return {@code false} if the current thread was interrupted, otherwise -
	 *         {@code true}
	 */
	private boolean verticalMove(int fromY, int toY, int x) {
		Board board = getBoard();
		// define the direction by coordinates
		boolean isUpDirection = (toY >= fromY);

		// bottom to top
		if (isUpDirection) {
			for (int i = fromY; i <= toY; i++) {

				if (interrupted())
					return false;

				// invert cells
				board.setCell(((board.getCell(x, i) == Cell.Empty) ? Cell.Full
						: Cell.Empty), x, i);
				fireBoardChanged(board);
				justSleep(ANIMATION_DELAY);
			}
			// top to bottom
		} else {
			for (int i = fromY; i >= toY; i--) {

				if (interrupted())
					return false;

				// invert cells
				board.setCell(((board.getCell(x, i) == Cell.Empty) ? Cell.Full
						: Cell.Empty), x, i);
				fireBoardChanged(board);
				justSleep(ANIMATION_DELAY);
			}
		}
		return true;
	}

	/**
	 * Animated walking in a spiral with inverting cells on the main board
	 */
	protected void animatedInvertBoard() {
		Board board = getBoard();
		// x: 0 --> board.width
		int fromX = 0;
		int toX = board.getWidth() - 1;
		// y: board.height --> 0
		int fromY = board.getHeight() - 1;
		int toY = 0;

		// until it reaches the middle of the board
		while (fromX != board.getWidth() / 2) {
			// spiral motion with a gradually narrowing
			if (!horizontalMove(fromX, toX, fromY--)
					|| !verticalMove(fromY, toY, toX--)
					|| !horizontalMove(toX, fromX, toY++)
					|| !verticalMove(toY, fromY, fromX++)) {
				Thread.currentThread().interrupt();
				return;
			}
		}
		justSleep(ANIMATION_DELAY * 2);
	}

	/**
	 * Blinking "9999" on the board specified number of times
	 * 
	 * @param repeatCount
	 *            the number of repeats of blinks
	 */
	protected void blinkNumbers(int repeatCount) {
		if (repeatCount <= 0)
			return;

		for (int i = 0; i < repeatCount; i++) {

			if (interrupted()) {
				// when checking the "interrupted" flag is reset, so sets it
				// again
				Thread.currentThread().interrupt();
				return;
			}

			clearBoard();
			justSleep(ANIMATION_DELAY * 5);
			insertNumbers();
			justSleep(ANIMATION_DELAY * 6);
		}
	}

	/**
	 * Processing of key presses
	 */
	private void processKeys() {
		if (getStatus() == Status.None)
			return;
		if (!keys.isEmpty())
			setStatus(Status.None);
	}

}
