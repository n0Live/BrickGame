package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.DOWN;
import static com.kry.brickgame.games.GameConsts.LEFT;
import static com.kry.brickgame.games.GameConsts.RIGHT;
import static com.kry.brickgame.games.GameConsts.UP;
import static com.kry.brickgame.games.GameUtils.checkBoardCollision;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawPoint;
import static com.kry.brickgame.games.GameUtils.setKeyDelay;
import static com.kry.brickgame.games.ObstacleUtils.getPreparedObstacles;
import static com.kry.brickgame.games.ObstacleUtils.getRandomObstacles;
import static com.kry.brickgame.games.ObstacleUtils.getSnakeObstacles;

import java.util.Arrays;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.shapes.SnakeShape;

/**
 * @author noLive
 */
public class SnakeGame extends GameWithLives {
	private static final long serialVersionUID = -4904453559355597994L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.SnakeSplash";
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 4;
	
	/**
	 * The snake
	 */
	private SnakeShape snake;
	
	/**
	 * Whether the snake can teleporting from one side of the board to another?
	 */
	private final boolean isToroidalField;
	/**
	 * Use preloaded levels or generate new ones?
	 */
	private final boolean usePreloadedLevels;
	
	/**
	 * Drawing of the snake on the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param x
	 *            x-coordinate position on the board
	 * @param y
	 *            y-coordinate position on the board
	 * @return - the board with the snake
	 */
	private static Board drawSnake(Board board, SnakeShape snake, int x, int y) {
		Board result = board;
		for (int i = 0; i < snake.getLength(); i++) {
			// draws the snake on the board
			// the head of the snake is blinking
			result = drawPoint(result, x + snake.x(i), y + snake.y(i), i == 0 ? Cell.Blink
					: Cell.Full);
		}
		return result;
	}
	
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
		
		// for every even type of game
		isToroidalField = getType() % 2 == 0;
		// for types 1-2
		usePreloadedLevels = getType() <= 2;
	}
	
	/**
	 * Adds "apple" (the blinking cell) to the board
	 */
	private void addApple() {
		int x, y;
		Board board = getBoard();
		// finds empty cell
		do {
			x = r.nextInt(boardWidth);
			y = r.nextInt(boardHeight);
		} while (board.getCell(x, y) != Cell.Empty);
		
		// adds "apple" - the blinking cell
		board.setCell(Cell.Blink, x, y);
		
		setBoard(board);
	}
	
	/**
	 * Launching the game
	 */
	@Override
	public Game call() {
		super.init();
		
		if (!desirialized) {
			loadNewLevel();
		}
		
		while (!isInterrupted() && getStatus() != Status.GameOver) {
            if (getStatus() == Status.Running && isStarted) {
                // deferred pause
                if (deferredPauseFlag) pause();

                if (getStatus() == Status.Running) {
                    int currentSpeed = getSpeed(true);
                    if (!usePreloadedLevels && getLevel() > 5) {
                        currentSpeed += ANIMATION_DELAY;
                    }
                    if (isToroidalField) {
                        currentSpeed += ANIMATION_DELAY / 2;
                    }

                    // moving of the snake
                    if (elapsedTime(currentSpeed) && !move(snake.getDirection())) {
                        loss(curX, curY);
                    }
                    // when the snake has reached the maximum length
                    if (snake.getLength() >= SnakeShape.getMaxLength()) {
                        win();
                    }
                }
            }
			// processing of key presses
			processKeys();
		}
		return getNextGame();
	}
	
	/**
	 * Generate the random coordinates for placing the gates
	 * 
	 * @param gatesCount
	 *            count of the gates (must be > 0)
	 * @param isVertical
	 *            {@code true} - for vertical borders, {@code false} - for
	 *            horizontal borders
	 * @return array containing the coordinates of the gates
	 */
	private int[] generateGates(int gatesCount, boolean isVertical) {
		int line = isVertical ? boardHeight : boardWidth;
		int[] gates = new int[gatesCount];
		
		// calculates the coordinates for the symmetrical arrangement
		// array must be sorted for Arrays.binarySearch()
		if (gatesCount > 0) {
			for (int i = 0; i < gatesCount / 2; i++) {
				gates[i] = r.nextInt(line / 2 - 1) + 1;
				gates[gatesCount - 1 - i] = boardHeight - 1 - gates[i];
			}
			if (gatesCount % 2 != 0) {
				gates[gatesCount / 2] = line / 2;
			}
		}
		return gates;
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
		if (direction == snake.getDirection().getOpposite())
		// than returns the last cell (tail) of the snake as the offset
			return snake.x(snake.tail());
		// otherwise gets the offset in dependence on the direction
		return SnakeShape.getShiftX(direction);
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
		if (direction == snake.getDirection().getOpposite())
		// than returns the last cell (tail) of the snake as the offset
			return snake.y(snake.tail());
		// otherwise gets the offset in dependence on the direction
		return SnakeShape.getShiftY(direction);
	}
	
	@Override
	protected int getSpeedOfFirstLevel() {
		return 400;
	}
	
	@Override
	protected int getSpeedOfTenthLevel() {
		return 150;
	}
	
	/**
	 * Loading or reloading the specified level
	 */
	@Override
	void loadNewLevel() {
		snake = new SnakeShape(getRotation() == Rotation.CLOCKWISE ? RIGHT : LEFT);
		// starting position - the middle of the bottom border of the board
		curX = boardWidth / 2 + (snake.getDirection() == RIGHT ? -1 : 1);
		
		curY = isToroidalField ? 1 : 0;
		
		move(snake.getDirection());
		
		if (isToroidalField) {
			prepareBorders(!usePreloadedLevels);
		}
		
		if (usePreloadedLevels) {
			loadPreparedObstacle();
		} else {
			loadRandomObstacles();
		}
		addApple();
		
		super.loadNewLevel();
	}
	
	/**
	 * Loading predefined obstacles
	 */
	private void loadPreparedObstacle() {
		if (getLevel() > 1) {
			setBoard(getPreparedObstacles(getBoard(), getSnakeObstacles()[getLevel()]));
		}
	}
	
	/**
	 * Generating random obstacles
	 */
	private void loadRandomObstacles() {
		if (getLevel() > 1) {
			setBoard(getRandomObstacles(getBoard(), getLevel() - 1, 0, 0, isToroidalField ? 2 : 1,
					0));
		}
	}
	
	/**
	 * Tries to move the snake
	 * 
	 * @param direction
	 *            the direction of the movement of the snake
	 * @return {@code true} if the movement succeeded otherwise {@code false}
	 */
	private boolean move(RotationAngle direction) {
		int newX;
		int newY;
		boolean isReversal = direction == snake.getDirection().getOpposite();
		
		Board board = getBoard();
		
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
		if (checkBoardCollision(board, headOfSnake, newX, newY)) if (isToroidalField) {
			if (newX < 0) {
				newX = boardWidth + newX;
			} else if (newX >= boardWidth) {
				newX = newX - boardWidth;
			}
			if (newY < 0) {
				newY = boardHeight + newY;
			} else if (newY >= boardHeight) {
				newY = newY - boardHeight;
			}
			
		} else return false;
		
		boolean isAppleAhead = board.getCell(newX, newY) == Cell.Blink;
		
		SnakeShape newSnake = snake.moveTo(direction, isAppleAhead);
		
		// if the reversal is not possible
		if (isReversal && newSnake.equals(snake)) {
			// returns the old values of the coordinates
			newX = curX;
			newY = curY;
		}
		
		// cut off the tail of the old snake, because the snake should already
		// move and the tail will interfere with checks
		int board_x = curX + snake.x(snake.tail());
		int board_y = curY + snake.y(snake.tail());
		board = drawPoint(board, board_x, board_y, Cell.Empty);
		
		// if it was an apple in front then erase it
		if (isAppleAhead) {
			board.setCell(Cell.Empty, newX, newY);
		}
		
		// check the collision with obstacles
		if (checkCollision(board, headOfSnake, newX, newY)) return false;
		
		// draw the new snake on the board
		setBoard(drawSnake(board, newSnake, newX, newY));
		
		if (isAppleAhead) {
			GameSound.playEffect(Effects.bonus);
			// increases score
			setScore(getScore() + 1);
			// add a new apple
			if (newSnake.getLength() < SnakeShape.getMaxLength()) {
				addApple();
			}
		}
		
		// the old snake is replaced by the new snake
		snake = newSnake.clone();
		curX = newX;
		curY = newY;
		
		// reset timer
		elapsedTime(0);
		
		return true;
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
		int gatesCount;
		int[] gates;
		
		gatesCount = r.nextInt(3) + 1;// 1-3
		gates = generateGates(gatesCount, true);
		// generates the vertical borders
		for (int i = 0; i < boardHeight; i++) {
			if (hasRandomGates && gatesCount > 1) {
				fill = Arrays.binarySearch(gates, i) >= 0 ? Cell.Empty : Cell.Full;
			} else {
				fill = i == boardHeight / 2 ? Cell.Empty : Cell.Full;
			}
			
			getBoard().setCell(fill, 0, i);
			getBoard().setCell(fill, boardWidth - 1, i);
		}
		
		gatesCount = r.nextInt(2) + 1;// 1-2
		gates = generateGates(gatesCount, false);
		// calculates the coordinates for the symmetrical arrangement of the
		// gates
		if (gatesCount > 1) {
			int gateCoord = r.nextInt(boardWidth / 2 - 1) + 1;
			gates[0] = gateCoord;
			gates[1] = boardWidth - 1 - gateCoord;
		}
		
		// generates the horizontal borders
		for (int i = 0; i < boardWidth; i++) {
			if (hasRandomGates && gatesCount > 1) {
				fill = Arrays.binarySearch(gates, i) >= 0 ? Cell.Empty : Cell.Full;
			} else {
				fill = i == boardWidth / 2 ? Cell.Empty : Cell.Full;
			}
			
			getBoard().setCell(fill, i, 0);
			getBoard().setCell(fill, i, boardHeight - 1);
		}
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (keys.isEmpty() || getStatus() == Status.None) return;
		
		super.processKeys();
		
		if (getStatus() == Status.Running && !isInterrupted() && isStarted) {
			if (containsKey(KeyPressed.KeyLeft)) if (move(LEFT)) {
				GameSound.playEffect(Effects.move);
				setKeyDelay(KeyPressed.KeyLeft, ANIMATION_DELAY * 3);
			} else {
				loss(curX, curY);
			}
			if (containsKey(KeyPressed.KeyRight)) if (move(RIGHT)) {
				GameSound.playEffect(Effects.move);
				setKeyDelay(KeyPressed.KeyRight, ANIMATION_DELAY * 3);
			} else {
				loss(curX, curY);
			}
			if (containsKey(KeyPressed.KeyDown)) if (move(DOWN)) {
				GameSound.playEffect(Effects.move);
				setKeyDelay(KeyPressed.KeyDown, ANIMATION_DELAY * 3);
			} else {
				loss(curX, curY);
			}
			if (containsKey(KeyPressed.KeyUp)) if (move(UP)) {
				GameSound.playEffect(Effects.move);
				setKeyDelay(KeyPressed.KeyUp, ANIMATION_DELAY * 3);
			} else {
				loss(curX, curY);
			}
			if (containsKey(KeyPressed.KeyRotate)) if (move(snake.getDirection())) {
				GameSound.playEffect(Effects.move);
				setKeyDelay(KeyPressed.KeyRotate, ANIMATION_DELAY * 2);
			} else {
				loss(curX, curY);
			}
		}
	}
	
}
