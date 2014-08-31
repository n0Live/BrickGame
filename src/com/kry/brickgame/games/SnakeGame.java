package com.kry.brickgame.games;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Obstacle;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.SnakeShape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.GameSplash;
import com.kry.brickgame.splashes.SnakeSplash;

/**
 * @author noLive
 * 
 */
public class SnakeGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final GameSplash splash = new SnakeSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 2;

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
	 * The current x-coordinate position on the board of the snake's head
	 */
	private int curX;;
	/**
	 * The current y-coordinate position on the board of the snake's head
	 */
	private int curY;

	public SnakeGame(int speed, int level) {
		super(speed, level);

		setStatus(Status.None);

		snake = new SnakeShape();

		// starting position - the middle of the bottom border of the board
		curX = boardWidth / 2 - snake.getLength() / 2;
		curY = 0;
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		super.start();

		setStatus(Status.Running);
		setLives(4);

		tryMove(snake.getDirection());
		//prepareBoard();
		loadPreparedObstacle();
		addApple();

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			// moving of the snake
			if ((getStatus() != Status.Paused) && (elapsedTime(getSpeed(true)))) {
				if (!tryMove(snake.getDirection())) {
					// kaboom and decrease lives
					kaboom(curX, curY);
					setLives(getLives() - 1);
					if (getLives() > 0) {
						loadLevel();
					} else {
						gameOver();
					}
				}
				// when the snake has reached the maximum length
				if (snake.getLength() >= snake.getMaxLength()) {
					// increases level and load it
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
			// from 2 to 5 - 1, from 6 to 20 - 2
			int obstaclesMultiplier = (getLevel() / 5) + 1;

			for (int i = 0; i < obstaclesMultiplier; i++) {
				for (int k = 1; k <= 2; k++) {
					generateObstacle(k);
				}
			}
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

		// finds empty cells
		do {
			// in line at the borders of the board shall not be put an obstacle
			x = r.nextInt(boardWidth - obstacle.getWidth() - 2) + 1;
			y = r.nextInt(boardHeight - obstacle.getHeight() - 2)
					+ obstacle.getHeight() + 1;
		} while (checkCollision(getBoard(), obstacle, x, y));

		setBoard(drawShape(getBoard(), x, y, obstacle, Cell.Full));
	}
	
	/**
	 * Loading predefined obstacles
	 */
	private void loadPreparedObstacle(){
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
		animatedClearBoard(true);// fast

		snake = new SnakeShape();
		curX = boardWidth / 2 - snake.getLength() / 2;
		curY = 0;

		tryMove(snake.getDirection());
		//prepareBoard();
		loadPreparedObstacle();
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
	private Board drawSnake(Board board, SnakeShape snake, int x, int y) {
		for (int i = 0; i < snake.getLength(); i++) {
			int board_x = x + snake.x(i);
			int board_y = y + snake.y(i);
			// draws the snake on the board
			// the head of the snake is blinking
			board.setCell(((i == 0) ? Cell.Blink : Cell.Full), board_x, board_y);
		}
		return board;
	}

	/**
	 * Tries to move the snake
	 * 
	 * @param snake
	 *            the snake after the rotating or movement
	 * @param newX
	 *            x-coordinate position on the board of the snake's head
	 * @param newY
	 *            y-coordinate position on the board of the snake's head
	 * @return {@code true} if the movement succeeded otherwise {@code false}
	 */
	private boolean tryMove(RotationAngle direction) {
		int newX;
		int newY;
		boolean isReversal = (direction == snake.getOppositeDirection(snake
				.getDirection()));

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
		if (checkBoardCollision(headOfSnake, newX, newY))
			return false;

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
		board.setCell(Cell.Empty, board_x, board_y);

		// if it was an apple in front then erase it
		if (isAppleAhead) {
			board.setCell(Cell.Empty, newX, newY);
		}

		// check the collision with obstacles
		if (checkCollision(board, headOfSnake, newX, newY)) {
			return false;
		}

		// draw the new snake on the board
		setBoard(board.clone());
		setBoard(drawSnake(getBoard(), newSnake, newX, newY));

		if (isAppleAhead) {
			// increases score
			setScore(getScore() + 1);
			// add a new apple
			if (newSnake.getLength() < newSnake.getMaxLength())
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
		if (direction == snake.getDirection().getRight().getRight()) {
			// than returns the last cell (tail) of the snake as the offset
			return snake.x(snake.tail());
		} else {
			// otherwise gets the offset in dependence on the direction
			return snake.getShiftX(direction);
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
		if (direction == snake.getDirection().getRight().getRight()) {
			// than returns the last cell (tail) of the snake as the offset
			return snake.y(snake.tail());
		} else {
			// otherwise gets the offset in dependence on the direction
			return snake.getShiftY(direction);
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

		if (keys.contains(KeyPressed.KeyLeft)) {
			tryMove(LEFT);
			justSleep(ANIMATION_DELAY * 4);
		}
		if (keys.contains(KeyPressed.KeyRight)) {
			tryMove(RIGHT);
			justSleep(ANIMATION_DELAY * 4);
		}
		if (keys.contains(KeyPressed.KeyDown)) {
			tryMove(DOWN);
			justSleep(ANIMATION_DELAY * 4);
		}
		if (keys.contains(KeyPressed.KeyUp)) {
			tryMove(UP);
			justSleep(ANIMATION_DELAY * 4);
		}
		if (keys.contains(KeyPressed.KeyRotate)) {
			tryMove(snake.getDirection());
			justSleep(ANIMATION_DELAY * 2);
		}
	}
}
