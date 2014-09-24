package com.kry.brickgame.games;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.ÑharacterShape;
import com.kry.brickgame.shapes.ÑharacterShape.Ñharacters;
import com.kry.brickgame.splashes.GunSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class GunGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new GunSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 16;

	private ÑharacterShape gun;

	/**
	 * Array that stores the coordinates of the bullets
	 */
	private volatile int[][] bullets;

	// decrease speed from the original
	private final int FIRST_LEVEL_SPEED = 600;
	private final int TENTH_LEVEL_SPEED = 240;

	@Override
	protected int getFIRST_LEVEL_SPEED() {
		return FIRST_LEVEL_SPEED;
	}

	@Override
	protected int getTENTH_LEVEL_SPEED() {
		return TENTH_LEVEL_SPEED;
	}

	/**
	 * Kind of game
	 * <p>
	 * {@code true} - the shot creates a new cell, {@code false} - the shot
	 * destroys a cell
	 */
	private boolean isCreationMode;
	/**
	 * Number of barrels
	 * <p>
	 * {@code true} - two barrels, {@code false} - one barrel
	 */
	private boolean hasTwoSmokingBarrels;
	/**
	 * Whether to shift the board?
	 */
	private boolean isShiftingBoard;
	/**
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;

	/**
	 * The Gun Game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game type of the game:
	 *            <ol>
	 *            <li>destruction mode: one gun;
	 *            <li>destruction mode: one gun and shifts the board;
	 *            <li>destruction mode: two guns;
	 *            <li>destruction mode: two guns and shifts the board;
	 *            <li>creation mode: one gun;
	 *            <li>creation mode: one gun and shifts the board;
	 *            <li>creation mode: two guns;
	 *            <li>creation mode: two guns and shifts the board;
	 *            <li>destruction mode: one gun, the board is upside down;
	 *            <li>destruction mode: one gun and shifts the board, the board
	 *            is upside down;
	 *            <li>destruction mode: two guns, the board is upside down;
	 *            <li>destruction mode: two guns and shifts the board, the board
	 *            is upside down;
	 *            <li>creation mode: one gun, the board is upside down;
	 *            <li>creation mode: one gun and shifts the board, the board is
	 *            upside down;
	 *            <li>creation mode: two guns, the board is upside down;
	 *            <li>creation mode: two guns and shifts the board, the board is
	 *            upside down;
	 */
	public GunGame(int speed, int level, int type) {
		super(speed, level, type);
		setStatus(Status.None);

		gun = new ÑharacterShape(Ñharacters.Gun);

		bullets = new int[boardHeight - gun.getHeight()][boardWidth];
		initBullets(bullets);

		// ==define the parameters of the types of game==
		// for types 5-8 and 13-16
		isCreationMode = ((getType() >= 5) && (getType() <= 8))
				|| ((getType() >= 13) && (getType() <= 16));
		// for every 3rd and 4th type of game
		hasTwoSmokingBarrels = ((getType() % 4 == 3) || (getType() % 4 == 0));
		// for every even type of game
		isShiftingBoard = (getType() % 2 == 0);
		// for types 8-16
		drawInvertedBoard = (getType() > 8);
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

		// create timer for bullets
		Timer bulletSwarm = new Timer("BulletSwarm", true);
		bulletSwarm.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isCreationMode)
					flightOfMud();
				else
					flightOfBullets();
			}
		}, 0, ANIMATION_DELAY / 2);

		while (!interrupted() && (getStatus() != Status.GameOver)) {

			int currentSpeed = getSpeed(true);

			// increase speed in CreationMode
			if (isCreationMode) {
				currentSpeed *= 5;
				// decrease speed if has two guns
			} else if (hasTwoSmokingBarrels) {
				currentSpeed -= ANIMATION_DELAY * 2;
			}

			if (getStatus() != Status.Paused) {
				// moving
				if (elapsedTime(currentSpeed)) {
					// try drop down lines
					if (!droppingDown()) {
						kaboom(curX, curY);
						setLives(getLives() - 1);
						if (getLives() > 0) {
							animatedClearBoard(true);// fast
							loadLevel();
						} else {
							gameOver();
						}
					}
				}
			}
			// processing of key presses
			processKeys();
		}

		bulletSwarm.cancel();
	}

	/**
	 * Loading or reloading the specified level
	 */
	private void loadLevel() {
		// starting position - the middle of the bottom border of the board
		curX = boardWidth / 2 - 1;
		curY = 0;

		// draws a rows on the top of the border
		setBoard(addLines(getBoard(), getLevel()));
		// draws the gun
		moveGun(curX, curY);
	}

	/**
	 * Shift the board horizontally on a random direction
	 */
	private void shiftBoard() {
		Random r = new Random();
		Board board = getBoard().clone();

		// erase the gun to not interfere
		board = drawShape(board, curX, curY, gun, Cell.Empty);
		// remove bullets from the board
		clearBullets(board);
		// shifts the board
		board = horizontalShift(board, (r.nextBoolean()) ? (-1) : (1));
		// return the gun to the board
		board = drawShape(board, curX, curY, gun, Cell.Full);
		setBoard(board);
	}

	/**
	 * Drop down one row
	 * 
	 * @return {@code true} there is no collision with the gun, otherwise
	 *         {@code false}
	 */
	private boolean droppingDown() {
		Board board = getBoard().clone();

		// erase the gun to not interfere with the checks
		board = drawShape(board, curX, curY, gun, Cell.Empty);
		// add line
		board = addLines(board, 1);
		// check whether the line is dropped to the gun
		boolean result = true;
		for (int i = 0; i < boardWidth; i++) {
			if (board.getCell(i, curY + gun.maxY()) == Cell.Full) {
				result = false;
				break;
			}
		}
		// return the gun to the board
		board = drawShape(board, curX, curY, gun, Cell.Full);
		setBoard(board);

		// for even types of game, shifts the board
		if (isShiftingBoard) {
			sleep(ANIMATION_DELAY);
			shiftBoard();
		}

		return result;
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
	private boolean moveGun(int x, int y) {
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
	private void clearBullets(Board board) {
		for (int i = 0; i < boardWidth; i++)
			for (int j = 0; j < boardHeight; j++)
				// bullet is blink
				if (board.getCell(i, j) == Cell.Blink)
					board.setCell(Cell.Empty, i, j);
	}

	/**
	 * Processing the flight of bullets (destruction mode)
	 */
	private synchronized void flightOfBullets() {
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
	private synchronized void flightOfMud() {
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
	private void fire(final int x, final int y, boolean hasTwoSmokingBarrels) {
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

	/**
	 * Add randomly generated lines on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param linesCount
	 *            count of added lines
	 * @return the board after adding lines
	 */
	private Board addLines(Board board, int linesCount) {
		// clear bullets
		clearBullets(board);
		return addLines(board, boardHeight - 1, linesCount, false);
	}

	@Override
	protected void setScore(int score) {
		int oldThousands = getScore() / 1000;

		super.setScore(score);

		// when a sufficient number of points changes the speed and the level
		if (getScore() / 1000 > oldThousands) {
			setSpeed(getSpeed() + 1);
			if (getSpeed() == 1) {
				setLevel(getLevel() + 1);

				animatedClearBoard(true);

				Board board = getBoard();
				for (int i = 0; i < getLevel(); i++) {
					// erase the gun
					board = drawShape(board, curX, curY, gun, Cell.Empty);
					// add line
					board = addLines(getBoard(), 1);
					// return the gun
					board = drawShape(board, curX, curY, gun, Cell.Full);

					setBoard(board);
					sleep(ANIMATION_DELAY * 3);
				}
			}
		}
	}

	@Override
	protected synchronized void fireBoardChanged(Board board) {
		Board newBoard = board.clone();

		// draws the inverted board
		if (drawInvertedBoard) {
			for (int i = 0; i < board.getHeight(); i++) {
				newBoard.setRow(board.getRow(i), board.getHeight() - i - 1);
			}
		}

		super.fireBoardChanged(newBoard);
	}

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		super.processKeys();

		if (getStatus() == Status.Running) {

			if (keys.contains(KeyPressed.KeyLeft)) {
				moveGun(curX - 1, curY);
				if (isCreationMode)
					keys.remove(KeyPressed.KeyLeft);
				else
					sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				moveGun(curX + 1, curY);
				if (isCreationMode)
					keys.remove(KeyPressed.KeyRight);
				else
					sleep(ANIMATION_DELAY * 2);
			}
			if ((keys.contains(KeyPressed.KeyDown))
					|| (keys.contains(KeyPressed.KeyUp))) {
				droppingDown();
				sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				fire(curX, curY + gun.maxY() + 1, hasTwoSmokingBarrels); 
				if (isCreationMode)
					keys.remove(KeyPressed.KeyRotate);
				else
					sleep(ANIMATION_DELAY);
			}
		}
	}

}
