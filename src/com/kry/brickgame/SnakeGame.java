package com.kry.brickgame;

import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.Shape.RotationAngle;

/**
 * @author noLive
 * 
 */
public class SnakeGame extends Game {

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

		if (!tryMove(snake, curX, curY))
			gameOver();

		while (getStatus() != Status.GameOver) {
			// moving of the snake
			if ((getStatus() != Status.Paused) && (elapsedTime(getSpeed(true)))) {
				if (!snakeMovement())
					gameOver();
			}
			// processing of key presses
			processKeys();
		}
	}

	/**
	 * Movement of the snake
	 * 
	 * @return {@code true} if the motion is possible, otherwise {@code false}
	 */
	private boolean snakeMovement() {
		int shiftX = snake.getShiftX(snake.getDirection());
		int shiftY = snake.getShiftY(snake.getDirection());

		return tryMove(snake.move(), curX + shiftX, curY + shiftY);
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
	private boolean tryMove(SnakeShape snake, int newX, int newY) {
		Shape headOfSnake = new Shape(1);

		// gets the head of the new snake
		headOfSnake.setCoord(0, snake.getCoord(0));

		// create a temporary board, a copy of the basic board
		Board board = getBoard().clone();

		// if the head of the new snake is same as the tail of the old snake
		// (snake made a 180-degree turn), then DO NOT cut off the tail of the
		// snake
		if (headOfSnake.getCoord(0) != snake.getCoord(snake.tail())) {
			// cut off the tail of the old snake
			int board_x = curX + this.snake.x(this.snake.tail());
			int board_y = curY + this.snake.y(this.snake.tail());
			board.setCell(Cell.Empty, board_x, board_y);
		}

		// Checks
		if (checkBoardCollision(headOfSnake, newX, newY))
			return false;
		if (checkCollision(board, headOfSnake, newX, newY))
			return false;

		// Draw the new snake on the board
		setBoard(board.clone());
		setBoard(drawSnake(getBoard(), snake, newX, newY));

		// The old snake is replaced by the new snake
		this.snake = snake.clone();
		curX = newX;
		curY = newY;

		return true;
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
		// ** Direction constants **
		final RotationAngle LEFT = RotationAngle.d270;
		final RotationAngle RIGHT = RotationAngle.d90;
		final RotationAngle UP = RotationAngle.d0;
		final RotationAngle DOWN = RotationAngle.d180;
		// **

		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyStart)) {
			pause();
			keys.remove(KeyPressed.KeyStart);
			return;
		}

		if (getStatus() != Status.Running)
			return;

		if (keys.contains(KeyPressed.KeyLeft)) {
			tryMove(snake.moveLeft(), curX + getShiftX(LEFT), curY
					+ getShiftY(LEFT));
			sleep(ANIMATION_DELAY * 3);
		}
		if (keys.contains(KeyPressed.KeyRight)) {
			tryMove(snake.moveRight(), curX + getShiftX(RIGHT), curY
					+ getShiftY(RIGHT));
			sleep(ANIMATION_DELAY * 3);
		}
		if (keys.contains(KeyPressed.KeyDown)) {
			tryMove(snake.moveDown(), curX + getShiftX(DOWN), curY
					+ getShiftY(DOWN));
			sleep(ANIMATION_DELAY * 3);
		}
		if (keys.contains(KeyPressed.KeyUp)) {
			tryMove(snake.moveUp(), curX + getShiftX(UP), curY + getShiftY(UP));
			sleep(ANIMATION_DELAY * 3);
		}
		if (keys.contains(KeyPressed.KeyRotate)) {
			//gets the offset for the current direction
			tryMove(snake.move(), curX + getShiftX(snake.getDirection()), curY
					+ getShiftY(snake.getDirection()));
			sleep(ANIMATION_DELAY);
		}
	}
}
