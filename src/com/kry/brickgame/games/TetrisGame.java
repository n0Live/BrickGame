package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.addLinesToBoard;
import static com.kry.brickgame.games.GameUtils.checkBoardCollisionHorizontal;
import static com.kry.brickgame.games.GameUtils.checkBoardCollisionVertical;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawPoint;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.shapes.TetrisShape.Figures;
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
	public static final int subtypesNumber = 90;

	/**
	 * Flag to check the completion of falling of a figure
	 */
	protected boolean isFallingFinished;
	/**
	 * The current figure
	 */
	protected TetrisShape curPiece;
	/**
	 * The "next" figure
	 */
	protected TetrisShape nextPiece;
	/**
	 * Has the liquid (crumbly) figures
	 */
	private boolean hasLiquidFigures;
	/**
	 * Has the acid figures
	 */
	private boolean hasAcidFigures;
	/**
	 * Has the figures that can pass through an obstacles
	 */
	private boolean hasThroughfallFigures;
	/**
	 * Has the random figures: liquid, acid or throughfall
	 */
	private boolean hasRandomFigures;
	/**
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;

	/**
	 * The Tetris
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param rotation
	 *            direction of rotation
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
	 *            <li>tetris with super gun and super mud gun;
	 *            <li>tetris with super point, super gun and super mud gun;
	 *            <li>tetris with super bomb - a figure that explode all around
	 *            after dropping down;
	 *            <li>tetris with super point, super gun, super mud gun and
	 *            super bomb;
	 *            <li>tetris with a liquid (crumbly) figures;
	 *            <li>tetris with a liquid figures and super point;
	 *            <li>tetris with a liquid figures and super gun;
	 *            <li>tetris with a liquid figures, super point and super gun;
	 *            <li>tetris with a liquid figures and super mud gun;
	 *            <li>tetris with a liquid figures, super gun and super mud gun;
	 *            <li>tetris with a liquid figures, super point, super gun and
	 *            super mud gun;
	 *            <li>tetris with a liquid figures and super bomb;
	 *            <li>tetris with a liquid figures, super point, super gun,
	 *            super mud gun and super bomb;
	 *            <li>tetris with an acid figures;
	 *            <li>tetris with an acid figures and super point;
	 *            <li>tetris with an acid figures and super gun;
	 *            <li>tetris with an acid figures, super point and super gun;
	 *            <li>tetris with an acid figures and super mud gun;
	 *            <li>tetris with an acid figures, super gun and super mud gun;
	 *            <li>tetris with an acid figures, super point, super gun and
	 *            super mud gun;
	 *            <li>tetris with an acid figures and super bomb;
	 *            <li>tetris with an acid figures, super point, super gun, super
	 *            mud gun and super bomb;
	 *            <li>tetris with a figures that can pass through an obstacles;.
	 *            <li>tetris with a throughfall figures and super point;
	 *            <li>tetris with a throughfall figures and super gun;
	 *            <li>tetris with a throughfall figures, super point and super
	 *            gun;
	 *            <li>tetris with a throughfall figures and super mud gun;
	 *            <li>tetris with a throughfall figures, super gun and super mud
	 *            gun;
	 *            <li>tetris with a throughfall figures, super point, super gun
	 *            and super mud gun;
	 *            <li>tetris with a throughfall figures and super bomb;
	 *            <li>tetris with a throughfall figures, super point, super gun,
	 *            super mud gun and super bomb;
	 *            <li>tetris with randomly: liquid figures, acid figures or
	 *            throughfall figures;
	 *            <li>tetris with super point and random super figures;
	 *            <li>tetris with super gun and random super figures;
	 *            <li>tetris with super point, super gun and random super
	 *            figures;
	 *            <li>tetris with super mud gun and random super figures;
	 *            <li>tetris with super gun, super mud gun and random super
	 *            figures;
	 *            <li>tetris with super point, super gun, super mud gun and
	 *            random super figures;
	 *            <li>tetris with super bomb and random super figures;
	 *            <li>tetris with a super point, super gun, super mud gun, super
	 *            bomb and random super figures;
	 *            <li>regular tetris, the board is upside down;
	 *            <li>tetris with super point - a figure that can pass through
	 *            an obstacles, the board is upside down;
	 *            <li>tetris with super gun - a figure that can shoot an
	 *            obstacles, the board is upside down;
	 *            <li>tetris with super point and super gun, the board is upside
	 *            down;
	 *            <li>tetris with super mud gun - a figure that can shoot an
	 *            create filled cells, the board is upside down;
	 *            <li>tetris with super gun and super mud gun, the board is
	 *            upside down;
	 *            <li>tetris with super point, super gun and super mud gun, the
	 *            board is upside down;
	 *            <li>tetris with super bomb - a figure that explode all around
	 *            after dropping down, the board is upside down;
	 *            <li>tetris with super point, super gun, super mud gun and
	 *            super bomb, the board is upside down;
	 *            <li>tetris with a liquid (crumbly) figures, the board is
	 *            upside down;
	 *            <li>tetris with a liquid figures and super point, the board is
	 *            upside down;
	 *            <li>tetris with a liquid figures and super gun, the board is
	 *            upside down;
	 *            <li>tetris with a liquid figures, super point and super gun,
	 *            the board is upside down;
	 *            <li>tetris with a liquid figures and super mud gun, the board
	 *            is upside down;
	 *            <li>tetris with a liquid figures, super gun and super mud gun,
	 *            the board is upside down;
	 *            <li>tetris with a liquid figures, super point, super gun and
	 *            super mud gun, the board is upside down;
	 *            <li>tetris with a liquid figures and super bomb, the board is
	 *            upside down;
	 *            <li>tetris with a liquid figures, super point, super gun,
	 *            super mud gun and super bomb, the board is upside down;
	 *            <li>tetris with an acid figures, the board is upside down;
	 *            <li>tetris with an acid figures and super point, the board is
	 *            upside down;
	 *            <li>tetris with an acid figures and super gun, the board is
	 *            upside down;
	 *            <li>tetris with an acid figures, super point and super gun,
	 *            the board is upside down;
	 *            <li>tetris with an acid figures and super mud gun, the board
	 *            is upside down;
	 *            <li>tetris with an acid figures, super gun and super mud gun,
	 *            the board is upside down;
	 *            <li>tetris with an acid figures, super point, super gun and
	 *            super mud gun, the board is upside down;
	 *            <li>tetris with an acid figures and super bomb, the board is
	 *            upside down;
	 *            <li>tetris with an acid figures, super point, super gun, super
	 *            mud gun and super bomb, the board is upside down;
	 *            <li>tetris with a figures that can pass through an obstacles;,
	 *            the board is upside down;
	 *            <li>tetris with a throughfall figures and super point, the
	 *            board is upside down;
	 *            <li>tetris with a throughfall figures and super gun, the board
	 *            is upside down;
	 *            <li>tetris with a throughfall figures, super point and super
	 *            gun, the board is upside down;
	 *            <li>tetris with a throughfall figures and super mud gun, the
	 *            board is upside down;
	 *            <li>tetris with a throughfall figures, super gun and super mud
	 *            gun, the board is upside down;
	 *            <li>tetris with a throughfall figures, super point, super gun
	 *            and super mud gun, the board is upside down;
	 *            <li>tetris with a throughfall figures and super bomb, the
	 *            board is upside down;
	 *            <li>tetris with a throughfall figures, super point, super gun,
	 *            super mud gun and super bomb, the board is upside down;
	 *            <li>tetris with randomly: liquid figures, acid figures or
	 *            throughfall figures, the board is upside down;
	 *            <li>tetris with super point and random super figures, the
	 *            board is upside down;
	 *            <li>tetris with super gun and random super figures, the board
	 *            is upside down;
	 *            <li>tetris with super point, super gun and random super
	 *            figures, the board is upside down;
	 *            <li>tetris with super mud gun and random super figures, the
	 *            board is upside down;
	 *            <li>tetris with super gun, super mud gun and random super
	 *            figures, the board is upside down;
	 *            <li>tetris with super point, super gun, super mud gun and
	 *            random super figures, the board is upside down;
	 *            <li>tetris with super bomb and random super figures, the board
	 *            is upside down;
	 *            <li>tetris with a super point, super gun, super mud gun, super
	 *            bomb and random super figures, the board is upside down.
	 */
	public TetrisGame(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);

		// for types 10-18 and 55-63
		hasLiquidFigures = (((getType() >= 10) && (getType() <= 18)) || ((getType() >= 55) && (getType() <= 63)));
		// for types 19-27 and 64-72
		hasAcidFigures = (((getType() >= 19) && (getType() <= 27)) || ((getType() >= 64) && (getType() <= 72)));
		// for types 28-36 and 73-81
		hasThroughfallFigures = (((getType() >= 28) && (getType() <= 36)) || ((getType() >= 73) && (getType() <= 81)));
		// for types 37-45 and 82-90
		hasRandomFigures = (((getType() >= 37) && (getType() <= 45)) || ((getType() >= 82) && (getType() <= 90)));
		// for types 46-90
		drawInvertedBoard = (getType() > 45);
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		// getLevel() - 1 - because on the first level doesn't need to add line
		setBoard(addLinesToBoard(getBoard(), 0, getLevel() - 1, true));

		setStatus(Status.Running);
		isFallingFinished = false;

		// Create the "next" figure
		nextPiece = TetrisShape.getRandomTetraminoes().setRandomRotate();
		newPiece();

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			doRepetitiveWork();
		}
	}

	/**
	 * Do the work that needs to be repeated until the end of the game
	 */
	protected void doRepetitiveWork() {
		if ((getStatus() == Status.Running) && (elapsedTime(getSpeed(true)))) {
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

	/**
	 * Creation of a new figure
	 */
	private void newPiece() {
		curPiece = new TetrisShape(Figures.NoShape);

		// X-coordinate - middle of the board
		curX = boardWidth / 2 - 1;
		// Y-coordinate - top edge, and so that the bottom edge of the figure
		// was at the top of the border
		curY = boardHeight - 1 - nextPiece.minY();

		// select random type of figures
		if (hasRandomFigures) {
			if (nextPiece.getFill() == Cell.Blink) {
				int typeOfSuperShape = new Random().nextInt(3);

				hasLiquidFigures = (typeOfSuperShape == 0) ? true : false;
				hasAcidFigures = (typeOfSuperShape == 1) ? true : false;
				hasThroughfallFigures = (typeOfSuperShape == 2) ? true : false;
			} else {
				hasLiquidFigures = false;
				hasAcidFigures = false;
				hasThroughfallFigures = false;
			}
		}

		if (!tryMove(nextPiece, curX, curY)) {
			gameOver();
		} else {
			nextPiece = setPieceFromType(getType());

			clearPreview();

			int previewX, previewY;
			// not for super figures
			if (nextPiece.getShape().ordinal() < Figures.REF_TO_FIRST_SUPER_SHAPE) {
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
	 * Sets a new shape, depending on the type of game
	 * 
	 * @param type
	 *            the type of game
	 * @return the new shape
	 */
	protected TetrisShape setPieceFromType(int type) {
		TetrisShape newPiece = null;
		Random r = new Random();

		if ((hasLiquidFigures || hasAcidFigures || hasThroughfallFigures || hasRandomFigures)
				&& (r.nextInt(7) == 0)) {
			// for super figures
			newPiece = TetrisShape.getRandomTetraminoes().setRandomRotate();
			newPiece.setFill(Cell.Blink);
		} else {
			// for every 1 from 9 level
			if (getType() % 9 == 1)
				newPiece = TetrisShape.getRandomTetraminoes().setRandomRotate();
			// for every 2 from 9 level
			else if (getType() % 9 == 2)
				newPiece = TetrisShape
						.getRandomTetraminoesAndSuper(new int[] { 0 });
			// for every 3 from 9 level
			else if (getType() % 9 == 3)
				newPiece = TetrisShape
						.getRandomTetraminoesAndSuper(new int[] { 1 });
			// for every 4 from 9 level
			else if (getType() % 9 == 4)
				newPiece = TetrisShape.getRandomTetraminoesAndSuper(new int[] {
						0, 1 });
			// for every 5 from 9 level
			else if (getType() % 9 == 5)
				newPiece = TetrisShape
						.getRandomTetraminoesAndSuper(new int[] { 2 });
			// for every 6 from 9 level
			else if (getType() % 9 == 6)
				newPiece = TetrisShape.getRandomTetraminoesAndSuper(new int[] {
						1, 2 });
			// for every 7 from 9 level
			else if (getType() % 9 == 7)
				newPiece = TetrisShape.getRandomTetraminoesAndSuper(new int[] {
						0, 1, 2 });
			// for every 8 from 9 level
			else if (getType() % 9 == 8)
				newPiece = TetrisShape
						.getRandomTetraminoesAndSuper(new int[] { 3 });
			// for every 9 from 9 level
			else if (getType() % 9 == 0)
				newPiece = TetrisShape.getRandomTetraminoesAndSuper(new int[] {
						0, 1, 2, 3 });
		}

		return newPiece;
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
	protected boolean tryMove(TetrisShape newPiece, int newX, int newY) {
		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the current figure from the temporary board to not interfere
		// with the checks
		board = eraseShape(board, curX, curY, curPiece);

		// If a collision from the new figure with the side boundary of the
		// board then trying to move aside
		int prepX = newX;
		while (checkBoardCollisionHorizontal(board, newPiece, prepX)) {
			prepX = ((prepX + newPiece.minX()) < 0) ? prepX + 1 : prepX - 1;
		}
		// Checks
		if (checkBoardCollisionVertical(board, newPiece, newY, false))
			return false;

		if (// for super point
		(newPiece.getShape() == Figures.SuperPoint)//
				|| ((newPiece.getShape().ordinal() < Figures.REF_TO_FIRST_SUPER_SHAPE) && //
				// all blink shapes (except super) in type 49 and 50 (some)
				(hasThroughfallFigures && (newPiece.getFill() == Cell.Blink)))//
		) {
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
	private static boolean specialCheckCollision(Board board,
			TetrisShape piece, int x, int y) {
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
			// check leaving from the board
			if (j >= boardHeight)
				break;

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
	protected static Board drawShape(Board board, int x, int y,
			TetrisShape piece, Cell fill) {
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
	protected static Board eraseShape(Board board, int x, int y,
			TetrisShape piece) {
		if (piece.getShape() == Figures.NoShape)
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
	protected void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	/**
	 * Rapidly drops of the figure to the bottom of the board
	 */
	protected void dropDown() {
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
	protected void pieceDropped() {
		if (curPiece.getShape() == Figures.SuperPoint) {// super point
			setBoard(drawShape(getBoard(), curX, curY, curPiece, Cell.Full));
		} else if ((curPiece.getShape() == Figures.SuperGun)
				|| (curPiece.getShape() == Figures.SuperMudGun)) { // guns
			setBoard(eraseShape(getBoard(), curX, curY, curPiece));
			curPiece = new TetrisShape(Figures.NoShape);
		} else if (curPiece.getShape() == Figures.SuperBomb) {// bomb
			kaboom(curX + 1, curY); // shift the epicenter to the bottom edge
		} else if (hasLiquidFigures // liquid figure
				&& (curPiece.getFill() == Cell.Blink)) {
			flowDown(getBoard(), curX, curY, curPiece, false);
		} else if (hasAcidFigures // acid figure
				&& (curPiece.getFill() == Cell.Blink)) {
			flowDown(getBoard(), curX, curY, curPiece, true);
		} else { // ordinal figure
			setBoard(drawShape(getBoard(), curX, curY, curPiece, Cell.Full));
		}

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
				animatedClearLine(getBoard(), curX, y);

				// if mud gun, than erase it before dropping downs lines
				if (curPiece.getShape() == Figures.SuperMudGun)
					eraseShape(board, curX, curY, curPiece);

				// drop the lines down on the filled line
				for (int k = y; k < boardHeight - 1; k++) {
					for (int x = 0; x < boardWidth; x++)
						board.setCell(board.getCell(x, k + 1), x, k);
				}

				// restore it after dropping downs lines
				if (curPiece.getShape() == Figures.SuperMudGun)
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
	 * Adds one randomly generated line at the bottom of the board
	 */
	protected boolean addLines() {
		Board board = getBoard().clone();

		board = addLinesToBoard(board, 0, 1, true);

		if (!board.equals(getBoard())) {
			setBoard(board);
			return true;
		} else
			return false;
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
	protected void shoot(int x, int y) {
		if ((y <= 0) || (y > boardHeight))
			return;

		Board board = getBoard();

		for (int i = y - 1; i >= 0; i--) {
			if (board.getCell(x, i) != Cell.Empty) {
				board.setCell(Cell.Empty, x, i);
				break;
			}
		}

		setBoard(board);
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
	protected void mudShoot(int x, int y) {
		if ((y <= 0) || (y > boardHeight))
			return;

		Board board = getBoard();

		for (int i = y - 1; i >= 0; i--) {
			if (board.getCell(x, i) != Cell.Empty) {
				if (board.getCell(x, i + 1) == Cell.Empty) {
					board.setCell(Cell.Full, x, i + 1);
				}
				break;
			}
			// if there were no obstacles then create a full cell at the border
			// of the board
			else if (i == 0) {
				board.setCell(Cell.Full, x, 0);
			}
		}

		setBoard(board);

		// check for full lines
		removeFullLines();
	}

	private void flowDown(Board board, int x, int y, TetrisShape piece,
			boolean isAcid) {

		int[][] sortedPoints = new int[piece.getLength()][2];
		int n = 0;
		for (int j = piece.minY(); j <= piece.maxY(); j++) {
			// check leaving from the board
			if (j >= boardHeight)
				break;

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
				sleep(ANIMATION_DELAY * 2);
			}
			if (isAcid && (board_y > 0)) {
				drawPoint(board, board_x, board_y - 1, piece.getFill());
				drawPoint(board, board_x, board_y, Cell.Empty);
				sleep(ANIMATION_DELAY * 2);
				drawPoint(board, board_x, board_y - 1, Cell.Empty);
			} else
				drawPoint(board, board_x, board_y, Cell.Full);
			setBoard(board);
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

	@Override
	protected synchronized void fireBoardChanged(Board board) {
		Board newBoard = board.clone();

		// draws the inverted board
		if (drawInvertedBoard) {
			for (int i = 0; i < board.getHeight(); i++) {
				newBoard.setRow(board.getRow(i), board.getHeight() - i - 1);
			}
		}

		super.fireBoardChanged(newBoard);
	}

	@Override
	protected synchronized void firePreviewChanged(Board board) {
		Board newBoard = board.clone();

		// draws the inverted board
		if (drawInvertedBoard
				&& !nextPiece.containsIn(new Figures[] { Figures.SuperPoint,
						Figures.SuperGun, Figures.SuperMudGun,
						Figures.SuperBomb })) {
			for (int i = 0; i < board.getHeight(); i++) {
				newBoard.setRow(board.getRow(i), board.getHeight() - i - 1);
			}
		}

		super.firePreviewChanged(newBoard);
	}

	@Override
	public void keyPressed(KeyPressed key) {
		if (drawInvertedBoard) {
			// swap the up and down buttons
			if (key == KeyPressed.KeyDown)
				keys.add(KeyPressed.KeyUp);
			else if (key == KeyPressed.KeyUp)
				keys.add(KeyPressed.KeyDown);
			else
				keys.add(key);
		} else
			super.keyPressed(key);
	}

	@Override
	public void keyReleased(KeyPressed key) {
		if (drawInvertedBoard) {
			// swap the up and down buttons
			if (key == KeyPressed.KeyDown)
				keys.remove(KeyPressed.KeyUp);
			else if (key == KeyPressed.KeyUp)
				keys.remove(KeyPressed.KeyDown);
			else
				keys.remove(key);
		} else
			super.keyReleased(key);
	}

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		super.processKeys();

		if ((getStatus() == Status.Running) && (!isFallingFinished)) {
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
				if (curPiece.getShape() == Figures.SuperGun) {
					// than shoot of it
					shoot(curX, curY + curPiece.minY());
				} else if (curPiece.getShape() == Figures.SuperMudGun) {
					mudShoot(curX, curY + curPiece.minY());
					// if the super point, than do nothing
				} else if (curPiece.getShape() != Figures.SuperPoint) {
					TetrisShape rotatedPiece;
					if (getRotation() == Rotation.Counterclockwise)
						rotatedPiece = curPiece.clone().rotateLeft();
					else
						rotatedPiece = curPiece.clone().rotateRight();
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
