package com.kry.brickgame.games;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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

	private final int FIRST_LEVEL_SPEED = 500;
	private final int TENTH_LEVEL_SPEED = 80;
	
	/*---MAGIC NUMBERS---*/

	protected int getFIRST_LEVEL_SPEED() {
		return FIRST_LEVEL_SPEED;
	}

	protected int getTENTH_LEVEL_SPEED() {
		return TENTH_LEVEL_SPEED;
	}

	private volatile int speed;
	private volatile int level;
	private volatile int score;
	private volatile int lives;

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

	public static Splash getSplash() {
		return splash;
	}

	protected synchronized Status getStatus() {
		return status;
	}

	protected synchronized void setStatus(Status status) {
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
	protected synchronized int getSpeed(boolean genuine) {
		if (genuine) {
			// getting a uniform distribution from FIRST_LEVEL_SPEED to
			// TENTH_LEVEL_SPEED
			return (getFIRST_LEVEL_SPEED() - (getFIRST_LEVEL_SPEED() - getTENTH_LEVEL_SPEED())
					/ (10 - 1) * (speed - 1));
		}
		return speed;
	}

	/**
	 * Speed level
	 * 
	 * @return speed level 1-10
	 */
	protected synchronized int getSpeed() {
		return getSpeed(false);
	}

	/**
	 * Set speed level
	 * 
	 * @param speed
	 *            speed level 1-10
	 */
	protected synchronized void setSpeed(int speed) {
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
	protected synchronized int getLevel() {
		return level;
	}

	/**
	 * Set level
	 * 
	 * @param level
	 *            level 1-10
	 */
	protected synchronized void setLevel(int level) {
		if (level < 1) {
			this.level = 10;
		} else if (level > 10) {
			this.level = 1;
		} else
			this.level = level;
		fireLevelChanged(this.level);
	}

	protected synchronized int getScore() {
		return score;
	}

	protected synchronized void setScore(int score) {
		if ((score > 19999) || (score < 0))
			this.score = 0;
		else
			this.score = score;
		fireInfoChanged(String.valueOf(score));
	}

	/**
	 * Lives
	 * 
	 * @return lives 0 - 4
	 */
	protected synchronized int getLives() {
		return lives;
	}

	/**
	 * Set lives
	 * 
	 * @param lives
	 *            lives 0 - 4
	 */
	protected synchronized void setLives(int lives) {
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

	protected synchronized Board getBoard() {
		return board;
	}

	protected synchronized void setBoard(Board board) {
		this.board = board;
		fireBoardChanged(board);
	}

	protected synchronized Board getPreview() {
		return preview;
	}

	protected synchronized void setPreview(Board preview) {
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
	protected static boolean checkCollision(Board board, Shape piece, int x,
			int y) {
		try {
			for (int i = 0; i < piece.getLength(); i++) {
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
	 * Animated clearing of a full line
	 * 
	 * @param x
	 *            point, on both sides of which cells will be removed
	 *            (x-coordinate)
	 * @param y
	 *            number of the line to be removed (y-coordinate)
	 */
	protected void animatedClearLine(Board board, int x, int y) {
		int x1 = x - 1; // left direction
		int x2 = x; // right direction

		while ((x1 >= 0) || (x2 < board.getWidth())) {
			if (x1 >= 0)
				board.setCell(Cell.Empty, x1--, y);
			if (x2 < board.getWidth())
				board.setCell(Cell.Empty, x2++, y);

			fireBoardChanged(board);
			sleep(ANIMATION_DELAY);
		}
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
	 * Shift the contents of the board horizontally on the delta x
	 * 
	 * @param board
	 *            the board for horizontal shift
	 * @param dX
	 *            delta x, if {@code dX > 0} then shift to the right, otherwise
	 *            shift to the left
	 * @return the board after horizontal shift
	 */
	protected static Board horizontalShift(Board board, int dX) {
		// If dX is greater than the width of the board, it is reduced
		int reducedDX = dX % board.getWidth();

		if (reducedDX == 0)
			return board;

		Board resultBoard = board.clone();

		for (int i = 0; i < Math.abs(reducedDX); i++) {
			// if shift to the right, then get the first column as temporary,
			// otherwise get the last column
			Cell[] tempColumn = (reducedDX > 0) ? board.getColumn(board
					.getWidth() - 1) : board.getColumn(0);

			for (int j = 0; j < board.getWidth(); j++) {
				Cell[] nextColumn = null;
				// replace the column on the side of the board to the
				// appropriate column on the other side
				if (((j == 0) && (reducedDX > 0))
						|| ((j == board.getWidth() - 1) && (reducedDX < 0))) {
					nextColumn = tempColumn;
				} else {
					// replace the column in the middle of the board to the
					// appropriate adjacent column
					nextColumn = board.getColumn(j
							+ ((reducedDX > 0) ? (-1) : 1));
				}
				resultBoard.setColumn(nextColumn, j);
			}
		}

		return resultBoard;
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
	protected static Board drawShape(Board board, int x, int y, Shape shape,
			Cell fill) {
		for (int i = 0; i < shape.getLength(); i++) {
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
	protected static Board drawPoint(Board board, int x, int y, Cell fill) {
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
					{ E, E, E, E, E } }, {
					// 6
					{ E, E, E, E, E },//
					{ E, E, E, E, E },//
					{ E, E, E, E, E },//
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
	 * Add randomly generated lines on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param fromLine
	 *            line, which starts the addition
	 * @param linesCount
	 *            count of added lines
	 * @param isUpwardDirection
	 *            if {@code true}, then the bottom-up direction of addition
	 * @return the board after adding lines
	 */
	protected static Board addLines(Board board, int fromLine, int linesCount,
			boolean isUpwardDirection) {

		if ((linesCount < 1)//
				|| ((isUpwardDirection) && //
				((fromLine + (linesCount - 1)) > board.getHeight()))//
				//
				|| ((!isUpwardDirection) && //
				((fromLine - (linesCount - 1)) < 0)))
			return board;

		// checks whether there are full cells at a distance of
		// <i>linesCount</i> from the top or the bottom of the board
		for (int i = 0; i < board.getWidth(); i++) {
			if (//
					((isUpwardDirection) && //
					(board.getCell(i, ((board.getHeight() - 1) - linesCount)) == Cell.Full))//
					//
					|| ((!isUpwardDirection) && //
					(board.getCell(i, (linesCount - 1)) == Cell.Full))//
			)
				return board;
		}

		Random r = new Random();
		Cell newLines[][] = new Cell[linesCount][board.getWidth()];

		// picks up or downs the lines of the board
		if (isUpwardDirection) {
			for (int y = (board.getHeight() - 1) - 1; y > fromLine + (linesCount - 1); y--) {
				board.setRow(board.getRow(y - 1), y);
			}
		} else {
			for (int y = 0; y < (fromLine - (linesCount - 1)); y++) {
				board.setRow(board.getRow(y + 1), y);
			}
		}

		// generates a new lines
		for (int line = 0; line < linesCount; line++) {
			boolean hasEmpty = false;
			boolean hasFull = false;

			for (int i = 0; i < board.getWidth(); i++) {
				if (r.nextBoolean()) {
					newLines[line][i] = Cell.Empty;
					hasEmpty = true;
				} else {
					newLines[line][i] = Cell.Full;
					hasFull = true;
				}
			}

			// if all the cells were empty, creates a full one in a random place
			// of the line
			if (!hasEmpty || !hasFull) {
				newLines[line][r.nextInt(board.getWidth())] = ((!hasEmpty) ? Cell.Empty
						: Cell.Full);
			}

			// adds the created line to the board
			board.setRow(newLines[line], (isUpwardDirection ? fromLine + line
					: fromLine - line));
		}
		return board;
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
