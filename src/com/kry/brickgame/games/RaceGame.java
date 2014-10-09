package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.—haracterShape;
import com.kry.brickgame.shapes.—haracterShape.—haracters;
import com.kry.brickgame.splashes.RaceSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class RaceGame extends GameWithLives {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new RaceSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 4;
	/**
	 * Racing car
	 */
	private —haracterShape car;
	/**
	 * List of the opponents
	 */
	private LinkedList<int[]> opponents;

	/**
	 * Array of possible x-axis positions of the car
	 */
	private int[] positions;
	/**
	 * Current position of the racing car
	 */
	private int curPosition;
	/**
	 * Current position of the border shift
	 */
	private int borderPosition;
	/**
	 * Number of traffic lanes:
	 * <p>
	 * {@code true} - three-lane, {@code false} - two-lane
	 */
	private boolean isThreelaneTraffic;
	/**
	 * Whether to draw the board upside down?
	 */
	private boolean drawInvertedBoard;

	/**
	 * The Race
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game type of the game:
	 *            <ol>
	 *            <li>race with two possible positions of the car
	 *            <li>race with three possible positions of the car and a wide
	 *            variety of positions of the opponents
	 *            <li>race with two possible positions of the car, the board is
	 *            upside down;
	 *            <li>race with three possible positions of the car, the board
	 *            is upside down;
	 */
	public RaceGame(int speed, int level, int type) {
		super(speed, level, type);

		car = new —haracterShape(—haracters.Car);

		// create the array of possible x-axis positions of the car
		int startPoint;
		if (type % 2 == 0) {
			positions = new int[3];
			startPoint = car.getWidth() / 2;
		} else {
			positions = new int[2];
			startPoint = 2 + car.getWidth() / 2;
		}
		for (int i = 0; i < positions.length; i++) {
			positions[i] = startPoint + i * car.getWidth();
		}

		// for every even type of game
		isThreelaneTraffic = (getType() % 2 == 0);
		// for types 3-4
		drawInvertedBoard = (getType() >= 3);
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		setStatus(Status.Running);

		loadNewLevel();

		while (!interrupted() && (getStatus() != Status.GameOver)) {

			int currentSpeed = (isThreelaneTraffic) ? getSpeed(true) / 2
					: getSpeed(true) / 3;

			if (getStatus() != Status.Paused) {
				// moving
				if (elapsedTime(currentSpeed)) {
					moveOn();
				}
			}
			// processing of key presses
			processKeys();
		}
	}

	/**
	 * Loading or reloading the specified level
	 */
	@Override
	protected void loadNewLevel() {
		// set position
		curPosition = 1;
		curX = positions[curPosition];
		curY = 0 - car.minY();

		borderPosition = 0;

		// initialization of the opponents
		opponents = new LinkedList<>();
		addOpponents();

		// draw the car
		moveCar(curPosition);
		// draw the opponents and the borders
		moveOn();

		setStatus(Status.Running);
	}

	/**
	 * Shifting the borders and opponents on a one cell down and drawing it
	 */
	private void moveOn() {
		Board board = getBoard();
		// draw borders
		setBoard(drawBorder(board, !isThreelaneTraffic));
		// draw opponents
		Iterator<int[]> it = opponents.iterator();

		while (it.hasNext()) {
			int[] opponent = it.next();
			// erase the opponent from the board
			board = drawShape(getBoard(), opponent[0], opponent[1], car,
					Cell.Empty);

			// if the opponent does not leave off the board
			if (opponent[1] + car.maxY() > 0) {

				// check for accident
				boolean isAccident = checkCollision(board, car, opponent[0],
						opponent[1] - 1);

				// draw the opponent on one cell below
				board = drawShape(getBoard(), opponent[0], --opponent[1], car,
						Cell.Full);

				if (isAccident) {
					setBoard(board);
					loss(curX, curY + car.maxY());
					return;
				}
			} else {
				it.remove();
				setScore(getScore() + 1);
			}
			setBoard(board);
		}
		// add new opponents
		addOpponents();
	}

	/**
	 * Adding the new opponents
	 * <p>
	 * The distance between the opponents and their positions depends on the
	 * {@code level} and {@code type}
	 * 
	 * @return {@code true} - if the new opponents have been added,
	 *         {@code false} - otherwise
	 */
	private boolean addOpponents() {
		// from 10 - on level 1, to 5 - on level 10
		int distance = 10 - getLevel() / 2;

		int lastOpponentY;
		if (opponents.isEmpty())
			lastOpponentY = boardHeight - distance;
		else
			lastOpponentY = opponents.getLast()[1] + car.maxY();

		if (lastOpponentY > boardHeight - distance)
			return false;

		Random r = new Random();
		int positionX;
		int coordX, coordY;

		// adding opponents until the board has a place
		do {
			positionX = r.nextInt(positions.length);
			coordX = positions[positionX];
			coordY = lastOpponentY + distance - car.minY();

			// for levels with 3 positions;
			if (isThreelaneTraffic
			// and chance from 1/10 - on level 1, to 1/5 - on level 10
					&& (r.nextInt(10 - getLevel() / 2) == 0)) {
				if (r.nextBoolean()) {// create two opponents
					// create the first opponent
					opponents.add(new int[] { coordX, coordY });
					// calculate position of the second one
					int anotherPositionX;
					do {
						anotherPositionX = r.nextInt(positions.length);
					} while (anotherPositionX == positionX);
					// create the second one
					coordX = positions[anotherPositionX];
					opponents.add(new int[] { coordX, coordY });
				} else { // or set position with shift
					if (positionX == 0) {
						coordX += 1;// shift to right
					} else if (positionX == positions.length - 1) {
						coordX -= 1;// shift to left
					} else {
						coordX += r.nextBoolean() ? 1 : -1;// random
					}
					opponents.add(new int[] { coordX, coordY });
				}
			} else {
				opponents.add(new int[] { coordX, coordY });
			}

			lastOpponentY = opponents.getLast()[1] + car.maxY();
		} while (lastOpponentY < boardHeight - distance);

		return true;
	}

	/**
	 * Drawing the borders on the board.
	 * 
	 * @param board
	 *            the board for drawing the borders
	 * @param isBoth
	 *            if {@code true}, then border will be added on both sides of
	 *            the board, otherwise - only on right side
	 * @return the board with the borders
	 */
	private Board drawBorder(Board board, boolean isBoth) {
		// Length of the filled region of the border
		final int filledSpanLength = 3;
		// Length of the empty region of the border
		final int emptySpanLength = 2;
		// The total length of the regions
		final int spanLength = filledSpanLength + emptySpanLength;

		Cell[] border = new Cell[boardHeight];

		Board newBoard = getBoard().clone();

		// generate border
		for (int i = 0; i < border.length; i++) {
			if ((i + borderPosition) % spanLength < filledSpanLength)
				border[i] = Cell.Full;
			else
				border[i] = Cell.Empty;
		}
		// add borders to the board
		newBoard.setColumn(border, boardWidth - 1);
		if (isBoth)
			newBoard.setColumn(border, 0);

		// shift the position of the border
		if (borderPosition < (spanLength - 1))
			borderPosition++;
		else
			borderPosition = 0;

		return newBoard;
	}

	/**
	 * Moving the racing car to the new position
	 * 
	 * @param position
	 *            the new position of the car
	 */
	private void moveCar(int position) {
		if ((position < 0) || (position >= positions.length))
			return;

		int newX = positions[position];

		// Create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// Erase the car to not interfere with the checks
		board = drawShape(board, curX, curY, car, Cell.Empty);

		// check for accident
		boolean isAccident = checkCollision(board, car, newX, curY);

		// draw the car on the new place
		setBoard(drawShape(board, newX, curY, car, Cell.Full));

		curX = newX;
		curPosition = position;

		if (isAccident)
			loss(curX, curY);
	}

	@Override
	protected void setScore(int score) {
		int oldHundreds = getScore() / 100;

		super.setScore(score);

		// when a sufficient number of points changes the speed and the level
		if (getScore() / 100 > oldHundreds) {
			setLevel(getLevel() + 1);

			if (getLevel() == 1)
				setSpeed(getSpeed() + 1);
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

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		super.processKeys();

		if (getStatus() == Status.Running) {

			if (keys.contains(KeyPressed.KeyLeft)) {
				moveCar(curPosition - 1);
				keys.remove(KeyPressed.KeyLeft);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				moveCar(curPosition + 1);
				keys.remove(KeyPressed.KeyRight);
			}
			if ((keys.contains(KeyPressed.KeyDown))
					|| (keys.contains(KeyPressed.KeyUp))) {
				moveOn();
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				moveOn();
				sleep(ANIMATION_DELAY);
			}
		}
	}

}
