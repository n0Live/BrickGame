package com.kry.brickgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.kry.brickgame.Board.Cells;

/**
 * <p>
 * ‘абричный класс дл€ игр.
 * </p>
 */
public class Game implements ActionListener {

	/**
	 *  оличество линий основного пол€ (доски), которые не должны выводитьс€ на
	 * экран ({@value} ). »спользуетс€ дл€ плавного по€влени€ фигур.
	 */
	public final static int UNSHOWED_LINES = 4;
	/**
	 * Ўирина основного пол€ (доски) ({@value} ).
	 */
	public final static int BOARD_WIDTH = 10;
	/**
	 * ¬ысота основного пол€ (доски) ({@value} ).
	 */
	public final static int BOARD_HEIGHT = 20 + UNSHOWED_LINES;
	/**
	 * Ўирина пол€ предпросмотра ({@value} ).
	 */
	public final static int PREVIEW_WIDTH = 4;
	/**
	 * ¬ысота пол€ предпросмотра ({@value} ).
	 */
	public final static int PREVIEW_HEIGHT = 4;

	protected Timer timer;

	/**
	 * —лушатели, подписанные на событи€ игры.
	 */
	private static ArrayList<IGameListener> listeners = new ArrayList<IGameListener>();

	static enum Status {
		None, Running, Paused, GameOver
	};

	/**
	 * —осто€ние игры
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
	 * ќсновна€ доска
	 */
	private Board board;
	/**
	 * ѕредпросмотр фигур
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
	 * —обытие изменени€ состо€ни€ доски
	 */
	protected void fireBoardChanged(Board board) {
		GameEvent event = new GameEvent(this, board);
		for (IGameListener listener : listeners) {
			listener.boardChanged(event);
		}
	}

	/**
	 * —обытие изменени€ состо€ни€ предпросмотра
	 */
	protected void firePreviewChanged(Board preview) {
		GameEvent event = new GameEvent(this, preview);
		for (IGameListener listener : listeners) {
			listener.previewChanged(event);
		}
	}

	/**
	 * —обытие изменени€ состо€ни€ игры
	 */
	protected void fireStatusChanged(Status status) {
		GameEvent event = new GameEvent(this, status);
		for (IGameListener listener : listeners) {
			listener.statusChanged(event);
		}
	}

	/**
	 * —обытие изменени€ дополнительной информации (счет и т.п.)
	 */
	protected void fireInfoChanged(String info) {
		GameEvent event = new GameEvent(this, info);
		for (IGameListener listener : listeners) {
			listener.infoChanged(event);
		}
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
	 * ќчистка доски
	 */
	protected void clearBoard() {
		board.clearBoard();
		fireBoardChanged(board);
	}

	/**
	 * ќчистка предпросмотра
	 */
	protected void clearPreview() {
		preview.clearBoard();
		firePreviewChanged(preview);
	}

	/**
	 * ѕроверка на пересечение (столкновение) новой фигуры с заполненными
	 * клетками на доске
	 * 
	 * @param board
	 *            - доска
	 * @param piece
	 *            - нова€ фигура
	 * @param x
	 *            - координата "x" фигуры
	 * @param y
	 *            - координата "y" фигуры
	 * @return true - если есть пересечение, false - если пересечени€ нет
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
	 * ѕроверка на пересечение (столкновение) новой фигуры с вертикальными
	 * (верхней и нижней) границами доски
	 * 
	 * @param piece
	 *            - нова€ фигура
	 * @param y
	 *            - координата "y" фигуры
	 * @return true - если есть пересечение, false - если пересечени€ нет
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
	 * ѕроверка на пересечение (столкновение) новой фигуры с горизонтальными
	 * (левой и правой) границами доски
	 * 
	 * @param piece
	 *            - нова€ фигура
	 * @param x
	 *            - координата "x" фигуры
	 * @return true - если есть пересечение, false - если пересечени€ нет
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
	 * ѕроверка на пересечение (столкновение) новой фигуры с
	 * {@link Game#checkBoardCollisionVertical вертикальными} и
	 * {@link Game#checkBoardCollisionHorisontal горизонтальными} границами
	 * доски
	 * 
	 * @param piece
	 *            - нова€ фигура
	 * @param x
	 *            - координата "x" фигуры
	 * @param y
	 *            - координата "y" фигуры
	 * @return true - если есть пересечение, false - если пересечени€ нет
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkCollision
	 */
	protected boolean checkBoardCollision(Shape piece, int x, int y) {
		return checkBoardCollisionVertical(piece, y)
				|| checkBoardCollisionHorisontal(piece, x);
	}

	protected void animatedClearBoard() {
		for (int y = BOARD_HEIGHT - 1; y >= 0; --y) {
			for (int x = 0; x < BOARD_WIDTH; ++x) {
				board.setCell(Cells.Full, x, y);
			}
			try {
				Thread.sleep(100);
				fireBoardChanged(board);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	protected void keyPressed(KeyPressed key) {
		// TODO Auto-generated method stub

	}

	public void start() {
		clearBoard();
		clearPreview();
	}

}
