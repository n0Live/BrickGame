package com.kry.brickgame.games;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.shapes.CoordinatedShape;
import com.kry.brickgame.shapes.Shape;

/**
 * @author noLive
 */
public enum GameUtils {
	;
	
	/**
	 * Suspended keys
	 * <p>
	 * {@code Long} <b>value</b> - time to which the processing of a key is
	 * suspended.
	 */
	static final Map<KeyPressed, Long> suspendedKeys = new HashMap<>(KeyPressed.values().length);
	
	/**
	 * Add randomly generated lines on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param fromLine
	 *            line, which starts the addition
	 * @param linesCount
	 *            count of added lines
	 * @param isUpwardDirection
	 *            if {@code true}, then the bottom-up direction of addition
	 * @return the board after adding lines
	 */
	static Board addLinesToBoard(Board board, int fromLine, int linesCount,
	        boolean isUpwardDirection) {
		if (board == null) return null;
		
		if (linesCount < 1//
		        || isUpwardDirection && //
		        fromLine + linesCount - 1 > board.getHeight()//
		        || !isUpwardDirection && //
		        fromLine - (linesCount - 1) < 0) return board;
		
		// checks whether there are full cells at a distance of
		// <i>linesCount</i> from the top or the bottom of the board
		for (int i = 0; i < board.getWidth(); i++)
			if (//
			isUpwardDirection && //
			        board.getCell(i, board.getHeight() - 1 - linesCount) == Cell.Full//
			        || !isUpwardDirection && //
			        board.getCell(i, linesCount - 1) == Cell.Full//
			) return board;
		
		Cell newLines[][] = new Cell[linesCount][board.getWidth()];
		
		Board resultBoard = new Board(board.getWidth(), board.getHeight());
		
		// picks up or downs the lines of the board
		if (isUpwardDirection) {
			for (int y = board.getHeight() - 1 - 1; y > fromLine + linesCount - 1; y--) {
				resultBoard.setRow(board.getRow(y - 1), y);
			}
		} else {
			for (int y = 0; y < fromLine - (linesCount - 1); y++) {
				resultBoard.setRow(board.getRow(y + 1), y);
			}
		}
		
		// generates a new lines
		Random r = new Random();
		for (int line = 0; line < linesCount; line++) {
			boolean hasEmpty = false;
			boolean hasFull = false;
			
			for (int i = 0; i < board.getWidth(); i++)
				if (r.nextBoolean()) {
					newLines[line][i] = Cell.Empty;
					hasEmpty = true;
				} else {
					newLines[line][i] = Cell.Full;
					hasFull = true;
				}
			
			// if all the cells were empty, creates a full one in a random place
			// of the line
			if (!hasEmpty || !hasFull) {
				newLines[line][r.nextInt(board.getWidth())] = !hasEmpty ? Cell.Empty : Cell.Full;
			}
			
			// adds the created line to the board
			resultBoard.setRow(newLines[line], isUpwardDirection ? fromLine + line : fromLine
			        - line);
		}
		return resultBoard;
	}
	
	/**
	 * Shift the contents of the board horizontally on the delta x
	 * 
	 * @param board
	 *            the board for horizontal shift
	 * @param dX
	 *            delta x, if {@code dX > 0} then shift to the right, otherwise
	 *            shift to the left
	 * @return the board after horizontal shift
	 */
	public static Board boardHorizontalShift(Board board, int dX) {
		if (board == null) return null;
		
		// If dX is greater than the width of the board, it is reduced
		int reducedDX = dX % board.getWidth();
		
		if (reducedDX == 0) return board;
		
		Board resultBoard = board.clone();
		
		for (int i = 0; i < Math.abs(reducedDX); i++) {
			// if shift to the right, then get the first column as temporary,
			// otherwise get the last column
			Cell[] tempColumn = reducedDX > 0 ? board.getColumn(board.getWidth() - 1) : board
			        .getColumn(0);
			
			for (int j = 0; j < board.getWidth(); j++) {
				Cell[] nextColumn;
				// replace the column on the side of the board to the
				// appropriate column on the other side
				if (j == 0 && reducedDX > 0 || j == board.getWidth() - 1 && reducedDX < 0) {
					nextColumn = tempColumn;
				} else {
					// replace the column in the middle of the board to the
					// appropriate adjacent column
					nextColumn = board.getColumn(j + (reducedDX > 0 ? -1 : 1));
				}
				resultBoard.setColumn(nextColumn, j);
			}
		}
		
		return resultBoard;
	}
	
	/**
	 * Collision check of the new figure with the
	 * {@link #checkBoardCollisionVertical vertical} and the
	 * {@link #checkBoardCollisionHorizontal horizontal} boundaries of the board
	 * 
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            - the new figure
	 * @param x
	 *            - x-coordinate of the figures
	 * @param y
	 *            - y-coordinate of the figures
	 * @return {@code true} if there is a collision
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollisionHorizontal
	 * @see #checkCollision
	 */
	static boolean checkBoardCollision(Board board, Shape piece, int x, int y) {
		return checkBoardCollisionVertical(board, piece, y, true)
		        || checkBoardCollisionHorizontal(board, piece, x);
	}
	
	/**
	 * Collision check of the new figure with the horizontal boundaries of the
	 * board
	 * 
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            - the new figure
	 * @param x
	 *            - x-coordinate of the figure
	 * @return {@code true} if there is a collision
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	static boolean checkBoardCollisionHorizontal(Board board, Shape piece, int x) {
		return x + piece.minX() < 0 || x + piece.maxX() >= board.getWidth();
	}
	
	/**
	 * Collision check of the new figure with the vertical boundaries of the
	 * board
	 * 
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            the new figure
	 * @param y
	 *            y-coordinate of the figure
	 * @param checkTopBoundary
	 *            is it necessary to check the upper boundary
	 * @return {@code true} if there is a collision
	 * @see #checkBoardCollisionHorizontal
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	static boolean checkBoardCollisionVertical(Board board, Shape piece, int y,
	        boolean checkTopBoundary) {
		return checkTopBoundary && y + piece.maxY() >= board.getHeight() || y + piece.minY() < 0;
	}
	
	/**
	 * Collision check of the new figure with a filled cells on the board
	 * 
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            the new figure
	 * @param x
	 *            x-coordinate of the figure
	 * @param y
	 *            y-coordinate of the figure
	 * @return {@code true} if there is a collision
	 * @see #checkBoardCollisionHorizontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	static boolean checkCollision(Board board, Shape piece, int x, int y) {
		return checkCollision(board, piece, x, y, false);
	}
	
	/**
	 * Collision check of the new figure with a filled cells on the board
	 * 
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            the new figure
	 * @param x
	 *            x-coordinate of the figure
	 * @param y
	 *            y-coordinate of the figure
	 * @param withBorder
	 *            include in the check collision the area which one cell sized
	 *            around the figure
	 * @return {@code true} if there is a collision
	 * @see #checkBoardCollisionHorizontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	static boolean checkCollision(Board board, Shape piece, int x, int y, boolean withBorder) {
		int board_x, board_y;
		Board checkBoard = board.clone();
		
		int minCheckRange = withBorder ? -1 : 0;
		int maxCheckRange = withBorder ? 1 : 0;
		
		for (int k = 0; k < piece.getLength(); k++) {
			// include in the check collision the area around the point
			for (int i = minCheckRange; i <= maxCheckRange; i++) {
				for (int j = minCheckRange; j <= maxCheckRange; j++) {
					board_x = x + piece.x(k) + i;
					board_y = y + piece.y(k) + j;
					
					if (board_x < 0 || board_x >= checkBoard.getWidth() || board_y < 0
					        || board_y >= checkBoard.getHeight()) {
						continue;
					}
					
					if (checkBoard.getCell(board_x, board_y) != Cell.Empty) return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Collision check of two figures with coordinates
	 * 
	 * @param first
	 *            the first figure
	 * @param second
	 *            the second figure
	 * @return {@code true} if there is a collision
	 */
	static boolean checkTwoShapeCollision(CoordinatedShape first, CoordinatedShape second) {
		if (first == null || second == null) return false;
		
		CoordinatedShape checkedFirst = first.clone();
		CoordinatedShape checkedSecond = second.clone();
		// when the figures are placed too far apart, returns false
		if (Math.abs(checkedFirst.y() + checkedFirst.minY() - checkedSecond.y()
		        + checkedSecond.minY()) > Math.max(checkedFirst.getHeight(),
		        checkedSecond.getHeight())
		        && Math.abs(checkedFirst.x() + checkedFirst.minX() - checkedSecond.x()
		                + checkedSecond.minX()) > Math.max(checkedFirst.getWidth(),
		                checkedSecond.getWidth())) return false;
		
		for (int i = 0; i < checkedFirst.getLength(); i++) {
			int givenFirstX = checkedFirst.x() + checkedFirst.x(i);
			int givenFirstY = checkedFirst.y() + checkedFirst.y(i);
			
			for (int j = 0; j < checkedSecond.getLength(); j++) {
				int givenSecondX = checkedSecond.x() + checkedSecond.x(j);
				int givenSecondY = checkedSecond.y() + checkedSecond.y(j);
				
				if (givenFirstX == givenSecondX && givenFirstY == givenSecondY) return true;
			}
		}
		return false;
	}
	
	/**
	 * Drawing the point of the figure on the board.
	 * <p>
	 * If the point is outside the borders of the board, then drawing it on the
	 * other side of the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate position on the board of the figure
	 * @param y
	 *            y-coordinate position on the board of the figure
	 * @param fill
	 *            type of fill the point
	 * @return the board with the point
	 */
	static Board drawPoint(Board board, int x, int y, Cell fill) {
		int board_x = x;
		int board_y = y;
		
		// check if the point is outside the borders of the board
		if (board_x < 0) {
			board_x = board.getWidth() + board_x;
		} else if (board_x >= board.getWidth()) {
			board_x = board_x - board.getWidth();
		}
		if (board_y < 0) {
			board_y = board.getHeight() + board_y;
		} else if (board_y >= board.getHeight()) {
			board_y = board_y - board.getHeight();
		}
		
		Board resultBoard = board.clone();
		resultBoard.setCell(fill, board_x, board_y);
		
		return resultBoard;
	}
	
	/**
	 * Drawing the figure with coordinates on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param shape
	 *            the figure
	 * @param fill
	 *            {@code Cells.Full} or {@code Cells.Blink} - to draw the
	 *            figure, {@code Cells.Empty} - to erase the figure
	 * @return the board with the figure
	 */
	static Board drawShape(Board board, CoordinatedShape shape, Cell fill) {
		return drawShape(board, shape.x(), shape.y(), shape, fill);
	}
	
	/**
	 * Drawing the figure on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate position on the board of the figure
	 * @param y
	 *            y-coordinate position on the board of the figure
	 * @param shape
	 *            the figure
	 * @param fill
	 *            {@code Cells.Full} or {@code Cells.Blink} - to draw the
	 *            figure, {@code Cells.Empty} - to erase the figure
	 * @return the board with the figure
	 */
	static Board drawShape(Board board, int x, int y, Shape shape, Cell fill) {
		if (shape == null || board == null) return board;
		
		Board resultBoard = board.clone();
		for (int i = 0; i < shape.getLength(); i++) {
			int board_x = x + shape.x(i);
			int board_y = y + shape.y(i);
			
			// if the figure does not leave off the board
			if (board_y < board.getHeight() && board_y >= 0 && board_x < board.getWidth()
			        && board_x >= 0) {
				// draws the point of the figure on the board
				resultBoard = drawPoint(resultBoard, board_x, board_y, fill);
			}
		}
		return resultBoard;
	}
	
	/**
	 * Returns the full inverted copy of the specified board
	 * 
	 * @param board
	 *            specified board
	 * @return the full inverted copy of the board
	 */
	static Board getInvertedBoard(Board board) {
		if (board == null) return null;
		
		Board resultBoard = board.clone();
		for (int i = 0; i < resultBoard.getWidth(); i++) {
			Cell[] reversed = new Cell[board.getHeight()];
			for (int j = 0; j < reversed.length; j++) {
				reversed[j] = board.getCell(i, reversed.length - j - 1);
			}
			resultBoard.setColumn(reversed, resultBoard.getWidth() - i - 1);
		}
		return resultBoard;
	}
	
	/**
	 * Returns the inverted horizontal copy of the specified board
	 * 
	 * @param board
	 *            specified board
	 * @return the inverted horizontal copy of the board
	 */
	static Board getInvertedHorizontalBoard(Board board) {
		if (board == null) return null;
		
		Board resultBoard = board.clone();
		for (int i = 0; i < resultBoard.getWidth(); i++) {
			resultBoard.setColumn(board.getColumn(i), resultBoard.getWidth() - i - 1);
		}
		return resultBoard;
	}
	
	/**
	 * Returns the inverted vertical copy of the specified board
	 * 
	 * @param board
	 *            specified board
	 * @return the inverted vertical copy of the board
	 */
	static Board getInvertedVerticalBoard(Board board) {
		if (board == null) return null;
		
		Board resultBoard = board.clone();
		for (int i = 0; i < resultBoard.getHeight(); i++) {
			resultBoard.setRow(board.getRow(i), resultBoard.getHeight() - i - 1);
		}
		return resultBoard;
	}
	
	/**
	 * Insert the cells to the board. Coordinate ( {@code x, y}) is set a point,
	 * which gets the lower left corner of the {@code cells}
	 * 
	 * @param board
	 *            the board for insertion
	 * @param cells
	 *            the cells to insert
	 * @param x
	 *            x-coordinate for the insertion
	 * @param y
	 *            y-coordinate for the insertion
	 * @return a new board after the insertion.
	 */
	static Board insertCellsToBoard(Board board, Cell[][] cells, int x, int y) {
		if (x >= board.getWidth() || y >= board.getHeight() || x + cells.length <= 0
		        || y + cells[0].length <= 0) return board;
		
		// calculate the shift when the cells is not completely inserted into
		// the board
		int fromX = x < 0 ? -x : 0;
		int toX = x + cells.length >= board.getWidth() ? board.getWidth() - x : cells.length;
		
		int fromY = y < 0 ? -y : 0;
		int toY = y + cells[0].length >= board.getHeight() ? board.getHeight() - y
		        : cells[0].length;
		
		Board resultBoard = board.clone();
		for (int i = fromX; i < toX; i++) {
			for (int j = fromY; j < toY; j++) {
				resultBoard.setCell(cells[i][j], x + i, y + j);
			}
		}
		return resultBoard;
	}
	
	/**
	 * Verifies that the selected line consists only of a filled cells.
	 * 
	 * @param board
	 *            board to check.
	 * @param y
	 *            number of the checked line.
	 * @return {@code true} if the line is full; {@code false} - otherwise.
	 */
	static boolean isFullLine(Board board, int y) {
		boolean result = true;
		for (int x = 0; x < board.getWidth(); x++)
			if (board.getCell(x, y) == Cell.Empty) {
				result = false;
				break;
			}
		return result;
	}
	
	/**
	 * Returns {@code true} if processing of the {@code key} is suspended.
	 * 
	 * @param key
	 *            specified key
	 */
	static boolean isKeySuspended(KeyPressed key) {
		Long value = suspendedKeys.get(key);
		if (value != null) {
			if (value > System.currentTimeMillis()) return true;
			suspendedKeys.remove(key);
		}
		return false;
	}
	
	/**
	 * Suspends processing of the {@code key} for a specified period of time in
	 * milliseconds.
	 * 
	 * @param key
	 *            specified key
	 * @param millis
	 *            period of time
	 */
	static void setKeyDelay(KeyPressed key, int millis) {
		suspendedKeys.put(key, System.currentTimeMillis() + millis);
	}
	
	/**
	 * Sleep for the specified number of milliseconds
	 * 
	 * @param millis
	 *            the length of time to sleep in milliseconds
	 */
	static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
}
