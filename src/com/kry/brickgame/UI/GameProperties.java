package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.fullColor;

import java.awt.Color;
import java.io.Serializable;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * The Game main properties
 * 
 * @author noLive
 */
public class GameProperties implements Serializable {
	private static final long serialVersionUID = 8838905157373710196L;
	
	protected Board board;
	protected Board preview;
	protected Status status;
	protected String info;
	protected String hiScores;
	protected int speed;
	protected int level;
	protected Rotation rotation;
	protected boolean mute;
	/**
	 * Color of elements that change their state from active to inactive
	 */
	public Color blinkColor;
	/**
	 * Flag for show high scores
	 */
	public boolean showHiScores;
	/**
	 * Flag for show "lives" label
	 */
	public boolean showLives;
	/**
	 * Flag for show "next" label
	 */
	public boolean showNext;
	/**
	 * Flag for blinking "Pause" icon
	 */
	public boolean showPauseIcon;
	
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
		
		blinkColor = fullColor;
		showPauseIcon = false;
		showHiScores = false;
		showLives = false;
		showNext = false;
	}
	
	/**
	 * The copy constructor
	 * 
	 * @param aObj
	 */
	private GameProperties(GameProperties aObj) {
		board = aObj.board.clone();
		preview = aObj.preview.clone();
		status = aObj.status;
		info = aObj.info;
		hiScores = aObj.hiScores;
		speed = aObj.speed;
		level = aObj.level;
		rotation = aObj.rotation;
		mute = aObj.mute;
		
		blinkColor = aObj.blinkColor;
		showPauseIcon = aObj.showPauseIcon;
		showHiScores = aObj.showHiScores;
		showLives = aObj.showLives;
		showNext = aObj.showNext;
	}
	
	@Override
	protected GameProperties clone() {
		return new GameProperties(this);
	}
	
	protected boolean isLabelsEquals(GameProperties other) {
		if (this == other) return true;
		if (other == null) return false;
		if (showHiScores != other.showHiScores) return false;
		if (showLives != other.showLives) return false;
		if (showNext != other.showNext) return false;
		if (showPauseIcon != other.showPauseIcon) return false;
		if (mute != other.mute) return false;
		if (speed != other.speed) return false;
		if (level != other.level) return false;
		if (status == null) {
			if (other.status != null) return false;
		} else if (status != other.status) return false;
		if (rotation == null) {
			if (other.rotation != null) return false;
		} else if (rotation != other.rotation) return false;
		if (info == null) {
			if (other.info != null) return false;
		} else if (!info.equals(other.info)) return false;
		if (hiScores == null) {
			if (other.hiScores != null) return false;
		} else if (!hiScores.equals(other.hiScores)) return false;
		return true;
	}
}
