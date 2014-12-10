package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.DOWN;
import static com.kry.brickgame.games.GameConsts.LEFT;
import static com.kry.brickgame.games.GameConsts.RIGHT;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.setKeyDelay;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.boards.BricksWall;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.InvadersSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 */
public class InvadersGame extends GameWithGun {
	private static final long serialVersionUID = 7821002285005926463L;
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
	private volatile int bricksX;
	/**
	 * Y-coordinate for insertion of the bricks wall
	 */
	private volatile int bricksY;
	/**
	 * The ball breaking bricks
	 */
	private final Shape ball;
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
	private final boolean hasTwoSmokingBarrels;
	/**
	 * Whether to shift the board?
	 */
	private final boolean isShiftingBricks;
	/**
	 * Use preloaded bricks wall or generate new ones?
	 */
	private final boolean usePreloadedBricks;
	/**
	 * Timer for the invasion
	 */
	private transient Timer invasionTimer;
	// Easter egg
	/**
	 * The X-Dimension, which has its own laws
	 */
	private boolean theXDimension;
	/**
	 * Are you ready to teleport to the X-Dimension?
	 */
	private volatile boolean isReadyToXDimension;
	/**
	 * X-coordinate of the starting position of the gun
	 */
	private final int startX;
	
	// increase speed from the original
	private static final int FIRST_LEVEL_SPEED = 300;
	private static final int TENTH_LEVEL_SPEED = 50;
	
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
		ball = BallUtils.getBall(Cell.Full);
		
		// for every 3rd and 4th type of game
		hasTwoSmokingBarrels = ((getType() % 4 == 3) || (getType() % 4 == 0));
		// for every even type of game
		isShiftingBricks = (getType() % 2 == 0);
		// for types 9-16
		setDrawInvertedBoard((getType() > 8));
		// for 1-4 and 9-12 type of game
		usePreloadedBricks = ((getType() % 8 >= 1) && (getType() % 8 < 5));
		
		/*
		 * The X-Dimension Easter Egg To get into the X-Dimension you might have
		 * to shoot through the bricks wall above the gun and nowhere else. When
		 * the bricks wall down to the level of the gun, you need to stand in
		 * front of the bullet hole.
		 */
		isReadyToXDimension = false;
		theXDimension = false;
		startX = boardWidth / 2 - 1;
		
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
			
			insertCellsToBoard(board, bricks.getBoard(), bricksX, bricksY);
			
			// increase scores
			setScore(getScore() + 1);
			
			if (isReadyToXDimension && (x != startX)) {
				isReadyToXDimension = false;
			}
		}
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
					
					// don't create a ball when it below the gun
					if (ballY < gun.getHeight()) {
						initBall();
						return;
					}
					
					ballVerticalDirection = DOWN;
					// magic aiming algorithm
					if (ballX < curX) { // if the ball to the left of the gun
						ballHorizontalDirection = (Math.abs(ballX - curX) < Math.abs(ballY - curY)) ? LEFT
								: RIGHT;
					} else {
						ballHorizontalDirection = (Math.abs(ballX - curX) < Math.abs(ballY - curY)) ? RIGHT
								: LEFT;
					}
					// delete this brick from bricks wall
					bricks.setCell(Cell.Empty, x, y);
					
					isFlyingBall = true;
					
					playEffect(Effects.turn);
					
					return;
				}
			}
		}
		
	}
	
	private void createInvasionTimer() {
		// if the invasionTimer was set, then cancel it
		if (invasionTimer != null) {
			invasionTimer.cancel();
		}
		
		invasionTimer = new Timer("Invasion", true);
		invasionTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					processInvasion();
				}
			}
		}, getSpeed(true) * 2, getSpeed(true));
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
		
		if (newBricksY <= -bricks.getHeight()) {
			win();
			return true;
		}
		
		// check collision with the gun
		boolean result = true;
		for (int i = 0; i < boardWidth; i++) {
			if (board.getCell(i, curY + gun.maxY()) == Cell.Full) {
				// checking for compliance with the conditions of teleportation
				// in the X-Dimension
				if (isReadyToXDimension) {
					for (int j = 0; j < bricks.getHeight(); j++) {
						if (bricks.getCell(bricksX + startX, j) == Cell.Full) {
							isReadyToXDimension = false;
							break;
						}
					}
					result = isReadyToXDimension && (curX == startX);
				} else {
					result = false;
				}
				break;
			}
		}
		
		// add the gun and the ball to the board
		board = drawShape(board, curX, curY, gun, gun.getFill());
		board = drawShape(board, ballX, ballY, ball, ball.getFill());
		
		bricksY = newBricksY;
		
		// reset the bullets
		// initBullets(bullets);
		
		setBoard(board);
		
		// for even types of game, shifts the board
		if (isShiftingBricks) {
			sleep(ANIMATION_DELAY);
			shiftBricks();
		}
		
		return result;
	}
	
	@Override
	protected synchronized void fireBoardChanged(Board board) {
		Board newBoard = board.clone();
		
		// the X-Dimension has its own laws
		if (theXDimension) {
			for (int i = 0; i < newBoard.getWidth(); i++) {
				for (int j = 0; j < newBoard.getHeight(); j++) {
					if ((newBoard.getCell(i, j) == Cell.Full) && (r.nextInt(4) != 0)) {
						newBoard.setCell(Cell.Empty, i, j);
					}
				}
			}
			drawShape(newBoard, curX, curY, gun, gun.getFill());
		}
		
		super.fireBoardChanged(newBoard);
	}
	
	@Override
	protected int getFIRST_LEVEL_SPEED() {
		return FIRST_LEVEL_SPEED;
	}
	
	@Override
	protected int getTENTH_LEVEL_SPEED() {
		return TENTH_LEVEL_SPEED;
	}
	
	/**
	 * Erasing the ball from the board
	 */
	private void initBall() {
		isFlyingBall = false;
		setBoard(drawBall(getBoard(), -1, -1));
	}
	
	/**
	 * Loading or reloading the specified level
	 */
	@Override
	protected void loadNewLevel() {
		// set the X-Dimension flags
		theXDimension = isReadyToXDimension && !isShiftingBricks;
		isReadyToXDimension = !theXDimension && !isShiftingBricks;
		
		// if the invasionTimer was set, then cancel it
		if (invasionTimer != null) {
			invasionTimer.cancel();
		}
		
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
		curX = startX;
		curY = 0;
		// draws the gun
		moveGun(curX, curY);
		
		createBall();
		
		super.loadNewLevel();
		
		// create timer for invasion
		if (!start) {
			createInvasionTimer();
		}
	}
	
	@Override
	protected void loss(int x, int y) {
		isReadyToXDimension = false;
		super.loss(x, y);
	}
	
	/**
	 * Move the ball to a new position in movement direction
	 */
	private void moveBall() {
		Point newCoords;
		// set new coordinates from directions
		newCoords = BallUtils
				.moveBall(ballX, ballY, ballHorizontalDirection, ballVerticalDirection);
		
		// if the ball fall off the board then remove it
		if (newCoords.y < 0) {
			initBall();
			bricks.setBricksCount(bricks.getBricksCount() - 1);
			return;
		}
		
		// whether the ball bounced off the surface?
		boolean bounce = false;
		
		// check collision with the board's borders
		if ((newCoords.x < 0) || (newCoords.x >= boardWidth)) {
			ballHorizontalDirection = ballHorizontalDirection.getOpposite();
			bounce = true;
		}
		newCoords = BallUtils
				.moveBall(ballX, ballY, ballHorizontalDirection, ballVerticalDirection);
		
		Board board = getBoard().clone();
		
		// check collision with the gun
		if (newCoords.y <= curY + gun.maxY()) {
			if (checkCollision(board, ball, newCoords.x, newCoords.y)) {
				loss(newCoords.x, newCoords.y);
				return;
			}
		}
		
		if (bounce) {
			playEffect(Effects.turn);
		}
		
		// draw the ball in the new position
		board = drawBall(board, newCoords.x, newCoords.y);
		setBoard(board);
	}
	
	/**
	 * Processing actions of invaders
	 */
	synchronized void processInvasion() {
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
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None) return;
		
		super.processKeys();
		
		if (getStatus() == Status.Running) {
			int movementSpeed = Math.round(ANIMATION_DELAY * 1.5f);
			int newX = curX, newY = curY;
			boolean move = false;
			
			if (containsKey(KeyPressed.KeyLeft)) {
				newX = newX - 1;
				move = true;
				setKeyDelay(KeyPressed.KeyLeft, movementSpeed);
			}
			if (containsKey(KeyPressed.KeyRight)) {
				newX = newX + 1;
				move = true;
				setKeyDelay(KeyPressed.KeyRight, movementSpeed);
			}
			
			if (move) {
				if (moveGun(newX, newY)) {
					playEffect(Effects.move);
				} else {
					loss(newX, newY);
				}
			}
			
			if (containsKey(KeyPressed.KeyDown)) {
				processInvasion();
				setKeyDelay(KeyPressed.KeyDown, movementSpeed);
			}
			if (containsKey(KeyPressed.KeyRotate)) {
				fire(curX, curY + gun.maxY() + 1, hasTwoSmokingBarrels);
				// slowing fire speed if hasTwoSmokingBarrels
				setKeyDelay(KeyPressed.KeyRotate,
						Math.round(movementSpeed * (hasTwoSmokingBarrels ? 3f : 1.5f)));
			}
		}
	}
	
	@Override
	protected synchronized void removeCell(Board board, int x, int y) {
		if ((x == ballX) && (y == ballY)) {
			initBall();
			bricks.setBricksCount(bricks.getBricksCount() - 1);
		} else {
			breakBrick(board, x, y);
		}
	}
	
	/**
	 * Shift the bricks wall horizontally
	 */
	private void shiftBricks() {
		// shift bricks
		bricks = bricks.shift(r.nextBoolean() ? -1 : 1);
		// insert shifted bricks to the board
		insertCellsToBoard(getBoard(), bricks.getBoard(), bricksX, bricksY);
	}
	
	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();
		// create timer for invasion,
		// for the deserialization
		createInvasionTimer();
		
		// create timer for bullets
		Timer bulletSwarm = new Timer("BulletSwarm", true);
		bulletSwarm.schedule(new TimerTask() {
			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					flightOfBullets();
				}
			}
			// twice as slow if hasTwoSmokingBarrels
		}, 0, ANIMATION_DELAY / (hasTwoSmokingBarrels ? 1 : 2));
		
		while (!Thread.currentThread().isInterrupted() && (getStatus() != Status.GameOver)) {
			// check the number of remaining bricks
			if (bricks.getBricksCount() <= 0) {
				clearBullets(getBoard());
				win();
			}
			// processing of key presses
			processKeys();
		}
		
		bulletSwarm.cancel();
		invasionTimer.cancel();
	}
}
