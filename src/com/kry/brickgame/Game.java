package com.kry.brickgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.kry.brickgame.Board.Cell;

/**
 * @author noLive
 * 
 */
public class Game implements Runnable {

	/*---MAGIC NUMBERS---*/
	/**
	 * Width of the default board ({@value} )
	 */
	public static final int BOARD_WIDTH = 10;
	/**
	 * Height of the default board ({@value} )
	 */
	public static final int BOARD_HEIGHT = 20;
	/**
	 * Width of the default preview board ({@value} )
	 */
	public static final int PREVIEW_WIDTH = 4;
	/**
	 * Height of the default preview board ({@value} )
	 */
	public static final int PREVIEW_HEIGHT = 4;
	/**
	 * Animation delay in milliseconds
	 */
	protected static final int ANIMATION_DELAY = 30;

	private static final int FIRST_LEVEL_SPEED = 500;
	private static final int TENTH_LEVEL_SPEED = 80;
	/*---MAGIC NUMBERS---*/

	private int speed = 1;
	private int level = 1;
	private int score = 0;

	/**
	 * Width of the board
	 */
	protected int boardWidth;
	/**
	 * Height of the board
	 */
	protected int boardHeight;
	/**
	 * Width of the preview board
	 */
	protected int previewWidth;
	/**
	 * Height of the preview board
	 */
	protected int previewHeight;

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

	public Game(int speed, int level, Board board, Board preview) {
		this.speed = speed;
		this.level = level;
		this.board = board;
		this.preview = preview;

		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		previewWidth = preview.getWidth();
		previewHeight = preview.getHeight();
	}

	public Game(int speed, int level) {
		this(speed, level, new Board(BOARD_WIDTH, BOARD_HEIGHT), // board
				new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT));// preview
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
			// getting a uniform distribution from FIRST_LEVEL_SPEED to
			// TENTH_LEVEL_SPEED
			return (FIRST_LEVEL_SPEED - (FIRST_LEVEL_SPEED - TENTH_LEVEL_SPEED)
					/ (10 - 1) * (speed - 1));
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
		} catch (IndexOutOfBoundsException e) {
			// do nothing - it's ok
		}
		return false;
	}

	/**
	 * Collision check of the new figure with the vertical boundaries of the
	 * board
	 * 
	 * @param piece
	 *            the new figure
	 * @param y
	 *            y-coordinate figures
	 * @param checkTopBoundary
	 *            is it necessary to check the upper boundary
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionVertical(Shape piece, int y,
			boolean checkTopBoundary) {
		if (checkTopBoundary && ((y + piece.maxY()) >= boardHeight))
			return true;
		if ((y - piece.maxY()) < 0)
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
		if ((x + piece.minX()) < 0 || (x + piece.maxX()) >= boardWidth)
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
		return checkBoardCollisionVertical(piece, y, true)
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
		boolean isUpDirection = (toY >= fromY);

		// the board is filled upwards
		if (isUpDirection) {
			for (int y = fromY; y <= toY; y++) {
				for (int x = 0; x < boardWidth; x++) {
					board.setCell(Cell.Full, x, y);
				}
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
			// and is cleaned downwards
		} else {
			for (int y = fromY; y >= toY; y--) {
				for (int x = 0; x < boardWidth; x++) {
					board.setCell(Cell.Empty, x, y);
				}
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
		}
		return isUpDirection;
	}

	/**
	 * Animated clearing of the board in several passes (upwards then downwards)
	 * 
	 */
	protected void animatedClearBoard() {
		animatedClearBoard(0, boardHeight - 1);
		animatedClearBoard(boardHeight - 1, 0);
	}

	/**
	 * Insert the cells in the basic board. Coordinate ( {@code x, y}) is set a
	 * point, which gets the lower left corner of the {@code cells}
	 * 
	 * @param cells
	 *            the cells to insert
	 * @param x
	 *            x-coordinate for the insertion
	 * @param y
	 *            y-coordinate for the insertion
	 * 
	 */
	protected void insertCells(Cell[][] cells, int x, int y) {
		Board board = getBoard();

		if ((x < 0) || (y < 0)) {
			return;
		}
		if ((x + cells.length > board.getWidth())
				|| (y + cells[0].length > board.getHeight())) {
			return;
		}

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				board.setCell(cells[i][j], x + i, y + j);
			}
		}
		fireBoardChanged(board);
	}

	/**
	 * Pause / Resume
	 */
	protected void pause() {
		if (getStatus() == Status.Running) {
			setStatus(Status.Paused);
		} else if (getStatus() == Status.Paused) {
			setStatus(Status.Running);
		}
	}

	/**
	 * Game Over
	 */
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

	/**
	 * Sleep for the specified number of milliseconds
	 * 
	 * @param millis
	 *            the length of time to sleep in milliseconds
	 */
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
