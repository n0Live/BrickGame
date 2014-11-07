package com.kry.brickgame.games;

import java.util.Random;

import com.kry.brickgame.SoundBank;
import com.kry.brickgame.SoundManager;
import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.shapes.CoordinatedShape;
import com.kry.brickgame.shapes.Shape;

/**
 * @author noLive
 * 
 */
public class GameUtils {
	/**
	 * Sound effects
	 */
	public enum Effects {
		select, move, turn, hit_cell, add_cell, bonus, fall, fall_super, remove_line, start_engine, engine;
	}

	/**
	 * Music
	 */
	public enum Music {
		welcome, start, win, game_over, tetris, kaboom;
	}

	/**
	 * Melodies for Dance game
	 */
	public enum Melodies {
		melody1, melody2, melody3, melody4, melody5, melody6, melody7, melody8, melody9;
	}

	public static final SoundBank effects = new SoundBank();
	public static final SoundBank music = new SoundBank();
	public static final SoundBank melodies = new SoundBank();

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
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	protected static boolean checkCollision(Board board, Shape piece, int x,
			int y, boolean withBorder) {
		int board_x, board_y;
		if (withBorder) {
			for (int k = 0; k < piece.getLength(); k++) {
				// include in the check collision the area around the point
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						board_x = x + piece.x(k) + i;
						board_y = y + piece.y(k) + j;

						if ((board_x < 0) || (board_x >= board.getWidth())
								|| (board_y < 0)
								|| (board_y >= board.getHeight()))
							continue;

						if (board.getCell(board_x, board_y) != Cell.Empty)
							return true;
					}
				}
			}
		} else {
			for (int i = 0; i < piece.getLength(); i++) {
				board_x = x + piece.x(i);
				board_y = y + piece.y(i);

				if ((board_x < 0) || (board_x >= board.getWidth())
						|| (board_y < 0) || (board_y >= board.getHeight()))
					continue;

				if (board.getCell(board_x, board_y) != Cell.Empty)
					return true;
			}
		}
		return false;
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
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 */
	protected static boolean checkCollision(Board board, Shape piece, int x,
			int y) {
		return checkCollision(board, piece, x, y, false);
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
	protected static boolean checkTwoShapeCollision(CoordinatedShape first,
			CoordinatedShape second) {
		if (first == null || second == null)
			return false;

		// when the figures are placed too far apart, returns false
		if (Math.abs(first.y() + first.minY() - second.y() + second.minY()) > Math
				.max(first.getHeight(), second.getHeight())
				&& Math.abs(first.x() + first.minX() - second.x()
						+ second.minX()) > Math.max(first.getWidth(),
						second.getWidth()))
			return false;

		for (int i = 0; i < first.getLength(); i++) {
			int givenFirstX = first.x() + first.x(i);
			int givenFirstY = first.y() + first.y(i);

			for (int j = 0; j < second.getLength(); j++) {
				int givenSecondX = second.x() + second.x(j);
				int givenSecondY = second.y() + second.y(j);

				if (givenFirstX == givenSecondX && givenFirstY == givenSecondY)
					return true;
			}
		}
		return false;
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
	 * 
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected static boolean checkBoardCollisionVertical(Board board,
			Shape piece, int y, boolean checkTopBoundary) {
		if (checkTopBoundary && ((y + piece.maxY()) >= board.getHeight()))
			return true;
		if ((y + piece.minY()) < 0)
			return true;
		return false;
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
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollision
	 * @see #checkCollision
	 */
	protected static boolean checkBoardCollisionHorizontal(Board board,
			Shape piece, int x) {
		if ((x + piece.minX()) < 0 || (x + piece.maxX()) >= board.getWidth())
			return true;
		return false;
	}

	/**
	 * Collision check of the new figure with the
	 * {@link Game#checkBoardCollisionVertical vertical} and the
	 * {@link Game#checkBoardCollisionHorizontal horizontal} boundaries of the
	 * board
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
	 * 
	 * @see #checkBoardCollisionVertical
	 * @see #checkBoardCollisionHorisontal
	 * @see #checkCollision
	 */
	protected static boolean checkBoardCollision(Board board, Shape piece,
			int x, int y) {
		return checkBoardCollisionVertical(board, piece, y, true)
				|| checkBoardCollisionHorizontal(board, piece, x);
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
		if (board == null)
			return board;
		
		// If dX is greater than the width of the board, it is reduced
		int reducedDX = dX % board.getWidth();

		if (reducedDX == 0)
			return board;

		Board resultBoard = board.clone();

		for (int i = 0; i < Math.abs(reducedDX); i++) {
			// if shift to the right, then get the first column as temporary,
			// otherwise get the last column
			Cell[] tempColumn = (reducedDX > 0) ? board.getColumn(board
					.getWidth() - 1) : board.getColumn(0);

			for (int j = 0; j < board.getWidth(); j++) {
				Cell[] nextColumn = null;
				// replace the column on the side of the board to the
				// appropriate column on the other side
				if (((j == 0) && (reducedDX > 0))
						|| ((j == board.getWidth() - 1) && (reducedDX < 0))) {
					nextColumn = tempColumn;
				} else {
					// replace the column in the middle of the board to the
					// appropriate adjacent column
					nextColumn = board.getColumn(j
							+ ((reducedDX > 0) ? (-1) : 1));
				}
				resultBoard.setColumn(nextColumn, j);
			}
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
	 * @return {@code true} if the insertion is success, otherwise {@code false}
	 */
	protected static void insertCellsToBoard(Board board, Cell[][] cells,
			int x, int y) {
		if (board == null)
			return;

		if ((x >= board.getWidth()) || (y >= board.getHeight())
				|| (x + cells.length <= 0) || (y + cells[0].length <= 0)) {
			return;
		}

		// calculate the shift when the cells is not completely inserted into
		// the board
		int fromX = (x < 0) ? -x : 0;
		int toX = (x + cells.length >= board.getWidth()) ? board.getWidth() - x
				: cells.length;

		int fromY = (y < 0) ? -y : 0;
		int toY = (y + cells[0].length >= board.getHeight()) ? board
				.getHeight() - y : cells[0].length;

		for (int i = fromX; i < toX; i++) {
			for (int j = fromY; j < toY; j++) {
				board.setCell(cells[i][j], x + i, y + j);
			}
		}
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
	 * 
	 * @return the board with the figure
	 */
	protected static Board drawShape(Board board, int x, int y, Shape shape,
			Cell fill) {
		if (shape == null || board == null)
			return board;

		for (int i = 0; i < shape.getLength(); i++) {
			int board_x = x + shape.x(i);
			int board_y = y + shape.y(i);

			// if the figure does not leave off the board
			if (((board_y < board.getHeight()) && (board_y >= 0))
					&& ((board_x < board.getWidth()) && (board_x >= 0))) {
				// draws the point of the figure on the board
				drawPoint(board, board_x, board_y, fill);
			}
		}
		return board;
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
	 * 
	 * @return the board with the figure
	 */
	protected static Board drawShape(Board board, CoordinatedShape shape,
			Cell fill) {
		return drawShape(board, shape.x(), shape.y(), shape, fill);
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
	protected static Board drawPoint(Board board, int x, int y, Cell fill) {
		if (board == null)
			return board;

		int board_x = x;
		int board_y = y;

		// check if the point is outside the borders of the board
		if (board_x < 0)
			board_x = board.getWidth() + board_x;
		else if (board_x >= board.getWidth())
			board_x = board_x - board.getWidth();
		if (board_y < 0)
			board_y = board.getHeight() + board_y;
		else if (board_y >= board.getHeight())
			board_y = board_y - board.getHeight();

		board.setCell(fill, board_x, board_y);

		return board;
	}

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
	protected static Board addLinesToBoard(Board board, int fromLine,
			int linesCount, boolean isUpwardDirection) {
		if (board == null)
			return board;

		if ((linesCount < 1)//
				|| ((isUpwardDirection) && //
				((fromLine + (linesCount - 1)) > board.getHeight()))//
				//
				|| ((!isUpwardDirection) && //
				((fromLine - (linesCount - 1)) < 0)))
			return board;

		// checks whether there are full cells at a distance of
		// <i>linesCount</i> from the top or the bottom of the board
		for (int i = 0; i < board.getWidth(); i++) {
			if (//
			((isUpwardDirection) && //
					(board.getCell(i, ((board.getHeight() - 1) - linesCount)) == Cell.Full))//
					//
					|| ((!isUpwardDirection) && //
					(board.getCell(i, (linesCount - 1)) == Cell.Full))//
			)
				return board;
		}

		Random r = new Random();
		Cell newLines[][] = new Cell[linesCount][board.getWidth()];

		// picks up or downs the lines of the board
		if (isUpwardDirection) {
			for (int y = (board.getHeight() - 1) - 1; y > fromLine
					+ (linesCount - 1); y--) {
				board.setRow(board.getRow(y - 1), y);
			}
		} else {
			for (int y = 0; y < (fromLine - (linesCount - 1)); y++) {
				board.setRow(board.getRow(y + 1), y);
			}
		}

		// generates a new lines
		for (int line = 0; line < linesCount; line++) {
			boolean hasEmpty = false;
			boolean hasFull = false;

			for (int i = 0; i < board.getWidth(); i++) {
				if (r.nextBoolean()) {
					newLines[line][i] = Cell.Empty;
					hasEmpty = true;
				} else {
					newLines[line][i] = Cell.Full;
					hasFull = true;
				}
			}

			// if all the cells were empty, creates a full one in a random place
			// of the line
			if (!hasEmpty || !hasFull) {
				newLines[line][r.nextInt(board.getWidth())] = ((!hasEmpty) ? Cell.Empty
						: Cell.Full);
			}

			// adds the created line to the board
			board.setRow(newLines[line], (isUpwardDirection ? fromLine + line
					: fromLine - line));
		}
		return board;
	}

	/**
	 * Returns the inverted copy of the specified board
	 * 
	 * @param board
	 *            specified board
	 * @return the inverted copy of the board
	 */
	protected static Board getInvertedBoard(Board board) {
		if (board == null)
			return board;
		
		Board newBoard = board.clone();
		for (int i = 0; i < board.getHeight(); i++) {
			newBoard.setRow(board.getRow(i), board.getHeight() - i - 1);
		}
		return newBoard;
	}

	/**
	 * Play the {@code sound}, from the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param sound
	 *            {@code enum} value, containing the name of the sound
	 */
	protected static <E extends Enum<E>> void play(SoundBank soundBank,
			Enum<E> sound) {
		if (!Game.isMuted())
			SoundManager.play(soundBank, sound);
	}

	/**
	 * Play the {@code sound}, from the {@code Effects}.
	 * 
	 * @param sound
	 *            {@code Effects} value, containing the name of the sound
	 */
	protected static void playEffect(Effects sound) {
		if (!Game.isMuted() && !SoundManager.isPlaying(music))
			SoundManager.play(effects, sound);
	}

	/**
	 * Play the {@code sound}, from the {@code Music}.
	 * 
	 * @param sound
	 *            {@code Music} value, containing the name of the sound
	 */
	protected static void playMusic(Music sound) {
		if (!Game.isMuted()) {
			stopAllSounds();
			if (Music.start.equals(sound))
				SoundManager.playAndWait(music, sound);
			else
				SoundManager.play(music, sound);
		}
	}

	/**
	 * Play the {@code sound} in a circle, from the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param sound
	 *            {@code enum} value, containing the name of the sound
	 * @param echoDelay
	 *            delay before starting the second audio stream. 0 -without
	 *            echo.
	 */
	protected static <E extends Enum<E>> void loop(SoundBank soundBank,
			Enum<E> sound, int echoDelay) {
		if (!Game.isMuted()) {
			SoundManager.loop(soundBank, sound);
			// double loop - workaround for ending gap
			if (echoDelay > 0) {
				Game.sleep(echoDelay);
				SoundManager.loop(soundBank, sound);
			}
		}
	}

	/**
	 * Stops playing for all sounds
	 */
	protected static void stopAllSounds() {
		effects.stopAll();
		music.stopAll();
		melodies.stopAll();
	}

}
