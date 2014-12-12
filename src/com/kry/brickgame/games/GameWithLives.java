package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.CB_LOSE;
import static com.kry.brickgame.games.GameConsts.CB_WIN;
import static com.kry.brickgame.games.GameUtils.playMusic;
import static com.kry.brickgame.games.GameUtils.sleep;

import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Music;

/**
 * @author noLive
 */
public abstract class GameWithLives extends Game {
	private static final long serialVersionUID = -3573267355159195541L;

	/**
	 * Play "start" music and wait for its ending or, if muted, just wait 1.5
	 * seconds
	 */
	private static void playAndWaitMusic() {
		if (!isMuted()) {
			playMusic(Music.start);
		} else {
			sleep(1500);
		}
	}

	/**
	 * Count of lives
	 * <p>
	 * Allowed values: 0-4
	 */
	private volatile int lives;

	protected boolean isStarted;

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
		this(speed, level, Rotation.None, type);
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

		setLives(4);
		isStarted = true;
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
	 * Loading the specified level
	 */
	protected void loadNewLevel() {
		// play music always except the first isStarted
		if (!isStarted) {
			playAndWaitMusic();
		}
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
	protected void loss(int x, int y) {
		setStatus(Status.DoSomeWork);

		// kaboom and decrease lives
		kaboom(x, y);
		setLives(getLives() - 1);
		if (getLives() > 0) {
			animatedClearBoard(CB_LOSE);
			reloadLevel();
		} else {
			gameOver();
		}
	}

	/**
	 * Reloading the specified level
	 */
	protected void reloadLevel() {
		loadNewLevel();
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
				getPreview().setCell(Cell.Full, 1, i);
				getPreview().setCell(Cell.Full, 2, i);
			}
		}
		firePreviewChanged(getPreview());
	}

	@Override
	protected void start() {
		super.start();
		// play music only in first isStarted, not after deserialization
		if (isStarted) {
			isStarted = false;
			playAndWaitMusic();
		}
	}

	/**
	 * Increase the level and load it
	 */
	protected void win() {
		setStatus(Status.DoSomeWork);

		playMusic(Music.win);
		animatedClearBoard(CB_WIN);

		setLevel(getLevel() + 1);
		if (getLevel() == 1) {
			setSpeed(getSpeed() + 1);
		}

		loadNewLevel();
	}

}
