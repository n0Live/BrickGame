package com.kry.brickgame.boards;

import java.util.Arrays;

/**
 * @author noLive
 * 
 */
public class Board implements Cloneable {

	/**
	 * Cell type
	 */
	public static enum Cell {
		Empty, Full, Blink
	};

	protected final static Cell E = Cell.Empty;
	protected final static Cell F = Cell.Full;
	protected final static Cell B = Cell.Blink;

	private int width;
	private int height;

	private Cell[][] board;

	/**
	 * Creating the board of the given size
	 * 
	 * @param width
	 *            the width of the board
	 * @param height
	 *            the height of the board
	 */
	public Board(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.board = new Cell[width][height];

		clearBoard();
	}

	/**
	 * The copy constructor of a Board
	 * 
	 * @param aBoard
	 *            - a board for copying
	 */
	public Board(Board aBoard) {
		this(aBoard.width, aBoard.height);
		for (int i = 0; i < aBoard.width; i++) {
			this.board[i] = aBoard.board[i].clone();
		}
	}

	public Board clone() {
		Board newBoard = new Board(this);
		return newBoard;
	}

	public Cell[][] getBoard() {
		return this.board;
	}

	public void setBoard(Cell[][] board) {
		this.board = board;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Clears the cells of the board
	 */
	public void clearBoard() {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				this.board[i][j] = E;
	}

	public Cell getCell(int x, int y) {
		return this.board[x][y];
	}

	public void setCell(Cell cell, int x, int y) {
		this.board[x][y] = cell;
	}

	public Cell[] getRow(int y) {
		Cell row[] = new Cell[this.getWidth()];

		for (int i = 0; i < row.length; i++) {
			row[i] = this.board[i][y];
		}

		return row;
	}

	/**
	 * Replacing a single row of the board
	 * 
	 * @param row
	 *            new row
	 * @param y
	 *            y-coordinate of the row
	 */
	public void setRow(Cell[] row, int y) {
		for (int i = 0; i < row.length; i++) {
			try {
				this.board[i][y] = row[i];
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
	}

	public Cell[] getColumn(int x) {
		Cell column[] = new Cell[this.getHeight()];

		for (int i = 0; i < column.length; i++) {
			column[i] = this.board[x][i];
		}

		return column;
	}

	/**
	 * Replacing a single column of the board
	 * 
	 * @param column
	 *            new column
	 * @param x
	 *            x-coordinate of the column
	 */
	public void setColumn(Cell[] column, int x) {
		for (int i = 0; i < column.length; i++) {
			try {
				this.board[x][i] = column[i];
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Board [" + width + "x" + height + "]").append("\n");
		// Going through the board (the board is filled from the bottom up)
		for (int i = height - 1; i >= 0; i--) {
			char line[] = new char[width];
			for (int j = 0; j < width; j++) {
				switch (board[j][i]) {
				case Full:
					line[j] = '0';
					break;
				case Blink:
					line[j] = '*';
					break;
				default:
					line[j] = '.';
					break;
				}
			}
			result.append(line).append("\n");
		}
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Board other = (Board) obj;
		if (!Arrays.deepEquals(board, other.board)) {
			return false;
		}
		if (height != other.height) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}
}
