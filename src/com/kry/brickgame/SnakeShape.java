package com.kry.brickgame;

/**
 * @author noLive
 * 
 */
public class SnakeShape extends Shape {
	/**
	 * Initial size of the snake
	 */
	private final static int INITIAL_LENGTH = 7;//TODO replace on 3 after tests
	/**
	 * Maximum size of the snake
	 */
	private final static int MAX_LENGTH = 13;

	/**
	 * Current size of the snake
	 */
	private int length;

	/**
	 * Direction of movement of the snake
	 */
	private RotationAngle direction;

	public SnakeShape() {
		super(MAX_LENGTH);
		this.length = INITIAL_LENGTH;
		this.setDirection(RotationAngle.d270);

		// Initialization of the snake
		for (int i = 0; i < getLength(); i++) {
			setX(i, i);
			setY(i, 0);
		}
	}

	/**
	 * The copy constructor of a SnakeShape
	 * 
	 * @param aSnakeShape
	 *            a SnakeShape object for copying
	 */
	public SnakeShape(SnakeShape aSnakeShape) {
		super(MAX_LENGTH);

		this.length = aSnakeShape.getLength();
		this.setDirection(aSnakeShape.getDirection());

		for (int i = 0; i < MAX_LENGTH; i++) {
			this.setX(i, aSnakeShape.x(i));
			this.setY(i, aSnakeShape.y(i));
		}
	}

	public SnakeShape clone() {
		SnakeShape newSnakeShape = new SnakeShape(this);
		return newSnakeShape;
	}

	public int getLength() {
		return length;
	}

	/**
	 * Return index of the last cell of the snake
	 */
	protected int tail() {
		return this.getLength() - 1;
	}

	public RotationAngle getDirection() {
		return direction;
	}

	private void setDirection(RotationAngle direction) {
		this.direction = direction;
	}

	/**
	 * Turn of the snake by 180 degrees
	 */
	public SnakeShape turnReversal() {
		SnakeShape newSnake = this.clone();

		// 180-degree turn
		newSnake.setDirection(getDirection().getRight().getRight());

		int lastCellX = x(tail());
		int lastCellY = y(tail());

		// change the value of the first cell to the last and so on
		for (int i = 0; i < getLength(); i++) {
			newSnake.setX(i, x(tail() - i) - lastCellX);
			newSnake.setY(i, y(tail() - i) - lastCellY);
		}

		return newSnake;
	}

	/**
	 * Gets the offset for the x-coordinate in dependence on the direction
	 * 
	 * @param direction
	 *            direction of movement of of the snake
	 * @return the offset for the x-coordinate
	 */
	public int getShiftX(RotationAngle direction) {
		switch (direction) {
		case d0:
			return 0;
		case d90:
			return 1;
		case d180:
			return 0;
		case d270:
			return -1;
		default:
			return 0;
		}
	}

	/**
	 * Gets the offset for the y-coordinate in dependence on the direction
	 * 
	 * @param direction
	 *            direction of movement of of the snake
	 * @return the offset for the y-coordinate
	 */
	public int getShiftY(RotationAngle direction) {
		switch (direction) {
		case d0:
			return 1;
		case d90:
			return 0;
		case d180:
			return -1;
		case d270:
			return 0;
		default:
			return 0;
		}
	}

	/**
	 * Movement of the snake in the selected direction
	 * 
	 * @param direction
	 *            direction of movement of of the snake
	 * @return the instance of of the snake after moving
	 */
	protected SnakeShape move(RotationAngle direction) {
		SnakeShape newSnake = this.clone();
		newSnake.setDirection(direction);

		int shiftX = getShiftX(direction);
		int shiftY = getShiftY(direction);

		for (int i = tail(); i > 0; i--) {
			newSnake.setX(i, x(i - 1) - shiftX);
			newSnake.setY(i, y(i - 1) - shiftY);
		}

		return newSnake;
	}

	/**
	 * Movement of the snake in the current direction
	 * 
	 * @return the instance of of the snake after moving
	 */
	public SnakeShape move() {
		return move(getDirection());
	}

	/**
	 * Rotating and movement of the snake in the selected direction
	 * 
	 * @param direction
	 *            direction of movement of of the snake
	 * @return the instance of of the snake after moving
	 */
	protected SnakeShape turn(RotationAngle direction) {
		if (direction == getDirection().getRight().getRight()) {
			return turnReversal();
		} else {
			return move(direction);
		}
	}

	/**
	 * Movement of the snake in the upward direction
	 * 
	 * @return the instance of of the snake after moving
	 */
	public SnakeShape moveUp() {
		return turn(RotationAngle.d0);
	}

	/**
	 * Movement of the snake in the downward direction
	 * 
	 * @return the instance of of the snake after moving
	 */
	public SnakeShape moveDown() {
		return turn(RotationAngle.d180);
	}

	/**
	 * Movement of the snake in the left direction
	 * 
	 * @return the instance of of the snake after moving
	 */
	public SnakeShape moveLeft() {
		return turn(RotationAngle.d270);
	}

	/**
	 * Movement of the snake in the right direction
	 * 
	 * @return the instance of of the snake after moving
	 */
	public SnakeShape moveRight() {
		return turn(RotationAngle.d90);
	}

	@Override
	public String toString() {
		// the figure and its rotation angle
		return "SnakeShape [length: " + getLength() + ", " + getDirection()
				// (head) - (tail)
				+ ", [" + x(0) + ";" + y(0) + "]<-[" + x(tail()) + ";"
				+ y(tail()) + "]]\n" + super.toString();
	}

}
