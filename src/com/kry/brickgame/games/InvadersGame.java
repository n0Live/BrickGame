package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;

import java.awt.Point;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.InvadersSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class InvadersGame extends GameWithGun {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new InvadersSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 16;
	/**
	 * The bricks wall
	 */
	private volatile BricksWall bricks;
	/**
	 * X-coordinate for insertion of the bricks wall
	 */
	private int bricksX;
	/**
	 * Y-coordinate for insertion of the bricks wall
	 */
	private int bricksY;
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
	 * Is the ball flies now?
	 */
	private boolean isFlyingBall;
	/**
	 * Number of barrels
	 * <p>
	 * {@code true} - two barrels, {@code false} - one barrel
	 */
	private boolean hasTwoSmokingBarrels;
	/**
	 * Whether to shift the board?
	 */
	private boolean isShiftingBricks;
	/**
	 * Use preloaded bricks wall or generate new ones?
	 */
	private boolean usePreloadedBricks;
	/**
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;
	/**
	 * Timer for the invasion
	 */
	private Timer invasionTimer;

	// increase speed from the original
	private final int FIRST_LEVEL_SPEED = 300;
	private final int TENTH_LEVEL_SPEED = 50;

	@Override
	protected int getFIRST_LEVEL_SPEED() {
		return FIRST_LEVEL_SPEED;
	}

	@Override
	protected int getTENTH_LEVEL_SPEED() {
		return TENTH_LEVEL_SPEED;
	}

	/**
	 * The Invaders Game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game type of the game:
	 *            <ol>
	 *            <li>invaders with preloaded bricks vs one gun;
	 *            <li>invaders with preloaded and shifting bricks vs one gun;
	 *            <li>invaders with preloaded bricks vs two guns;
	 *            <li>invaders with preloaded and shifting bricks vs two guns;
	 *            <li>invaders with randomly generated bricks vs one gun;
	 *            <li>invaders with randomly generated and shifting bricks vs
	 *            one gun;
	 *            <li>invaders with randomly generated bricks vs two guns;
	 *            <li>invaders with randomly generated and shifting bricks vs
	 *            two guns;
	 *            <li>invaders with preloaded bricks vs one gun, the board is
	 *            upside down;
	 *            <li>invaders with preloaded and shifting bricks vs one gun,
	 *            the board is upside down;
	 *            <li>invaders with preloaded bricks vs two guns, the board is
	 *            upside down;
	 *            <li>invaders with preloaded and shifting bricks vs two guns,
	 *            the board is upside down;
	 *            <li>invaders with randomly generated bricks vs one gun, the
	 *            board is upside down;
	 *            <li>invaders with randomly generated and shifting bricks vs
	 *            one gun, the board is upside down;
	 *            <li>invaders with randomly generated bricks vs two guns, the
	 *            board is upside down;
	 *            <li>invaders with randomly generated and shifting bricks vs
	 *            two guns, the board is upside down.
	 */
	public InvadersGame(int speed, int level, int type) {
		super(speed, level, type);

		// initialize the ball
		this.ball = BallUtils.getBall(Cell.Full);

		// for every 3rd and 4th type of game
		hasTwoSmokingBarrels = ((getType() % 4 == 3) || (getType() % 4 == 0));
		// for every even type of game
		isShiftingBricks = (getType() % 2 == 0);
		// for types 9-16
		drawInvertedBoard = (getType() > 8);
		// for 1-4 and 9-12 type of game
		usePreloadedBricks = ((getType() % 8 >= 1) && (getType() % 8 < 5));
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		setStatus(Status.Running);

		loadNewLevel();

		// create timer for bullets
		Timer bulletSwarm = new Timer("BulletSwarm", true);
		bulletSwarm.schedule(new TimerTask() {
			@Override
			public void run() {
				flightOfBullets();
			}
			// twice as slow if hasTwoSmokingBarrels
		}, 0, ANIMATION_DELAY / (hasTwoSmokingBarrels ? 1 : 2));

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			// check the number of remaining bricks
			if (bricks.getBricksCount() <= 0) {
				win();
			}
			// processing of key presses
			processKeys();
		}

		bulletSwarm.cancel();
		invasionTimer.cancel();
	}

	/**
	 * Loading or reloading the specified level
	 * 
	 */
	@Override
	protected void loadNewLevel() {
		setStatus(Status.DoSomeWork);

		// if the invasionTimer was set, then cancel it
		if (invasionTimer != null)
			invasionTimer.cancel();

		// clear the bullets
		initBullets(bullets);

		// return the ball to the start position
		initBall();

		// create the bricks wall
		bricks = new BricksWall(getLevel(), usePreloadedBricks);
		bricksX = 0;
		bricksY = (boardHeight - bricks.getHeight());
		insertCellsToBoard(getBoard(), bricks.getBoard(), bricksX, bricksY);

		// set the starting position of the gun
		curX = boardWidth / 2 - 1;
		curY = 0;
		// draws the gun
		moveGun(curX, curY);

		createBall();

		// create timer for invasion
		invasionTimer = new Timer("Invasion", true);
		invasionTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					processInvasion();
				}
			}
		}, getSpeed(true) * 2, getSpeed(true));

		setStatus(Status.Running);
	}

	/**
	 * Processing actions of invaders
	 */
	private synchronized void processInvasion() {
		if (isFlyingBall) { // the ball already created
			moveBall();
		} else {
			// dropping down the bricks wall
			if (!droppingDown()) {
				loss(curX, curY);
			} else {
				createBall();
			}
		}
	}

	/**
	 * Erasing the ball from the board
	 */
	private void initBall() {
		isFlyingBall = false;
		setBoard(drawBall(getBoard(), -1, -1));
	}

	/**
	 * Creating the ball from one of a bricks in the bricks wall
	 * <p>
	 * Setting the coordinates of the ball and ball's movement direction
	 */
	private void createBall() {
		for (int y = 0; y < bricks.getHeight(); y++) {
			for (int x = bricks.getWidth() - 1; x >= 0; x--) {
				if (bricks.getCell(x, y) == Cell.Full) {
					ballX = x + bricksX;
					ballY = y + bricksY;
					ballVerticalDirection = DOWN;
					// magic aiming algorithm
					if (ballX < curX) { // if the ball to the left of the gun
						ballHorizontalDirection = (Math.abs(ballX - curX) < Math
								.abs(ballY - curY)) ? LEFT : RIGHT;
					} else {
						ballHorizontalDirection = (Math.abs(ballX - curX) < Math
								.abs(ballY - curY)) ? RIGHT : LEFT;
					}
					// delete this brick from bricks wall
					bricks.setCell(Cell.Empty, x, y);

					isFlyingBall = true;
					return;
				}
			}
		}

	}

	/**
	 * Move the ball to a new position in movement direction
	 */
	private void moveBall() {
		Point newCoords;
		// set new coordinates from directions
		newCoords = BallUtils.moveBall(ballX, ballY, ballHorizontalDirection,
				ballVerticalDirection);

		// if the ball fall off the board then remove it
		if (newCoords.y < 0) {
			initBall();
			bricks.setBricksCount(bricks.getBricksCount() - 1);
			return;
		}

		// check collision with the board's borders
		if ((newCoords.x < 0) || (newCoords.x >= boardWidth)) {
			ballHorizontalDirection = ballHorizontalDirection.getOpposite();
		}
		newCoords = BallUtils.moveBall(ballX, ballY, ballHorizontalDirection,
				ballVerticalDirection);

		Board board = getBoard().clone();

		// check collision with the gun
		if (newCoords.y <= curY + gun.maxY()) {
			if (checkCollision(board, ball, newCoords.x, newCoords.y)) {
				loss(newCoords.x, newCoords.y);
				return;
			}
		}
		// draw the ball in the new position
		board = drawBall(board, newCoords.x, newCoords.y);
		setBoard(board);
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
			insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY);

			// increase scores
			setScore(getScore() + 1);
		}
	}

	/**
	 * Drop down one row
	 * 
	 * @return {@code true} there is no collision with the gun, otherwise
	 *         {@code false}
	 */
	private synchronized boolean droppingDown() {
		// create new empty board
		Board board = new Board(boardWidth, boardHeight);

		// draw the bricks on one line down
		int newBricksY = bricksY - 1;
		insertCellsToBoard(board, bricks.getBoard(), bricksX, newBricksY);

		// check collision with the gun
		boolean result = true;
		for (int i = 0; i < boardWidth; i++) {
			if (board.getCell(i, curY + gun.maxY()) == Cell.Full) {
				result = false;
				break;
			}
		}

		// add the gun and the ball to the board
		board = drawShape(board, curX, curY, gun, gun.getFill());
		board = drawShape(board, ballX, ballY, ball, ball.getFill());

		bricksY = newBricksY;

		// reset the bullets
		initBullets(bullets);

		setBoard(board);

		// for even types of game, shifts the board
		if (isShiftingBricks) {
			sleep(ANIMATION_DELAY);
			shiftBricks();
		}

		return result;
	}

	/**
	 * Shift the bricks wall horizontally
	 */
	private void shiftBricks() {
		// shift bricks
		bricks.shift((new Random().nextBoolean()) ? (-1) : (1));
		// insert shifted bricks to the board
		insertCellsToBoard(getBoard(), bricks.getBoard(), bricksX, bricksY);
	}

	@Override
	protected void removeCell(Board board, int x, int y) {
		if ((x == ballX) && (y == ballY)) {
			initBall();
			bricks.setBricksCount(bricks.getBricksCount() - 1);
		} else {
			breakBrick(board, x, y);
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
				if (!moveGun(curX - 1, curY))
					loss(curX - 1, curY);
				else
					sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				if (!moveGun(curX + 1, curY))
					loss(curX + 1, curY);
				else
					sleep(ANIMATION_DELAY * 2);
			}
			if ((keys.contains(KeyPressed.KeyDown))
					|| (keys.contains(KeyPressed.KeyUp))) {
				processInvasion();
				sleep(ANIMATION_DELAY * 2);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				fire(curX, curY + gun.maxY() + 1, hasTwoSmokingBarrels);
				sleep(ANIMATION_DELAY);
			}
		}
	}
}
