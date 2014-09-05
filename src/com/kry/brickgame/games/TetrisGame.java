package com.kry.brickgame.games;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.shapes.TetrisShape.Tetrominoes;
import com.kry.brickgame.splashes.Splash;
import com.kry.brickgame.splashes.TetrisSplash;

/**
 * @author noLive
 * 
 */
public class TetrisGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new TetrisSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 48;

	/**
	 * Flag to check the completion of falling of a figure
	 */
	private volatile boolean isFallingFinished;
	/**
	 * The current figure
	 */
	private TetrisShape curPiece;
	/**
	 * The "next" figure
	 */
	private TetrisShape nextPiece;
	/**
	 * X-coordinate position on the board of the current figure
	 */
	private int curX;
	/**
	 * Y-coordinate position on the board of the current figure
	 */
	private int curY;

	/**
	 * The Tetris
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game type of the game:
	 *            <ol>
	 *            <li>regular tetris;
	 *            <li>tetris with super point - a figure that can pass through
	 *            an obstacles;
	 *            <li>tetris with super gun - a figure that can shoot an
	 *            obstacles;
	 *            <li>tetris with super point and super gun;
	 *            <li>tetris with super mud gun - a figure that can shoot an
	 *            create filled cells;
	 *            <li>tetris with super point and super mud gun;
	 *            <li>tetris with super gun and super mud gun;
	 *            <li>tetris with super point, super gun and super mud gun;
	 *            <li>tetris with super bomb - a figure that explode all around
	 *            after dropping down;
	 *            <li>tetris with super point and super bomb;
	 *            <li>tetris with super gun and super bomb;
	 *            <li>tetris with super point, super gun and super bomb;
	 *            <li>tetris with super mud gun and super bomb;
	 *            <li>tetris with super point, super mud gun and super bomb;
	 *            <li>tetris with super gun, super mud gun and super bomb;
	 *            <li>tetris with super point, super gun, super mud gun and
	 *            super bomb;
	 *            <li>tetris with a liquid (crumbly) figures;
	 *            <li>tetris with a liquid figures and super point;
	 *            <li>tetris with a liquid figures and super gun;
	 *            <li>tetris with a liquid figures, super point and super gun;
	 *            <li>tetris with a liquid figures and super mud gun;
	 *            <li>tetris with a liquid figures, super point and super mud
	 *            gun;
	 *            <li>tetris with a liquid figures, super gun and super mud gun;
	 *            <li>tetris with a liquid figures, super point, super gun and
	 *            super mud gun;
	 *            <li>tetris with a liquid figures and super bomb;
	 *            <li>tetris with a liquid figures, super point and super bomb;
	 *            <li>tetris with a liquid figures, super gun and super bomb;
	 *            <li>tetris with a liquid figures, super point, super gun and
	 *            super bomb;
	 *            <li>tetris with a liquid figures, super mud gun and super
	 *            bomb;
	 *            <li>tetris with a liquid figures, super point, super mud gun
	 *            and super bomb;
	 *            <li>tetris with a liquid figures, super gun, super mud gun and
	 *            super bomb;
	 *            <li>tetris with a liquid figures, super point, super gun,
	 *            super mud gun and super bomb;
	 *            <li>tetris with a acid figures;
	 *            <li>tetris with a acid figures and super point;
	 *            <li>tetris with a acid figures and super gun;
	 *            <li>tetris with a acid figures, super point and super gun;
	 *            <li>tetris with a acid figures and super mud gun;
	 *            <li>tetris with a acid figures, super point and super mud gun;
	 *            <li>tetris with a acid figures, super gun and super mud gun;
	 *            <li>tetris with a acid figures, super point, super gun and
	 *            super mud gun;
	 *            <li>tetris with a acid figures and super bomb;
	 *            <li>tetris with a acid figures, super point and super bomb;
	 *            <li>tetris with a acid figures, super gun and super bomb;
	 *            <li>tetris with a acid figures, super point, super gun and
	 *            super bomb;
	 *            <li>tetris with a acid figures, super mud gun and super bomb;
	 *            <li>tetris with a acid figures, super point, super mud gun and
	 *            super bomb;
	 *            <li>tetris with a acid figures, super gun, super mud gun and
	 *            super bomb;
	 *            <li>tetris with a acid figures, super point, super gun, super
	 *            mud gun and super bomb.
	 */
	public TetrisGame(int speed, int level, int type) {
		super(speed, level, type);

		setStatus(Status.None);

		curPiece = new TetrisShape();
		nextPiece = new TetrisShape();
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();

		// getLevel() - 1 - because on the first level doesn't need to add line
		addLines(getLevel() - 1);

		setStatus(Status.Running);
		isFallingFinished = false;

		// Create the "next" figure
		nextPiece.setRandomShapeAndRotate();
		newPiece();

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			// dropping of a figure
			if ((getStatus() != Status.Paused) && (elapsedTime(getSpeed(true)))) {
				if (isFallingFinished) {
					isFallingFinished = false;
					newPiece();
				} else {
					oneLineDown();
				}
			}
			// processing of key presses
			processKeys();
		}
	}

	/**
	 * Creation of a new figure
	 */
	private void newPiece() {
		curPiece.setShape(Tetrominoes.NoShape);

		// X-coordinate - middle of the board
		curX = boardWidth / 2 - 1;
		// Y-coordinate - top edge, and so that the bottom edge of the figure
		// was at the top of the border
		curY = boardHeight - 1 - nextPiece.minY();

		if (!tryMove(nextPiece, curX, curY)) {
			gameOver();
		} else {
			Random r = new Random();

			// chance to drop a super figure is 1 to 8
			if (r.nextInt(8) == 0) {
				switch (getType()) {
				case 2:
					nextPiece.setRandomSuperShape(new int[] { 0 });
					break;
				case 3:
					nextPiece.setRandomSuperShape(new int[] { 1 });
					break;
				case 4:
					nextPiece.setRandomSuperShape(new int[] { 0, 1 });
					break;
				case 5:
					nextPiece.setRandomSuperShape(new int[] { 2 });
					break;
				case 6:
					nextPiece.setRandomSuperShape(new int[] { 0, 2 });
					break;
				case 7:
					nextPiece.setRandomSuperShape(new int[] { 1, 2 });
					break;
				case 8:
					nextPiece.setRandomSuperShape(new int[] { 0, 1, 2 });
					break;
				case 9:
					nextPiece.setRandomSuperShape(new int[] { 3 });
					break;
				case 10:
					nextPiece.setRandomSuperShape(new int[] { 0, 3 });
					break;
				case 11:
					nextPiece.setRandomSuperShape(new int[] { 1, 3 });
					break;
				case 12:
					nextPiece.setRandomSuperShape(new int[] { 0, 1, 3 });
					break;
				case 13:
					nextPiece.setRandomSuperShape(new int[] { 2, 3 });
					break;
				case 14:
					nextPiece.setRandomSuperShape(new int[] { 0, 2, 3 });
					break;
				case 15:
					nextPiece.setRandomSuperShape(new int[] { 1, 2, 3 });
					break;
				case 16:
					nextPiece.setRandomSuperShape(new int[] { 0, 1, 2, 3 });
					break;
				case 17:
				case 33:
					nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 18:
				case 34:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 19:
				case 35:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 1 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 20:
				case 36:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 1 });
					break;
				case 21:
				case 37:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 2 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 22:
				case 38:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 2 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 23:
				case 39:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 1, 2 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 24:
				case 40:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 1, 2 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 25:
				case 41:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 26:
				case 42:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 27:
				case 43:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 1, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 28:
				case 44:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 1, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 29:
				case 45:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 2, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 30:
				case 46:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 2, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 31:
				case 47:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 1, 2, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				case 32:
				case 48:
					if (r.nextBoolean())
						nextPiece.setRandomSuperShape(new int[] { 0, 1, 2, 3 });
					else
						nextPiece.setRandomShapeAndRotate().setFill(Cell.Blink);
					break;
				default:
					nextPiece.setRandomShapeAndRotate();
					break;
				}
			} else
				nextPiece.setRandomShapeAndRotate();

			clearPreview();

			int previewX, previewY;
			// not for super figures
			if (nextPiece.getShape().ordinal() <= 7) {
				// X-coordinate:
				// (middle of the board)-(half the width of the
				// figure)-(offset of the leftmost x-coordinate from zero)
				previewX = (previewWidth / 2)
						- ((nextPiece.maxX() - nextPiece.minX() + 1) / 2)
						- (nextPiece.minX());
				// Y-coordinate
				// (remember that the figure is drawn from the bottom up):
				// (middle of the board)+(half the height of the
				// figure)+(offset of the lower y-coordinate from zero)
				previewY = (previewHeight / 2)
						- ((nextPiece.maxY() - nextPiece.minY() + 1) / 2)
						- (nextPiece.minY());
			}
			// for super figures
			else {
				previewX = 0;
				previewY = 0;
			}

			setPreview(drawShape(getPreview(), previewX, previewY, nextPiece,
					nextPiece.getFill()));
		}
	}

	/**
	 * Tries to move the figure
	 * 
	 * @param newPiece
	 *            the figure after the movement (eg. to rotate the figure)
	 * @param newX
	 *            x-coordinate position on the board of the figure
	 * @param newY
	 *            y-coordinate position on the board of the figure
	 * @return {@code true} if the movement succeeded, otherwise {@code false}
	 */
	private boolean tryMove(TetrisShape newPiece, int newX, int newY) {
		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the current figure from the temporary board to not interfere
		// with the checks
		board = eraseShape(board, curX, curY, curPiece);

		// If a collision from the new figure with the side boundary of the
		// board then trying to move aside
		int prepX = newX;
		while (checkBoardCollisionHorizontal(newPiece, prepX)) {
			prepX = ((prepX + newPiece.minX()) < 0) ? prepX + 1 : prepX - 1;
		}
		// Checks
		if (checkBoardCollisionVertical(newPiece, newY, false))
			return false;

		// for super figures
		if (newPiece.getShape() == Tetrominoes.SuperPoint) {
			if (checkCollision(board, newPiece, prepX, newY)) {
				// checking whether filled line at the current (old) figure
				if (checkForFullLines(getBoard(), curPiece, curX, curY))
					return false;
				if (specialCheckCollision(board, newPiece, prepX, newY))
					return false;
			}
		} else { // for ordinal figures
			if (checkCollision(board, newPiece, prepX, newY))
				return false;
		}

		// Erase the current figure from the basic board and draw the new figure
		setBoard(drawShape(board, prepX, newY, newPiece, newPiece.getFill()));

		// The current figure is replaced by the new
		curPiece = newPiece.clone();
		curX = prepX;
		curY = newY;

		return true;
	}

	/**
	 * Collision check for the super figure. Allows the figure to pass through
	 * the filled area if there is emptiness below.
	 * 
	 * @param board
	 *            the board for collision check
	 * @param piece
	 *            the super figure
	 * @param x
	 *            x-coordinate of the figure
	 * @param y
	 *            y-coordinate of the figure
	 * @return {@code true} if there is a collision
	 * @see Game#checkCollision
	 */
	private boolean specialCheckCollision(Board board, TetrisShape piece,
			int x, int y) {
		try {
			boolean isNotCollision = false;
			int board_x;

			for (int i = 0; i < piece.getLength(); i++) {
				isNotCollision = false;
				board_x = x + piece.x(i);
				// checks whether there is at least one empty cell below
				for (int board_y = y + piece.y(i); board_y >= 0; board_y--) {
					if (board.getCell(board_x, board_y) == Cell.Empty) {
						isNotCollision = true;
						break;
					}
				}
				// exits if at least one line has a collision
				if (!isNotCollision)
					break;
			}
			return !isNotCollision;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checking whether filled line at the figure
	 * 
	 * @param board
	 *            the board for check
	 * @param piece
	 *            the figure for check
	 * @param x
	 *            x-coordinate of the figure
	 * @param y
	 *            y-coordinate of the figure
	 * @return {@code true} if at least one line is full
	 */
	private boolean checkForFullLines(Board board, TetrisShape piece, int x,
			int y) {

		for (int j = y + piece.minY(); j <= y + piece.maxY(); j++) {
			boolean hasFullLine = true;
			for (int i = 0; i < boardWidth; i++) {
				// true - only if all cells is full
				hasFullLine &= (board.getCell(i, j) != Cell.Empty);
			}
			// exits if at least one line is full
			if (hasFullLine)
				return true;
		}
		return false;
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
	 * @param piece
	 *            the figure
	 * @param fill
	 *            {@code Cells.Full} or {@code Cells.Blink} - to draw the
	 *            figure, {@code Cells.Empty} - to erase the figure
	 * 
	 * @return the board with the figure
	 */
	private Board drawShape(Board board, int x, int y, TetrisShape piece,
			Cell fill) {
		Cell[] boardFill = new Cell[piece.getLength()];

		for (int i = 0; i < piece.getLength(); i++) {
			int board_x = x + piece.x(i);
			int board_y = y + piece.y(i);

			// if the figure does not leave off the board
			if (((board_y < board.getHeight()) && (board_y >= 0))
					&& ((board_x < board.getWidth()) && (board_x >= 0))) {
				// gets the fill type from the board cell
				boardFill[i] = board.getCell(board_x, board_y);
				// draws the point of the figure on the board
				drawPoint(board, board_x, board_y, fill);
			} else {
				// if the figure leaves off the board, then sets fill type to
				// empty
				boardFill[i] = Cell.Empty;
			}
		}
		piece.setBoardFill(boardFill);

		return board;
	}

	/**
	 * Erasing the figure from the board. The board's cells returned to its
	 * original state.
	 * 
	 * @param board
	 *            the board for erasing
	 * @param x
	 *            x-coordinate position on the board of the figure
	 * @param y
	 *            y-coordinate position on the board of the figure
	 * @param piece
	 *            the figure
	 * 
	 * @return the board without the figure
	 */
	private Board eraseShape(Board board, int x, int y, TetrisShape piece) {
		if (piece.getShape() == Tetrominoes.NoShape)
			return board;

		for (int i = 0; i < piece.getLength(); i++) {
			int board_x = x + piece.x(i);
			int board_y = y + piece.y(i);

			// if the figure does not leave off the board
			if (((board_y < board.getHeight()) && (board_y >= 0))
					&& ((board_x < board.getWidth()) && (board_x >= 0))) {
				// draws the original point on the board
				drawPoint(board, board_x, board_y, piece.getBoardFill()[i]);
			}
		}
		return board;
	}

	/**
	 * Dropping on one line down
	 */
	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	/**
	 * Rapidly drops of the figure to the bottom of the board
	 */
	private void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			newY--;
		}
		pieceDropped();
	}

	/**
	 * Ending of falling of the figure
	 */
	private void pieceDropped() {
		// if the guns
		if ((curPiece.getShape() == Tetrominoes.SuperGun)
				|| (curPiece.getShape() == Tetrominoes.SuperMudGun)) {
			// erase it
			setBoard(eraseShape(getBoard(), curX, curY, curPiece));
			// if the bomb
		} else if (curPiece.getShape() == Tetrominoes.SuperBomb) {
			// explode it
			kaboom(curX + 1, curY + 1);
			// if the liquid (crumbly) figure
		} else if ((getType() >= 17) && (getType() <= 32)
				&& (curPiece.getFill() == Cell.Blink)) {
			flowDown(getBoard(), curX, curY, curPiece, false);
			// if the acid figure
		} else if ((getType() >= 32) && (getType() <= 48)
				&& (curPiece.getFill() == Cell.Blink)) {
			flowDown(getBoard(), curX, curY, curPiece, true);
		} else {
			// add the figure to the board
			// Cells.Full instead curPiece.getFill() because is Blink should
			// change to Full when lying on the board
			setBoard(drawShape(getBoard(), curX, curY, curPiece, Cell.Full));
		}

		// curPiece.setShape(Tetrominoes.NoShape);

		isFallingFinished = true;

		removeFullLines();

		// check for game over
		if ((curY + curPiece.maxY()) >= boardHeight)
			gameOver();

	}

	/**
	 * Removal of a filled lines
	 */
	private int removeFullLines() {
		Board board = getBoard();

		int numFullLines = 0;

		// going through on all lines
		for (int y = 0; y < boardHeight - 1; y++) {
			boolean lineIsFull = true;
			// check for the existence an empty cell in the line
			for (int x = 0; x < boardWidth; x++) {
				if (board.getCell(x, y) == Cell.Empty) {
					lineIsFull = false;
					break;
				}
			}
			if (lineIsFull) {
				numFullLines++;

				// animated clearing of a full line
				animatedClearLine(y, curX);

				// if mud gun, than erase it before dropping downs lines
				if (curPiece.getShape() == Tetrominoes.SuperMudGun)
					eraseShape(board, curX, curY, curPiece);

				// drop the lines down on the filled line
				for (int k = y; k < boardHeight - 1; k++) {
					for (int x = 0; x < boardWidth; x++)
						board.setCell(board.getCell(x, k + 1), x, k);
				}

				// restore it after dropping downs lines
				if (curPiece.getShape() == Tetrominoes.SuperMudGun)
					drawShape(board, curX, curY, curPiece, curPiece.getFill());

				// return to one line back (because we removed the filled line)
				y--;
			}
		}

		if (numFullLines > 0) {
			// increasing score
			switch (numFullLines) {
			case 1:
				setScore(getScore() + 1);
				break;
			case 2:
				setScore(getScore() + 3);
				break;
			case 3:
				setScore(getScore() + 7);
				break;
			case 4:
				setScore(getScore() + 15);
				break;
			default:
				break;
			}
		}
		return numFullLines;
	}

	/**
	 * Animated clearing of a full line
	 * 
	 * @param line
	 *            number of the line to be removed (y-coordinate)
	 * @param startPoint
	 *            point, on both sides of which cells will be removed
	 *            (x-coordinate)
	 */
	private void animatedClearLine(int line, int startPoint) {
		final Board board = getBoard();

		int x1 = startPoint - 1; // left direction
		int x2 = startPoint; // right direction

		while ((x1 >= 0) || (x2 < boardWidth)) {
			if (x1 >= 0)
				board.setCell(Cell.Empty, x1--, line);
			if (x2 < boardWidth)
				board.setCell(Cell.Empty, x2++, line);

			sleep(ANIMATION_DELAY);
			setBoard(board);
		}
	}

	/**
	 * Adds randomly generated lines at the bottom of the board
	 * 
	 * @param linesCount
	 *            count of added lines
	 * @return if {@code linesCount < 0} or when adding the lines, the board
	 *         reaches the upper border, then return {@code false}, otherwise
	 *         the lines adds and returns {@code true}
	 */
	private boolean addLines(int linesCount) {
		if (linesCount < 1)
			return false;

		Board board = getBoard();

		// checks whether there are full cells at a distance of
		// <i>linesCount</i> from the top of the board
		for (int i = 0; i < boardWidth; i++) {
			if (board.getCell(i, (boardHeight - linesCount)) == Cell.Full)
				return false;
		}

		Random r = new Random();
		Cell newLines[][] = new Cell[linesCount][boardWidth];

		// picks up the lines of the board
		for (int y = boardHeight - 1; y >= linesCount; y--) {
			for (int x = 0; x < boardWidth; x++)
				board.setCell(board.getCell(x, y - 1), x, y);
		}

		// generates a new lines
		for (int line = 0; line < linesCount; line++) {
			boolean hasEmpty = false;
			boolean hasFull = false;

			for (int i = 0; i < boardWidth; i++) {
				if ((Math.abs(r.nextInt()) % 2) == 0) {
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
				newLines[line][Math.abs(r.nextInt(boardWidth))] = ((!hasEmpty) ? Cell.Empty
						: Cell.Full);
			}

			// adds the created line to the board
			board.setLine(newLines[line], line);
		}

		setBoard(board);
		return true;
	}

	/**
	 * Adds one randomly generated line at the bottom of the board
	 * 
	 * @return if when adding the line, the board reaches the upper border, then
	 *         return {@code false}, otherwise the line adds and returns
	 *         {@code true}
	 */
	private boolean addLines() {
		return addLines(1);
	}

	/**
	 * Destroying (setting {@code Cell.Empty}) a single cell, located under the
	 * cell with the specified coordinates {@code [x, y]}
	 * 
	 * @param x
	 *            x-coordinate of the cell from where shot will be made
	 * @param y
	 *            y-coordinate of the cell from where shot will be made
	 */
	private void shoot(int x, int y) {
		if ((y <= 0) || (y > boardHeight))
			return;

		Board board = getBoard();
		for (int i = y - 1; i >= 0; i--) {
			if (board.getCell(x, i) != Cell.Empty) {
				board.setCell(Cell.Empty, x, i);
				break;
			}
		}
	}

	/**
	 * Filling (setting {@code Cell.Full}) a single cell, located under the cell
	 * with the specified coordinates {@code [x, y]}
	 * 
	 * @param x
	 *            x-coordinate of the cell from where shot will be made
	 * @param y
	 *            y-coordinate of the cell from where shot will be made
	 */
	private void mudShoot(int x, int y) {
		if ((y <= 0) || (y > boardHeight))
			return;

		Board board = getBoard();
		for (int i = y - 1; i >= 0; i--) {
			if (board.getCell(x, i) != Cell.Empty) {
				if (board.getCell(x, i + 1) == Cell.Empty) {
					board.setCell(Cell.Full, x, i + 1);
					break;
				}
			}
			// if there were no obstacles then create a full cell at the border
			// of the board
			else if (i == 0) {
				board.setCell(Cell.Full, x, 0);
			}
		}
		// check for full lines
		removeFullLines();
	}

	private void flowDown(Board board, int x, int y, TetrisShape piece,
			boolean isAcid) {

		int[][] sortedPoints = new int[piece.getLength()][2];
		int n = 0;
		for (int j = piece.minY(); j <= piece.maxY(); j++) {
			for (int i = piece.minX(); i <= piece.maxX(); i++) {
				for (int k = 0; k < piece.getLength(); k++) {
					if ((piece.x(k) == i) && (piece.y(k) == j)) {
						sortedPoints[n++] = piece.getCoord(k);
						break;
					}
				}
			}
		}

		for (int i = 0; i < sortedPoints.length; i++) {
			int board_x = x + sortedPoints[i][0];
			int board_y = y + sortedPoints[i][1];

			while ((board_y > 0)
					&& (board.getCell(board_x, board_y - 1) == Cell.Empty)) {
				drawPoint(board, board_x, board_y - 1, piece.getFill());
				drawPoint(board, board_x, board_y, Cell.Empty);
				board_y--;
				setBoard(board);
				sleep(ANIMATION_DELAY);
			}
			if (isAcid && (board_y > 0)) {
				drawPoint(board, board_x, board_y - 1, piece.getFill());
				drawPoint(board, board_x, board_y, Cell.Empty);
				sleep(ANIMATION_DELAY * 2);
				drawPoint(board, board_x, board_y - 1, Cell.Empty);
			} else
				drawPoint(board, board_x, board_y, Cell.Full);
		}
	}

	@Override
	protected void setScore(int score) {
		int oldHundreds = getScore() / 100;

		super.setScore(score);

		// when a sufficient number of points changes the speed and the level
		if (getScore() / 100 > oldHundreds) {
			setSpeed(getSpeed() + 1);
			if (getSpeed() == 1) {
				setLevel(getLevel() + 1);
				for (int i = 0; i < getLevel() - 1; i++) {
					sleep(ANIMATION_DELAY * 4);
					addLines();
				}
			}
		}
	}

	/**
	 * Processing of key presses
	 */
	private void processKeys() {
		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyReset)) {
			keys.remove(KeyPressed.KeyReset);
			ExitToMainMenu();
			return;
		}

		if (keys.contains(KeyPressed.KeyStart)) {
			keys.remove(KeyPressed.KeyStart);
			pause();
			return;
		}

		if (getStatus() != Status.Running)
			return;
		if (!isFallingFinished) {
			if (keys.contains(KeyPressed.KeyLeft)) {
				tryMove(curPiece, curX - 1, curY);
				sleep(ANIMATION_DELAY * 3);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				tryMove(curPiece, curX + 1, curY);
				sleep(ANIMATION_DELAY * 3);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				// if we have the super gun
				if (curPiece.getShape() == Tetrominoes.SuperGun) {
					// than shoot of it
					shoot(curX, curY + curPiece.minY());
				} else if (curPiece.getShape() == Tetrominoes.SuperMudGun) {
					mudShoot(curX, curY + curPiece.minY());
				} else {
					TetrisShape rotatedPiece = curPiece.clone().rotateRight();
					tryMove(rotatedPiece, curX, curY);
				}
				keys.remove(KeyPressed.KeyRotate);
			}
			if (keys.contains(KeyPressed.KeyDown)) {
				oneLineDown();
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyUp)) {
				dropDown();
				keys.remove(KeyPressed.KeyUp);
			}
		}
	}

}
