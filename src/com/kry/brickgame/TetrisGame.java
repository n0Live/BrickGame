package com.kry.brickgame;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.Timer;

import com.kry.brickgame.Board.Cells;
import com.kry.brickgame.TetrisShape.Tetrominoes;

public class TetrisGame extends Game {

	/**
	 * Падение фигуры закончилось?
	 */
	private boolean isFallingFinished = false;
	/**
	 * Счетчик удаленных линий
	 */
	private int numLinesRemoved = 0;
	/**
	 * Координата "x" размещения на доске текущей фигуры
	 */
	private int curX = 0;
	/**
	 * Координата "y" размещения на доске текущей фигуры
	 */
	private int curY = 0;
	/**
	 * Текущая фигура
	 */
	private TetrisShape curPiece;
	/**
	 * Следующая фигура
	 */
	private TetrisShape nextPiece;

	public TetrisGame() {
		super();

		setStatus(Status.None);
		timer = new Timer(400, this);

		curPiece = new TetrisShape();
		nextPiece = new TetrisShape();

		clearPreview();
		clearBoard();
	}

	/**
	 * События по таймеру
	 */
	public void actionPerformed(ActionEvent e) {
		// если падение закончилось - создаем новую фигуру, иначе - падаем
		// дальше
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	/**
	 * Запуск игры
	 */
	public void start() {
		super.start();

		if (getStatus() == Status.Paused)
			return;

		setStatus(Status.Running);
		isFallingFinished = false;
		numLinesRemoved = 0;

		// Создаем "следующую" фигуру
		nextPiece.setRandomShapeAndRotate();
		newPiece();
		timer.start();
	}

	/**
	 * Пауза/продолжение
	 */
	private void pause() {
		if (getStatus() == Status.Running) {
			timer.stop();
			setStatus(Status.Paused);
		} else if (getStatus() == Status.Paused) {
			timer.start();
			setStatus(Status.Running);
		}
	}

	/**
	 * Быстрое падение до низа доски или препятствия
	 */
	private void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	/**
	 * Падение на одну линию вниз
	 */
	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	/**
	 * Фигура упала вниз
	 */
	private void pieceDropped() {
		Cells fill = (curPiece.getShape() == Tetrominoes.NoShape) ? Cells.Empty
				: Cells.Full;

		// дорисовываем упавшую фигуру к доске
		setBoard(drawPiece(getBoard(), curX, curY, curPiece, fill));

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	/**
	 * Создание новой фигуры
	 */
	private void newPiece() {

		curPiece.setShape(Tetrominoes.NoShape);

		// Координата x - середина доски
		curX = BOARD_WIDTH / 2 - 1;
		// Координата y - верхний край, причем таким образом, чтобы нижний край
		// фигуры находился у границы не показываемых строк #UNSHOWED_LINES
		curY = BOARD_HEIGHT - (UNSHOWED_LINES - nextPiece.maxY());

		// Logger.getAnonymousLogger().info(nextPiece.toString()); // TODO
		// удалить
		// логер

		// Попытка поместить фигуру на доску
		if (!tryMove(nextPiece, curX, curY)) {
			timer.stop();
			setStatus(Status.GameOver);
		} else { // если попытка удалась
			// готовим следующую фигуру и помещаем ее в предпросмотр
			nextPiece.setRandomShapeAndRotate();
			clearPreview();
			Logger.getAnonymousLogger().info(nextPiece.toString()); // TODO
																	// удалить
			setPreview(drawPiece(getPreview(),//
					// Координата "x":
					(PREVIEW_WIDTH / 2) // середина ширины доски
							// половина ширины фигуры
							- ((nextPiece.maxX() - nextPiece.minX() + 1) / 2)
							- (nextPiece.minX()),
					// Координата "y" (помним, что y рисуется снизу вверх):
					(PREVIEW_HEIGHT / 2 - 1)// середина высоты доски
							// половина высоты фигуры
							+ ((nextPiece.maxY() - nextPiece.minY() + 1) / 2)
							+ (nextPiece.minY()),//
					nextPiece, Cells.Full));
		}
	}

	/**
	 * Отрисовка фигуры на доске
	 * 
	 * @param board
	 *            - доска (основная доска или предпросмотр или временная доска
	 *            для проверки)
	 * @param x
	 *            - координата "x" для размещения фигуры на доске
	 * @param y
	 *            - координата "y" для размещения фигуры на доске
	 * @param piece
	 *            - фигура
	 * @param fill
	 *            - заливка: Cells.Full - для рисования фигуры, Cells.Empty -
	 *            для стирания
	 * @return - возвращает доску с нарисованной (или стертой) фигурой
	 */
	private Board drawPiece(Board board, int x, int y, TetrisShape piece,
			Cells fill) {
		for (int i = 0; i < piece.getCoords().length; ++i) {
			int board_x = x + piece.x(i);
			int board_y = y - piece.y(i);
			board.setCell(fill, board_x, board_y);
		}
		return board;
	}

	/**
	 * Перемещение фигуры
	 * 
	 * @param newPiece
	 *            - фигура после перемещения (напр., для поворота фигуры)
	 * @param newX
	 *            - координата "x" для размещения фигуры на доске после
	 *            перемещения
	 * @param newY
	 *            - координата "y" для размещения фигуры на доске после
	 *            перемещения
	 * @return - возвращает true - если перемещение удалось и false - в
	 *         противном случае
	 */
	private boolean tryMove(TetrisShape newPiece, int newX, int newY) {
		// Создаем временную доску, копию основной
		Board board = getBoard().clone();

		// Стираем текущую фигуру с временной доски, чтобы не мешалась при
		// проверках
		board = drawPiece(board, curX, curY, curPiece, Cells.Empty);

		// При пересечении с боковой границей доски, пытаемся сдвинуться в
		// сторону
		int prepX = newX;
		while (checkBoardCollisionHorisontal(newPiece, prepX)) {
			prepX = ((prepX + newPiece.minX()) < 0) ? prepX + 1 : prepX - 1;
		}
		// Проверки
		if (checkBoardCollisionVertical(newPiece, newY))
			return false;
		if (checkCollision(board, newPiece, prepX, newY))
			return false;

		// Стираем текущую фигуру с основной доски и рисуем новую
		setBoard(drawPiece(getBoard(), curX, curY, curPiece, Cells.Empty));
		setBoard(drawPiece(getBoard(), prepX, newY, newPiece, Cells.Full));

		// Заменяем текущую фигуру на новую
		curPiece = newPiece.clone();
		curX = prepX;
		curY = newY;

		return true;
	}

	/**
	 * Удаление заполненных линий
	 */
	private void removeFullLines() {
		Board board = getBoard().clone();

		isFallingFinished = true;

		int numFullLines = 0;

		// проходим по всем строкам
		for (int y = 0; y < BOARD_HEIGHT - 1; ++y) {
			boolean lineIsFull = true;
			// проверка на наличие пустой ячейки в строке
			for (int x = 0; x < BOARD_WIDTH; ++x) {
				if (board.getCell(x, y) == Cells.Empty) {
					lineIsFull = false;
					break;
				}
			}
			if (lineIsFull) {
				++numFullLines;
				// опускаем строки сверху на заполненную строку
				for (int k = y; k < BOARD_HEIGHT - 1; ++k) {
					for (int x = 0; x < BOARD_WIDTH; ++x)
						board.setCell(board.getCell(x, k + 1), x, k);
				}
				// возвращаемся на строку назад (т.к. заполненную мы удалили)
				--y;
			}
		}

		setBoard(board);

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
		}
		curPiece.setShape(Tetrominoes.NoShape);
		fireInfoChanged(String.valueOf(numLinesRemoved));
	}

	/**
	 * Обработка нажатий кнопок
	 */
	public void keyPressed(KeyPressed key) {
		if (getStatus() == Status.None)
			return;

		if (key == KeyPressed.KeyStart) {
			if (getStatus() == Status.GameOver) {
				start();
			} else {
				pause();
			}
			return;
		}

		if (getStatus() != Status.Running)
			return;

		if (!isFallingFinished) {
			switch (key) {
			case KeyLeft:
				tryMove(curPiece, curX - 1, curY);
				break;
			case KeyRight:
				tryMove(curPiece, curX + 1, curY);
				break;
			case KeyRotate:
				TetrisShape rotatedPiece = new TetrisShape(curPiece)
						.rotateRight();
				tryMove(rotatedPiece, curX, curY);
				break;
			case KeyDown:
				oneLineDown();
				break;
			case KeyMode:
				dropDown();
				break;
			default:
				break;
			}
		}
	}

}
