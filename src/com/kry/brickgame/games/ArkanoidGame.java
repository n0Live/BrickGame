package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.playEffect;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.boards.BricksWall;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.shapes.ArkanoidPlatformShape;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.ArkanoidSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class ArkanoidGame extends GameWithLives {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new ArkanoidSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 64;

	/**
	 * The bit, who which kicks the ball
	 */
	private ArkanoidPlatformShape platform;
	/**
	 * The ball breaking bricks
	 */
	private Shape ball;
	/**
	 * X-coordinate position of the ball
	 */
	private int ballX;
	/**
	 * Y-coordinate position of the ball
	 */
	private int ballY;
	/**
	 * The vertical direction of the ball
	 */
	private RotationAngle ballVerticalDirection;
	/**
	 * The horizontal direction of the ball
	 */
	private RotationAngle ballHorizontalDirection;
	/**
	 * The bricks wall
	 */
	private volatile BricksWall bricks;
	/**
	 * Y-coordinate position on the board for the second platform
	 */
	private int secY;
	/**
	 * X-coordinate for insertion of the bricks wall
	 */
	private int bricksX;
	/**
	 * Y-coordinate for insertion of the bricks wall
	 */
	private int bricksY;
	/**
	 * Use preloaded bricks wall or generate new ones?
	 */
	private boolean usePreloadedBricks;
	/**
	 * Whether to shift the bricks wall?
	 */
	private boolean isShiftingBricks;
	/**
	 * Use the platform on both sides of the board?
	 */
	private boolean useDoubleSidedPlatform;
	/**
	 * Is this the beginning of the level?
	 */
	private boolean isStartOfLevel;

	/**
	 * The Arkanoid
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param rotation
	 *            direction of rotation
	 * @param type
	 *            type of the game:
	 *            <ol>
	 *            <li>arkanoid with a 4-cell platform and preloaded bricks;
	 *            <li>arkanoid with a 3-cell platform and preloaded bricks;
	 *            <li>arkanoid with a 2-cell platform and preloaded bricks;
	 *            <li>arkanoid with a 1-cell platform and preloaded bricks;
	 *            <li>arkanoid with a 4-cell platform, preloaded and shifting
	 *            bricks;
	 *            <li>arkanoid with a 3-cell platform, preloaded and shifting
	 *            bricks;
	 *            <li>arkanoid with a 2-cell platform, preloaded and shifting
	 *            bricks;
	 *            <li>arkanoid with a 1-cell platform, preloaded and shifting
	 *            bricks;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board
	 *            and preloaded bricks;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board
	 *            and preloaded bricks;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board
	 *            and preloaded bricks;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board
	 *            and preloaded bricks;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks;
	 *            <li>arkanoid with a 4-cell platform and randomly generated
	 *            bricks;
	 *            <li>arkanoid with a 3-cell platform and randomly generated
	 *            bricks;
	 *            <li>arkanoid with a 2-cell platform and randomly generated
	 *            bricks;
	 *            <li>arkanoid with a 1-cell platform and randomly generated
	 *            bricks;
	 *            <li>arkanoid with a 4-cell platform, randomly generated and
	 *            shifting bricks;
	 *            <li>arkanoid with a 3-cell platform, randomly generated and
	 *            shifting bricks;
	 *            <li>arkanoid with a 2-cell platform, randomly generated and
	 *            shifting bricks;
	 *            <li>arkanoid with a 1-cell platform, randomly generated and
	 *            shifting bricks;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board
	 *            and randomly generated bricks;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board
	 *            and randomly generated bricks;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board
	 *            and randomly generated bricks;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board
	 *            and randomly generated bricks;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks;
	 *            <li>arkanoid with a 4-cell platform and preloaded bricks, the
	 *            board is upside down;
	 *            <li>arkanoid with a 3-cell platform and preloaded bricks, the
	 *            board is upside down;
	 *            <li>arkanoid with a 2-cell platform and preloaded bricks, the
	 *            board is upside down;
	 *            <li>arkanoid with a 1-cell platform and preloaded bricks, the
	 *            board is upside down;
	 *            <li>arkanoid with a 4-cell platform, preloaded and shifting
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 3-cell platform, preloaded and shifting
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 2-cell platform, preloaded and shifting
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 1-cell platform, preloaded and shifting
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board
	 *            and preloaded bricks, the board is upside down;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board
	 *            and preloaded bricks, the board is upside down;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board
	 *            and preloaded bricks, the board is upside down;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board
	 *            and preloaded bricks, the board is upside down;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks, the board is upside down;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks, the board is upside down;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks, the board is upside down;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board,
	 *            preloaded and shifting bricks, the board is upside down;
	 *            <li>arkanoid with a 4-cell platform and randomly generated
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 3-cell platform and randomly generated
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 2-cell platform and randomly generated
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 1-cell platform and randomly generated
	 *            bricks, the board is upside down;
	 *            <li>arkanoid with a 4-cell platform, randomly generated and
	 *            shifting bricks, the board is upside down;
	 *            <li>arkanoid with a 3-cell platform, randomly generated and
	 *            shifting bricks, the board is upside down;
	 *            <li>arkanoid with a 2-cell platform, randomly generated and
	 *            shifting bricks, the board is upside down;
	 *            <li>arkanoid with a 1-cell platform, randomly generated and
	 *            shifting bricks, the board is upside down;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board
	 *            and randomly generated bricks, the board is upside down;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board
	 *            and randomly generated bricks, the board is upside down;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board
	 *            and randomly generated bricks, the board is upside down;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board
	 *            and randomly generated bricks, the board is upside down;
	 *            <li>arkanoid with 4-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks, the board is upside
	 *            down;
	 *            <li>arkanoid with 3-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks, the board is upside
	 *            down;
	 *            <li>arkanoid with 2-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks, the board is upside
	 *            down;
	 *            <li>arkanoid with 1-cell platforms on both sides of the board,
	 *            randomly generated and shifting bricks, the board is upside
	 *            down;
	 */
	public ArkanoidGame(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);

		// platform size depends on the type of game
		if (getType() % 4 == 1)
			platform = new ArkanoidPlatformShape(3);// 4
		else if (getType() % 4 == 2)
			platform = new ArkanoidPlatformShape(2);// 3
		else if (getType() % 4 == 3)
			platform = new ArkanoidPlatformShape(1);// 2
		else
			platform = new ArkanoidPlatformShape(0);// 1

		// initialize the ball
		this.ball = BallUtils.getBall(Cell.Full);

		// for types 1-16 and 33-48
		usePreloadedBricks = ((getType() <= 16))
				|| ((getType() >= 33) && (getType() <= 48));
		// for every 5-8 types
		isShiftingBricks = ((getType() % 8 >= 5) || (getType() % 8 == 0));
		// for every 9-16 types
		useDoubleSidedPlatform = ((getType() % 16 >= 9) || (getType() % 16 == 0));
		// for types 32-64
		setDrawInvertedBoard((getType() > 32));
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		loadNewLevel();

		// create timer for shift the bricks
		Timer shiftBricksTimer = new Timer("ShiftBricksTimer", true);
		if (isShiftingBricks) {
			shiftBricksTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if (getStatus() == Status.Running) {
						shiftBricks();
					}
				}
			}, 0, getFIRST_LEVEL_SPEED());
		}

		int currentSpeed;
		while (!interrupted() && (getStatus() != Status.GameOver)) {
			// change the speed in depending of the platform size
			switch (platform.getType()) {
			case 0:// 1
				currentSpeed = getSpeed(true) * 5 / 4;// slower
				break;
			case 1:// 2
				currentSpeed = getSpeed(true);
				break;
			case 2:// 3
				currentSpeed = getSpeed(true) * 3 / 4;
				break;
			default:// 4
				currentSpeed = getSpeed(true) / 2;// faster
				break;
			}

			// decrease game speed if use double sided platform
			if (useDoubleSidedPlatform)
				currentSpeed += ANIMATION_DELAY;

			if ((getStatus() != Status.Paused) && (elapsedTime(currentSpeed))) {
				if (!isStartOfLevel)
					moveBall();
			}
			// processing of key presses
			processKeys();
		}

		if (isShiftingBricks) {
			shiftBricksTimer.cancel();
		}
	}

	/**
	 * Loading or reloading the specified level
	 * 
	 * @param setBricks
	 *            if {@code true} then the bricks wall will be re-created
	 */
	private void loadLevel(boolean setBricks) {
		curX = boardWidth / 2 - 1;
		curY = 0;
		secY = boardHeight - 1;

		ballX = curX;
		ballY = curY + 1;
		ballVerticalDirection = UP;
		ballHorizontalDirection = (getRotation() == Rotation.Clockwise) ? RIGHT
				: LEFT;

		// create the bricks wall
		if (setBricks) {
			bricks = new BricksWall(getLevel(), usePreloadedBricks);

			bricksX = 0;
			bricksY = (useDoubleSidedPlatform) ? ((boardHeight - bricks
					.getHeight()) / 2) : (boardHeight - bricks.getHeight());

			insertCellsToBoard(getBoard(), bricks.getBoard(), bricksX, bricksY);
		}
		// init ball
		drawBall(getBoard(), ballX, ballY);

		isStartOfLevel = true;

		// init platform
		movePlatform(curX);

		setStatus(Status.Running);
	}

	@Override
	protected void loadNewLevel() {
		loadLevel(true);
		super.loadNewLevel();
	}

	@Override
	protected void reloadLevel() {
		loadLevel(false);
	}

	/**
	 * Shift the bricks wall horizontally
	 */
	private synchronized void shiftBricks() {
		// shift bricks
		bricks.shift((getRotation() == Rotation.Clockwise) ? 1 : -1);
		// insert shifted bricks to the board
		Board board = getBoard().clone();
		insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY);
		// re-drawing the ball
		setBoard(drawBall(board, ballX, ballY));
	}

	/**
	 * Move the platform to a new location
	 * 
	 * @param x
	 *            x-coordinate position of the new location
	 * @return {@code true} if the movement succeeded, otherwise {@code false}
	 */
	private boolean movePlatform(int x) {
		if ((x + platform.minX() < 0) || (x + platform.maxX() >= boardWidth))
			return false;

		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the platform and draw on the new place
		board = drawShape(board, curX, curY, platform, Cell.Empty);
		board = drawShape(board, x, curY, platform, Cell.Full);
		if (useDoubleSidedPlatform) {
			board = drawShape(board, curX, secY, platform, Cell.Empty);
			board = drawShape(board, x, secY, platform, Cell.Full);
		}

		// Move the ball, if it upon the platform
		if (((ballY == curY + 1)//
				|| (useDoubleSidedPlatform && (ballY == secY - 1)))//
				&& ((ballX >= curX + platform.minX())//
				&& (ballX <= curX + platform.maxX()))) {

			int newBallX = ballX + (x - curX);
			board = drawBall(board, newBallX, ballY);
		}

		setBoard(board);
		curX = x;

		return true;
	}

	/**
	 * Draw the ball to a new position and erase it from the old position
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate of the new position
	 * @param y
	 *            y-coordinate of the new position
	 * @return the board after drawing the ball
	 */
	private Board drawBall(Board board, int x, int y) {
		// draw the ball
		Board newBoard = BallUtils.drawBall(board, ball, ballX, ballY, x, y);

		// replace current coordinates to the new ones
		ballX = x;
		ballY = y;

		return newBoard;
	}

	/**
	 * Move the ball to a new position in movement direction
	 */
	private void moveBall() {
		Board board = getBoard();

		Point newCoords;

		// set new coordinates from directions
		newCoords = BallUtils.moveBall(ballX, ballY, ballHorizontalDirection,
				ballVerticalDirection);

		// if the ball fall off the board then game_over live
		if ((newCoords.y < 0)
				|| (useDoubleSidedPlatform && (newCoords.y >= boardHeight))) {
			loss();
			return;
		}

		// whether the ball bounced off the surface?
		boolean bounce = false;

		// check collision with the board's borders
		if (newCoords.y >= boardHeight) {
			ballVerticalDirection = DOWN;
			bounce = true;
		}
		if ((newCoords.x < 0) || (newCoords.x >= boardWidth)) {
			ballHorizontalDirection = ballHorizontalDirection.getOpposite();
			bounce = true;
		}
		newCoords = BallUtils.moveBall(ballX, ballY, ballHorizontalDirection,
				ballVerticalDirection);

		// try to break brick under current position of the ball
		breakBrick(board, ballX, ballY);

		// check collision with the bricks or the platform
		if ((board.getCell(newCoords.x, ballY) != Cell.Empty)
				|| (board.getCell(ballX, newCoords.y) != Cell.Empty)
				|| (board.getCell(newCoords.x, newCoords.y) != Cell.Empty)) {

			// at first, checked the cells with whom the ball touches the edges
			if ((board.getCell(newCoords.x, ballY) != Cell.Empty)
					|| (board.getCell(ballX, newCoords.y) != Cell.Empty)) {
				if (board.getCell(newCoords.x, ballY) != Cell.Empty) {
					ballHorizontalDirection = ballHorizontalDirection
							.getOpposite();
					if (!isPlatform(newCoords.x, ballY)) {
						breakBrick(board, newCoords.x, ballY);
					} else {
						bounce = true;
					}
				}
				if (board.getCell(ballX, newCoords.y) != Cell.Empty) {
					ballVerticalDirection = ballVerticalDirection.getOpposite();
					if (!isPlatform(ballX, newCoords.y)) {
						breakBrick(board, ballX, newCoords.y);
					} else {
						bounce = true;
					}
				}
				// if there are none,then processed the cell with whom the ball
				// touches the apex
			} else {
				ballHorizontalDirection = ballHorizontalDirection.getOpposite();
				ballVerticalDirection = ballVerticalDirection.getOpposite();
				if (!isPlatform(newCoords.x, newCoords.y)) {
					breakBrick(board, newCoords.x, newCoords.y);
				} else {
					bounce = true;
				}
			}

			newCoords.x = ballX;
			newCoords.y = ballY;
		}

		if (bounce) {
			playEffect(Effects.turn);
		}

		board = drawBall(board, newCoords.x, newCoords.y);
		setBoard(board);

		// when destroying all bricks
		if (bricks.getBricksCount() <= 0)
			win();
	}

	/**
	 * Processing breaking bricks
	 * 
	 * @param board
	 *            the board for drawing the bricks wall after breaking bricks
	 * @param x
	 *            x-coordinate of the brick for breaking
	 * @param y
	 *            y-coordinate of the brick for breaking
	 */
	private void breakBrick(Board board, int x, int y) {
		// coordinates are given to the bricks wall's grid
		int givenX = x - bricksX;
		int givenY = y - bricksY;

		if (bricks.breakBrick(givenX, givenY)) {
			playEffect(Effects.hit_cell);

			insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY);

			// increase scores
			setScore(getScore() + 1);
		}
	}

	/**
	 * Checks for the platform to the coordinates {@code x, y}
	 * 
	 * @param x
	 *            x-coordinate to check
	 * @param y
	 *            y-coordinate to check
	 * @return {@code true} if these coordinates is the platform, otherwise -
	 *         {@code false}
	 */
	private boolean isPlatform(int x, int y) {
		return ((x >= curX + platform.minX())//
				&& (x <= curX + platform.maxX())//
		&& ((y == curY) || (useDoubleSidedPlatform && (y == secY))));
	}

	/**
	 * Drawing effect of the explosion and decreasing lives
	 */
	private void loss() {
		// saves current bricks wall
		Board curBricks = bricks.clone();

		super.loss(ballX, ballY);

		// restores saved bricks wall
		insertCellsToBoard(getBoard(), curBricks.getBoard(), bricksX, bricksY);
		fireBoardChanged(getBoard());
	}

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		super.processKeys();

		int movementDelay;

		// decreasing the platfom's movement speed for the 1- and 2-cell
		// platform
		switch (platform.getType()) {
		case 0:// 1
			movementDelay = ANIMATION_DELAY * 3;
			break;
		case 1:// 2
			movementDelay = ANIMATION_DELAY * 2;
			break;
		default:// 3-4
			movementDelay = ANIMATION_DELAY;
		}

		if (getStatus() == Status.Running) {

			if (keys.contains(KeyPressed.KeyLeft)) {
				if (movePlatform(curX - 1)) {
					playEffect(Effects.move);
					sleep(movementDelay);
				}
			}

			if (keys.contains(KeyPressed.KeyRight)) {
				if (movePlatform(curX + 1)) {
					playEffect(Effects.move);
					sleep(movementDelay);
				}
			}

			if (keys.contains(KeyPressed.KeyRotate)) {
				if (isStartOfLevel) {
					isStartOfLevel = false;
					playEffect(Effects.turn);
				}
				moveBall();
				sleep(ANIMATION_DELAY * 2);
			}
		}
	}

}
