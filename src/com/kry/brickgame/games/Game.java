package com.kry.brickgame.games;

import static com.kry.brickgame.IO.ScoresManager.getScoresManager;
import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.BOARD_HEIGHT;
import static com.kry.brickgame.games.GameConsts.BOARD_WIDTH;
import static com.kry.brickgame.games.GameConsts.CB_GAME_OVER;
import static com.kry.brickgame.games.GameConsts.MAX_GAME_LEVEL;
import static com.kry.brickgame.games.GameConsts.MAX_GAME_SPEED;
import static com.kry.brickgame.games.GameConsts.MAX_VISIBLE_SCORES;
import static com.kry.brickgame.games.GameConsts.PREVIEW_HEIGHT;
import static com.kry.brickgame.games.GameConsts.PREVIEW_WIDTH;
import static com.kry.brickgame.games.GameUtils.getInvertedVerticalBoard;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.isKeySuspended;
import static com.kry.brickgame.games.GameUtils.sleep;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.kry.brickgame.IO.GameLoader;
import com.kry.brickgame.UI.GameEvent;
import com.kry.brickgame.UI.GameListener;
import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.games.GameSound.Music;
/**
 * @author noLive
 */
public abstract class Game implements Callable<Game>, Serializable {
	private static final long serialVersionUID = -8891762583782516818L;

	private static final ArrayList<GameListener> listeners = new ArrayList<>();

	/**
	 * Is the sound turned off?
	 */
	private static boolean mute;

	static final Object lock = new Object();

	static final Random r = new Random();

	/**
	 * Set of the pressed keys
	 */
	final Set<KeyPressed> keys = EnumSet.noneOf(KeyPressed.class);

	/**
	 * Speed
	 */
	private int speed;

	/**
	 * Cached value of the genuine speed.
	 * <p>
	 * A zero or negative value indicates that not cached values yet.
	 */
	private int cachedGenuineSpeed;

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
	int boardWidth;
	/**
	 * Height of the board
	 */
	int boardHeight;
	/**
	 * Width of the preview board
	 */
	int previewWidth;
	/**
	 * Height of the preview board
	 */
	int previewHeight;
	/**
	 * X-coordinate position on the board
	 */
	int curX;
	/**
	 * Y-coordinate position on the board
	 */
	int curY;
	/**
	 * Game status
	 */
	private volatile Status status;
	/**
	 * Next game instance
	 */
	transient Game nextGame;

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
	private Board preview;

	/**
	 * If true than game will exit to selector menu
	 */
	volatile transient boolean exitToMainMenuFlag;
	/**
	 * If true than game will quit (with or without save)
	 */
	volatile transient boolean quitFlag;
	/**
	 * If true then game will paused as soon as it would possible
	 */
	volatile transient boolean deferredPauseFlag;

	/**
	 * Scheduled thread pool
	 */
	static final ScheduledExecutorService scheduledExecutors = Executors
			.newScheduledThreadPool(2);

	/**
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;

	/**
	 * Was the game deserialized?
	 */
	boolean desirialized;

	public static synchronized void addGameListener(GameListener listener) {
		listeners.add(listener);
	}

	static void fireMuteChanged(boolean mute) {
		GameEvent event = new GameEvent(Game.class, mute);
		for (GameListener listener : listeners) {
			listener.muteChanged(event);
		}
	}

	public static synchronized GameListener[] getGameListeners() {
		return listeners.toArray(new GameListener[listeners.size()]);
	}

	/**
	 * Gets current mute status
	 * 
	 * @return {@code true} - sounds are playing, {@code false} - sounds don't
	 *         playing
	 */
	public static boolean isMuted() {
		return mute;
	}

	public static synchronized void removeGameListener(GameListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Sets play or not game's sounds
	 * 
	 * @param mute
	 *            {@code true} - sounds are playing, {@code false} - sounds
	 *            don't playing
	 */
	public static void setMuted(boolean mute) {
		Game.mute = mute;
		fireMuteChanged(mute);
	}

	/**
	 * The Game
	 */
	public Game() {
		desirialized = false;
		curX = 0;
		curY = 0;

		board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		preview = new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT);
		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		previewWidth = preview.getWidth();
		previewHeight = preview.getHeight();

		speed = 1;
		level = 1;
		status = Status.None;
		rotation = Rotation.NONE;

		exitToMainMenuFlag = false;
		quitFlag = false;
		deferredPauseFlag = false;

		setDrawInvertedBoard(false);

		timePoint = System.currentTimeMillis();
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
	protected Game(int speed, int level, Board board, Board preview,
			Rotation rotation, int type) {
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
		this(speed, level, Rotation.NONE, type);
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
	 * Returns {@code true} if this game has been interrupted.
	 * 
	 * @return <code>true</code> if this game has been interrupted;
	 *         <code>false</code> otherwise.
	 */
	protected boolean isInterrupted() {
		return exitToMainMenuFlag || Thread.currentThread().isInterrupted();
	}

	/**
	 * Returns {@code true} if this game's animation has been skipped.
	 * 
	 * @return <code>true</code> if animation has been skipped;
	 *         <code>false</code> otherwise.
	 */
	protected boolean isSkipAnimation() {
		return (quitFlag || deferredPauseFlag || isInterrupted());
	}

	/**
	 * Gets a next game after this
	 * 
	 * @return a next game
	 */
	protected Game getNextGame() {
		if (nextGame == null) nextGame = new GameSelector();
		return nextGame;
	}

	/**
	 * Animated clearing of the board on Game Over
	 */
	private void animatedClearBoard() {
		animatedClearBoard(CB_GAME_OVER);
	}

	/**
	 * Animated clearing of the board (upwards then downwards)
	 * 
	 * @param millis
	 *            duration of the animation in milliseconds
	 */
	void animatedClearBoard(int millis) {
		// label for exit from both for-loops
		CLEAR: if (!isSkipAnimation()) {
			// delay between animation frames
			int delay = millis / (boardHeight * 2);

			// the board is filled upwards
			for (int y = 0; y < boardHeight; y++) {
				processKeys();
				if (isSkipAnimation()) break CLEAR;
				for (int x = 0; x < boardWidth; x++) {
					board.setCell(Cell.Full, x, y);
				}
				fireBoardChanged(board);
				sleep(delay);
			}
			// and is cleaned downwards
			for (int y = boardHeight - 1; y >= 0; y--) {
				processKeys();
				if (isSkipAnimation()) break CLEAR;
				for (int x = 0; x < boardWidth; x++) {
					board.setCell(Cell.Empty, x, y);
				}
				fireBoardChanged(board);
				sleep(delay);
			}
		}
		// immediately clear board when animation interrupted
		if (isSkipAnimation()) {
			board.clearBoard();
			fireBoardChanged(board);
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
	void animatedClearLine(Board board, int x, int y) {
		int x1 = x - 1; // left direction
		int x2 = x; // right direction

		// change status for stopping other work
		Status prevStatus = getStatus();
		setStatus(Status.DoSomeWork);

		if (!isSkipAnimation()) {
			GameSound.playEffect(Effects.remove_line);

			while (x1 >= 0 || x2 < board.getWidth()) {
				if (x1 >= 0) {
					board.setCell(Cell.Empty, x1--, y);
				}
				if (x2 < board.getWidth()) {
					board.setCell(Cell.Empty, x2++, y);
				}

				fireBoardChanged(board);
				sleep(ANIMATION_DELAY * 2);
				if (isSkipAnimation()) break;
			}
		}
		// restore previous status
		setStatus(prevStatus);
	}

	/**
	 * Select another rotation
	 */
	private void changeRotation() {
		setRotation(rotation.getNext());
	}

	/**
	 * Clears the cells of the board and fire the
	 * {@link #fireBoardChanged(Board)} event
	 */
	void clearBoard() {
		board.clearBoard();
		fireBoardChanged(board);
	}

	/**
	 * Clears the cells of the preview and fire the
	 * {@link #firePreviewChanged(Board)} event
	 */
	void clearPreview() {
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
	boolean containsKey(KeyPressed key) {
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
	boolean elapsedTime(int millis) {
		long nowTime = System.currentTimeMillis();
		boolean result = nowTime - timePoint >= millis;
		if (result) {
			timePoint = nowTime;
		}
		return result;
	}

	/**
	 * Exit to Main menu
	 */
	private void exitToMainMenu() {
		stopAllSounds();

		setHiScore();

		if (!exitToMainMenuFlag) {
			nextGame = new GameSelector(speed, level, this.getClass()
					.getCanonicalName(), getType());
			exitToMainMenuFlag = true;
		}
	}

	void fireBoardChanged(Board board) {
		Board invBoard = getInvertedVerticalBoard(board);
		GameEvent event = new GameEvent(this, isInvertedBoard() ? invBoard
				: board.clone(), false);
		for (GameListener listener : listeners) {
			listener.boardChanged(event);
		}
	}

	void fireExit() {
		GameEvent event = new GameEvent(this);
		for (GameListener listener : listeners) {
			listener.exit(event);
		}
	}

	void fireInfoChanged(String info) {
		GameEvent event = new GameEvent(this, info);
		for (GameListener listener : listeners) {
			listener.infoChanged(event);
		}
	}

	void fireLevelChanged(int level) {
		GameEvent event = new GameEvent(this, level);
		for (GameListener listener : listeners) {
			listener.levelChanged(event);
		}
	}

	void firePreviewChanged(Board preview) {
		GameEvent event = new GameEvent(this, preview, true);
		for (GameListener listener : listeners) {
			listener.previewChanged(event);
		}
	}

	void fireRotationChanged(Rotation rotation) {
		GameEvent event = new GameEvent(this, rotation);
		for (GameListener listener : listeners) {
			listener.rotationChanged(event);
		}
	}

	void fireSpeedChanged(int speed) {
		GameEvent event = new GameEvent(this, (float) speed);
		for (GameListener listener : listeners) {
			listener.speedChanged(event);
		}
	}

	void fireStatusChanged(Status status) {
		GameEvent event = new GameEvent(this, status);
		for (GameListener listener : listeners) {
			listener.statusChanged(event);
		}
	}

	/**
	 * Game Over
	 */
	void gameOver() {
		setStatus(Status.GameOver);

		GameSound.playMusic(Music.game_over);

		animatedClearBoard();

		exitToMainMenu();
	}

	/**
	 * Get the main board
	 * 
	 * @return the main board
	 */
	Board getBoard() {
		return board;
	}

	int getHiScore() {
		return getScoresManager()
				.getHiScore(this.getClass().getCanonicalName());
	}

	/**
	 * Level
	 * 
	 * @return level 1-10
	 */
	int getLevel() {
		return level;
	}

	/**
	 * Get the preview board
	 * 
	 * @return the preview board
	 */
	Board getPreview() {
		return preview;
	}

	/**
	 * Get the direction of rotation
	 * 
	 * @return the direction of rotation
	 */
	Rotation getRotation() {
		return rotation;
	}

	/**
	 * Get the score
	 * 
	 * @return the score
	 */
	int getScore() {
		return score;
	}

	/**
	 * Speed level
	 * 
	 * @return speed level 1-10
	 */
	int getSpeed() {
		return speed;
	}

	/**
	 * Returns genuine speed in millisecond or speed level. Lower value of the
	 * genuine speed means a faster speed.
	 * 
	 * @param genuine
	 *            return genuine speed (true) or speed level (false).
	 * @return if {@code genuine} than return genuine speed in millisecond else
	 *         return speed level 1-10
	 */
	int getSpeed(boolean genuine) {
		if (genuine) {
			// getting a uniform distribution from FIRST_LEVEL_SPEED to
			// TENTH_LEVEL_SPEED
			if (cachedGenuineSpeed <= 0) {
				int speedOfFirstLevel = getSpeedOfFirstLevel();
				int speedOfTenthLevel = getSpeedOfTenthLevel();
				float speedIncrement = (float) (speedOfFirstLevel - speedOfTenthLevel)
						/ (10 - 1);
				cachedGenuineSpeed = Math.round(speedOfFirstLevel
						- speedIncrement * (speed - 1));
			}
			return cachedGenuineSpeed;
		}
		return speed;
	}

	/**
	 * Game speed on the 1st level
	 */
	abstract int getSpeedOfFirstLevel();

	/**
	 * Game speed on the 10th level
	 */
	abstract int getSpeedOfTenthLevel();

	/**
	 * Get the status of game
	 * 
	 * @return the status
	 */
	Status getStatus() {
		return status;
	}

	/**
	 * Get the type of game
	 * 
	 * @return the type
	 */
	int getType() {
		return type;
	}

	/**
	 * Initialize the game
	 */
	void init() {
		nextGame = null;

		stopAllSounds();
		fireMuteChanged(mute);

		fireSpeedChanged(speed);
		fireLevelChanged(level);
		fireRotationChanged(rotation);
		fireStatusChanged(status);
		fireInfoChanged(String.valueOf("HI" + getHiScore()));
		fireInfoChanged(String.valueOf(score));
		firePreviewChanged(preview);
		fireBoardChanged(board);
	}

	/**
	 * Stops all of game's sounds
	 */
	@SuppressWarnings("static-method")
	void stopAllSounds() {
		GameSound.stopAllSounds();
	}

	/**
	 * Get the flag for the drawing the board invertedly
	 * 
	 * @return {@code true} if needed to draw the inverted board
	 */
	boolean isInvertedBoard() {
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
	void kaboom(int x, int y) {
		if (isSkipAnimation()) return;
		/**
		 * Nested (inner) class to draw an explosion
		 */
		final class Kaboom {
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
				int lowerLeftX = x - waves[wave][0].length / 2;
				int lowerLeftY = y - waves[wave].length / 2;

				setBoard(insertCellsToBoard(getBoard(), waves[wave],
						lowerLeftX, lowerLeftY));
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
		while (newX - EXPLODE_SIZE / 2 < 0) {
			newX++;
		}
		while (newX - EXPLODE_SIZE / 2 + EXPLODE_SIZE > boardWidth) {
			newX--;
		}
		while (newY - EXPLODE_SIZE / 2 < 0) {
			newY++;
		}
		while (newY - EXPLODE_SIZE / 2 + EXPLODE_SIZE > boardHeight) {
			newY--;
		}

		GameSound.playMusic(Music.kaboom);
		final Kaboom kaboom = new Kaboom();

		for (int i = 0; i < BLAST_WAVE_PASSES; i++) {
			// draw the blast waves
			for (int k = 0; k < kaboom.waves.length; k++) {
				kaboom.blast(newX, newY, k);
				processKeys();
				if (isSkipAnimation()) return;
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
			// stop sound on games
			stopAllSounds();
			// send score
			fireInfoChanged(String.valueOf(score));
			// send high score
			fireInfoChanged(String.valueOf("HI" + setHiScore()));

			setStatus(Status.Paused);
		}
	}

	/**
	 * Pause that game immediately
	 */
	public void pause(boolean immediately) {
		if (immediately) deferredPauseFlag = true;
		pause();
	}

	/**
	 * Processing of key presses
	 */
	void processKeys() {
		// decrease CPU loading
		sleep(30);

		if (keys.isEmpty() || getStatus() == Status.None) return;

		if (keys.contains(KeyPressed.KeyShutdown)) {
			keys.remove(KeyPressed.KeyShutdown);
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
			if (!isMuted()) {
				mute();
			} else {
				unmute();
			}
			return;
		}

		if (getStatus() == Status.Paused)
			if (keys.contains(KeyPressed.KeyRotate)) {
				keys.remove(KeyPressed.KeyRotate);
				changeRotation();
			}
	}

	/**
	 * Sets mute status as {@code true} - game's sounds don't playing
	 */
	void mute() {
		setMuted(true);
		stopAllSounds();
	}

	/**
	 * Sets mute status as {@code false} - game's sounds are playing
	 */
	@SuppressWarnings("static-method")
	void unmute() {
		setMuted(false);
	}

	/**
	 * Quit from the game
	 */
	void quit() {
		if (getStatus() == Status.DoSomeWork
				&& !(this instanceof SplashScreen || this instanceof GameSelector)) {
			quitFlag = true;
		} else {
			quitFlag = false;
			stopAllSounds();
			if (!saveState()) {
				GameLoader.deleteSavedGame();
			}
			fireInfoChanged(String.valueOf("HI" + setHiScore()));
			fireExit();
		}
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		desirialized = true;
	}

	/**
	 * Resume
	 */
	void resume() {
		if (getStatus() == Status.Paused) {
			setStatus(Status.Running);
			deferredPauseFlag = false;
		}
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
	void setBoard(Board board) {
		this.board = board;
		fireBoardChanged(board);
	}

	/**
	 * Set the flag for the drawing the board invertedly
	 * 
	 * @param drawInvertedBoard
	 *            {@code true} if needed to draw the inverted board
	 */
	void setDrawInvertedBoard(boolean drawInvertedBoard) {
		this.drawInvertedBoard = drawInvertedBoard;
	}

	int setHiScore() {
		return getScoresManager().setHiScore(
				this.getClass().getCanonicalName(), getScore());
	}

	/**
	 * Set level and fire the {@link #fireLevelChanged(int)} event
	 * 
	 * @param level
	 *            level 1-10
	 */
	void setLevel(int level) {
		if (level < 1) {
			this.level = MAX_GAME_LEVEL;
		} else if (level > MAX_GAME_LEVEL) {
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
	void setPreview(Board preview) {
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
	void setRotation(Rotation rotation) {
		this.rotation = rotation;
		fireRotationChanged(rotation);
	}

	/**
	 * Set the score and fire the {@link #fireInfoChanged(String)} event
	 * 
	 * @param score
	 *            score 0 - 19999
	 */
	void setScore(int score) {
		if (score > MAX_VISIBLE_SCORES) {
			this.score = MAX_VISIBLE_SCORES;
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
	void setSpeed(int speed) {
		if (speed < 1) {
			this.speed = MAX_GAME_SPEED;
		} else if (speed > MAX_GAME_SPEED) {
			this.speed = 1;
		} else {
			this.speed = speed;
		}
		cachedGenuineSpeed = -1;
		fireSpeedChanged(this.speed);
	}

	/**
	 * Set the status of game and fire the {@link #fireStatusChanged(Status)}
	 * event
	 * 
	 * @param status
	 *            the status of game
	 */
	void setStatus(Status status) {
		this.status = status;
		fireStatusChanged(status);
	}

}
