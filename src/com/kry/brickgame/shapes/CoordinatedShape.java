package com.kry.brickgame.shapes;

import java.awt.Point;

public abstract class CoordinatedShape extends CharacterShape {
	/**
	 * X-coordinate position
	 */
	protected int x;
	/**
	 * Y-coordinate position
	 */
	protected int y;

	public CoordinatedShape(int type, int length, int x, int y,
			RotationAngle direction) {
		super(type, length);

		this.x = x;
		this.y = y;
		changeRotationAngle(direction);
	}

	public CoordinatedShape(int type, int length) {
		super(type, length);

		setX(0 - minX());
		setY(0 - minY());
	}

	public CoordinatedShape(CoordinatedShape aShape) {
		super(aShape);

		setX(aShape.x());
		setY(aShape.y());
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
	 * Returns the movement direction
	 * 
	 * @return movement direction
	 */
	public RotationAngle getDirection() {
		return this.getRotationAngle();
	}

	/**
	 * X-coordinate position on the board
	 */
	public int x() {
		return x;
	}

	/**
	 * Setting the x-coordinate position on the board
	 * 
	 * @param x
	 *            - the x-coordinate
	 */
	public void setX(int coordX) {
		this.x = coordX;
	}

	/**
	 * Y-coordinate position on the board
	 */
	public int y() {
		return y;
	}

	/**
	 * Setting the y-coordinate position on the board
	 * 
	 * @param y
	 *            - the y-coordinate
	 */
	public void setY(int coordY) {
		this.y = coordY;
	}

	/**
	 * Setting the coordinates of the new position
	 * 
	 * @param position
	 *            coordinates of the new position
	 */
	public void setCoordinates(Point position) {
		this.x = position.x;
		this.y = position.y;
	}

	/**
	 * Gets the coordinates of the current position
	 * 
	 * @return coordinates of the current position
	 */
	public Point getCoordinates() {
		return new Point(x, y);
	}

}
