package com.kry.brickgame.UI;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * The Game main properties
 * 
 * @author noLive
 */
public class GameProperties {
	protected Board board;
	protected Board preview;
	protected Status status;
	protected String info;
	protected String hiScores;
	protected int speed;
	protected int level;
	protected Rotation rotation;
	protected boolean mute;

	protected GameProperties() {
		board = null;
		preview = null;
		status = null;
		info = null; // scores
		hiScores = null;
		speed = 1;
		level = 1;
		rotation = null;
		mute = false;
	}

}
