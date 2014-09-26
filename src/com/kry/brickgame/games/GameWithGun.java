package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.*;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.ÑharacterShape;
import com.kry.brickgame.shapes.ÑharacterShape.Ñharacters;

/**
 * @author noLive
 * 
 */
public abstract class GameWithGun extends GameWithLives {
	/**
	 * The Gun
	 */
	protected ÑharacterShape gun;
	/**
	 * Array that stores the coordinates of the bullets
	 */
	protected volatile int[][] bullets;

	/**
	 * The Game with gun
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game
	 */
	public GameWithGun(int speed, int level, int type) {
		super(speed, level, type);

		gun = new ÑharacterShape(Ñharacters.Gun);

		bullets = new int[boardHeight - gun.getHeight()][boardWidth];
		initBullets(bullets);
	}
	
	/**
	 * Filling the bullets array with zeros
	 * 
	 * @param bullets
	 *            the bullets array
	 */
	private static void initBullets(int[][] bullets) {
		for (int i = 0; i < bullets.length; i++)
			for (int j = 0; j < bullets[i].length; j++)
				bullets[i][j] = 0;
	}

	/**
	 * Remove all bullets from the board
	 * 
	 * @param board
	 *            the board, which is necessary to remove the bullets
	 */
	protected void clearBullets(Board board) {
		for (int i = 0; i < boardWidth; i++)
			for (int j = 0; j < boardHeight; j++)
				// bullet is blink
				if (board.getCell(i, j) == Cell.Blink)
					board.setCell(Cell.Empty, i, j);
	}

	/**
	 * Move the gun to a new location
	 * 
	 * @param x
	 *            x-coordinate position of the new location
	 * @param y
	 *            y-coordinate position of the new location
	 * @return {@code true} if the movement succeeded, otherwise {@code false}
	 */
	protected boolean moveGun(int x, int y) {
		if ((x < 0) || (x >= boardWidth) || (y < 0) || (y >= boardHeight))
			return false;

		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the gun to not interfere with the checks
		board = drawShape(board, curX, curY, gun, Cell.Empty);

		if (checkCollision(board, gun, x, y))
			return false;

		// draw the gun on the new place
		setBoard(drawShape(board, x, y, gun, Cell.Full));

		curX = x;
		curY = y;

		return true;
	}

	/**
	 * Processing the flight of bullets (destruction mode)
	 */
	protected synchronized void flightOfBullets() {
		clearBullets(getBoard());

		for (int y = 0; y < bullets.length; y++) {
			for (int x = 0; x < bullets[y].length; x++) {
				// if 0, than bullet is not exist
				if (bullets[y][x] > 0) {
					Board board = getBoard();
					// if the bullet does not reach the border of the board
					if (bullets[y][x] < boardHeight - 1) {
						// if under the bullet is filled cell
						if (board.getCell(x, bullets[y][x]) == Cell.Full) {
							// remove the cell
							board.setCell(Cell.Empty, x, bullets[y][x]++);
							// increase scores
							setScore(getScore() + 1);
							// remove the bullet
							bullets[y][x] = 0;
						} else {
							// otherwise, continues flying
							board.setCell(Cell.Blink, x, bullets[y][x]++);
						}
					} else if (bullets[y][x] == boardHeight - 1) {
						// if under the bullet is filled cell
						if (board.getCell(x, bullets[y][x]) == Cell.Full) {
							// increase scores
							setScore(getScore() + 1);
						}
						// show the bullet
						board.setCell(Cell.Blink, x, bullets[y][x]);
						fireBoardChanged(board);
						sleep(ANIMATION_DELAY / 2);
						// remove the bullet
						board.setCell(Cell.Empty, x, bullets[y][x]);
						bullets[y][x] = 0;
					}
					fireBoardChanged(board);
				}
			}
		}
	}

	/**
	 * Processing the flight of mud bullets (creation mode)
	 */
	protected synchronized void flightOfMud() {
		clearBullets(getBoard());

		for (int y = 0; y < bullets.length; y++) {
			for (int x = 0; x < bullets[y].length; x++) {
				// if 0, than bullet is not exist
				if (bullets[y][x] > 0) {
					Board board = getBoard();
					// if the bullet does not reach the border of the board
					if (bullets[y][x] < boardHeight - 1) {
						// if in front of the bullet is filled cell
						if (board.getCell(x, bullets[y][x] + 1) == Cell.Full) {
							// stop the bullet before the cell
							board.setCell(Cell.Full, x, bullets[y][x]);
							setScore(getScore() + 1);
							// check for a filled lines
							removeFullLines(bullets[y][x]);
							// remove the bullet
							bullets[y][x] = 0;
						} else {
							// otherwise, continues flying
							board.setCell(Cell.Blink, x, bullets[y][x]++);
						}
					} else if (bullets[y][x] == boardHeight - 1) {
						// stop the bullet on the border of the board
						board.setCell(Cell.Full, x, bullets[y][x]);
						setScore(getScore() + 1);
						// check for a filled lines
						removeFullLines(bullets[y][x]);
						// remove the bullet
						bullets[y][x] = 0;
					}
					fireBoardChanged(board);
				}
			}
		}
	}

	/**
	 * Removal of a filled lines
	 */
	private boolean removeFullLines(int y) {
		boolean result = false;

		Board board = getBoard();

		boolean lineIsFull = true;
		for (int x = 0; x < boardWidth; x++) {
			if (board.getCell(x, y) == Cell.Empty) {
				lineIsFull = false;
				break;
			}
		}
		if (lineIsFull) {
			animatedClearLine(getBoard(), curX, y);

			// erase the gun from the board before dropping ups lines
			board = drawShape(board, curX, curY, gun, Cell.Empty);

			// drop the lines up on the filled line
			for (int i = y; i > 0; i--) {
				for (int j = 0; j < boardWidth; j++)
					board.setCell(board.getCell(j, i - 1), j, i);
			}

			// restore the gun after dropping ups lines
			board = drawShape(board, curX, curY, gun, Cell.Full);
			// increase scores
			setScore(getScore() + 10);
			// reset the bullets
			initBullets(bullets);

			setBoard(board);
			result = true;
		}
		return result;
	}

	/**
	 * Destroying (setting {@code Cell.Empty}) a single cell on the row above
	 * the specified coordinates {@code [x, y]}
	 * 
	 * @param x
	 *            x-coordinate of the cell from where shot will be made
	 * @param y
	 *            y-coordinate of the cell from where shot will be made
	 * @param hasTwoSmokingBarrels
	 *            if {@code true},then shot is made with two guns on the sides,
	 *            otherwise shot is made with one gun on the center
	 */
	protected void fire(final int x, final int y, boolean hasTwoSmokingBarrels) {
		if ((x < 0) || (x >= boardWidth) || (y < 0) || (y >= boardHeight))
			return;

		for (int i = x - 1; i <= x + 1; i++) {
			if (((hasTwoSmokingBarrels) && (i == x))
					|| ((!hasTwoSmokingBarrels) && (i != x)))
				continue;
			if ((i >= 0) && (i < boardWidth)) {
				for (int j = 0; j < bullets.length; j++) {
					if (bullets[j][i] == 0) {
						bullets[j][i] = y;
						break;
					}
				}
			}
		}
	}

}
