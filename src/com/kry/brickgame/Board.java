package com.kry.brickgame;

public class Board {

	/**
	 * Cell type
	 */
	static enum Cell {
		Empty, Full, Blink
	};

	protected final static Cell Empty = Cell.Empty;
	protected final static Cell Full = Cell.Full;
	protected final static Cell Blink = Cell.Blink;

	private Cell[][] board;

	protected Cell[][] getBoard() {
		return this.board;
	}

	protected void setBoard(Cell[][] board) {
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

	private int unshowedLines;

	protected int getUnshowedLines() {
		return unshowedLines;
	}

	/**
	 * Creating the board of a given size and a number of lines that should not
	 * be displayed
	 * 
	 * @param width
	 *            the width of the board
	 * @param height
	 *            the height of the board
	 * @param unshowedLines
	 *            number of lines that should not be displayed
	 */
	public Board(int width, int height, int unshowedLines) {
		super();
		this.width = width;
		this.height = height;
		this.unshowedLines = unshowedLines;
		this.board = new Cell[width][height];
	}

	/**
	 * Creating the board of a given size
	 * 
	 * @param width
	 *            the width of the board
	 * @param height
	 *            the height of the board
	 */
	public Board(int width, int height) {
		this(width, height, 0);
	}

	/**
	 * The copy constructor of a Board
	 * 
	 * @param aBoard
	 *            - a board for copying
	 */
	public Board(Board aBoard) {
		super();
		this.width = aBoard.width;
		this.height = aBoard.height;
		this.board = new Cell[aBoard.width][aBoard.height];
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
	 * Clears the cells of the board
	 */
	protected void clearBoard() {
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j)
				this.board[i][j] = Cell.Empty;
	}

	protected Cell getCell(int x, int y) {
		return this.board[x][y];
	}

	protected void setCell(Cell cell, int x, int y) {
		this.board[x][y] = cell;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Board [" + width + "x" + height + "]").append("\n");
		// Going through the board (the board is filled from the bottom up)
		for (int i = height - 1; i >= 0; --i) {
			char line[] = new char[width];
			for (int j = 0; j < width; ++j) {
				// If the cell[j][i] is full then print "0" otherwise "."
				line[j] = (board[j][i] == Cell.Full) ? '0' : '.';
			}
			result.append(line).append("\n");
		}
		return result.toString();
	}
}
