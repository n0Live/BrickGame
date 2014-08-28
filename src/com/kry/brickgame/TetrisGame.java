package com.kry.brickgame;

import java.util.Random;

import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.TetrisShape.Tetrominoes;

/**
 * @author noLive
 * 
 */
public class TetrisGame extends Game {

	/**
	 * Flag to check the completion of falling of a figure
	 */
	private volatile boolean isFallingFinished = false;
	/**
	 * Counter of the number of removed lines
	 */
	private int numLinesRemoved = 0;
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
	private int curX = 0;
	/**
	 * Y-coordinate position on the board of the current figure
	 */
	private int curY = 0;

	public TetrisGame(int speed, int level) {
		super(speed, level);

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
		numLinesRemoved = 0;

		// Create the "next" figure
		nextPiece.setRandomShapeAndRotate();
		newPiece();

		while (getStatus() != Status.GameOver) {
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
		curX = boardWidth / 2;
		// Y-coordinate - top edge, and so that the bottom edge of the figure
		// was at the top of the border
		curY = boardHeight - 1 + nextPiece.maxY();

		if (!tryMove(nextPiece, curX, curY)) {
			gameOver();
		} else {
			nextPiece.setRandomShapeAndRotate();

			// TODO delete after test
			if (nextPiece.getShape() == Tetrominoes.SquareShape)
				nextPiece.setFill(Cell.Blink);

			clearPreview();
			setPreview(drawShape(getPreview(),//
					// X-coordinate:
					// (middle of the board)-(half the width of the
					// figure)-(offset of the leftmost x-coordinate from zero)
					(previewWidth / 2)
							- ((nextPiece.maxX() - nextPiece.minX() + 1) / 2)
							- (nextPiece.minX()),
					// Y-coordinate
					// (remember that the figure is drawn from the bottom up):
					// (middle of the board)+(half the height of the
					// figure)+(offset of the lower y-coordinate from zero)
					(previewHeight / 2 - 1)
							+ ((nextPiece.maxY() - nextPiece.minY() + 1) / 2)
							+ (nextPiece.minY()),//
					nextPiece, nextPiece.getFill()));
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
		board = drawShape(board, curX, curY, curPiece, Cell.Empty);

		// If a collision from the new figure with the side boundary of the
		// board then trying to move aside
		int prepX = newX;
		while (checkBoardCollisionHorizontal(newPiece, prepX)) {
			prepX = ((prepX + newPiece.minX()) < 0) ? prepX + 1 : prepX - 1;
		}
		// Checks
		if (checkBoardCollisionVertical(newPiece, newY, false))
			return false;
		if (checkCollision(board, newPiece, prepX, newY))
			return false;

		// Erase the current figure from the basic board and draw the new figure
		setBoard(drawShape(getBoard(), curX, curY, curPiece, Cell.Empty));
		setBoard(drawShape(getBoard(), prepX, newY, newPiece,
				newPiece.getFill()));

		// The current figure is replaced by the new
		curPiece = newPiece.clone();
		curX = prepX;
		curY = newY;

		return true;
	}

	/**
	 * Dropping on one line down
	 */
	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	/**
	 * Rapidly drops of the figure to the bottom of the board or a obstructions
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
		// add the figure to the board
		if (curPiece.getShape() != Tetrominoes.NoShape)
			// Cells.Full instead curPiece.getFill() because is Blink should
			// change to Full when lying on the board
			setBoard(drawShape(getBoard(), curX, curY, curPiece, Cell.Full));

		int oldHundreds = getScore() / 100;

		removeFullLines();

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

		// check for game over
		if ((curY + curPiece.maxY()) > boardHeight)
			gameOver();

	}

	/**
	 * Removal of a filled lines
	 */
	private void removeFullLines() {
		Board board = getBoard();

		isFallingFinished = true;
		curPiece.setShape(Tetrominoes.NoShape);

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

				// drop the lines down on the filled line
				for (int k = y; k < boardHeight - 1; k++) {
					for (int x = 0; x < boardWidth; x++)
						board.setCell(board.getCell(x, k + 1), x, k);
				}
				// return to one line back (because we removed the filled line)
				y--;
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;

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
	 * Processing of key presses
	 */
	private void processKeys() {
		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyStart)) {
			pause();
			keys.remove(KeyPressed.KeyStart);
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
				TetrisShape rotatedPiece = curPiece.clone().rotateRight();
				tryMove(rotatedPiece, curX, curY);
				keys.remove(KeyPressed.KeyRotate);
			}
			if (keys.contains(KeyPressed.KeyDown)) {
				oneLineDown();
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyReset)) {
				dropDown();
				keys.remove(KeyPressed.KeyReset);
			}
		}
	}

}
