package com.kry.brickgame.games;

import static com.kry.brickgame.IO.ScoresManager.getScoresManager;
import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.BOARD_HEIGHT;
import static com.kry.brickgame.games.GameConsts.BOARD_WIDTH;
import static com.kry.brickgame.games.GameConsts.CB_GAME_OVER;
import static com.kry.brickgame.games.GameConsts.PREVIEW_HEIGHT;
import static com.kry.brickgame.games.GameConsts.PREVIEW_WIDTH;
import static com.kry.brickgame.games.GameUtils.getInvertedBoard;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.isKeySuspended;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.playMusic;
import static com.kry.brickgame.games.GameUtils.sleep;
import static com.kry.brickgame.games.GameUtils.stopAllSounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.kry.brickgame.Main;
import com.kry.brickgame.IO.GameLoader;
import com.kry.brickgame.UI.GameEvent;
import com.kry.brickgame.UI.GameListener;
import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.games.GameUtils.Music;

/**
 * @author noLive
 */
public abstract class Game implements Runnable, Serializable {
	private static final long serialVersionUID = -8891762583782516818L;

	/**
	 * Number of subtypes
	 */
	public static int subtypesNumber;

	private static ArrayList<GameListener> listeners = new ArrayList<GameListener>();

	/**
	 * Is the sound turned off?
	 */
	private static boolean mute;

	public static synchronized void addGameListener(GameListener listener) {
		listeners.add(listener);
	}

	protected static void fireMuteChanged(boolean mute) {
		GameEvent event = new GameEvent(Game.class, mute);
		for (GameListener listener : listeners) {
			listener.muteChanged(event);
		}
	}

	public static synchronized GameListener[] getGameListeners() {
		return listeners.toArray(new GameListener[listeners.size()]);
	}

	protected static boolean isMuted() {
		return mute;
	}

	public static synchronized void removeGameListener(GameListener listener) {
		listeners.remove(listener);
	}

	public static void setMuted(boolean mute) {
		Game.mute = mute;
		if (mute) {
			stopAllSounds();
		}
		fireMuteChanged(mute);
	}

	final Random r;
	/**
	 * Set of the pressed keys
	 */
	protected Set<KeyPressed> keys = new HashSet<KeyPressed>();
	/**
	 * Speed
	 */
	private int speed;
	/**
	 * Level
	 */
	private int level;
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
	 */
	public Game() {
		r = new Random();

		setStatus(Status.None);

		stopAllSounds();

		setSpeed(1);
		setLevel(1);
		setRotation(Rotation.None);

		setBoard(new Board(BOARD_WIDTH, BOARD_HEIGHT));
		setPreview(new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT));

		setDrawInvertedBoard(false);

		curX = 0;
		curY = 0;

		timePoint = System.currentTimeMillis();

		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		previewWidth = preview.getWidth();
		previewHeight = preview.getHeight();

		clearBoard();
		clearPreview();
	}

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
	protected Game(int speed, int level, Board board, Board preview, Rotation rotation, int type) {
		this(speed, level, rotation, type);

		setBoard(board);
		setPreview(preview);

		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		previewWidth = preview.getWidth();
		previewHeight = preview.getHeight();

		clearBoard();
		clearPreview();
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
		this(speed, level, Rotation.None, type);
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
		this();

		setSpeed(speed);
		setLevel(level);
		setRotation(rotation);

		this.type = type;
	}

	/**
	 * Animated clearing of the board on Game Over
	 */
	protected void animatedClearBoard() {
		animatedClearBoard(CB_GAME_OVER);
	}

	/**
	 * Animated clearing of the board (upwards then downwards)
	 * 
	 * @param millis
	 *            duration of the animation in milliseconds
	 */
	protected void animatedClearBoard(int millis) {
		setStatus(Status.DoSomeWork);
		// delay between animation frames
		int delay = millis / (boardHeight * 2);

		// the board is filled upwards
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				board.setCell(Cell.Full, x, y);
			}
			fireBoardChanged(board);
			processKeys();
			sleep(delay);
		}
		// and is cleaned downwards
		for (int y = boardHeight - 1; y >= 0; y--) {
			for (int x = 0; x < boardWidth; x++) {
				board.setCell(Cell.Empty, x, y);
			}
			fireBoardChanged(board);
			processKeys();
			sleep(delay);
		}
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

		// change status form stopping other work
		Status prevStatus = getStatus();
		setStatus(Status.DoSomeWork);

		playEffect(Effects.remove_line);

		while ((x1 >= 0) || (x2 < board.getWidth())) {
			if (x1 >= 0) {
				board.setCell(Cell.Empty, x1--, y);
			}
			if (x2 < board.getWidth()) {
				board.setCell(Cell.Empty, x2++, y);
			}

			fireBoardChanged(board);
			sleep(ANIMATION_DELAY * 2);
		}

		// restore previous status
		setStatus(prevStatus);
	}

	/**
	 * Select another rotation
	 */
	protected void changeRotation() {
		setRotation(rotation.getNext());
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
	 * Returns {@code true} if the {@code keys} set contains the specified
	 * {@code key} and processing of this {@code key} isn't suspended.
	 * 
	 * @param key
	 *            specified key
	 */
	protected boolean containsKey(KeyPressed key) {
		return keys.contains(key) && !isKeySuspended(key);
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
		if (result) {
			timePoint = nowTime;
		}
		return result;
	}

	/**
	 * Exit to Main menu
	 */
	protected void exitToMainMenu() {
		stopAllSounds();

		setHiScore();

		Main.gameSelector.setSpeed(speed);
		Main.gameSelector.setLevel(level);

		Thread.currentThread().interrupt();
		Main.setGame(Main.gameSelector);
	}

	protected synchronized void fireBoardChanged(Board board) {
		GameEvent event = new GameEvent(this, (isInvertedBoard() ? getInvertedBoard(board) : board));
		for (GameListener listener : listeners) {
			listener.boardChanged(event);
		}
	}

	protected void fireExit() {
		GameEvent event = new GameEvent(this);
		for (GameListener listener : listeners) {
			listener.exit(event);
		}
	}

	protected synchronized void fireInfoChanged(String info) {
		GameEvent event = new GameEvent(this, info);
		for (GameListener listener : listeners) {
			listener.infoChanged(event);
		}
	}

	protected synchronized void fireLevelChanged(int level) {
		GameEvent event = new GameEvent(this, level);
		for (GameListener listener : listeners) {
			listener.levelChanged(event);
		}
	}

	protected synchronized void firePreviewChanged(Board preview) {
		GameEvent event = new GameEvent(this, preview);
		for (GameListener listener : listeners) {
			listener.previewChanged(event);
		}
	}

	protected void fireRotationChanged(Rotation rotation) {
		GameEvent event = new GameEvent(this, rotation);
		for (GameListener listener : listeners) {
			listener.rotationChanged(event);
		}
	}

	protected synchronized void fireSpeedChanged(int speed) {
		GameEvent event = new GameEvent(this, (float) speed);
		for (GameListener listener : listeners) {
			listener.speedChanged(event);
		}
	}

	protected synchronized void fireStatusChanged(Status status) {
		GameEvent event = new GameEvent(this, status);
		for (GameListener listener : listeners) {
			listener.statusChanged(event);
		}
	}

	/**
	 * Game Over
	 */
	protected void gameOver() {
		setStatus(Status.GameOver);

		playMusic(Music.game_over);

		animatedClearBoard();

		exitToMainMenu();
	}

	/**
	 * Get the main board
	 * 
	 * @return the main board
	 */
	protected synchronized Board getBoard() {
		return board;
	}

	protected int getHiScore() {
		return getScoresManager().getHiScore(this.getClass().getCanonicalName());
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
	 * Get the preview board
	 * 
	 * @return the preview board
	 */
	protected synchronized Board getPreview() {
		return preview;
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
	 * Get the score
	 * 
	 * @return the score
	 */
	protected synchronized int getScore() {
		return score;
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
	 * Speed
	 * 
	 * @param genuine
	 *            return genuine speed (true) or speed level (false)
	 * @return if genuine than return genuine speed in millisecond else return
	 *         speed level 1-10
	 */
	protected synchronized int getSpeed(boolean genuine) {
		if (genuine)
			// getting a uniform distribution from FIRST_LEVEL_SPEED to
			// TENTH_LEVEL_SPEED
			return (getSpeedOfFirstLevel() - (getSpeedOfFirstLevel() - getSpeedOfTenthLevel())
					/ (10 - 1) * (speed - 1));
		return speed;
	}

	/**
	 * Game speed on the 1st level
	 */
	abstract protected int getSpeedOfFirstLevel();

	/**
	 * Game speed on the 10th level
	 */
	abstract protected int getSpeedOfTenthLevel();

	/**
	 * Get the status of game
	 * 
	 * @return the status
	 */
	protected synchronized Status getStatus() {
		return status;
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
	 * Get the flag for the drawing the board invertedly
	 * 
	 * @return {@code true} if needed to draw the inverted board
	 */
	protected boolean isInvertedBoard() {
		return drawInvertedBoard;
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

				insertCellsToBoard(getBoard(), waves[wave], lowerLeftX, lowerLeftY);
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
				processKeys();
			}
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
			if (key == KeyPressed.KeyDown) {
				keys.add(KeyPressed.KeyUp);
				return;
			}
			if (key == KeyPressed.KeyUp) {
				keys.add(KeyPressed.KeyDown);
				return;
			}
		}
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
			if (key == KeyPressed.KeyDown) {
				keys.remove(KeyPressed.KeyUp);
			} else if (key == KeyPressed.KeyUp) {
				keys.remove(KeyPressed.KeyDown);
			} else {
				keys.remove(key);
			}
		} else {
			keys.remove(key);
		}
	}

	/**
	 * Pause
	 */
	public void pause() {
		if (getStatus() == Status.Running) {
			// send score
			fireInfoChanged(String.valueOf(score));
			// send high score
			fireInfoChanged(String.valueOf("HI" + setHiScore()));

			setStatus(Status.Paused);
			stopAllSounds();
		}
	}

	/**
	 * Processing of key presses
	 */
	protected void processKeys() {
		if (getStatus() == Status.None) return;

		if (keys.contains(KeyPressed.KeyOnOff)) {
			keys.remove(KeyPressed.KeyOnOff);
			quit();
			return;
		}

		if (keys.contains(KeyPressed.KeyReset)) {
			keys.remove(KeyPressed.KeyReset);
			exitToMainMenu();
			return;
		}

		if (keys.contains(KeyPressed.KeyStart)) {
			keys.remove(KeyPressed.KeyStart);
			if (getStatus() != Status.Paused) {
				pause();
			} else {
				resume();
			}
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

	/**
	 * Quit from the game
	 */
	protected void quit() {
		if (!saveState()) {
			GameLoader.deleteSavedGame();
		}
		fireExit();
	}

	/**
	 * Resume
	 */
	public void resume() {
		if (getStatus() == Status.Paused) {
			setStatus(Status.Running);
		}
	}

	@Override
	public void run() {
		start();
	}

	/**
	 * Save state of the current game
	 * 
	 * @return {@code true} - when success, {@code false} - otherwise
	 */
	public boolean saveState() {
		if (!(this instanceof GameSelector || this instanceof SplashScreen)
				&& (getStatus() == Status.Running || getStatus() == Status.Paused)) {
			setStatus(Status.Paused);
			return GameLoader.saveGame(this);
		}
		return false;
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
	 * Set the flag for the drawing the board invertedly
	 * 
	 * @param drawInvertedBoard
	 *            {@code true} if needed to draw the inverted board
	 */
	protected void setDrawInvertedBoard(boolean drawInvertedBoard) {
		this.drawInvertedBoard = drawInvertedBoard;
	}

	protected int setHiScore() {
		return getScoresManager().setHiScore(this.getClass().getCanonicalName(), getScore());
	}

	/**
	 * Set level and fire the {@link #fireLevelChanged(int)} event
	 * 
	 * @param level
	 *            level 1-10
	 */
	protected void setLevel(int level) {
		if (level < 1) {
			this.level = 10;
		} else if (level > 10) {
			this.level = 1;
		} else {
			this.level = level;
		}
		fireLevelChanged(this.level);
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
	 * Set the score and fire the {@link #fireInfoChanged(String)} event
	 * 
	 * @param score
	 *            score 0 - 19999
	 */
	protected synchronized void setScore(int score) {
		if (score > 19999) {
			this.score = 19999;
		} else if (score < 0) {
			this.score = 0;
		} else {
			this.score = score;
		}
		fireInfoChanged(String.valueOf(score));
	}

	/**
	 * Set speed level and fire the {@link #fireSpeedChanged(int)} event
	 * 
	 * @param speed
	 *            speed level 1-10
	 */
	protected void setSpeed(int speed) {
		if (speed < 1) {
			this.speed = 10;
		} else if (speed > 10) {
			this.speed = 1;
		} else {
			this.speed = speed;
		}
		fireSpeedChanged(this.speed);
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

	public void start() {
		fireBoardChanged(board);
		firePreviewChanged(preview);
		fireSpeedChanged(speed);
		fireLevelChanged(level);
		fireRotationChanged(rotation);
		fireMuteChanged(mute);
		fireStatusChanged(status);
		fireInfoChanged(String.valueOf(score));
		fireInfoChanged(String.valueOf("HI" + getHiScore()));
	}

}
