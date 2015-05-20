package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.CB_WIN;
import static com.kry.brickgame.games.GameUtils.addLinesToBoard;
import static com.kry.brickgame.games.GameUtils.boardHorizontalShift;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.setKeyDelay;
import static com.kry.brickgame.games.GameUtils.sleep;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.games.GameSound.Music;

/**
 * @author noLive
 */
public class GunGame extends GameWithGun {
	private static final long serialVersionUID = -8031498937942999783L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.GunSplash";
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 16;
	/**
	 * Movement speed of the gun
	 */
	private static final int MOVEMENT_SPEED = Math.round(ANIMATION_DELAY * 1.5f);
	/**
	 * Fire speed of the gun
	 */
	private final int FIRE_SPEED;
	
	/**
	 * Kind of game
	 * <p>
	 * {@code true} - the shot creates a new cell, {@code false} - the shot
	 * destroys a cell
	 */
	final boolean isCreationMode;
	
	/**
	 * Number of barrels
	 * <p>
	 * {@code true} - two barrels, {@code false} - one barrel
	 */
	private final boolean hasTwoSmokingBarrels;
	
	/**
	 * Whether to shift the board?
	 */
	private final boolean isShiftingBoard;
	
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
		
		// ==define the parameters of the types of game==
		// for types 5-8 and 13-16
		isCreationMode = getType() >= 5 && getType() <= 8 || getType() >= 13 && getType() <= 16;
		// for every 3rd and 4th type of game
		hasTwoSmokingBarrels = getType() % 4 == 3 || getType() % 4 == 0;
		// for every even type of game
		isShiftingBoard = getType() % 2 == 0;
		// for types 8-16
		setDrawInvertedBoard(getType() > 8);
		
		// slow down if hasTwoSmokingBarrels
		FIRE_SPEED = Math.round(ANIMATION_DELAY * (hasTwoSmokingBarrels ? 2.5f : 1.5f));
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
		return addLinesToBoard(board, boardHeight - 1, linesCount, false);
	}
	
	/**
	 * Launching the game
	 */
	@Override
	public Game call() {
		super.init();
		
		if (!desirialized) {
			loadNewLevel();
		}
		
		// create timer for bullets
		ScheduledFuture<?> bulletSwarm = scheduledExecutors.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (!(exitFlag || Thread.currentThread().isInterrupted())
				        && getStatus() == Status.Running) {
					if (isCreationMode) {
						flightOfMud();
					} else {
						flightOfBullets();
					}
				}
			}
			// twice as slow if hasTwoSmokingBarrels
		}, 0, ANIMATION_DELAY / (hasTwoSmokingBarrels ? 1 : 2), TimeUnit.MILLISECONDS);
		
		while (!(exitFlag || Thread.currentThread().isInterrupted())
		        && getStatus() != Status.GameOver) {
			if (getStatus() == Status.Running && isStarted) {
				int currentSpeed = getSpeed(true);
				// increase game speed when hasTwoSmokingBarrels
				if (hasTwoSmokingBarrels) {
					currentSpeed = isCreationMode ? currentSpeed * 2 : currentSpeed
					        - ANIMATION_DELAY / 2;
				}
				
				// moving
				if (elapsedTime(currentSpeed)) {
					// try drop down lines
					if (!droppingDown()) {
						loss(curX, curY);
					}
				}
			}
			// processing of key presses
			processKeys();
		}
		
		bulletSwarm.cancel(true);
		return nextGame;
	}
	
	/**
	 * Drop down one row
	 * 
	 * @return {@code true} there is no collision with the gun, otherwise
	 *         {@code false}
	 */
	private boolean droppingDown() {
		boolean result = true;
		synchronized (lock) {
			Board board = getBoard();
			// erase the gun to not interfere with the checks
			board = drawShape(board, curX, curY, gun, Cell.Empty);
			// add line
			board = addLines(board, 1);
			// check whether the line is dropped to the gun
			for (int i = 0; i < boardWidth; i++) {
				if (board.getCell(i, curY + gun.maxY()) == Cell.Full) {
					result = false;
					break;
				}
			}
			// return the gun to the board
			board = drawShape(board, curX, curY, gun, Cell.Full);
			setBoard(board);
		}
		
		// for even types of game, shifts the board
		if (isShiftingBoard) {
			sleep(ANIMATION_DELAY);
			shiftBoard();
		}
		
		return result;
	}
	
	@Override
	protected int getSpeedOfFirstLevel() {
		return isCreationMode ? 4500 : 500;
	}
	
	@Override
	protected int getSpeedOfTenthLevel() {
		return isCreationMode ? 2500 : 250;
	}
	
	/**
	 * Loading or reloading the specified level
	 */
	@Override
	void loadNewLevel() {
		// starting position - the middle of the bottom border of the board
		curX = boardWidth / 2 - 1;
		curY = 0;
		
		// clear the bullets
		initBullets(bullets);
		
		synchronized (lock) {
			// draws a rows on the top of the border
			setBoard(addLines(getBoard(), getLevel()));
			// draws the gun
			moveGun(curX, curY);
		}
		
		super.loadNewLevel();
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (keys.isEmpty() || getStatus() == Status.None) return;
		
		super.processKeys();
		
		if (getStatus() == Status.Running) {
			if (containsKey(KeyPressed.KeyLeft)) {
				if (moveGun(curX - 1, curY)) {
					GameSound.playEffect(Effects.move);
					if (isCreationMode) {
						keys.remove(KeyPressed.KeyLeft);
					} else {
						setKeyDelay(KeyPressed.KeyLeft, MOVEMENT_SPEED);
					}
				}
			}
			if (containsKey(KeyPressed.KeyRight)) {
				if (moveGun(curX + 1, curY)) {
					GameSound.playEffect(Effects.move);
					if (isCreationMode) {
						keys.remove(KeyPressed.KeyRight);
					} else {
						setKeyDelay(KeyPressed.KeyRight, MOVEMENT_SPEED);
					}
				}
			}
			if (isStarted) {
				if (containsKey(KeyPressed.KeyDown)) {
					if (droppingDown()) {
						GameSound.playEffect(Effects.move);
						setKeyDelay(KeyPressed.KeyDown, MOVEMENT_SPEED);
					} else {
						loss(curX, curY);
					}
				}
				if (containsKey(KeyPressed.KeyRotate)) {
					fire(curX, curY + gun.maxY() + 1, hasTwoSmokingBarrels);
					if (isCreationMode) {
						keys.remove(KeyPressed.KeyRotate);
					} else {
						setKeyDelay(KeyPressed.KeyRotate, FIRE_SPEED);
					}
				}
			}
		}
	}
	
	@Override
	void setScore(int score) {
		int oldThousands = getScore() / 1000;
		
		super.setScore(score);
		
		// when a sufficient number of points changes the speed and the level
		if (getScore() / 1000 > oldThousands) {
			setSpeed(getSpeed() + 1);
			if (getSpeed() == 1) {
				setLevel(getLevel() + 1);
				
				setStatus(Status.DoSomeWork);
				synchronized (lock) {
					Board board = getBoard();
					// delete flying bullets
					clearBullets(board);
					initBullets(bullets);
					// erase the gun
					board = drawShape(board, curX, curY, gun, Cell.Empty);
					
					GameSound.playMusic(Music.win);
					animatedClearBoard(CB_WIN);
					
					// add lines
					for (int i = 0; i < getLevel(); i++) {
						board = addLines(getBoard(), 1);
						setBoard(board);
						sleep(ANIMATION_DELAY * 3);
					}
					
					// return the gun
					board = drawShape(board, curX, curY, gun, Cell.Full);
					setBoard(board);
				}
				setStatus(Status.Running);
			}
		}
	}
	
	/**
	 * Shift the board horizontally on a random direction
	 */
	private void shiftBoard() {
		synchronized (lock) {
			Board board = getBoard();
			// erase the gun to not interfere
			board = drawShape(board, curX, curY, gun, Cell.Empty);
			// remove bullets from the board
			clearBullets(board);
			// shifts the board
			board = boardHorizontalShift(board, r.nextBoolean() ? -1 : 1);
			// return the gun to the board
			board = drawShape(board, curX, curY, gun, Cell.Full);
			setBoard(board);
		}
	}
	
}
