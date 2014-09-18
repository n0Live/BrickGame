package com.kry.brickgame.games;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.splashes.FroggerSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class FroggerGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new FroggerSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 16;
	/**
	 * The frog
	 */
	private Shape frog;

	private Board road;

	private Cell[][] tracts;
	/**
	 * Current position of the road shift
	 */
	private int roadPosition;

	public FroggerGame(int speed, int level, int type) {
		super(speed, level, type);
		setStatus(Status.None);

		this.frog = new Shape(1);
		frog.setCoord(0, new int[] { 0, 0 });
		frog.setFill(Cell.Blink);
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();

		setStatus(Status.Running);
		setLives(4);

		loadLevel();

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			// moving of the snake
			if ((getStatus() != Status.Paused)
					&& (elapsedTime(getSpeed(true) * 3))) {
				shiftRoad(true, true);
			}
			// processing of key presses
			processKeys();
		}
	}

	private void loadLevel() {
		road = loadRoad(false);
		insertCells(road.getBoard(), 0, 1);

		setFrog();
	}

	private void setFrog() {
		curX = boardWidth / 2 - 1;
		curY = 0;

		jumpFrog(curX, curY);
	}

	private Board loadRoad(boolean usePreloaded) {
		final Cell F = Cell.Full;
		final Cell E = Cell.Empty;
		// preloaded tracks
		tracts = new Cell[][] {
				//
				{ E, E, F, F, E, E, E, E, E, F, F, F, F, E, E, E },
				{ E, E, E, F, F, E, E, E, E, E, F, F, F, F, E, E },
				{ F, E, E, E, E, F, F, E, E, E, E, E, F, F, F, F },
				{ E, E, E, F, E, E, E, E, F, F, E, E, E, E, F, F },
				{ F, E, E, E, E, F, F, F, F, E, E, E, E, F, F, F },
				{ F, F, F, E, E, E, E, F, F, F, F, E, E, E, E, F },
				{ F, E, E, E, E, F, F, F, F, E, E, E, E, F, F, F },
				{ F, F, E, E, E, E, E, F, F, F, E, E, E, E, E, F }, };

		// generated random tracts
		if (!usePreloaded) {
			Random r = new Random();
			int emptySpanLength, fullSpanLength;
			int k;

			for (int i = 0; i < tracts.length; i++) {
				// set length of full and empty span
				emptySpanLength = r.nextInt(2) + 3;
				fullSpanLength = r.nextInt(3) + 1;

				do {
					// start point
					k = r.nextInt(tracts[i].length);
					// checking if under the start point has an empty cell
				} while ((i > 1) && (tracts[i - 1][k] != E));

				for (int j = 0; j < tracts[i].length; j++) {
					// in first, create the empty span
					if (emptySpanLength > 0) {
						tracts[i][k + j] = E;
						emptySpanLength--;
					}
					// in second, create the full span
					else if (fullSpanLength > 0) {
						tracts[i][k + j] = F;
						fullSpanLength--;
					}

					if ((emptySpanLength == 0) && (fullSpanLength == 0)) {
						// when created both, regenerating their length
						emptySpanLength = r.nextInt(2) + 3;

						while (tracts[i].length - j - 1 <= emptySpanLength) {
							// check the empty span does not leave the tract
							// and there is at least one cell to the full span
							emptySpanLength--;
						}
						// sets the length of the full span for the remainder of
						// the tract length
						fullSpanLength = tracts[i].length - j - 1
								- emptySpanLength;
						// but not more than 4
						if (fullSpanLength > 4)
							fullSpanLength = r.nextInt(3) + 1;
					}
					// if "k + j" out of bounds, set "k" such that "k + j = 0"
					if ((k + j + 1) >= tracts[i].length)
						k = -(j + 1);
				}
			}

		}

		int width = boardWidth;
		int height = boardHeight - 3;
		Board board = new Board(width, height);

		Cell[] tract = new Cell[width];

		for (int i = 0; i < height; i++) {
			if (i % 2 == 0) {
				// even lines must be filled
				for (int j = 0; j < width; j++) {
					tract[j] = Cell.Full;
				}
			} else {
				// initial position changes from level
				roadPosition = getLevel() - 1;

				int tractLength = tracts[i / 2].length;
				int length = width;
				// if the remainder is less than the width of the tract, copies
				// only the remainder at first
				if (tractLength - roadPosition < width)
					length = (tractLength - roadPosition);

				System.arraycopy(tracts[i / 2], roadPosition, tract, 0, length);

				if (length != width) {
					// complement to the width from the beginning of the tract
					System.arraycopy(tracts[i / 2], 0, tract, length, width
							- length);
				}
			}
			board.setRow(tract, i);
		}
		return board;
	}

	private void shiftRoad(boolean isLeftToRight, boolean withFrog) {

		Cell[] tract = new Cell[boardWidth];

		int tractLength = tracts[0].length;

		roadPosition = (isLeftToRight) ? roadPosition - 1 : roadPosition + 1;

		if (roadPosition < 0)
			roadPosition = tractLength - 1;
		else if (roadPosition >= tractLength)
			roadPosition = 0;

		for (int i = 0; i < road.getHeight(); i++) {
			if (i % 2 != 0) {

				int length = boardWidth;
				// if the remainder is less than the width of the tract, copies
				// only the remainder at first
				if (tractLength - roadPosition < boardWidth)
					length = (tractLength - roadPosition);

				System.arraycopy(tracts[i / 2], roadPosition, tract, 0, length);

				if (length != boardWidth) {
					// complement to the width from the beginning of the tract
					System.arraycopy(tracts[i / 2], 0, tract, length,
							boardWidth - length);
				}
				road.setRow(tract, i);
			}
		}
		insertCells(road.getBoard(), 0, 1);

		if (withFrog && ((curY > 0) && (curY < boardHeight - 1))) {
			setBoard(drawShape(getBoard(), curX, curY, frog, Cell.Empty));
			curX = (isLeftToRight) ? curX + 1 : curX - 1;
		}
		setBoard(drawShape(getBoard(), curX, curY, frog, frog.getFill()));
	}

	/**
	 * Moving the frog to the new position
	 * 
	 * @param x
	 *            x-coordinate position of the new position
	 * @param y
	 *            y-coordinate position of the new position
	 * @return {@code true} if the movement succeeded, otherwise {@code false}
	 */
	private boolean jumpFrog(int x, int y) {
		if ((y < 0) || (y >= boardHeight))
			return true;

		if ((x < 0) || (x >= boardWidth))
			return false;

		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the frog to not interfere with the checks
		board = drawShape(board, curX, curY, frog, Cell.Empty);

		// check for collisions
		if (checkCollision(board, frog, x, y))
			return false;

		// draw the frog on the new place
		if (y == boardHeight - 1) {
			setBoard(drawShape(board, x, y, frog, Cell.Full));
			setFrog();
		} else {
			setBoard(drawShape(board, x, y, frog, frog.getFill()));
			curX = x;
			curY = y;
		}

		return true;
	}

	/**
	 * Processing of key presses
	 */
	private void processKeys() {
		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyReset)) {
			keys.remove(KeyPressed.KeyReset);
			ExitToMainMenu();
			return;
		}

		if (keys.contains(KeyPressed.KeyStart)) {
			keys.remove(KeyPressed.KeyStart);
			pause();
			return;
		}

		if (getStatus() != Status.Running)
			return;

		if (keys.contains(KeyPressed.KeyLeft)) {
			jumpFrog(curX - 1, curY);
			keys.remove(KeyPressed.KeyLeft);
		}
		if (keys.contains(KeyPressed.KeyRight)) {
			jumpFrog(curX + 1, curY);
			keys.remove(KeyPressed.KeyRight);
		}
		if (keys.contains(KeyPressed.KeyDown)) {
			jumpFrog(curX, curY - 2);
			keys.remove(KeyPressed.KeyDown);
		}
		if (keys.contains(KeyPressed.KeyUp)) {
			int dY = (curY < boardHeight - 2) ? 2 : 1;
			jumpFrog(curX, curY + dY);
			keys.remove(KeyPressed.KeyUp);
		}
		if (keys.contains(KeyPressed.KeyRotate)) {
			int dY = (curY < boardHeight - 2) ? 2 : 1;
			jumpFrog(curX, curY + dY);
			keys.remove(KeyPressed.KeyRotate);
		}
	}

}
