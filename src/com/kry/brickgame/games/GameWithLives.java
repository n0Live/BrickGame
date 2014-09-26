package com.kry.brickgame.games;

import com.kry.brickgame.Board.Cell;

/**
 * @author noLive
 * 
 */
public abstract class GameWithLives extends Game {
	/**
	 * Count of lives
	 * <p>
	 * Allowed values: 0-4
	 */
	private volatile int lives;

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
	}

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
				getPreview().setCell(Cell.Full, 1, i);
				getPreview().setCell(Cell.Full, 2, i);
			}
		}
		firePreviewChanged(getPreview());
	}

}
