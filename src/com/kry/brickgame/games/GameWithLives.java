package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.CB_LOSE;
import static com.kry.brickgame.games.GameConsts.CB_WIN;

import java.util.concurrent.TimeUnit;

import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Music;

/**
 * @author noLive
 */
public abstract class GameWithLives extends Game {
	private static final long serialVersionUID = -3573267355159195541L;

	/**
	 * Count of lives
	 * <p>
	 * Allowed values: 0-4
	 */
	private volatile int lives;

	/**
	 * Whether the game is started?
	 */
	volatile boolean isStarted;

	/**
	 * Duration of playing the start music
	 */
	static final int START_MUSIC_DURATION = 1500;

	/**
	 * The Game with lives without rotation
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game
	 */
	public GameWithLives(int speed, int level, int type) {
		this(speed, level, Rotation.NONE, type);
	}

	/**
	 * The Game with lives
	 * <p>
	 * Game is over when all lives ends
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
	public GameWithLives(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	/**
	 * Lives
	 * 
	 * @return lives 0 - 4
	 */
	int getLives() {
		return lives;
	}

	@Override
	void init() {
		super.init();
		if (!desirialized) setLives(4);
	}

	/**
	 * Loading the specified level
	 */
	void loadNewLevel() {
		isStarted = false;
		playAndWaitMusic();
		setStatus(Status.Running);
	}

	/**
	 * Drawing effect of the explosion and decreasing lives
	 * 
	 * @param x
	 *            x-coordinate of the epicenter of the explosion
	 * @param y
	 *            y-coordinate of the epicenter of the explosion
	 */
	void loss(int x, int y) {
		setStatus(Status.DoSomeWork);
		synchronized (lock) {
			// kaboom and decrease lives
			kaboom(x, y);
			setLives(getLives() - 1);
			if (getLives() > 0) {
				animatedClearBoard(CB_LOSE);
				if (!(exitFlag || Thread.currentThread().isInterrupted())) {
					reloadLevel();
				}
			} else {
				gameOver();
			}
		}
		if (readyToQuitFlag) quit();
	}

	/**
	 * Play "start" music and wait for its ending or, if muted, just wait 1.5
	 * seconds
	 */
	private void playAndWaitMusic() {
		if (!readyToQuitFlag) {
			if (!isMuted()) {
				GameSound.playMusic(Music.start);
			}

			// wait until the start music played
			scheduledExecutors.schedule(new Runnable() {
				@Override
				public void run() {
					isStarted = true;
				}
			}, START_MUSIC_DURATION, TimeUnit.MILLISECONDS);
		} else { // don't wait when quit
			isStarted = true;
		}
	}

	/**
	 * Reloading the specified level
	 */
	void reloadLevel() {
		loadNewLevel();
	}

	/**
	 * Set lives
	 * 
	 * @param lives
	 *            lives 0 - 4
	 */
	void setLives(int lives) {
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
				getPreview().setCell(Cell.Full, 1, i);
				getPreview().setCell(Cell.Full, 2, i);
			}
		}
		firePreviewChanged(getPreview());
	}

	/**
	 * Increase the level and load it
	 */
	void win() {
		setStatus(Status.DoSomeWork);
		synchronized (lock) {
			GameSound.playMusic(Music.win);
			animatedClearBoard(CB_WIN);

			setLevel(getLevel() + 1);
			if (getLevel() == 1) {
				setSpeed(getSpeed() + 1);
			}

			if (!(exitFlag || Thread.currentThread().isInterrupted())) {
				loadNewLevel();
			}
		}
		if (readyToQuitFlag) quit();
	}

}
