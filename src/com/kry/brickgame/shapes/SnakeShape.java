package com.kry.brickgame.shapes;

/**
 * @author noLive
 */
public class SnakeShape extends Shape {
	private static final long serialVersionUID = -394507760656713746L;
	/**
	 * Initial size of the snake
	 */
	private final static int INITIAL_LENGTH = 3;
	/**
	 * Maximum size of the snake
	 */
	private final static int MAX_LENGTH = 16;
	
	/**
	 * Gets the maximum possible length of the snake
	 */
	public static int getMaxLength() {
		return MAX_LENGTH;
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
	 * Current size of the snake
	 */
	private int length;
	
	/**
	 * Direction of the movement of the snake
	 */
	private RotationAngle direction;
	
	/**
	 * Constructor of the SnakeShape
	 */
	public SnakeShape(RotationAngle direction) {
		super(MAX_LENGTH);
		length = INITIAL_LENGTH;
		setDirection(direction);
		
		// Initialization of the snake
		for (int i = 0; i < getLength(); i++) {
			switch (direction) {
			case d0:// up
				setX(i, 0);
				setY(i, -i);
				break;
			case d90:// right
				setX(i, -i);
				setY(i, 0);
				break;
			case d180:// down
				setX(i, 0);
				setY(i, i);
				break;
			case d270:// left
				setX(i, i);
				setY(i, 0);
				break;
			}
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
		
		length = aSnakeShape.getLength();
		setDirection(aSnakeShape.getDirection());
		
		for (int i = 0; i < MAX_LENGTH; i++) {
			setX(i, aSnakeShape.x(i));
			setY(i, aSnakeShape.y(i));
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
		SnakeShape newSnake = new SnakeShape(this);
		newSnake.setDirection(direction);
		
		int shiftX = getShiftX(direction);
		int shiftY = getShiftY(direction);
		
		for (int i = tail(); i > 0; i--) {
			newSnake.setX(i, x(i - 1) - shiftX);
			newSnake.setY(i, y(i - 1) - shiftY);
		}
		
		return newSnake;
	}
	
	private SnakeShape setDirection(RotationAngle direction) {
		this.direction = direction;
		return this;
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
		if (direction == getDirection().getOpposite()) return turnAround();
		if (isAppleAhead) return eatApple(direction);
		return move(direction);
	}
	
	@Override
	public SnakeShape clone() {
		SnakeShape cloned = (SnakeShape) super.clone();
		return cloned;
	}
	
	/**
	 * Increases snake on the one cell in the specified direction
	 * 
	 * @param direction
	 *            direction of movement of the snake
	 * @return the instance of the snake after increasing
	 */
	public SnakeShape eatApple(RotationAngle direction) {
		SnakeShape newSnake = new SnakeShape(this);
		
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		SnakeShape other = (SnakeShape) obj;
		if (direction != other.direction) return false;
		if (length != other.length) return false;
		return true;
	}
	
	/**
	 * Return direction of movement of the snake
	 */
	public RotationAngle getDirection() {
		return direction;
	}
	
	/**
	 * Gets the current length of the snake
	 */
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (direction == null ? 0 : direction.hashCode());
		result = prime * result + length;
		return result;
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
	 * Return index of the last cell of the snake
	 */
	public int tail() {
		return getLength() - 1;
	}
	
	@Override
	public String toString() {
		// the figure and its rotation angle
		return "SnakeShape [length: " + getLength() + ", " + getDirection()
		        // (head) - (tail)
		        + ", [" + x(0) + ";" + y(0) + "]<-[" + x(tail()) + ";" + y(tail()) + "]]\n"
		        + super.toString();
	}
	
	/**
	 * Turn of the snake by 180 degrees
	 */
	@Override
	public SnakeShape turnAround() {
		SnakeShape newSnake = new SnakeShape(this);
		
		RotationAngle direction;
		
		int shiftX = x(tail()) - x(tail() - 1);
		int shiftY = y(tail()) - y(tail() - 1);
		
		// sets the new direction in the direction of the tail
		if (shiftX != 0) {
			if (shiftX < 0) {
				direction = RotationAngle.d270;
			} else {
				direction = RotationAngle.d90;
			}
		} else {
			if (shiftY < 0) {
				direction = RotationAngle.d180;
			} else {
				direction = RotationAngle.d0;
			}
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
	
}
