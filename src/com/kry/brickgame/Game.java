package com.kry.brickgame;

import java.util.ArrayList;

import com.kry.brickgame.Board.Cells;

/**
 * <p>
 * Фабричный класс для игр.
 * </p>
 */
public class Game implements Runnable {

	/*---MAGIC NUMBERS---*/
	/**
	 * Количество линий основного поля (доски), которые не должны выводиться на
	 * экран ({@value} ). Используется для плавного появления фигур.
	 */
	public final static int UNSHOWED_LINES = 4;
	/**
	 * Ширина основного поля (доски) ({@value} ).
	 */
	public final static int BOARD_WIDTH = 10;
	/**
	 * Высота основного поля (доски) ({@value} ).
	 */
	public final static int BOARD_HEIGHT = 20 + UNSHOWED_LINES;
	/**
	 * Ширина поля предпросмотра ({@value} ).
	 */
	public final static int PREVIEW_WIDTH = 4;
	/**
	 * Высота поля предпросмотра ({@value} ).
	 */
	public final static int PREVIEW_HEIGHT = 4;
	/**
	 * Задержка анимации, миллисекунды
	 */
	private final static int ANIMATION_DELAY = 50;
	/*---MAGIC NUMBERS---*/

	protected TimerGame timer;

	private int speed = 0;

	/**
	 * Слушатели, подписанные на события игры.
	 */
	private static ArrayList<IGameListener> listeners = new ArrayList<IGameListener>();

	static enum Status {
		None, Running, Paused, GameOver
	};

	/**
	 * Состояние игры
	 */
	private Status status;

	protected Status getStatus() {
		return status;
	}

	protected void setStatus(Status status) {
		this.status = status;
		fireStatusChanged(status);
	}

	/**
	 * Основная доска
	 */
	private Board board;
	/**
	 * Предпросмотр фигур
	 */
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

	/**
	 * Событие изменения состояния доски
	 */
	protected void fireBoardChanged(Board board) {
		GameEvent event = new GameEvent(this, board);
		for (IGameListener listener : listeners) {
			listener.boardChanged(event);
		}
	}

	/**
	 * Событие изменения состояния предпросмотра
	 */
	protected synchronized void firePreviewChanged(Board preview) {
		GameEvent event = new GameEvent(this, preview);
		for (IGameListener listener : listeners) {
			listener.previewChanged(event);
		}
	}

	/**
	 * Событие изменения состояния игры
	 */
	protected synchronized void fireStatusChanged(Status status) {
		GameEvent event = new GameEvent(this, status);
		for (IGameListener listener : listeners) {
			listener.statusChanged(event);
		}
	}

	/**
	 * Событие изменения дополнительной информации (счет и т.п.)
	 */
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
	 * Очистка доски
	 */
	protected void clearBoard() {
		board.clearBoard();
		fireBoardChanged(board);
	}

	/**
	 * Очистка предпросмотра
	 */
	protected void clearPreview() {
		preview.clearBoard();
		firePreviewChanged(preview);
	}

	/**
	 * Проверка на пересечение (столкновение) новой фигуры с заполненными
	 * клетками на доске
	 * 
	 * @param board
	 *            - доска
	 * @param piece
	 *            - новая фигура
	 * @param x
	 *            - координата "x" фигуры
	 * @param y
	 *            - координата "y" фигуры
	 * @return true - если есть пересечение, false - если пересечения нет
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
	 * Проверка на пересечение (столкновение) новой фигуры с вертикальными
	 * (верхней и нижней) границами доски
	 * 
	 * @param piece
	 *            - новая фигура
	 * @param y
	 *            - координата "y" фигуры
	 * @return true - если есть пересечение, false - если пересечения нет
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
	 * Проверка на пересечение (столкновение) новой фигуры с горизонтальными
	 * (левой и правой) границами доски
	 * 
	 * @param piece
	 *            - новая фигура
	 * @param x
	 *            - координата "x" фигуры
	 * @return true - если есть пересечение, false - если пересечения нет
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollisionHorisontal(Shape piece, int x) {
		if ((x + piece.minX()) < 0 || (x + piece.maxX()) >= BOARD_WIDTH)
			return true;
		return false;
	}

	/**
	 * Проверка на пересечение (столкновение) новой фигуры с
	 * {@link Game#checkBoardCollisionVertical вертикальными} и
	 * {@link Game#checkBoardCollisionHorisontal горизонтальными} границами
	 * доски
	 * 
	 * @param piece
	 *            - новая фигура
	 * @param x
	 *            - координата "x" фигуры
	 * @param y
	 *            - координата "y" фигуры
	 * @return true - если есть пересечение, false - если пересечения нет
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollision(Shape piece, int x, int y) {
		return checkBoardCollisionVertical(piece, y)
				|| checkBoardCollisionHorisontal(piece, x);
	}

	/**
	 * Анимированная очистка доски
	 * 
	 * @param firstRun
	 *            - первый запуск (да|нет)
	 * @return если первый запуск - доска заполняется снизу вверх, иначе
	 *         очищается сверху вниз
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
	 * Анимированная очистка доски в два прохода (снизу вверх и сверху вниз)
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
