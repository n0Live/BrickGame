package com.kry.brickgame.shapes;

import java.awt.Point;

/**
 * @author noLive
 */
public abstract class CoordinatedShape extends CharacterShape {
	private static final long serialVersionUID = -1378907881713510647L;
	/**
	 * X-coordinate position
	 */
	protected int x;
	/**
	 * Y-coordinate position
	 */
	protected int y;
	
	public CoordinatedShape(CoordinatedShape aShape) {
		super(aShape);
		
		setX(aShape.x());
		setY(aShape.y());
	}
	
	public CoordinatedShape(int type, int length) {
		super(type, length);
		
		setX(0 - minX());
		setY(0 - minY());
	}
	
	public CoordinatedShape(int type, int length, int x, int y, RotationAngle direction) {
		super(type, length);
		
		this.x = x;
		this.y = y;
		changeRotationAngle(direction);
	}
	
	@Override
	public CoordinatedShape clone() {
		return (CoordinatedShape) super.clone();
	}
	
	/**
	 * Gets the coordinates of the current position
	 * 
	 * @return coordinates of the current position
	 */
	public Point getCoordinates() {
		return new Point(x, y);
	}
	
	/**
	 * Returns the movement direction
	 * 
	 * @return movement direction
	 */
	public RotationAngle getDirection() {
		return getRotationAngle();
	}
	
	/**
	 * Setting the coordinates of the new position depending of the direction
	 * 
	 * @param direction
	 *            movement direction
	 */
	public void move(RotationAngle direction) {
		switch (direction) {
		case d0:
			y++;
			break;
		case d90:
			x++;
			break;
		case d180:
			y--;
			break;
		case d270:
			x--;
			break;
		}
	}
	
	/**
	 * Setting the coordinates of the new position
	 * 
	 * @param position
	 *            coordinates of the new position
	 */
	public void setCoordinates(Point position) {
		x = position.x;
		y = position.y;
	}
	
	/**
	 * Setting the x-coordinate position on the board
	 * 
	 * @param coordX
	 *            - the x-coordinate
	 */
	public void setX(int coordX) {
		x = coordX;
	}
	
	/**
	 * Setting the y-coordinate position on the board
	 * 
	 * @param coordY
	 *            - the y-coordinate
	 */
	public void setY(int coordY) {
		y = coordY;
	}
	
	@Override
	public String toString() {
		return "[" + this.x() + ", " + this.y() + "]\n" + super.toString();
	}
	
	/**
	 * X-coordinate position on the board
	 */
	public int x() {
		return x;
	}
	
	/**
	 * Y-coordinate position on the board
	 */
	public int y() {
		return y;
	}
	
}
