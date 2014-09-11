package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class SnakeShape extends Shape {
	/**
	 * Initial size of the snake
	 */
	private final static int INITIAL_LENGTH = 3;
	/**
	 * Maximum size of the snake
	 */
	private final static int MAX_LENGTH = 16;

	/**
	 * Current size of the snake
	 */
	private int length;

	/**
	 * Direction of movement of the snake
	 */
	private RotationAngle direction;

	/**
	 * Constructor of the SnakeShape
	 */
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
	 * The copy constructor of the SnakeShape
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

	/**
	 * Gets the current length of the snake
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gets the maximum possible length of the snake
	 */
	public static int getMaxLength() {
		return MAX_LENGTH;
	}

	/**
	 * Return index of the last cell of the snake
	 */
	public int tail() {
		return this.getLength() - 1;
	}

	/**
	 * Return direction of movement of the snake
	 */
	public RotationAngle getDirection() {
		return direction;
	}

	private SnakeShape setDirection(RotationAngle direction) {
		this.direction = direction;
		return this;
	}

	/**
	 * Returns the direction opposite to the specified direction
	 * 
	 * @param direction
	 *            the specified direction
	 * @return the opposite direction
	 */
	public static RotationAngle getOppositeDirection(RotationAngle direction) {
		return direction.getRight().getRight();
	}

	/**
	 * Increases snake on the one cell in the specified direction
	 * 
	 * @param direction
	 *            direction of movement of the snake
	 * @return the instance of the snake after increasing
	 */
	public SnakeShape eatApple(RotationAngle direction) {
		SnakeShape newSnake = this.clone();

		if (newSnake.getLength() != getMaxLength()) {
			int shiftX = getShiftX(direction);
			int shiftY = getShiftY(direction);

			newSnake.length++;
			for (int i = 1; i <= newSnake.tail(); i++) {
				newSnake.setX(i, x(i - 1) - shiftX);
				newSnake.setY(i, y(i - 1) - shiftY);
			}
			newSnake.setDirection(direction);
		}
		return newSnake;
	}

	/**
	 * Turn of the snake by 180 degrees
	 */
	public SnakeShape turnReversal() {
		SnakeShape newSnake = this.clone();

		RotationAngle direction;

		int shiftX = x(tail()) - x(tail() - 1);
		int shiftY = y(tail()) - y(tail() - 1);

		// sets the new direction in the direction of the tail
		if (shiftX != 0) {
			if (shiftX < 0)
				direction = RotationAngle.d270;
			else
				direction = RotationAngle.d90;
		} else {
			if (shiftY < 0)
				direction = RotationAngle.d180;
			else
				direction = RotationAngle.d0;
		}

		newSnake.setDirection(direction);

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
	 *            direction of movement of the snake
	 * @return the offset for the x-coordinate
	 */
	public static int getShiftX(RotationAngle direction) {
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
	 *            direction of movement of the snake
	 * @return the offset for the y-coordinate
	 */
	public static int getShiftY(RotationAngle direction) {
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
	 *            direction of movement of the snake
	 * @return the instance of the snake after moving
	 */
	private SnakeShape move(RotationAngle direction) {
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
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	public SnakeShape moveTo(boolean isAppleAhead) {
		return turn(getDirection(), isAppleAhead);
	}

	/**
	 * Movement of the snake in the selected direction
	 * 
	 * @param direction
	 *            direction of movement of the snake
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	public SnakeShape moveTo(RotationAngle direction, boolean isAppleAhead) {
		return turn(direction, isAppleAhead);
	}

	/**
	 * Rotating and movement of the snake in the selected direction
	 * 
	 * @param direction
	 *            direction of movement of the snake
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	protected SnakeShape turn(RotationAngle direction, boolean isAppleAhead) {
		if (direction == getOppositeDirection(getDirection())) {
			return turnReversal();
		} else {
			if (isAppleAhead) {
				return eatApple(direction);
			} else {
				return move(direction);
			}
		}
	}

	/**
	 * Movement of the snake in the upward direction
	 * 
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	public SnakeShape moveUp(boolean isAppleAhead) {
		return turn(RotationAngle.d0, isAppleAhead);
	}

	/**
	 * Movement of the snake in the downward direction
	 * 
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	public SnakeShape moveDown(boolean isAppleAhead) {
		return turn(RotationAngle.d180, isAppleAhead);
	}

	/**
	 * Movement of the snake in the left direction
	 * 
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	public SnakeShape moveLeft(boolean isAppleAhead) {
		return turn(RotationAngle.d270, isAppleAhead);
	}

	/**
	 * Movement of the snake in the right direction
	 * 
	 * @param isAppleAhead
	 *            the indication that the snake should eat the apple ahead of
	 *            her
	 * @return the instance of the snake after moving
	 */
	public SnakeShape moveRight(boolean isAppleAhead) {
		return turn(RotationAngle.d90, isAppleAhead);
	}

	@Override
	public String toString() {
		// the figure and its rotation angle
		return "SnakeShape [length: " + getLength() + ", " + getDirection()
				// (head) - (tail)
				+ ", [" + x(0) + ";" + y(0) + "]<-[" + x(tail()) + ";"
				+ y(tail()) + "]]\n" + super.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + length;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnakeShape other = (SnakeShape) obj;
		if (direction != other.direction)
			return false;
		if (length != other.length)
			return false;
		return true;
	}

}
