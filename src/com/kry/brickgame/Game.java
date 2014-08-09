package com.kry.brickgame;

import java.util.ArrayList;

import com.kry.brickgame.Board.Cells;

public class Game implements Runnable {

	/*---MAGIC NUMBERS---*/
	/**
	 * The number of lines of the base field (the board), that should not be
	 * displayed ({@value} ). Used for smooth the appearance of a figures.
	 */
	public final static int UNSHOWED_LINES = 4;
	/**
	 * Width of the board ({@value} ).
	 */
	public final static int BOARD_WIDTH = 10;
	/**
	 * Height of the board ({@value} ).
	 */
	public final static int BOARD_HEIGHT = 20 + UNSHOWED_LINES;
	/**
	 * Width of the preview board ({@value} ).
	 */
	public final static int PREVIEW_WIDTH = 4;
	/**
	 * Height of the preview board ({@value} ).
	 */
	public final static int PREVIEW_HEIGHT = 4;
	/**
	 * Animation delay in milliseconds
	 */
	private final static int ANIMATION_DELAY = 50;
	/*---MAGIC NUMBERS---*/

	protected TimerGame timer;

	private int speed = 0;

	private static ArrayList<IGameListener> listeners = new ArrayList<IGameListener>();

	static enum Status {
		None, Running, Paused, GameOver
	};

	private Status status;

	protected Status getStatus() {
		return status;
	}

	protected void setStatus(Status status) {
		this.status = status;
		fireStatusChanged(status);
	}

	private Board board;
	private Board preview;

	static enum KeyPressed {
		KeyNone, KeyLeft, KeyRight, KeyUp, KeyDown, KeyRotate, KeyStart, KeyMode, KeyMute
	};

	public Game() {
		board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		preview = new Board(PREVIEW_WIDTH, PREVIEW_HEIGHT);
	}

	public static void addGameListener(IGameListener listener) {
		listeners.add(listener);
	}

	public static IGameListener[] getGameListeners() {
		return listeners.toArray(new IGameListener[listeners.size()]);
	}

	public static void removeGameListener(IGameListener listener) {
		listeners.remove(listener);
	}

	protected void fireBoardChanged(Board board) {
		GameEvent event = new GameEvent(this, board);
		for (IGameListener listener : listeners) {
			listener.boardChanged(event);
		}
	}

	protected synchronized void firePreviewChanged(Board preview) {
		GameEvent event = new GameEvent(this, preview);
		for (IGameListener listener : listeners) {
			listener.previewChanged(event);
		}
	}

	protected synchronized void fireStatusChanged(Status status) {
		GameEvent event = new GameEvent(this, status);
		for (IGameListener listener : listeners) {
			listener.statusChanged(event);
		}
	}

	protected synchronized void fireInfoChanged(String info) {
		GameEvent event = new GameEvent(this, info);
		for (IGameListener listener : listeners) {
			listener.infoChanged(event);
		}
	}

	/**
	 * Speed
	 * 
	 * @param genuine
	 *            return genuine speed (true) or speed level (false)
	 * @return if genuine than return genuine speed in millisecond else return
	 *         speed level 0-9
	 */
	protected int getSpeed(boolean genuine) {
		if (genuine) {
			return (ANIMATION_DELAY * 10) / (speed + 1);
		}
		return speed;
	}

	protected int getSpeed() {
		return getSpeed(false);
	}

	protected void setSpeed(int speed) {
		this.speed = speed;
	}

	protected Board getBoard() {
		return board;
	}

	protected void setBoard(Board board) {
		this.board = board;
		fireBoardChanged(board);
	}

	protected Board getPreview() {
		return preview;
	}

	protected void setPreview(Board preview) {
		this.preview = preview;
		firePreviewChanged(preview);
	}

	/**
	 * Clears the cells of the board and raises the {@code fireBoardChanged}
	 * event
	 */
	protected void clearBoard() {
		board.clearBoard();
		fireBoardChanged(board);
	}

	/**
	 * Clears the cells of the preview and raises the {@code firePreviewChanged}
	 * event
	 */
	protected void clearPreview() {
		preview.clearBoard();
		firePreviewChanged(preview);
	}

	/**
	 * Collision check of a new figure with a filled cells on a board
	 * 
	 * @param board
	 *            - a board
	 * @param piece
	 *            - a new figure
	 * @param x
	 *            - x-coordinate figures
	 * @param y
	 *            - y-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	protected boolean checkCollision(Board board, Shape piece, int x, int y) {
		try {
			for (int i = 0; i < piece.getCoords().length; ++i) {
				int board_x = x + piece.x(i);
				int board_y = y - piece.y(i);
				if (board.getCell(board_x, board_y) != Cells.Empty)
					return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	/**
	 * Collision check of a new figure with the vertical boundaries of the board
	 * 
	 * @param piece
	 *            - a new figure
	 * @param y
	 *            - y-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionVertical(Shape piece, int y) {
		if ((y - piece.maxY()) < 0 || (y - piece.minY()) >= BOARD_HEIGHT)
			return true;
		return false;
	}

	/**
	 * Collision check of a new figure with the horizontal boundaries of the
	 * board
	 * 
	 * @param piece
	 *            - a new figure
	 * @param x
	 *            - x-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionHorizontal(Shape piece, int x) {
		if ((x + piece.minX()) < 0 || (x + piece.maxX()) >= BOARD_WIDTH)
			return true;
		return false;
	}

	/**
	 * Collision check of a new figure with the
	 * {@link Game#checkBoardCollisionVertical vertical} and the
	 * {@link Game#checkBoardCollisionHorizontal horizontal} boundaries of the
	 * board
	 * 
	 * @param piece
	 *            - a new figure
	 * @param x
	 *            - x-coordinate figures
	 * @param y
	 *            - y-coordinate figures
	 * @return true if there is a collision
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollision(Shape piece, int x, int y) {
		return checkBoardCollisionVertical(piece, y)
				|| checkBoardCollisionHorizontal(piece, x);
	}

	/**
	 * Animated clearing of the board
	 * 
	 * @param firstRun
	 *            - is the first run
	 * @return if is the first run then the board is filled upwards, otherwise
	 *         is cleaned downwards
	 */
	protected void animatedClearBoard(boolean firstRun) {
		if (firstRun) {
			for (int y = 0; y < BOARD_HEIGHT; ++y) {
				for (int x = 0; x < BOARD_WIDTH; ++x) {
					board.setCell(Cells.Full, x, y);
				}
				try {
					Thread.sleep(ANIMATION_DELAY);
					fireBoardChanged(board);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} else {
			for (int y = BOARD_HEIGHT - 1; y >= 0; --y) {
				for (int x = 0; x < BOARD_WIDTH; ++x) {
					board.setCell(Cells.Empty, x, y);
				}
				try {
					Thread.sleep(ANIMATION_DELAY);
					fireBoardChanged(board);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	/**
	 * Animated clearing of the board in several passes (upwards then downwards)
	 * 
	 */
	protected void animatedClearBoard() {
		animatedClearBoard(true);
		animatedClearBoard(false);
	}

	protected void gameOver() {
		setStatus(Status.GameOver);
		animatedClearBoard();
		Main.setGame(Main.gameSelector);
	}

	protected void keyPressed(KeyPressed key) {
		// TODO Auto-generated method stub

	}

	public void start() {
		clearBoard();
		clearPreview();
	}

	@Override
	public void run() {
		this.start();
	}

}
