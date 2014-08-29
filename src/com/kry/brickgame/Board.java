package com.kry.brickgame;

/**
 * @author noLive
 * 
 */
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
		for (int i = 0; i < aBoard.width; i++) {
			this.board[i] = aBoard.board[i].clone();
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
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				this.board[i][j] = Empty;
	}

	protected Cell getCell(int x, int y) {
		return this.board[x][y];
	}

	protected void setCell(Cell cell, int x, int y) {
		this.board[x][y] = cell;
	}

	protected Cell[] getLine(int y) {
		Cell line[] = new Cell[this.getWidth()];

		for (int i = 0; i < line.length; i++) {
			line[i] = this.board[i][y];
		}

		return line;
	}

	/**
	 * Replacing a single line of the board
	 * 
	 * @param line
	 *            new line
	 * @param y
	 *            y-coordinate of the line
	 */
	protected void setLine(Cell[] line, int y) {
		for (int i = 0; i < line.length; i++) {
			this.board[i][y] = line[i];
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
}
