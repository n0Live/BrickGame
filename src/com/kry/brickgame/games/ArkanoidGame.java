package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.DOWN;
import static com.kry.brickgame.games.GameConsts.LEFT;
import static com.kry.brickgame.games.GameConsts.RIGHT;
import static com.kry.brickgame.games.GameConsts.UP;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.setKeyDelay;

import java.awt.Point;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.boards.BricksWall;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.shapes.ArkanoidPlatformShape;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.ArkanoidSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 */
public class ArkanoidGame extends GameWithLives {
	private static final long serialVersionUID = 8505150526466946063L;
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
	private final Shape ball;
	/**
	 * X-coordinate position of the ball
	 */
	private volatile int ballX;
	/**
	 * Y-coordinate position of the ball
	 */
	private volatile int ballY;
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
	private volatile int bricksX;
	/**
	 * Y-coordinate for insertion of the bricks wall
	 */
	private volatile int bricksY;
	/**
	 * Use preloaded bricks wall or generate new ones?
	 */
	private final boolean usePreloadedBricks;
	/**
	 * Whether to shift the bricks wall?
	 */
	private final boolean isShiftingBricks;
	/**
	 * Use the platform on both sides of the board?
	 */
	private final boolean useDoubleSidedPlatform;
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
		if (getType() % 4 == 1) {
			platform = new ArkanoidPlatformShape(3);// 4
		} else if (getType() % 4 == 2) {
			platform = new ArkanoidPlatformShape(2);// 3
		} else if (getType() % 4 == 3) {
			platform = new ArkanoidPlatformShape(1);// 2
		} else {
			platform = new ArkanoidPlatformShape(0);// 1
		}
		
		// initialize the ball
		ball = BallUtils.getBall(Cell.Full);
		
		// for types 1-16 and 33-48
		usePreloadedBricks = ((getType() <= 16)) || ((getType() >= 33) && (getType() <= 48));
		// for every 5-8 types
		isShiftingBricks = ((getType() % 8 >= 5) || (getType() % 8 == 0));
		// for every 9-16 types
		useDoubleSidedPlatform = ((getType() % 16 >= 9) || (getType() % 16 == 0));
		// for types 32-64
		setDrawInvertedBoard((getType() > 32));
		
		loadNewLevel();
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
			
			setBoard(insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY));
			
			// increase scores
			setScore(getScore() + 1);
		}
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
	
	@Override
	protected int getSpeedOfFirstLevel() {
		return 400;
	}
	
	@Override
	protected int getSpeedOfTenthLevel() {
		return 80;
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
		ballHorizontalDirection = (getRotation() == Rotation.Clockwise) ? RIGHT : LEFT;
		
		// create the new bricks wall
		if (setBricks) {
			bricks = new BricksWall(getLevel(), usePreloadedBricks);
			
			bricksX = 0;
			bricksY = (useDoubleSidedPlatform) ? ((boardHeight - bricks.getHeight()) / 2)
					: (boardHeight - bricks.getHeight());
		}
		
		Board board = getBoard();
		board = insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY);
		
		// init ball
		drawBall(board, ballX, ballY);
		
		setBoard(board);
		
		isStartOfLevel = true;
		
		// init platform
		movePlatform(curX);
	}
	
	@Override
	protected void loadNewLevel() {
		loadLevel(true);
		super.loadNewLevel();
	}
	
	/**
	 * Drawing effect of the explosion and decreasing lives
	 */
	private void loss() {
		super.loss(ballX, ballY);
	}
	
	/**
	 * Move the ball to a new position in movement direction
	 */
	private void moveBall() {
		Point newCoords;
		// set new coordinates from directions
		newCoords = BallUtils
				.moveBall(ballX, ballY, ballHorizontalDirection, ballVerticalDirection);
		
		// if the ball fall off the board then game_over live
		if ((newCoords.y < 0) || (useDoubleSidedPlatform && (newCoords.y >= boardHeight))) {
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
		newCoords = BallUtils
				.moveBall(ballX, ballY, ballHorizontalDirection, ballVerticalDirection);
		
		synchronized (lock) {
			Board board = getBoard();
			// try to break brick under current position of the ball
			breakBrick(board, ballX, ballY);
			
			// check collision with the bricks or the platform
			if ((board.getCell(newCoords.x, ballY) != Cell.Empty)
					|| (board.getCell(ballX, newCoords.y) != Cell.Empty)
					|| (board.getCell(newCoords.x, newCoords.y) != Cell.Empty)) {
				
				// at first, checked the cells with whom the ball touches the
				// edges
				if ((board.getCell(newCoords.x, ballY) != Cell.Empty)
						|| (board.getCell(ballX, newCoords.y) != Cell.Empty)) {
					if (board.getCell(newCoords.x, ballY) != Cell.Empty) {
						ballHorizontalDirection = ballHorizontalDirection.getOpposite();
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
					// if there are none, then processed the cell with whom the
					// ball touches the apex
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
			board = drawBall(board, newCoords.x, newCoords.y);
			setBoard(board);
		}
		
		if (bounce) {
			playEffect(Effects.turn);
		}
		
		// when destroying all bricks
		if (bricks.getBricksCount() <= 0) {
			win();
		}
	}
	
	/**
	 * Move the platform to a new location
	 * 
	 * @param x
	 *            x-coordinate position of the new location
	 * @return {@code true} if the movement succeeded, otherwise {@code false}
	 */
	private boolean movePlatform(int x) {
		if ((x + platform.minX() < 0) || (x + platform.maxX() >= boardWidth)) return false;
		
		synchronized (lock) {
			Board board = getBoard();
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
		}
		
		return true;
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None) return;
		
		super.processKeys();
		
		if (getStatus() == Status.Running) {
			// decreasing the platfom's movement speed in depending of the
			// platform size (slower when used small platform)
			final float slowestSpeed = 1.75f;
			final float speedStep = 0.25f;
			int movementDelay = Math.round(ANIMATION_DELAY
					* (slowestSpeed - platform.getType() * speedStep));
			
			if (containsKey(KeyPressed.KeyLeft)) {
				if (movePlatform(curX - 1)) {
					playEffect(Effects.move);
					setKeyDelay(KeyPressed.KeyLeft, movementDelay);
				}
			}
			if (containsKey(KeyPressed.KeyRight)) {
				if (movePlatform(curX + 1)) {
					playEffect(Effects.move);
					setKeyDelay(KeyPressed.KeyRight, movementDelay);
				}
			}
			if (containsKey(KeyPressed.KeyRotate)) {
				if (isStartOfLevel) {
					isStartOfLevel = false;
					playEffect(Effects.turn);
				}
				moveBall();
				setKeyDelay(KeyPressed.KeyRotate, ANIMATION_DELAY * 2);
			}
		}
	}
	
	@Override
	protected void reloadLevel() {
		loadLevel(false);
		super.loadNewLevel();
	}
	
	/**
	 * Launching the game
	 */
	@Override
	public void run() {
		super.run();
		
		// create timer for shift the bricks
		ScheduledExecutorService shiftBricksTimer = null;
		if (isShiftingBricks) {
			shiftBricksTimer = Executors.newSingleThreadScheduledExecutor();
			shiftBricksTimer.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					if (!Thread.currentThread().isInterrupted() && getStatus() == Status.Running) {
						shiftBricks();
					}
				}
			}, 0, getSpeedOfFirstLevel(), TimeUnit.MILLISECONDS);
		}
		
		final float slowestSpeed = 1.20f;
		final float speedStep = 0.20f;
		
		while (!Thread.currentThread().isInterrupted() && (getStatus() != Status.GameOver)) {
			if (getStatus() == Status.Running) {
				// change the speed in depending of the platform size
				// (slower when used small platform)
				float speedFactor = (slowestSpeed - platform.getType() * speedStep);
				int currentSpeed = Math.round(getSpeed(true) * speedFactor);
				
				// decrease game speed if use double sided platform
				if (useDoubleSidedPlatform) {
					currentSpeed += ANIMATION_DELAY;
				}
				
				if (elapsedTime(currentSpeed) && !isStartOfLevel) {
					moveBall();
				}
			}
			// processing of key presses
			processKeys();
		}
		
		if (shiftBricksTimer != null) {
			shiftBricksTimer.shutdownNow();
		}
	}
	
	/**
	 * Shift the bricks wall horizontally
	 */
	void shiftBricks() {
		// shift bricks
		bricks = bricks.shift((getRotation() == Rotation.Clockwise) ? 1 : -1);
		synchronized (lock) {
			// insert shifted bricks to the board
			Board board = getBoard();
			board = insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY);
			// re-drawing the ball
			setBoard(drawBall(board, ballX, ballY));
		}
	}
	
}
