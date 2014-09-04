package com.kry.brickgame.games;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.kry.brickgame.Board;
import com.kry.brickgame.GameEvent;
import com.kry.brickgame.GameListener;
import com.kry.brickgame.Main;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class Game extends Thread { // implements Runnable
	/**
	 * Animated splash for game
	 */
	public static Splash splash;
	/**
	 * Number of subtypes
	 */
	public static int subtypesNumber;

	/*---MAGIC NUMBERS---*/
	/**
	 * Width of the default board ({@value} )
	 */
	private static final int BOARD_WIDTH = 10;
	/**
	 * Height of the default board ({@value} )
	 */
	private static final int BOARD_HEIGHT = 20;
	/**
	 * Width of the default preview board ({@value} )
	 */
	private static final int PREVIEW_WIDTH = 4;
	/**
	 * Height of the default preview board ({@value} )
	 */
	private static final int PREVIEW_HEIGHT = 4;
	/**
	 * Animation delay in milliseconds
	 */
	protected static final int ANIMATION_DELAY = 30;

	private static final int FIRST_LEVEL_SPEED = 500;
	private static final int TENTH_LEVEL_SPEED = 80;
	/*---MAGIC NUMBERS---*/

	private int speed;
	private int level;
	private int score;
	private int lives;
	private int type;

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

	public static enum Status {
		None, Running, Paused, GameOver, DoSomeWork, ComingSoon
	};

	/**
	 * Game status
	 */
	private volatile Status status;

	private long timePoint = System.currentTimeMillis();

	private volatile Board board;
	private volatile Board preview;

	public static enum KeyPressed {
		KeyNone, KeyLeft, KeyRight, KeyUp, KeyDown, KeyRotate, KeyStart, KeyReset, KeyMute, KeyOnOff
	};

	protected Set<KeyPressed> keys = new HashSet<KeyPressed>();

	/**
	 * The Game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param board
	 *            main board
	 * @param preview
	 *            preview board
	 * @param type
	 *            type of the game
	 */
	public Game(int speed, int level, Board board, Board preview, int type) {
		this.speed = speed;
		this.level = level;
		this.board = board;
		this.preview = preview;
		this.type = type;

		this.score = 0;
		this.lives = 4;

		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		previewWidth = preview.getWidth();
		previewHeight = preview.getHeight();
	}

	/**
	 * The Game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game
	 */
	public Game(int speed, int level, int type) {
		this(speed, level, new Board(BOARD_WIDTH, BOARD_HEIGHT), // board
				new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT), type);// preview
	}

	public Game() {
		this(1, 1, 1);
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

	public Splash getSplash() {
		return splash;
	}

	protected Status getStatus() {
		return status;
	}

	protected void setStatus(Status status) {
		this.status = status;
		fireStatusChanged(status);
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

	/**
	 * Lives
	 * 
	 * @return lives 0 - 4
	 */
	protected int getLives() {
		return lives;
	}

	/**
	 * Set lives
	 * 
	 * @param lives
	 *            lives 0 - 4
	 */
	protected void setLives(int lives) {
		if (lives > 4) {
			this.lives = 4;
		} else if (lives < 0) {
			this.lives = 0;
		} else {
			this.lives = lives;
		}
		clearPreview();
		if (this.lives > 0) {
			for (int i = 0; i < lives; i++) {
				preview.setCell(Cell.Full, 1, i);
				preview.setCell(Cell.Full, 2, i);
			}
		}
		firePreviewChanged(preview);
	}

	public int getType() {
		return type;
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
	 * Collision check of the new figure with a filled cells on the board
	 * 
	 * @return true if there is a collision
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            the new figure
	 * @param x
	 *            x-coordinate of the figure
	 * @param y
	 *            y-coordinate of the figure
	 * @return {@code true} if there is a collision
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	protected boolean checkCollision(Board board, Shape piece, int x, int y) {
		try {
			for (int i = 0; i < piece.getCoords().length; i++) {
				int board_x = x + piece.x(i);
				int board_y = y + piece.y(i);
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
	 *            y-coordinate of the figure
	 * @param checkTopBoundary
	 *            is it necessary to check the upper boundary
	 * @return {@code true} if there is a collision
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionVertical(Shape piece, int y,
			boolean checkTopBoundary) {
		if (checkTopBoundary && ((y + piece.maxY()) >= boardHeight))
			return true;
		if ((y + piece.minY()) < 0)
			return true;
		return false;
	}

	/**
	 * Collision check of the new figure with the horizontal boundaries of the
	 * board
	 * 
	 * @param piece
	 *            - the new figure
	 * @param x
	 *            - x-coordinate of the figure
	 * @return {@code true} if there is a collision
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
	 * Collision check of the new figure with the
	 * {@link Game#checkBoardCollisionVertical vertical} and the
	 * {@link Game#checkBoardCollisionHorizontal horizontal} boundaries of the
	 * board
	 * 
	 * @param piece
	 *            - the new figure
	 * @param x
	 *            - x-coordinate of the figures
	 * @param y
	 *            - y-coordinate of the figures
	 * @return {@code true} if there is a collision
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
	 * Animated clearing of the board (upwards then downwards)
	 * 
	 * @param isFast
	 *            if {@code true} then animation speed is increased twice
	 */
	protected void animatedClearBoard(boolean isFast) {
		int k = (isFast ? 2 : 1);

		// the board is filled upwards
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				board.setCell(Cell.Full, x, y);
			}
			fireBoardChanged(board);
			sleep(ANIMATION_DELAY / k);
		}
		// and is cleaned downwards
		for (int y = boardHeight - 1; y >= 0; y--) {
			for (int x = 0; x < boardWidth; x++) {
				board.setCell(Cell.Empty, x, y);
			}
			fireBoardChanged(board);
			sleep(ANIMATION_DELAY / k);
		}
	}

	/**
	 * Animated clearing of the board on the normal speed
	 * 
	 */
	protected void animatedClearBoard() {
		animatedClearBoard(false);
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
	 * @return {@code true} if the insertion is success, otherwise {@code false}
	 */
	protected boolean insertCells(Cell[][] cells, int x, int y) {
		Board board = getBoard();

		if ((x < 0) || (y < 0)) {
			return false;
		}
		if ((x + cells.length > board.getWidth())
				|| (y + cells[0].length > board.getHeight())) {
			return false;
		}

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				board.setCell(cells[i][j], x + i, y + j);
			}
		}
		fireBoardChanged(board);
		return true;
	}

	/**
	 * Drawing the figure on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate position on the board of the figure
	 * @param y
	 *            y-coordinate position on the board of the figure
	 * @param shape
	 *            the figure
	 * @param fill
	 *            {@code Cells.Full} or {@code Cells.Blink} - to draw the
	 *            figure, {@code Cells.Empty} - to erase the figure
	 * 
	 * @return the board with the figure
	 */
	protected Board drawShape(Board board, int x, int y, Shape shape, Cell fill) {
		for (int i = 0; i < shape.getCoords().length; i++) {
			int board_x = x + shape.x(i);
			int board_y = y + shape.y(i);

			// if the figure does not leave off the board
			if (((board_y < board.getHeight()) && (board_y >= 0))
					&& ((board_x < board.getWidth()) && (board_x >= 0))) {
				// draws the point of the figure on the board
				drawPoint(board, board_x, board_y, fill);
			}
		}
		return board;
	}

	/**
	 * Drawing the point of the figure on the board.
	 * <p>
	 * If the point is outside the borders of the board, then drawing it on the
	 * other side of the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate position on the board of the figure
	 * @param y
	 *            y-coordinate position on the board of the figure
	 * @param fill
	 *            type of fill the point
	 * @return the board with the point
	 */
	protected Board drawPoint(Board board, int x, int y, Cell fill) {
		int board_x = x;
		int board_y = y;

		// check if the point is outside the borders of the board
		if (board_x < 0)
			board_x = board.getWidth() + board_x;
		else if (board_x >= board.getWidth())
			board_x = board_x - board.getWidth();
		if (board_y < 0)
			board_y = board.getHeight() + board_y;
		else if (board_y >= board.getHeight())
			board_y = board_y - board.getHeight();

		board.setCell(fill, board_x, board_y);

		return board;
	}

	/**
	 * Drawing effect of the explosion
	 * 
	 * @param x
	 *            x-coordinate of the epicenter
	 * @param y
	 *            x-coordinate of the epicenter
	 */
	protected void kaboom(int x, int y) {
		/**
		 * Inner class to draw an explosion
		 */
		class Kaboom {
			final Cell E = Cell.Empty;
			final Cell F = Cell.Full;
			/**
			 * Blast waves
			 */
			final Cell waves[][][] = new Cell[][][] { {
					// 0
					{ F, F, F },//
					{ F, E, F },//
					{ F, F, F } }, {
					// 1
					{ F, F, F, F, F },//
					{ F, E, E, E, F },//
					{ F, E, E, E, F },//
					{ F, E, E, E, F },//
					{ F, F, F, F, F } }, {
					// 2
					{ F, E, F, E, F },//
					{ E, E, E, E, E },//
					{ F, E, E, E, F },//
					{ E, E, E, E, E },//
					{ F, E, F, E, F } }, {
					// 3
					{ F, E, F, E, F },//
					{ E, F, F, F, E },//
					{ F, F, E, F, F },//
					{ E, F, F, F, E },//
					{ F, E, F, E, F } }, {
					// 4
					{ E, E, E, E, E },//
					{ E, F, F, F, E },//
					{ E, F, E, F, E },//
					{ E, F, F, F, E },//
					{ E, E, E, E, E } }, {
					// 5
					{ E, E, E, E, E },//
					{ E, E, E, E, E },//
					{ E, E, F, E, E },//
					{ E, E, E, E, E },//
					{ E, E, E, E, E } }

			};

			/**
			 * Drawing a single pass of the blast wave
			 * 
			 * @param x
			 *            x-coordinate of the epicenter
			 * @param y
			 *            x-coordinate of the epicenter
			 * @param wave
			 *            number of the blast wave
			 */
			void blast(int x, int y, int wave) {
				// converts the coordinates of the epicenter to the coordinates
				// of the lower left corner
				int lowerLeftX = x - (waves[wave][0].length / 2);
				int lowerLeftY = y - (waves[wave].length / 2);

				insertCells(waves[wave], lowerLeftX, lowerLeftY);

				sleep(ANIMATION_DELAY * 2);
			}
		}

		// diameter of the explosion
		// must be an odd number
		final int EXPLODE_SIZE = 5;

		final int BLAST_WAVE_PASSES = 4;

		int newX = x;
		int newY = y;

		// if the explosion leave off the board, move the epicenter point
		while ((newX - EXPLODE_SIZE / 2) < 0) {
			newX++;
		}
		while ((newX - EXPLODE_SIZE / 2 + EXPLODE_SIZE) > boardWidth) {
			newX--;
		}
		while ((newY - EXPLODE_SIZE / 2) < 0) {
			newY++;
		}
		while ((newY - EXPLODE_SIZE / 2 + EXPLODE_SIZE) > boardHeight) {
			newY--;
		}

		Kaboom kaboom = new Kaboom();

		for (int i = 0; i < BLAST_WAVE_PASSES; i++) {
			// draw the blast waves
			for (int k = 0; k < kaboom.waves.length; k++) {
				kaboom.blast(newX, newY, k);
			}
		}
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
		Thread.currentThread().interrupt();
		Main.setGame(Main.gameSelector);
	}

	/**
	 * Exit to Main menu
	 */
	protected void ExitToMainMenu() {
		setStatus(Status.None);
		Thread.currentThread().interrupt();
		Main.setGame(Main.gameSelector);
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
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Processing key pressing
	 * 
	 * @param key
	 *            keyCode associated with the pressed key
	 */
	public void keyPressed(KeyPressed key) {
		keys.add(key);
	}

	/**
	 * Processing key releasing
	 * 
	 * @param key
	 *            keyCode associated with the released key
	 */
	public void keyReleased(KeyPressed key) {
		keys.remove(key);
	}

}
