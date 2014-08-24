package com.kry.brickgame;

import java.util.EventObject;

import com.kry.brickgame.Game.Status;

public class GameEvent extends EventObject {

	private static final long serialVersionUID = 3341669389633046775L;
	private Board board;
	private Status status;
	private String info;
	private int speed;
	private int level;

	/**
	 * Events of the game
	 * 
	 * @param source
	 *            source of the event
	 * @param board
	 *            state of a board
	 * @param status
	 *            game state
	 * @param info
	 *            additional info (score, etc.)
	 * @param speed
	 *            game speed
	 * @param level
	 *            game level
	 */
	public GameEvent(Object source, Board board, Status status, String info,
			int speed, int level) {
		super(source);
		this.board = board;
		this.status = status;
		this.info = info;
		this.speed = speed;
		this.level = level;
	}

	public GameEvent(Object source) {
		super(source);
	}

	public GameEvent(Object source, Board board) {
		super(source);
		this.board = board;
	}

	public GameEvent(Object source, Status status) {
		super(source);
		this.status = status;
	}

	public GameEvent(Object source, String info) {
		super(source);
		this.info = info;
	}

	public GameEvent(Object source, float speed) {
		super(source);
		this.speed = (int) speed;
	}

	public GameEvent(Object source, int level) {
		super(source);
		this.level = level;
	}

	protected Status getStatus() {
		return status;
	}

	protected String getInfo() {
		return info;
	}

	protected Board getBoard() {
		return board;
	}

	protected int getSpeed() {
		return speed;
	}

	protected int getLevel() {
		return level;
	}

}
