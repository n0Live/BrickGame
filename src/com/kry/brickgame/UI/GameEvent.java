package com.kry.brickgame.UI;

import java.util.EventObject;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.games.GameConsts;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * @author noLive
 * 
 */
public class GameEvent extends EventObject {
	private static final long serialVersionUID = 3341669389633046775L;

	private GameProperties properties;

	public GameEvent(Object source) {
		super(source);
		properties = new GameProperties();
	}

	/**
	 * Events of the game
	 * 
	 * @param source
	 *            source of the event
	 * @param board
	 *            state of the main board
	 * @param preview
	 *            state of the preview board
	 * @param status
	 *            game state
	 * @param info
	 *            additional info (score, etc.)
	 * @param hiScores
	 *            high scores
	 * @param speed
	 *            game speed
	 * @param level
	 *            game level
	 */
	public GameEvent(Object source, Board board, Board preview, Status status,
			String info, String hiScores, int speed, int level,
			Rotation rotation, boolean mute) {
		this(source);
		properties.board = board;
		properties.preview = preview;
		properties.status = status;
		properties.info = info;
		properties.hiScores = hiScores;
		properties.speed = speed;
		properties.level = level;
		properties.rotation = rotation;
		properties.mute = mute;
	}

	public GameEvent(Object source, Board board) {
		this(source);
		if (board.getWidth() == GameConsts.PREVIEW_WIDTH
				&& board.getHeight() == GameConsts.PREVIEW_HEIGHT)
			properties.preview = board;
		else
			properties.board = board;
	}

	public GameEvent(Object source, Status status) {
		this(source);
		properties.status = status;
	}

	public GameEvent(Object source, String info) {
		this(source);
		if (info.startsWith("HI")) {
			properties.hiScores = info.substring(2);
		} else {
			properties.info = info;
		}
	}

	public GameEvent(Object source, float speed) {
		this(source);
		properties.speed = (int) speed;
	}

	public GameEvent(Object source, int level) {
		this(source);
		properties.level = level;
	}

	public GameEvent(Object source, Rotation rotation) {
		this(source);
		properties.rotation = rotation;
	}

	public GameEvent(Object source, boolean mute) {
		this(source);
		properties.mute = mute;
	}

	protected Status getStatus() {
		return properties.status;
	}

	protected String getInfo() {
		return properties.info;
	}

	protected String gethiScores() {
		return properties.hiScores;
	}

	protected Board getBoard() {
		return properties.board;
	}

	protected Board getPreview() {
		return properties.preview;
	}

	protected int getSpeed() {
		return properties.speed;
	}

	protected int getLevel() {
		return properties.level;
	}

	protected Rotation getRotation() {
		return properties.rotation;
	}

	protected boolean isMute() {
		return properties.mute;
	}

}
