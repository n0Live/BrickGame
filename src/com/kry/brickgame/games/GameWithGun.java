package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.isFullLine;
import static com.kry.brickgame.games.GameUtils.sleep;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.shapes.GunShape;

/**
 * @author noLive
 */
public abstract class GameWithGun extends GameWithLives {
	private static final long serialVersionUID = -1845599776247419199L;

	/**
	 * The Gun
	 */
	final GunShape gun;

	/**
	 * Array that stores the coordinates of the bullets
	 */
	final AtomicReferenceArray<AtomicIntegerArray> bullets;

	// is equals int[][] but atomic

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

		gun = new GunShape();

		int bulletsArrayWidth = boardWidth;
		bullets = new AtomicReferenceArray<>(
				new AtomicIntegerArray[bulletsArrayWidth]);
		initBullets(bullets);
	}

	/**
	 * Adding a cell to a board and increasing scores
	 * 
	 * @param board
	 *            a board
	 * @param x
	 *            x-coordinate of the cell
	 * @param y
	 *            y-coordinate of the cell
	 */
	private void addCell(Board board, int x, int y) {
		GameSound.playEffect(Effects.add_cell);
		synchronized (lock) {
			// add the cell
			board.setCell(Cell.Full, x, y);
		}
		// increase scores
		setScore(getScore() + 1);
	}

	/**
	 * Adding a cell to a board and increasing scores, after that removing line
	 * if is filled.
	 * 
	 * @param board
	 *            a board
	 * @param x
	 *            x-coordinate of the cell
	 * @param y
	 *            y-coordinate of the cell
	 * @return a board after adding a cell and removing a full lines
	 */
	private Board addCellAndCheckLine(Board board, int x, int y) {
		addCell(board, x, y);
		return removeFullLines(board, y);
	}

	/**
	 * Remove all bullets from a board
	 * 
	 * @param board
	 *            a board, which is necessary to remove a bullets
	 */
	static void clearBullets(Board board) {
		synchronized (lock) {
			for (int i = 0; i < board.getWidth(); i++) {
				for (int j = 0; j < board.getHeight(); j++)
					// bullets.get(y) is blink
					if (board.getCell(i, j) == Cell.Blink) {
						board.setCell(Cell.Empty, i, j);
					}
			}
		}
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
	void fire(int x, int y, boolean hasTwoSmokingBarrels) {
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) return;

		for (int i = x - 1; i <= x + 1; i++) {
			if (hasTwoSmokingBarrels && i == x || !hasTwoSmokingBarrels
					&& i != x) {
				continue;
			}
			if (i >= 0 && i < boardWidth) {
				for (int j = 0; j < bullets.get(i).length(); j++) {
					if (bullets.get(i).get(j) == 0) {
						bullets.get(i).set(j, y);
						break;
					}
				}
			}
		}
		// playEffect(Effects.turn); <= removed due occurring audio artifacts
	}

	private void flight(boolean ofBullets) {
		synchronized (lock) {
			Board board = getBoard();
			clearBullets(board);
			for (int x = 0; x < bullets.length(); x++) {
				for (int y = 0; y < bullets.get(x).length(); y++) {
					if (isInterrupted()) return;
					// if 0, than bullet is not exist
					if (bullets.get(x).get(y) > 0) {
						// if the bullet does not reach the border of the
						// board
						if (bullets.get(x).get(y) < boardHeight - 1) {
							// if under the bullet is filled cell
							if (board.getCell(x, bullets.get(x).get(y)) == Cell.Full) {
								if (ofBullets) {// flightOfBullets
									board = removeCell(board, x, bullets.get(x)
											.get(y));
								} else {// flightOfMud
									// if previous cell is empty
									if (board.getCell(x,
											bullets.get(x).get(y) - 1) == Cell.Empty) {
										// stop the bullet before the cell
										board = addCellAndCheckLine(board, x,
												bullets.get(x).get(y) - 1);
									}
								}
								// remove the bullet
								bullets.get(x).set(y, 0);
							} else {
								// otherwise, continues flying
								board.setCell(Cell.Blink, x, bullets.get(x)
										.getAndIncrement(y));
							}
						} else if (bullets.get(x).get(y) == boardHeight - 1) {
							if (ofBullets) { // flightOfBullets
								// if under the bullet is filled cell
								if (board.getCell(x, bullets.get(x).get(y)) == Cell.Full) {
									board = removeCell(board, x, bullets.get(x)
											.get(y));
								}
								// show the bullet
								board.setCell(Cell.Blink, x, bullets.get(x)
										.get(y));
								fireBoardChanged(board);
								sleep(ANIMATION_DELAY / 2);
								// remove the bullet
								board.setCell(Cell.Empty, x, bullets.get(x)
										.get(y));
							} else {// flightOfMud
								// if under the bullet is filled cell
								if (board.getCell(x, bullets.get(x).get(y)) == Cell.Full) {
									// and previous cell is empty
									if (board.getCell(x,
											bullets.get(x).get(y) - 1) == Cell.Empty) {
										// stop the bullet before the cell
										board = addCellAndCheckLine(board, x,
												bullets.get(x).get(y) - 1);
									}
								} else {
									// stop the bullet on the border of the
									// board
									board = addCellAndCheckLine(board, x,
											bullets.get(x).get(y));
								}
							}
							// remove the bullet
							bullets.get(x).set(y, 0);
						}
						fireBoardChanged(board);
					}
				}
			}
			setBoard(board);
		}
	}

	/**
	 * Processing the flight of bullets (destruction mode)
	 */
	void flightOfBullets() {
		flight(true);
	}

	/**
	 * Processing the flight of mud bullets (creation mode)
	 */
	void flightOfMud() {
		flight(false);
	}

	/**
	 * Filling the bullets array with zeros
	 * 
	 * @param bullets
	 *            the bullets array
	 */
	void initBullets(AtomicReferenceArray<AtomicIntegerArray> bullets) {
		int bulletsArrayHeight = boardHeight - gun.getHeight();
		int[] array = new int[bulletsArrayHeight];
		Arrays.fill(array, 0);

		for (int i = 0; i < bullets.length(); i++) {
			bullets.set(i, new AtomicIntegerArray(array));
		}
	}

	@Override
	void loss(int x, int y) {
		synchronized (lock) {
			Board board = getBoard();
			clearBullets(board);
			setBoard(board);
		}
		super.loss(x, y);
	}

	/**
	 * Move the gun to a new location
	 * 
	 * @param x
	 *            x-coordinate position of the new location
	 * @param y
	 *            y-coordinate position of the new location
	 * @return {@code false} if there was a collision, otherwise {@code true}
	 */
	boolean moveGun(int x, int y) {
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) return true;

		synchronized (lock) {
			Board board = getBoard();
			// Erase the gun to not interfere with the checks
			board = drawShape(board, curX, curY, gun, Cell.Empty);

			if (checkCollision(board, gun, x, y)) return false;

			// draw the gun on the new place
			setBoard(drawShape(board, x, y, gun, Cell.Full));

			curX = x;
			curY = y;
		}

		return true;
	}

	/**
	 * Erasing a cell from a board and increasing scores
	 * 
	 * @param board
	 *            a board
	 * @param x
	 *            x-coordinate of the cell
	 * @param y
	 *            y-coordinate of the cell
	 * @return a board after erasing a cell
	 */
	Board removeCell(Board board, int x, int y) {
		GameSound.playEffect(Effects.hit_cell);
		synchronized (lock) {
			// remove the cell
			board.setCell(Cell.Empty, x, y);
		}
		// increase scores
		setScore(getScore() + 1);
		return board;
	}

	/**
	 * Removing of a filled lines
	 * 
	 * @param board
	 *            a board
	 * @param y
	 *            number of a checked line (y-coordinate)
	 * @return a board after removing of a filled lines
	 */
	private Board removeFullLines(Board board, int y) {
		Board result = board;

		synchronized (lock) {
			if (isFullLine(result, y)) {
				// change status for stopping other work
				Status prevStatus = getStatus();
				setStatus(Status.DoSomeWork);

				// reset the bullets
				initBullets(bullets);
				clearBullets(result);

				GameSound.playEffect(Effects.remove_line);

				animatedClearLine(result, curX, y);

				// erase the gun from the board before dropping ups lines
				result = drawShape(result, curX, curY, gun, Cell.Empty);

				// drop the lines up on the filled line
				for (int i = y; i > 0; i--) {
					for (int j = 0; j < boardWidth; j++) {
						result.setCell(result.getCell(j, i - 1), j, i);
					}
				}

				// restore the gun after dropping ups lines
				result = drawShape(result, curX, curY, gun, Cell.Full);

				// increase scores
				setScore(getScore() + 10);

				// restore previous status
				setStatus(prevStatus);
			}
		}
		return result;
	}

	@Override
	void win() {
		synchronized (lock) {
			Board board = getBoard();
			clearBullets(board);
			setBoard(board);
		}
		super.win();
	}

}
