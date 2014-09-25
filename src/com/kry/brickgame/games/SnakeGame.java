package com.kry.brickgame.games;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.games.Game.Status;
import com.kry.brickgame.shapes.Obstacle;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.shapes.SnakeShape;
import com.kry.brickgame.splashes.SnakeSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class SnakeGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new SnakeSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 4;

	// ** Direction constants **
	private static final RotationAngle LEFT = RotationAngle.d270;
	private static final RotationAngle RIGHT = RotationAngle.d90;
	private static final RotationAngle UP = RotationAngle.d0;
	private static final RotationAngle DOWN = RotationAngle.d180;
	// **

	/**
	 * The snake
	 */
	private SnakeShape snake;

	/**
	 * Whether the snake can teleporting from one side of the board to another?
	 */
	private boolean isToroidalField;
	/**
	 * Use preloaded levels or generate new ones?
	 */
	private boolean usePreloadedLevels;

	/**
	 * The Snake
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
	 *            <li>regular snake with standard obstacles
	 *            <li>snake on a toroidal field with standard obstacles
	 *            <li>regular snake with randomly generated obstacles
	 *            <li>snake on a toroidal field with randomly generated
	 *            obstacles
	 */
	public SnakeGame(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
		setStatus(Status.None);
		// for every even type of game
		isToroidalField = (getType() % 2 == 0);
		// for types 1-2
		usePreloadedLevels = (getType() <= 2);
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();

		setStatus(Status.Running);
		setLives(4);

		loadLevel();

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			// moving of the snake
			if ((getStatus() != Status.Paused) && (elapsedTime(getSpeed(true)))) {
				if (!tryMove(snake.getDirection())) {
					loss();
				}
				// when the snake has reached the maximum length
				if (snake.getLength() >= SnakeShape.getMaxLength()) {
					// increases level and load it
					animatedClearBoard(true);// fast
					incLevel();
					loadLevel();
				}
			}
			// processing of key presses
			processKeys();
		}
	}

	/**
	 * Preparation of the boards for the new level
	 */
	private void prepareBoard() {
		if (getLevel() > 1) {
			// plus one random obstacle each level
			for (int i = 0; i < getLevel() - 1; i++) {
				generateObstacle(new Random().nextInt(3));
			}
		}
	}

	/**
	 * Generating borders for the toroidal field
	 * 
	 * @param hasRandomGates
	 *            {@code true} - to generate a random gates, {@code false} - to
	 *            generate a gates, placed in the middle of the border
	 */
	private void prepareBorders(boolean hasRandomGates) {
		Cell fill;

		// generates the vertical borders
		for (int i = 0; i < boardHeight; i++) {
			// except for the corner points
			if ((hasRandomGates) && (i != 0) && (i != boardHeight - 1)) {
				Random r = new Random();
				fill = (r.nextInt(boardHeight) == 0) ? Cell.Empty : Cell.Full;
			} else
				fill = (i == boardHeight / 2) ? Cell.Empty : Cell.Full;
			getBoard().setCell(fill, 0, i);
			getBoard().setCell(fill, boardWidth - 1, i);
		}
		// generates the horizontal borders
		for (int i = 0; i < boardWidth; i++) {
			// except for the corner points
			if ((hasRandomGates) && (i != 0) && (i != boardWidth - 1)) {
				Random r = new Random();
				fill = (r.nextInt(boardWidth) == 0) ? Cell.Empty : Cell.Full;
			} else
				fill = (i == boardWidth / 2) ? Cell.Empty : Cell.Full;
			getBoard().setCell(fill, i, 0);
			getBoard().setCell(fill, i, boardHeight - 1);
		}
	}

	/**
	 * Creates the obstacle and places it on the board randomly
	 * 
	 * @param type
	 *            type of the obstacle
	 */
	private void generateObstacle(int type) {
		Random r = new Random();
		int x, y;

		Obstacle obstacle = new Obstacle(type);
		obstacle.setRandomRotate();

		// in line at the borders of the board shall not be put an obstacle
		// k - count of empty line at the borders
		int k = (isToroidalField) ? 2 : 1;

		// finds empty cells
		do {
			x = r.nextInt(boardWidth - obstacle.getWidth() - k * 2) + k;
			y = r.nextInt(boardHeight - obstacle.getHeight() - k * 2)
					+ obstacle.getHeight() + k;
		} while (checkCollision(getBoard(), obstacle, x, y));

		setBoard(drawShape(getBoard(), x, y, obstacle, Cell.Full));
	}

	/**
	 * Loading predefined obstacles
	 */
	private void loadPreparedObstacle() {
		Obstacle obstacle;

		switch (getLevel()) {
		case 2:
			obstacle = new Obstacle(1);
			setBoard(drawShape(getBoard(), 1, 3, obstacle, Cell.Full));
			obstacle = new Obstacle(2).changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 8, 8, obstacle, Cell.Full));
			break;
		case 3:
			obstacle = new Obstacle(1).changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 6, 3, obstacle, Cell.Full));
			obstacle = new Obstacle(2).changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 0, 8, obstacle, Cell.Full));
			break;
		case 4:
			obstacle = new Obstacle(1).changeRotationAngle(DOWN);
			setBoard(drawShape(getBoard(), 6, 15, obstacle, Cell.Full));
			obstacle = new Obstacle(2);
			setBoard(drawShape(getBoard(), 3, 11, obstacle, Cell.Full));
			break;
		case 5:
			obstacle = new Obstacle(1).changeRotationAngle(RIGHT);
			setBoard(drawShape(getBoard(), 1, 15, obstacle, Cell.Full));
			obstacle = new Obstacle(2);
			setBoard(drawShape(getBoard(), 3, 6, obstacle, Cell.Full));
			break;
		case 6:
			obstacle = new Obstacle(1).changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 6, 3, obstacle, Cell.Full));
			obstacle.changeRotationAngle(RIGHT);
			setBoard(drawShape(getBoard(), 1, 15, obstacle, Cell.Full));
			obstacle = new Obstacle(2);
			setBoard(drawShape(getBoard(), 3, 11, obstacle, Cell.Full));
			obstacle.changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 8, 8, obstacle, Cell.Full));
			break;
		case 7:
			obstacle = new Obstacle(1);
			setBoard(drawShape(getBoard(), 1, 3, obstacle, Cell.Full));
			obstacle.changeRotationAngle(DOWN);
			setBoard(drawShape(getBoard(), 6, 15, obstacle, Cell.Full));
			obstacle = new Obstacle(2);
			setBoard(drawShape(getBoard(), 3, 6, obstacle, Cell.Full));
			obstacle.changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 0, 8, obstacle, Cell.Full));
			break;
		case 8:
			obstacle = new Obstacle(1).changeRotationAngle(RIGHT);
			setBoard(drawShape(getBoard(), 1, 15, obstacle, Cell.Full));
			obstacle.changeRotationAngle(DOWN);
			setBoard(drawShape(getBoard(), 6, 15, obstacle, Cell.Full));
			obstacle = new Obstacle(2).changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 0, 8, obstacle, Cell.Full));
			setBoard(drawShape(getBoard(), 8, 8, obstacle, Cell.Full));
			break;
		case 9:
			obstacle = new Obstacle(1);
			setBoard(drawShape(getBoard(), 1, 3, obstacle, Cell.Full));
			obstacle.changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 6, 3, obstacle, Cell.Full));
			obstacle = new Obstacle(2);
			setBoard(drawShape(getBoard(), 3, 11, obstacle, Cell.Full));
			obstacle.changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 0, 8, obstacle, Cell.Full));
			break;
		case 10:
			obstacle = new Obstacle(1).changeRotationAngle(LEFT);
			setBoard(drawShape(getBoard(), 6, 3, obstacle, Cell.Full));
			obstacle.changeRotationAngle(DOWN);
			setBoard(drawShape(getBoard(), 6, 15, obstacle, Cell.Full));
			obstacle = new Obstacle(2);
			setBoard(drawShape(getBoard(), 3, 6, obstacle, Cell.Full));
			setBoard(drawShape(getBoard(), 3, 11, obstacle, Cell.Full));
			break;
		default:
			break;
		}
	}

	/**
	 * Increases level and speed
	 */
	private void incLevel() {
		setLevel(getLevel() + 1);
		if (getLevel() == 1)
			setSpeed(getSpeed() + 1);
	}

	/**
	 * Loading or reloading the specified level
	 */
	private void loadLevel() {
		snake = new SnakeShape((getRotation() == Rotation.Clockwise) ? RIGHT
				: LEFT);
		// starting position - the middle of the bottom border of the board
		curX = boardWidth / 2 + ((snake.getDirection() == RIGHT) ? -1 : 1);

		curY = (isToroidalField) ? 1 : 0;

		tryMove(snake.getDirection());

		if (isToroidalField) {
			prepareBorders(!usePreloadedLevels);
		}

		if (usePreloadedLevels)
			loadPreparedObstacle();
		else
			prepareBoard();
		addApple();
	}

	/**
	 * Drawing of the snake on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate position on the board
	 * @param y
	 *            y-coordinate position on the board
	 * 
	 * @return - the board with the snake
	 */
	private static Board drawSnake(Board board, SnakeShape snake, int x, int y) {
		for (int i = 0; i < snake.getLength(); i++) {
			// draws the snake on the board
			// the head of the snake is blinking
			drawPoint(board, x + snake.x(i), y + snake.y(i),
					((i == 0) ? Cell.Blink : Cell.Full));
		}
		return board;
	}

	/**
	 * Tries to move the snake
	 * 
	 * @param direction
	 *            the direction of the movement of the snake
	 * @return {@code true} if the movement succeeded otherwise {@code false}
	 */
	private boolean tryMove(RotationAngle direction) {
		int newX;
		int newY;
		boolean isReversal = (direction == snake.getDirection().getOpposite());

		if (isReversal) {
			newX = curX + snake.x(snake.tail());
			newY = curY + snake.y(snake.tail());
		} else {
			newX = curX + getShiftX(direction);
			newY = curY + getShiftY(direction);
		}

		// gets the head of the snake
		Shape headOfSnake = new Shape(1);
		headOfSnake.setCoord(0, snake.getCoord(0));

		// check the out off the board
		if (checkBoardCollision(headOfSnake, newX, newY)) {
			if (isToroidalField) {
				if (newX < 0)
					newX = boardWidth + newX;
				else if (newX >= boardWidth)
					newX = newX - boardWidth;
				if (newY < 0)
					newY = boardHeight + newY;
				else if (newY >= boardHeight)
					newY = newY - boardHeight;

			} else
				return false;
		}

		boolean isAppleAhead = (getBoard().getCell(newX, newY) == Cell.Blink);

		SnakeShape newSnake = snake.moveTo(direction, isAppleAhead);

		// if the reversal is not possible
		if (isReversal && newSnake.equals(snake)) {
			// returns the old values of the coordinates
			newX = curX;
			newY = curY;
		}

		// create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// cut off the tail of the old snake, because the snake should already
		// move and the tail will interfere with checks
		int board_x = curX + snake.x(snake.tail());
		int board_y = curY + snake.y(snake.tail());
		drawPoint(board, board_x, board_y, Cell.Empty);

		// if it was an apple in front then erase it
		if (isAppleAhead) {
			board.setCell(Cell.Empty, newX, newY);
		}

		// check the collision with obstacles
		if (checkCollision(board, headOfSnake, newX, newY)) {
			return false;
		}

		// draw the new snake on the board
		setBoard(drawSnake(board.clone(), newSnake, newX, newY));

		if (isAppleAhead) {
			// increases score
			setScore(getScore() + 1);
			// add a new apple
			if (newSnake.getLength() < SnakeShape.getMaxLength())
				addApple();
		}

		// the old snake is replaced by the new snake
		this.snake = newSnake.clone();
		curX = newX;
		curY = newY;

		return true;
	}

	/**
	 * Adds "apple" (the blinking cell) to the board
	 */
	private void addApple() {
		Random r = new Random();
		int x, y;

		// finds empty cell
		do {
			x = r.nextInt(boardWidth);
			y = r.nextInt(boardHeight);
		} while (getBoard().getCell(x, y) != Cell.Empty);

		// adds "apple" - the blinking cell
		getBoard().setCell(Cell.Blink, x, y);
	}

	/**
	 * Calculate the offset for the x-coordinate for the selected direction
	 * 
	 * @param direction
	 *            direction of movement of of the snake
	 * @return the offset for the x-coordinate
	 */
	private int getShiftX(RotationAngle direction) {
		// if snake made a 180-degree turn
		if (direction == snake.getDirection().getOpposite()) {
			// than returns the last cell (tail) of the snake as the offset
			return snake.x(snake.tail());
		} else {
			// otherwise gets the offset in dependence on the direction
			return SnakeShape.getShiftX(direction);
		}
	}

	/**
	 * Calculate the offset for the y-coordinate for the selected direction
	 * 
	 * @param direction
	 *            direction of movement of of the snake
	 * @return the offset for the y-coordinate
	 */
	private int getShiftY(RotationAngle direction) {
		// if snake made a 180-degree turn
		if (direction == snake.getDirection().getOpposite()) {
			// than returns the last cell (tail) of the snake as the offset
			return snake.y(snake.tail());
		} else {
			// otherwise gets the offset in dependence on the direction
			return SnakeShape.getShiftY(direction);
		}
	}

	/**
	 * Drawing effect of the explosion and decreasing lives
	 */
	private void loss() {
		// kaboom and decrease lives
		kaboom(curX, curY);
		setLives(getLives() - 1);
		if (getLives() > 0) {
			animatedClearBoard(true);// fast
			loadLevel();
		} else {
			gameOver();
		}
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
				if (!tryMove(LEFT))
					loss();
				sleep(ANIMATION_DELAY * 4);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				if (!tryMove(RIGHT))
					loss();
				sleep(ANIMATION_DELAY * 4);
			}
			if (keys.contains(KeyPressed.KeyDown)) {
				if (!tryMove(DOWN))
					loss();
				sleep(ANIMATION_DELAY * 4);
			}
			if (keys.contains(KeyPressed.KeyUp)) {
				if (!tryMove(UP))
					loss();
				sleep(ANIMATION_DELAY * 4);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				if (!tryMove(snake.getDirection()))
					loss();
				sleep(ANIMATION_DELAY * 2);
			}
		}
	}

}
