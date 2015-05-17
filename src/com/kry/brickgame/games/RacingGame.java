package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.setKeyDelay;

import java.util.Iterator;
import java.util.LinkedList;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.shapes.CarShape;

/**
 * @author noLive
 */
public class RacingGame extends GameWithLives {
	private static final long serialVersionUID = 4304043854809432310L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.RaceSplash";
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 4;
	
	private static final int LOOP_DELAY = ANIMATION_DELAY * 16;
	/**
	 * Racing car
	 */
	private final CarShape car;
	/**
	 * List of the opponents
	 */
	private LinkedList<int[]> opponents;
	
	/**
	 * Array of possible x-axis positions of the car
	 */
	private final int[] positions;
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
	private final boolean isThreelaneTraffic;
	
	private boolean oddMove;
	
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
	public RacingGame(int speed, int level, int type) {
		super(speed, level, type);
		
		car = new CarShape();
		
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
		isThreelaneTraffic = getType() % 2 == 0;
		// for types 3-4
		setDrawInvertedBoard(getType() >= 3);
		
		loadNewLevel();
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
		// from 10 - on level 1, to 5 - on level 10 plus some random
		int distance = 10 - getLevel() / 2 + r.nextInt(3);
		
		int lastOpponentY;
		if (opponents.isEmpty()) {
			lastOpponentY = boardHeight - distance;
		} else {
			lastOpponentY = opponents.getLast()[1] + car.maxY();
		}
		
		if (lastOpponentY > boardHeight - distance) return false;
		
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
			        && r.nextInt(10 - getLevel() / 2) == 0) {
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
	 * Launching the game
	 */
	@Override
	public Game call() {
		super.init();
		
		// don't start playing sound after deserialization
		if (getStatus() != Status.Paused) {
			GameSound.loop(GameSound.effects, Effects.engine, LOOP_DELAY);
		}
		
		while (!(exitFlag || Thread.currentThread().isInterrupted())
		        && getStatus() != Status.GameOver) {
			if (getStatus() == Status.Running) {
				int currentSpeed = getSpeed(true);
				if (isThreelaneTraffic) {
					// slow down if isThreelaneTraffic
					currentSpeed = Math.round(currentSpeed * 1.5f);
				}
				
				// moving
				if (elapsedTime(Math.round(currentSpeed / 2f))) {
					// borders moving twice as fast as opponents
					moveOn(oddMove = !oddMove);
				}
			}
			// processing of key presses
			processKeys();
		}
		return nextGame;
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
		
		// generate border
		for (int i = 0; i < border.length; i++) {
			if ((i + borderPosition) % spanLength < filledSpanLength) {
				border[i] = Cell.Full;
			} else {
				border[i] = Cell.Empty;
			}
		}
		// add borders to the board
		Board newBoard = board.clone();
		newBoard.setColumn(border, boardWidth - 1);
		if (isBoth) {
			newBoard.setColumn(border, 0);
		}
		
		// shift the position of the border
		if (borderPosition < spanLength - 1) {
			borderPosition++;
		} else {
			borderPosition = 0;
		}
		
		return newBoard;
	}
	
	@Override
	protected int getSpeedOfFirstLevel() {
		return 200;
	}
	
	@Override
	protected int getSpeedOfTenthLevel() {
		return 50;
	}
	
	/**
	 * Loading or reloading the specified level
	 */
	@Override
	void loadNewLevel() {
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
		moveOn(true);
		
		super.loadNewLevel();
		
		if (!isStarted) {
			GameSound.loop(GameSound.effects, Effects.engine, LOOP_DELAY);
		}
	}
	
	@Override
	void loss(int x, int y) {
		GameSound.stop(GameSound.effects, Effects.engine);
		super.loss(x, y);
	}
	
	/**
	 * Moving the racing car to the new position
	 * 
	 * @param position
	 *            the new position of the car
	 */
	private boolean moveCar(int position) {
		if (position < 0 || position >= positions.length) return false;
		
		int newX = positions[position];
		
		Board board = getBoard();
		
		// Erase the car to not interfere with the checks
		board = drawShape(board, curX, curY, car, Cell.Empty);
		
		// check for accident
		boolean isAccident = checkCollision(board, car, newX, curY);
		
		// draw the car on the new place
		setBoard(drawShape(board, newX, curY, car, Cell.Full));
		
		curX = newX;
		curPosition = position;
		
		if (isAccident) {
			loss(curX, curY);
		}
		
		return !isAccident;
	}
	
	/**
	 * Shifting the borders and opponents on a one cell down and drawing it
	 */
	private void moveOn(boolean catchOpponents) {
		Board board = getBoard();
		// draw borders
		board = drawBorder(board, !isThreelaneTraffic);
		if (catchOpponents) {
			// draw opponents
			Iterator<int[]> it = opponents.iterator();
			while (it.hasNext()) {
				int[] opponent = it.next();
				// erase the opponent from the board
				board = drawShape(board, opponent[0], opponent[1], car, Cell.Empty);
				
				// if the opponent does not leave off the board
				if (opponent[1] + car.maxY() > 0) {
					
					// check for accident
					boolean isAccident = checkCollision(board, car, opponent[0], opponent[1] - 1);
					
					// draw the opponent on one cell below
					board = drawShape(board, opponent[0], --opponent[1], car, Cell.Full);
					
					if (isAccident) {
						setBoard(board);
						loss(curX, curY + car.maxY());
						return;
					}
				} else {
					it.remove();
					setScore(getScore() + 1);
				}
			}
			// add new opponents
			addOpponents();
		}
		setBoard(board);
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (keys.isEmpty() || getStatus() == Status.None) return;
		
		super.processKeys();
		
		if (getStatus() == Status.Running) {
			if (containsKey(KeyPressed.KeyLeft)) {
				if (moveCar(curPosition - 1)) {
					GameSound.playEffect(Effects.move);
				}
				keys.remove(KeyPressed.KeyLeft);
			}
			if (containsKey(KeyPressed.KeyRight)) {
				if (moveCar(curPosition + 1)) {
					GameSound.playEffect(Effects.move);
				}
				keys.remove(KeyPressed.KeyRight);
			}
			if (containsKey(KeyPressed.KeyUp)) {
				moveOn(oddMove = !oddMove);
				setKeyDelay(KeyPressed.KeyUp, ANIMATION_DELAY);
			}
			if (containsKey(KeyPressed.KeyRotate)) {
				moveOn(oddMove = !oddMove);
				setKeyDelay(KeyPressed.KeyRotate, ANIMATION_DELAY);
			}
		}
	}
	
	@Override
	void resume() {
		if (getStatus() == Status.Paused) {
			GameSound.loop(GameSound.effects, Effects.engine, ANIMATION_DELAY * 14);
		}
		super.resume();
	}
	
	@Override
	void setScore(int score) {
		int oldHundreds = getScore() / 100;
		
		super.setScore(score);
		
		// when a sufficient number of points changes the speed and the level
		if (getScore() / 100 > oldHundreds) {
			setLevel(getLevel() + 1);
			
			if (getLevel() == 1) {
				setSpeed(getSpeed() + 1);
			}
		}
	}
	
	@Override
	void win() {
		GameSound.stop(GameSound.effects, Effects.engine);
		super.win();
	}
	
}
