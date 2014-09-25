package com.kry.brickgame.games;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.shapes.ÑharacterShape;
import com.kry.brickgame.shapes.ÑharacterShape.Ñharacters;
import com.kry.brickgame.splashes.ArkanoidSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class ArkanoidGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new ArkanoidSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 64;

	// ** Direction constants **
	private static final RotationAngle UP = RotationAngle.d0;
	private static final RotationAngle DOWN = RotationAngle.d180;
	private static final RotationAngle RIGHT = RotationAngle.d90;
	private static final RotationAngle LEFT = RotationAngle.d270;
	// **

	/**
	 * The bit, who which kicks the ball
	 */
	private ÑharacterShape platform;
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
	private volatile Board bricks;
	/**
	 * Number of bricks is not broken at the current level
	 */
	private int bricksCount;
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
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;

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
		setStatus(Status.None);

		// platform size depends on the type of game
		if (getType() % 4 == 1)
			platform = new ÑharacterShape(Ñharacters.Platform4);
		else if (getType() % 4 == 2)
			platform = new ÑharacterShape(Ñharacters.Platform3);
		else if (getType() % 4 == 3)
			platform = new ÑharacterShape(Ñharacters.Platform2);
		else
			platform = new ÑharacterShape(Ñharacters.Platform1);

		// initialize the ball
		this.ball = new Shape(1);
		ball.setCoord(0, new int[] { 0, 0 });
		ball.setFill(Cell.Full);

		// for types 1-16 and 33-48
		usePreloadedBricks = ((getType() <= 16))
				|| ((getType() >= 33) && (getType() <= 48));
		// for every 4-8 types
		isShiftingBricks = ((getType() % 8 >= 5) || (getType() % 8 == 0));
		// for every 9-16 types
		useDoubleSidedPlatform = ((getType() % 16 >= 9) || (getType() % 16 == 0));
		// for types 8-16
		drawInvertedBoard = (getType() > 32);
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();

		setLives(4);

		loadLevel(true);

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
			// if the size of the platform is 1, decrease the speed
			currentSpeed = (platform.getType() == Ñharacters.Platform1) ? getSpeed(true)
					: getSpeed(true) / 2;

			if ((getStatus() != Status.Paused) && (elapsedTime(currentSpeed))) {
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
			bricks = (usePreloadedBricks) ? setPreloadedBricks()
					: generateBricks();

			bricksX = 0;
			bricksY = (useDoubleSidedPlatform) ? ((boardHeight - bricks
					.getHeight()) / 2) : (boardHeight - bricks.getHeight());

			insertCells(getBoard(), bricks.getBoard(), bricksX, bricksY);
		}
		// init ball
		drawBall(getBoard(), ballX, ballY);
		// init platform
		movePlatform(curX);

		setStatus(Status.Running);
	}

	/**
	 * Creating the randomly generated bricks wall
	 * 
	 * @return the bricks wall
	 */
	private Board generateBricks() {
		Random r = new Random();

		// bricks wall's height from 6 on level 1, to 9 on level 10
		Board genBricks = new Board(boardWidth, getLevel() / 3 + 6);
		bricksCount = 0;

		// on one chance from 3 - the wall has a vertical symmetry
		boolean isVerticalSymmetry = (r.nextInt(3) == 0);
		// calculate center for a vertical symmetry
		// (for odd and even lines count)
		int centerPoint = genBricks.getHeight() / 2
				+ ((genBricks.getHeight() % 2 != 0) ? 1 : 0);

		for (int i = 0; i < genBricks.getHeight(); i++) {
			if (isVerticalSymmetry && (i >= centerPoint)) {

				genBricks.setRow(
						genBricks.getRow(genBricks.getHeight() - 1 - i), i);

				for (int j = 0; j < boardWidth / 2; j++) {
					// calculate count of the bricks
					if (genBricks.getCell(j, i) == Cell.Full)
						bricksCount += 2;
				}
			} else {
				for (int j = 0; j < boardWidth / 2; j++) {
					// the chance for full cell 2 to 3
					Cell brick = (r.nextInt(3) == 0) ? Cell.Empty : Cell.Full;
					genBricks.setCell(brick, j, i);
					genBricks.setCell(brick, boardWidth - 1 - j, i);

					// calculate count of the bricks
					if (brick == Cell.Full)
						bricksCount += 2;
				}
			}
		}

		return genBricks;
	}

	/**
	 * Setting predefined bricks wall
	 * 
	 * @return the bricks wall
	 */
	private Board setPreloadedBricks() {
		final Cell F = Cell.Full;
		final Cell E = Cell.Empty;
		final Cell[][][] preloadedBricks = new Cell[][][] {//
		{ // 1
						{ E, F, E, F, E, E, F, E, F, E },
						{ E, E, F, E, E, E, E, F, E, E },//
						{ E, F, F, F, E, E, F, F, F, E },
						{ F, F, F, F, F, F, F, F, F, F },
						{ F, F, F, E, F, F, E, F, F, F },
						{ E, F, F, F, F, F, F, F, F, E },
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, E, E, F, F, F, F, E, E, E }, },//
				{// 2
						{ F, F, F, F, F, F, F, F, F, F },
						{ F, F, F, F, F, F, F, F, F, F },//
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, F, F, F, F, F, F, F, F, E },
						{ E, F, F, F, F, F, F, F, F, E },
						{ E, E, E, F, F, F, F, E, E, E },
						{ E, E, E, F, F, F, F, E, E, E }, },//

				{// 3
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, F, F, F, F, F, F, F, F, E },//
						{ E, F, F, F, E, E, F, F, F, E },
						{ F, F, F, F, F, F, F, F, F, F },
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, F, F, F, E, E, F, F, F, E },
						{ E, F, F, F, F, F, F, F, F, E },
						{ F, F, F, F, F, F, F, F, F, F }, },//
				{// 4
						{ E, F, F, F, F, F, F, F, F, E },
						{ F, E, F, F, F, F, F, F, E, F },//
						{ F, F, E, F, F, F, F, E, F, F },
						{ F, F, F, E, F, F, E, F, F, F },
						{ F, F, E, F, F, F, F, E, F, F },
						{ F, E, F, F, F, F, F, F, E, F },
						{ E, F, F, F, F, F, F, F, F, E }, },//

				{// 5
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, F, F, F, F, F, F, F, F, E },//
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, F, F, F, F, F, F, F, F, E },
						{ F, F, F, F, F, F, F, F, F, F }, },//
				{// 6
						{ F, F, E, F, F, F, F, E, F, F },
						{ F, E, F, F, E, E, F, F, E, F },//
						{ E, E, F, E, F, F, E, F, E, E },
						{ E, F, F, F, F, F, F, F, F, E },
						{ F, F, F, F, E, E, F, F, F, F },
						{ F, F, F, F, E, E, F, F, F, F },
						{ E, F, F, F, F, F, F, F, F, E },
						{ E, E, F, F, F, F, F, F, E, E }, },//
				{// 7
						{ F, E, E, F, E, E, F, E, E, F },
						{ F, E, F, F, F, F, F, F, E, F },//
						{ F, E, F, F, F, F, F, F, E, F },
						{ F, F, F, F, F, F, F, F, F, F },
						{ F, F, F, F, F, F, F, F, F, F },
						{ F, E, F, F, F, F, F, F, E, F },
						{ F, E, F, F, F, F, F, F, E, F },
						{ F, E, E, F, E, E, F, E, E, F }, },//
				{// 8
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, F, F, F, F, F, F, F, F, E },//
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, E, F, E, F, F, E, F, E, E },
						{ E, F, E, E, F, F, E, E, F, E },
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, F, F, F, F, F, F, F, F, E },
						{ E, E, F, F, F, F, F, F, E, E }, },//
				{ // 9
						{ F, F, E, E, F, F, E, E, F, F },
						{ E, F, F, E, F, F, E, F, F, E },//
						{ E, E, F, F, F, F, F, F, E, E },
						{ F, F, F, F, F, F, F, F, F, F },
						{ F, F, F, F, F, F, F, F, F, F },
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, F, F, E, F, F, E, F, F, E },
						{ F, F, E, E, F, F, E, E, F, F }, },//
				{// 10
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, F, F, F, F, F, E, F, F, E },//
						{ F, F, E, E, F, F, F, F, F, F },
						{ E, F, F, E, F, F, E, E, E, E },
						{ E, F, F, F, F, F, F, F, F, E },
						{ E, E, F, F, F, F, F, F, E, E },
						{ E, E, E, F, F, F, F, E, E, E },
						{ E, E, E, E, F, F, E, E, E, E }, },//
		};

		Board newBricks = new Board(boardWidth,
				preloadedBricks[getLevel() - 1].length);

		bricksCount = 0;
		for (int i = 0; i < newBricks.getHeight(); i++) {
			// the bricks is filled from the bottom up
			newBricks.setRow(
					preloadedBricks[getLevel() - 1][newBricks.getHeight() - 1
							- i], i);

			// calculate count of the bricks
			for (int j = 0; j < newBricks.getWidth(); j++) {
				if (newBricks.getCell(j, i) == Cell.Full)
					bricksCount++;
			}
		}

		return newBricks;
	}

	/**
	 * Shift the bricks wall horizontally
	 */
	private synchronized void shiftBricks() {
		// shift bricks
		bricks = horizontalShift(bricks,
				(getRotation() == Rotation.Clockwise) ? 1 : -1);
		// insert shifted bricks to the board
		insertCells(getBoard(), bricks.getBoard(), bricksX, bricksY);
		// re-drawing the ball
		setBoard(drawBall(getBoard(), ballX, ballY));
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
		// Create a temporary board, a copy of the basic board
		Board newBoard = board.clone();

		// Erase the ball and draw on the new place
		newBoard = drawShape(newBoard, ballX, ballY, ball, Cell.Empty);
		newBoard = drawShape(newBoard, x, y, ball, ball.getFill());

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

		int newX, newY;
		// set new coordinates from directions
		newX = ballX + ((ballHorizontalDirection == RIGHT) ? 1 : -1);
		newY = ballY + ((ballVerticalDirection == UP) ? 1 : -1);

		// if the ball fall off the board then loss live
		if ((newY < 0) || (useDoubleSidedPlatform && (newY >= boardHeight))) {
			loss();
			return;
		}

		// check collision with the board's borders
		if (newY < 0) {
			ballVerticalDirection = DOWN;
			newY = ballY - 1;
		}
		if ((newX < 0) || (newX >= boardWidth)) {
			ballHorizontalDirection = ballHorizontalDirection.getOpposite();
			newX = ballX - (newX - ballX);
		}

		// try to break brick under current position of the ball
		breakBrick(board, ballX, ballY);

		// check collision with the bricks or the platform
		if ((board.getCell(newX, ballY) != Cell.Empty)
				|| (board.getCell(ballX, newY) != Cell.Empty)
				|| (board.getCell(newX, newY) != Cell.Empty)) {

			// at first, checked the cells with whom the ball touches the edges
			if ((board.getCell(newX, ballY) != Cell.Empty)
					|| (board.getCell(ballX, newY) != Cell.Empty)) {
				if (board.getCell(newX, ballY) != Cell.Empty) {
					ballHorizontalDirection = ballHorizontalDirection
							.getOpposite();
					if (!isPlatform(newX, ballY)) {
						breakBrick(board, newX, ballY);
					}
				}
				if (board.getCell(ballX, newY) != Cell.Empty) {
					ballVerticalDirection = ballVerticalDirection.getOpposite();
					if (!isPlatform(ballX, newY)) {
						breakBrick(board, ballX, newY);
					}
				}
				// if there are none,then processed the cell with whom the ball
				// touches the apex
			} else {
				ballHorizontalDirection = ballHorizontalDirection.getOpposite();
				ballVerticalDirection = ballVerticalDirection.getOpposite();
				if (!isPlatform(newX, newY)) {
					breakBrick(board, newX, newY);
				}
			}

			newX = ballX;
			newY = ballY;
		}

		board = drawBall(board, newX, newY);
		setBoard(board);

		// when destroying all bricks
		if (bricksCount <= 0)
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

		if ((givenX < 0) || (givenX >= bricks.getWidth()) || (givenY < 0)
				|| (givenY >= bricks.getHeight()))
			return;

		if (bricks.getCell(givenX, givenY) != Cell.Empty) {
			bricks.setCell(Cell.Empty, givenX, givenY);

			insertCells(board, bricks.getBoard(), bricksX, bricksY);

			// increase scores
			setScore(getScore() + 1);
			// decrease bricks count
			bricksCount--;
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
		setStatus(Status.DoSomeWork);

		// saves current bricks wall
		Board curBricks = bricks.clone();

		// kaboom and decrease lives
		kaboom(ballX, ballY);
		setLives(getLives() - 1);

		if (getLives() > 0) {
			animatedClearBoard(true);
			loadLevel(false);
		} else {
			gameOver();
		}

		// restores saved bricks wall
		insertCells(getBoard(), curBricks.getBoard(), bricksX, bricksY);
	}

	/**
	 * Increase the level and load it
	 */
	private void win() {
		setStatus(Status.DoSomeWork);

		setLevel(getLevel() + 1);
		if (getLevel() == 1)
			setSpeed(getSpeed() + 1);

		animatedClearBoard(true);
		loadLevel(true);
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
				movePlatform(curX - 1);
				sleep(ANIMATION_DELAY);
			}

			if (keys.contains(KeyPressed.KeyRight)) {
				movePlatform(curX + 1);
				sleep(ANIMATION_DELAY);
			}

			if (keys.contains(KeyPressed.KeyRotate)) {
				moveBall();
				sleep(ANIMATION_DELAY);
			}
		}
	}

}
