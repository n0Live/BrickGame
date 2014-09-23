package com.kry.brickgame.games;

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
	public static final int subtypesNumber = 8;

	// ** Direction constants **
	private static final RotationAngle LEFT = RotationAngle.d270;
	private static final RotationAngle RIGHT = RotationAngle.d90;
	private static final RotationAngle UP = RotationAngle.d0;
	private static final RotationAngle DOWN = RotationAngle.d180;
	// **

	private ÑharacterShape platform;

	private Shape ball;

	private int ballX;

	private int ballY;

	private RotationAngle ballVerticalDirection;

	private RotationAngle ballHorizontalDirection;

	private Board bricks;

	public ArkanoidGame(int speed, int level, int type) {
		super(speed, level, type);
		setStatus(Status.None);

		platform = new ÑharacterShape(Ñharacters.Platform);

		// initialize the ball
		this.ball = new Shape(1);
		ball.setCoord(0, new int[] { 0, 0 });
		ball.setFill(Cell.Full);

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
			if ((getStatus() != Status.Paused) && (elapsedTime(getSpeed(true)))) {
				moveBall();
			}
			// processing of key presses
			processKeys();
		}
	}

	/**
	 * Loading or reloading the specified level
	 */
	private void loadLevel() {
		curX = boardWidth / 2 - 1;
		curY = 0;

		ballX = curX;
		ballY = curY + 1;
		ballVerticalDirection = UP;
		ballHorizontalDirection = RIGHT;

		drawBall(ballX, ballY);
		movePlatform(curX);
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

		if ((ballY == curY + 1)//
				&& ((ballX >= curX + platform.minX())//
				&& (ballX <= curX + platform.maxX()))) {
			int newBallX = ballX + (x - curX);
			board = drawBall(newBallX, ballY);
		}

		setBoard(board);
		curX = x;

		return true;
	}

	/**
	 * Move the ball to a new location
	 * 
	 * @param x
	 *            x-coordinate position of the new location
	 * @param y
	 *            y-coordinate position of the new location
	 * @return {@code true} if the movement succeeded, otherwise {@code false}
	 */
	private Board drawBall(int x, int y) {
		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the ball to not interfere with the checks
		board = drawShape(board, ballX, ballY, ball, Cell.Empty);

		// draw the ball on the new place
		setBoard(drawShape(board, x, y, ball, ball.getFill()));

		ballX = x;
		ballY = y;

		return board;
	}

	private boolean moveBall() {
		int newX = ballX;
		int newY = ballY;

		newX = ballX + ((ballVerticalDirection == RIGHT) ? 1 : -1);
		newY = ballY + ((ballVerticalDirection == UP) ? 1 : -1);

		if (newY >= boardHeight) {
			ballVerticalDirection = DOWN;
			newY = ballY - 1;
		}
		if ((newX < 0) || (newX >= boardWidth)) {
			ballHorizontalDirection = ballHorizontalDirection.getRight()
					.getRight();
			newX = ballX - (newX - ballX);
		}

		drawBall(newX, newY);

		return false;
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
				sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				movePlatform(curX + 1);
				sleep(ANIMATION_DELAY * 2);
			}

			if (keys.contains(KeyPressed.KeyDown)) {
				sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyUp)) {
				sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				sleep(ANIMATION_DELAY * 2);
			}
		}
	}

}
