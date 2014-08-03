package com.kry.brickgame;

public class Board {

	static enum Cells {
		Empty, Full
	};

	protected final static Cells Empty = Cells.Empty;
	protected final static Cells Full = Cells.Full;

	private Cells[][] board;

	protected Cells[][] getBoard() {
		return this.board;
	}

	protected void setBoard(Cells[][] board) {
		this.board = board;
	}

	private int width;
	
	protected int getWidth() {
		return width;
	}


	private int height;

	protected int getHeight() {
		return height;
	}
	/**
	 * Создание доски
	 * 
	 * @param width
	 *            - ширина доски
	 * @param height
	 *            - высота доски
	 */
	public Board(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.board = new Cells[width][height];
	}

	/**
	 * Копирующий конструктор
	 * 
	 * @param aBoard
	 *            - доска, с которой будет создаваться копия
	 */
	public Board(Board aBoard) {
		super();
		this.width = aBoard.width;
		this.height = aBoard.height;
		this.board = new Cells[aBoard.width][aBoard.height];
		for (int x = 0; x < aBoard.width; ++x) {
			for (int y = 0; y < aBoard.height; ++y) {
				this.board[x][y] = aBoard.board[x][y];
			}
		}
	}

	public Board clone() {
		Board newBoard = new Board(this);
		return newBoard;
	}

	/**
	 * Очистка доски
	 */
	protected void clearBoard() {
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j)
				this.board[i][j] = Cells.Empty;
	}

	protected Cells getCell(int x, int y) {
		return this.board[x][y];
	}

	protected void setCell(Cells cell, int x, int y) {
		this.board[x][y] = cell;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Board [" + width + "x" + height + "]").append("\n");
		// Перебираем строки доски (доска заполняется снизу вверх)
		for (int i = height - 1; i >= 0; --i) {
			char line[] = new char[width];
			for (int j = 0; j < width; ++j) {
				// если ячейка заполнена рисуем "0", иначе "."
				line[j] = (board[j][i] == Cells.Full) ? '0' : '.';
			}
			result.append(line).append("\n");
		}
		return result.toString();
	}
}
