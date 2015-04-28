package com.kry.brickgame.boards;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author noLive
 */
public class Board implements Cloneable, Serializable {
	/**
	 * Cell type
	 */
	public static enum Cell {
		Empty, Full, Blink
	}
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private static final long serialVersionUID = -2792579857001935997L;
	
	protected final static Cell E = Cell.Empty;
	protected final static Cell F = Cell.Full;
	protected final static Cell B = Cell.Blink;
	
	private final int width;
	private final int height;
	
	private Cell[][] board;
	
	/**
	 * The copy constructor of a Board
	 * 
	 * @param aBoard
	 *            - a board for copying
	 */
	public Board(Board aBoard) {
		this(aBoard.width, aBoard.height);
		for (int i = 0; i < aBoard.width; i++) {
			board[i] = aBoard.board[i].clone();
		}
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
		board = new Cell[width][height];
		
		clearBoard();
	}
	
	/**
	 * Clears the cells of the board
	 */
	public void clearBoard() {
		lock.writeLock().lock();
		try {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					board[i][j] = E;
				}
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public Board clone(){
		Board cloned;
		try {
			cloned = (Board) super.clone();
			cloned.board = board.clone();
			for (int i = 0; i < width; i++) {
				cloned.board[i] = board[i].clone();
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			cloned = new Board(this);
		}
		return cloned;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Board other = (Board) obj;
		if (height != other.height) return false;
		if (width != other.width) return false;
        return Arrays.deepEquals(board, other.board);
    }
	
	public Cell[][] getBoard() {
		lock.readLock().lock();
		try {
			Cell[][] newBoard = board.clone();
			for (int i = 0; i < board.length; i++) {
				newBoard[i] = board[i].clone();
			}
			return newBoard;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public Cell getCell(int x, int y) {
		lock.readLock().lock();
		try {
			return board[x][y];
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public Cell[] getColumn(int x) {
		lock.readLock().lock();
		try {
			Cell column[] = new Cell[getHeight()];

            System.arraycopy(board[x], 0, column, 0, column.length);
			
			return column;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public boolean hasBlinkedCell() {
		lock.readLock().lock();
		try {
			for (Cell[] column : board) {
				for (Cell element : column)
					if (element == Cell.Blink) return true;
			}
			return false;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public int getHeight() {
		return height;
	}
	
	public Cell[] getRow(int y) {
		lock.readLock().lock();
		try {
			Cell row[] = new Cell[getWidth()];
			
			for (int i = 0; i < row.length; i++) {
				row[i] = board[i][y];
			}
			
			return row;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public int getWidth() {
		return width;
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
	
	public void setCell(Cell cell, int x, int y) {
		lock.writeLock().lock();
		try {
			board[x][y] = cell;
		} finally {
			lock.writeLock().unlock();
		}
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
        if (x < 0 || x >= board.length) return;
		lock.writeLock().lock();
		try {
            int columnLength = Math.min(column.length, board[x].length);
            System.arraycopy(column, 0, board[x], 0, columnLength);
		} finally {
			lock.writeLock().unlock();
		}
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
        if (y < 0 || y >= board[0].length) return;
        lock.writeLock().lock();
        try {
            int rowLength = Math.min(row.length, board.length);
			for (int i = 0; i < rowLength; i++) {
			    board[i][y] = row[i];
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Board [").append(width).append("x").append(height).append("]\n");
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
