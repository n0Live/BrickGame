package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.getInvertedBoard;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.playMusic;
import static com.kry.brickgame.games.GameUtils.stopAllSounds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.kry.brickgame.GameEvent;
import com.kry.brickgame.GameListener;
import com.kry.brickgame.Main;
import com.kry.brickgame.ScoresManager;
import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.games.GameUtils.Music;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public abstract class Game extends Thread {
	/**
	 * Animated splash for game
	 */
	public static Splash splash;
	/**
	 * Number of subtypes
	 */
	public static int subtypesNumber;

	public static enum Status {
		None, Running, Paused, GameOver, DoSomeWork, ComingSoon
	};

	public static enum Rotation {
		None, Clockwise, Counterclockwise;

		/**
		 * Get the next rotation
		 * <p>
		 * If current rotation is {@code None}, then return {@code None}
		 * 
		 * @return the next rotation
		 */
		public Rotation getNext() {
			// if None then getNext() return None
			if (this == Rotation.None)
				return this;
			else
				return this.ordinal() < Rotation.values().length - 1 ? Rotation
						.values()[this.ordinal() + 1] : Rotation.values()[1];
		}
	};

	public static enum KeyPressed {
		KeyNone, KeyLeft, KeyRight, KeyUp, KeyDown, KeyRotate, KeyStart, KeyReset, KeyMute, KeyOnOff
	};

	private static ArrayList<GameListener> listeners = new ArrayList<GameListener>();

	/**
	 * Set of the pressed keys
	 */
	protected Set<KeyPressed> keys = new HashSet<KeyPressed>();

	/*---MAGIC NUMBERS---*/
	// ** Direction constants **
	protected static final RotationAngle UP = RotationAngle.d0;
	protected static final RotationAngle DOWN = RotationAngle.d180;
	protected static final RotationAngle RIGHT = RotationAngle.d90;
	protected static final RotationAngle LEFT = RotationAngle.d270;
	// **
	/**
	 * Width of the default board ({@value} )
	 */
	protected static final int BOARD_WIDTH = 10;
	/**
	 * Height of the default board ({@value} )
	 */
	protected static final int BOARD_HEIGHT = 20;
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

	// ** anumatedClearBoard constants **
	protected static final int CB_GAME_OVER = 6000;
	protected static final int CB_WIN = 5240;
	protected static final int CB_LOSE = 1200;
	// **

	private final int FIRST_LEVEL_SPEED = 500;
	private final int TENTH_LEVEL_SPEED = 80;

	protected int getFIRST_LEVEL_SPEED() {
		return FIRST_LEVEL_SPEED;
	}

	protected int getTENTH_LEVEL_SPEED() {
		return TENTH_LEVEL_SPEED;
	}

	/*------*/

	/**
	 * Is the sound turned off?
	 */
	private static boolean mute = false;
	/**
	 * Speed
	 */
	private static int speed;
	/**
	 * Level
	 */
	private static int level;
	/**
	 * Score
	 */
	private volatile int score;
	/**
	 * Type of game
	 */
	private int type;
	/**
	 * Direction of rotation
	 */
	private Rotation rotation;
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
	/**
	 * X-coordinate position on the board
	 */
	protected int curX;
	/**
	 * Y-coordinate position on the board
	 */
	protected int curY;
	/**
	 * Game status
	 */
	private volatile Status status;
	/**
	 * The time base for the {@link #elapsedTime(int)}
	 */
	private long timePoint;
	/**
	 * The main (base) board
	 */
	private volatile Board board;
	/**
	 * The preview board
	 */
	private volatile Board preview;
	/**
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;

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
	 * @param rotation
	 *            direction of rotation
	 * @param type
	 *            type of the game
	 */
	protected Game(int speed, int level, Board board, Board preview,
			Rotation rotation, int type) {
		stopAllSounds();

		this.type = type;

		setSpeed(speed);
		setLevel(level);
		setBoard(board);
		setPreview(preview);
		setRotation(rotation);

		setDrawInvertedBoard(false);

		clearBoard();
		clearPreview();

		setScore(0);
		setStatus(Status.None);

		this.curX = 0;
		this.curY = 0;

		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		previewWidth = preview.getWidth();
		previewHeight = preview.getHeight();

		timePoint = System.currentTimeMillis();
	}

	/**
	 * The Game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param rotation
	 *            direction of rotation
	 * @param type
	 *            type of the game
	 */
	public Game(int speed, int level, Rotation rotation, int type) {
		this(speed, level, new Board(BOARD_WIDTH, BOARD_HEIGHT), // board
				new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT), rotation, type);// preview
	}

	/**
	 * The Game without rotation
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
				new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT), Rotation.None, type);// preview
	}

	/**
	 * The Game
	 */
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
		GameEvent event = new GameEvent(this,
				(isInvertedBoard() ? getInvertedBoard(board) : board));
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

	protected void fireRotationChanged(Rotation rotation) {
		GameEvent event = new GameEvent(this, rotation);
		for (GameListener listener : listeners) {
			listener.rotationChanged(event);
		}
	}

	protected void fireMuteChanged(boolean mute) {
		GameEvent event = new GameEvent(this, mute);
		for (GameListener listener : listeners) {
			listener.muteChanged(event);
		}
	}

	/**
	 * Get the splash screen for game
	 * 
	 * @return the splash
	 */
	protected static Splash getSplash() {
		return splash;
	}

	/**
	 * Get the status of game
	 * 
	 * @return the status
	 */
	protected synchronized Status getStatus() {
		return status;
	}

	/**
	 * Set the status of game and fire the {@link #fireStatusChanged(Status)}
	 * event
	 * 
	 * @param status
	 *            the status of game
	 */
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
	protected int getSpeed() {
		return getSpeed(false);
	}

	/**
	 * Set speed level and fire the {@link #fireSpeedChanged(int)} event
	 * 
	 * @param speed
	 *            speed level 1-10
	 */
	protected void setSpeed(int speed) {
		if (speed < 1) {
			Game.speed = 10;
		} else if (speed > 10) {
			Game.speed = 1;
		} else
			Game.speed = speed;
		fireSpeedChanged(Game.speed);
	}

	/**
	 * Level
	 * 
	 * @return level 1-10
	 */
	protected static int getLevel() {
		return level;
	}

	/**
	 * Set level and fire the {@link #fireLevelChanged(int)} event
	 * 
	 * @param level
	 *            level 1-10
	 */
	protected void setLevel(int level) {
		if (level < 1) {
			Game.level = 10;
		} else if (level > 10) {
			Game.level = 1;
		} else
			Game.level = level;
		fireLevelChanged(Game.level);
	}

	/**
	 * Get the score
	 * 
	 * @return the score
	 */
	protected synchronized int getScore() {
		return score;
	}

	/**
	 * Set the score and fire the {@link #fireInfoChanged(String)} event
	 * 
	 * @param score
	 *            score 0 - 19999
	 */
	protected synchronized void setScore(int score) {
		if (score > 19999)
			this.score = 19999;
		else if (score < 0)
			this.score = 0;
		else
			this.score = score;
		fireInfoChanged(String.valueOf(score));
	}

	/**
	 * Get the type of game
	 * 
	 * @return the type
	 */
	protected int getType() {
		return type;
	}

	/**
	 * Get the direction of rotation
	 * 
	 * @return the direction of rotation
	 */
	protected Rotation getRotation() {
		return rotation;
	}

	/**
	 * Set the direction of rotation and fire the
	 * {@link #fireRotationChanged(Rotation)} event
	 * 
	 * @param rotation
	 *            the direction of rotation
	 */
	protected void setRotation(Rotation rotation) {
		this.rotation = rotation;
		fireRotationChanged(rotation);
	}

	/**
	 * Select another rotation
	 */
	protected void changeRotation() {
		setRotation(rotation.getNext());
	}

	/**
	 * Get the flag for the drawing the board invertedly
	 * 
	 * @return {@code true} if needed to draw the inverted board
	 */
	public boolean isInvertedBoard() {
		return drawInvertedBoard;
	}

	protected static boolean isMuted() {
		return mute;
	}

	protected void setMuted(boolean mute) {
		Game.mute = mute;
		if (mute) {
			stopAllSounds();
		}
		fireMuteChanged(mute);
	}

	/**
	 * Set the flag for the drawing the board invertedly
	 * 
	 * @param drawInvertedBoard
	 *            {@code true} if needed to draw the inverted board
	 */
	public void setDrawInvertedBoard(boolean drawInvertedBoard) {
		this.drawInvertedBoard = drawInvertedBoard;
	}

	/**
	 * Get the main board
	 * 
	 * @return the main board
	 */
	protected synchronized Board getBoard() {
		return board;
	}

	/**
	 * Set the main board and fire the {@link #fireBoardChanged(Board)} event
	 * 
	 * @param board
	 *            the main board
	 */
	protected synchronized void setBoard(Board board) {
		this.board = board;
		fireBoardChanged(board);
	}

	/**
	 * Get the preview board
	 * 
	 * @return the preview board
	 */
	protected synchronized Board getPreview() {
		return preview;
	}

	/**
	 * Set the main preview and fire the {@link #firePreviewChanged(Board)}
	 * event
	 * 
	 * @param preview
	 *            the preview board
	 */
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
	 * Clears the cells of the board and fire the
	 * {@link #fireBoardChanged(Board)} event
	 */
	protected void clearBoard() {
		board.clearBoard();
		fireBoardChanged(board);
	}

	/**
	 * Clears the cells of the preview and fire the
	 * {@link #firePreviewChanged(Board)} event
	 */
	protected void clearPreview() {
		preview.clearBoard();
		firePreviewChanged(preview);
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

		playEffect(Effects.remove_line);

		while ((x1 >= 0) || (x2 < board.getWidth())) {
			if (x1 >= 0)
				board.setCell(Cell.Empty, x1--, y);
			if (x2 < board.getWidth())
				board.setCell(Cell.Empty, x2++, y);

			fireBoardChanged(board);
			sleep(ANIMATION_DELAY * 2);
		}
	}

	/**
	 * Animated clearing of the board (upwards then downwards)
	 * 
	 * @param millis
	 *            duration of the animation in milliseconds
	 */
	protected void animatedClearBoard(int millis) {
		// delay between animation frames
		int delay = millis / (boardHeight * 2);

		// the board is filled upwards
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				board.setCell(Cell.Full, x, y);
			}
			fireBoardChanged(board);
			sleep(delay);
		}
		// and is cleaned downwards
		for (int y = boardHeight - 1; y >= 0; y--) {
			for (int x = 0; x < boardWidth; x++) {
				board.setCell(Cell.Empty, x, y);
			}
			fireBoardChanged(board);
			sleep(delay);
		}
	}

	/**
	 * Animated clearing of the board on Game Over
	 * 
	 */
	protected void animatedClearBoard() {
		animatedClearBoard(CB_GAME_OVER);
	}

	/**
	 * Drawing effect of the explosion
	 * 
	 * @param x
	 *            x-coordinate of the epicenter
	 * @param y
	 *            y-coordinate of the epicenter
	 */
	protected void kaboom(int x, int y) {
		/**
		 * Nested (inner) class to draw an explosion
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
			 *            y-coordinate of the epicenter
			 * @param wave
			 *            number of the blast wave
			 */
			void blast(int x, int y, int wave) {
				// converts the coordinates of the epicenter to the coordinates
				// of the lower left corner
				int lowerLeftX = x - (waves[wave][0].length / 2);
				int lowerLeftY = y - (waves[wave].length / 2);

				insertCellsToBoard(getBoard(), waves[wave], lowerLeftX,
						lowerLeftY);
				fireBoardChanged(getBoard());

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

		playMusic(Music.kaboom);

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

			fireInfoChanged(String.valueOf("HI" + setHiScore()));
			
			setStatus(Status.Paused);
			stopAllSounds();
		} else if (getStatus() == Status.Paused) {
			setStatus(Status.Running);
		}
	}

	/**
	 * Game Over
	 */
	protected void gameOver() {
		setStatus(Status.GameOver);

		playMusic(Music.game_over);

		animatedClearBoard();
		
		ExitToMainMenu();
	}

	@SuppressWarnings("unchecked")
	protected int setHiScore(){
		return ScoresManager.getInstance()
				.setHiScore((Class<Game>) this.getClass(), getScore());
	}
	
	@SuppressWarnings("unchecked")
	protected int getHiScore(){
		return ScoresManager.getInstance()
				.getHiScore((Class<Game>) this.getClass());
	}
	
	/**
	 * Exit to Main menu
	 */
	protected void ExitToMainMenu() {
		setStatus(Status.None);

		stopAllSounds();

		setHiScore();

		Thread.currentThread().interrupt();
		Main.setGame(Main.gameSelector);
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
		if (isInvertedBoard()) {
			// swap the up and down buttons
			if (key == KeyPressed.KeyDown)
				keys.add(KeyPressed.KeyUp);
			else if (key == KeyPressed.KeyUp)
				keys.add(KeyPressed.KeyDown);
			else
				keys.add(key);
		} else
			keys.add(key);
	}

	/**
	 * Processing key releasing
	 * 
	 * @param key
	 *            keyCode associated with the released key
	 */
	public void keyReleased(KeyPressed key) {
		if (isInvertedBoard()) {
			// swap the up and down buttons
			if (key == KeyPressed.KeyDown)
				keys.remove(KeyPressed.KeyUp);
			else if (key == KeyPressed.KeyUp)
				keys.remove(KeyPressed.KeyDown);
			else
				keys.remove(key);
		} else
			keys.remove(key);
	}

	/**
	 * Processing of key presses
	 */
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyReset)) {
			keys.remove(KeyPressed.KeyReset);
			ExitToMainMenu();
			return;
		}

		if (keys.contains(KeyPressed.KeyStart)) {
			keys.remove(KeyPressed.KeyStart);
			pause();
			return;
		}

		if (keys.contains(KeyPressed.KeyMute)) {
			keys.remove(KeyPressed.KeyMute);
			setMuted(!isMuted());
			return;
		}

		if (getStatus() == Status.Paused) {
			if (keys.contains(KeyPressed.KeyRotate)) {
				keys.remove(KeyPressed.KeyRotate);
				changeRotation();
			}
		}
	}

}
