package com.kry.brickgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.kry.brickgame.Board.Cell;

public class Game implements Runnable {

	/*---MAGIC NUMBERS---*/
	/**
	 * The number of lines of the base field (the board), that should not be
	 * displayed ({@value} ). Used for smooth the appearance of a figures.
	 */
	public static final int UNSHOWED_LINES = 4;
	/**
	 * Width of the board ({@value} ).
	 */
	public static final int BOARD_WIDTH = 10;
	/**
	 * Height of the board ({@value} ).
	 */
	public static final int BOARD_HEIGHT = 20 + UNSHOWED_LINES;
	/**
	 * Width of the preview board ({@value} ).
	 */
	public static final int PREVIEW_WIDTH = 4;
	/**
	 * Height of the preview board ({@value} ).
	 */
	public static final int PREVIEW_HEIGHT = 4;
	/**
	 * Animation delay in milliseconds
	 */
	protected static final int ANIMATION_DELAY = 30;
	/**
	 * Coefficient to get genuine speed
	 */
	protected static final int SPEED_RATIO = 50;
	/*---MAGIC NUMBERS---*/

	private int speed = 1;
	private int level = 1;
	private int score = 0;

	private static ArrayList<GameListener> listeners = new ArrayList<GameListener>();

	static enum Status {
		None, Running, Paused, GameOver, DoSomeWork
	};

	/**
	 * Game status
	 */
	private volatile Status status;

	private long timePoint = System.currentTimeMillis();

	protected Status getStatus() {
		return status;
	}

	protected void setStatus(Status status) {
		this.status = status;
		fireStatusChanged(status);
	}

	private volatile Board board;
	private volatile Board preview;

	static enum KeyPressed {
		KeyNone, KeyLeft, KeyRight, KeyUp, KeyDown, KeyRotate, KeyStart, KeyReset, KeyMute, KeyOnOff
	};

	protected Set<KeyPressed> keys = new HashSet<KeyPressed>();

	public Game(int speed, int level) {
		this.speed = speed;
		this.level = level;
		board = new Board(BOARD_WIDTH, BOARD_HEIGHT, UNSHOWED_LINES);
		preview = new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT);
	}

	public Game() {
		this(1, 1);
	}

	public static synchronized void addGameListener(GameListener listener) {
		listeners.add(listener);
	}

	public static synchronized GameListener[] getGameListeners() {
		return listeners.toArray(new GameListener[listeners.size()]);
	}

	public static synchronized void removeGameListener(GameListener listener) {
		listeners.remove(listener);
	}

	protected synchronized void fireBoardChanged(Board board) {
		GameEvent event = new GameEvent(this, board);
		for (GameListener listener : listeners) {
			listener.boardChanged(event);
		}
	}

	protected synchronized void firePreviewChanged(Board preview) {
		GameEvent event = new GameEvent(this, preview);
		for (GameListener listener : listeners) {
			listener.previewChanged(event);
		}
	}

	protected synchronized void fireStatusChanged(Status status) {
		GameEvent event = new GameEvent(this, status);
		for (GameListener listener : listeners) {
			listener.statusChanged(event);
		}
	}

	protected synchronized void fireInfoChanged(String info) {
		GameEvent event = new GameEvent(this, info);
		for (GameListener listener : listeners) {
			listener.infoChanged(event);
		}
	}

	protected synchronized void fireSpeedChanged(int speed) {
		GameEvent event = new GameEvent(this, (float) speed);
		for (GameListener listener : listeners) {
			listener.speedChanged(event);
		}
	}

	protected synchronized void fireLevelChanged(int level) {
		GameEvent event = new GameEvent(this, level);
		for (GameListener listener : listeners) {
			listener.levelChanged(event);
		}
	}

	/**
	 * Speed
	 * 
	 * @param genuine
	 *            return genuine speed (true) or speed level (false)
	 * @return if genuine than return genuine speed in millisecond else return
	 *         speed level 1-10
	 */
	protected int getSpeed(boolean genuine) {
		if (genuine) {
			return (SPEED_RATIO * 10) / speed;
		}
		return speed;
	}

	/**
	 * Speed level
	 * 
	 * @return speed level 1-10
	 */
	protected int getSpeed() {
		return getSpeed(false);
	}

	/**
	 * Set speed level
	 * 
	 * @param speed
	 *            speed level 1-10
	 */
	protected void setSpeed(int speed) {
		if (speed < 1) {
			this.speed = 10;
		} else if (speed > 10) {
			this.speed = 1;
		} else
			this.speed = speed;
		fireSpeedChanged(this.speed);
	}

	/**
	 * Level
	 * 
	 * @return level 1-10
	 */
	protected int getLevel() {
		return level;
	}

	/**
	 * Set level
	 * 
	 * @param level
	 *            level 1-10
	 */
	protected void setLevel(int level) {
		if (level < 1) {
			this.level = 10;
		} else if (level > 10) {
			this.level = 1;
		} else
			this.level = level;
		fireLevelChanged(this.level);
	}

	protected int getScore() {
		return score;
	}

	protected void setScore(int score) {
		this.score = score;
		fireInfoChanged(String.valueOf(score));
	}

	protected Board getBoard() {
		return board;
	}

	protected void setBoard(Board board) {
		this.board = board;
		fireBoardChanged(board);
	}

	protected Board getPreview() {
		return preview;
	}

	protected void setPreview(Board preview) {
		this.preview = preview;
		firePreviewChanged(preview);
	}

	/**
	 * Calculates if elapsed of {@code millis} since the last time point. If
	 * elapsed, the time point is set the current time.
	 * 
	 * @param millis
	 *            delay in milliseconds
	 * @return true - if elapsed of {@code millis} since the last time point
	 */
	protected boolean elapsedTime(int millis) {
		long nowTime = System.currentTimeMillis();
		boolean result = ((nowTime - timePoint) >= millis);
		if (result)
			timePoint = nowTime;
		return result;
	}

	/**
	 * Clears the cells of the board and raises the {@code fireBoardChanged}
	 * event
	 */
	protected void clearBoard() {
		board.clearBoard();
		fireBoardChanged(board);
	}

	/**
	 * Clears the cells of the preview and raises the {@code firePreviewChanged}
	 * event
	 */
	protected void clearPreview() {
		preview.clearBoard();
		firePreviewChanged(preview);
	}

	/**
	 * Collision check of a new figure with a filled cells on a board
	 * 
	 * @param board
	 *            - a board
	 * @param piece
	 *            - a new figure
	 * @param x
	 *            - x-coordinate figures
	 * @param y
	 *            - y-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	protected boolean checkCollision(Board board, Shape piece, int x, int y) {
		try {
			for (int i = 0; i < piece.getCoords().length; i++) {
				int board_x = x + piece.x(i);
				int board_y = y - piece.y(i);
				if (board.getCell(board_x, board_y) != Cell.Empty)
					return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	/**
	 * Collision check of a new figure with the vertical boundaries of the board
	 * 
	 * @param piece
	 *            - a new figure
	 * @param y
	 *            - y-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionVertical(Shape piece, int y) {
		if ((y - piece.maxY()) < 0 || (y - piece.minY()) >= BOARD_HEIGHT)
			return true;
		return false;
	}

	/**
	 * Collision check of a new figure with the horizontal boundaries of the
	 * board
	 * 
	 * @param piece
	 *            - a new figure
	 * @param x
	 *            - x-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionHorizontal(Shape piece, int x) {
		if ((x + piece.minX()) < 0 || (x + piece.maxX()) >= BOARD_WIDTH)
			return true;
		return false;
	}

	/**
	 * Collision check of a new figure with the
	 * {@link Game#checkBoardCollisionVertical vertical} and the
	 * {@link Game#checkBoardCollisionHorizontal horizontal} boundaries of the
	 * board
	 * 
	 * @param piece
	 *            - a new figure
	 * @param x
	 *            - x-coordinate figures
	 * @param y
	 *            - y-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollision(Shape piece, int x, int y) {
		return checkBoardCollisionVertical(piece, y)
				|| checkBoardCollisionHorizontal(piece, x);
	}

	/**
	 * Animated clearing of the board. Direction is determined from the value of
	 * {@code fromY} and {@code toY}
	 * 
	 * @param fromY
	 *            starting line (y-coordinate)
	 * @param toY
	 *            ending line (y-coordinate)
	 * @return if {@code toY} is more than {@code fromY} then the board is
	 *         filled upwards, otherwise is cleaned downwards
	 */
	protected boolean animatedClearBoard(int fromY, int toY) {
		boolean isUpDirection = ((toY - fromY) >= 0);

		// the board is filled upwards
		if (isUpDirection) {
			for (int y = fromY; y <= toY; y++) {
				for (int x = 0; x < BOARD_WIDTH; x++) {
					board.setCell(Cell.Full, x, y);
				}
				fireBoardChanged(board);
				try {
					Thread.sleep(ANIMATION_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
			// and is cleaned downwards
		} else {
			for (int y = fromY; y >= toY; y--) {
				for (int x = 0; x < BOARD_WIDTH; x++) {
					board.setCell(Cell.Empty, x, y);
				}
				fireBoardChanged(board);
				try {
					Thread.sleep(ANIMATION_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
		return isUpDirection;
	}

	/**
	 * Animated clearing of the board in several passes (upwards then downwards)
	 * 
	 */
	protected void animatedClearBoard() {
		animatedClearBoard(0, BOARD_HEIGHT - UNSHOWED_LINES);
		animatedClearBoard(BOARD_HEIGHT - UNSHOWED_LINES, 0);
	}

	protected void gameOver() {
		setStatus(Status.GameOver);
		animatedClearBoard();
		Main.setGame(Main.gameSelector);
	}

	protected void keyPressed(KeyPressed key) {
		keys.add(key);
	}

	protected void keyReleased(KeyPressed key) {
		keys.remove(key);
	}

	public void start() {
		clearBoard();
		clearPreview();
	}

	@Override
	public void run() {
		this.start();
	}

}
