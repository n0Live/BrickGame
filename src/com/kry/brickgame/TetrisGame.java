package com.kry.brickgame;

import com.kry.brickgame.Board.Cells;
import com.kry.brickgame.TetrisShape.Tetrominoes;

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

	public TetrisGame() {
		super();

		setStatus(Status.None);

		curPiece = new TetrisShape();
		nextPiece = new TetrisShape();

		clearPreview();
		clearBoard();
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();

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
		curX = BOARD_WIDTH / 2;
		// Y-coordinate - top edge, and so that the bottom edge of the figure
		// was at the border of #UNSHOWED_LINES
		curY = BOARD_HEIGHT - 1 - (UNSHOWED_LINES - nextPiece.maxY());

		if (!tryMove(nextPiece, curX, curY)) {
			gameOver();
		} else {
			nextPiece.setRandomShapeAndRotate();

			if (nextPiece.getShape() == Tetrominoes.SquareShape)
				nextPiece.setFill(Cells.Blink);

			clearPreview();
			setPreview(drawPiece(getPreview(),//
					// X-coordinate:
					// (middle of the board)-(half the width of the
					// figure)-(offset of the leftmost x-coordinate from zero)
					(PREVIEW_WIDTH / 2)
							- ((nextPiece.maxX() - nextPiece.minX() + 1) / 2)
							- (nextPiece.minX()),
					// Y-coordinate
					// (remember that the figure is drawn from the bottom up):
					// (middle of the board)+(half the height of the
					// figure)+(offset of the lower y-coordinate from zero)
					(PREVIEW_HEIGHT / 2 - 1)
							+ ((nextPiece.maxY() - nextPiece.minY() + 1) / 2)
							+ (nextPiece.minY()),//
					nextPiece, nextPiece.getFill()));
		}
	}

	/**
	 * Moving a figure
	 * 
	 * @param newPiece
	 *            a figure after the movement (eg. to rotate the figure)
	 * @param newX
	 *            x-coordinate position on the board of a figure
	 * @param newY
	 *            y-coordinate position on the board of a figure
	 * @return {@code true} if the movement succeeded otherwise {@code false}
	 */
	private boolean tryMove(TetrisShape newPiece, int newX, int newY) {
		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the current figure from the temporary board to not interfere
		// with the checks
		board = drawPiece(board, curX, curY, curPiece, Cells.Empty);

		// If a collision from the new figure with the side boundary of the
		// board then trying to move aside
		int prepX = newX;
		while (checkBoardCollisionHorizontal(newPiece, prepX)) {
			prepX = ((prepX + newPiece.minX()) < 0) ? prepX + 1 : prepX - 1;
		}
		// Checks
		if (checkBoardCollisionVertical(newPiece, newY))
			return false;
		if (checkCollision(board, newPiece, prepX, newY))
			return false;

		// Erase the current figure from the basic board and draw the new figure
		setBoard(drawPiece(getBoard(), curX, curY, curPiece, Cells.Empty));
		setBoard(drawPiece(getBoard(), prepX, newY, newPiece,
				newPiece.getFill()));

		// The current figure is replaced by the new
		curPiece = newPiece.clone();
		curX = prepX;
		curY = newY;

		return true;
	}

	/**
	 * Drawing of a figure on a board
	 * 
	 * @param board
	 *            a board for drawing
	 * @param x
	 *            x-coordinate position on a board of a figure
	 * @param y
	 *            y-coordinate position on a board of a figure
	 * @param piece
	 *            a figure
	 * @param fill
	 *            {@code Cells.Full} or {@code Cells.Blink} - to draw a figure,
	 *            {@code Cells.Empty} - to erase a figure
	 * 
	 * @return - the board with the figure
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
			--newY;
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
			setBoard(drawPiece(getBoard(), curX, curY, curPiece, Cells.Full));

		removeFullLines();

		if (!isFallingFinished)
			newPiece();

	}

	/**
	 * Removal of a filled lines
	 */
	private void removeFullLines() {
		Board board = getBoard();

		isFallingFinished = true;

		int numFullLines = 0;

		// going through on all lines
		for (int y = 0; y < BOARD_HEIGHT - 1; ++y) {
			boolean lineIsFull = true;
			// check for the existence an empty cell in the line
			for (int x = 0; x < BOARD_WIDTH; ++x) {
				if (board.getCell(x, y) == Cells.Empty) {
					lineIsFull = false;
					break;
				}
			}
			if (lineIsFull) {
				++numFullLines;

				// animated clearing of a full line
				animatedClearLine(y, curX);

				// drop the lines down on the filled line
				for (int k = y; k < BOARD_HEIGHT - 1; ++k) {
					for (int x = 0; x < BOARD_WIDTH; ++x)
						board.setCell(board.getCell(x, k + 1), x, k);
				}
				// return to one line back (because we removed the filled line)
				--y;
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
		}
		curPiece.setShape(Tetrominoes.NoShape);
		fireInfoChanged(String.valueOf(numLinesRemoved));
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

		while ((x1 >= 0) || (x2 < BOARD_WIDTH)) {
			if (x1 >= 0)
				board.setCell(Cells.Empty, x1--, line);
			if (x2 < BOARD_WIDTH)
				board.setCell(Cells.Empty, x2++, line);

			try {
				Thread.sleep(ANIMATION_DELAY);
				setBoard(board);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Pause / Resume
	 */
	private void pause() {
		if (getStatus() == Status.Running) {
			setStatus(Status.Paused);
		} else if (getStatus() == Status.Paused) {
			setStatus(Status.Running);
		}
	}

	/**
	 * Processing of key presses
	 */
	private void processKeys() {
		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyStart)) {
			if (getStatus() == Status.GameOver) {
				start();
			} else {
				pause();
			}
			keys.remove(KeyPressed.KeyStart);
			return;
		}

		if (getStatus() != Status.Running)
			return;
		if (!isFallingFinished) {
			if (keys.contains(KeyPressed.KeyLeft)) {
				tryMove(curPiece, curX - 1, curY);
				keys.remove(KeyPressed.KeyLeft);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				tryMove(curPiece, curX + 1, curY);
				keys.remove(KeyPressed.KeyRight);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				TetrisShape rotatedPiece = new TetrisShape(curPiece)
						.rotateRight();
				tryMove(rotatedPiece, curX, curY);
				keys.remove(KeyPressed.KeyRotate);
			}
			if (keys.contains(KeyPressed.KeyDown)) {
				oneLineDown();
				keys.remove(KeyPressed.KeyDown);
			}
			if (keys.contains(KeyPressed.KeyReset)) {
				dropDown();
				keys.remove(KeyPressed.KeyReset);
			}
		}
	}

}
